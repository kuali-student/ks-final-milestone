package org.kuali.student.rules.internal.common.spring.interceptors.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EHCacheInterceptor extends AbstractCacheInterceptor
    implements InitializingBean
{
    private Cache cache;

    /**
     * sets cache name to be used
     */
    public void setCache( Cache cache )
    {
        this.cache = cache;
    }

    /**
     * Checks if required attributes are provided.
     */
    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull( cache, "A cache is required. Use setCache(Cache)." );
    }

    public Object getCacheObject( Object key )
    {
        Element element = this.cache.get( key );
        return ( element == null ? null : element.getValue() );
    }

    public void addToCache( Object key, Object obj )
    {
        Element element = new Element( key, obj );
        this.cache.put( element );
    }

}
