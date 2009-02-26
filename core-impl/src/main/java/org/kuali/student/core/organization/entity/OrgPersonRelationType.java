package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KS_ORG_PERSON_REL_TYPE_T")
@NamedQueries( {
		@NamedQuery(name = "OrgPersonRelationType.getOrgPersonRelationTypesForOrgType", query = "SELECT oprt FROM OrgPersonRelationType oprt JOIN oprt.organizationTypes ot WHERE ot.id = :orgTypeKey"),
		@NamedQuery(name = "OrgPersonRelationType.getPersonIdsForOrgByRelationType", query = "SELECT DISTINCT opr.personId FROM OrgPersonRelation opr JOIN opr.org o JOIN opr.orgPersonRelationType t WHERE o.id = :orgId AND t.id = :orgPersonRelationTypeKey"),
		@NamedQuery(name = "OrgPersonRelationType.hasOrgPersonRelation", query = "SELECT COUNT(oprt) FROM OrgPersonRelationType oprt JOIN oprt.orgPersonRelations relations JOIN oprt.organizations orgs WHERE relations.personId = :personId AND orgs.id = :orgId AND oprt.id = :orgPersonRelationTypeKey") })
public class OrgPersonRelationType implements
		AttributeOwner<OrgPersonRelationTypeAttribute> {
	/*
	 * Assuming that that we don't need Attributes on this type
	 */
	@Id
	@Column(name = "OPRT_KEY")
	private String id;

	@Column(name = "OPRT_NAME")
	private String name;

	@Column(name = "OPRT_DESC", length = 2000)
	private String desc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
	private List<OrgPersonRelationTypeAttribute> attributes;

	@ManyToMany(mappedBy = "orgPersonRelationTypes")
	private List<Org> organizations;

	@ManyToMany(mappedBy = "orgPersonRelationTypes")
	private List<OrgType> organizationTypes;

	@OneToMany(mappedBy = "orgPersonRelationType")
	private List<OrgPersonRelation> orgPersonRelations;

	@Override
	public List<OrgPersonRelationTypeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<OrgPersonRelationTypeAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgPersonRelationTypeAttribute> attributes) {
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
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Org> getOrganizations() {
		if (null == organizations) {
			organizations = new ArrayList<Org>();
		}
		return organizations;
	}

	public void setOrganizations(List<Org> organizations) {
		this.organizations = organizations;
	}

	public void setOrgPersonRelations(List<OrgPersonRelation> orgPersonRelations) {
		this.orgPersonRelations = orgPersonRelations;
	}

	public List<OrgPersonRelation> getOrgPersonRelations() {
		return orgPersonRelations;
	}

	public void setOrganizationTypes(List<OrgType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}

	public List<OrgType> getOrganizationTypes() {
		return organizationTypes;
	}
}
