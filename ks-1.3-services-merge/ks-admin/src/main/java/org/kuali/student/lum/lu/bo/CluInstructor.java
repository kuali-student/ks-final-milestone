package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluInstructor extends KsBusinessObjectBase {

	private static final long serialVersionUID = -1329580309570854394L;

	@Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "PERS_ID")
    private String personId;

    
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

}
