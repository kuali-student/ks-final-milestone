package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSLU_CLU_PUBL_VARI", uniqueConstraints={@UniqueConstraint(columnNames={"VARI_KEY", "OWNER"})})
public class CluPublicationVariant {
	@Id
	private String id;

	@Column(name="VARI_KEY")
	private String key;
	
	@Column(name="VARI_VALUE")
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluPublication owner;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CluPublication getOwner() {
		return owner;
	}

	public void setOwner(CluPublication owner) {
		this.owner = owner;
	}
}
