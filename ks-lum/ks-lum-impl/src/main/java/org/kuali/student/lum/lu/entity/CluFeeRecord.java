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

package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.CurrencyAmount;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_CLU_FEE_REC")
public class CluFeeRecord extends MetaEntity implements
		AttributeOwner<CluFeeRecordAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "FEE_TYPE")
	private String feeType;

    @Embedded
    private CurrencyAmount currencyAmmount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<CluFeeRecordAttribute> attributes;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "KSLU_CLU_FEEREC_JN_AFFIL_ORG", joinColumns = @JoinColumn(name = "CLU_FEE_REC_ID"), inverseJoinColumns = @JoinColumn(name = "AFFIL_ORG_ID"))
	private List<AffiliatedOrg> affiliatedOrgs;

	@Override
    public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public CurrencyAmount getCurrencyAmmount() {
		return currencyAmmount;
	}

	public void setCurrencyAmmount(CurrencyAmount currencyAmmount) {
		this.currencyAmmount = currencyAmmount;
	}

	public List<CluFeeRecordAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CluFeeRecordAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<AffiliatedOrg> getAffiliatedOrgs() {
		if(null == affiliatedOrgs) {
			this.affiliatedOrgs = new ArrayList<AffiliatedOrg>();
		}
		return affiliatedOrgs;
	}

	public void setAffiliatedOrgs(List<AffiliatedOrg> affiliatedOrgs) {
		this.affiliatedOrgs = affiliatedOrgs;
	}
}
