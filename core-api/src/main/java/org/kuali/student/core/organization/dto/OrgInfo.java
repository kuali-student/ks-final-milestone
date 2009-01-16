package org.kuali.student.core.organization.dto;

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
public class OrgInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String longName; 
	@XmlElement
	private String shortName; 
	@XmlElement
	private String desc; 
	@XmlElement
	private Date effectiveDate; 
	@XmlElement
	private Date expirationDate; 
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
	private String id; 
	public String getLongName(){
		return longName;
	}
	public void setLongName(String longName){
		this.longName = longName;
	}
	public String getShortName(){
		return shortName;
	}
	public void setShortName(String shortName){
		this.shortName = shortName;
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
	public Map<String, String> getAttributes() {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
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
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
}