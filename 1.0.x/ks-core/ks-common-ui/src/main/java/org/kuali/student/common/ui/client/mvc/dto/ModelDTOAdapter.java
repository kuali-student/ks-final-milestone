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

package org.kuali.student.common.ui.client.mvc.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.dictionary.DictionaryManager;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;

@Deprecated
public class ModelDTOAdapter implements Serializable{
	private static final long serialVersionUID = 1L;
	private String objectKey;
	private ModelDTO baseModelDTO;
	private Map<String, String> objectKeyClassNameMap;
	
	public ModelDTOAdapter(ModelDTO baseModelDTO, Map<String, String> objectKeyClassNameMap, String objectKey){
		this.objectKey = objectKey;
		this.baseModelDTO = baseModelDTO;
		this.objectKeyClassNameMap = objectKeyClassNameMap;
	}
	
	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public ModelDTO getBaseModelDTO() {
		return baseModelDTO;
	}

	public void setBaseModelDTO(ModelDTO baseModelDTO) {
		this.baseModelDTO = baseModelDTO;
	}

	public Map<String, String> getObjectKeyClassNameMap() {
		return objectKeyClassNameMap;
	}

	public void setObjectKeyClassNameMap(Map<String, String> objectKeyClassNameMap) {
		this.objectKeyClassNameMap = objectKeyClassNameMap;
	}

	public void put(String path, ModelDTOValue value, boolean autoCommit){
		
		//recursive method
		if(autoCommit){
			this.put(baseModelDTO, path, value);
		}
		else{
			this.putTransient(baseModelDTO, path, value);
		}
		
    }
	
	public void put(String path, ModelDTOValue value){
		this.putTransient(baseModelDTO, path, value);	
    }
	
	private void putTransient(ModelDTO base, String path, ModelDTOValue value){
		if(path.startsWith("/")){
            path = path.substring(1);
        }
        String[] pathArr = path.split("/", 2);
        String key = pathArr[0];
        if(pathArr.length > 1){
            
            //System.out.println(key);
            if(key.contains("[")){
                String[] keyArr = pathArr[0].split("[");
                key = keyArr[0];
                String[] attributeArr = keyArr[1].replace("]", "").split("=");
                if(attributeArr[0].equals("id")){
                    String id = attributeArr[1];
                    //TODO: not handled yet
                }
                else if(attributeArr[0].equalsIgnoreCase("value")){
                    String itemValue = attributeArr[1];
                    //TODO: not handled yet
                }
            }
            else{
            	ModelDTO modelDTO = null;
            	ModelDTOValue modelDTOValue = null;
            	//check for transientMap
                if(base.transientMap == null){
                	base.copyMapToTransientMap();
                }
            	modelDTOValue = base.transientMap.get(key);
                
                if(modelDTOValue == null){
                    modelDTO = getModelDTO(key, false);
                    ModelDTOType modelDTOType = new ModelDTOType();
                    modelDTOType.set(modelDTO);

                    base.transientMap.put(key, modelDTOType);                    
                }
                else{
                    modelDTO = ((ModelDTOType) modelDTOValue).get();
                }
                
                //If there is an adapter in place use that! otherwise continue processing
                if(modelDTO.getAdapter() != null){
                	modelDTO.put(pathArr[1], value);
                }
                else{
                	putTransient(modelDTO, pathArr[1], value);
                }
            }
        }
        else{
            if(base.transientMap == null){
            	base.copyMapToTransientMap();
            }
            base.transientMap.put(key, value);
        	//base.evaluatePutTransient(key, value);
        }
	}
	
	private void put(ModelDTO base, String path, ModelDTOValue value){
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        String[] pathArr = path.split("/", 2);
        String key = pathArr[0];
        if(pathArr.length > 1){
            
            //System.out.println(key);
            if(key.contains("[")){
                String[] keyArr = pathArr[0].split("[");
                key = keyArr[0];
                String[] attributeArr = keyArr[1].replace("]", "").split("=");
                if(attributeArr[0].equals("id")){
                    String id = attributeArr[1];
                    //TODO: not handled yet
                }
                else if(attributeArr[0].equalsIgnoreCase("value")){
                    String itemValue = attributeArr[1];
                    //TODO: not handled yet
                }
            }
            else{
            	ModelDTO modelDTO = null;
            	ModelDTOValue modelDTOValue = null;
            	//check for transientMap
            	modelDTOValue = base.map.get(key);

                
                if(modelDTOValue == null){
                    modelDTO = getModelDTO(key, true);
                    ModelDTOType modelDTOType = new ModelDTOType();
                    modelDTOType.set(modelDTO);
                    base.map.put(key, modelDTOType);
                    
                }
                else{
                    modelDTO = ((ModelDTOType) modelDTOValue).get();
                }
                
                //If there is an adapter in place use that! otherwise continue processing
                if(modelDTO.getAdapter() != null){
                	modelDTO.put(pathArr[1], value, true);
                }
                else{
                	put(modelDTO, pathArr[1], value);
                }
            }
        }
        else{
            //System.out.println(key);
        	base.map.put(key, value);
        }
	}
	
