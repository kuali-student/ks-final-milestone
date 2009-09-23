package org.kuali.student.common.ui.client.mvc.dto;

import java.io.Serializable;
import java.util.Map;

import org.kuali.student.common.ui.client.dictionary.DictionaryManager;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.core.dictionary.dto.Field;

import com.google.gwt.core.client.GWT;

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

	public void put(String path, ModelDTOValue value){
		
		//recursive method
		this.put(baseModelDTO, path, value);
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
                ModelDTOValue modelDTOValue = base.map.get(key);
                ModelDTO modelDTO = null;
                if(modelDTOValue == null){
                    modelDTO = new ModelDTO(getClassName(key));
                    ModelDTOType modelDTOType = new ModelDTOType();
                    modelDTOType.set(modelDTO);
                    
                    base.map.put(key, modelDTOType);
                }
                else{
                    modelDTO = ((ModelDTOType) modelDTOValue).get();
                }
                
                //If there is an adapter in place use that! otherwise continue processing
                if(modelDTO.getAdapter() != null){
                	modelDTO.put(pathArr[1], value);
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
            	System.out.println(key);
                ModelDTOValue modelDTOValue = base.map.get(key);
                
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
        	returnValue = base.map.get(key);
        }
    	return returnValue;
	}
	
    private String getClassName(String key){
    	DictionaryManager dictionary = DictionaryManager.getInstance();
    	String name = null;
    	//GWT.log(key + " " + objectKey + ((StringType) baseModelDTO.map.get("type")).get() + ((StringType) baseModelDTO.map.get("state")).get(), null);
/*    	Map<String, Field> fields = dictionary.getFields(objectKey, ((StringType) baseModelDTO.map.get("type")).get(), ((StringType) baseModelDTO.map.get("state")).get());
    	for(String k: fields.keySet()){
    		System.out.println("Field key: " + k + " Name: " + fields.get(k).getFieldDescriptor().getName());
    	}*/
    	Field field = dictionary.getField(objectKey, ((StringType) baseModelDTO.map.get("type")).get(), ((StringType) baseModelDTO.map.get("state")).get(), key);
    	
    	//System.out.println("Trying to get field with name: " + key + " from ObjectStructure: " + objectKey);
    	if(field != null && field.getFieldDescriptor() != null){
    		
    		//System.out.println(field.getFieldDescriptor().getObjectStructure().getKey());
    		name = objectKeyClassNameMap.get(field.getFieldDescriptor().getObjectStructure().getKey());
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
    	
        return name;
    }
}