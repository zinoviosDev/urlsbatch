package fr.marcdev.urlsbatch.util.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {

	public static <O extends Object> List<O> concat(final List<O> o1, final List<O> o2) {
		List<O> result = new ArrayList<>();
		Optional.of(o1).map(List<O>::stream).ifPresentOrElse(
				obj1 -> Optional.of(o2).map(List<O>::stream)
				.ifPresentOrElse(obj2 -> result.addAll(Stream.concat(obj1, obj2).collect(Collectors.toList())), () -> result.addAll(o1)),
				() -> result.addAll(o2)
		);
		return result;
	}
}
