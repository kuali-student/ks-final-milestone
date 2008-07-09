package org.kuali.student.rules.internal.common.spring.interceptors.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class MemoryCacheInterceptor extends AbstractCacheInterceptor
    implements InitializingBean
{
    /** SLF4J logging framework */
    private final static Logger logger = LoggerFactory.getLogger(MemoryCacheInterceptor.class);
    /** Default max cache size */
    private final static int DEFAULT_MAX_SIZE = 100;
    /** Max cache size */
    private int maxSize = DEFAULT_MAX_SIZE;
    /** Cache map */
    private static Map<Object,Object> cache = null;
   
    /**
     * Constructor.
     */
    public MemoryCacheInterceptor()
    {
    }

    /**
     * @return Returns the cache's maxSize
     */
    public int getMaxSize() 
    {
        return this.maxSize;
    }
    /**
     * @param maxSize Sets the cache's maxSize
     */
    public void setMaxSize( int maxSize ) 
    {
        this.maxSize = maxSize;
    }
    
    /**
     * Initializes the idMap variable which provides a 
     * <code>java.lang.Class</code> to method mapping.
     * 
     * @throws ClassNotFoundException when a class in <code>identifiers</code> could not be found
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception
    {
        if ( this.maxSize < -1 || this.maxSize == 0 ) 
        {
            throw new IllegalArgumentException( "Illegal Cache maxSize" );
        }
        
        if ( maxSize == -1) 
        {
            cache = new HashMap<Object,Object>();
        } 
        else 
        {
            cache = new LinkedHashMap<Object,Object>() 
            {                
                /**
                 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
                 */
                protected boolean removeEldestEntry( Map.Entry eldest ) 
                {
                    return size() > maxSize;
                }
            };
        }
    }

    /**
     * Gets an object from the cache.
     * 
     * @see org.kuali.student.rules.internal.common.spring.interceptors.cache.AbstractCacheInterceptor#getCacheObject(java.lang.Object)
     */
    public Object getCacheObject( Object key )
    {
        return cache.get( key );
    }

    /**
     * Adds an object to the cache.
     * This overridden method ...
     * 
     * @see org.kuali.student.rules.internal.common.spring.interceptors.cache.AbstractCacheInterceptor#addToCache(java.lang.Object, java.lang.Object)
     */
    public void addToCache( Object key, Object obj )
    {
        cache.put( key, obj );
    }

}