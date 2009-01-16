package org.kuali.student.core.dictionary.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class FieldDescriptor implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 
	@XmlElement
	private String dataType; 
	@XmlElement
	private Integer minLength; 
	@XmlElement
	private String maxLength; 
	@XmlElement
	private String validChars; 
	@XmlElement
	private String invalidChars; 
	@XmlElement
	private String minValue; 
	@XmlElement
	private String maxValue; 
	@XmlElement
	private EnumFieldView enumueration; 
	@XmlElement
	private Integer minOccurs; 
	@XmlElement
	private String maxOccurs; 
	@XmlElement
	private Boolean readOnly; 
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public String getDataType(){
		return dataType;
	}
	public void setDataType(String dataType){
		this.dataType = dataType;
	}
	public Integer getMinLength(){
		return minLength;
	}
	public void setMinLength(Integer minLength){
		this.minLength = minLength;
	}
	public String getMaxLength(){
		return maxLength;
	}
	public void setMaxLength(String maxLength){
		this.maxLength = maxLength;
	}
	public String getValidChars(){
		return validChars;
	}
	public void setValidChars(String validChars){
		this.validChars = validChars;
	}
	public String getInvalidChars(){
		return invalidChars;
	}
	public void setInvalidChars(String invalidChars){
		this.invalidChars = invalidChars;
	}
	public String getMinValue(){
		return minValue;
	}
	public void setMinValue(String minValue){
		this.minValue = minValue;
	}
	public String getMaxValue(){
		return maxValue;
	}
	public void setMaxValue(String maxValue){
		this.maxValue = maxValue;
	}
	public EnumFieldView getEnumueration() {
		return enumueration;
	}
	public void setEnumueration(EnumFieldView enumueration) {
		this.enumueration = enumueration;
	}
	public Integer getMinOccurs(){
		return minOccurs;
	}
	public void setMinOccurs(Integer minOccurs){
		this.minOccurs = minOccurs;
	}
	public String getMaxOccurs(){
		return maxOccurs;
	}
	public void setMaxOccurs(String maxOccurs){
		this.maxOccurs = maxOccurs;
	}
	public Boolean getReadOnly(){
		return readOnly;
	}
	public void setReadOnly(Boolean readOnly){
		this.readOnly = readOnly;
	}

}