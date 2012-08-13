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

package org.kuali.student.r1.core.statement.naturallanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public class ContextRegistryTest {

	private ContextRegistry<DeveloperContext> emptyRegistry;
	private ContextRegistry<DeveloperContext> fullRegistry;
	
	private DeveloperContext context1 = new DeveloperContext();
	private DeveloperContext context2 = new DeveloperContext();
	private DeveloperContext context3 = new DeveloperContext();

	private static class DeveloperContext implements Context<String> {

	    public Map<String, Object> createContextMap(String id, ContextInfo contextInfo) throws OperationFailedException {
	        Map<String, Object> contextMap = new HashMap<String, Object>();
	        contextMap.put(id, id);
	        
	        return contextMap;
	    }
	}
	
    @Before
    public void setUp() throws Exception {
    	emptyRegistry = new ContextRegistry<DeveloperContext>();
		
		Map<String, List<DeveloperContext>> map = new HashMap<String, List<DeveloperContext>>();
		List<DeveloperContext> list1 = new ArrayList<DeveloperContext>();
		list1.add(context1);
		list1.add(context2);
		map.put("c1", list1);
		List<DeveloperContext> list2 = new ArrayList<DeveloperContext>();
		list2.add(context3);
		map.put("c2", list2);
		
    	fullRegistry = new ContextRegistry<DeveloperContext>(map);
    }

    @Test
	public void testAdd() throws Exception {
    	List<DeveloperContext> list = new ArrayList<DeveloperContext>();
    	DeveloperContext context = new DeveloperContext();
    	list.add(context);
		emptyRegistry.add("123", context);
		
		Assert.assertNotNull(emptyRegistry.get("123"));
		Assert.assertEquals(1, emptyRegistry.get("123").size());
		Assert.assertEquals(context, emptyRegistry.get("123").get(0));
	}

	@Test
	public void testGet() throws Exception {
    	List<DeveloperContext> list = new ArrayList<DeveloperContext>();
		DeveloperContext context = new DeveloperContext();
    	list.add(context);
		emptyRegistry.add("123", context);
		
		Assert.assertNotNull(emptyRegistry.get("123"));
		Assert.assertEquals(1, emptyRegistry.get("123").size());
		Assert.assertEquals(context, emptyRegistry.get("123").get(0));
	}

	@Test
	public void testGet_MultipleContext() throws Exception {
		Assert.assertEquals(2, fullRegistry.size());
		Assert.assertNotNull(fullRegistry.get("c1"));
		Assert.assertEquals(2, fullRegistry.get("c1").size());
		Assert.assertEquals(context1, fullRegistry.get("c1").get(0));
		Assert.assertEquals(context2, fullRegistry.get("c1").get(1));
		Assert.assertNotNull(fullRegistry.get("c2"));
		Assert.assertEquals(1, fullRegistry.get("c2").size());
		Assert.assertEquals(context3, fullRegistry.get("c2").get(0));
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
		
		Assert.assertNotNull(emptyRegistry.get("123"));
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
