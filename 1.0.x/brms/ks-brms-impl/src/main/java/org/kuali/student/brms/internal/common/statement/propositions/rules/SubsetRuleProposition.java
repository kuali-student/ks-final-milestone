/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.internal.common.statement.propositions.rules;

import java.util.List;
import java.util.Map;

import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.brms.internal.common.statement.propositions.Fact;
import org.kuali.student.brms.internal.common.statement.propositions.PropositionType;
import org.kuali.student.brms.internal.common.statement.propositions.SubsetProposition;
import org.kuali.student.brms.internal.common.statement.report.PropositionReport;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

public class SubsetRuleProposition<E> extends AbstractRuleProposition<E> {

	public SubsetRuleProposition(String id, String propositionName, 
			RulePropositionInfo ruleProposition, Map<String, ?> factMap) {
    	super(id, propositionName, PropositionType.SUBSET, ruleProposition);

		YieldValueFunctionInfo yvf = ruleProposition.getLeftHandSide().getYieldValueFunction();
		List<FactStructureInfo> factStructureList = yvf.getFactStructureList();
		FactStructureInfo criteriaStructure = factStructureList.get(0);
		FactStructureInfo factStructure = factStructureList.get(1);
		
		if (criteriaStructure == null) {
			throw new PropositionException("Criteria fact structure cannot be null");
		} else if (factStructure == null) {
			throw new PropositionException("Fact structure cannot be null");
		}
		
		Fact fact = getFacts(factMap, factStructure, MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY);
		super.factDTO = fact.getFactDTO();
		super.factColumn = fact.getFactColumn();

		Fact criteria = getFacts(factMap, criteriaStructure, MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY);
		super.criteriaDTO = criteria.getFactDTO();
		String criteriaColumn = criteria.getFactColumn();
		
		if(logger.isDebugEnabled()) {
			logger.debug("\n---------- YVFSubsetProposition ----------"
					+ "\nFact static="+factStructure.isStaticFact()
					+ "\nFact key="+FactUtil.createFactKey(factStructure)
					+ "\nYield value function type="+yvf.getYieldValueFunctionType()
					+ "\nCriteria static="+criteriaStructure.isStaticFact()
					+ "\nCriteria key="+FactUtil.createCriteriaKey(criteriaStructure)
					+ "\nCriteria fact="+super.criteriaDTO
					+ "\nFact="+super.factDTO
					+ "\n--------------------------------------------------");
		}

		super.proposition = new SubsetProposition<E>(id, propositionName, 
				criteriaDTO,criteriaColumn, factDTO, factColumn); 
	}

    @Override
    public PropositionReport buildReport() {
        return buildDefaultReport(MessageContextConstants.PROPOSITION_SUBSET_SUCCESS_MESSAGE, MessageContextConstants.PROPOSITION_SUBSET_FAILURE_MESSAGE);
    }
}
