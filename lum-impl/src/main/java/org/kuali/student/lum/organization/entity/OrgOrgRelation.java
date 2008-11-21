package org.kuali.student.lum.organization.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.MetaEntity;

@Entity
public class OrgOrgRelation extends MetaEntity {
	@Id
	private String id;
	
	@ManyToOne
	@JoinColumn(name="org")
	private Org org;

	@ManyToOne
	@JoinColumn(name="relatedOrg")
	private Org relatedOrg;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	@ManyToOne
	@JoinColumn(name="OrgOrgRelationType")
	private OrgOrgRelationType type;
	
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Org getRelatedOrg() {
		return relatedOrg;
	}

	public void setRelatedOrg(Org relatedOrg) {
		this.relatedOrg = relatedOrg;
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

	public OrgOrgRelationType getType() {
		return type;
	}

	public void setType(OrgOrgRelationType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
