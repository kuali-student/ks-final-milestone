package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.common.entity.Attribute;

@Entity
@Table(name = "KSLU_CLU_PUBL_TYPE_ATTR")
public class CluPublicationTypeAttribute extends Attribute<CluPublicationType> {

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluPublicationType owner;

	@Override
	public CluPublicationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(CluPublicationType owner) {
		this.owner = owner;
	}

}
