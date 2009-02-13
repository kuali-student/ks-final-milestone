package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T min;
	private Collection<T> fact;

    public final static String DEFAULT_SUCCESS_MESSAGE = "Minimum constraint fulfilled";
    public final static String DEFAULT_FAILURE_MESSAGE = "Minimum not met. Minimum found: #min#";

    public final static String MIN_REPORT_TEMPLATE_TOKEN = "min";

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue, ruleProposition);
        this.fact = fact;
	}

    @Override
    public Boolean apply() {
    	this.min = Collections.min(this.fact);

        result = checkTruthValue(min, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(min);

        return result;
    }

    @Override
    public PropositionReport buildReport() {
        // TODO: Use the operator to compute exact message
        String minStr = getTypeAsString(this.min);
    	addMessageToken(MIN_REPORT_TEMPLATE_TOKEN, minStr);
        buildDefaultReport(DEFAULT_SUCCESS_MESSAGE, DEFAULT_FAILURE_MESSAGE);
        return report;
    }
}
