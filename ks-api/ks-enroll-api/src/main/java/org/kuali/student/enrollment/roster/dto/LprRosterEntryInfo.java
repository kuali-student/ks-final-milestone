/**
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.enrollment.roster.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.roster.infc.LprRosterEntry;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprRosterEntryInfo", propOrder = {
                "id", "typeKey", "stateKey", 
                "effectiveDate", "expirationDate",
                "lprRosterId", "lprId", "position", 
                "meta", "attributes", "_futureElements"})

public class LprRosterEntryInfo 
    extends RelationshipInfo 
    implements LprRosterEntry, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String lprRosterId;

    @XmlElement
    private String lprId;

    @XmlElement
    private Integer position;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     *  Constructs a new LprRosterEntryInfo.
     */
    public LprRosterEntryInfo() {
    }

    /**
     *  Constructs a new LprRosterEntryInfo from anotehr
     *  LprRosterEntry.
     *
     *  @param entry the LprRosterEntry to copy
     */
    public LprRosterEntryInfo(LprRosterEntry entry) {
        super();
        
        if (entry != null) {
            this.lprRosterId = entry.getLprRosterId();
            this.lprId = entry.getLprId();
            this.position = entry.getPosition();
        }
    }

    @Override
    public String getLprRosterId() {
        return lprRosterId;
    }

    public void setLprRosterId(String lprRosterId) {
        this.lprRosterId = lprRosterId;
    }

    @Override
    public String getLprId() {
        return lprId;
    }

    public void setLprId(String lprId) {
        this.lprId = lprId;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
