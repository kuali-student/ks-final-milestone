/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.internal.common.spring.interceptors.cache;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springmodules.cache.key.HashCodeCacheKeyGenerator;

public abstract class AbstractCacheInterceptor implements MethodInterceptor {
    /** SLF4J logging framework */
    private final static Logger logger = LoggerFactory.getLogger(MemoryCacheInterceptor.class);

    /**
     * 
     * This method returns cached data. 
     * 
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            Serializable key = generateHashCodeMethodKey(methodInvocation);
            logger.info("Returning cached data for key: " + key);

            Object result = getCacheObject(key);

            if (result == null) {
                logger.info("Invoking method to get data: "
                        + methodInvocation.getMethod().getDeclaringClass()
                                .getName() + "::"
                        + methodInvocation.getMethod().getName());
                result = methodInvocation.proceed();
                addToCache(key, result);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Gets a cached object by key from the cache.
     * If no object exists then return null.
     * 
     * @param key A cache key to look up cache object for
     * @return A cache object if it exists otherwise null
     */
    protected abstract Object getCacheObject(Object key);

    /**
     * Adds an object to the cache by key.
     * 
     * @param key Cached object key
     * @param obj Cached object
     */
    protected abstract void addToCache(Object key, Object obj);

    /**
     * Removes an object from the cache by key.
     * 
     * @param key Key to remove
     */
    protected abstract void removeFromCache(Object key);

    /**
     * Creates a hash code cache key for a method.
     * 
     * @param methodInvocation Method to create a key for
     * @return Serializable cache key
     */
    private Serializable generateHashCodeMethodKey(MethodInvocation methodInvocation) {
        HashCodeCacheKeyGenerator keyGenerator = new HashCodeCacheKeyGenerator();
        keyGenerator.setGenerateArgumentHashCode(true);
        return keyGenerator.generateKey(methodInvocation);
    }

}
