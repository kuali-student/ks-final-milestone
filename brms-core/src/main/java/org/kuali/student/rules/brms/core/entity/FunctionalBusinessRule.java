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
import java.util.Hashtable;
import java.util.Set;

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
import org.kuali.student.rules.brms.parser.GenerateRuleSet;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more RuleElement instances. The class also contains BusinessRuleEvaluation
 * and RuleMetaData instances.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Entity
@Table(name = "FunctionalBusinessRule_T")
@NamedQueries({
        @NamedQuery(name = "FunctionalBusinessRule.findByRuleID", query = "SELECT c FROM FunctionalBusinessRule c WHERE c.ruleIdentifier = :ruleID"),
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
    private String ruleIdentifier;
    private String compiledRuleID;
    @Embedded
    private RuleMetaData ruleMetaData;
    @Embedded
    private BusinessRuleEvaluation businessRuleEvaluation;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "functionalBusinessRule")
    private Collection<RuleElement> ruleElements;

    public static final char INITIAL_PROPOSITION_PLACEHOLDER = 'A';
    private static final String RULE_PACKAGE = "org.kuali.student.rules.enrollment";
    private static final String RULESET_DESC = "Enrollment Rules";
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
        ruleIdentifier = null;
        compiledRuleID = null;
        ruleMetaData = null;
        businessRuleEvaluation = null;
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
            String ruleIdentified, String compileRuleID, RuleMetaData ruleMetaData,
            BusinessRuleEvaluation businessRuleEvaluation, String agendaType, String businessRuleType,
            String anchorType, String anchor) {
        this.name = name;
        this.description = description;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.ruleIdentifier = ruleIdentified;
        this.compiledRuleID = compileRuleID;
        this.ruleMetaData = ruleMetaData;
        this.businessRuleEvaluation = businessRuleEvaluation;
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

        Collection<RuleElement> ruleElements = this.getRuleElements();

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
     * This method builds the drools rule set container associated with the functional business rule.
     * 
     * @param rule
     * @return
     * @throws Exception
     */
    public GenerateRuleSet buildRuleSet() throws Exception {
        GenerateRuleSet grs = new GenerateRuleSet(createAdjustedRuleFunctionString());

        grs.setRuleSetName(RULE_PACKAGE + this.getRuleIdentifier());
        grs.setRuleSetDescription(RULESET_DESC);
        grs.setRuleName(this.getRuleIdentifier());
        grs.setRuleDescription(this.getDescription()); // Rule description cannot be empty
        grs.setRuleCategory(null);
        grs.setRuleAttributes(null);

        Hashtable<String, String> funcConstraintsMap = new Hashtable<String, String>();

        HashMap<String, RuleProposition> propositions = this.getRulePropositions();

        Set<String> labels = propositions.keySet();

        for (String label : labels) {
            funcConstraintsMap.put(label, label);
        }

        grs.setLhsFuncConstraintMap(funcConstraintsMap);
        grs.setRuleOutcome("Propositions.setProposition(\"" + VALIDATION_OUTCOME + "\",true);");

        grs.parse();

        return grs;
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
        Collection<RuleElement> ruleElements = this.getRuleElements();

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
        if (ruleElements == null) {
            ruleElements = new ArrayList<RuleElement>();
        }
        ruleElements.add(ruleElement);
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
     * @return the ruleMetaData
     */
    public final RuleMetaData getRuleMetaData() {
        return ruleMetaData;
    }

    /**
     * @param ruleMetaData
     *            the ruleMetaData to set
     */
    public final void setRuleMetaData(RuleMetaData ruleMetaData) {
        this.ruleMetaData = ruleMetaData;
    }

    /**
     * @return the businessRuleEvaluation
     */
    public final BusinessRuleEvaluation getBusinessRuleEvaluation() {
        return businessRuleEvaluation;
    }

    /**
     * @param businessRuleEvaluation
     *            the businessRuleEvaluation to set
     */
    public final void setBusinessRuleEvaluation(BusinessRuleEvaluation businessRuleEvaluation) {
        this.businessRuleEvaluation = businessRuleEvaluation;
    }

    /**
     * @return the ruleElements
     */
    public final Collection<RuleElement> getRuleElements() {
        return ruleElements;
    }

    /**
     * @param ruleElements
     *            the ruleElements to set
     */
    public final void setRuleElements(Collection<RuleElement> ruleElements) {
        this.ruleElements = ruleElements;
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
     * @return the ruleIdentifier
     */
    public final String getRuleIdentifier() {
        return ruleIdentifier;
    }

    /**
     * @param ruleIdentifier
     *            the ruleIdentifier to set
     */
    public final void setRuleIdentifier(String ruleIdentifier) {
        this.ruleIdentifier = ruleIdentifier;
    }

    /**
     * @return the compiledRuleID
     */
    public String getCompiledRuleID() {
        return compiledRuleID;
    }

    /**
     * @param compiledRuleID
     *            the compiledRuleID to set
     */
    public void setCompiledRuleID(String compiledRuleID) {
        this.compiledRuleID = compiledRuleID;
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

}
