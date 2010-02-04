package org.kuali.student.brms.statement.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name = "KSSTMT_OBJECT_SUB_TYPE_ATTR")
public class ObjectSubTypeAttribute extends Attribute<ObjectSubType> {
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private ObjectSubType owner;

	@Override
	public ObjectSubType getOwner() {
		return owner;
	}

	@Override
	public void setOwner(ObjectSubType owner) {
		this.owner = owner;
	}
}
