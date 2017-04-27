package com.exercise.cache;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.exercise.cache.SetAssociativeCache;
import com.exercise.cache.replacementalgorithm.impl.LRUCacheReplacementAlgorithm;

public class SetAssociativeCacheTest {
	
    private SetAssociativeCache<TestInteger, String> cache;
    private static final int N = 2;
    private static final int SET_NUMBER = 2;

	
	@Before
	public void setUp() throws Exception {
		cache = new SetAssociativeCache<TestInteger, String>(SET_NUMBER, N, new LRUCacheReplacementAlgorithm<>());
	}

    @Test
    public void testGetSetIndex() throws Exception {
        int aSetIndex = cache.getSetIndex(new TestInteger(5));
        assertThat(aSetIndex, lessThanOrEqualTo(SET_NUMBER));
    }
    
 //   @Test
    public void testPut() throws Exception {
    	add4Values();
        
        assertNull(cache.get(new TestInteger(5)));

               
        cache.put(new TestInteger(5), "Value5");

        // TestInteger(1) is replaced
        assertNull(cache.get(new TestInteger(1))); 
        assertEquals(cache.get(new TestInteger(5)), "Value5");
        
        cache.put(new TestInteger(6), "Value6");
        
        // TestInteger(2) is replaced
        assertNull(cache.get(new TestInteger(2))); 
        assertEquals(cache.get(new TestInteger(6)), "Value6");
    }
    
    @Test
    public void testGet() throws Exception {
    	add4Values();
        assertNull(cache.get(new TestInteger(5)));
        
        // 1 and 2 are now recently used
        cache.get(new TestInteger(1));   
        
        cache.get(new TestInteger(2));
        
        cache.put(new TestInteger(5), "Value5");

        // TestInteger(3) is replaced
        assertNull(cache.get(new TestInteger(3))); 
        assertEquals(cache.get(new TestInteger(5)), "Value5");
        
        cache.put(new TestInteger(6), "Value6");
       
        
        // TestInteger(4) is replaced
        assertNull(cache.get(new TestInteger(4))); 
        assertEquals(cache.get(new TestInteger(6)), "Value6");
    }  
    
    private void add4Values() throws Exception {
        cache.put(new TestInteger(1), "Value1");
        cache.put(new TestInteger(2), "Value2");
        cache.put(new TestInteger(3), "Value3");
        cache.put(new TestInteger(4), "Value4");   	
    }
        
    class TestInteger {
        	private int value;

			public TestInteger(int value) {
				this.value = value;
			}

			@Override
			public int hashCode() {
				return value;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				TestInteger other = (TestInteger) obj;
				if (value != other.value)
					return false;
				return true;
			}
			
			@Override
			public String toString() {
				return String.valueOf(value);
			}
      }
        

}

