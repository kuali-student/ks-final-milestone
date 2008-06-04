package org.kuali.student.rules.BRMS.mapper;

import org.kuali.student.rules.BRMSCore.entity.ComparisonOperator;


/**
 * Abstract superclass that implements common proposition.
 * Also wraps <code>CloneNotSupportedException</code> with RuntimeException.
 *
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 * @param <T>
 *
 */
public abstract class AbstractProposition<T extends Comparable<? super T>> implements Proposition<T> {

    //~ Instance fields --------------------------------------------------------
    protected Boolean result = false;
    protected String propositionName;
    protected PropositionReport report = new PropositionReport();
    protected ComparisonOperator operator;
    T expectedValue;
    
    //~ Constructors -----------------------------------------------------------
    public AbstractProposition() {
        super();
    }
    
    /**
     * Construct from fields.
     * @param propositionName
     */
    public AbstractProposition(String propositionName) {
        this.propositionName = propositionName;        
    }

    public Boolean apply(ComparisonOperator operator, T expectedValue) {
        this.operator = operator;
        this.expectedValue = expectedValue;
        
        return result;
    }
    
    protected Boolean checkTruthValue(T computedValue) {
        Boolean truthValue = false;
        int compareValue = computedValue.compareTo(expectedValue);
        
        switch(operator) {
            
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
    
    protected abstract void cacheReport(String format, Object... args);
    /**
     * @return the result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * @return the propositionName
     */
    public String getPropositionName() {
        return propositionName;
    }

    /**
     * @param propositionName the propositionName to set
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
     * @param report the report to set
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
     * @param operator the operator to set
     */
    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    /**
     * @return the expectedValue
     */
    public T getExpectedValue() {
        return expectedValue;
    }

    /**
     * @param expectedValue the expectedValue to set
     */
    public void setExpectedValue(T expectedValue) {
        this.expectedValue = expectedValue;
    }
}
