package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

@Entity
public class DateRangeAttribute extends Attribute<DateRange, DateRangeAttributeDef>{
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private DateRange owner;

	@Override
	public DateRange getOwner() {
		return owner;
	}

	@Override
	public void setOwner(DateRange owner) {
		this.owner = owner;
	}
}
