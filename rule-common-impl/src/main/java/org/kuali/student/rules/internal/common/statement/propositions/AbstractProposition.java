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

import java.util.Comparator;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

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
    protected PropositionReport report = new PropositionReport();
    protected ComparisonOperator operator;
    protected T expectedValue;

    // ~ Constructors -----------------------------------------------------------
    public AbstractProposition() {
        super();
    }

    /**
     * Construct from fields.
     * 
     * @param propositionName
     */
    public AbstractProposition(String id, String propositionName, ComparisonOperator operator, T expectedValue) {
        this.id = id;
        this.propositionName = propositionName;
        this.operator = operator;
        this.expectedValue = expectedValue;
    }

    protected Boolean checkTruthValue(Comparable<T> computedValue, T expectedValue) {

        if (!(computedValue instanceof Comparable) || !(expectedValue instanceof Comparable)) {
            throw new IllegalArgumentException("Both computed value and expected values have to implement java.lang.Comparable.");
        }

        Boolean truthValue = false;
        int compareValue = computedValue.compareTo(expectedValue);

        switch (operator) {

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

    protected Boolean checkTruthValue(Comparator<T> comparator, T computedValue, T expectedValue) {
        Boolean truthValue = false;
        int compareValue = comparator.compare(computedValue, expectedValue);

        switch (operator) {

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

    public abstract Boolean apply();

    protected abstract void cacheReport(String format, Object... args);

    /**
     * @return the result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(Boolean result) {
        this.result = result;
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
     * @param propositionName
     *            the propositionName to set
     */
    public void setPropositionName(String propositionName) {
        this.propositionName = propositionName;
    }

    /**
     * @return the report
     */
    public PropositionReport getReport() {
        return report;
    }

    /**
     * @param report
     *            the report to set
     */
    public void setReport(PropositionReport report) {
        this.report = report;
    }

    /**
     * @return the operator
     */
    public ComparisonOperator getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    /**
     * @return the expectedValue
     */
    public String getExpectedValueAsString() {
        return expectedValue.toString();
    }

    /**
     * @param expectedValue
     *            the expectedValue to set
     */
    public void setExpectedValue(T expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String toString() {
    	return "Proposition[id=" + this.id 
    		+ ", propositionName=" + this.propositionName
    		+ ", type=" + this.getClass().getSimpleName()
    		+ ", result="+this.result + "]";
    }
}
