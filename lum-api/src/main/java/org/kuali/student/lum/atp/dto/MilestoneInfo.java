package org.kuali.student.lum.atp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class MilestoneInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name; 
	@XmlElement
	private String desc; 
	@XmlElement
	private String atpKey; 
	@XmlElement
	private Date milestoneDate; 
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
	private Map<String, String> attributes;
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
	public Date getMilestoneDate(){
		return milestoneDate;
	}
	public void setMilestoneDate(Date milestoneDate){
		this.milestoneDate = milestoneDate;
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