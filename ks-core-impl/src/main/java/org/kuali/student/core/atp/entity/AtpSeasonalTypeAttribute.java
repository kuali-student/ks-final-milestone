package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_ATP_SEASONAL_TYPE_ATTR")
public class AtpSeasonalTypeAttribute extends Attribute<AtpSeasonalType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private AtpSeasonalType owner;

	@Override
	public AtpSeasonalType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(AtpSeasonalType owner) {
		this.owner = owner;
	}
}
