package org.kuali.student.core.validation.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResult implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String element; 
	@XmlElement
	private String errorLevel; 
	@XmlElement
	private String message; 
	@XmlElement
	private String task; 
	public String getElement(){
		return element;
	}
	public void setElement(String element){
		this.element = element;
	}
	public String getErrorLevel(){
		return errorLevel;
	}
	public void setErrorLevel(String errorLevel){
		this.errorLevel = errorLevel;
	}
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public String getTask(){
		return task;
	}
	public void setTask(String task){
		this.task = task;
	}
}