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

package org.kuali.student.brms.rulemanagement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;


/**
 * Contains meta data about one Rule Element within a functional business rule. Rule Element can represent a logical operator
 * (AND, OR, XOR, NOT), a parentheses (left or right) or a proposition. If Rule Element type is Proposition then the object
 * has one Rule Proposition associated with it.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleElementInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
    
    @XmlElement
    private String businessRuleElemnetTypeKey;
    
    @XmlElement
    private Integer ordinalPosition;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String desc;
    
    @XmlElement
    private RulePropositionInfo businessRuleProposition;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
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
    public String getBusinessRuleElemnetTypeKey() {
        return businessRuleElemnetTypeKey;
    }

    /**
     * @param businessRuleElemnetTypeKey the businessRuleElemnetTypeKey to set
     */
    public void setBusinessRuleElemnetTypeKey(String businessRuleElemnetTypeKey) {
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
     * @return the businessRuleProposition
     */
    public RulePropositionInfo getBusinessRuleProposition() {
        return businessRuleProposition;
    }

    /**
     * @param businessRuleProposition the businessRuleProposition to set
     */
    public void setBusinessRuleProposition(RulePropositionInfo businessRuleProposition) {
        this.businessRuleProposition = businessRuleProposition;
    }
}
