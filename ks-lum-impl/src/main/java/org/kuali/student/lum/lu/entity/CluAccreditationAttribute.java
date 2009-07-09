package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_ACCRED_ATTR")
public class CluAccreditationAttribute extends Attribute<CluAccreditation> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluAccreditation owner;

	@Override
	public CluAccreditation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluAccreditation owner) {
		this.owner = owner;
	}
}
