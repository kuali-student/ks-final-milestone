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
package org.kuali.student.enrollment.hold.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.hold.infc.Hold;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoldInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "issueIdId", "personId", "isWarning", "isOverridable", "effectiveDate", "releasedDate", "metaInfo", "attributes", "_futureElements"})

public class HoldInfo extends IdEntityInfo implements Hold, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;

    @XmlElement
    private String issueId;

    @XmlElement
    private Boolean isWarning;

    @XmlElement
    private Boolean isOverridable;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date releasedDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    private HoldInfo() {
	personId = null;
	isWarning = false;
	isOverridable = false;
	issueId = null;
        effectiveDate = null;
        releasedDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new HoldInfo from another Hold.
     *
     * @param hold the Hold to copy
     */
    public HoldInfo(Hold hold) {
        super(hold);

	this.personId = hold.getPersonId();
	this.issueId = hold.getIssueId();
	this.isWarning = hold.isWarning();
	this.isOverridable = hold.isOverridable();

        this.effectiveDate = null != hold.getEffectiveDate() ? new Date(hold.getEffectiveDate().getTime()) : null;
        this.releasedDate = null != hold.getReleasedDate() ? new Date(hold.getReleasedDate().getTime()) : null;

        _futureElements = null;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    @Override
    public Boolean isWarning() {
        return isWarning;
    }

    @Override
    public void setWarning(boolean isWarning) {
        this.isWarning = isWarning;
    }

    @Override
    public Boolean isOverridable() {
	return isOverridable;
    }

    @Override
    public void setOverridable(boolean isOverridable) {
        this.isOverridable = isOverridable;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = new Date(effectiveDate.getTime());
    }

    @Override
    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = new Date(releasedDate.getTime());
    }
}
