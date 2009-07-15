package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_ADMIN_ORG_ATTR")
public class CluAdminOrgAttribute extends Attribute<CluAdminOrg> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluAdminOrg owner;

	@Override
	public CluAdminOrg getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluAdminOrg owner) {
		this.owner = owner;
	}
	
	

}
