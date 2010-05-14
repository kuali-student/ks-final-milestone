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
 * Contains meta data about one Rule Element within a functional business rule. Rule Element can represent a logical operator
 * (AND, OR, XOR, NOT), a parentheses (left or right) or a proposition. If Rule Element type is Proposition then the object
 * has one Rule Proposition associated with it.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleElementDTO implements Serializable{

    @XmlElement
    private String operation;
    
    @XmlElement
    private Integer ordinalPosition;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String description;
    
    @XmlElement
    private RulePropositionDTO ruleProposition;


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
     * @return the operation
     */
    public final String getOperation() {
        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public final void setOperation(String operation) {
        this.operation = operation;
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
     * @return the ruleProposition
     */
    public final RulePropositionDTO getRuleProposition() {
        return ruleProposition;
    }

    /**
     * @param ruleProposition
     *            the ruleProposition to set
     */
    public final void setRuleProposition(RulePropositionDTO ruleProposition) {
        this.ruleProposition = ruleProposition;
    }
}
