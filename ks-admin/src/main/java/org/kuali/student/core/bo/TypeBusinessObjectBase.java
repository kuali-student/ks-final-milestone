package org.kuali.student.core.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TypeBusinessObjectBase extends KsInactivatableFromToBase implements TypeBusinessObject {
	
    private static final long serialVersionUID = 1L;


	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE_DESC")
	private String description;

	public TypeBusinessObjectBase() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
