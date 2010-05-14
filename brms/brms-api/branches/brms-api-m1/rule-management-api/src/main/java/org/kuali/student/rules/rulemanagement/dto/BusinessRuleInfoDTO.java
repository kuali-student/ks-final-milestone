/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more RuleElementDTO instances. The class also contains RuleMetaDataDTO instance.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleInfoDTO implements Serializable{

    @XmlAttribute
    private String businessRuleId;

    @XmlElement
    private String businessRuleTypeKey;
    
    @XmlElement
    private String anchorValue;
    
    @XmlElement
    private String anchorTypeKey;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String description;

    @XmlElement    
    private String successMessage;
    
    @XmlElement
    private String failureMessage;

    @XmlElement
    private Date effectiveStartTime;
    
    @XmlElement
    private Date effectiveEndTime;
    
    @XmlElement
    private String status;
    
    @XmlElement
    private String compiledId;
    
    @XmlElement
    private String repositorySnapshotName;
    
    @XmlElement(name = "ruleElement")
    @XmlElementWrapper(name = "ruleElementList")
    private List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
    

    @XmlElement
    private MetaInfoDTO metaInfo;


    /**
     * @return the businessRuleId
     */
    public String getBusinessRuleId() {
        return businessRuleId;
    }


    /**
     * @param businessRuleId the businessRuleId to set
     */
    public void setBusinessRuleId(String businessRuleId) {
        this.businessRuleId = businessRuleId;
    }


    /**
     * @return the businessRuleTypeKey
     */
    public String getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }


    /**
     * @param businessRuleTypeKey the businessRuleTypeKey to set
     */
    public void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }


    /**
     * @return the anchorValue
     */
    public String getAnchorValue() {
        return anchorValue;
    }


    /**
     * @param anchorValue the anchorValue to set
     */
    public void setAnchorValue(String anchorValue) {
        this.anchorValue = anchorValue;
    }


    /**
     * @return the anchorTypeKey
     */
    public String getAnchorTypeKey() {
        return anchorTypeKey;
    }


    /**
     * @param anchorTypeKey the anchorTypeKey to set
     */
    public void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the successMessage
     */
    public String getSuccessMessage() {
        return successMessage;
    }


    /**
     * @param successMessage the successMessage to set
     */
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }


    /**
     * @return the failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }


    /**
     * @param failureMessage the failureMessage to set
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }


    /**
     * @return the effectiveStartTime
     */
    public Date getEffectiveStartTime() {
        return effectiveStartTime;
    }


    /**
     * @param effectiveStartTime the effectiveStartTime to set
     */
    public void setEffectiveStartTime(Date effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }


    /**
     * @return the effectiveEndTime
     */
    public Date getEffectiveEndTime() {
        return effectiveEndTime;
    }


    /**
     * @param effectiveEndTime the effectiveEndTime to set
     */
    public void setEffectiveEndTime(Date effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }


    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }


    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * @return the ruleElementList
     */
    public List<RuleElementDTO> getRuleElementList() {
        return ruleElementList;
    }


    /**
     * @param ruleElementList the ruleElementList to set
     */
    public void setRuleElementList(List<RuleElementDTO> ruleElementList) {
        this.ruleElementList = ruleElementList;
    }


    /**
     * @return the metaInfo
     */
    public MetaInfoDTO getMetaInfo() {
        return metaInfo;
    }


    /**
     * @param metaInfo the metaInfo to set
     */
    public void setMetaInfo(MetaInfoDTO metaInfo) {
        this.metaInfo = metaInfo;
    }


    /**
     * @return the compiledId
     */
    public String getCompiledId() {
        return compiledId;
    }


    /**
     * @param compiledId the compiledId to set
     */
    public void setCompiledId(String compiledId) {
        this.compiledId = compiledId;
    }

    /**
     * @return the repositorySnapshotName
     */
    public String getRepositorySnapshotName() {
        return repositorySnapshotName;
    }

    /**
     * @param repositorySnapshotName the repositorySnapshotName to set
     */
    public void setRepositorySnapshotName(String repositorySnapshotName) {
        this.repositorySnapshotName = repositorySnapshotName;
    }   
}
