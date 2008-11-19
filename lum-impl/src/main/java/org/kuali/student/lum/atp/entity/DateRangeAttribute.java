package org.kuali.student.lum.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

@Entity
public class DateRangeAttribute extends Attribute{
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private DateRange owner;

	public DateRange getOwner() {
		return owner;
	}

	public void setOwner(DateRange owner) {
		this.owner = owner;
	}
}
