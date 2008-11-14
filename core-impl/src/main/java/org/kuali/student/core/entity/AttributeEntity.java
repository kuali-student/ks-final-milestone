package org.kuali.student.core.entity;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class AttributeEntity<T> {
	@Id
	String id;
	
	@ManyToOne
	T owner;
	String name;
	String value;
	public T getOwner() {
		return owner;
	}
	public void setOwner(T owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
}
