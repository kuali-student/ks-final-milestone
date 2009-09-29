/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.mvc.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;

import com.google.gwt.core.client.GWT;

/**
 * Generic class used to wrap RPC results following the SDO pattern.
 * 
 * @author Kuali Student Team
 *
 */
public class ModelDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String className;
	private String key;
	protected Map<String, ModelDTOValue> map = new HashMap<String, ModelDTOValue>();
	protected Map<String, String> applicationStateMap = new HashMap<String, String>();
	
	private transient ModelDTOAdapter adapter = null;
	
	public ModelDTO(){}
	
	public ModelDTOAdapter getAdapter() {
		if(GWT.isClient()){
			return adapter;
		}
		else{
			return null;
		}
	}

	public void setAdapter(ModelDTOAdapter adapter) {
		if(GWT.isClient()){
			this.adapter = adapter;
		}
	}

	/**
	 * This cop
	 * @param newModelDTO
	 */
	public void copyFrom(ModelDTO newModelDTO){
	    applicationStateMap.putAll(newModelDTO.applicationStateMap);
		map.putAll(newModelDTO.map);
	}
	
	/**
	 * Construct a new instance representing the specified class name
	 * @param className
	 */
	public ModelDTO(String className) {
		this.className = className;
	}

	/**
	 * Return the name of the class that this object represents
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 *
	 * @return Set<String> containing the names of the bean properties contained within
	 */
	public Set<String> keySet() {
		return map.keySet();
	}
	
	/**
	 * Sets a bean property value
	 * @param key String key for the bean property
	 * @param value ModelDTOValue value of the property
	 */
	public void put(String key, ModelDTOValue value) {
	    //GWT.log(key, null);
	    if(value instanceof ModelDTOType){
	    	((ModelDTOType) value).get().setKey(key);
	    }
	    
		if(GWT.isClient() && adapter != null){
			adapter.put(key, value);
		}
		else{
			map.put(key, value);			
		}		
	}

	/** 
	 * Puts a string value as a StringType value in the ModelDTO
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value){
	    StringType stringTypeValue = new StringType();
	    stringTypeValue.set(value);
	    put(key, stringTypeValue);
	}
	
	/**
	 * 
	 * @param key String key for the bean property
	 * @return ModelDTOValue value of the property
	 */
	public ModelDTOValue get(String key) {
		if(GWT.isClient() && adapter != null){
			return adapter.get(key);
		}
		else{
			return map.get(key);
		}
	}


    /** 
     * Gets a string value from a StringType value in the ModelDTO. 
     * This method will throw a ClassCastException if the key is not mapped to 
     * a StringType value
     * 
     * @param key
     * @param value
     */
    public String getString(String key){
        StringType stringType = (StringType)get(key);
        if (stringType != null){
            return stringType.get();
        } else {
            return null;
        }
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
	
    /**
     * Gets the application state key value pair set.
     * 
     * @return Set of application state entries
     */
    public Set<Map.Entry<String,String>> getApplicationStateEntrySet() {
        return this.applicationStateMap.entrySet();
    }

    /**
	 * 
	 * @return number of values in internal map
	 */
	public int size() {
		return map.size();
	}
	
	public String toString(){
	    return map.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
