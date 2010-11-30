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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.BaseEntity;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */

@Entity
@Table(name = "KSLU_CLU_ADMIN_ORG")

public class CluAdminOrg extends BaseEntity implements AttributeOwner<CluAdminOrgAttribute>  {
    
    @Column(name = "ORG_ID")
    private String orgId;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "IS_PR")
    private boolean isPrimary=false;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluAdminOrgAttribute> attributes;

    @ManyToOne
    @JoinColumn(name="CLU_ID")
    private Clu clu;
    
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    
    public List<CluAdminOrgAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CluAdminOrgAttribute> attributes) {
        this.attributes = attributes;
    }
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

}
