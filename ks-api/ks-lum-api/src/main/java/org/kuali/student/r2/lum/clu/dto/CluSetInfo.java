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

package org.kuali.student.r2.lum.clu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.clu.infc.CluSet;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluSetInfo", propOrder = {"id", "descr", "stateKey", "typeKey", "name", "membershipQuery", "adminOrg", "isReusable", "isReferenceable", "cluSetIds", "cluIds",
        "effectiveDate", "expirationDate", "meta", "attributes" , "_futureElements" }) 
public class CluSetInfo extends IdEntityInfo implements Serializable, CluSet {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private MembershipQueryInfo membershipQuery;

    @XmlElement
    private String adminOrg;

    @XmlElement
    private Boolean isReusable;

    @XmlElement
    private Boolean isReferenceable;

    @XmlElement
    private List<String> cluSetIds;

    @XmlElement
    private List<String> cluIds;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CluSetInfo() {

    }

    public CluSetInfo(CluSet cluSet) {
        super(cluSet);
        if (null != cluSet) {
            this.membershipQuery = (null != cluSet.getMembershipQuery()) ? new MembershipQueryInfo(cluSet.getMembershipQuery()) : null;
            this.adminOrg = cluSet.getAdminOrg();
            this.isReusable = cluSet.getIsReusable();
            this.isReferenceable = cluSet.getIsReferenceable();
            this.cluSetIds = new ArrayList<String>(cluSet.getCluSetIds());
            this.cluIds = new ArrayList<String>(cluSet.getCluIds());
            this.effectiveDate = (null != cluSet.getEffectiveDate()) ? new Date(cluSet.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != cluSet.getExpirationDate()) ? new Date(cluSet.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public List<String> getCluSetIds() {
        if (cluSetIds == null) {
            cluSetIds = new ArrayList<String>();
        }
        return cluSetIds;
    }

    public void setCluSetIds(List<String> cluSetIds) {
        this.cluSetIds = cluSetIds;
    }

    @Override
    public List<String> getCluIds() {
        if (cluIds == null) {
            cluIds = new ArrayList<String>();
        }
        return cluIds;
    }

    public void setCluIds(List<String> cluIds) {
        this.cluIds = cluIds;
    }

    @Override
    public MembershipQueryInfo getMembershipQuery() {
        return membershipQuery;
    }

    public void setMembershipQuery(MembershipQueryInfo membershipQuery) {
        this.membershipQuery = membershipQuery;
    }

    @Override
    public String getAdminOrg() {
        return adminOrg;
    }

    public void setAdminOrg(String adminOrg) {
        this.adminOrg = adminOrg;
    }

    @Override
    public Boolean getIsReusable() {
        return isReusable;
    }

    public void setIsReusable(Boolean isReusable) {
        this.isReusable = isReusable;
    }

    @Override
    public Boolean getIsReferenceable() {
        return isReferenceable;
    }

    public void setIsReferenceable(Boolean isReferenceable) {
        this.isReferenceable = isReferenceable;
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
