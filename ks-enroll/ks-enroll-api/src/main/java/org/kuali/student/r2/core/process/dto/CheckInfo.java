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
@XmlType(name = "CheckInfo", propOrder = { "id", "typeKey", "stateKey", "name",
                "descr", "issueId", "milestoneTypeKey", 
                "agendaId", "processKey", 
                "rightComparisonValue", "leftComparisonAgendaId", "rightComparisonAgendaId",
                "meta", "attributes", "_futureElements" })

public class CheckInfo 
    extends IdEntityInfo 
    implements Check, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement 
    private String issueId;

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
    private String processKey;

    @XmlAnyElement
    private List<Element> _futureElements;


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
            this.issueId = check.getIssueId();
            this.milestoneTypeKey = check.getMilestoneTypeKey();
            this.agendaId = check.getAgendaId();
            this.processKey = check.getProcessKey();
            this.rightComparisonValue = check.getRightComparisonValue();
            this.leftComparisonAgendaId = check.getLeftComparisonAgendaId();
            this.rightComparisonAgendaId = check.getRightComparisonAgendaId();
        }
    }

    @Override
    public String getIssueId() {
        return this.issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
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
    public String getProcessKey() {
        return this.processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
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
