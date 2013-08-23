/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
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

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.hold.infc.AppliedHold;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoldInfo", propOrder = {"id", "typeKey", "stateKey", "name",
                "descr", "holdIssueId", "personId",  "effectiveDate", "expirationDate",
                "meta", "attributes", "_futureElements" }) 

public class AppliedHoldInfo 
    extends RelationshipInfo
    implements AppliedHold, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;

    @XmlElement
    private String holdIssueId;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlAnyElement
    private List<Object> _futureElements;  


    /**
     *  Constructs a new HoldInfo.
     */
    public AppliedHoldInfo() {
    }

    /**
     *  Constructs a new HoldInfo from anotehr Hold.
     *
     *  @param hold the hold to copy
     */
    public AppliedHoldInfo(AppliedHold hold) {
        super(hold);

        if (hold != null) {
            this.personId = hold.getPersonId();
            this.holdIssueId = hold.getHoldIssueId();
            this.name = hold.getName();
            this.descr = hold.getDescr();
        }
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getHoldIssueId() {
        return holdIssueId;
    }

    public void setHoldIssueId(String holdIssueId) {
        this.holdIssueId = holdIssueId;
    }

    @Override
    public Date getReleasedDate() {
        return getExpirationDate();
    }

    /**
     *
     * @deprecated please use setExpirationDate instead.
     */
    @Deprecated
    public void setReleasedDate(Date releasedDate) {
        setExpirationDate(releasedDate);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }
}
