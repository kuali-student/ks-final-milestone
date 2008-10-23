/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * Contains meta data about the WHEN part of Drool rules. The Rule Proposition consists of left hand side, operator and right
 * hand side of a given rule.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RulePropositionDTO implements Serializable {

    @XmlElement
    private String name;
    
    @XmlElement
    private String description;
    
    @XmlElement
    private String failureMessage;
    
    @XmlElement
    private String comparisonDataType;
    
    @XmlElement
    private LeftHandSideDTO leftHandSide;
    
    @XmlElement
    private String comparisonOperatorType;
    
    @XmlElement
    private RightHandSideDTO rightHandSide;


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
    public final LeftHandSideDTO getLeftHandSide() {
        return leftHandSide;
    }

    /**
     * @param leftHandSide
     *            the leftHandSide to set
     */
    public final void setLeftHandSide(LeftHandSideDTO leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    /**
     * @return the rightHandSide
     */
    public final RightHandSideDTO getRightHandSide() {
        return rightHandSide;
    }

    /**
     * @param rightHandSide
     *            the rightHandSide to set
     */
    public final void setRightHandSide(RightHandSideDTO rightHandSide) {
        this.rightHandSide = rightHandSide;
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
     * @return the comparisonDataType
     */
    public String getComparisonDataType() {
        return comparisonDataType;
    }

    /**
     * @param comparisonDataType the comparisonDataType to set
     */
    public void setComparisonDataType(String comparisonDataType) {
        this.comparisonDataType = comparisonDataType;
    }

    /**
     * @return the comparisonOperatorType
     */
    public String getComparisonOperatorType() {
        return comparisonOperatorType;
    }

    /**
     * @param comparisonOperatorType the comparisonOperatorType to set
     */
    public void setComparisonOperatorType(String comparisonOperatorType) {
        this.comparisonOperatorType = comparisonOperatorType;
    }    
}
