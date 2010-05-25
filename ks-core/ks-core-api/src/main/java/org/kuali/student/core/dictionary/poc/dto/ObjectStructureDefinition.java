package org.kuali.student.core.dictionary.poc.dto;

import java.util.ArrayList;
import java.util.List;

public class ObjectStructureDefinition {
	protected String name;// TODO do we need this?
	protected Class<?> businessObjectClass;
	protected List<FieldDefinition> attributes;
	protected boolean hasMetaData;//TODO do we need this?

	public Class<?> getBusinessObjectClass() {
		return businessObjectClass;
	}

	public void setBusinessObjectClass(Class<?> businessObjectClass) {
		this.businessObjectClass = businessObjectClass;
	}

	public List<FieldDefinition> getAttributes() {
		if(attributes==null){
			attributes = new ArrayList<FieldDefinition>();
		}
		return attributes;
	}

	public void setAttributes(List<FieldDefinition> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHasMetaData() {
		return hasMetaData;
	}

	public void setHasMetaData(boolean hasMetaData) {
		this.hasMetaData = hasMetaData;
	}
}