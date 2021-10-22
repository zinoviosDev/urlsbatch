package fr.marcdev.urlsbatch.service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.marcdev.urlsbatch.config.ConsoleConstants;
import fr.marcdev.urlsbatch.model.URLOccurrence;
import lombok.extern.slf4j.Slf4j;

@Profile("!test")
@ConditionalOnProperty(prefix = "command.line.runner", value = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
@Service
public class MainService implements CommandLineRunner {
	
	@Autowired private BatchProcessService batchProcessService;
	@Autowired private UserInputService userInputService;
	
	@Value("${value.nb.with.highest.occur}")
	int valueNbWithHighestOccurencesToFind;
	
	@Value("${log.dir.path}")
	String logDirPath;

	@Override
	public void run(String... args) throws Exception {
		log.info(ConsoleConstants.DEFAULT_START_MESSAGE);
		Optional<Path> path = Optional.ofNullable(Path.of(logDirPath));
		if(path.isEmpty()) {
			log.info(ConsoleConstants.INPUT_FOLDER_PATH_INSTRUCTIONS);
			path = Optional.of(this.userInputService.getUserInput()); // should not be null
		}
		displayResults(this.batchProcessService.computeURLOccurrencesFromLogDirectory(path.get()));
	}

	/**
	 * * +-----------------+-----+------------+-------+
	 * | Domaine         | Clé | Val clé    | Nb occ.
	 * +-----------------+-----+------------+-------+
	 * | bid.org         | id  | 535        | 43    |
	 * | a.com           | id  | 263        | 42    |
	 * | test.net        | id  | 561        | 41    |
	 * | bid.org         | id  | 303, 992   | 40    |
	 * | bid.org         | id  | 828        | 39    |
	 * | test.net        | id  | 265, 232   | 39    |
	 * +-----------------+-----+------------+-------+
	 * 
	 * @param urlStats
	 */
	private void displayResults(final List<URLOccurrence> urlStats) {
		log.info(ConsoleConstants.RESULT_TABLE_HEADER);
		urlStats.forEach(u -> System.out.format(ConsoleConstants.RESULT_TABLE_ALIGN_FORMAT, 
							u.getDomain(), u.getKey(), String.join(", ", u.getKeyvalues()), u.getKeyValueOccurrence()));
		log.info(ConsoleConstants.RESULT_TABLE_FOOTER);
		
		//StringUtils.
	}
}
