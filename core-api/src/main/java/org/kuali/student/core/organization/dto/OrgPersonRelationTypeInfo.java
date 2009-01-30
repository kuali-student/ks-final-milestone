package org.kuali.student.core.organization.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgPersonRelationTypeInfo extends TypeInfo implements Serializable {
	private static final long serialVersionUID = -131212190181253863L;
	@XmlElement
	private String revName;
	@XmlElement
	private String revDesc;
	@XmlElement
	private String orgPositionRestriction;
	
	public String getRevName(){
		return revName;
	}
	public void setRevName(String revName){
		this.revName = revName;
	}
	public String getRevDesc(){
		return revDesc;
	}
	public void setRevDesc(String revDesc){
		this.revDesc = revDesc;
	}
	public void setOrgPositionRestriction(String orgPositionRestriction) {
		this.orgPositionRestriction = orgPositionRestriction;
	}
	public String getOrgPositionRestriction() {
		return orgPositionRestriction;
	}
}
