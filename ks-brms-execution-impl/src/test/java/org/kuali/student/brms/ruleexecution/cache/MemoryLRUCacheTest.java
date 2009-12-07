package org.kuali.student.brms.ruleexecution.cache;

import java.util.Collection;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class MemoryLRUCacheTest {

	private Cache<String,String> cache;

	@Before
	public void setup() {
		cache = new MemoryLRUCache<String,String>(3);
		cache.put("key-1", "value-1");
		cache.put("key-2", "value-2");
		cache.put("key-3", "value-3");
	}
	
	@Test
	public void testCache_Put_NewValue() throws Exception {
		String value = cache.put("key", "value");
		Assert.assertNull(value);
		Assert.assertEquals("value", cache.get("key"));
	}

	@Test
	public void testCache_Put_ReturnOldValue() throws Exception {
		String value = cache.put("key-1", "value-999");
		Assert.assertNotNull(value);
		Assert.assertEquals(value, "value-1");
	}

	@Test
	public void testCache_Get() throws Exception {
		Assert.assertEquals("value-1", cache.get("key-1"));
	}

	@Test
	public void testCache_Get_InvalidKey() throws Exception {
		Assert.assertNull(cache.get("key-xxx"));
	}

	@Test
	public void testCache_Remove() throws Exception {
		String value = cache.remove("key-2");
		Assert.assertEquals("value-2", value);
		Assert.assertNull(cache.get("key-2"));
	}

	@Test
	public void testCache_Remove_InvalidKey() throws Exception {
		String value = cache.remove("key-xxx");
		Assert.assertNull(value);
	}

	@Test
	public void testCache_Remove_EldestCacheEntry() throws Exception {
		cache.put("key-4", "value-4");
		Assert.assertNull(cache.get("key-1"));
	}
	
	@Test
	public void testCache_Clear() throws Exception {
		cache.clear();
		Assert.assertTrue(cache.isEmpty());
	}

	@Test
	public void testIsEmpty() throws Exception {
		Assert.assertFalse(cache.isEmpty());
	}

	@Test
	public void testCache_Size() throws Exception {
		Assert.assertEquals(3, cache.size());
	}

	@Test
	public void testCache_ContainsKey() throws Exception {
		Assert.assertTrue(cache.containsKey("key-2"));
	}

	@Test
	public void testCache_ContainsInvalidKey() throws Exception {
		Assert.assertFalse(cache.containsKey("key-xxx"));
	}

	@Test
	public void testCache_KeySet() throws Exception {
		Set<String> keySet = cache.keySet();
		
		Assert.assertEquals(3, keySet.size());
		Assert.assertTrue(keySet.contains("key-1"));
		Assert.assertTrue(keySet.contains("key-2"));
		Assert.assertTrue(keySet.contains("key-3"));
	}
}
