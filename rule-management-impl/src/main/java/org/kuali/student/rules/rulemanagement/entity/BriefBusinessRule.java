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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.RuleElementType;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more LUIPerson instances. The class also contains RuleMetaData instance.
 * 
 * @author Kuali Student Team
 */
@Entity
@Table(name = "BusinessRule_T")
public class BriefBusinessRule  {
    
    public static final String PROPOSITION_LABEL_PREFIX = "P";
    public static final int INITIAL_PROPOSITION_PLACEHOLDER = 1;
    public static final String VALIDATION_OUTCOME = "validationResultOutcome";

    @Id
    private String id;
    private String originalRuleId;
    
    private String name;

    @Column(nullable = false)
    private String anchor;

    @Column(nullable = false)
    BusinessRuleStatus state;    
    
    @Embedded
    private RuleMetaData metaData;

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
        sb.append("\nAnchorValue:" + this.anchor);
        sb.append("\nEffectiveDate:" + this.metaData.getEffectiveDate());
        sb.append("\nExpirationDate:" + this.metaData.getExpirationDate());
        sb.append("\nOriginalRuleId:" + this.originalRuleId);
        sb.append("\nRuleElement...");
    
        
        return sb.toString();
    }
}
