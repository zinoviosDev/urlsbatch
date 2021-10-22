package fr.marcdev.urlsbatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import fr.marcdev.urlsbatch.service.MainService;
import fr.marcdev.urlsbatch.service.UserInputService;

@ActiveProfiles("test")
@SpringBootTest(properties = { "command.line.runner.enabled=false" })
class UrlsbatchApplicationTests {
	
    @Autowired
    private ApplicationContext context;

    @Test
    void whenContextLoads_thenRunnersAreNotLoaded() {
        assertNotNull(this.context.getBean(UserInputService.class));
        assertThrows(NoSuchBeanDefinitionException.class, 
          () -> this.context.getBean(MainService.class), 
          "MainService should not be loaded during this integration test");
    }
}
