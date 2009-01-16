package org.kuali.student.core.dictionary.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class EnumFieldView implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private List<EnumContextInfo> contextDescriptors; 
	@XmlAttribute
	private String key; 
	public List<EnumContextInfo> getContextDescriptors(){
		if(contextDescriptors == null){
			contextDescriptors = new ArrayList<EnumContextInfo>();
		}
		return contextDescriptors;
	}
	public void setContextDescriptors(List<EnumContextInfo> contextDescriptors){
		this.contextDescriptors = contextDescriptors;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}