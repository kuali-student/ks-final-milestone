package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KS_ORG_ORG_REL_TYPE_T")
public class OrgOrgRelationType implements
		AttributeOwner<OrgOrgRelationTypeAttribute> {
	@Id
	@Column(name = "OORT_KEY")
	private String key;

	@Column(name = "OORT_NAME")
	private String name;

	@Column(name = "OORT_DESC",length=2000)//TODO what is a good number for these long descriptions?
	private String desc;

	@Column(name = "REV_NAME")
	private String revName;

	@Column(name = "REV_DESC")
	private String revDesc;

	@ManyToOne
	@JoinColumn(name = "ORG_HIERARCHY")
	private OrgHierarchy orgHierarchy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgOrgRelationTypeAttribute> attributes;

	@Override
	public List<OrgOrgRelationTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<OrgOrgRelationTypeAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgOrgRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRevName() {
		return revName;
	}

	public void setRevName(String revName) {
		this.revName = revName;
	}

	public String getRevDesc() {
		return revDesc;
	}

	public void setRevDesc(String revDesc) {
		this.revDesc = revDesc;
	}

	public OrgHierarchy getOrgHierarchy() {
		return orgHierarchy;
	}

	public void setOrgHierarchy(OrgHierarchy orgHierarchy) {
		this.orgHierarchy = orgHierarchy;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
