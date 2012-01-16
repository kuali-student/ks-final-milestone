package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsMetaInactivatableFromToBase;

public class CluAccreditation extends KsMetaInactivatableFromToBase {

	private static final long serialVersionUID = -5120619224420495466L;
	
	@Column(name = "ORG_ID")
	private String orgId;

	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
