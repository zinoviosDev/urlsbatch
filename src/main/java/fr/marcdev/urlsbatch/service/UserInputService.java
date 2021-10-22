package fr.marcdev.urlsbatch.service;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInputService {
	
	public Path getUserInput() {
		String enteredDirPath = null;
        try(var scanner = new Scanner(System.in)) {
            while (enteredDirPath == null) {
                enteredDirPath = StringEscapeUtils.escapeJava(scanner.nextLine());
            }
        } catch(IllegalStateException | NoSuchElementException
        		| InvalidPathException | NullPointerException e) {
        	log.error("The is an error while reading user Input", e);
        }
		return Paths.get(enteredDirPath);
	}
}
