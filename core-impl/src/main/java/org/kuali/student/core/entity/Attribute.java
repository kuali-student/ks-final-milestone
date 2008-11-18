package org.kuali.student.core.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.kuali.student.common.util.UUIDHelper;

@MappedSuperclass
public abstract class Attribute {
	@Id
	private String id;
	@ManyToOne(targetEntity=AttributeDef.class)
	@JoinColumn(name="ATTR_DEF")
	private AttributeDef attrDef;
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

	public AttributeDef getAttrDef() {
		return attrDef;
	}
	public void setAttrDef(AttributeDef attrDef) {
		this.attrDef = attrDef;
	}
	
	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
}
