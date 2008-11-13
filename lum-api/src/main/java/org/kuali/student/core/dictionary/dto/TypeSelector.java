package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class TypeSelector implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private StateSelector state; 
	@XmlAttribute
	private String key; 
	public StateSelector getState(){
		return state;
	}
	public void setState(StateSelector state){
		this.state = state;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}