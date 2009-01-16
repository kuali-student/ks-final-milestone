package org.kuali.student.core.dictionary.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class StateSelector implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private FieldSelector field; 
	@XmlAttribute
	private String key; 
	public FieldSelector getField(){
		return field;
	}
	public void setField(FieldSelector field){
		this.field = field;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}