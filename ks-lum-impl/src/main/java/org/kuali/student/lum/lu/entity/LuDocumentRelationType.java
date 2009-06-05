package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSLU_LU_DOC_RELTN_TYPE")
public class LuDocumentRelationType extends
		Type<LuDocumentRelationTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
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
