package fr.marcdev.urlsbatch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.google.common.collect.Comparators;

import fr.marcdev.urlsbatch.util.collections.MapUtil;

@ActiveProfiles("test")
@SpringBootTest(properties = { "command.line.runner.enabled=false" })
public class MapUtilsTest {
	
	private static Map<String, Integer> unsortedmap;
	
	@BeforeAll
    static void beforeAllTests_should_be_loaded() {
		unsortedmap = new HashMap<>();
		unsortedmap.put("test.net_id_197", 13);
		unsortedmap.put("test.net_id_198", 21);
		unsortedmap.put("test.net_id_199", 23);
		unsortedmap.put("a.com_id_959", 31);
		unsortedmap.put("bid.org_id_362", 20);
		unsortedmap.put("a.com_id_957", 29);
		unsortedmap.put("bid.org_id_364", 28);
		unsortedmap.put("a.com_id_955", 21);
		unsortedmap.put("test.net_id_568", 31);
    }

	
	@DisplayName("whenAnUnsortedMapIsGiven_ItShouldBeSortedByDescValues")
	@Test
	void whenAnUnsortedMapIsGiven_ItShouldBeSortedByDescValues() {
		var sortedmap = MapUtil.getSortedMapByDescValues(unsortedmap);
		
		assertThat(sortedmap.entrySet(), everyItem(is(in(unsortedmap.entrySet()))));
		// assertThat(sortedmap.entrySet(), containsInAnyOrder(unsortedmap.entrySet()));
		// assertThat(sortedmap.entrySet(), both(everyItem(is(in(unsortedmap.entrySet())))).and(containsInAnyOrder(unsortedmap.entrySet())));
		assertThat(Comparators.isInOrder(new ArrayList<Integer>(sortedmap.values()), Comparator.<Integer> reverseOrder()), is(true));
	}
	
	
	@DisplayName("givenASortedMapAndHighestValues_ItShouldGiveAMapContainingOldValuesAsKeysAndCorrespondingListOfKeysAsValues")
	@Test
	void givenASortedMapAndHighestValues_ItShouldGiveAMapContainingOldValuesAsKeysAndCorrespondingListOfKeysAsValues() {
//		int hvn = 5;
//		Map<Integer, List<String>> map = MapUtil.getValueKeysMapList(MapUtil.getSortedMapByDescValues(unsortedmap), hvn);
		// assertThat(map.keySet().stream().collect(Collectors.toList()), is(equalTo(highestValues)));
	}
}
