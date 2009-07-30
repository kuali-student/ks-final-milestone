package org.kuali.student.core.organization.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgTreeInfo implements Serializable {

	private static final long serialVersionUID = 7315439355073246895L;

	@XmlAttribute
	private String orgId;
	@XmlAttribute
	private String parentId;
	@XmlAttribute
	private String displayName;
	
	public OrgTreeInfo() {
		super();
	}
	public OrgTreeInfo(String orgId, String parentId, String displayName) {
		super();
		this.orgId = orgId;
		this.parentId = parentId;
		this.displayName = displayName;
	}	
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
