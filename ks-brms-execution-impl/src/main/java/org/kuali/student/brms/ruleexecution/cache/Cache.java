package org.kuali.student.brms.ruleexecution.cache;

import java.util.Set;

public interface Cache<K,V> {

	/**
	 * Adds a <code>key</code> and <code>value</code> to the cache. 
	 * If the cache previously contained a mapping for the key, 
	 * the old value is replaced by the new specified value.
	 * 
	 * @param key Cache key
	 * @param value Cache value
	 * @return the previous value associated with <tt>key</tt>, or
	 *         <tt>null</tt> if there was no mapping for <tt>key</tt>
	 */
	public V put(K key, V value);

	/**
	 * Gets a <code>value</code> from the cache by <code>key</code>.
	 * 
	 * @param key Cache key
	 * @return Cache value
	 */
	public V get(K key);

	/**
	 * Removes the <code>key</code> from this cache if it is present.
	 * 
	 * @param key Cache key
     * @return Previous value associated with <tt>>key</tt> or 
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
	 */
	public V remove(K key);

	/**
	 * Clears the cache.
	 */
	public void clear();

	/**
     * Returns <tt>true</tt> if this cache contains the specified key.  
     * More formally, returns <tt>true</tt> if and only if
     * this cache contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
	 * 
	 * @param key Key whose presence in this cache is to be tested
	 * @return <tt>true</tt> if this cache contains the specified key
	 */
	public boolean containsKey(K key);

	/**
     * Returns <tt>true</tt> if this map contains no key-value mappings in cache.
	 * 
	 * @return <tt>true</tt> if this map contains no key-value mappings in cache
	 */
	public boolean isEmpty();

	/**
	 * Returns a set of all keys in cache.
	 * 
	 * @return Set of all keys
	 */
	public Set<K> keySet();

	/**
	 * Returns the size of cache.
	 * 
	 * @return Size of cache
	 */
	public int size();
}
