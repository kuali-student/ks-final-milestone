package org.kuali.student.rules.ruleexecution.runtime.report;

import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

public interface ReportBuilder {
    /**
     * Creates proposition result report.
     * 
     * @param propContainer Contains a list of propositions
     * @return The proposition container <code>propContainer</code> with a report
     */
    public PropositionReport execute(PropositionContainer propContainer);
}
