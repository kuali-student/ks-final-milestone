package org.kuali.student.core.enumeration.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class EnumeratedValue implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String code; 
	@XmlElement
	private String abbrevValue; 
	@XmlElement
	private String value; 
	@XmlElement
	private Date effectiveDate; 
	@XmlElement
	private Date expirationDate; 
	@XmlElement
	private String sortKey; 
	@XmlElement
	private List<EnumContextValue> contexts; 
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getAbbrevValue(){
		return abbrevValue;
	}
	public void setAbbrevValue(String abbrevValue){
		this.abbrevValue = abbrevValue;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public Date getEffectiveDate(){
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate){
		this.effectiveDate = effectiveDate;
	}
	public Date getExpirationDate(){
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate){
		this.expirationDate = expirationDate;
	}
	public String getSortKey(){
		return sortKey;
	}
	public void setSortKey(String sortKey){
		this.sortKey = sortKey;
	}
	public List<EnumContextValue> getContexts(){
		if(contexts == null){
			contexts = new ArrayList<EnumContextValue>();
		}
		return contexts;
	}
	public void setContexts(List<EnumContextValue> contexts){
		this.contexts = contexts;
	}
}