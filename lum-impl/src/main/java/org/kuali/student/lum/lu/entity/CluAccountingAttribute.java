package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_ACCT_ATTR")
public class CluAccountingAttribute extends Attribute<CluAccounting> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluAccounting owner;

	@Override
	public CluAccounting getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluAccounting owner) {
		this.owner = owner;
	}
}
