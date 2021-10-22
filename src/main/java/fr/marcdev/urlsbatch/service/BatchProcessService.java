package fr.marcdev.urlsbatch.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.marcdev.urlsbatch.exception.handling.ThrowingFunction;
import fr.marcdev.urlsbatch.model.URLOccurrence;
import fr.marcdev.urlsbatch.model.URLQuery;
import fr.marcdev.urlsbatch.model.UrlStat;
import fr.marcdev.urlsbatch.util.collections.ListUtil;
import fr.marcdev.urlsbatch.util.collections.MapUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BatchProcessService {
	
	@Value("${urls.types.to.validate}") String[] schemes;
	@Value("${log.file.extensions}") String[] logFileExtensions;
	@Value("${buffer.reader.size}") int bufferReaderSize;
	@Value("${key.parameter}") String keyParam;
	@Value("${value.nb.with.highest.occur}") int valueNbWithHighestOccur;
	
	@Autowired FilesService filesService;
	
	final static Pattern PATTERN = Pattern.compile("[_]");
    	
	/**
	 * Parse a list of log files and returns the list of keys values (like 535) which have this highest occurrence 
	 * @param logDirPath The path of directory containing logs
	 * @return List<URLOccurrence> the list of URLOccurence containing (domaine, keyId, list of keyValues, occNumber)
	 */
	public List<URLOccurrence> computeURLOccurrencesFromLogDirectory(final Path logDirPath) {
		final var logFileExtWrap = "*.{" + String.join(", ", this.logFileExtensions) + "}";		
		return computeUrlOccurrences(buildDataModel(filesService.newDirectoryStream(logDirPath, logFileExtWrap)));
	}
	
	protected UrlStat buildDataModel(final InputStream inptstream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inptstream), bufferReaderSize);
		Stream<URLQuery> uqstream = reader.lines().parallel().map(ThrowingFunction.unchecked(l -> new URLQuery(new URI(l), keyParam))).sequential();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
		UrlStat urlstat = new UrlStat(150,0.75f,4);
		executorService.execute(() -> {
			urlstat.putAll(uqstream.collect(() -> new UrlStat(150,0.75f,4),
					(urlst, uq) -> buildKey(uq).ifPresent(urlst::addOccurrence), ConcurrentMap::putAll));
		});
		executorService.shutdown();
        try {
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("An error happened while collecting data from file", e);
		}
        return urlstat;
//		return uqstream.collect(() -> new UrlStat(150,0.75f,4), (urlst, uq) -> buildKey(uq).ifPresent(urlst::addOccurrence), ConcurrentMap::putAll);
	}

	protected List<URLOccurrence> computeUrlOccurrences(final UrlStat urlStats) {
		return MapUtil.getHighestValuesWithKeysPairs(urlStats, valueNbWithHighestOccur)
			.entrySet().parallelStream().map(this::getUocc).flatMap(List::stream).collect(Collectors.toList());
	}
	
	protected List<URLOccurrence> getUocc(final Entry<Integer, List<String>> entry) {
		return entry.getValue().stream().map(k -> PATTERN.split(k))
			.map(ke -> new URLOccurrence(ke[0], ke[1], Arrays.asList(ke[2]), entry.getKey()))
			.collect(ArrayList<URLOccurrence>::new, 
				(s, e) -> s.stream().filter(fromS -> fromS.equals(e)).findAny()
				.ifPresentOrElse(fromS -> fromS.setKeyvalues(ListUtil.concat(fromS.getKeyvalues(), e.getKeyvalues())), () -> s.add(e))
			, List::addAll);
	}
	
	private synchronized Optional<String> buildKey(final URLQuery uquery) {
		return (Optional<String>)(Optional.ofNullable(uquery.getQueryParameters().get(this.keyParam))
			.map(value -> String.join("_", uquery.getURI().getHost(), this.keyParam, value)));
	}
}
