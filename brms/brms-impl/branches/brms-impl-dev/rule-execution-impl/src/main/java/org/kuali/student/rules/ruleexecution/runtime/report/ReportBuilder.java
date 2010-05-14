package org.kuali.student.rules.ruleexecution.runtime.report;

import java.util.Map;

import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.report.RuleReport;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

public interface ReportBuilder {
    /**
     * Creates proposition report.
     * 
     * @param propContainer Contains a list of propositions
     * @return Rule report
     * @throws MessageBuilderException Errors building message
     */
    public RuleReport buildReport(PropositionContainer propContainer);

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
    public RuleReport buildReport(PropositionContainer propContainer, Map<String, Object> messageContextMap);
}
