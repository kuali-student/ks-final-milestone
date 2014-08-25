/*
 * Copyright 2014 Kuali Foundation Licensed under the
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
package org.kuali.student.common.test.proxy;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.kuali.student.common.mock.MockService;


/**
 * @author Kuali Student Team (ks.collab@kuali.org)
 * 
 * Adds a test case that confirms an inefficient use of a caching decorator for bulk loading service calls.
 *
 */
@RunWith(value=BlockJUnit4ClassRunner.class)
public class TestCountingMethodCallProxy {

	private class DTO {
		
		private String id;
		
		private String value;

		public DTO(String id, String value) {
			super();
			this.id = id;
			this.value = value;
		}

		public String getId() {
			return id;
		}

		public String getValue() {
			return value;
		}
		
		
	}
	private interface SimpleService extends MockService {
		public DTO getOne(String id);
		
		public List<DTO>getMany(List<String>ids);
	}
	
	private class SimpleServiceDecorator implements SimpleService {

		protected SimpleService nextDecorator;
		
		
		public SimpleServiceDecorator(SimpleService nextDecorator) {
			super();
			this.nextDecorator = nextDecorator;
		}

		@Override
		public DTO getOne(String id) {
			return nextDecorator.getOne(id);
		}

		@Override
		public List<DTO> getMany(List<String> ids) {
			return nextDecorator.getMany(ids);
		}

		@Override
		public void clear() {
			nextDecorator.clear();
		}
		
		
	}
	private class SimpleServiceCacheDecorator extends SimpleServiceDecorator {

		private Map<String, DTO>dtoCache = new HashMap<String, TestCountingMethodCallProxy.DTO>();
		
		private boolean bulkAwareMode = false;
		
		public SimpleServiceCacheDecorator(SimpleService nextDecorator) {
			super(nextDecorator);
		}

		@Override
		public DTO getOne(String id) {
			
			DTO result = dtoCache.get(id);
			
			if (result != null)
				return result;
			else {
				
				result = nextDecorator.getOne(id);
				
				if (result != null)
					dtoCache.put(id, result);
				
				return result;	
			}
			
		}

		private void simpleGetMany(List<String>ids, List<DTO>results) {
			for (String id : ids) {
				DTO result = dtoCache.get(id);
				
				if (result == null)
					result = getOne(id);
				
				results.add(result);
			}
		}
		@Override
		public List<DTO> getMany(List<String> ids) {
			
			List<DTO>results = new ArrayList<TestCountingMethodCallProxy.DTO>();
			
			if (bulkAwareMode)
				bulkAwareGetMany(ids, results);
			else
				simpleGetMany(ids, results);
			
			return results;
		}

		private void bulkAwareGetMany(List<String> ids, List<DTO> results) {
			
			List<String>cacheMissIds = new ArrayList<String>();
			
			for (String id : ids) {
				
				DTO result = this.dtoCache.get(id);
				
				if (result == null) {
					// cache-miss
					cacheMissIds.add(id);
				}
				else {
					results.add(result);
				}
			}
			
			List<DTO>cacheMisses = nextDecorator.getMany(cacheMissIds);
			
			for (DTO dto : cacheMisses) {
				this.dtoCache.put(dto.getId(), dto);
			}
			
			results.addAll(cacheMisses);
		}

		public void setBulkAwareMode(boolean bulkAwareMode) {
			this.bulkAwareMode = bulkAwareMode;
		}

		@Override
		public void clear() {
			super.clear();
			this.dtoCache.clear();
		}
		
		
		
		
	}
	
	private class SimpleServiceMapImpl implements SimpleService {

		private Map<String, DTO> dataMap = new HashMap<String, TestCountingMethodCallProxy.DTO>();
		
		@Override
		public DTO getOne(String id) {
			return dataMap.get(id);
		}

		@Override
		public List<DTO> getMany(List<String> ids) {
			List<DTO>many = new ArrayList<TestCountingMethodCallProxy.DTO>();

			for (String id : ids) {
				DTO result = getOne(id);
				
				if (result != null)
					many.add(result);
			}
			
			return many;
		}
		
		public void storeDTO (DTO object) {
			this.dataMap.put(object.getId(), object);
		}

		@Override
		public void clear() {
			this.dataMap.clear();
		}
		
		
	}
	@Test
	public void testSimpleProxy() {
		
		SimpleServiceMapImpl base = new SimpleServiceMapImpl();
		
		MethodCountingInvocationHandler handler = new MethodCountingInvocationHandler(base);
		
		SimpleService instance = (SimpleService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {SimpleService.class}, handler);
		
		SimpleServiceCacheDecorator top = new SimpleServiceCacheDecorator(instance);
		
		base.storeDTO(new DTO("1", "one"));
		base.storeDTO(new DTO("2", "two"));
		base.storeDTO(new DTO("3", "three"));
		
		top.setBulkAwareMode(true);
		
		List<DTO> many = top.getMany(Arrays.asList(new String[] {"1","2", "3"}));
		
		Assert.assertEquals(3, many.size());
		
		int oneCount = handler.getMethodCount("getOne");
		
		Assert.assertEquals(0, oneCount);
		
		top.clear();
		
		base.storeDTO(new DTO("1", "one"));
		base.storeDTO(new DTO("2", "two"));
		base.storeDTO(new DTO("3", "three"));
		
		top.getOne("2");
		
		many = top.getMany(Arrays.asList(new String[] {"1","2", "3"}));
		
		Assert.assertEquals(3, many.size());
		
		oneCount = handler.getMethodCount("getOne");
		
		Assert.assertEquals(1, oneCount);
		
	}
	
}
