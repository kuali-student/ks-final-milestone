/**
 *
 */
package org.kuali.student.rules.statement;

import java.util.HashSet;
import java.util.Set;

/**
 * A constraint that specifies that a fact set must be a subset of a given size of a given set of criteria.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SubsetProposition<T extends Integer, E> extends AbstractProposition<T> {

    // ~ Instance fields --------------------------------------------------------

    Set<E> criteriaSet;
    Set<E> factSet;

    // ~ Constructors -----------------------------------------------------------

    public SubsetProposition() {
        super();
    }

    public SubsetProposition(String propositionName, Set<E> criteriaSet, Set<E> factSet) {
        super(propositionName);
        this.criteriaSet = criteriaSet;
        this.factSet = factSet;
    }

    
    // ~ Methods ----------------------------------------------------------------    

    
    @SuppressWarnings("unchecked")
    @Override
    public Boolean apply(ComparisonOperator operator, T expectedValue) {
        
        super.apply(operator, expectedValue);
 
        Set<E> met = and();
        Integer count = met.size();

        result = checkTruthValue((T)count);
        
        cacheReport("%d of %s is still required", count);

        return result;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    protected void cacheReport(String format, Object... args) {
        Integer count = (Integer) args[0];

        if (result) {
            report.setSuccessMessage("subset constraint fulfilled");
            return;
        }

        // TODO: Use the operator to compute exact message
        Set<E> unMet = andNot();
        int needed = expectedValue - count;
        String advice = String.format(format, needed, unMet.toString());
        report.setFailureMessage(advice);
    }

    /**
     * Returns the intersection of the fact set with the criteria set.
     * 
     * @return the intersection
     */
    public Set<E> and() {
        Set<E> rval = new HashSet<E>(factSet);
        rval.retainAll(criteriaSet);

        return rval;
    }

    /**
     * Returns the disjunction of the criteria set from the fact set.
     * 
     * @return
     */
    public Set<E> andNot() {
        HashSet<E> rval = new HashSet<E>(criteriaSet);
        rval.removeAll(factSet);

        return rval;
    }

    /**
     * @return the criteriaSet
     */
    public Set<E> getCriteriaSet() {
        return criteriaSet;
    }

    /**
     * @param criteriaSet
     *            the criteriaSet to set
     */
    public void setCriteriaSet(Set<E> criteriaSet) {
        this.criteriaSet = new HashSet<E>(criteriaSet);
    }

    /**
     * @return the factSet
     */
    public Set<?> getFactSet() {
        return factSet;
    }

    /**
     * @param factSet
     *            the factSet to set
     */
    public void setFactSet(Set<E> factSet) {
        this.factSet = new HashSet<E>(factSet);
    }
}
