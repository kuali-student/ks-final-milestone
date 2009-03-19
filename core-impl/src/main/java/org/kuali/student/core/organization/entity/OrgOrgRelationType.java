package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSOR_ORG_ORG_RELTN_TYPE")
@NamedQueries( {
		@NamedQuery(name = "OrgOrgRelationType.getOrgOrgRelationTypesForOrgHierarchy", query = "SELECT oort FROM OrgOrgRelationType oort WHERE oort.orgHierarchy.id = :orgHierarchy"),
		@NamedQuery(name = "OrgOrgRelationType.getOrgOrgRelationTypesForOrgType", query = "SELECT DISTINCT oort FROM OrgOrgRelation oor JOIN oor.org org JOIN oor.type oort WHERE org.type.id = :orgTypeKey") })
public class OrgOrgRelationType extends Type<OrgOrgRelationTypeAttribute> {
    //implements AttributeOwner<OrgOrgRelationTypeAttribute> {
	/*@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCR", length = 2000)
	// TODO what is a good number for these long descriptions?
	private String desc;*/

	@Column(name = "REV_NAME")
	private String revName;

	@Column(name = "REV_DESCR")
	private String revDesc;

	@ManyToOne
	@JoinColumn(name = "ORG_HIRCHY")
	private OrgHierarchy orgHierarchy;

	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;*/

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

/*	public String getName() {
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
	}*/

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

/*	public Date getEffectiveDate() {
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
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

}
