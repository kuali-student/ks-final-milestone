package org.kuali.student.rules.ruleexecution.cache;

import java.util.Set;

public interface Cache<K,V> {

	public void put(K key, V value);

	public V get(K key);

	public void remove(K key);

	public void clear();

	public boolean containsKey(K key);

	public boolean isEmpty();

	public Set<K> keySet();

	public int size();
}
