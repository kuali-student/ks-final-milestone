package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_SET_ATTR")
public class CluSetAttribute extends Attribute<CluSet> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluSet owner;

	@Override
	public CluSet getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluSet owner) {
		this.owner = owner;
	}
}