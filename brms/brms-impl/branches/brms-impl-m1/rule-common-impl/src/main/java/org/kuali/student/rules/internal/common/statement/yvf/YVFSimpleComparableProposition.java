package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.SimpleComparableProposition;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFSimpleComparableProposition<T extends Comparable<T>> extends AbstractYVFProposition<T> {

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

		if (fact.isStaticFact()) {
			String value = fact.getStaticValue();
			String dataType = fact.getStaticValueDataType();
			if (value == null || value.isEmpty() || dataType == null || dataType.isEmpty()) {
				throw new PropositionException("Static value and data type cannot be null or empty");
			}
			factObject = (T) BusinessRuleUtil.convertToDataType(dataType, value);
		} else {
			if (factMap == null || factMap.isEmpty()) {
				throw new PropositionException("Fact map cannot be null or empty");
			}
	    	String factKey = FactUtil.createFactKey(fact);
			FactResultDTO factDTO = (FactResultDTO) factMap.get(factKey);
			// Get only the first column (column 1)
			List<Map<String,String>> resultList= factDTO.getResultList();
			String value = resultList.get(0).entrySet().iterator().next().getValue();
			
			Map<String, FactResultColumnInfoDTO> columnMetaData = factDTO.getFactResultTypeInfo().getResultColumnsMap();
			// Get only the first column (column 1)
			FactResultColumnInfoDTO info = columnMetaData.entrySet().iterator().next().getValue();;
			String dataType = info.getDataType();
			factObject = (T) BusinessRuleUtil.convertToDataType(dataType, value);
		}

		if(logger.isDebugEnabled()) {
			logger.debug("Yield value function type="+yvf.getYieldValueFunctionType());
			logger.debug("Comparison operator="+comparisonOperator);
			logger.debug("Expected value="+expectedValue);
			logger.debug("Fact object="+factObject);
		}

		super.proposition = new SimpleComparableProposition<T>(id, propositionName, 
        		comparisonOperator, expectedValue, factObject); 
	}
}
