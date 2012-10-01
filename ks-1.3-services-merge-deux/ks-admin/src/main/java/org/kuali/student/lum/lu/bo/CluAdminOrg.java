package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluAdminOrg extends KsBusinessObjectBase {

	private static final long serialVersionUID = -7668691777254348964L;
	
	@Column(name = "ORG_ID")
    private String orgId;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "IS_PR")
    private boolean primary=false;

    private String cluId;

    
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

}
