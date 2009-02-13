package org.kuali.student.core.organization.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgHierarchyInfo extends TypeInfo implements Serializable {
	private static final long serialVersionUID = -7132469408588613318L;

	@XmlElement
	private String rootOrgId;
	public String getRootOrgId(){
		return rootOrgId;
	}
	public void setRootOrgId(String rootOrgId){
		this.rootOrgId = rootOrgId;
	}
}
