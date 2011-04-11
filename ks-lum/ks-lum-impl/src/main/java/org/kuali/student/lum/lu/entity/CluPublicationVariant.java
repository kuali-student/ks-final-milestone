package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.entity.BaseEntity;

@Entity
@Table(name = "KSLU_CLU_PUBL_VARI", uniqueConstraints={@UniqueConstraint(columnNames={"VARI_KEY", "OWNER"})})
public class CluPublicationVariant extends BaseEntity{
	
	@Column(name="VARI_KEY")
	private String key;
	
	@Column(name="VARI_VALUE")
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "OWNER")
	private CluPublication owner;

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
