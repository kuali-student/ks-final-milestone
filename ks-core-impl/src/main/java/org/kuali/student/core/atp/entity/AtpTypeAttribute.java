package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_ATP_TYPE_ATTR")
public class AtpTypeAttribute extends Attribute<AtpType> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private AtpType owner;

	@Override
	public AtpType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(AtpType owner) {
		this.owner = owner;
	}
}
