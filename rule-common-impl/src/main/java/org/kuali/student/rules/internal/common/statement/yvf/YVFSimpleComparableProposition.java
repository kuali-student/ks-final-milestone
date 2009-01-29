package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.SimpleComparableProposition;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFSimpleComparableProposition<T extends Comparable<T>> extends AbstractYVFProposition<T> {

	public final static String SIMPLE_COMPARABLE_COLUMN_KEY = "key.proposition.column.simplecomparable";

	public YVFSimpleComparableProposition(String id, String propositionName, 
			ComparisonOperator comparisonOperator, T expectedValue, 
			YieldValueFunctionDTO yvf, Map<String, ?> factMap) {
		if (id == null || id.isEmpty()) {
			throw new PropositionException("Proposition id cannot be null");
		} else if (propositionName == null || propositionName.isEmpty()) {
			throw new PropositionException("Proposition name cannot be null");
		} else if (comparisonOperator == null) {
			throw new PropositionException("Comparison operator name cannot be null");
		} else if (expectedValue == null) {
			throw new PropositionException("Expected value cannot be null");
		} else if (yvf == null) {
			throw new PropositionException("Yield value function cannot be null");
		}

		List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
		FactStructureDTO fact = factStructureList.get(0);

		if (fact == null) {
			throw new PropositionException("Fact structure cannot be null");
		}

		T factObject = null;
		FactResultDTO factDTO = null;

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

			String column = fact.getResultColumnKeyTranslations().get(SIMPLE_COMPARABLE_COLUMN_KEY);
			if (column == null || column.trim().isEmpty()) {
				throw new PropositionException("Intersection fact column not found for key '"+
						SIMPLE_COMPARABLE_COLUMN_KEY+"'. Fact structure id: " + fact.getFactStructureId());
			}

			List<T> factList = getList(factDTO, column);
			if (factList == null || factList.isEmpty()) {
				throw new PropositionException("Facts not found for column '"+column+
						"'. Fact structure id: " + fact.getFactStructureId());
			}
			factObject = factList.get(0);
		}

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
        		comparisonOperator, expectedValue, factObject); 
        getReport().setFactResult(factDTO);
	}
}
