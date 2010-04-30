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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.kuali.student.brms.internal.common.runtime.ast.BooleanFunctionResult;
import org.kuali.student.brms.internal.common.statement.PropositionContainer;
import org.kuali.student.brms.internal.common.statement.propositions.rules.RuleProposition;
import org.kuali.student.brms.internal.common.statement.report.PropositionReport;
import org.kuali.student.brms.internal.common.statement.report.RuleReport;
import org.kuali.student.brms.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.brms.ruleexecution.runtime.report.ReportBuilder;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

/**
 * This RuleReportBuilder class builds a rule report from a collection of
 * propositions. The report is built using the boolean expression 
 * (functional rule) found in the <code>PropositionContainer</code>.
 */
public class RuleReportBuilderImpl extends AbstractMessageBuilder implements ReportBuilder {
    public RuleReportBuilderImpl(SimpleExecutor executor) {
    	super(executor);
    }

    /**
     * Creates proposition report from a list of propositions.
     * 
     * @param pc Contains a list of propositions
     * @return Rule report
     */
    public RuleReport buildReport(final PropositionContainer pc) {
    	return buildReport(pc, null);
    }

    /**
     * Builds a report using proposition message templates and message 
     * context objects.
     * proposition message template keys must match keys in the message 
     * context map.
     * 
     * @param pc Contains a list of propositions
     * @param messageContextMap Message context map
     * @return Rule report
     * @throws MessageBuilderException Errors building message
     */
    public RuleReport buildReport(final PropositionContainer pc, final Map<String, Object> messageContextMap) {
    	// This is the final rule report message summary
        RuleReport ruleReport = pc.getRuleReport();
    	if(!pc.getOverrideReport()) {
	    	BooleanFunctionResult result = super.build(pc.getFunctionalRuleString(), pc.getPropositionMap(), messageContextMap);
	    	String message = result.getMessage();
	
	        ruleReport.setSuccessful(pc.getRuleResult());
	
	        if (pc.getRuleResult() == true) {
	            ruleReport.setSuccessMessage(message);
	        } else {
	            ruleReport.setFailureMessage(message);
	        }
    	}

        pc.setRuleReport(ruleReport);
        List<PropositionReport> propositionReportList = createPropositionReport(pc.getPropositions());
        ruleReport.setPropositionReports(propositionReportList);
        
        return ruleReport;
    }

    /**
     * Creates a list of proposition reports.
     * 
     * @param propositionList Collection of rule propositions
     * @return List of proposition reports
     * @throws MessageBuilderException Errors building message
     */
    private List<PropositionReport> createPropositionReport(Collection<RuleProposition> propositionList) {
        List<PropositionReport> propositionReportList = new ArrayList<PropositionReport>();
        for(RuleProposition prop : propositionList) {
        	prop.getReport().setSuccessful(prop.getResult());
        	propositionReportList.add(prop.getReport());
        }
        return propositionReportList;
    }
}
