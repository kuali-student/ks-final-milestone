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

package org.kuali.student.r2.lum.lu.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.BaseEntity;

@Entity
@Table(name = "KSLU_CLU_IDENT")
public class CluIdentifier extends BaseEntity implements AttributeOwner<CluIdentifierAttribute> {

    @Column(name = "CD")
    private String code;

    @Column(name = "SHRT_NAME")
    private String shortName;

    @Column(name = "LNG_NAME")
    private String longName;

    @Column(name = "LVL")
    private String level;

    @Column(name = "DIVISION")
    private String division;

    @Column(name = "VARTN")
    private String variation;

    @Column(name = "SUFX_CD")
    private String suffixCode;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ST")
    private String state;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluIdentifierAttribute> attributes;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuffixCode() {
        return suffixCode;
    }

    public void setSuffixCode(String suffixCode) {
        this.suffixCode = suffixCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<CluIdentifierAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CluIdentifierAttribute> attributes) {
        this.attributes = attributes;
    }        
}
