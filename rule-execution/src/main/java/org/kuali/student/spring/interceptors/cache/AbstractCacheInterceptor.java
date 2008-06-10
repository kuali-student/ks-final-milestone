package org.kuali.student.spring.interceptors.cache;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springmodules.cache.key.HashCodeCacheKeyGenerator;


public abstract class AbstractCacheInterceptor 
    implements MethodInterceptor
{
    /** SLF4J logging framework */
    private final static Logger logger = LoggerFactory.getLogger(MemoryCacheInterceptor.class);
    

    public Object invoke( MethodInvocation methodInvocation )
        throws Throwable
    {
        try
        {
            Serializable key = generateHashCodeMethodKey( methodInvocation );           
            logger.info( "Returning cached data for key: " + key );

            Object result = getCacheObject( key );
            
            if ( result == null ) 
            {
                logger.info( "Invoking method to get data: " + methodInvocation.getMethod().getDeclaringClass().getName() 
                        + "::" + methodInvocation.getMethod().getName());
                result = methodInvocation.proceed();
                addToCache( key, result );
            } 
            
            return result;
        }
        catch( Exception e )
        {
            throw new RuntimeException( e.getMessage(), e );
        }
    }

     /**
     * Gets a cached object by key from the cache.
     * If no object exists then return null.
     * 
     * @param key A cache key to look up cache object for
     * @return A cache object if it exists otherwise null
     */
    protected abstract Object getCacheObject( Object key );

    /**
     * Adds an object to the cache by key.
     * 
     * @param key Cached object key
     * @param obj Cached object
     */
    protected abstract void addToCache( Object key, Object obj );
    
    /**
     * Creates a hash code cache key for a method.
     * 
     * @param methodInvocation Method to create a key for
     * @return Serializable cache key
     */
    private Serializable generateHashCodeMethodKey( MethodInvocation methodInvocation )
    {
        HashCodeCacheKeyGenerator keyGenerator = new HashCodeCacheKeyGenerator();
        keyGenerator.setGenerateArgumentHashCode( true );
        return keyGenerator.generateKey( methodInvocation );
    }
    
}
