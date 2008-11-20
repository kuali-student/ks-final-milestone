package org.kuali.student.core.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String value; 
	@XmlAttribute
	private String key; 
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}