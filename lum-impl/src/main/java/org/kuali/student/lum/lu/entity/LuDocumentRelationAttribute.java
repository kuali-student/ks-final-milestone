package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KS_LU_DOC_REL_ATTR_T")
public class LuDocumentRelationAttribute extends Attribute<LuDocumentRelation> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuDocumentRelation owner;

	@Override
	public LuDocumentRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuDocumentRelation owner) {
		this.owner = owner;
	}

}