package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_CLUCLU_RELTN_ATTR")
public class CluCluRelationAttribute extends Attribute<CluCluRelation> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluCluRelation owner;

	@Override
	public CluCluRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluCluRelation owner) {
		this.owner = owner;
	}
}
