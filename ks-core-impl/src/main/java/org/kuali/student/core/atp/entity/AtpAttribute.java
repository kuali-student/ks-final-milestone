package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_ATP_ATTR")
public class AtpAttribute extends Attribute<Atp> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Atp owner;

	@Override
	public Atp getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Atp owner) {
		this.owner = owner;
	}
}
