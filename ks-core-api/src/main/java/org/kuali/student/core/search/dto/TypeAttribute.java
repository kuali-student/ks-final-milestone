package org.kuali.student.core.search.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TypeAttribute implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String parentType; 
	@XmlElement
	private String name; 
	@XmlElement
	private String alias; 
	public String getParentType(){
		return parentType;
	}
	public void setParentType(String parentType){
		this.parentType = parentType;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getAlias(){
		return alias;
	}
	public void setAlias(String alias){
		this.alias = alias;
	}
}