package org.kuali.student.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A <code>Map<code> implementation with a fixed maximum size of a 
 * least recently used (LRU) entry list using a <code>LinkedHashMap</code>.
 * 
 * @param <K> Key
 * @param <V> Value
 */
public class LRUMap<K,V> extends LinkedHashMap<K,V> {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** Maximum size of map */
	private int maxSize = 50;

	/**
	 * Constructs a new LRU Map with a default maximum size of 50 entries.
	 */
	public LRUMap() {
	}

	/**
	 * Constructs a new LRU Map.
	 * 
	 * @param maxSize Maximum size of LRU map.
	 */
	public LRUMap(int maxSize) {
    	this.maxSize = maxSize;
    }

	/**
	 * Removed oldest entry in map.
	 * 
	 * @param eldest Oldest entry to remove
	 */
    public boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > this.maxSize;
	}
}
