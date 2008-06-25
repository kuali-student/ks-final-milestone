/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Entity
@Table(name = "FunctionalBusinessRule_T")
@NamedQueries({
        @NamedQuery(name = "FunctionalBusinessRule.findByRuleID", query = "SELECT c FROM FunctionalBusinessRule c WHERE c.identifier = :ruleID"),
        @NamedQuery(name = "FunctionalBusinessRule.findByAgendaType", query = "SELECT c FROM FunctionalBusinessRule c WHERE c.agendaType = :agendaType AND c.businessRuleType = :businessRuleType AND c.anchorType = :anchorType AND c.anchor = :anchor")})
public class FunctionalBusinessRule {

    @Id
    private String id;

    private String name;
    private String description;
    private String agendaType;
    private String businessRuleType;
    private String anchorType;
    private String anchor;
    private String successMessage;
    private String failureMessage;
    @Column(unique = true, nullable = false)
    private String identifier;
    private String compiledID;
    @Embedded
    private RuleMetaData metaData;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "functionalBusinessRule")
    private Collection<RuleElement> elements;

    public static final char INITIAL_PROPOSITION_PLACEHOLDER = 'A';
    public static final String VALIDATION_OUTCOME = "validationResultOutcome";

    /**
     * Sets up an empty instance.
     */
    public FunctionalBusinessRule() {
        id = null;
        name = null;
        description = null;
        successMessage = null;
        failureMessage = null;
        identifier = null;
        compiledID = null;
        metaData = null;
        agendaType = null;
        businessRuleType = null;
        anchorType = null;
        anchor = null;
    }

    /**
     * Sets up a RuleProposition instance.
     * 
     * @param id
     * @param name
     * @param description
     * @param ruleIdentified
     * @param ruleMetaData
     */
    public FunctionalBusinessRule(String name, String description, String successMessage, String failureMessage,
            String ruleIdentified, String compileRuleID, RuleMetaData ruleMetaData, String agendaType,
            String businessRuleType, String anchorType, String anchor) {
        this.name = name;
        this.description = description;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.identifier = ruleIdentified;
        this.compiledID = compileRuleID;
        this.metaData = ruleMetaData;
        this.agendaType = agendaType;
        this.businessRuleType = businessRuleType;
        this.anchorType = anchorType;
        this.anchor = anchor;
    }

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
                case AND_TYPE:
                    functionString.append(" " + RuleElementType.AND_TYPE.getName() + " ");
                    break;
                case LPAREN_TYPE:
                    functionString.append(RuleElementType.LPAREN_TYPE.getName());
                    break;
                case NOT_TYPE:
                    functionString.append(RuleElementType.NOT_TYPE.getName());
                    break;
                case OR_TYPE:
                    functionString.append(" " + RuleElementType.OR_TYPE.getName() + " ");
                    break;
                case PROPOSITION_TYPE:
                    functionString.append(proposition);
                    proposition++;
                    break;
                case RPAREN_TYPE:
                    functionString.append(RuleElementType.RPAREN_TYPE.getName());
                    break;
                case XOR_TYPE:
                    functionString.append(RuleElementType.XOR_TYPE.getName());
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
        Collection<RuleElement> ruleElements = this.getElements();

        char key = INITIAL_PROPOSITION_PLACEHOLDER;
        for (RuleElement ruleElement : ruleElements) {
            if (ruleElement.getOperation() == RuleElementType.PROPOSITION_TYPE) {
                propositions.put(String.valueOf(key), ruleElement.getRuleProposition());
                key++;
            }
        }
        return propositions;
    }

    /**
     * Adds a new RuleElement to the list of rule elements that the functional business rule is composed of
     * 
     * @param ruleElement
     *            a new Rule Element to add to this business rule object
     */
    public void addRuleElement(RuleElement ruleElement) {
        if (elements == null) {
            elements = new ArrayList<RuleElement>();
        }
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
     * @return the compiledRuleID
     */
    public String getCompiledID() {
        return compiledID;
    }

    /**
     * @param compiledRuleID
     *            the compiledRuleID to set
     */
    public void setCompiledID(String compiledRuleID) {
        this.compiledID = compiledRuleID;
    }

    /**
     * @return the agendaType
     */
    public final String getAgendaType() {
        return agendaType;
    }

    /**
     * @param agendaType
     *            the agendaType to set
     */
    public final void setAgendaType(String agendaType) {
        this.agendaType = agendaType;
    }

    /**
     * @return the businessRuleType
     */
    public final String getBusinessRuleType() {
        return businessRuleType;
    }

    /**
     * @param businessRuleType
     *            the businessRuleType to set
     */
    public final void setBusinessRuleType(String businessRuleType) {
        this.businessRuleType = businessRuleType;
    }

    /**
     * @return the anchorType
     */
    public final String getAnchorType() {
        return anchorType;
    }

    /**
     * @param anchorType
     *            the anchorType to set
     */
    public final void setAnchorType(String anchorType) {
        this.anchorType = anchorType;
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
     * @return the identifier
     */
    public final String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public final void setIdentifier(String identifier) {
        this.identifier = identifier;
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
    public final Collection<RuleElement> getElements() {
        return elements;
    }

    /**
     * @param elements
     *            the elements to set
     */
    public final void setElements(Collection<RuleElement> elements) {
        this.elements = elements;
    }

}
