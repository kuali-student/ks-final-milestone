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
	protected Map<String, ModelDTOValue> map = new HashMap<String, ModelDTOValue>();
	protected Map<String, ModelDTOValue.ListType> relations = new HashMap<String, ModelDTOValue.ListType>();
	
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
		if(GWT.isClient() && adapter != null){
			adapter.put(key, value);
		}
		else{
			map.put(key, value);			
		}		
	}
	
	/**
	 * Put a list of model dto's related to this model dto
	 * 
	 * @param key
	 */
	public void putRelations(String key, ModelDTOValue.ListType value){
	    relations.put(key, value);
	}
	
	public ModelDTOValue.ListType getRelations(String key){
	    return relations.get(key);
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
	 * 
	 * @return number of values in internal map
	 */
	public int size() {
		return map.size();
	}
	
	public String toString(){
	    return map.toString();
	}
}
