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
package org.kuali.student.brms.internal.common.statement.report;

import java.util.List;

/**
 * Rule report stores the success message and/or failure message summary of 
 * all the propositions (business rule). 
 */
public class RuleReport implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * True for successful report; otherwise false for an unsuccessful report
     */
	private Boolean successful = Boolean.FALSE;
	
	/**
	 * Successful report message
	 */
	private String successMessage;
    
	/**
	 * Failure report message
	 */
	private String failureMessage;
	
	/**
	 * List of proposition reports
	 */
	private List<PropositionReport> propositionReportList;
    
	/**
	 * Returns true if report is successful.
	 * @return True if report is successful; otherwise false
	 */
    public Boolean isSuccessful() {
		return successful;
	}
	
    /**
	 * Set success to true if report is successful; otherwise set it to false.
     * @param success True if report is successful; otherwise false
     */
	public void setSuccessful(final Boolean success) {
		this.successful = success;
	}

    /**
     * @return the successMessage
     */
    public String getSuccessMessage() {
        return successMessage;
    }
    /**
     * @param successMessage the successMessage to set
     */
    public void setSuccessMessage(final String successMessage) {
        this.successMessage = successMessage;
    }
    /**
     * @return the failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }
    /**
     * @param failureMessage the failureMessage to set
     */
    public void setFailureMessage(final String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * Gets a list of proposition reports.
     * 
     * @return Proposition report list
     */
	public List<PropositionReport> getPropositionReports() {
		return propositionReportList;
	}

	/**
     * Sets a list of proposition reports.
	 * 
	 * @param propositionReportList Proposition report list
	 */
	public void setPropositionReports(List<PropositionReport> propositionReportList) {
		this.propositionReportList = propositionReportList;
	}

    public String toString() {
    	return "RuleReport[successful=" + successful + "]";
    }
}
