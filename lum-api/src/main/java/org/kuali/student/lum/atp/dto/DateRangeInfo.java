package org.kuali.student.lum.atp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.AttributeInfo;
import org.kuali.student.core.dto.MetaInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class DateRangeInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 
	@XmlElement
	private String atpKey; 
	@XmlElement
	private Date startDate; 
	@XmlElement
	private Date endDate; 
	@XmlElement
	private List<AttributeInfo> attributes; 
	@XmlElement
	private MetaInfo metaInfo; 
	@XmlAttribute
	private String type; 
	@XmlAttribute
	private String state; 
	@XmlAttribute
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
	public String getAtpKey(){
		return atpKey;
	}
	public void setAtpKey(String atpKey){
		this.atpKey = atpKey;
	}
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	public List<AttributeInfo> getAttributes(){
		if(attributes == null){
			attributes = new ArrayList<AttributeInfo>();
		}
		return attributes;
	}
	public void setAttributes(List<AttributeInfo> attributes){
		this.attributes = attributes;
	}
	public MetaInfo getMetaInfo(){
		return metaInfo;
	}
	public void setMetaInfo(MetaInfo metaInfo){
		this.metaInfo = metaInfo;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
}