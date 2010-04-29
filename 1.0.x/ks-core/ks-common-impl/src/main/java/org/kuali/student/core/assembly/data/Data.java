/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.assembly.data;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author wilj
 */
@SuppressWarnings({"serial", "unchecked"})
public class Data implements Serializable, Iterable<Data.Property>, HasChangeCallbacks {

    public enum DataType {
        STRING, INTEGER, LONG, FLOAT, DOUBLE, BOOLEAN, DATE, TRUNCATED_DATE, DATA, LIST
    }

    @XmlRootElement
    public static class BooleanValue implements Value {
        Boolean value;

        protected BooleanValue() {

        }

        public BooleanValue(final Boolean value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Boolean.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    @XmlRootElement
    public static class DataValue implements Value {
        Data value;

        protected DataValue() {

        }

        public DataValue(final Data value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Data.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return value.toString();
            }
        }
    }

    @XmlRootElement
    public static class DateValue implements Value {
        Date value;

        protected DateValue() {

        }

        public DateValue(final Date value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Date.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    @XmlRootElement
    public static class DoubleValue implements Value {
        Double value;

        protected DoubleValue() {

        }

        public DoubleValue(final Double value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Double.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    @XmlRootElement
    public static class FloatValue implements Value {
        Float value;

        protected FloatValue() {

        }

        public FloatValue(final Float value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Float.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    public static class IntegerKey implements Key {
        private Integer key;

        protected IntegerKey() {

        }

        public IntegerKey(final Integer key) {
            this.key = key;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof IntegerKey && key.equals(((IntegerKey) obj).key);
        }

        @Override
        public <T> T get() {
            return (T) key;
        }

        @Override
        public Class getType() {
            return Integer.class;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return key.hashCode();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return String.valueOf(key);
        }

    }

    @XmlRootElement
    public static class IntegerValue implements Value {
        Integer value;

        protected IntegerValue() {

        }

        public IntegerValue(final Integer value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Integer.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    public interface Key extends Serializable {

        <T> T get();

        Class getType();
    }

    @XmlRootElement
    public static class LongValue implements Value {
        Long value;

        protected LongValue() {

        }

        public LongValue(final Long value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Long.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    public interface Property {
        <T> T getKey();

        Class getKeyType();

        Key getWrappedKey();

        <T> T getValue();

        Class getValueType();

        Value getWrappedValue();
    }

    @XmlRootElement
    public static class ShortValue implements Value {
        Short value;

        protected ShortValue() {

        }

        public ShortValue(final Short value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Short.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    @XmlRootElement
    public static class StringKey implements Key {
        String key;

        protected StringKey() {

        }

        public StringKey(final String key) {
            this.key = key;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof StringKey && key.equals(((StringKey) obj).key);
        }

        @Override
        public <T> T get() {
            return (T) key;
        }

        @Override
        public Class<?> getType() {
            return String.class;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return key.hashCode();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return key;
        }

    }

    @XmlRootElement
    public static class StringValue implements Value {
        String value;

        protected StringValue() {

        }

        public StringValue(final String value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return String.class;
        }

        @Override
        public String toString() {
            return value;
        }
        
        public void setValue(String value) {
			this.value = value;
		}
    }

    @XmlRootElement
    public static class TimestampValue implements Value {
        Timestamp value;

        protected TimestampValue() {

        }

        public TimestampValue(final Timestamp value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Timestamp.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    @XmlRootElement
    public static class TimeValue implements Value {
        Time value;

        protected TimeValue() {

        }

        public TimeValue(final Time value) {
            this.value = value;
        }

        @Override
        public <T> T get() {
            return (T) value;
        }

        @Override
        public Class getType() {
            return Time.class;
        }

        @Override
        public String toString() {
            if (value == null) {
                return null;
            } else {
                return String.valueOf(value);
            }
        }
    }

    public interface Value extends Serializable {
        <T> T get();

        Class getType();
    }

    public static final Key WILDCARD_KEY = new Data.StringKey(QueryPath.getWildCard());

    private transient Set<ChangeCallback> changeCallbacks;

    private String className;

    private Map<Key, Value> map;

    private Data parent = null;

    private Key parentKey = null;

    public Data() {
        this(Data.class.getName());
    }

    public Data(final String className) {
        this.className = className;
        map = new LinkedHashMap<Key, Value>();
    }

    protected void _getQueryPath(final QueryPath path) {
        if (parent != null) {
            parent._getQueryPath(path);
            path.add(parentKey);
        }
    }

    protected void execChangeCallbacks(ChangeType type, QueryPath path) {
        if (changeCallbacks != null) {
            for (ChangeCallback c : changeCallbacks) {
                c.onChange(type, path);
            }
        }
        if (parent != null) {
            parent.execChangeCallbacks(type, path);
        }
    }

    public ChangeCallbackRegistration addChangeCallback(final ChangeCallback callback) {
        if (changeCallbacks == null) {
            changeCallbacks = new HashSet<ChangeCallback>();
        }
        changeCallbacks.add(callback);
        return new ChangeCallbackRegistration() {
            @Override
            public void remove() {
                if (changeCallbacks != null) {
                    changeCallbacks.remove(callback);
                }
            }
        };
    }

    private void put(Key key, Value value) {
        Value existing = map.put(key, value);
        ChangeType type = existing == null ? ChangeType.ADD : ChangeType.UPDATE;
        QueryPath path = getQueryPath();
        path.add(key);
        execChangeCallbacks(type, path);
    }

    public void remove(Key key) {
        // TODO probably need to add all of the other remove(type) methods
        map.remove(key);
        QueryPath path = getQueryPath();
        path.add(key);
        execChangeCallbacks(ChangeType.REMOVE, path);
    }

    public void add(final Boolean value) {
        put(new IntegerKey(map.size()), new BooleanValue(value));
    }

    public void add(final Data value) {
        final Key k = new IntegerKey(map.size());
        put(k, new DataValue(value));
        if (value != null) {
            value.parent = this;
            value.parentKey = k;
        }
    }

    public void add(final Date value) {
        put(new IntegerKey(map.size()), new DateValue(value));
    }

    public void add(final Double value) {
        put(new IntegerKey(map.size()), new DoubleValue(value));
    }

    public void add(final Float value) {
        put(new IntegerKey(map.size()), new FloatValue(value));
    }

    public void add(final Integer value) {
        put(new IntegerKey(map.size()), new IntegerValue(value));
    }

    public void add(final Long value) {
        put(new IntegerKey(map.size()), new LongValue(value));
    }

    public void add(final Short value) {
        put(new IntegerKey(map.size()), new ShortValue(value));
    }

    public void add(final String value) {
        put(new IntegerKey(map.size()), new StringValue(value));
    }

    public void add(final Time value) {
        put(new IntegerKey(map.size()), new TimeValue(value));
    }

    public void add(final Timestamp value) {
        put(new IntegerKey(map.size()), new TimestampValue(value));
    }

    public Data copy() {
        return copy(new Data(this.className), true);
    }

    public Data copy(Data target, boolean recurse) {
        // note, this was the clone() method, but my eclipse code cleanup insists on @Override, and the compiler gives an
        // error
        for (final Entry<Key, Value> e : map.entrySet()) {
            if (recurse && e.getValue().getType().equals(Data.class)) {
                Data value = e.getValue().get();
                value = value.copy();
                target.map.put(e.getKey(), new DataValue(value));
            } else {
                target.map.put(e.getKey(), e.getValue());
            }
        }
        return target;
    }

    public <T> T get(final Integer key) {
        final Value v = map.get(new IntegerKey(key));
        T result = null;
        if (v != null) {
            result = (T) v.get();
        }
        return result;
    }

    public <T> T get(final Key key) {
        final Value v = map.get(key);
        T result = null;
        if (v != null) {
            result = (T) v.get();
        }
        return result;
    }

    public <T> T get(final String key) {
        final Value v = map.get(new StringKey(key));
        T result = null;
        if (v != null) {
            result = (T) v.get();
        }
        return result;
    }

    public String getClassName() {
        return this.className;
    }

    public Data getParent() {
        return parent;
    }

    public QueryPath getQueryPath() {
        final QueryPath result = new QueryPath();
        _getQueryPath(result);
        return result;
    }

    
    /**
     * @return an Iterator that does not contain any _runtimeData
     */
    public Iterator<Property> realPropertyIterator(){
    	HashMap<Key, Value> propertyMap = new HashMap<Key, Value>(map);
    	propertyMap.remove("_runtimeData");
        final Iterator<Map.Entry<Key, Value>> impl = propertyMap.entrySet().iterator();

        return new Iterator<Property>() {
            Map.Entry<Key, Value> current;

            @Override
            public boolean hasNext() {
                return impl.hasNext();
            }

            @Override
            public Property next() {
                final Map.Entry<Key, Value> entry = impl.next();
                current = entry;
                return new Property() {
                    @Override
                    public <T> T getKey() {
                        return (T) entry.getKey().get();
                    }

                    @Override
                    public Class<?> getKeyType() {
                        return entry.getKey().getType();
                    }

                    @Override
                    public <T> T getValue() {
                        return (T) entry.getValue().get();
                    }

                    @Override
                    public Class<?> getValueType() {
                        return entry.getValue().getType();
                    }

                    @Override
                    public Key getWrappedKey() {
                        return entry.getKey();
                    }

                    @Override
                    public Value getWrappedValue() {
                        return entry.getValue();
                    }
                };
            }

            @Override
            public void remove() {
                impl.remove();
                QueryPath path = getQueryPath();
                path.add(current.getKey());
                execChangeCallbacks(ChangeType.REMOVE, path);
            }
        };
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Property> iterator() {
        final Iterator<Map.Entry<Key, Value>> impl = map.entrySet().iterator();

        return new Iterator<Property>() {
            Map.Entry<Key, Value> current;

            @Override
            public boolean hasNext() {
                return impl.hasNext();
            }

            @Override
            public Property next() {
                final Map.Entry<Key, Value> entry = impl.next();
                current = entry;
                return new Property() {
                    @Override
                    public <T> T getKey() {
                        return (T) entry.getKey().get();
                    }

                    @Override
                    public Class<?> getKeyType() {
                        return entry.getKey().getType();
                    }

                    @Override
                    public <T> T getValue() {
                        return (T) entry.getValue().get();
                    }

                    @Override
                    public Class<?> getValueType() {
                        return entry.getValue().getType();
                    }

                    @Override
                    public Key getWrappedKey() {
                        return entry.getKey();
                    }

                    @Override
                    public Value getWrappedValue() {
                        return entry.getValue();
                    }
                };
            }

            @Override
            public void remove() {
                impl.remove();
                QueryPath path = getQueryPath();
                path.add(current.getKey());
                execChangeCallbacks(ChangeType.REMOVE, path);
            }
        };
    }

    public <T> T query(final QueryPath path) {
        T result = null;
        Data d = this;
        for (final Iterator itr = path.iterator(); itr.hasNext() && d != null;) {
            final Key k = (Key) itr.next();
            if (itr.hasNext()) {
                d = d.get(k);
            } else {
                result = (T) d.get(k);
            }
        }
        return result;
    }

    public <T> T query(final String path) {
        return (T) query(QueryPath.parse(path));
    }

    public Class<?> getType(final QueryPath path) {
        Value result = null;
        Data d = this;
        for (final Iterator itr = path.iterator(); itr.hasNext();) {
            final Key k = (Key) itr.next();
            if (itr.hasNext()) {
                d = d.get(k);
            } else {
                result = map.get(k);
            }
        }
        return result.getType();

    }

    public void set(final Integer key, final Boolean value) {
        put(new IntegerKey(key), new BooleanValue(value));
    }

    public void set(final Integer key, final Data value) {
        final Key k = new IntegerKey(key);
        put(k, new DataValue(value));
        if (value != null) {
            value.parent = this;
            value.parentKey = k;
        }
    }

    public void set(final Integer key, final Date value) {
        put(new IntegerKey(key), new DateValue(value));
    }

    public void set(final Integer key, final Double value) {
        put(new IntegerKey(key), new DoubleValue(value));
    }

    public void set(final Integer key, final Float value) {
        put(new IntegerKey(key), new FloatValue(value));
    }

    public void set(final Integer key, final Integer value) {
        put(new IntegerKey(key), new IntegerValue(value));
    }

    public void set(final Integer key, final Long value) {
        put(new IntegerKey(key), new LongValue(value));
    }

    public void set(final Integer key, final Short value) {
        put(new IntegerKey(key), new ShortValue(value));
    }

    public void set(final Integer key, final String value) {
        put(new IntegerKey(key), new StringValue(value));
    }

    public void set(final Integer key, final Time value) {
        put(new IntegerKey(key), new TimeValue(value));
    }

    public void set(final Integer key, final Timestamp value) {
        put(new IntegerKey(key), new TimestampValue(value));
    }

    public void set(final Key key, final Boolean value) {
        put(key, new BooleanValue(value));
    }

    public void set(final Key key, final Data value) {
        put(key, new DataValue(value));
        if (value != null) {
            value.parent = this;
            value.parentKey = key;
        }
    }

    public void set(final Key key, final Date value) {
        put(key, new DateValue(value));
    }

    public void set(final Key key, final Double value) {
        put(key, new DoubleValue(value));
    }

    public void set(final Key key, final Float value) {
        put(key, new FloatValue(value));
    }

    public void set(final Key key, final Integer value) {
        put(key, new IntegerValue(value));
    }

    public void set(final Key key, final Long value) {
        put(key, new LongValue(value));
    }

    public void set(final Key key, final Short value) {
        put(key, new ShortValue(value));
    }

    public void set(final Key key, final String value) {
        put(key, new StringValue(value));
    }

    public void set(final Key key, final Time value) {
        put(key, new TimeValue(value));
    }

    public void set(final Key key, final Timestamp value) {
        put(key, new TimestampValue(value));
    }

    public void set(final Key key, final Value value) {
        put(key, value);
        if (value instanceof DataValue) {
            final Data d = value.get();
            if (d != null) {
                d.parent = this;
                d.parentKey = key;
            }
        }
    }

    public void set(final String key, final Boolean value) {
        put(new StringKey(key), new BooleanValue(value));
    }

    public void set(final String key, final Data value) {
        final Key k = new StringKey(key);
        put(k, new DataValue(value));
        if (value != null) {
            value.parent = this;
            value.parentKey = k;
        }
    }

    public void set(final String key, final Date value) {
        put(new StringKey(key), new DateValue(value));
    }

    public void set(final String key, final Double value) {
        put(new StringKey(key), new DoubleValue(value));
    }

    public void set(final String key, final Float value) {
        put(new StringKey(key), new FloatValue(value));
    }

    public void set(final String key, final Integer value) {
        put(new StringKey(key), new IntegerValue(value));
    }

    public void set(final String key, final Long value) {
        put(new StringKey(key), new LongValue(value));
    }

    public void set(final String key, final Short value) {
        put(new StringKey(key), new ShortValue(value));
    }

    public void set(final String key, final String value) {
        put(new StringKey(key), new StringValue(value));
    }

    public void set(final String key, final Time value) {
        put(new StringKey(key), new TimeValue(value));
    }

    public void set(final String key, final Timestamp value) {
        put(new StringKey(key), new TimestampValue(value));
    }

    public Integer size() {
        return map.size();
    }

    public String toString() {
        StringBuffer dataString = new StringBuffer();

        dataString.append("{");
        for (Iterator itr = this.iterator(); itr.hasNext();) {
            Property p = (Property) itr.next();
            dataString.append(p.getKey() + "=" + p.getValue() + ", ");
        }
        dataString.append("}");

        return dataString.toString();
    }

    public boolean containsKey(Key key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Value value) {
        return map.containsValue(value);
    }

    /**
     * @return set of top level keys known to this data branch
     */
    public Set keySet(){
    	return map.keySet();
    }
}
