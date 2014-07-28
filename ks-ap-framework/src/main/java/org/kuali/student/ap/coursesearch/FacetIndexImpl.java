package org.kuali.student.ap.coursesearch;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.util.KeyValue;

public class FacetIndexImpl implements FacetIndex, Serializable {
    private static final long serialVersionUID = -8207871534515391613L;

    private final Map<Object, List<KeyValue>> m;

    public FacetIndexImpl(Map<Object, List<KeyValue>> m) {
        this.m = Collections.synchronizedMap(m);
    }

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
    public List<KeyValue> get(Object key) {
        return m.get(key);
    }

    @Override
    public List<KeyValue> put(Object key, List<KeyValue> value) {
        return m.put(key,value);
    }
    @Override
    public List<KeyValue> remove(Object key) {
        return m.remove(key);
    }

    @Override
    public void putAll(Map<?, ? extends List<KeyValue>> m) {
       this.m.putAll(m);
    }

    @Override
    public void clear() {
        this.m.clear();
    }

    @Override
    public Set<Object> keySet() {
        return this.m.keySet();
    }

    @Override
    public Collection<List<KeyValue>> values() {
        return this.m.values();
    }

    @Override
    public Set<Entry<Object, List<KeyValue>>> entrySet() {
        return this.m.entrySet();
    }
}
