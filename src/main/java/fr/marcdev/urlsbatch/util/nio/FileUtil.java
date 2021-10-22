package fr.marcdev.urlsbatch.util.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	
	public List<Path> searchByName(String name) throws IOException {
		List<Path> path = null;
		try {
			path = Files.find(Paths.get("."), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile())
			.filter(p -> p.toString().endsWith(name))
			.collect(Collectors.toList());
		} catch (IOException e) {
			log.error("Encountered error while reading path");
			throw e;
		}
		return path;
	}
}
