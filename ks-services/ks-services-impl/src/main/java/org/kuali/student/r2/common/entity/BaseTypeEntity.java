package org.kuali.student.r2.common.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.BaseEntity;
import org.kuali.student.core.entity.KSEntityConstants;

@MappedSuperclass
@AttributeOverrides({
@AttributeOverride(name="id", column=@Column(name="TYPE_KEY"))})
@Table(name = "KSLP_LPR_TYPE")
public class BaseTypeEntity extends BaseEntity {
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TYPE_DESC",length=KSEntityConstants.LONG_TEXT_LENGTH)
	private String descr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
}
