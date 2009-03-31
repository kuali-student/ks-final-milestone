package org.kuali.student.core.atp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSAP_DT_RANGE_TYPE_ATTR")
public class DateRangeTypeAttribute extends Attribute<DateRangeType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private DateRangeType owner;

	@Override
	public DateRangeType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(DateRangeType owner) {
		this.owner = owner;
	}
}
