package org.kuali.student.core.enumeration.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EnumContextValue implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String type; 
	@XmlElement
	private String value; 
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
}