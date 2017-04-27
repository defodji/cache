package com.exercise.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exercise.cache.component.CacheSet;
import com.exercise.cache.replacementalgorithm.CacheReplacementAlgorithm;

/**
 * Implements an N-way, Set-Associative cache
 * <p>
 * The cache itself is entirely in memory. The maximum number of sets and their
 * maximum capacity are parameterized. If an item cannot be stored then the
 * specified replacement algorithm will be executed to identify an evictee.
 * <p>
 *
 * @param <K>
 *            Type of the key
 * @param <V>
 *            Type of the value
 */
public class SetAssociativeCache<K, V> {
	private static final Logger logger = LogManager.getLogger(SetAssociativeCache.class);

	private final List<CacheSet<K, V>> cacheSets;
	private final int setCount;

	/**
	 * Creates an N-way, Set associative cache
	 *
	 * @param setNumber
	 *            number of sets
	 * @param setSize
	 *            maximum size of each set
	 * @param cacheAlgorithm
	 *            the replacement algorithm to use
	 */
	@SuppressWarnings("unchecked")
	public SetAssociativeCache(int setNumber, int setSize, CacheReplacementAlgorithm<K> cacheAlgorithm) {
		cacheSets = new ArrayList<>();
		this.setCount = setNumber;
		CacheReplacementAlgorithm<K> newAlgorithmInstance = null;
		// initialize cache sets
		for (int i = 0; i < setCount; i++) {
			try {
				newAlgorithmInstance = cacheAlgorithm.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error("Invalid caching replacement algorithm" + e);
			}
			cacheSets.add(new CacheSet<>(setSize, newAlgorithmInstance));
			logger.info("Initialized new CacheSet to place at position: " + i);
		}
		logger.info("Initialized cache with algorithm: " + cacheAlgorithm.getClass().getSimpleName() + " with "
				+ this.setCount + " sets each with a capacity of " + setSize);
	}

	/**
	 * Puts an element into the cache
	 *
	 * @param key
	 *            the key for retrieval and storage
	 * @param value
	 *            the value at the specified key
	 */
	public void put(K key, V value) {
		int setIndex = getSetIndex(key);
		cacheSets.get(setIndex).add(key, value);
	}

	/**
	 * Indicates the CacheSet where the data will be stored
	 *
	 * @param key The key of the data to be stored in the cache
	 * @return The index of the CacheSet where this element will be/has been
	 *         stored
	 */
	int getSetIndex(K key) {
		return Math.abs(key.hashCode()) % setCount;
	}

	/**
	 * Gets the cache value using the specified key
	 *
	 * @param key
	 *            the key used to retrieve the element
	 * @return the value
	 */
	public V get(K key) {
		int setIndex = getSetIndex(key);
		return cacheSets.get(setIndex).get(key);
	}

}