    public ModelDTOValue get(String path){
    	return get(baseModelDTO, path);
        
    }
	
	private ModelDTOValue get(ModelDTO base, String path){
    	ModelDTOValue returnValue = null;
    	
    	if(path.startsWith("/")){
            path = path.substring(1);
        }
        String[] pathArr = path.split("/", 2);
        String key = pathArr[0];
        if(pathArr.length > 1){
            if(key.contains("[")){
                
                //throw new UnsupportedActionException("Keys with [key=value] are currently unsupported");
/*                String[] keyArr = pathArr[0].split("[");
                key = keyArr[0];
                String[] attributeArr = keyArr[1].replace("]", "").split("=");
                if(attributeArr[0].equals("id")){
                    String id = attributeArr[1];
                    //TODO: not handled yet
                }
                else if(attributeArr[0].equalsIgnoreCase("value")){
                    String itemValue = attributeArr[1];
                    //TODO: not handled yet
                }*/
            }
            else{
            	ModelDTOValue modelDTOValue = null;
            	if(base.transientMap != null){
            		modelDTOValue = base.transientMap.get(key);
            	}
            	else{
            		modelDTOValue = base.map.get(key);
            	}
                
                if(modelDTOValue == null){
                    returnValue = null;
                }
                else{
                	ModelDTO modelDTO = null;
                    modelDTO = ((ModelDTOType) modelDTOValue).get();
                    returnValue = get(modelDTO, pathArr[1]);
                }
            }
        }
        else{
        	if(base.transientMap != null){
        		returnValue = base.transientMap.get(key);
        	}
        	else{
        		returnValue = base.map.get(key);
        	}
        }
    	return returnValue;
	}
	
    private ModelDTO getModelDTO(String key, boolean autoCommit){
    	ModelDTO modelDTO = null;
    	DictionaryManager dictionary = DictionaryManager.getInstance();
    	String name = null;
    	//GWT.log(key + " " + objectKey + ((StringType) baseModelDTO.map.get("type")).get() + ((StringType) baseModelDTO.map.get("state")).get(), null);
/*    	Map<String, Field> fields = dictionary.getFields(objectKey, ((StringType) baseModelDTO.map.get("type")).get(), ((StringType) baseModelDTO.map.get("state")).get());
    	for(String k: fields.keySet()){
    		System.out.println("Field key: " + k + " Name: " + fields.get(k).getFieldDescriptor().getName());
    	}*/
    	System.out.println("baseModelDTO:\n" + baseModelDTO.toString());
    	
    	StringType dtoType = (StringType) baseModelDTO.get("type");
    	StringType dtoState = (StringType) baseModelDTO.get("state");
    	
    	String dtoTypeString = (dtoType == null) ? null : dtoType.get();
    	String dtoStateString = (dtoState == null) ? null : dtoState.get();
    	
    	Field field = null;
    	if (dtoTypeString != null && dtoStateString != null) {
    		field = dictionary.getField(objectKey, dtoTypeString, dtoStateString, key);
    	}
    	
    	//System.out.println("Trying to get field with name: " + key + " from ObjectStructure: " + objectKey);
    	if(field != null && field.getFieldDescriptor() != null){
    		//FIXME this is a temporary solution assuming only 1 type and state possible
    		String type = "";
    		String state = "";
    		List<Type> types = field.getFieldDescriptor().getObjectStructure().getType();
    		for(Type t: types){
    			type = t.getKey();
    			for(State s: t.getState()){
    				state = s.getKey();
    			}
    		}
    		//System.out.println(field.getFieldDescriptor().getObjectStructure().getKey());
    		name = objectKeyClassNameMap.get(field.getFieldDescriptor().getObjectStructure().getKey());
    		
    		//Create modelDTO
    		modelDTO = new ModelDTO(name);
    		modelDTO.setKey(key);
    		StringType typeValue = new StringType(type);
    		StringType stateValue = new StringType(state);
    		if(autoCommit){
    			modelDTO.map.put("type", typeValue);
    			modelDTO.map.put("state", stateValue);
    		}
    		else{
    			modelDTO.copyMapToTransientMap();
    			modelDTO.transientMap.put("type", typeValue);
    			modelDTO.transientMap.put("state", stateValue);
    		}
    		
    		
    	}
    	
/*    	if(field == null){
    	    System.out.println("Field is null" + " Type is " + type + " State is " +  state + " ObjectKey is " + objectKey);
    	}
    	else{
    	    System.out.println("Field is not null" + " Type is " + type + " State is " +  state + " ObjectKey is " + objectKey);
    	    System.out.println(field.getFieldDescriptor().getObjectStructure().getKey());
    	    name = field.getFieldDescriptor().getObjectStructure().getKey();
    	}
    	
        if(key.equalsIgnoreCase("officialIdentifier")){
            name = CluIdentifierInfo.class.getName();
        }
        else if(key.equalsIgnoreCase("primaryInstructor")){
            name = CluInstructorInfo.class.getName();
        }
        else if(key.equalsIgnoreCase("publishingInfo")){
            name = CluPublishingInfo.class.getName();
        }*/
    	
        return modelDTO;
    }
}
