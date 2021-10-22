package fr.marcdev.urlsbatch.util.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapUtil {
	
	private MapUtil() {}
	
	// get a Map of values with corresponding list of given keys
	public static <K, V extends Comparable<V>> Map<V, List<K>> getHighestValuesWithKeysPairs(final Map<K, V> unSortedMap, final int highestValuesNb) {
		return getValueKeysMapList(getSortedMapByDescValues(unSortedMap), highestValuesNb);
	}
	
	// sort map by highest values
	public static <K, V extends Comparable<V>> Map<K, V> getSortedMapByDescValues(final Map<K, V> unSortedMap) {	 
		return unSortedMap.entrySet().parallelStream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.collect(() -> Collections.synchronizedMap(new LinkedHashMap<K, V>(unSortedMap.size())),
				(s, e) -> s.put(e.getKey(), e.getValue()), Map::putAll);
	}
	
	// get a Map of values with corresponding list of given keys
	public static <K, V extends Comparable<V>> Map<V, List<K>> getValueKeysMapList(final Map<K, V> sortedMapByDescValues, final int highestValuesNb) {
		final List<V> hv = sortedMapByDescValues.values().stream().distinct().limit(highestValuesNb).collect(Collectors.toList());
		final Map<K, V> reducedMap = sortedMapByDescValues.entrySet().stream().takeWhile(e -> e.getValue().compareTo(hv.get(highestValuesNb - 1)) >= 0)
			.collect(LinkedHashMap<K,V>::new, (s, e) -> s.put(e.getKey(), e.getValue()), Map::putAll);
		return hv.stream().collect(() -> new LinkedHashMap<V, List<K>>(hv.size()), (s, v) -> s.put(v, getKeysByValue(reducedMap, v)), Map::putAll);
	}
	
	// get a List of Map keys corresponding to a given value
	public static <K, V> List<K> getKeysByValue(final Map<K, V> map, final V value) {
		return map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), value)).map(Map.Entry::getKey).collect(Collectors.toList());
	}
}
