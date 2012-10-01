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

package org.kuali.student.common.util.spring;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class MethodArgsToObjectEhcacheAdvice implements Advice {
	final Logger LOG = Logger.getLogger(getClass());

	private CacheManager cacheManager;
	private String cacheName;
	private boolean enabled;
	
	
	

	/**
	 * 
	 */
	public MethodArgsToObjectEhcacheAdvice() {
		super();
	}

	/**
	 * @param cacheName
	 */
	public MethodArgsToObjectEhcacheAdvice(String cacheName) {
		super();
		this.cacheName = cacheName;
	}

	public Object invalidateCache(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		if(enabled){
			if (cacheManager == null) {
				cacheManager = CacheManager.getInstance();
				try {
					cacheManager.addCache(cacheName);
				} catch (ObjectExistsException e) {
	
				}
			}
			LOG.info("Invalidating Cache: " + cacheName);
			cacheManager.getCache(cacheName).removeAll();
		}
		return result;
	}

	public Object getFromCache(ProceedingJoinPoint pjp) throws Throwable {
		if(!enabled){
			return pjp.proceed();
		}
		
		if (cacheManager == null) {
			cacheManager = CacheManager.getInstance();
			try {
				cacheManager.addCache(cacheName);
			} catch (ObjectExistsException e) {

			}
		}
		String cacheKey = generateCacheKey(pjp);

		Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
		Object result = null;
		if (cachedResult == null) {
			result = pjp.proceed();
			LOG.info("Storing to Cache: " + cacheName);
			cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
		} else {
			LOG.info("Found in Cache: " + cacheName);
			result = cachedResult.getValue();
		}

		return result;
	}

	/**
	 * Generate cache key based on the ProceedingJonPoint. Other advices can extend and override this method to implement their own strategy for key generation
	 */
	protected String generateCacheKey(ProceedingJoinPoint pjp) {
		final StringBuffer cacheKey = new StringBuffer(pjp.getSignature().getName());
		cacheKey.append("(");
		for (int i = 0; i < pjp.getArgs().length; i++) {
			
			if(null == pjp.getArgs()[i]) {
				// FIXME: This will result in inconsistent behvior if the value is the literal '<null>' vs being null
				cacheKey.append("<null>");
			} else {
				cacheKey.append(pjp.getArgs()[i].toString());
			}
			
			if (i + 1 != pjp.getArgs().length) {
				cacheKey.append(",");
			}
		}
		
		cacheKey.append(")");
		return cacheKey.toString();
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

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
