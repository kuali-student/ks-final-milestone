package org.kuali.student.core.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

@MappedSuperclass
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"NAME", "OWNER"})})

public abstract class Attribute<T extends AttributeOwner<?>> {
	@Id
	private String id;

	@Column(name="ATTR_NAME")
	private String name;
	
	@Column(name="ATTR_VALUE")
	private String value;

	public abstract T getOwner();
	public abstract void setOwner(T owner);

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

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
