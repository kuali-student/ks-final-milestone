package org.kuali.student.core.dictionary.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectStructure implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private TypeSelector type; 
	@XmlAttribute
	private String objectTypeKey; 
	public TypeSelector getType(){
		return type;
	}
	public void setType(TypeSelector type){
		this.type = type;
	}
	public String getObjectTypeKey(){
		return objectTypeKey;
	}
	public void setObjectTypeKey(String objectTypeKey){
		this.objectTypeKey = objectTypeKey;
	}
}