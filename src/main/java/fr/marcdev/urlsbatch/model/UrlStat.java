package fr.marcdev.urlsbatch.model;

import java.util.concurrent.ConcurrentHashMap;

public class UrlStat extends ConcurrentHashMap<String, Integer> {

	private static final long serialVersionUID = 2310364751698884446L;

	public synchronized void addOccurrence(final String value) {
		this.computeIfPresent(value, (k, v) -> ++v);
		this.computeIfAbsent(value, v -> 1);
	}

	public UrlStat(final int initialCapacity, final float loadFactor, final int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
}
