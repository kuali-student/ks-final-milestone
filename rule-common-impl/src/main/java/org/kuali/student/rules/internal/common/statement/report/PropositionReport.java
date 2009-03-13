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
package org.kuali.student.rules.internal.common.statement.report;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;

/**
 * Proposition report stores the success message and/or failure message after a proposition is applied 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class PropositionReport {

	/**
	 * Proposition name
	 */
	private String name;
	
	/**
	 * Proposition type
	 */
	private PropositionType type;
	
    /**
     * True for successful report; otherwise false for an unsuccessful report
     */
	private Boolean successful = false;
	
	/**
	 * Success or failure report message
	 */
	private String message;
	
    /**
     * Criteria used in the proposition
     */
    private FactResultDTO criteria;

    /**
     * Facts used in the proposition
     */
    private FactResultDTO facts;

    /**
     * Result of proposition execution
     */
    private FactResultDTO propositionResult;

    /**
     * Constructor.
     * 
     * @param name proposition name
     */
    public PropositionReport(String name, PropositionType type) {
    	this.name = name;
    	this.type = type;
    }

	/**
	 * Gets proposition name.
	 * 
	 * @return Proposition name
	 */
	public String getPropositionName() {
		return this.name;
	}

	/**
	 * Gets proposition type.
	 * 
	 * @return Proposition type
	 */
	public PropositionType getPropositionType() {
		return this.type;
	}

	/**
	 * Returns true if report is successful.
	 * @return True if report is successful; otherwise false
	 */
    public Boolean isSuccessful() {
		return this.successful;
	}
	
    /**
	 * Set success to true if report is successful; otherwise set it to false.
     * @param success True if report is successful; otherwise false
     */
	public void setSuccessful(final Boolean success) {
		this.successful = success;
	}

//    /**
//     * @return the successMessage
//     */
//    public String getSuccessMessage() {
//        return this.successMessage;
//    }
//
//    /**
//     * @param successMessage the successMessage to set
//     */
//    public void setSuccessMessage(final String successMessage) {
//        this.successMessage = successMessage;
//    }
//
//    /**
//     * @return the failureMessage
//     */
//    public String getFailureMessage() {
//        return this.failureMessage;
//    }
//
//    /**
//     * @param failureMessage the failureMessage to set
//     */
//    public void setFailureMessage(final String failureMessage) {
//        this.failureMessage = failureMessage;
//    }

    /**
     * @return the successMessage
     */
    public String getMessage() {
        return this.message;
    }
    /**
     * @param successMessage the successMessage to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Returns the facts used in the proposition.
     * 
     * @return Facts
     */
    public FactResultDTO getFactResult() {
		return this.facts;
	}

    /**
     * Sets the facts used in the proposition.
     * 
     * @param facts Facts
     */
	public void setFactResult(FactResultDTO facts) {
		this.facts = facts;
	}

	/**
     * Returns the criteria used in the proposition.
	 * 
	 * @return Criteria fact
	 */
	public FactResultDTO getCriteriaResult() {
		return this.criteria;
	}

	/**
     * Sets the criteria used in the proposition.
	 * 
	 * @param criteria Criteria fact
	 */
	public void setCriteriaResult(FactResultDTO criteria) {
		this.criteria = criteria;
	}

	/**
	 * Returns the proposition execution result.
	 * 
	 * @return Proposition execution result
	 */
	public FactResultDTO getPropositionResult() {
		return propositionResult;
	}

	/**
	 * Sets the proposition execution result.
	 * 
	 * @param result Proposition execution result
	 */
	public void setPropositionResult(FactResultDTO result) {
		this.propositionResult = result;
	}

	public String toString() {
    	return "PropositionReport[name="+this.name+
    		", type="+this.type+
    		", successful=" + successful + 
    		", message='" + this.message + "']";
    }
}
