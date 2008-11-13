package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class EnumContextInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private FieldDescriptor contextValueDescriptor; 
	@XmlAttribute
	private String type; 
	public FieldDescriptor getContextValueDescriptor(){
		return contextValueDescriptor;
	}
	public void setContextValueDescriptor(FieldDescriptor contextValueDescriptor){
		this.contextValueDescriptor = contextValueDescriptor;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
}