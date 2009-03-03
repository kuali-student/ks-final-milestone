package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.util.Collection;

import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessage;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.propositions.rules.RuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

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

    public BooleanMessage getBooleanMessage() {
    	BooleanMessage bm = new BooleanMessage(this.getResult(), 
    			propositionReport.getSuccessMessage(), propositionReport.getFailureMessage()); 
    	return bm;
    }

	public String getMessageId() {
		return this.getPropositionName();
	}
}
