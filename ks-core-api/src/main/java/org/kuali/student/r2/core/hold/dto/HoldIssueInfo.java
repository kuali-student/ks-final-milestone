/*
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
package org.kuali.student.r2.core.hold.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.xerces.impl.dtd.XMLElementDecl;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.hold.infc.HoldIssue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "organizationId", "isHoldIssueTermAndDateBased", "isHoldIssueDateBasedOnly",
        "firstAppliedDate", "lastAppliedDate","firstApplicationTermId", "lastApplicationTermId",
        "maintainHistoryOfApplicationOfHold","holdCode",
        "meta",
        "attributes", "_futureElements" })
public class HoldIssueInfo extends IdEntityInfo implements HoldIssue, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String organizationId;
    @XmlElement
    private Boolean isHoldIssueTermAndDateBased;
    @XmlElement
    private Boolean isHoldIssueDateBasedOnly;
    @XmlElement
    private Date firstAppliedDate;
    @XmlElement
    private Date lastAppliedDate;
    @XmlElement
    private String firstApplicationTermId;
    @XmlElement
    private String lastApplicationTermId;
    @XmlElement
    private Boolean maintainHistoryOfApplicationOfHold;
    @XmlElement
    private String holdCode;
    @XmlAnyElement
    private List<Object> _futureElements;

    public HoldIssueInfo() {
    }

    /**
     * Constructs a new IssueInfo from another Issue.
     *
     * @param issue the Issue to copy
     */
    public HoldIssueInfo(HoldIssue issue) {
        super(issue);
        if (null != issue) {
            this.organizationId = issue.getOrganizationId();
            this.isHoldIssueTermAndDateBased = issue.getIsHoldIssueTermAndDateBased();
            this.isHoldIssueDateBasedOnly = issue.getIsHoldIssueDateBasedOnly();
            this.firstAppliedDate = issue.getFirstAppliedDate();
            this.lastAppliedDate = issue.getLastAppliedDate();
            this.firstApplicationTermId = issue.getFirstApplicationTermId();
            this.lastApplicationTermId = issue.getLastApplicationTermId();
            this.maintainHistoryOfApplicationOfHold = issue.getMaintainHistoryOfApplicationOfHold();
            this.holdCode = issue.getHoldCode();
        }
    }

    @Override
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String orgId) {
        this.organizationId = orgId;
    }

    public Boolean getIsHoldIssueTermAndDateBased() {
        return isHoldIssueTermAndDateBased;
    }

    public void setIsHoldIssueTermAndDateBased(Boolean holdIssueTermAndDateBased) {
        isHoldIssueTermAndDateBased = holdIssueTermAndDateBased;
    }

    public Boolean getIsHoldIssueDateBasedOnly() {
        return isHoldIssueDateBasedOnly;
    }

    public void setIsHoldIssueDateBasedOnly(Boolean holdIssueDateBasedOnly) {
        isHoldIssueDateBasedOnly = holdIssueDateBasedOnly;
    }

    public Date getFirstAppliedDate() {
        return firstAppliedDate;
    }

    public void setFirstAppliedDate(Date firstAppliedDate) {
        this.firstAppliedDate = firstAppliedDate;
    }

    public Date getLastAppliedDate() {
        return lastAppliedDate;
    }

    public void setLastAppliedDate(Date lastAppliedDate) {
        this.lastAppliedDate = lastAppliedDate;
    }

    public String getFirstApplicationTermId() {
        return firstApplicationTermId;
    }

    public void setFirstApplicationTermId(String firstApplicationTermId) {
        this.firstApplicationTermId = firstApplicationTermId;
    }

    public String getLastApplicationTermId() {
        return lastApplicationTermId;
    }

    public void setLastApplicationTermId(String lastApplicationTermId) {
        this.lastApplicationTermId = lastApplicationTermId;
    }

    public Boolean getMaintainHistoryOfApplicationOfHold() {
        return maintainHistoryOfApplicationOfHold;
    }

    public void setMaintainHistoryOfApplicationOfHold(Boolean maintainHistoryOfApplicationOfHold) {
        this.maintainHistoryOfApplicationOfHold = maintainHistoryOfApplicationOfHold;
    }

    public String getHoldCode() {
        return holdCode;
    }

    public void setHoldCode(String holdCode) {
        this.holdCode = holdCode;
    }
}
