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

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;

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
    protected ComparisonOperator operator;
    protected T expectedValue;
    protected PropositionType propositionType;
    protected Collection<T> resultValues;

    protected FactResultDTO criteriaDTO;
    protected FactResultDTO factDTO;
    protected String criteriaColumn;
    protected String factColumn;
    
    private final Map<String,Object> contextMap = new HashMap<String, Object>();
    
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
    		ComparisonOperator operator, T expectedValue,
    		FactResultDTO criteriaDTO, String criteriaColumn, 
    		FactResultDTO factDTO, String factColumn) {
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

    protected Boolean checkTruthValue(Comparable<T> computedValue, T expectedValue) {
        if (!(computedValue instanceof Comparable) || !(expectedValue instanceof Comparable)) {
            throw new IllegalArgumentException("Both computed value and expected values have to implement java.lang.Comparable.");
        }

        int compareValue = computedValue.compareTo(expectedValue);
        return compare(compareValue);
    }

    protected Boolean checkTruthValue(Comparable<T> computedValue) {
        if (!(computedValue instanceof Comparable) || !(expectedValue instanceof Comparable)) {
            throw new IllegalArgumentException("Both computed value and expected values have to implement java.lang.Comparable.");
        }

        int compareValue = computedValue.compareTo(this.expectedValue);
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
    public abstract Boolean apply();

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

    public FactResultDTO getCriteria() {
    	return this.criteriaDTO;
    }

    public FactResultDTO getFact() {
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
