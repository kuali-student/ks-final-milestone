package org.kuali.student.core.atp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSAP_ATP_DUR_TYPE")
public class AtpDurationType extends Type<AtpDurationTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<AtpDurationTypeAttribute> attributes;

	public List<AtpDurationTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<AtpDurationTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<AtpDurationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}
