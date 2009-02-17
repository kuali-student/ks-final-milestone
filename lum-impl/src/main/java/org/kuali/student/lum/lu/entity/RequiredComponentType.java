package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="KS_REQ_COMP_TYPE_T")
public class RequiredComponentType {
	@Id
	@Column(name = "ID")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
