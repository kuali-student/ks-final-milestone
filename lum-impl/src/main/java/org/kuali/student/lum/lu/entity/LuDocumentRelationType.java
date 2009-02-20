package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.kuali.student.core.entity.Type;

@Entity
public class LuDocumentRelationType extends
		Type<LuDocumentRelationTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
	private List<LuDocumentRelationTypeAttribute> attributes;

	public List<LuDocumentRelationTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuDocumentRelationTypeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuDocumentRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}
