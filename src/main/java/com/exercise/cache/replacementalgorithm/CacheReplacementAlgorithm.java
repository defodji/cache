package com.exercise.cache.replacementalgorithm;


/**
 * This interface describe the template for a cache replacement algorithm. It
 * can be implemented to add another cache replacement algorithm.
 *
 * @param <E>
 *            Cache Entry type
 */
public interface CacheReplacementAlgorithm<K> {

	/**
	 * Returns the next entry to be removed
	 *
	 * @return The key of the entry of the cache that should be removed
	 */
	public K entryKeyToRemove();

	/**
	 * Notifies the cache eviction algorithm of an access (read, write or
	 * removal) to the cache set. The data collected by this method will be used
	 * to find the element of the set to be replaced when the set is full
	 *
	 *
	 * @param cacheEntry
	 *            The cache entry which has just been retrieved
	 * @param accessType
	 *            the type of access (READ, WRITE or REMOVAL)
	 */
	public void notifyCacheAccess(K entryKey, AccessType accessType);

}
