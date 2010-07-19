package org.kuali.student.core.dictionary.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectStructureDefinition {
	@XmlElement(required = true)
	// ? is this required ? XmlID
	// Removed the XmlIDREF annotation for the dataObjectStructure field of FieldDefinition and 
	// remove the XmlID annotation.  Since these annotations prevents the dataObjectStructure to be serialized.  This causes a problem when trying to retrieve translation information of data of the form {myKey1=myValue2, myKey2=myValue2}
	protected String name;// TODO do we need this?
	@XmlElement
	protected String businessObjectClass;
    @XmlElement
    protected List<FieldDefinition> attributes;
    @XmlElement
    protected boolean hasMetaData;//TODO do we need this?

	public String getBusinessObjectClass() {
		return businessObjectClass;
	}

	public void setBusinessObjectClass(String businessObjectClass) {
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