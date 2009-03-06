package org.kuali.student.rules.lumgui.client.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class TypeInfo implements Serializable {

    private static final long serialVersionUID = 123123142351353L;
    
	private String name;
	private String desc;
	private Date effectiveDate;
	private Date expirationDate;
	private Map<String, String> attributes;
	private String key;
	
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
	public Map<String, String> getAttributes(){
		if(attributes == null){
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes){
		this.attributes = attributes;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
	public String getId(){
		return key;
	}
	public void setId(String key){
		this.key = key;
	}
}
