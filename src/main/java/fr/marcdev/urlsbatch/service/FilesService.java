package fr.marcdev.urlsbatch.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FilesService {

	public BufferedInputStream newDirectoryStream(final Path logDirPath, final String logFileExtWrap) {
		try {
			return new BufferedInputStream(new SequenceInputStream(Collections.enumeration(
					StreamSupport.stream(Files.newDirectoryStream(logDirPath, logFileExtWrap).spliterator(), false)
					.map(p -> this.newInputStream(p).orNull()).collect(Collectors.toList())
			)));
		} catch (IOException e) {
			log.error("An error occured when reading log directory", e);
		}
		return null;
	}
	
	public List<File> searchFilesInPath(Path directory, String mustContains, String mustNotContains) {
		List<File> files = null;
		try {
			files = Files.list(directory).map(Path::toFile)
					.filter((mustNotContains != null) ? f -> !f.getAbsolutePath().contains(mustNotContains) : f -> true)
			        .filter((mustContains != null) ? f -> f.getAbsolutePath().contains(mustContains) : f -> true)
			        .collect(Collectors.toList());
		} catch (IOException e) {
			log.error("An error occured when reading directory", e);
		}
		return files;
	}
	
	public Optional<InputStream> newInputStream(final Path p) {
		InputStream is = null;
		try {
			is = Files.newInputStream(p);
		} catch (IOException e) {
			log.error("An error occured when reading log file", e);
		}
		return Optional.of(is);
	}
	
	public static void main(String[] args) {
		FilesService fs = new FilesService();
		fs.searchFilesInPath(Paths.get("."), "TODO", "^[^\\.].*$").forEach(System.out::println);
		fs.searchFilesInPath(Paths.get("."), null, "^[^\\.].*$").forEach(System.out::println);
	}
}
