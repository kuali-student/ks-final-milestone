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

import java.util.HashSet;
import java.util.Set;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

/**
 * A constraint that specifies that a fact set must be a subset of a given size of a given set of criteria.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class IntersectionProposition<E> extends AbstractProposition<Integer> {

    // ~ Instance fields --------------------------------------------------------

    Set<E> criteriaSet;
    Set<E> factSet;

    // ~ Constructors -----------------------------------------------------------

    public IntersectionProposition() {
        super();
    }

    public IntersectionProposition(String id, String propositionName, ComparisonOperator operator, Integer expectedValue,
            Set<E> criteriaSet, Set<E> factSet) {
        super(id, propositionName, operator, expectedValue);
        this.criteriaSet = criteriaSet;
        this.factSet = (factSet == null ? new HashSet<E>() : factSet);
    }

    // ~ Methods ----------------------------------------------------------------

    @Override
    public Boolean apply() {
        Set<E> met = and();
        Integer count = met.size();

        result = checkTruthValue(count, super.expectedValue);

        cacheReport("%d of %s is still required", count, super.expectedValue);

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
        Integer expectedValue = (Integer) args[1];
        if (result) {
            report.setSuccessMessage("Intersection constraint fulfilled");
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
    public Set<E> getFactSet() {
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
