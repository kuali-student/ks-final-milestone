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

package org.kuali.student.lum.lu.assembly.data.client;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;

/** 
 * 	This is class extends the data object to support additional non-data elements that are used by
 *  the lu screens (eg. RuleInfo). This is a temporary solution to support ui screens that at this
 *  time does not make use of the new DataModel.   
 *
 */
public class LuData extends Data {

	private static final long serialVersionUID = 1L;

	private Data data;
	protected Map<String, String> applicationStateMap = new HashMap<String, String>();

	public LuData(Data data){
		this.data = data;
	}
	
	public LuData(){
		this.data = new Data();
	}
	
	/**
	 * Set the underlying data
	 * @param data
	 */
	public void setData(Data data){
		this.data = data;
	}
	
    /**
     * Get the underlying data
     */
    public Data getData(){
        return data;
    }
    
    /**
	 * Gets the application state by key.
	 * 
	 * @param key Application state key
	 * @return
	 */
	public String getApplicationState(String key) {
		return applicationStateMap.get(key);
	}

	/**
	 * Adds the application state.
	 * 
	 * @param key Application state key
	 * @param value Application state value
	 */
	public void putApplicationState(String key, String value) {
		this.applicationStateMap.put(key, value);
	}
	
	//The following methods just delegate to the wrapped data object
		
	public void add(Boolean value) {
		data.add(value);
	}

	public void add(Data value) {
		data.add(value);
	}

	public void add(Date value) {
		data.add(value);
	}

	public void add(Double value) {
		data.add(value);
	}

	public void add(Float value) {
		data.add(value);
	}

	public void add(Integer value) {
		data.add(value);
	}

	public void add(Long value) {
		data.add(value);
	}

	public void add(Short value) {
		data.add(value);
	}

	public void add(String value) {
		data.add(value);
	}

	public void add(Time value) {
		data.add(value);
	}

	public void add(Timestamp value) {
		data.add(value);
	}

	public ChangeCallbackRegistration addChangeCallback(ChangeCallback callback) {
		return data.addChangeCallback(callback);
	}

	public boolean containsKey(Key key) {
		return data.containsKey(key);
	}

	public boolean containsValue(Value value) {
		return data.containsValue(value);
	}

	public Data copy() {
		return data.copy();
	}

	public Data copy(Data target, boolean recurse) {
		return data.copy(target, recurse);
	}

	public boolean equals(Object obj) {
		return data.equals(obj);
	}

	public <T> T get(Integer key) {
		return data.<T>get(key);
	}

	public <T> T get(Key key) {
		return data.<T>get(key);
	}

	public <T> T get(String key) {
		return data.<T>get(key);
	}

	public String getClassName() {
		return data.getClassName();
	}

	public Data getParent() {
		return data.getParent();
	}

	public QueryPath getQueryPath() {
		return data.getQueryPath();
	}

	public Class<?> getType(QueryPath path) {
		return data.getType(path);
	}

	public int hashCode() {
		return data.hashCode();
	}

	public Iterator<Property> iterator() {
		return data.iterator();
	}

	@Override
	public Iterator<Property> realPropertyIterator() {
		return data.realPropertyIterator();
	}
	
	public <T> T query(QueryPath path) {
		return data.<T>query(path);
	}

	public <T> T query(String path) {
		return data.<T>query(path);
	}

	public void remove(Key key) {
		data.remove(key);
	}

	public void set(Integer key, Boolean value) {
		data.set(key, value);
	}


	public void set(Integer key, Data value) {
		data.set(key, value);
	}

	public void set(Integer key, Date value) {
		data.set(key, value);
	}

	public void set(Integer key, Double value) {
		data.set(key, value);
	}

	public void set(Integer key, Float value) {
		data.set(key, value);
	}

	public void set(Integer key, Integer value) {
		data.set(key, value);
	}

	public void set(Integer key, Long value) {
		data.set(key, value);
	}

	public void set(Integer key, Short value) {
		data.set(key, value);
	}

	public void set(Integer key, String value) {
		data.set(key, value);
	}

	public void set(Integer key, Time value) {
		data.set(key, value);
	}

	public void set(Integer key, Timestamp value) {
		data.set(key, value);
	}

	public void set(Key key, Boolean value) {
		data.set(key, value);
	}

	

	public void set(Key key, Data value) {
		data.set(key, value);
	}

	public void set(Key key, Date value) {
		data.set(key, value);
	}

	public void set(Key key, Double value) {
		data.set(key, value);
	}

	public void set(Key key, Float value) {
		data.set(key, value);
	}

	public void set(Key key, Integer value) {
		data.set(key, value);
	}

	public void set(Key key, Long value) {
		data.set(key, value);
	}

	public void set(Key key, Short value) {
		data.set(key, value);
	}

	public void set(Key key, String value) {
		data.set(key, value);
	}

	public void set(Key key, Time value) {
		data.set(key, value);
	}

	public void set(Key key, Timestamp value) {
		data.set(key, value);
	}

	public void set(Key key, Value value) {
		data.set(key, value);
	}

	public void set(String key, Boolean value) {
		data.set(key, value);
	}

	

	public void set(String key, Data value) {
		data.set(key, value);
	}

	public void set(String key, Date value) {
		data.set(key, value);
	}

	public void set(String key, Double value) {
		data.set(key, value);
	}

	public void set(String key, Float value) {
		data.set(key, value);
	}

	public void set(String key, Integer value) {
		data.set(key, value);
	}

	public void set(String key, Long value) {
		data.set(key, value);
	}

	public void set(String key, Short value) {
		data.set(key, value);
	}

	public void set(String key, String value) {
		data.set(key, value);
	}

	public void set(String key, Time value) {
		data.set(key, value);
	}

	public void set(String key, Timestamp value) {
		data.set(key, value);
	}

	public Integer size() {
		return data.size();
	}

	public String toString() {
		return data.toString();
	}

	public Set keySet() {
		return data.keySet();
	}

}
