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

package org.kuali.student.common.test.spring;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class IdToObjectEhcacheAdvice implements Advice {
	final Logger LOG = Logger.getLogger(IdToObjectEhcacheAdvice.class);
	
	private CacheManager cacheManager;
	private String cacheName;

	/**
	 * 
	 */
	public IdToObjectEhcacheAdvice() {
		super();
	}

	/**
	 * @param cacheName
	 */
	public IdToObjectEhcacheAdvice(String cacheName) {
		super();
		this.cacheName = cacheName;
	}

	public Object invalidateCache(ProceedingJoinPoint pjp) throws Throwable {
		if (cacheManager == null) {
			cacheManager = CacheManager.getInstance();
			try {
				cacheManager.addCache(cacheName);
			} catch (ObjectExistsException e) {

			}
		}
		LOG.info("Invalidating Cache");
		cacheManager.getCache(cacheName).remove(pjp.getArgs()[0]);
		return pjp.proceed();
	}

	@SuppressWarnings("unchecked")
	public Object getFromCache(ProceedingJoinPoint pjp) throws Throwable {
		if (cacheManager == null) {
			cacheManager = CacheManager.getInstance();
			try {
				cacheManager.addCache(cacheName);
			} catch (ObjectExistsException e) {

			}
		}
		// Have two caches for the one object? one by id, the other by method
		// call?
		if (pjp.getArgs().length == 1 && pjp.getArgs()[0] instanceof List) {
			List<Object> results = new ArrayList<Object>();
			List<String> uncachedIdList = new ArrayList<String>();
			for (String id : (List<String>) pjp.getArgs()[0]) {
				// Look in the cache
				LOG.info("Looking in Cache");
				Element cachedResult = cacheManager.getCache(cacheName).get(id);
				if (cachedResult == null) {
					uncachedIdList.add(id);
				} else {
					results.add(cachedResult.getValue());
				}
			}
			if (uncachedIdList.size() > 0) {
				List<Idable> uncachedResults = (List<Idable>) pjp.proceed();
				if (uncachedResults != null) {
					for (Idable uncachedResult : uncachedResults) {
						// Add to the cache and add to results
						LOG.info("Storing to Cache");
						results.add(uncachedResult);
						cacheManager.getCache(cacheName).put(
								new Element(uncachedResult.getId(),
										uncachedResult));
					}
				}
			}
			return results;
		}
		if (pjp.getArgs().length == 1 && pjp.getArgs()[0] instanceof String) {
			String id = (String) pjp.getArgs()[0];
			LOG.info("Looking in Cache");
			Element resultElement = cacheManager.getCache(cacheName).get(id);
			Object result;
			if (resultElement == null) {
				result = pjp.proceed();
				LOG.info("Storing to Cache");
				cacheManager.getCache(cacheName).put(new Element(id, result));
			} else {
				result = resultElement.getValue();
			}
			return result;
		}

		return pjp.proceed();
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName
	 *            the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

}
