package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "KSLU_RSRC", uniqueConstraints = @UniqueConstraint(columnNames = {
		"RSRC_TYPE_ID", "CLU_ID" }))
public class Resource {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "RSRC_TYPE_ID")
	private String resourceTypeId;

	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(String resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}
}
