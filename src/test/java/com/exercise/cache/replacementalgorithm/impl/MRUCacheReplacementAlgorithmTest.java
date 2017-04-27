package com.exercise.cache.replacementalgorithm.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.exercise.cache.replacementalgorithm.AccessType;
import com.exercise.cache.replacementalgorithm.impl.MRUCacheReplacementAlgorithm;

public class MRUCacheReplacementAlgorithmTest {
	
	@Test
	public void testWriteOnly() throws Exception {
		MRUCacheReplacementAlgorithm<String> mru = new MRUCacheReplacementAlgorithm<>();
        mru.notifyCacheAccess("1", AccessType.WRITE);
        mru.notifyCacheAccess("2", AccessType.WRITE);
        mru.notifyCacheAccess("1", AccessType.WRITE);
        mru.notifyCacheAccess("2", AccessType.WRITE);
        assertEquals(mru.entryKeyToRemove(), "2");
	}	
	
	@Test
	public void testReadWrite() throws Exception {
		MRUCacheReplacementAlgorithm<String> mru = new MRUCacheReplacementAlgorithm<>();
        mru.notifyCacheAccess("1", AccessType.WRITE);
        mru.notifyCacheAccess("2", AccessType.WRITE);
        mru.notifyCacheAccess("1", AccessType.READ);
        mru.notifyCacheAccess("2", AccessType.READ);
        assertEquals(mru.entryKeyToRemove(), "2");
	}		

}
