package org.kuali.student.brms.statement.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSST_REF_STMT_REL_ATTR")
public class RefStatementRelationAttribute extends Attribute<RefStatementRelation> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private RefStatementRelation owner;

	@Override
	public RefStatementRelation getOwner() {
		return owner;
	}

	@Override
	public void setOwner(RefStatementRelation owner) {
		this.owner = owner;
	}
}
