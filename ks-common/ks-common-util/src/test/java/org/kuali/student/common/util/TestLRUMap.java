/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
