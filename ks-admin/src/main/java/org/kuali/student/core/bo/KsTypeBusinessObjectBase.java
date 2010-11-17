package org.kuali.student.core.bo;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class KsTypeBusinessObjectBase extends KsInactivatableFromToBase implements KsTypeBusinessObject {
	
    private static final long serialVersionUID = 1L;


	@Column(name = "NAME")
	private String name;

	@Column(name = "TYPE_DESC")
	private String description;

	public KsTypeBusinessObjectBase() {
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
