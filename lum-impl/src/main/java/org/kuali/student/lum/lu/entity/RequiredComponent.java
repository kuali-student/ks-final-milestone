package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="KS_REQ_COMP_T")
public class RequiredComponent {
	@Id
	@Column(name = "ID")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="REQ_COMP_TYPE_ID")
	private RequiredComponentType requiredComponentType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RequiredComponentType getRequiredComponentType() {
		return requiredComponentType;
	}

	public void setRequiredComponentType(RequiredComponentType requiredComponentType) {
		this.requiredComponentType = requiredComponentType;
	}
	
}
