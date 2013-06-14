/**
 * Copyright 2013 The Kuali Foundation
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
package org.kuali.student.enrollment.waitlist.dto;

import org.kuali.student.enrollment.waitlist.infc.WaitListEntry;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprRosterEntryInfo", propOrder = {
        "id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "waitListId", "studentId", "luiId", "position", "lastCheckIn", "blockingRuleIds",
        "meta", "attributes", "_futureElements"})

public class WaitListEntryInfo extends RelationshipInfo implements WaitListEntry, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String waitListId;
    @XmlElement
    private String studentId;
    @XmlElement
    private String luiId;
    @XmlElement
    private Integer position;
    @XmlElement
    private Date lastCheckIn;
    @XmlElement
    private List<String> blockingRuleIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public WaitListEntryInfo() {
    }

    public WaitListEntryInfo(WaitListEntry entry) {
        super();

        if(entry != null) {
            setWaitListId(entry.getWaitListId());
            setStudentId(entry.getStudentId());
            setPosition(entry.getPosition());
            setLuiId(entry.getLuiId());
            if(entry.getLastCheckIn() != null) {
                setLastCheckIn(new Date(entry.getLastCheckIn().getTime()));
            }

            if(entry.getBlockingRuleIds() != null) {
                setBlockingRuleIds(new ArrayList<String>(blockingRuleIds));
            }

        }
    }

    @Override
    public String getWaitListId() {
        return waitListId;
    }

    public void setWaitListId(String waitListId) {
        this.waitListId = waitListId;
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public Date getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(Date lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    @Override
    public List<String> getBlockingRuleIds() {
        if(blockingRuleIds == null) {
            blockingRuleIds = new ArrayList<String>();
        }
        return blockingRuleIds;
    }

    public void setBlockingRuleIds(List<String> blockingRuleIds) {
        this.blockingRuleIds = blockingRuleIds;
    }
}
