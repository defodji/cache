package com.exercise.cache.component;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.exercise.cache.component.CacheSet;
import com.exercise.cache.replacementalgorithm.impl.MRUCacheReplacementAlgorithm;

public class CacheSetTest {

	private CacheSet<String, String> cacheSet;
	
	@Before
	public void setUp() throws Exception {
		cacheSet = new CacheSet<>(5, new MRUCacheReplacementAlgorithm<>());
        cacheSet.add("key1", "value1");
        cacheSet.add("key2", "value2");
        cacheSet.add("key3", "value3");
        cacheSet.add("key4", "value4");
        cacheSet.add("key5", "value5");
	}

	@Test
	public void testGet() throws Exception {
		assertEquals(cacheSet.get("key1"), "value1");
		assertEquals(cacheSet.get("key2"), "value2");
		assertEquals(cacheSet.get("key3"), "value3");
		assertEquals(cacheSet.get("key4"), "value4");
		assertEquals(cacheSet.get("key5"), "value5");
	}
	
	@Test
	public void testAddWithReplacement() throws Exception {
		cacheSet.get("key1");
		cacheSet.get("key2");
		cacheSet.add("key6", "value6");
		// key2 should be gone
		assertNull(cacheSet.get("key2"));
		
		cacheSet.add("key7", "value7");

		// key6 should be gone
		assertNull(cacheSet.get("key6"));
	}	

}