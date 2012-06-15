/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.exemption.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.exemption.infc.Exemption;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExemptionInfo", propOrder = {"id", "typeKey", "stateKey", 
                "name", "descr", "exemptionRequestId", "processKey", "checkId",
                "personId", "effectiveDate", "expirationDate", 
                "useLimit", "useCount", "dateOverride", 
                "milestoneOverride", "learningResultOverride", 
                "meta", "attributes", "_futureElements"})

public class ExemptionInfo 
    extends IdEntityInfo 
    implements Exemption, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String exemptionRequestId;
    
    @XmlElement
    private String processKey;
    
    @XmlElement
    private String checkId;
    @XmlElement
    private String personId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private Integer useLimit;

    @XmlElement
    private Integer useCount;

    @XmlElement
    private DateOverrideInfo dateOverride;

    @XmlElement
    private MilestoneOverrideInfo milestoneOverride;

    @XmlElement
    private LearningResultOverrideInfo learningResultOverride;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ExemptionInfo() {
        super();
    }

    /**
     * Constructs a new ExemptionInfo from another Exemption.
     * 
     * @param exemption
     *            the Exemption to copy
     */
    public ExemptionInfo(Exemption exemption) {
        super(exemption);

        if (null != exemption) {
            this.exemptionRequestId = exemption.getExemptionRequestId();
            this.processKey = exemption.getProcessKey();
            this.checkId = exemption.getCheckId();
            this.personId = exemption.getPersonId();
            this.effectiveDate = exemption.getEffectiveDate();
            this.expirationDate = exemption.getExpirationDate();
            this.useLimit = exemption.getUseLimit();
            this.useCount = exemption.getUseCount();

            if (exemption.getDateOverride() != null) {
                this.dateOverride = new DateOverrideInfo(exemption.getDateOverride());
            }

            if (exemption.getMilestoneOverride() != null) {
                this.milestoneOverride = new MilestoneOverrideInfo(exemption.getMilestoneOverride());
            }

            if (exemption.getLearningResultOverride() != null) {
                this.learningResultOverride = new LearningResultOverrideInfo(exemption.getLearningResultOverride());
            }
        }

        _futureElements = null;
    }

    @Override
    public String getExemptionRequestId() {
        return exemptionRequestId;
    }

    public void setExemptionRequestId(String exemptionRequestId) {
        this.exemptionRequestId = exemptionRequestId;
    }

    
    @Override
    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    @Override
    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
    
    
    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    @Override
    public Integer getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(Integer useLimit) {
        this.useLimit = useLimit;
    }

    @Override
    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    @Override
    public DateOverrideInfo getDateOverride() {
        return dateOverride;
    }

    public void setDateOverride(DateOverrideInfo dateOverrideInfo) {
        this.dateOverride = new DateOverrideInfo(dateOverrideInfo);
    }

    @Override
    public MilestoneOverrideInfo getMilestoneOverride() {
        return milestoneOverride;
    }

    public void setMilestoneOverride(MilestoneOverrideInfo milestoneOverrideInfo) {
        this.milestoneOverride = milestoneOverrideInfo;
    }

    @Override
    public LearningResultOverrideInfo getLearningResultOverride() {
        return learningResultOverride;
    }

    public void setLearningResultOverride(LearningResultOverrideInfo learningResultOverrideInfo) {
        this.learningResultOverride = learningResultOverrideInfo;
    }
}
