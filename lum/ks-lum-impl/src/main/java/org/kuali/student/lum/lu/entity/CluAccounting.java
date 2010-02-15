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
package org.kuali.student.lum.lu.entity;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KSLU_CLU_ACCT")
public class CluAccounting implements AttributeOwner<CluAccountingAttribute> {

    @Id
    @Column(name = "ID")
    private String id;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluAccountingAttribute> attributes;

	@ManyToMany
	@JoinTable(name = "KSLU_CLU_ACCT_JN_AFFIL_ORG", joinColumns = @JoinColumn(name = "CLU_ACCT_ID"), inverseJoinColumns = @JoinColumn(name = "AFFIL_ORG_ID"))
	private List<AffiliatedOrg> affiliatedOrgs;
	
	@PrePersist
	public  void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}
	
    public List<CluAccountingAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CluAccountingAttribute>();
        }
        return attributes;
    }

    public void setAttributes(List<CluAccountingAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public List<AffiliatedOrg> getAffiliatedOrgs() {
		if(null == this.affiliatedOrgs) {
			this.affiliatedOrgs = new ArrayList<AffiliatedOrg>();
		}
		
		return this.affiliatedOrgs;
	}

	public void setAffiliatedOrgs(List<AffiliatedOrg> affiliatedOrgs) {
		this.affiliatedOrgs = affiliatedOrgs;
	}        
}
