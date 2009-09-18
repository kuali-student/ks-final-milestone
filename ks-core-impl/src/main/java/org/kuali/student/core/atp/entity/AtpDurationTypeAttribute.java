package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_ATP_DUR_TYPE_ATTR")
public class AtpDurationTypeAttribute extends Attribute<AtpDurationType> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private AtpDurationType owner;

	@Override
	public AtpDurationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(AtpDurationType owner) {
		this.owner = owner;
	}

}
