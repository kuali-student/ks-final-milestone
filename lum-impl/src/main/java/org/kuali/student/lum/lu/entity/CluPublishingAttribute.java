package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;
@Entity
@Table(name="KS_CLU_PUBLISHING_ATTR_T")
public class CluPublishingAttribute extends Attribute<CluPublishing> {
	
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluPublishing owner;

	@Override
	public CluPublishing getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluPublishing owner) {
		this.owner = owner;
	}
	


}
