package org.kuali.student.core.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeAmountInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String atpDurationTypeKey; 
	@XmlElement
	private Integer timeQuantity; 
	public String getAtpDurationTypeKey(){
		return atpDurationTypeKey;
	}
	public void setAtpDurationTypeKey(String atpDurationTypeKey){
		this.atpDurationTypeKey = atpDurationTypeKey;
	}
	public Integer getTimeQuantity(){
		return timeQuantity;
	}
	public void setTimeQuantity(Integer timeQuantity){
		this.timeQuantity = timeQuantity;
	}
}