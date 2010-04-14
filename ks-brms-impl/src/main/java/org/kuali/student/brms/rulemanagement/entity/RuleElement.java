/**
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

package org.kuali.student.brms.rulemanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.common.util.UUIDHelper;

/**
 * Contains meta data about one Rule Element within a functional business rule. Rule Element can represent a logical operator
 * (AND, OR, XOR, NOT), a parentheses (left or right) or a proposition. If Rule Element type is Proposition then the object
 * has one Rule Proposition associated with it.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "KSBRMS_RULE_ELEMENT")
public class RuleElement {

    @Id
    @Column(name="ID")
    private String id;
    
    @Column(name="RULE_ELMT_TYPE_KEY")
    @Enumerated(EnumType.STRING)
    private RuleElementType businessRuleElemnetTypeKey;
    
    @Column(name="ORDINAL_POS")
    private Integer ordinalPosition;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="DESCR")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "BUS_RULE")
    private BusinessRule businessRule;
    
    @OneToOne(cascade = {CascadeType.ALL})
    private RuleProposition ruleProposition;


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
    public final String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public final String getDesc() {
        return desc;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDesc(String description) {
        this.desc = description;
    }

    /**
     * @return the businessRuleElemnetTypeKey
     */
    public RuleElementType getBusinessRuleElemnetTypeKey() {
        return businessRuleElemnetTypeKey;
    }

    /**
     * @param businessRuleElemnetTypeKey the businessRuleElemnetTypeKey to set
     */
    public void setBusinessRuleElemnetTypeKey(RuleElementType businessRuleElemnetTypeKey) {
        this.businessRuleElemnetTypeKey = businessRuleElemnetTypeKey;
    }

    /**
     * @return the ordinalPosition
     */
    public final Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    /**
     * @param ordinalPosition
     *            the ordinalPosition to set
     */
    public final void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    /**
     * @return the BusinessRule
     */
    public final BusinessRule getBusinessRule() {
        return businessRule;
    }

    /**
     * @param BusinessRule
     *            the businessRule to set
     */
    public final void setBusinessRule(BusinessRule businessRule) {
        this.businessRule = businessRule;
    }

    /**
     * @return the ruleProposition
     */
    public final RuleProposition getRuleProposition() {
        return ruleProposition;
    }

    /**
     * @param ruleProposition
     *            the ruleProposition to set
     */
    public final void setRuleProposition(RuleProposition ruleProposition) {
        this.ruleProposition = ruleProposition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("[ID:");
        sb.append(this.id);
        sb.append(", ");
        sb.append("RuleElementType:");
        sb.append(this.businessRuleElemnetTypeKey.toString());
        sb.append(", ");
        sb.append("Name:");
        sb.append(this.name);
        if(null != this.ruleProposition) {
           sb.append(", ");            
           sb.append("Proposition YVF:");
           sb.append(this.ruleProposition.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType());
           sb.append(", ");
           sb.append("Proposition Operator:");
           sb.append(this.ruleProposition.getComparisonOperatorTypeKey());
           sb.append(", ");
           sb.append("Proposition RHS:");
           sb.append(this.ruleProposition.getRightHandSide().getExpectedValue());
        }
        sb.append("]");
        return sb.toString();
    }
}
