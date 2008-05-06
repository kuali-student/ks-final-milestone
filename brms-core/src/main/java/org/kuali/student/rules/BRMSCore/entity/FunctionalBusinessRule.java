package org.kuali.student.rules.BRMSCore.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more RuleElement instances. The class also contains BusinessRuleEvaluation
 * and RuleMetaData instances.
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
@Entity
@Table(name = "FunctionalBusinessRule_T")
@TableGenerator(name = "idGen", uniqueConstraints = {@UniqueConstraint(columnNames = {"ruleIdentifier"})})
@NamedQueries({@NamedQuery(name = "FunctionalBusinessRule.findByRuleID", query = "SELECT c FROM FunctionalBusinessRule c WHERE c.ruleIdentifier = :ruleID")})
public class FunctionalBusinessRule {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
    private Long id;

    private String name;
    private String description;
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
    }

    /**
     * Sets up a RuleProposition instance.
     * 
     * @param id
     * @param name
     * @param description
     * @param ruleSetIdentified
     * @param ruleMetaData
     */
    public FunctionalBusinessRule(String name, String description, String successMessage, String failureMessage, String ruleSetIdentified, String compileRuleID, RuleMetaData ruleMetaData, BusinessRuleEvaluation businessRuleEvaluation) {
        this.name = name;
        this.description = description;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.ruleIdentifier = ruleSetIdentified;
        this.compiledRuleID = compileRuleID;
        this.ruleMetaData = ruleMetaData;
        this.businessRuleEvaluation = businessRuleEvaluation;
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
     * @return the ruleSetIdentifier
     */
    public final String getRuleSetIdentifier() {
        return ruleIdentifier;
    }

    /**
     * @param ruleSetIdentifier
     *            the ruleSetIdentifier to set
     */
    public final void setRuleSetIdentifier(String ruleSetIdentifier) {
        ruleIdentifier = ruleSetIdentifier;
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
     * @param compiledRuleID the compiledRuleID to set
     */
    public void setCompiledRuleID(String compiledRuleID) {
        this.compiledRuleID = compiledRuleID;
    }

}
