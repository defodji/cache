package com.exercise.cache.replacementalgorithm.impl;

/**
 * The Most Recently Used (LRU) cache replacement algorithm
 *
 * @param <E>
 *            Type of cache entry
 */
public class MRUCacheReplacementAlgorithm<K> extends TimeReplacementAlgorithm<K> {

	@Override
	public K entryKeyToRemove() {
		return head.getKey();
	}

	@Override
	protected void notifyRemoval() {
		entryTimestamps.remove(head.getKey());
		remove(head);
	}

}
