package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
import java.util.Collection;

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

@Entity
@Table(name = "FunctionalBusinessRule_T")
@TableGenerator(name = "idGen")
@NamedQueries({@NamedQuery(name = "FunctionalBusinessRule.findByRuleID",
		query = "SELECT c FROM FunctionalBusinessRule c WHERE c.ruleSetIdentifier = ?1")})
public class FunctionalBusinessRule {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;
	private String name;
	private String description;
	private String ruleSetIdentifier;
	@Embedded
	private RuleMetaData ruleMetaData;	
	@Embedded
	private BusinessRuleEvaluation businessRuleEvaluation;	
	@OneToMany(mappedBy="functionalBusinessRule") private Collection<RuleElement> ruleElements;

	
	/**
	 * 
	 */
	public FunctionalBusinessRule() {
		id = null;
		name = null;
		description = null;
		ruleSetIdentifier = null;
		ruleMetaData = null;
		businessRuleEvaluation = null;
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param ruleSetIdentified
	 * @param ruleMetaData
	 */
	public FunctionalBusinessRule(String name, String description,
			String ruleSetIdentified, RuleMetaData ruleMetaData, BusinessRuleEvaluation businessRuleEvaluation) {
		this.name = name;
		this.description = description;
		this.ruleSetIdentifier = ruleSetIdentified;
		this.ruleMetaData = ruleMetaData;
		this.businessRuleEvaluation = businessRuleEvaluation;
	}

	public void addRuleElement(RuleElement ruleElement) {
		if (this.ruleElements == null) {
			this.ruleElements = new ArrayList<RuleElement>(); 
		}
		this.ruleElements.add(ruleElement);
	}
		
	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param name the name to set
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
	 * @param description the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the ruleSetIdentified
	 */
	public final String getRuleSetIdentified() {
		return ruleSetIdentifier;
	}

	/**
	 * @param ruleSetIdentified the ruleSetIdentified to set
	 */
	public final void setRuleSetIdentified(String ruleSetIdentified) {
		this.ruleSetIdentifier = ruleSetIdentified;
	}

	/**
	 * @return the ruleMetaData
	 */
	public final RuleMetaData getRuleMetaData() {
		return ruleMetaData;
	}

	/**
	 * @param ruleMetaData the ruleMetaData to set
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
	 * @param businessRuleEvaluation the businessRuleEvaluation to set
	 */
	public final void setBusinessRuleEvaluation(
			BusinessRuleEvaluation businessRuleEvaluation) {
		this.businessRuleEvaluation = businessRuleEvaluation;
	}
}
