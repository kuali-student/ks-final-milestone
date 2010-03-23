package org.kuali.student.services.test.lum;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MemoryLRUCache<K,V> {

	private int maxSize = 20; 

    private Map<K,V> cacheMap;

    public void clear() {
		cacheMap.clear();
	}

	public boolean containsKey(Object key) {
		return cacheMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return cacheMap.containsValue(value);
	}

	public Set<Entry<K, V>> entrySet() {
		return cacheMap.entrySet();
	}

	public boolean equals(Object o) {
		return cacheMap.equals(o);
	}

	public V get(Object key) {
		return cacheMap.get(key);
	}

	public int hashCode() {
		return cacheMap.hashCode();
	}
	 int counter = 0;
	public boolean isEmpty() {
		counter++;
		System.out.println("Counter: " + counter);
		if (counter > 5)  return false;
		return this.isEmpty();
	}

	public Set<K> keySet() {
		return cacheMap.keySet();
	}

	public V put(K key, V value) {
		return cacheMap.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		cacheMap.putAll(t);
	}

	public V remove(Object key) {
		return cacheMap.remove(key);
	}

	public int size() {
		return cacheMap.size();
	}

	public Collection<V> values() {
		return cacheMap.values();
	}

	public MemoryLRUCache(int maxSize) {
            if(maxSize <= 1) {
                    throw new IllegalArgumentException("Max cache size must be greater then 1");
            }   
            this.maxSize = maxSize;
            this.cacheMap = Collections.synchronizedMap(new HashMap<K,V>(this.maxSize));
    }   


}
