package com.exercise.cache;

import java.util.stream.IntStream;

import com.exercise.cache.replacementalgorithm.CacheReplacementAlgorithm;
import com.exercise.cache.replacementalgorithm.impl.LRUCacheReplacementAlgorithm;
import com.exercise.cache.replacementalgorithm.impl.MRUCacheReplacementAlgorithm;

/**
 * Client application to test the SetAssociativeCache.
 *
 */
public class CacheClient {
	public static void main(String[] args) {
		int setNumber = Integer.parseInt(args[0]);
		if (setNumber <= 0) {
			throw new IllegalArgumentException("Invalid number of sets.");
		}
		int setSize = Integer.parseInt(args[1]);
		if (setSize <= 0) {
			throw new IllegalArgumentException("Invalid size of a set.");
		}
		String replacementAlgoStr = args[2];
		CacheReplacementAlgorithm<Integer> replacementAlgorithm = null;
		if ("LRU".equalsIgnoreCase(replacementAlgoStr)) {
			replacementAlgorithm = new LRUCacheReplacementAlgorithm<>();
		} else if ("MRU".equalsIgnoreCase(replacementAlgoStr)) {
			replacementAlgorithm = new MRUCacheReplacementAlgorithm<>();
		} else {
			throw new IllegalArgumentException("Invalid replacement algorithm value - use LRU or MRU.");
		}

		SetAssociativeCache<Integer, String> cache = new SetAssociativeCache<>(setNumber, setSize, replacementAlgorithm);
		overflowCache(cache, setNumber, setSize, replacementAlgoStr);
	}

	private static void overflowCache(SetAssociativeCache<Integer, String> cache, int setNumber, int setSize,
			String replacementAlgoStr) {
		System.out.println(setSize + "-way set associative cache with " + setNumber + " sets and " + replacementAlgoStr
				+ " replacement algorithm.");
		IntStream.rangeClosed(1, setNumber * setSize + 5).forEach(i -> {
			cache.put(new Integer(i), "Value" + i);
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				System.out.println("Interrupted while waiting");
			}
			System.out.println("Add to cache entry with  key " + i + " and value Value" + i);
		});
		System.out.println("Content of the cache");
		IntStream.rangeClosed(1, setNumber * setSize + 5).forEach(i -> {
			System.out.println("Getting cache value for key " + i + " : " + cache.get(new Integer(i)));
		});
	}
}
