package org.kuali.student.lum.lo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_LOLO_ALLOWED_RELTN_TYPE")
public class AllowedLoLoRelationType extends MetaEntity {
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "LOLO_RELTN_TYPE_ID")
	private String relationTypeId;
	
	@Column(name = "LO_TYPE_ID")
	private String loTypeId;
	
	@Column(name = "LO_REL_TYPE_ID")
	private String relatedTypeId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	public String getRelationTypeId() {
		return relationTypeId;
	}

	public void setRelationTypeId(String relationTypeId) {
		this.relationTypeId = relationTypeId;
	}

	public String getLoTypeId() {
		return loTypeId;
	}

	public void setLoTypeId(String loTypeId) {
		this.loTypeId = loTypeId;
	}

	public String getRelatedTypeId() {
		return relatedTypeId;
	}

	public void setRelatedTypeId(String relatedTypeId) {
		this.relatedTypeId = relatedTypeId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}
}
