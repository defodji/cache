package com.exercise.cache.component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.exercise.cache.replacementalgorithm.AccessType;
import com.exercise.cache.replacementalgorithm.CacheReplacementAlgorithm;

/**
 * A CacheSet stores a set of cache entries. A read/write lock is used to make
 * sure that the cache set is thread-safe.
 *
 * @param <E>
 *            A subclass of CacheEntry
 */
public class CacheSet<K,V> {

	private final Map<K, V> cacheContent;
	private final int setSize;
	private final CacheReplacementAlgorithm<K> cacheAlgorithm;
	private final ReentrantReadWriteLock reentrantReadWriteLock;

	public CacheSet(int setSize, CacheReplacementAlgorithm<K> cacheAlgorithm) {
		this.cacheContent = new HashMap<>();
		this.setSize = setSize;
		this.cacheAlgorithm = cacheAlgorithm;
		reentrantReadWriteLock = new ReentrantReadWriteLock(false);
	}

	/**
	 * Add the specified value to the CacheSet
	 *
	 * @param value
	 *            The CacheEntry to add
	 */
	public void add(K newKey, V newValue) {
		try {
			reentrantReadWriteLock.writeLock().lock();
			if (cacheContent.keySet().size() < setSize) {
				cacheContent.put(newKey, newValue);
			} else {
				freeCacheSpace();
				cacheContent.put(newKey, newValue);
			}
			cacheAlgorithm.notifyCacheAccess(newKey, AccessType.WRITE);
		} finally {
			reentrantReadWriteLock.writeLock().unlock();
		}
	}

	/**
	 * Returns true if the given cache entry key is present in the CacheSet
	 *
	 * @param cacheKeyToFind
	 * @return
	 */
	public boolean contains(K cacheKeyToFind) {
		return cacheContent.keySet().contains(cacheKeyToFind);
	}

	/**
	 * Get the value related with the specified key
	 *
	 * @param key
	 *            the key to use when searching for the CacheEntry
	 * @param <K>
	 *            The type of the key
	 * @return the CacheEntry
	 */
	public V get(K key) {
		try {
			reentrantReadWriteLock.readLock().lock();
			V value = cacheContent.get(key);
			if (value != null) {
				cacheAlgorithm.notifyCacheAccess(key, AccessType.READ);
				return value;
			}
			return null;
		} finally {
			reentrantReadWriteLock.readLock().unlock();
		}
	}

	/**
	 * Frees an entry in the cache using the specified algorithm
	 */
	private void freeCacheSpace() {
		K cacheEntryKeyToRemove = cacheAlgorithm.entryKeyToRemove();
		cacheContent.remove(cacheEntryKeyToRemove);
		cacheAlgorithm.notifyCacheAccess(cacheEntryKeyToRemove, AccessType.REMOVAL);
	}
}
