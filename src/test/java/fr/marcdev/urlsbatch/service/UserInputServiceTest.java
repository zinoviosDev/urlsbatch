package fr.marcdev.urlsbatch.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.InputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import fr.marcdev.urlsbatch.UrlsBatchApplication;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@SpringBootTest(classes = UrlsBatchApplication.class)
@Disabled
@Slf4j
class UserInputServiceTest {
	
	private static final InputStream DEFAULT_STDIN = System.in;
	
	@InjectMocks UserInputService service;
	
	

	@DisplayName("Test should get user input")
	@Test
	void shouldGetUserInput() {
		assertThat(this.service, is(notNullValue()));
	}
	
	@AfterEach
	void rollbackChangesToStdin() {
	    System.setIn(DEFAULT_STDIN);
	}
	
	@AfterAll
	static void afterAllTestsHadRun() {
		log.info("End of UserInputServiceTest tests");
	}
}
