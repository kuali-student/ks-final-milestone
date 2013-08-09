/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.clu.infc.AffiliatedOrg;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AffiliatedOrgInfo", propOrder = {"id", "meta", "attributes", "orgId", "percentage", "effectiveDate", "expirationDate", "typeKey", "stateKey" , "_futureElements" }) 
public class AffiliatedOrgInfo extends IdNamelessEntityInfo implements AffiliatedOrg, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String orgId;
    @XmlElement
    private Long percentage;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public AffiliatedOrgInfo() {
    }

    public AffiliatedOrgInfo(AffiliatedOrg affiliatedOrg) {

        super(affiliatedOrg);
        this.orgId = affiliatedOrg.getOrgId();
        this.percentage = affiliatedOrg.getPercentage();
        this.effectiveDate = (affiliatedOrg.getEffectiveDate() != null)
                ? new Date(affiliatedOrg.getEffectiveDate().getTime())
                : null;

        this.expirationDate = (affiliatedOrg.getExpirationDate() != null)
                ? new Date(affiliatedOrg.getExpirationDate().getTime())
                : null;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}