package org.kuali.student.rules.internal.common.statement.propositions.rules;

import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.SimpleComparableProposition;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class SimpleComparableRuleProposition<T extends Comparable<T>> extends AbstractRuleProposition<T> {

	public final static String SIMPLE_COMPARABLE_COLUMN_KEY = "key.proposition.column.simplecomparable";

	public SimpleComparableRuleProposition(String id, String propositionName, 
			RulePropositionDTO ruleProposition, Map<String, ?> factMap) {
		if (id == null || id.isEmpty()) {
			throw new PropositionException("Proposition id cannot be null");
		} else if (propositionName == null || propositionName.isEmpty()) {
			throw new PropositionException("Proposition name cannot be null");
		} else if (ruleProposition.getComparisonOperatorTypeKey() == null) {
			throw new PropositionException("Comparison operator cannot be null");
		} else if (ruleProposition.getRightHandSide().getExpectedValue() == null) {
			throw new PropositionException("Expected value cannot be null");
		} else if (ruleProposition == null) {
			throw new PropositionException("Rule proposition cannot be null");
		}

		YieldValueFunctionDTO yvf = ruleProposition.getLeftHandSide().getYieldValueFunction();
		List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
		FactStructureDTO fact = factStructureList.get(0);

		if (fact == null) {
			throw new PropositionException("Fact structure cannot be null");
		}

		T factObject = null;
		factDTO = null;

		if (fact.isStaticFact()) {
			String value = fact.getStaticValue();
			String dataType = fact.getStaticValueDataType();
			if (value == null || value.isEmpty() || dataType == null || dataType.isEmpty()) {
				throw new PropositionException("Static value and data type cannot be null or empty");
			}
			factObject = (T) BusinessRuleUtil.convertToDataType(dataType, value);
			factDTO = createStaticFactResult(dataType, value);
		} else {
			if (factMap == null || factMap.isEmpty()) {
				throw new PropositionException("Fact map cannot be null or empty");
			}
	    	String factKey = FactUtil.createFactKey(fact);
			factDTO = (FactResultDTO) factMap.get(factKey);

			// Get only the first column (column 1)
			//List<Map<String,String>> resultList = factDTO.getResultList();
			//String value = resultList.get(0).entrySet().iterator().next().getValue();
			
			//Map<String, FactResultColumnInfoDTO> columnMetaData = factDTO.getFactResultTypeInfo().getResultColumnsMap();
			// Get only the first column (column 1)
			//FactResultColumnInfoDTO info = columnMetaData.entrySet().iterator().next().getValue();

			//String dataType = info.getDataType();
			//factObject = (T) BusinessRuleUtil.convertToDataType(dataType, value);

			factColumn = fact.getResultColumnKeyTranslations().get(SIMPLE_COMPARABLE_COLUMN_KEY);
			if (factColumn == null || factColumn.trim().isEmpty()) {
				throw new PropositionException("Intersection fact column not found for key '"+
						SIMPLE_COMPARABLE_COLUMN_KEY+"'. Fact structure id: " + fact.getFactStructureId());
			}

			List<T> factList = getList(factDTO, factColumn);
			if (factList == null || factList.isEmpty()) {
				throw new PropositionException("Facts not found for column '"+factColumn+
						"'. Fact structure id: " + fact.getFactStructureId());
			}
			factObject = factList.get(0);
		}

		ComparisonOperator comparisonOperator = ComparisonOperator.valueOf(ruleProposition.getComparisonOperatorTypeKey()); 
		@SuppressWarnings("unchecked")
		T expectedValue = (T) BusinessRuleUtil.convertToDataType(ruleProposition.getComparisonDataTypeKey(), ruleProposition.getRightHandSide().getExpectedValue());

		if(logger.isDebugEnabled()) {
			logger.debug("\n---------- YVFSimpleComparableProposition ----------"
					+ "\nFact static="+fact.isStaticFact()
					+ "\nFact key="+FactUtil.createFactKey(fact)
					+ "\nYield value function type="+yvf.getYieldValueFunctionType()
					+ "\nComparison operator="+comparisonOperator
					+ "\nExpected value="+expectedValue
					+ "\nFact object="+factObject
					+ "\n--------------------------------------------------");
		}

		super.proposition = new SimpleComparableProposition<T>(id, propositionName, 
        		comparisonOperator, expectedValue, factObject, ruleProposition); 
	}
}
