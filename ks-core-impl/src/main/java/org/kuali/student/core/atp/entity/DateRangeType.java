package org.kuali.student.core.atp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSAP_DT_RANGE_TYPE")
@NamedQueries( { 
	@NamedQuery(name = "DateRangeType.findDateRangeTypesForAtpType", query = "SELECT dateRange.type FROM DateRange dateRange WHERE dateRange.atp.type.id = :atpTypeId") 
})
public class DateRangeType extends Type<DateRangeTypeAttribute> {
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<DateRangeTypeAttribute> attributes;

	public List<DateRangeTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<DateRangeTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<DateRangeTypeAttribute> attributes) {
		this.attributes = attributes;
	}
}
