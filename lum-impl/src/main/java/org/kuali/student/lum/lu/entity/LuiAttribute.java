package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_LUI_ATTR")
public class LuiAttribute extends Attribute<Lui> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Lui owner;

	@Override
	public Lui getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Lui owner) {
		this.owner = owner;
	}

}
