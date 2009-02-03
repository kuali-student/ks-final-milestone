package org.kuali.student.core.organization.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgOrgRelationTypeInfo extends TypeInfo implements Serializable {
	private static final long serialVersionUID = 8661530450861801859L;

	@XmlElement
	private String revName;
	@XmlElement
	private String revDesc;
	@XmlElement
	private String orgHierarchyKey;
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
	public String getOrgHierarchyKey(){
		return orgHierarchyKey;
	}
	public void setOrgHierarchyKey(String orgHierarchyKey){
		this.orgHierarchyKey = orgHierarchyKey;
	}
}
