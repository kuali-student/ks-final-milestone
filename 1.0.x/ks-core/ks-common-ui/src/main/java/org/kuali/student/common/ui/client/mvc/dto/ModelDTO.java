/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.mvc.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValueBinding;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
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
	protected transient Map<String, ModelDTOValue> transientMap = null;
	private transient ModelDTOAdapter adapter = null;
	
    protected void copyMapToTransientMap(){
    	transientMap = new HashMap<String, ModelDTOValue>();
    	for(String mk: map.keySet()){
	    	ModelDTOValue copy = HasModelDTOValueBinding.deepCopy(map.get(mk));
	    	if(copy instanceof ModelDTOType){
	    		((ModelDTOType) copy).get().copyMapToTransientMap();
	    	}
	    	else if(copy instanceof ListType){
	    		for(ModelDTOValue listValue: ((ListType) copy).get()){
	    			if(listValue instanceof ModelDTOType){
	    				((ModelDTOType) listValue).get().copyMapToTransientMap();
	    			}
	    		}
	    	}
	    	transientMap.put(mk, copy);
    	}
    }
	
	//private void putTransient(String)
	
	/**
	 * Sets a bean property value
	 * @param key String key for the bean property
	 * @param value ModelDTOValue value of the property
	 */
	public void put(String key, ModelDTOValue value) {
		if(transientMap == null){
       	 	copyMapToTransientMap();
        }
        
	    if(value instanceof ModelDTOType){
	    	((ModelDTOType) value).get().setKey(key);
	    }
	    
		if(GWT.isClient() && adapter != null){
			adapter.put(key, value);
		}
		else{
			transientMap.put(key, value);
			//TODO put this back in later
			//evaluatePutTransient(key, value);				
		}		
	}
	
	/*protected void evaluatePutTransient(String key, ModelDTOValue newValue){
		if(this.transientMap.containsKey(key)){
			ModelDTOValue oldValue = transientMap.get(key);
			if(newValue instanceof ModelDTOType && oldValue instanceof ModelDTOType){
				ModelDTO model = ((ModelDTOType) newValue).get();
				ModelDTO oldModel = ((ModelDTOType) oldValue).get();
				for(String k: model.keySet()){
					oldModel.put(k, model.get(k));
				}
				((ModelDTOType)oldValue).set(oldModel);
				transientMap.put(key, oldValue);
			}
			else if(newValue instanceof ListType && oldValue instanceof ListType){
				List<ModelDTOValue> list = ((ListType) newValue).get();
				List<ModelDTOValue> oldList = ((ListType) oldValue).get();
				for(ModelDTOValue v: list ){
					if(v instanceof ModelDTOType){
						ModelDTO newModel = ((ModelDTOType) v).get();
						ModelDTOValue id = newModel.get("id");
						if(id != null){
							String idValue = ((StringType)id).get();
							ModelDTO oldModel = matchId(idValue, oldList);
							if(oldModel != null){
								for(String k: newModel.keySet()){
									oldModel.put(k, newModel.get(k));
								}
							}
							else{
								oldList.add(v);
							}
						}
						else{
							oldList.add(v);
						}
					}
					else{
						//are lists of lists possible, potential problems maybe?
						oldList = list;
					}
				}
				((ListType)oldValue).set(oldList);
				transientMap.put(key, oldValue);
			}
			else{
				transientMap.put(key, newValue);
			}
		}
		else{
			transientMap.put(key, newValue);
		}
		
	}
	
	private ModelDTO matchId(String idValue, List<ModelDTOValue> list){
		for(ModelDTOValue v: list ){
			if(v instanceof ModelDTOType){
				ModelDTO vModel = ((ModelDTOType) v).get();
				ModelDTOValue id = vModel.get("id");
				if(id != null){
					String currentId = ((StringType)id).get();
					if(idValue.equals(currentId)){
						return vModel;
					}
				}
			}
			else{
				//bad list exception maybe, or nothing
			}
		}
		return null;
	}*/
	
	public void put(String key, ModelDTOValue value, boolean commitChange){
		if(commitChange){
	       	if(transientMap != null){
	    		//TODO throw exception instead
	       		throw new IllegalStateException("The transient map has uncommitted changes, put failed.  " +
	       				"Call commit() before attempting to put values in the real map.  Attempted key is: " + key);
	       		//commit();
	    	}
	 	    if(value instanceof ModelDTOType){
		    	((ModelDTOType) value).get().setKey(key);
		    }
	       	 
			if(GWT.isClient() && adapter != null){
				adapter.put(key, value, commitChange);
			}
			else{
				map.put(key, value);
			}
		}
		else{
			this.put(key, value);
		}
	}
	
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
	
	public void commit(){
		if(transientMap != null){
			for(String mk: transientMap.keySet()){
				ModelDTOValue value = transientMap.get(mk);
				if(value instanceof ModelDTOType){
				    ModelDTO m =((ModelDTOType) value).get();  
					if (m != null) {
					    m.commit();
					}
				}
				else if(value instanceof ListType){
					listCommit(((ListType) value).get());
				}
			}
			map = transientMap;
			transientMap = null;
		}
	}
	
	private void listCommit(List<ModelDTOValue> list){
	    if (list != null) {
    		for(ModelDTOValue value : list){
    			if(value instanceof ModelDTOType){
    			    ModelDTO m =((ModelDTOType) value).get();  
                    if (m != null) {
                        m.commit();
                    }
    			}
    			else if(value instanceof ListType){
    				listCommit(((ListType) value).get());
    			}
    		}
		}
	}
	
	public void rollback(){
		//Iteration might not be needed
		if(transientMap != null){
			for(String mk: transientMap.keySet()){
				ModelDTOValue value = transientMap.get(mk);
				if(value instanceof ModelDTOType){
				    ModelDTO m =((ModelDTOType) value).get();  
                    if (m != null) {
                        m.rollback();
                    }
				}
			}
		}
		transientMap = null;
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
	 * Puts a string value as a StringType value in the ModelDTO
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value, boolean autoCommit){
	    StringType stringTypeValue = new StringType();
	    stringTypeValue.set(value);
	    put(key, stringTypeValue, autoCommit);
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
			if(transientMap != null){
				return transientMap.get(key);
			}
			else{
				return map.get(key);
			}
			
		}
	}

    /** 
     * Gets a raw string value from a StringType value in the ModelDTO. 
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
     * Gets the raw model dto list from ListType value in ModelDTO.
     * This method will throw a ClassCastException if the key is not mapped to 
     * a StringType value
     */
    public List<ModelDTOValue> getList(String key){
        ListType listType = (ListType)get(key);
        if (listType != null){
            return listType.get();
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
		String s = "";
		if(transientMap != null){
			s = "Real:" + map.toString() +"&Transient:" + transientMap.toString();
		}
		else{
			s = "Real:" + map.toString();
		}
	    return s;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
