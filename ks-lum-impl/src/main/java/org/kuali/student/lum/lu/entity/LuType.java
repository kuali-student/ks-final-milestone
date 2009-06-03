package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSLU_LUTYPE")
public class LuType extends Type<LuTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuTypeAttribute> attributes;

	public List<LuTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}
