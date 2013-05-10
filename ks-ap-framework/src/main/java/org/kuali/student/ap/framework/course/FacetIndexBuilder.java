package org.kuali.student.ap.framework.course;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.util.KeyValue;

public class FacetIndexBuilder extends
		LinkedHashMap<String, Map<String, Map<String, KeyValue>>> implements
		FacetIndex {

	private static final long serialVersionUID = -8207871534515391613L;

	public FacetIndex build() {
		final Map<String, Map<String, Map<String, KeyValue>>> m = Collections
				.synchronizedMap(Collections.unmodifiableMap(this));
		return new FacetIndex() {

			@Override
			public int size() {
				return m.size();
			}

			@Override
			public boolean isEmpty() {
				return m.isEmpty();
			}

			@Override
			public boolean containsKey(Object key) {
				return m.containsKey(key);
			}

			@Override
			public boolean containsValue(Object value) {
				return m.containsValue(value);
			}

			@Override
			public Map<String, Map<String, KeyValue>> get(Object key) {
				return m.get(key);
			}

			@Override
			public Map<String, Map<String, KeyValue>> put(String key,
					Map<String, Map<String, KeyValue>> value) {
				return m.put(key, value);
			}

			@Override
			public Map<String, Map<String, KeyValue>> remove(Object key) {
				return m.remove(key);
			}

			@Override
			public void putAll(
					Map<? extends String, ? extends Map<String, Map<String, KeyValue>>> src) {
				m.putAll(src);
			}

			@Override
			public void clear() {
				m.clear();
			}

			@Override
			public Set<String> keySet() {
				return m.keySet();
			}

			@Override
			public Collection<Map<String, Map<String, KeyValue>>> values() {
				return m.values();
			}

			@Override
			public Set<java.util.Map.Entry<String, Map<String, Map<String, KeyValue>>>> entrySet() {
				return m.entrySet();
			}
		};
	}

}
