package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FieldSelector implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private FieldDescriptor fieldDescriptor; 
	@XmlElement
	private Boolean isSelector; 
	@XmlAttribute
	private String key; 
	public FieldDescriptor getFieldDescriptor(){
		return fieldDescriptor;
	}
	public void setFieldDescriptor(FieldDescriptor fieldDescriptor){
		this.fieldDescriptor = fieldDescriptor;
	}
	public Boolean getIsSelector(){
		return isSelector;
	}
	public void setIsSelector(Boolean isSelector){
		this.isSelector = isSelector;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}