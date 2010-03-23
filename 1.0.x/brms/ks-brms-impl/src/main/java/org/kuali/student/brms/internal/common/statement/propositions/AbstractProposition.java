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
package org.kuali.student.brms.internal.common.statement.propositions;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.regex.RegularExpression;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;

/**
 * Abstract superclass that implements common proposition. Also wraps <code>CloneNotSupportedException</code> with
 * RuntimeException.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 * @author Kuali Student Team (len.kuali@gmail.com)
 */
public abstract class AbstractProposition<T> implements Proposition {

    // ~ Instance fields -------------------------------------------------------
    protected Boolean result = false;
    protected String id;
    protected String propositionName;
    protected ComparisonOperator operator;
    protected T expectedValue;
    protected PropositionType propositionType;
    protected Collection<T> resultValues;

    protected FactResultInfo criteriaDTO;
    protected FactResultInfo factDTO;
    protected String criteriaColumn;
    protected String factColumn;
    
    private final Map<String,Object> contextMap = new HashMap<String, Object>();
    
    private RegularExpression regex = new RegEx();
    
    private final static class RegEx implements RegularExpression {
		public Boolean matches(String regex, String s) {
			return s.matches(regex);
		}
    }
    
    // ~ Constructor -----------------------------------------------------------
    public AbstractProposition() {
    }

    /**
     * Constructor.
     * 
     * @param id Proposition identifier
     * @param propositionName Proposition name
     * @param type Proposition type
     * @param operator Boolean operator (<,>,<=,>=,=,!=,matches,not matches)
     * @param expectedValue Expected value
     * @param ruleProposition Rule proposition
     */
    public AbstractProposition(String id, String propositionName, PropositionType type, 
    		ComparisonOperator operator, T expectedValue,
    		FactResultInfo criteriaDTO, String criteriaColumn, 
    		FactResultInfo factDTO, String factColumn) {
        this.id = id;
        this.propositionName = propositionName;
        this.propositionType = type;
        this.operator = operator;
        this.expectedValue = expectedValue;
        this.criteriaDTO = criteriaDTO;
        this.factDTO = factDTO;
        this.criteriaColumn = criteriaColumn;
        this.factColumn = factColumn;
    }

    /**
     * Sets a regular expression class. This is optional.<br/> 
     * Default <a href="../util/regex/Pattern.html#sum">regular expression</a> 
     * uses Java's {@link java.lang.String#matches(String)}
     * 
     * @param regex Regular expression class
     */
    public void setRegularExpression(RegularExpression regex) {
    	this.regex = regex;
    }

    protected Boolean checkTruthValue(Comparable<T> computedValue) {
        if (!(computedValue instanceof Comparable<?>) || !(expectedValue instanceof Comparable<?>)) {
            throw new IllegalArgumentException("Both computed value and expected values must implement java.lang.Comparable.");
        }

        return compare(computedValue);
    }

    private Boolean compare(Comparable<T> computedValue) {
    	int compareValue = 999;
    	Boolean truthValue = false;
        switch (this.operator) {
	        case EQUAL_TO:
	            compareValue = computedValue.compareTo(this.expectedValue);
	            truthValue = (compareValue == 0);
	            break;
	        case LESS_THAN:
	            compareValue = computedValue.compareTo(this.expectedValue);
	            truthValue = (compareValue == -1);
	            break;
	        case LESS_THAN_OR_EQUAL_TO:
	            compareValue = computedValue.compareTo(this.expectedValue);
	            truthValue = (compareValue == 0 || compareValue == -1);
	            break;
	        case GREATER_THAN:
	            compareValue = computedValue.compareTo(this.expectedValue);
	            truthValue = (compareValue == 1);
	            break;
	        case GREATER_THAN_OR_EQUAL_TO:
	            compareValue = computedValue.compareTo(this.expectedValue);
	            truthValue = (compareValue == 0 || compareValue == 1);
	            break;
	        case NOT_EQUAL_TO:
	            compareValue = computedValue.compareTo(this.expectedValue);
	            truthValue = (compareValue != 0);
	            break;
	        case MATCHES:
	        	truthValue = this.regex.matches(this.expectedValue.toString(), computedValue.toString());
	        	break;
	        case NOT_MATCHES:
	        	truthValue = !this.regex.matches(this.expectedValue.toString(), computedValue.toString());
	            break;
	        default: 
	        	throw new IllegalStateException("Invalid comparison operator: " + this.operator);
        }
	    return truthValue;
    }

    protected void addMessageContext(String token, Object value) {
    	this.contextMap.put(token, value);
    }
    
    public Map<String,Object> getMessageContextMap() {
    	return this.contextMap;
    }

    public abstract void buildMessageContextMap();
    
    protected String getTypeAsString(T type) {
	    String s = null;
    	if (type.getClass() == Date.class) {
	    	Date date = Date.class.cast(type);
	    	s = BusinessRuleUtil.formatIsoDate(date);
	    } else if (type.getClass() == Calendar.class ) {
        	Calendar cal = Calendar.class.cast(type);
        	s = BusinessRuleUtil.formatIsoDate(cal.getTime());
        } else if (type.getClass() == GregorianCalendar.class ) {
        	GregorianCalendar cal = GregorianCalendar.class.cast(type);
        	s = BusinessRuleUtil.formatIsoDate(cal.getTime());
        //} else if (type.getClass() == XMLGregorianCalendar.class ) {
        //	XMLGregorianCalendar xmlCal = XMLGregorianCalendar.class.cast(type);
        //	GregorianCalendar cal = xmlCal.toGregorianCalendar();
        //	s = BusinessRuleUtil.formatDate(cal.getTime());
        } else {
        	s = type.toString();
        }
    	return s;
    }

    /**
     * Executes the proposition rule.
     */
    //public abstract Boolean apply();

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

    public FactResultInfo getCriteria() {
    	return this.criteriaDTO;
    }

    public FactResultInfo getFact() {
    	return this.factDTO;
    }

    public String getCriteriaColumn() {
    	return this.criteriaColumn;
    }

    public String getFactColumn() {
    	return this.factColumn;
    }

    public String toString() {
    	return "Proposition[id=" + this.id 
    		+ ", propositionName=" + this.propositionName
    		+ ", propositionType=" + this.propositionType
    		+ ", class=" + this.getClass().getSimpleName()
    		+ ", result="+this.result + "]";
    }
}
