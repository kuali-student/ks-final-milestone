package org.kuali.student.r2.core.class1.organization.model;

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

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.core.organization.entity.OrgAttribute;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgType;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.infc.Org;

public class OrgEntity {

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
    
    public OrgEntity() {
    }

    public OrgEntity(Org org) {
        //super(org);
        
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

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
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

    public List<OrgAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<OrgAttribute> attributes) {
        this.attributes = attributes;
    }

    public OrgType getType() {
        return type;
    }

    public void setType(OrgType type) {
        this.type = type;
    }

    public List<OrgPersonRelationType> getOrgPersonRelationTypes() {
        return orgPersonRelationTypes;
    }

    public void setOrgPersonRelationTypes(List<OrgPersonRelationType> orgPersonRelationTypes) {
        this.orgPersonRelationTypes = orgPersonRelationTypes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }   
    
    public OrgInfo toDto() {
        OrgInfo org = new OrgInfo();
        
        return org;
    }
    
}
