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

package org.kuali.student.brms.ruleexecution.runtime.report.ast;

import java.util.Collection;

import org.kuali.student.brms.internal.common.statement.propositions.PropositionType;
import org.kuali.student.brms.internal.common.statement.propositions.rules.RuleProposition;
import org.kuali.student.brms.internal.common.statement.report.PropositionReport;

public class PropositionMock implements RuleProposition {
	private String id;
	private String propositionName;
	private PropositionReport propositionReport;
	private Boolean result;
	
	public PropositionMock(String id, String propositionName) {
		this.id = id;
		this.propositionName = propositionName;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public void setReport(PropositionReport propositionReport) {
		this.propositionReport = propositionReport;
	}

	public Boolean apply() {
		return Boolean.TRUE;
	}
	
    public PropositionReport buildReport() {
    	return this.propositionReport;
    }
    
	public Boolean getResult() {
		return this.result;
	}
	
	public PropositionReport getReport() {
    	return this.propositionReport;
	}

	public String getPropositionName() {
    	return this.propositionName;
	}

	public String getId() {
    	return this.id;
	}
	
	public PropositionType getType() {
    	return null;
	}

	public Collection<?> getResultValues() {
    	return null;
	}

	public String getMessageId() {
		return this.propositionName;
	}

	public Boolean isSuccesful() {
		return this.result;
	}

	public String getMessage() {
		return this.propositionReport.getMessage();
	}
}
