package org.kuali.student.core.search.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dictionary.dto.FieldDescriptor;

@XmlAccessorType(XmlAccessType.FIELD)
public class QueryParamInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement(namespace="http://student.kuali.org/wsdl/dictionary")
	private FieldDescriptor fieldDescriptor; 
	@XmlAttribute
	private String key; 
	public FieldDescriptor getFieldDescriptor(){
		return fieldDescriptor;
	}
	public void setFieldDescriptor(FieldDescriptor fieldDescriptor){
		this.fieldDescriptor = fieldDescriptor;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}