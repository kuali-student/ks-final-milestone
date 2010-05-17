package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

public class ObjectStructureDefinition{
    protected Class<?> businessObjectClass;
    protected List<FieldDefinition> attributes;
	public Class<?> getBusinessObjectClass() {
		return businessObjectClass;
	}
	public void setBusinessObjectClass(Class<?> businessObjectClass) {
		this.businessObjectClass = businessObjectClass;
	}
	public List<FieldDefinition> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<FieldDefinition> attributes) {
		this.attributes = attributes;
	}
 }