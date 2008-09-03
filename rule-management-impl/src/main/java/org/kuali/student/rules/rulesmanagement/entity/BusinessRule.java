/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulesmanagement.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more RuleElement instances. The class also contains RuleMetaData instance.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "FunctionalBusinessRule_T")
@NamedQueries({@NamedQuery(name = "BusinessRule.findByRuleID", query = "SELECT c FROM BusinessRule c WHERE c.ruleId = :ruleID"), @NamedQuery(name = "BusinessRule.findByAgendaType", query = "SELECT c FROM BusinessRule c WHERE c.agendaType = :agendaType AND c.businessRuleType = :businessRuleType AND c.anchorType = :anchorType AND c.anchor = :anchor")})
public class BusinessRule {

    public static final String PROPOSITION_LABEL_PREFIX = "P";
    public static final int INITIAL_PROPOSITION_PLACEHOLDER = 1;
    public static final String VALIDATION_OUTCOME = "validationResultOutcome";

    @Id
    private String id;

    private String name;
    private String description;

    private String businessRuleTypeKey;
    private String anchor;

    private String successMessage;
    private String failureMessage;

    @Column(unique = true, nullable = false)
    private String ruleId;
    private String compiledId;

    @Embedded
    private RuleMetaData metaData;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "businessRule")
    private List<RuleElement> elements = new ArrayList<RuleElement>();

    /**
     * Generates a function string from a functional business rule based on RuleElementType classification.
     * 
     * @param rule
     *            Functional business rule used to create its function string representation
     * @return Returns function string e.g. "(A OR B) AND C"
     */
    public String createRuleFunctionString() {

        Collection<RuleElement> ruleElements = this.getElements();

        StringBuilder functionString = new StringBuilder();
        char proposition = INITIAL_PROPOSITION_PLACEHOLDER; // each proposition is represented as a letter

        // step through rule elements and create a function string
        for (RuleElement ruleElement : ruleElements) {
            switch (ruleElement.getOperation()) {
                case AND:
                    functionString.append(" " + RuleElementType.AND.getName() + " ");
                    break;
                case LPAREN:
                    functionString.append(RuleElementType.LPAREN.getName());
                    break;
                case NOT:
                    functionString.append(RuleElementType.NOT.getName());
                    break;
                case OR:
                    functionString.append(" " + RuleElementType.OR.getName() + " ");
                    break;
                case PROPOSITION:
                    functionString.append(proposition);
                    proposition++;
                    break;
                case RPAREN:
                    functionString.append(RuleElementType.RPAREN.getName());
                    break;
                case XOR:
                    functionString.append(RuleElementType.XOR.getName());
                    break;
                default:
                    functionString.append("(unknown)");
            }
        }
        return functionString.toString().trim();
    }

    public String createAdjustedRuleFunctionString() {
        String functionString = createRuleFunctionString();
        functionString = functionString.replace("AND", "*");
        functionString = functionString.replace("OR", "+");
        return functionString;
    }

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
        for (RuleElement ruleElement : elements) {
            if (ruleElement.getOperation() == RuleElementType.PROPOSITION) {
                propositions.put(PROPOSITION_LABEL_PREFIX + String.valueOf(key), ruleElement.getRuleProposition());
                key++;
            }
        }
        return propositions;
    }

    /**
     * Adds a new RuleElement to the list of rule elements that the business rule is composed of
     * 
     * @param ruleElement
     *            a new Rule Element to add to this business rule object
     */
    public void addRuleElement(RuleElement ruleElement) {
        elements.add(ruleElement);
    }

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
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
    public final String getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }

    /**
     * @param businessRuleTypeKey
     *            the businessRuleTypeKey to set
     */
    public final void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
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
     * @return the ruleId
     */
    public final String getRuleId() {
        return ruleId;
    }

    /**
     * @param ruleId
     *            the ruleId to set
     */
    public final void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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
     * @return the elements
     */
    public final List<RuleElement> getElements() {
        return elements;
    }

    /**
     * @param elements
     *            the elements to set
     */
    public final void setElements(List<RuleElement> elements) {
        this.elements = elements;
    }
}
