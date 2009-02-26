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

import java.util.Collection;
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
public class IntersectionProposition<T> extends AbstractProposition<Integer> {

    public final static String PROPOSITION_MESSAGE_CONTEXT_TOKEN_INTERSECT_DIFF = "prop_intersection_diff";
    public final static String PROPOSITION_MESSAGE_CONTEXT_TOKEN_INTERSECT_MET = "prop_intersection_metset";
    public final static String PROPOSITION_MESSAGE_CONTEXT_TOKEN_INTERSECT_UNMET = "prop_intersection_unmetset";

    // ~ Instance fields --------------------------------------------------------
	private Set<T> met;
	
    Set<T> criteriaSet;
    Set<T> factSet;
    Collection<?> resultValues;

    // ~ Constructors -----------------------------------------------------------

    public IntersectionProposition() {
        super();
    }

    public IntersectionProposition(String id, String propositionName, 
    		ComparisonOperator operator, Integer expectedValue,
            Set<T> criteriaSet, Set<T> factSet) {
        super(id, propositionName, PropositionType.INTERSECTION, operator, expectedValue);
        this.criteriaSet = criteriaSet;
        this.factSet = (factSet == null ? new HashSet<T>() : factSet);
    }

    // ~ Methods ----------------------------------------------------------------

    @Override
    public Boolean apply() {
        this.met = and();
        Integer count = Integer.valueOf(met.size());

        result = checkTruthValue(count, super.expectedValue);

        this.resultValues = met;
        
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    public void buildMessageContextMap() {
        Integer count = met.size();
        Integer expectedValue = (Integer) super.expectedValue;
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_TOKEN_INTERSECT_MET, met.toString());

        Set<T> unMet = andNot();
        Integer needed = expectedValue - count;
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_TOKEN_INTERSECT_DIFF, needed.toString());
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_TOKEN_INTERSECT_UNMET, unMet.toString());

        /*if (result) {
	        report.setSuccessMessage("Intersection constraint fulfilled");
	        return report;
	    }
        String advice = "No advice given";
        if (needed == 0 && super.operator == ComparisonOperator.NOT_EQUAL_TO) {
    		advice = String.format("Found %d course(s) %s but expected not %d", count, met.toString(), expectedValue);
        } else if (needed < 0) {
            switch(super.operator) {
            	case EQUAL_TO:
            		advice = String.format("Found %d course(s) %s but expected only %d", count, met.toString(), expectedValue);
            		break;
            	case LESS_THAN_OR_EQUAL_TO:
	        		advice = String.format("Found %d course(s) %s but expected only %d or less", count, met.toString(), expectedValue);
	        		break;
            	case LESS_THAN:
	        		advice = String.format("Found %d course(s) %s but expected less than %d", count, met.toString(), expectedValue);
	        		break;
        		default:
            }
        } else if (expectedValue > count) {
            switch(super.operator) {
	        	case GREATER_THAN_OR_EQUAL_TO:
	        		advice = String.format("Found %d course(s) %s but expected %d or more", count, met.toString(), expectedValue);
	        		break;
	        	case GREATER_THAN:
	        		advice = String.format("Found %d course(s) %s but expected more than %d", count, met.toString(), expectedValue);
	        		break;
	    		default:
	        }
        } else {
	        advice = String.format("%d of %s is still required", needed, unMet.toString());
        }
        report.setFailureMessage(advice);
        return report;*/
    }

    /**
     * Returns the intersection of the fact set with the criteria set.
     * 
     * @return the intersection
     */
    public Set<T> and() {
        Set<T> rval = new HashSet<T>(factSet);
        rval.retainAll(criteriaSet);

        return rval;
    }

    /**
     * Returns the disjunction of the criteria set from the fact set.
     * 
     * @return
     */
    public Set<T> andNot() {
        HashSet<T> rval = new HashSet<T>(criteriaSet);
        rval.removeAll(factSet);

        return rval;
    }

    /**
     * @return the criteriaSet
     */
    public Set<T> getCriteriaSet() {
        return criteriaSet;
    }

    /**
     * @param criteriaSet
     *            the criteriaSet to set
     */
    public void setCriteriaSet(Set<T> criteriaSet) {
        this.criteriaSet = new HashSet<T>(criteriaSet);
    }

    /**
     * @return the factSet
     */
    public Set<T> getFactSet() {
        return factSet;
    }

    /**
     * @param factSet
     *            the factSet to set
     */
    public void setFactSet(Set<T> factSet) {
        this.factSet = new HashSet<T>(factSet);
    }

    public Collection<?> getResultValues() {
    	return this.resultValues;
    }
}
