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

package org.kuali.student.core.statement.naturallanguage;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.exceptions.OperationFailedException;

public class ContextRegistryTest {

	private ContextRegistry<DeveloperContext> emptyRegistry;
	private ContextRegistry<DeveloperContext> fullRegistry;
	
	private DeveloperContext context1 = new DeveloperContext();
	private DeveloperContext context2 = new DeveloperContext();

	private static class DeveloperContext implements Context<String> {

	    public Map<String, Object> createContextMap(String id) throws OperationFailedException {
	        Map<String, Object> contextMap = new HashMap<String, Object>();
	        contextMap.put(id, id);
	        
	        return contextMap;
	    }
	}
	
    @Before
    public void setUp() throws Exception {
    	emptyRegistry = new ContextRegistry<DeveloperContext>();
		
		Map<String, DeveloperContext> map = new HashMap<String, DeveloperContext>();
		map.put("c1", context1);
		map.put("c2", context2);
		
    	fullRegistry = new ContextRegistry<DeveloperContext>(map);
    }

    @Test
	public void testAdd() throws Exception {
		DeveloperContext context = new DeveloperContext();
		emptyRegistry.add("123", context);
		
		Assert.assertEquals(context, emptyRegistry.get("123"));
	}

	@Test
	public void testGet() throws Exception {
		DeveloperContext context = new DeveloperContext();
		emptyRegistry.add("123", context);
		
		Assert.assertEquals(context, emptyRegistry.get("123"));
	}

	@Test
	public void testGet_MultipleContext() throws Exception {
		Assert.assertEquals(context1, fullRegistry.get("c1"));
		Assert.assertEquals(context2, fullRegistry.get("c2"));
	}

	@Test
	public void testContainsKey() throws Exception {
		DeveloperContext context = new DeveloperContext();
		emptyRegistry.add("123", context);
		
		Assert.assertTrue(emptyRegistry.containsKey("123"));
	}

	@Test
	public void testContainsKey_MultipleContext() throws Exception {
		Assert.assertTrue(fullRegistry.containsKey("c1"));
		Assert.assertTrue(fullRegistry.containsKey("c2"));
	}

	@Test
	public void testRemove() throws Exception {
		DeveloperContext context = new DeveloperContext();
		emptyRegistry.add("123", context);
		
		Assert.assertEquals(context, emptyRegistry.get("123"));
		emptyRegistry.remove("123");
		Assert.assertFalse(emptyRegistry.containsKey("123"));
		Assert.assertNull(emptyRegistry.get("123"));
	}

	@Test
	public void testRemove_MultipleContext() throws Exception {
		fullRegistry.remove("c1");
		Assert.assertFalse(fullRegistry.containsKey("c1"));
		Assert.assertNull(fullRegistry.get("c1"));
	}
}
