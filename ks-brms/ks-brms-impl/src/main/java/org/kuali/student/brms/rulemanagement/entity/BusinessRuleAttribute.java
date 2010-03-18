package org.kuali.student.brms.rulemanagement.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSBRMS_BUS_RULE_ATTR")
public class BusinessRuleAttribute extends Attribute<BusinessRule> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private BusinessRule owner;

	@Override
	public BusinessRule getOwner() {
		return owner;
	}

	@Override
	public void setOwner(BusinessRule owner) {
		this.owner = owner;
	}

}
