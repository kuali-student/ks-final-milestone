package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.SimpleComparableProposition;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class YVFSimpleComparableProposition<T extends Comparable<T>> extends YVFProposition<T> {

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
		} else if (factMap == null || factMap.isEmpty()) {
			throw new PropositionException("Fact map cannot be null or empty");
		}

		List<FactStructureDTO> factStructureList = yvf.getFactStructureList();
		FactStructureDTO fact = factStructureList.get(0);

		T factObject = null;

		if (fact.isStaticFact()) {
			String value = fact.getStaticValue();
			String dataType = fact.getStaticValueDataType();
			factObject = (T) BusinessRuleUtil.convertToDataType(dataType, value);
		} else {
	    	String factKey = FactUtil.createFactKey(fact);
			FactResultDTO factDTO = (FactResultDTO) factMap.get(factKey);
			String value = factDTO.getResultList().get(0).get("column1");
			Map<String, FactResultColumnInfoDTO> columnMetaData = factDTO.getFactResultTypeInfo().getResultColumnsMap();
			FactResultColumnInfoDTO info = columnMetaData.get("column1");
			String dataType = info.getDataType();
			factObject = (T) BusinessRuleUtil.convertToDataType(dataType, value);
		}

		super.proposition = new SimpleComparableProposition<T>(id, propositionName, 
        		comparisonOperator, expectedValue, factObject); 
	}
}
