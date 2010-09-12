package org.kuali.student.core.dictionary.service.poc.jaxws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectStructureDefinitionWrapper {
	private String rootDefinitionName;
	private List<ObjectStructureDefinition> definitions;

	public List<ObjectStructureDefinition> getDefinitions() {
		if(definitions == null){
			definitions = new ArrayList<ObjectStructureDefinition>();
		}
		
		return definitions;
	}
	public void setDefinitions(List<ObjectStructureDefinition> definitions) {
		this.definitions = definitions;
	}
	public String getRootDefinitionName() {
		return rootDefinitionName;
	}
	public void setRootDefinitionName(String rootDefinitionName) {
		this.rootDefinitionName = rootDefinitionName;
	}
	
}
