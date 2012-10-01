/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.r2.core.process.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.process.infc.Check;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "holdIssueId",
    "milestoneTypeKey",
    "agendaId",
    "childProcessKey",
    "rightComparisonValue",
    "leftComparisonAgendaId",
    "rightComparisonAgendaId",
    "meta",
    "attributes",
    "_futureElements"})

public class CheckInfo 
    extends IdEntityInfo 
    implements Check, Serializable {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    private static final long serialVersionUID = 1L;
    
    @XmlElement 
    private String holdIssueId;

    @XmlElement 
    private String milestoneTypeKey;

    @XmlElement 
    private String agendaId;

    @XmlElement
    private String rightComparisonAgendaId;

    @XmlElement
    private String rightComparisonValue;

    @XmlElement
    private String leftComparisonAgendaId;

    @XmlElement 
    private String childProcessKey;

    @XmlAnyElement
    private List<Element> _futureElements;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    /**
     * Constructs a new CheckInfo.
     */    
    public CheckInfo() {
    }

    /**
     * Constructs a new CheckInfo from another Check.
     * 
     * @param check the Check to copy
     */
    public CheckInfo(Check check) {
        super(check);
        if (check != null) {
            this.holdIssueId = check.getHoldIssueId();
            this.milestoneTypeKey = check.getMilestoneTypeKey();
            this.agendaId = check.getAgendaId();
            this.leftComparisonAgendaId = check.getLeftComparisonAgendaId();
            this.rightComparisonValue = check.getRightComparisonValue();
            this.rightComparisonAgendaId = check.getRightComparisonAgendaId();
            this.childProcessKey = check.getChildProcessKey();
        }
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    @Override
    public String getHoldIssueId() {
        return this.holdIssueId;
    }

    public void setHoldIssueId(String holdIssueId) {
        this.holdIssueId = holdIssueId;
    }

    @Override
    public String getMilestoneTypeKey() {
        return this.milestoneTypeKey;
    }

    public void setMilestoneTypeKey(String milestoneTypeKey) {
        this.milestoneTypeKey = milestoneTypeKey;
    }

    @Override
    public String getAgendaId() {
        return this.agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    @Override
    public String getChildProcessKey() {
        return this.childProcessKey;
    }

    public void setChildProcessKey(String childProcessKey) {
        this.childProcessKey= childProcessKey;
    }

    @Override
    public String getRightComparisonValue() {
        return this.rightComparisonValue;
    }

    public void setRightComparisonValue(String rightComparisonValue) {
        this.rightComparisonValue = rightComparisonValue;
    }

    @Override
    public String getLeftComparisonAgendaId() {
        return this.leftComparisonAgendaId;
    }

    public void setLeftComparisonAgendaId(String leftComparisonAgendaId) {
        this.leftComparisonAgendaId = leftComparisonAgendaId;
    }

    @Override
    public String getRightComparisonAgendaId() {
        return this.rightComparisonAgendaId;
    }

    public void setRightComparisonAgendaId(String rightComparisonAgendaId) {
        this.rightComparisonAgendaId = rightComparisonAgendaId;
    }
}
