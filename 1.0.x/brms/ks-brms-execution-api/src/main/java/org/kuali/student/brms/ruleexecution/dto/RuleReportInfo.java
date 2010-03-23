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
package org.kuali.student.brms.ruleexecution.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Proposition report stores the success message and/or failure message after a proposition is applied 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class RuleReportInfo implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    @XmlElement
    private Boolean successful = false;

    @XmlElement
    private String successMessage;

    @XmlElement
    private String failureMessage;
    
    @XmlElement
    private List<PropositionReportInfo> propositionReportList;
    
    /**
     * Constructor
     */
    public RuleReportInfo() {
    }

    /**
	 * Returns true if report is successful.
	 * @return True if report is successful; otherwise false
	 */
    public Boolean isSuccessful() {
		return successful;
	}
	
    /**
	 * Set success to true if report is successful; other wise set t false.
     * @param success True if report is successful; other false
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
     * Returns a list of proposition reports.
     * @return List of proposition reports
     */
	public List<PropositionReportInfo> getPropositionReports() {
		return propositionReportList;
	}

	/**
     * Sets a list of proposition reports.
	 * @param propositionReportList List of proposition reports
	 */
	public void setPropositionReports(List<PropositionReportInfo> propositionReportList) {
		this.propositionReportList = propositionReportList;
	}

    public String toString() {
    	return "RuleReportDTO[successful=" + this.successful + "]";
    }
}
