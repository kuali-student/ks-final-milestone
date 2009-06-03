package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_DT_RANGE_ATTR")
public class DateRangeAttribute extends Attribute<DateRange> {
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
