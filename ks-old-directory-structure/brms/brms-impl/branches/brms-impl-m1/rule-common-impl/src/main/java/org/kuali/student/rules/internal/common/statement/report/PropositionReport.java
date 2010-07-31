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

/**
 * Proposition report stores the success message and/or failure message after a proposition is applied 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class PropositionReport {

    private boolean successful = false;
	private String successMessage;
    private String failureMessage;
    
	/**
	 * Returns true if report is successful.
	 * @return True if report is successful; otherwise false
	 */
    public boolean isSuccessful() {
		return successful;
	}
	
    /**
	 * Set success to true if report is successful; otherwise set it to false.
     * @param success True if report is successful; otherwise false
     */
	public void setSuccessful(final boolean success) {
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

    public String toString() {
    	return "PropositionReport[successful=" + successful + "]";
    }
}
