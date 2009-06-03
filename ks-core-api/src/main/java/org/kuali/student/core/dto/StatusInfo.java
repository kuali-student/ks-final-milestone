package org.kuali.student.core.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatusInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Boolean success = true;
	@XmlElement
	private String message = "";
	public Boolean getSuccess(){
		return success;
	}
	public void setSuccess(Boolean success){
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
