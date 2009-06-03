package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_FEE_ATTR")
public class CluFeeAttribute extends Attribute<CluFee> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluFee owner;

	@Override
	public CluFee getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluFee owner) {
		this.owner = owner;
	}
}
