package org.kuali.student.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Type<T extends Attribute<?>> implements AttributeOwner<T> {
	@Id
	@Column(name = "TYPE_KEY")
	private String key;

	private String name;
	@Column(name = "TYPE_DESC",length=2000)//TODO what is a good number for these long descriptions?
	private String desc;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	public String getId() {
		return key;
	}

	public void setId(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
