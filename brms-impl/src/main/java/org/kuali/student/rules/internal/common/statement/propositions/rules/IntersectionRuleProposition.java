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
package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.IntersectionProposition;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class IntersectionRuleProposition<E> extends AbstractRuleProposition<E> {

	public IntersectionRuleProposition(String id, String propositionName, 
			RulePropositionDTO ruleProposition, Map<String, ?> factMap) {
    	super(id, propositionName, PropositionType.INTERSECTION, ruleProposition);

		YieldValueFunctionDTO yvf = ruleProposition.getLeftHandSide().getYieldValueFunction();
		List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
		FactStructureDTO criteria = factStructureList.get(0);
		FactStructureDTO fact = factStructureList.get(1);

		if (criteria == null) {
			throw new PropositionException("Criteria fact structure cannot be null");
		} else if (fact == null) {
			throw new PropositionException("Fact structure cannot be null");
		}

		Set<E> criteriaSet = null;
		Set<E> factSet = null;
		criteriaDTO = null;
		factDTO = null;

		if (criteria.isStaticFact()) {
			String value = criteria.getStaticValue();
			String dataType = criteria.getStaticValueDataType();
			if (value == null || value.isEmpty() || dataType == null || dataType.isEmpty()) {
				throw new PropositionException("Static value and data type cannot be null or empty. Fact structure id: " + criteria.getFactStructureId());
			}
			criteriaSet = getSet(dataType, value);
			criteriaDTO = createStaticFactResult(dataType, value);
		} else {
			if (factMap == null || factMap.isEmpty()) {
				throw new PropositionException("Fact map cannot be null or empty");
			}
			String criteriaKey = FactUtil.createCriteriaKey(criteria);
			criteriaDTO = (FactResultDTO) factMap.get(criteriaKey);

			String column = criteria.getResultColumnKeyTranslations().get(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY);
			if (column == null || column.trim().isEmpty()) {
				throw new PropositionException("Intersection criteria column not found for key '"+
						MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY+"'. Fact structure id: " + criteria.getFactStructureId());
			}

			criteriaSet = getSet(criteriaDTO, column);
			if (criteriaSet == null || criteriaSet.isEmpty()) {
				throw new PropositionException("Criteria facts not found for column '"+column+
						"'. Fact structure id: " + criteria.getFactStructureId());
			}
		}

		if (fact.isStaticFact()) {
			String value = fact.getStaticValue();
			String dataType = fact.getStaticValueDataType();
			if (value == null || value.isEmpty() || dataType == null || dataType.isEmpty()) {
				throw new PropositionException("Static value and data type cannot be null or empty. Fact structure id: "+
						fact.getFactStructureId());
			}
			factSet = getSet(dataType, value);
			factDTO = createStaticFactResult(dataType, value);
		} else {
	    	String factKey = FactUtil.createFactKey(fact);
			factDTO = (FactResultDTO) factMap.get(factKey);

			factColumn = fact.getResultColumnKeyTranslations().get(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY);
			if (factColumn == null || factColumn.trim().isEmpty()) {
				throw new PropositionException("Intersection fact column not found for key '"+
						MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY+"'. Fact structure id: " + fact.getFactStructureId());
			}

			factSet = getSet(factDTO, factColumn);
			if (factSet == null || factSet.isEmpty()) {
				throw new PropositionException("Facts not found for column '"+factColumn+
						"'. Fact structure id: " + fact.getFactStructureId());
			}
		}

		ComparisonOperator comparisonOperator = ComparisonOperator.valueOf(ruleProposition.getComparisonOperatorTypeKey()); 
		Integer expectedValue = Integer.valueOf(ruleProposition.getRightHandSide().getExpectedValue());

		if(logger.isDebugEnabled()) {
			logger.debug("\n---------- YVFIntersectionProposition ----------"
					+ "\nFact static="+fact.isStaticFact()
					+ "\nFact key="+FactUtil.createFactKey(fact)
					+ "\nYield value function type="+yvf.getYieldValueFunctionType()
					+ "\nComparison operator="+comparisonOperator
					+ "\nExpected value="+expectedValue
					+ "\nCriteria static="+criteria.isStaticFact()
					+ "\nCriteria key="+FactUtil.createCriteriaKey(criteria)
					+ "\nCriteria set="+criteriaSet
					+ "\nFact set="+factSet
					+ "\n--------------------------------------------------");
		}

		super.proposition = new IntersectionProposition<E>(id, propositionName, 
        		comparisonOperator, expectedValue, criteriaSet, factSet); 
	}

    @Override
    public PropositionReport buildReport() {
        return buildDefaultReport(MessageContextConstants.PROPOSITION_INTERSECTION_SUCCESS_MESSAGE, MessageContextConstants.PROPOSITION_INTERSECTION_FAILURE_MESSAGE);
    }
}
