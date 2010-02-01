package org.kuali.student.brms.statement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSSTMT_NL_USAGE_TYPE")
public class NlUsageType extends Type<NlUsageTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<NlUsageTypeAttribute> attributes = new ArrayList<NlUsageTypeAttribute>();
	
	@Override
	public List<NlUsageTypeAttribute> getAttributes() {
		return this.attributes;
	}

	@Override
	public void setAttributes(List<NlUsageTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "NlUsageType[id=" + getId() + "]";
	}

}
