package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LU_DOC_RELTN_TYPE_ATTR")
public class LuDocumentRelationTypeAttribute extends
		Attribute<LuDocumentRelationType> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuDocumentRelationType owner;

	@Override
	public LuDocumentRelationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuDocumentRelationType owner) {
		this.owner = owner;
	}

}
