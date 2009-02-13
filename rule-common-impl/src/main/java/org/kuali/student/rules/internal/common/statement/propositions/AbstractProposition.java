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
package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.TemplatePatternMatcher;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

/**
 * Abstract superclass that implements common proposition. Also wraps <code>CloneNotSupportedException</code> with
 * RuntimeException.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 * @author Kuali Student Team (len.kuali@gmail.com)
 */
public abstract class AbstractProposition<T> implements Proposition {

    // ~ Instance fields --------------------------------------------------------
    protected Boolean result = false;
    protected String id;
    protected String propositionName;
    protected PropositionReport report;
    protected ComparisonOperator operator;
    protected T expectedValue;
    protected PropositionType propositionType;
    protected Collection<T> resultValues;
    protected RulePropositionDTO ruleProposition;
    
    protected Boolean reportBuilt = Boolean.FALSE;
    
    private final TemplatePatternMatcher templatePatternMatcher = new TemplatePatternMatcher("#", "#");
    private final Map<String,String> tokenMap = new HashMap<String, String>();
    
    public final static String EXPECTED_VALUE_REPORT_MESSAGE_TOKEN = "expectedValue";
    public final static String OPERATOR_REPORT_MESSAGE_TOKEN = "operator";

    // ~ Constructors -----------------------------------------------------------
    public AbstractProposition() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param id Proposition identifier
     * @param propositionName Proposition name
     * @param type Proposition type
     * @param operator Boolean operator (<,>,<=,>=,=)
     * @param expectedValue Expected value
     * @param ruleProposition Rule proposition
     */
    public AbstractProposition(String id, String propositionName, PropositionType type, 
    		ComparisonOperator operator, T expectedValue, RulePropositionDTO ruleProposition) {
        this.id = id;
        this.propositionName = propositionName;
        this.propositionType = type;
        this.operator = operator;
        this.expectedValue = expectedValue;
        this.ruleProposition = ruleProposition;
        this.report = new PropositionReport(propositionName, type);
        
        addMessageToken(OPERATOR_REPORT_MESSAGE_TOKEN, this.operator.toString());
        addMessageToken(EXPECTED_VALUE_REPORT_MESSAGE_TOKEN, getTypeAsString(this.expectedValue));
    }

    protected Boolean checkTruthValue(Comparable<T> computedValue, T expectedValue) {
        if (!(computedValue instanceof Comparable) || !(expectedValue instanceof Comparable)) {
            throw new IllegalArgumentException("Both computed value and expected values have to implement java.lang.Comparable.");
        }

        int compareValue = computedValue.compareTo(expectedValue);
        return compare(compareValue);
    }

    protected Boolean checkTruthValue(Comparator<T> comparator, T computedValue, T expectedValue) {
        int compareValue = comparator.compare(computedValue, expectedValue);
        return compare(compareValue);
    }

    private Boolean compare(int compareValue) {
        Boolean truthValue = false;
        switch (this.operator) {
	        case EQUAL_TO:
	            truthValue = (compareValue == 0);
	            break;
	        case LESS_THAN:
	            truthValue = (compareValue == -1);
	            break;
	        case LESS_THAN_OR_EQUAL_TO:
	            truthValue = (compareValue == 0 || compareValue == -1);
	            break;
	        case GREATER_THAN:
	            truthValue = (compareValue == 1);
	            break;
	        case GREATER_THAN_OR_EQUAL_TO:
	            truthValue = (compareValue == 0 || compareValue == 1);
	            break;
	        case NOT_EQUAL_TO:
	            truthValue = (compareValue != 0);
	            break;
        }
	    return truthValue;
    }
    
    protected void addMessageToken(String token, String value) {
    	tokenMap.put(token, value);
    }
    
    protected String buildMessage(String message) {
    	return templatePatternMatcher.match(message, tokenMap);
    }

    protected String getTypeAsString(T type) {
	    String s = null;
    	if (type.getClass() == Date.class) {
	    	Date date = Date.class.cast(type);
	    	s = BusinessRuleUtil.formatDate(date);
	    } else if (type.getClass() == Calendar.class ) {
        	Calendar cal = Calendar.class.cast(type);
        	s = BusinessRuleUtil.formatDate(cal.getTime());
        } else if (type.getClass() == GregorianCalendar.class ) {
        	GregorianCalendar cal = GregorianCalendar.class.cast(type);
        	s = BusinessRuleUtil.formatDate(cal.getTime());
        /*} else if (type.getClass() == XMLGregorianCalendar.class ) {
        	XMLGregorianCalendar xmlCal = XMLGregorianCalendar.class.cast(type);
        	GregorianCalendar cal = xmlCal.toGregorianCalendar();
        	s = BusinessRuleUtil.formatDate(cal.getTime());*/
        } else {
        	s = type.toString();
        }
    	return s;
    }

    /**
     * Executes the proposition rule.
     */
    public abstract Boolean apply();

    /**
     * Generates a proposition report.
     * 
     * Success/failure message is optionally set by the rule proposition.
     * If success/failure message is not set then a generic success/failure 
     * message is generated by the report.
     */
    public abstract PropositionReport buildReport();

    protected PropositionReport buildDefaultReport(String successMessage, String failureMessage) {
        // Build success message
        if (result) {
    		if(ruleProposition.getSuccessMessage() == null || ruleProposition.getSuccessMessage().trim().isEmpty()) {
    			report.setSuccessMessage(successMessage);
    		} else {
	    		String msg = buildMessage(ruleProposition.getSuccessMessage());
	    		report.setSuccessMessage(msg);
    		}
            return report;
        }
        // Build failure message
		if(ruleProposition.getFailureMessage() == null || ruleProposition.getFailureMessage().trim().isEmpty()) {
	        String msg = buildMessage(failureMessage);
	        report.setFailureMessage(msg);
		} else {
			String msg = buildMessage(ruleProposition.getFailureMessage());
	        report.setFailureMessage(msg);
		}
		reportBuilt = Boolean.TRUE;
        return report;
    }
    
    /**
     * @return the result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * Returns the proposition id
     */
    public String getId() {
    	return id;
    }

    /**
     * @return the propositionName
     */
    public String getPropositionName() {
        return propositionName;
    }

	/**
	 * Returns the proposition type.
	 * @return Proposition type
	 */
	public PropositionType getType() {
		return this.propositionType;
	}

    /**
     * @return the report
     */
    public PropositionReport getReport() {
    	return (reportBuilt ? report : buildReport());
    }

    /**
     * @return the operator
     */
    public ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * @return the expectedValue
     */
    public String getExpectedValueAsString() {
        return expectedValue.toString();
    }

    /**
     * Gets results of proposition computation.
     * 
     * @return Proposition computation results
     */
    public Collection<?> getResultValues() {
    	return this.resultValues;
    }

	public String toString() {
    	return "Proposition[id=" + this.id 
    		+ ", propositionName=" + this.propositionName
    		+ ", propositionType=" + this.propositionType
    		+ ", class=" + this.getClass().getSimpleName()
    		+ ", result="+this.result + "]";
    }
}
