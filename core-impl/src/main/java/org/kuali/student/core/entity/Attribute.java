package org.kuali.student.core.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

@MappedSuperclass
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"ATTR_DEF", "OWNER"})})

public abstract class Attribute<T,S extends AttributeDef> {
	@Id
	private String id;
	@ManyToOne(targetEntity=AttributeDef.class)
	@JoinColumn(name="ATTR_DEF")
	private S attrDef;
	private String value;


	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public S getAttrDef() {
		return attrDef;
	}
	public void setAttrDef(S attrDef) {
		this.attrDef = attrDef;
	}
	

	public abstract T getOwner();
	public abstract void setOwner(T owner);
	
	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
}
