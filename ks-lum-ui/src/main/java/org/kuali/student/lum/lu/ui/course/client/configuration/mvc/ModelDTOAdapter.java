package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUDictionaryManager;

public class ModelDTOAdapter {
    public static void set(ModelDTO base, String path, ModelDTOValue value, String objectKey, String type, String state){
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        String[] pathArr = path.split("/", 2);
        String key = pathArr[0];
        if(pathArr.length > 1){
            
            System.out.println(key);
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
                ModelDTOValue modelDTOValue = base.get(key);
                ModelDTO modelDTO = null;
                if(modelDTOValue == null){
                    modelDTO = new ModelDTO(getClassName(key, objectKey, type, state));
                    ModelDTOType modelDTOType = new ModelDTOType();
                    modelDTOType.set(modelDTO);
                    
                    base.put(key, modelDTOType);
                }
                else{
                    modelDTO = ((ModelDTOType) modelDTOValue).get();
                }
                
                set(modelDTO, pathArr[1], value, objectKey, type, state);
            }
        }
        else{
            System.out.println(key);
            base.put(key, value);
        }
     
    }
    
    public static ModelDTOValue get(ModelDTO base, String path){
    	ModelDTOValue returnValue = null;
    	
    	if(path.startsWith("/")){
            path = path.substring(1);
        }
        String[] pathArr = path.split("/", 2);
        String key = pathArr[0];
        if(pathArr.length > 1){
            
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
                ModelDTOValue modelDTOValue = base.get(key);
                
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
        	returnValue = base.get(key);
        }
    	return returnValue;
        
    }
    
    private static String getClassName(String key, String objectKey, String type, String state){
    	LUDictionaryManager dictionary = LUDictionaryManager.getInstance();
    	
    	Field field = dictionary.getField(objectKey, type, state, key);
    	
    	String name = field.getFieldDescriptor().getObjectStructure().getKey();
    	System.out.println(name);
/*    	String name = null;
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
