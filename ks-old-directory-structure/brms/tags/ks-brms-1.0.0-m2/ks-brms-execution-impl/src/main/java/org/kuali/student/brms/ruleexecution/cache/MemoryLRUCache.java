package org.kuali.student.brms.ruleexecution.cache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MemoryLRUCache<K,V> implements Cache<K,V> {
	
	private int maxSize = 20;

	private Map<K,V> cacheMap;

	public MemoryLRUCache(int maxSize) {
		if(maxSize <= 1) {
			throw new IllegalArgumentException("Max cache size must be greater then 1");
		}
		this.maxSize = maxSize;
		this.cacheMap = Collections.synchronizedMap(new LruMap<K,V>(this.maxSize));
	}
	
	public void put(K key, V value) {
		this.cacheMap.put(key, value);
	}
	
	public V get(K key) {
		return this.cacheMap.get(key);
	}
	
	public void remove(K key) {
		this.remove(key);
	}

	public void clear() {
		this.cacheMap.clear();
	}
	
	public boolean containsKey(K key) {
		return this.cacheMap.containsKey(key);
	}
	
	public boolean isEmpty() {
		return this.isEmpty();
	}
	
	public Set<K> keySet() {
		return this.cacheMap.keySet();
	}

	public int size() {
		return this.cacheMap.size();
	}
	
	private final static class LruMap<K,V> extends LinkedHashMap<K,V> {
		/** Class serial version uid */
	    private static final long serialVersionUID = 1L;

		private int maxSize = 20;

		public LruMap() {
	    }

		public LruMap(int maxSize) {
	    	this.maxSize = maxSize;
	    }

	    public boolean removeEldestEntry(Map.Entry<K,V> eldest) {
			return size() > this.maxSize;
		}
	}
}
