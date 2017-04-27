package com.exercise.cache.replacementalgorithm.impl;

/**
 * The Least Recently Used (LRU) cache replacement algorithm
 *
 * @param <K>
 *            Type of cache entry key
 */
public class LRUCacheReplacementAlgorithm<K> extends TimeReplacementAlgorithm<K> {

	@Override
	public K entryKeyToRemove() {
		return tail.getKey();
	}

	@Override
	protected void notifyRemoval() {
		entryTimestamps.remove(tail.getKey());
		remove(tail);
	}

}
