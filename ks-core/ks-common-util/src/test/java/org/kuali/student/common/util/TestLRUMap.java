package org.kuali.student.common.util;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestLRUMap {

	private LRUMap<String,String> cache;

	@Before
	public void setup() {
		cache = new LRUMap<String,String>(3);
		cache.put("key-1", "value-1");
		cache.put("key-2", "value-2");
		cache.put("key-3", "value-3");
	}
	
	@Test
	public void testDefaultMaximumEntries() throws Exception {
		cache = new LRUMap<String,String>();
		for(int i=0; i<60; i++) {
			cache.put("key-"+i, "value-"+i);
		}
		Assert.assertEquals(50, cache.size());
	}

	@Test
	public void testRemoveEldestEntry() throws Exception {
		cache.put("key-4", "value-4");
		Assert.assertEquals(3, cache.size());
		Assert.assertNull(cache.get("key-1"));
	}
	
}
