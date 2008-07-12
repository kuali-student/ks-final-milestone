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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EHCacheInterceptor 
    extends AbstractCacheInterceptor implements InitializingBean {
    private Cache cache;

    /**
     * sets cache name to be used
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    /**
     * Checks if required attributes are provided.
     */
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache, "A cache is required. Use setCache(Cache).");
    }

    public Object getCacheObject(Object key) {
        Element element = this.cache.get(key);
        return (element == null ? null : element.getValue());
    }

    public void addToCache(Object key, Object obj) {
        Element element = new Element(key, obj);
        this.cache.put(element);
    }

    protected void removeFromCache(Object key) {
        this.cache.remove(key);
    }

}
