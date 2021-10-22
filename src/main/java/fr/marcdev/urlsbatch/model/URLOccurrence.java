package fr.marcdev.urlsbatch.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class URLOccurrence {
	private String domain, key;
	@EqualsAndHashCode.Exclude private List<String> keyvalues;
	private Integer keyValueOccurrence;
}
