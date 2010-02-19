/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.organization.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name="KSOR_ORG_HIRCHY")
public class OrgHierarchy implements AttributeOwner<OrgHierarchyAttribute>{
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String name; 
	
	@Column(name = "DESCR",length=2000)//TODO what is a good number for these long descriptions?
	private String descr; 

	@ManyToOne
    @JoinColumn(name="ROOT_ORG")
	private Org rootOrg; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;  

	@ManyToMany
	@JoinTable(
	        name="KSOR_ORG_HIRCHY_JN_ORG_TYPE",
	        joinColumns=
	            @JoinColumn(name="ORG_HIRCHY_ID", referencedColumnName="ID"),
	        inverseJoinColumns=
	            @JoinColumn(name="ORG_TYPE_ID", referencedColumnName="TYPE_KEY")
	    )
	private List<OrgType> organizationTypes;
	
	@OneToMany(mappedBy = "orgHierarchy")
	private List<OrgOrgRelationType> orgOrgRelationTypes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgHierarchyAttribute> attributes;
	
	@Override
	public List<OrgHierarchyAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgHierarchyAttribute> attributes) {
		this.attributes=attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Org getRootOrg() {
		return rootOrg;
	}

	public void setRootOrg(Org rootOrg) {
		this.rootOrg = rootOrg;
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

	public List<OrgType> getOrganizationTypes() {
		return organizationTypes;
	}

	public void setOrganizationTypes(List<OrgType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}

	public List<OrgOrgRelationType> getOrgOrgRelationTypes() {
		return orgOrgRelationTypes;
	}

	public void setOrgOrgRelationTypes(List<OrgOrgRelationType> orgOrgRelationTypes) {
		this.orgOrgRelationTypes = orgOrgRelationTypes;
	}

}
