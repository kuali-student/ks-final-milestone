package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_REQ_COM_TYPE_ATTR")
public class ReqComponentTypeAttribute extends Attribute<ReqComponentType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private ReqComponentType owner;

	@Override
	public ReqComponentType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(ReqComponentType owner) {
		this.owner = owner;
	}

}
