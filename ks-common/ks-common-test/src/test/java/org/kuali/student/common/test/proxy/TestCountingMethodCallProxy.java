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

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.kuali.student.common.cache.KSCacheUtils;
import org.kuali.student.common.cache.KSCacheUtils.BulkCacheElementLoader;
import org.kuali.student.common.cache.KSCacheUtils.CacheElementCopier;
import org.kuali.student.common.cache.KSCacheUtils.SingleCacheElementLoader;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.HasId;


/**
 * @author Kuali Student Team (ks.collab@kuali.org)
 * 
 * Adds a test case that confirms an inefficient use of a caching decorator for bulk loading service calls.
 *
 */
@RunWith(value=BlockJUnit4ClassRunner.class)
public class TestCountingMethodCallProxy {

	private class DTO implements HasId, Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4788688618267922206L;

		private String id;
		
		private String value;

		public DTO (DTO source) {
			this (source.getId(), source.getValue());
		}
		
		public DTO(String id, String value) {
			super();
			this.id = id;
			this.value = value;
		}

		@Override
		public String getId() {
			return id;
		}

		public String getValue() {
			return value;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
	}
	private interface SimpleService extends MockService {
		public DTO getOne(String id) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException;
		
		public List<DTO>getMany(List<String>ids) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException;
	}
	
	private CacheElementCopier<DTO>copier = new CacheElementCopier<TestCountingMethodCallProxy.DTO>() {

		@Override
		public DTO deepCopy(DTO source) {
			return new DTO(source);
		}
		
	}; 
	
	private class SimpleServiceDecorator implements SimpleService {

		protected SimpleService nextDecorator;
		
		
		public SimpleServiceDecorator(SimpleService nextDecorator) {
			super();
			this.nextDecorator = nextDecorator;
		}

		@Override
		public DTO getOne(String id) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
			return nextDecorator.getOne(id);
		}

		@Override
		public List<DTO> getMany(List<String> ids) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
			return nextDecorator.getMany(ids);
		}

		@Override
		public void clear() {
			nextDecorator.clear();
		}
		
		
	}
	private class SimpleServiceCacheDecorator extends SimpleServiceDecorator {

		private Cache dtoCache = new Cache(new CacheConfiguration("dtoCache", 0));
		
		private boolean bulkAwareMode = false;
		
		public SimpleServiceCacheDecorator(SimpleService nextDecorator) {
			super(nextDecorator);
			
			dtoCache.initialise();
		}

		@Override
		public DTO getOne(String id) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
			
			return KSCacheUtils.cacheAwareLoad(dtoCache, id, copier, new SingleCacheElementLoader<DTO>() {

				@Override
				public DTO load(String key) throws DoesNotExistException,
						OperationFailedException, InvalidParameterException,
						MissingParameterException, PermissionDeniedException {
					return nextDecorator.getOne(key);
				}
				
			});
//			DTO result = dtoCache.get(id);
//			
//			if (result != null)
//				return result;
//			else {
//				
//				result = 
//				
//				if (result != null)
//					dtoCache.put(id, result);
//				
//				return result;	
//			}
			
		}

		private void simpleGetMany(List<String>ids, List<DTO>results) throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
			for (String id : ids) {
				
				Element cachedResult = dtoCache.get(id);
				
				if (cachedResult == null)
					results.add (getOne(id));
				else 
					results.add((DTO) cachedResult.getValue());
			}
		}
		@Override
		public List<DTO> getMany(List<String> ids) throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
			
			List<DTO>results = new ArrayList<TestCountingMethodCallProxy.DTO>();
			
			if (bulkAwareMode)
				bulkAwareGetMany(ids, results);
			else
				simpleGetMany(ids, results);
			
			return results;
		}

		private void bulkAwareGetMany(List<String> ids, List<DTO> results) throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
			
			results.addAll(KSCacheUtils.cacheAwareBulkLoad(dtoCache, ids, copier, new BulkCacheElementLoader<DTO>() {

				@Override
				public List<DTO> load(List<String> cacheMissKeys)
						throws DoesNotExistException, OperationFailedException,
						InvalidParameterException, MissingParameterException,
						PermissionDeniedException {
					return nextDecorator.getMany(cacheMissKeys);
				}
				
				
			}));
		}

		public void setBulkAwareMode(boolean bulkAwareMode) {
			this.bulkAwareMode = bulkAwareMode;
		}

		@Override
		public void clear() {
			super.clear();
			this.dtoCache.removeAll();
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
	public void testSimpleProxy() throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		
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
	
	@Test
	public void testCacheImmutability() throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		
		/*
		 * 
		 */
	
		SimpleServiceMapImpl base = new SimpleServiceMapImpl();
		
		MethodCountingInvocationHandler handler = new MethodCountingInvocationHandler(base);
		
		SimpleService instance = (SimpleService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {SimpleService.class}, handler);
		
		SimpleServiceCacheDecorator top = new SimpleServiceCacheDecorator(instance);
		
		base.storeDTO(new DTO("1", "one"));
		base.storeDTO(new DTO("2", "two"));
		base.storeDTO(new DTO("3", "three"));
		
		top.setBulkAwareMode(true);
		
		DTO cachedTwo = top.getOne("2");
		
		cachedTwo.setValue("Four");
		
		DTO secondTwo = top.getOne("2");
		
		Assert.assertNotEquals("Expected object ids to be different", cachedTwo, secondTwo);
		
	}
	
}
