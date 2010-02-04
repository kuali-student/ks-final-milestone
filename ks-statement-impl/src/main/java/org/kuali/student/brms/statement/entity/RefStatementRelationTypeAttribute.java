package org.kuali.student.brms.statement.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSSTMT_REF_STMT_REL_TYPE_ATTR")
public class RefStatementRelationTypeAttribute extends Attribute<RefStatementRelationType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private RefStatementRelationType owner;

	@Override
	public RefStatementRelationType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(RefStatementRelationType owner) {
		this.owner = owner;
	}
}
