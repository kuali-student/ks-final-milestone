package org.kuali.student.r1.common.dictionary.service.jaxws;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.kuali.student.r1.common.dictionary.dto.DataType;
import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;

@Deprecated
public class ObjStructXmlTypeAdapter extends XmlAdapter<ObjectStructureDefinitionWrapper, ObjectStructureDefinition> {

	@Override
	public ObjectStructureDefinitionWrapper marshal(ObjectStructureDefinition objStruct)
			throws Exception {
		ObjectStructureDefinitionWrapper wrapper = new ObjectStructureDefinitionWrapper();
		
		wrapper.setRootDefinitionName(objStruct.getName());
		
		Map<String,ObjectStructureDefinition> objStructDefMap = parseNestedDefinitions(objStruct, new HashMap<String,ObjectStructureDefinition>());
		
		wrapper.getDefinitions().addAll(objStructDefMap.values());
		
		return wrapper;
	}


	@Override
	public ObjectStructureDefinition unmarshal(
			ObjectStructureDefinitionWrapper wrapper) throws Exception {
		String rootDefinitionName = wrapper.getRootDefinitionName();
		if(rootDefinitionName!=null){
			for(ObjectStructureDefinition objStruct : wrapper.getDefinitions()){
				if(rootDefinitionName.equals(objStruct.getName())){
					return objStruct;
				}
			}
		}
		return null;
	}

	private Map<String,ObjectStructureDefinition> parseNestedDefinitions(ObjectStructureDefinition objStruct, Map<String, ObjectStructureDefinition> objStructDefMap) {
		//See if this type was already parsed, if not, add to the list of nested structures and recurse
		if(!objStructDefMap.containsKey(objStruct.getName())){
			objStructDefMap.put(objStruct.getName(), objStruct);
			
			//Loop through all field definitions and check for complex types
			for(FieldDefinition fd:objStruct.getAttributes()){
				if(DataType.COMPLEX.equals(fd.getDataType())){
					parseNestedDefinitions(fd.getDataObjectStructure(), objStructDefMap);
				}
			}
		}
		return objStructDefMap;
	}

}
