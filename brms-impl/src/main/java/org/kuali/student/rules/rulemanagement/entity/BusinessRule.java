/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.RuleElementType;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more LUIPerson instances. The class also contains RuleMetaData instance.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "BusinessRule_T")
@NamedQueries({@NamedQuery(name = "BusinessRule.findIdsByBusinessRuleType", query = "SELECT c.id FROM BusinessRule c WHERE c.businessRuleType.businessRuleTypeKey = :businessRuleTypeKey"),
               @NamedQuery(name = "BusinessRule.findByBusinessRuleTypeAndAnchor", query = "SELECT c FROM BusinessRule c WHERE  c.anchor = :anchor AND c.businessRuleType.businessRuleTypeKey = :businessRuleTypeKey"),
//               @NamedQuery(name = "BusinessRule.findByState", query = "SELECT c FROM BusinessRule c WHERE  c.anchor = :anchor AND c.businessRuleType.businessRuleTypeKey = :businessRuleTypeKey AND c.state = :state AND c.metaData.effectiveDate <= :today AND c.metaData.expirationDate >= :today"),
               @NamedQuery(name = "BusinessRule.findByState", query = "SELECT c FROM BusinessRule c WHERE  c.anchor = :anchor AND c.businessRuleType.businessRuleTypeKey = :businessRuleTypeKey AND c.state = :state AND c.metaData.effectiveDate <= :today AND c.metaData.expirationDate >= :today"),               
               @NamedQuery(name = "BusinessRule.findAllVersions", query = "SELECT c FROM BusinessRule c WHERE  c.originalRuleId = :originalRuleId"),
               @NamedQuery(name = "BusinessRule.findAnchorsByAnchorType", query = "SELECT a.anchor FROM BusinessRule a WHERE a.businessRuleType.anchorTypeKey = :anchorTypeKey")})
public class BusinessRule  {
    
    public static final String PROPOSITION_LABEL_PREFIX = "P";
    public static final int INITIAL_PROPOSITION_PLACEHOLDER = 1;
    public static final String VALIDATION_OUTCOME = "validationResultOutcome";

    @Id
    private String id;
    private String originalRuleId;
    
    private String name;
    
    private String description;

    @ManyToOne
    private BusinessRuleType businessRuleType;
    
    @Column(nullable = false)
    private String anchor;

    private String successMessage;
    private String failureMessage;

    @Column(nullable = false)
    BusinessRuleStatus state;    
    
    /* Repository Variables */
    private String compiledId;
    private String repositorySnapshotName;
    
    @Embedded
    private RuleMetaData metaData;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "businessRule", fetch = FetchType.EAGER)
    @OrderBy("ordinalPosition ASC")
    private List<RuleElement> ruleElements = new ArrayList<RuleElement>();

    /**
     * Generates a HashMap of <unique alphabet character, proposition> pair from a functional business rule.
     * 
     * @param rule
     *            Functional business rule used to generate HashMap
     * @return Returns HashMap
     */
    public HashMap<String, RuleProposition> getRulePropositions() {

        HashMap<String, RuleProposition> propositions = new HashMap<String, RuleProposition>();

        int key = INITIAL_PROPOSITION_PLACEHOLDER;
        for (RuleElement ruleElement : ruleElements) {
            if (ruleElement.getBusinessRuleElemnetTypeKey() == RuleElementType.PROPOSITION) {
                propositions.put(PROPOSITION_LABEL_PREFIX + String.valueOf(key), ruleElement.getRuleProposition());
                key++;
            }
        }
        return propositions;
    }

    /**
     * Adds a new LUIPerson to the list of rule ruleEmelemnts that the business rule is composed of
     * 
     * @param ruleElement
     *            a new Rule Element to add to this business rule object
     */
    public void addRuleElement(RuleElement ruleElement) {
        ruleElements.add(ruleElement);
    }

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(String id) {
        this.id = id;
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
    public final String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the successMessage
     */
    public final String getSuccessMessage() {
        return successMessage;
    }

    /**
     * @param successMessage
     *            the successMessage to set
     */
    public final void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * @return the failureMessage
     */
    public final String getFailureMessage() {
        return failureMessage;
    }

    /**
     * @param failureMessage
     *            the failureMessage to set
     */
    public final void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * @return the compiledRuleId
     */
    public String getCompiledId() {
        return compiledId;
    }

    /**
     * @param compiledRuleId
     *            the compiledRuleId to set
     */
    public void setCompiledId(String compiledRuleId) {
        this.compiledId = compiledRuleId;
    }

    /**
     * @return the businessRuleType
     */
    public final BusinessRuleType getBusinessRuleType() {
        return businessRuleType;
    }

    /**
     * @param businessRuleTypeKey
     *            the businessRuleTypeKey to set
     */
    public final void setBusinessRuleType(BusinessRuleType businessRuleType) {
        this.businessRuleType = businessRuleType;
    }

    /**
     * @return the anchor
     */
    public final String getAnchor() {
        return anchor;
    }

    /**
     * @param anchor
     *            the anchor to set
     */
    public final void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * @return the metaData
     */
    public final RuleMetaData getMetaData() {
        return metaData;
    }

    /**
     * @param metaData
     *            the metaData to set
     */
    public final void setMetaData(RuleMetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * @return the ruleEmelemnts
     */
    public final List<RuleElement> getRuleElements() {
        return ruleElements;
    }

    /**
     * @param ruleEmelemnts
     *            the ruleEmelemnts to set
     */
    public final void setRuleElements(List<RuleElement> elements) {
        this.ruleElements = elements;
    }

    /**
     * @return the originalRuleId
     */
    public String getOriginalRuleId() {
        return originalRuleId;
    }

    /**
     * @param originalRuleId the originalRuleId to set
     */
    public void setOriginalRuleId(String originalRuleId) {
        this.originalRuleId = originalRuleId;
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

    /**
     * @return the state
     */
    public BusinessRuleStatus getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(BusinessRuleStatus state) {
        this.state = state;
    }                
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("ID:" + this.id);
        sb.append("\nName:" + this.name);
        sb.append("\nState:" + this.state.toString());
        sb.append("\nBusinessRuleType:" + this.businessRuleType.getBusinessRuleTypeKey().toString());
        sb.append("\nAnchorType:" + this.businessRuleType.getAnchorTypeKey().toString());
        sb.append("\nAnchorValue:" + this.anchor);
        sb.append("\nEffectiveDate:" + this.metaData.getEffectiveDate());
        sb.append("\nExpirationDate:" + this.metaData.getExpirationDate());
        sb.append("\nOriginalRuleId:" + this.originalRuleId);
        sb.append("\nCompiledRuleId:" + this.compiledId);
        sb.append("\nRepositorySnapshotName:" + this.repositorySnapshotName);
        sb.append("\nRuleElement...");
        int i = 1;
        for(RuleElement element : ruleElements) {
            sb.append("\nElement" + i++ + ":" + element.toString());
        }
    
        
        return sb.toString();
    }
}
