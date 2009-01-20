package org.kuali.student.core.search.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class Criterion implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private TypeAttribute attribute; 
	@XmlElement
	private String operator; 
	@XmlElement
	private String value; 
	@XmlElement
	private List<String> values; 
	public TypeAttribute getAttribute(){
		return attribute;
	}
	public void setAttribute(TypeAttribute attribute){
		this.attribute = attribute;
	}
	public String getOperator(){
		return operator;
	}
	public void setOperator(String operator){
		this.operator = operator;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public List<String> getValues(){
		if(values == null){
			values = new ArrayList<String>();
		}
		return values;
	}
	public void setValues(List<String> values){
		this.values = values;
	}
}