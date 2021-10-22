package fr.marcdev.urlsbatch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import fr.marcdev.urlsbatch.model.URLOccurrence;
import fr.marcdev.urlsbatch.util.collections.MapUtil;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@SpringBootTest(properties = { "command.line.runner.enabled=false" })
@Slf4j
class BatchProcessServiceUT {

	@Value("${key.parameter}") String keyParam;
	
	@Autowired
    private ApplicationContext context;
	
	@SpyBean
	private BatchProcessService batchProcessService;
	
	Entry<Integer, List<String>> entry;
	
	@BeforeEach
    void beforeExecutingTests_context_and_batchProcessService_should_be_loaded() {
        assertThat(this.context.getBean(BatchProcessService.class), is(notNullValue()));
        
        Map<Integer, List<String>> map = new HashMap<>();
		map.put(40, List.of("bid.org_id_303", "bid.org_id_992"));
		entry = map.entrySet().stream().findFirst().get();
		
		MockedStatic<MapUtil> mapUtilMock = Mockito.mockStatic(MapUtil.class);
//		mapUtilMock.when(MapUtil.getHighestValuesWithKeysPairs(
//				new HashMap<Integer, String>(), 0))
//		.thenReturn(new HashMap<Integer, String>());
    }
	
	@AfterAll
	static void whenAllTestsEnded() {
		log.info("All tests are finished");
	}
	
	@Test
	void testGetUocc() {
		List<URLOccurrence> urlOccs = batchProcessService.getUocc(entry);
		assertThat(urlOccs, hasSize(1));
		URLOccurrence uo = urlOccs.get(0);
		assertThat(uo, is(equalTo(new URLOccurrence("bid.org", "id", List.of("303", "992"), 40))));
	}
}
