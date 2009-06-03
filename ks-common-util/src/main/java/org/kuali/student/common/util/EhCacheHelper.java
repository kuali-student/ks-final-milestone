/**
 * 
 */
package org.kuali.student.common.util;

import java.io.Serializable;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Simplified api for enabling Service and DAO classes with EhCache.  
 * @author garystruthers
 *
 */
public class EhCacheHelper {
	
	private CacheManager cacheManager; // singleton uses default ehcache.xml configuration in ehcache.jar
	/**
	 * 
	 */
	public EhCacheHelper() {
		initCacheManager();
	}
	
	public EhCacheHelper(String configurationFileName) {
		initCacheManager(configurationFileName);
	}
	
	private void initCacheManager(String configurationFileName) {
		if(cacheManager == null) {
			cacheManager = CacheManager.create(configurationFileName); 			
		}
	}

	/**
	 * EhCache has a default configuration file within ehcache.jar which is used if there is no ehcache.xml in the classpath.
	 * It is a singleton, so it's only created once.
	 * @see http://ehcache.sourceforge.net/EhcacheUserGuide.html#id.s6.2   for default configuration 
	 * @see http://ehcache.sourceforge.net/EhcacheUserGuide.html#usingthecachemanager
	 * To customize configuration, add an ehcache.xml file to the classpath.
	 * 
	 */
	private void initCacheManager() {

		if(cacheManager == null) {
			cacheManager = CacheManager.create(); 			
		}
	}
	
	/**
	 * Creates a cache and adds it to the cache manager
	 * @param cacheName you could name the cache with the classname that uses it.
	 * 
	 */
	public void createCache(String cacheName) {
		if(!cacheManager.cacheExists(cacheName)) {
			cacheManager.addCache(cacheName);
		}
	}
	
	/**
	 * Like Hibernate's saveOrUpdate, this is used to create a new cache element and update existing element
	 * The cache knows if cache.put if creating or updating an element.
	 * @param cacheName
	 * @param key	must be unique, should be descriptive
	 * @param value the thing cached
	 */
	public void saveOrUpdateCacheElement(String cacheName, String key, Object value) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}
	
	/**
	 * Get the thing cached
	 * @param cacheName
	 * @param key
	 * @return a Serializable object Note, EhCache can support non-serializable object in memory only cache
	 */
	public Serializable getCacheElementValue(String cacheName, String key) {
		Element element = getCacheElement(cacheName, key);
		if(element == null) {
			return null;
		}
		return element.getValue();
	}

	
	/**
	 * Gets Element, useful for calling Element methods like getHitCount()
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public Element getCacheElement(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		return cache.get(key);		
	}

	/**
	 * Removes 1 element from cache
	 * @param cacheName
	 * @param key
	 */
	public void evictCacheElement(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.remove(key);
	}
	
	/**
	 * Remove all elements from cache
	 * @param cacheName
	 */
	public void evictAllCacheElements(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.removeAll();
	}
	
	/**
	 * Gets how many times element has been accessed. Useful for testing and optimization
	 * @param element
	 * @return
	 */
	public long getCacheElementHitCount(Element element) {
		if(element == null) {
			return 0;
		}
		return element.getHitCount();
	}
	
	/**
	 * @return
	 */
	public String[] getCacheNames() {
		return cacheManager.getCacheNames();
	}
}
