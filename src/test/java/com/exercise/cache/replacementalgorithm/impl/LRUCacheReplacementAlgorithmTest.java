package com.exercise.cache.replacementalgorithm.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.exercise.cache.replacementalgorithm.AccessType;
import com.exercise.cache.replacementalgorithm.impl.LRUCacheReplacementAlgorithm;

public class LRUCacheReplacementAlgorithmTest {
	
	@Test
	public void testWriteOnly() throws Exception {
		LRUCacheReplacementAlgorithm<String> mru = new LRUCacheReplacementAlgorithm<>();
        mru.notifyCacheAccess("1", AccessType.WRITE);
        mru.notifyCacheAccess("2", AccessType.WRITE);
        mru.notifyCacheAccess("1", AccessType.WRITE);
        mru.notifyCacheAccess("2", AccessType.WRITE);
        assertEquals(mru.entryKeyToRemove(), "1");
	}	
	
	@Test
	public void testReadWrite() throws Exception {
		LRUCacheReplacementAlgorithm<String> mru = new LRUCacheReplacementAlgorithm<>();
        mru.notifyCacheAccess("1", AccessType.WRITE);
        mru.notifyCacheAccess("2", AccessType.WRITE);
        mru.notifyCacheAccess("1", AccessType.READ);
        mru.notifyCacheAccess("2", AccessType.READ);
        assertEquals(mru.entryKeyToRemove(), "1");
	}		

}
