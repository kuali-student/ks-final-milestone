/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.KSEntityConstants;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name="KSOR_ORG")
@NamedQueries({
	@NamedQuery(name="Org.getOrganizationsByIdList", query="SELECT o FROM Org o WHERE o.id IN (:orgIdList)")
})
public class Org extends MetaEntity implements AttributeOwner<OrgAttribute>{
	
	@Id
	@Column(name = "ID")
	private String id; 
	
	@Column(name = "LNG_NAME")
	private String longName; 
	
	@Column(name = "SHRT_NAME")
	private String shortName; 
	
	@Column(name = "SHRT_DESCR",length=KSEntityConstants.SHORT_TEXT_LENGTH)
	private String shortDesc; 
	
	@Column(name = "LNG_DESCR",length=KSEntityConstants.LONG_TEXT_LENGTH)
	private String longDesc; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate; 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgAttribute> attributes;
	
	@ManyToOne
    @JoinColumn(name="TYPE")
	private OrgType type; 
	
	@ManyToMany
	@JoinTable(
	        name="KSOR_ORG_JN_ORG_PERS_REL_TYPE",
	        joinColumns=
	            @JoinColumn(name="ORG_ID", referencedColumnName="ID"),
	        inverseJoinColumns=
	            @JoinColumn(name="ORG_PERS_RELTN_TYPE_ID", referencedColumnName="TYPE_KEY")
	    )
	private List<OrgPersonRelationType> orgPersonRelationTypes;

	@Column(name = "ST")
	private String state;
	
	/**
	 * AutoGenerate the Id
	 */
	@Override
	public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	@Override
	public List<OrgAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgAttribute> attributes) {
		this.attributes=attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public OrgType getType() {
		return type;
	}

	public void setType(OrgType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	} 
	
	public List<OrgPersonRelationType> getOrgPersonRelationTypes() {
		return orgPersonRelationTypes;
	}

	public void setOrgPersonRelationTypes(List<OrgPersonRelationType> orgPersonRelationTypes) {
		this.orgPersonRelationTypes = orgPersonRelationTypes;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public String getLongDesc() {
		return longDesc;
	}
}
