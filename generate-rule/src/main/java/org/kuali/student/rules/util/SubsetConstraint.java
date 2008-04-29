/**
 *
 */
package org.kuali.student.rules.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * A constraint that specifies that a fact set must be a subset of a given size of a given set of criteria.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SubsetConstraint<E> extends ConstraintStrategy {

    // ~ Instance fields --------------------------------------------------------

    HashSet<E> criteriaSet;
    HashSet<E> factSet;
    Integer requiredSubsetSize;

    // ~ Constructors -----------------------------------------------------------

    public SubsetConstraint() {
        super();
    }

    public SubsetConstraint(String constraintID, String requestClassName, String requestMethodName, Set criteriaSet, Integer requiredSubsetSize) {
        super(constraintID, requestClassName, requestMethodName);
        this.criteriaSet = new HashSet<E>(criteriaSet);
        this.requiredSubsetSize = requiredSubsetSize;

    }

    // ~ Methods ----------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.drools.util.Constraint#apply()
     */
    @Override
    public Boolean apply(String propVar) {
        System.out.println("applying constraint ");
        populateFactSet();

        Set<E> met = and();
        int count = met.size();
        cacheAdvice("%d of %s is still required", met, count, propVar);

        return count >= requiredSubsetSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    protected void cacheAdvice(String format, Object... args) {
        Set<E> met = (Set<E>) args[0];
        Integer count = (Integer) args[1];
        String propVar = (String) args[2];

        if (count >= requiredSubsetSize) {
            Propositions.setFailureMessage(propVar, "subset constraint fulfilled");
            return;
        }

        Set<E> unMet = andNot();
        int needed = requiredSubsetSize - count;
        String advice = String.format(format, needed, unMet.toString());
        Propositions.setFailureMessage(propVar, advice);

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

    private void populateFactSet() {

        try {
            Method m = requestMethodNameAccessor();

            Set<E> facts = (Set<E>) m.invoke(request);
            setFactSet(facts);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @return the requiredSubsetSize
     */
    public Integer getRequiredSubsetSize() {
        return requiredSubsetSize;
    }

    /**
     * @param requiredSubsetSize
     *            the requiredSubsetSize to set
     */
    public void setRequiredSubsetSize(Integer requiredSubsetSize) {
        this.requiredSubsetSize = requiredSubsetSize;
    }
}
