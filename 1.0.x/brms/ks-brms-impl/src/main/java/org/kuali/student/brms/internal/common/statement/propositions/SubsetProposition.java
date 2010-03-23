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

import java.util.Collection;
import java.util.Set;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.propositions.functions.Intersection;
import org.kuali.student.brms.internal.common.statement.propositions.rules.AbstractRuleProposition;

/**
 * A constraint that specifies that a fact set must be a subset of a given size of a given set of criteria.
 * 
 * @param <E>
 *            the type of elements being constrained
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SubsetProposition<E> extends AbstractProposition<Integer> {
	private Set<E> met;
	
    private Intersection intersection;

    private Collection<?> resultValues;

    private String criteriaColumn;
    private String factColumn;

    public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_MET = "prop_subset_metset";
    public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_UNMET = "prop_subset_unmetset";
    public final static String PROPOSITION_MESSAGE_CONTEXT_KEY_DIFFERENCE = "prop_subset_diff";

    public SubsetProposition() {
        super();
    }

    public SubsetProposition(String id, String propositionName, 
    		FactResultInfo criteriaDTO, String criteriaColumn, 
    		FactResultInfo factDTO, String factColumn) {
        super(id, propositionName, PropositionType.SUBSET, 
        		ComparisonOperator.EQUAL_TO, new Integer(criteriaDTO.getResultList().size()),
        		criteriaDTO, criteriaColumn, factDTO, factColumn);
		this.factColumn = factColumn;
		this.criteriaColumn = criteriaColumn;
		intersection = new Intersection();
    	intersection.setCriteria(criteriaDTO, criteriaColumn);
    	intersection.setFact(factDTO, factColumn);
    }

    @Override
    public Boolean apply() {
    	intersection.setOperation(Intersection.Operation.INTERSECTION.toString());
    	FactResultInfo factResult = intersection.compute();
    	this.met = AbstractRuleProposition.getSet(factResult, factColumn);

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

        intersection.setOperation(Intersection.Operation.SUBSET_DIFFERENCE.toString());
    	Set<E> unMet = AbstractRuleProposition.getSet(intersection.compute(), factColumn);

        Integer diff = super.expectedValue - count;
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_KEY_MET, this.met);
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_KEY_UNMET, unMet);
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_KEY_DIFFERENCE, diff);
    }

    public Collection<?> getResultValues() {
    	return this.resultValues;
    }
}
