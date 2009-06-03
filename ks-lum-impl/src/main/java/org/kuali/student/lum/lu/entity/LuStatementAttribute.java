package org.kuali.student.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSLU_STMT_ATTR")
public class LuStatementAttribute extends Attribute<LuStatement> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private LuStatement owner;

	@Override
	public LuStatement getOwner() {
		return owner;
	}

	@Override
	public void setOwner(LuStatement owner) {
		this.owner = owner;
	}

}
