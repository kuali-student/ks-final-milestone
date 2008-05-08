/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.BRMSCore.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Contains meta data about the WHEN part of Drool rules. The Rule Proposition consists of left hand side, operator and right
 * hand side of a given rule.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Entity
@Table(name = "RuleProposition_T")
@TableGenerator(name = "idGen")
public class RuleProposition {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
    private Long id;
    private String name;
    private String description;
    private String failureMessage;
    @Embedded
    private LeftHandSide leftHandSide;
    @Embedded
    private Operator operator;
    @Embedded
    private RightHandSide rightHandSide;

    /**
     * Sets up an empty instance.
     */
    public RuleProposition() {
        id = null;
        name = null;
        description = null;
        failureMessage = null;
        leftHandSide = null;
        operator = null;
        rightHandSide = null;
    }

    /**
     * Sets up a RuleProposition instance.
     * 
     * @param name
     * @param description
     * @param leftHandSide
     * @param operator
     * @param rightHandSide
     */
    public RuleProposition(String name, String description, String failureMessage, LeftHandSide leftHandSide, Operator operator, RightHandSide rightHandSide) {
        this.name = name;
        this.description = description;
        this.failureMessage = failureMessage;
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
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
     * @return the operator
     */
    public final Operator getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public final void setOperator(Operator operator) {
        this.operator = operator;
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
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public final void setId(Long id) {
        this.id = id;
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
}
