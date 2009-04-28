package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_STMT_TYPE_ATTR")
public class LuStatementTypeAttribute extends Attribute<LuStatementType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuStatementType owner;

	@Override
	public LuStatementType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuStatementType owner) {
		this.owner = owner;
	}

}
