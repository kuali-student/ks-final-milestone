package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_ATTR")
public class CluAttribute extends Attribute<Clu> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Clu owner;

	@Override
	public Clu getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Clu owner) {
		this.owner = owner;
	}
}
