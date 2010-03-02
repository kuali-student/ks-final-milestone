/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.brms.rulemanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.common.util.UUIDHelper;

/**
 * Contains meta data about the WHEN part of Drool rules. The Rule Proposition consists of left hand side, comparisonOperatorTypeKey and right
 * hand side of a given rule.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "KSBRMS_RULE_PROP")
public class RuleProposition {

    @Id
    @Column(name="ID")
    private String id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="DESCR")
    private String desc;
    
    @Column(name="SUCCESS_MSG")
    private String successMessage;
    
    @Column(name="FAILURE_MSG")
    private String failureMessage;
    
    @Column (name="CMP_DATA_TYPE_KEY")
    private String comparisonDataTypeKey;
    
    @OneToOne(cascade = {CascadeType.ALL} )
    private LeftHandSide leftHandSide;
    
    @Column(name="COMP_OPER_TYPE_KEY")
    @Enumerated(EnumType.STRING)
    private ComparisonOperator comparisonOperatorTypeKey;
    
    @Embedded
    private RightHandSide rightHandSide;


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
     * @return the leftHandSide
     */
    public final LeftHandSide getLeftHandSide() {
        return leftHandSide;
    }

    /**
     * @param leftHandSide
     *            the leftHandSide to set
     */
    public final void setLeftHandSide(LeftHandSide leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    /**
     * @return the comparisonOperatorTypeKey
     */
    public ComparisonOperator getComparisonOperatorTypeKey() {
        return comparisonOperatorTypeKey;
    }

    /**
     * @param comparisonOperatorTypeKey the comparisonOperatorTypeKey to set
     */
    public void setComparisonOperatorTypeKey(ComparisonOperator comparisonOperatorTypeKey) {
        this.comparisonOperatorTypeKey = comparisonOperatorTypeKey;
    }

    /**
     * @return the rightHandSide
     */
    public final RightHandSide getRightHandSide() {
        return rightHandSide;
    }

    /**
     * @param rightHandSide
     *            the rightHandSide to set
     */
    public final void setRightHandSide(RightHandSide rightHandSide) {
        this.rightHandSide = rightHandSide;
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
     * Returns the success message.
     * 
     * @return Success message
     */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * Sets the success message.
	 * 
	 * @param successMessage Success message
	 */
	public void setSuccessMessage(String successMessage) {
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
     * @return the comparisonDataTypeKey
     */
    public String getComparisonDataTypeKey() {
        return comparisonDataTypeKey;
    }

    /**
     * @param comparisonDataTypeKey the comparisonDataTypeKey to set
     */
    public void setComparisonDataTypeKey(String comparisonDataTypeKey) {
        this.comparisonDataTypeKey = comparisonDataTypeKey;
    }
}
