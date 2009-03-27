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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.Fact;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.propositions.SumProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class SumRuleProposition<E extends Number> extends AbstractRuleProposition<Number> {
	
    public SumRuleProposition(String id, String propositionName, 
			RulePropositionDTO ruleProposition, Map<String, ?> factMap) {
    	super(id, propositionName, PropositionType.SUM, ruleProposition);

		YieldValueFunctionDTO yvf = ruleProposition.getLeftHandSide().getYieldValueFunction();
		List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
		FactStructureDTO factStructure = factStructureList.get(0);

		if (factStructure == null) {
			throw new PropositionException("Fact structure cannot be null");
		}

		Fact fact = getFacts(factMap, factStructure, MessageContextConstants.PROPOSITION_SUM_COLUMN_KEY);

		super.factDTO = fact.getFactDTO();
		super.factColumn = fact.getFactColumn();
		
		ComparisonOperator comparisonOperator = ComparisonOperator.valueOf(ruleProposition.getComparisonOperatorTypeKey()); 
		BigDecimal expectedValue = new BigDecimal(ruleProposition.getRightHandSide().getExpectedValue());

		if(logger.isDebugEnabled()) {
			logger.debug("\n---------- YVFSumProposition ----------"
					+ "\nFact static="+factStructure.isStaticFact()
					+ "\nFact key="+FactUtil.createFactKey(factStructure)
					+ "\nYield value function type="+yvf.getYieldValueFunctionType()
					+ "\nComparison operator="+comparisonOperator
					+ "\nExpected value="+expectedValue
					+ "\nFact="+factDTO
					+ "\n--------------------------------------------------");
		}

//        super.proposition = new SumProposition<Number>(id, propositionName, 
//        		comparisonOperator, expectedValue, factList); 
        super.proposition = new SumProposition<Number>(id, propositionName, 
        		comparisonOperator, expectedValue, factDTO, factColumn); 
	}

    @Override
    public PropositionReport buildReport() {
        return buildDefaultReport(MessageContextConstants.PROPOSITION_SUM_SUCCESS_MESSAGE, MessageContextConstants.PROPOSITION_SUM_FAILURE_MESSAGE);
    }
}
