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
package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.rules.internal.common.runtime.ast.BooleanMessage;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.propositions.rules.RuleProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.statement.report.RuleReport;
import org.kuali.student.rules.ruleexecution.runtime.SimpleExecutor;
import org.kuali.student.rules.ruleexecution.runtime.report.ReportBuilder;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class RuleReportBuilder extends AbstractMessageBuilder implements ReportBuilder {
    public RuleReportBuilder(SimpleExecutor executor) {
    	super(executor);
    }

    /**
     * Creates proposition result report.
     * 
     * @param propContainer Contains a list of propositions
     * @return The proposition container <code>propContainer</code> with a report
     */
    public RuleReport buildReport(PropositionContainer propContainer) {
    	HashMap<String, BooleanMessage> nodeMessageMap = new HashMap<String, BooleanMessage>();
        for (RuleProposition proposition : propContainer.getPropositions()) {
        	BooleanMessage booleanMessage = proposition.getBooleanMessage();
        	nodeMessageMap.put(proposition.getPropositionName(), booleanMessage);
        }
        // This is the final rule report message summary
    	String message = super.buildMessage(propContainer.getFunctionalRuleString(), nodeMessageMap);
        
        RuleReport ruleReport = propContainer.getRuleReport();
        ruleReport.setSuccessful(propContainer.getRuleResult());

        if (propContainer.getRuleResult() == true) {
            ruleReport.setSuccessMessage(message);
        } else {
            ruleReport.setFailureMessage(message);
        }

        propContainer.setRuleReport(ruleReport);
        List<PropositionReport> propositionReportList = createPropositionReport(propContainer.getPropositions());
        ruleReport.setPropositionReports(propositionReportList);
        
        return ruleReport;
    }
    
    private List<PropositionReport> createPropositionReport(Collection<RuleProposition> propositionList) {
        List<PropositionReport> propositionReportList = new ArrayList<PropositionReport>();
        for(RuleProposition prop : propositionList) {
        	prop.getReport().setSuccessful(prop.getResult());
        	propositionReportList.add(prop.getReport());
        }
        return propositionReportList;
    }
}
