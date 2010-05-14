package org.kuali.student.rules.internal.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class CommonTestUtil {
	public static List<BigDecimal> createList(String list) {
    	List<BigDecimal> set = new ArrayList<BigDecimal>();
        for(String s : list.split(",")) {
        	set.add(new BigDecimal(s.trim()));
        }
        return set;
    }
  
	public static Calendar createDate(int year, int month, int day, int hourOfDay, int minute) {
		return createGregorianCalendar(year, month, day, hourOfDay, minute);
    }

	public static GregorianCalendar createGregorianCalendar(int year, int month, int day, int hourOfDay, int minute) {
		GregorianCalendar cal = new GregorianCalendar();
    	cal.set(year, month-1, day, hourOfDay, minute, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal;
    }

	public static RulePropositionDTO createRuleProposition(YieldValueFunctionDTO yvf, String expectedValue, String comparisonOperator) {
		return createRuleProposition(yvf, expectedValue, comparisonOperator, null);
	}
	
	public static RulePropositionDTO createRuleProposition(YieldValueFunctionDTO yvf, String expectedValue, String comparisonOperator, String comparisonDataType) {
		RulePropositionDTO ruleProposition = new RulePropositionDTO();
		LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
		ruleProposition.setLeftHandSide(leftHandSide);
		ruleProposition.setComparisonOperatorTypeKey(comparisonOperator);
		ruleProposition.setComparisonDataTypeKey(comparisonDataType);
		leftHandSide.setYieldValueFunction(yvf);
		RightHandSideDTO rightHandSide = new RightHandSideDTO();
		rightHandSide.setExpectedValue(expectedValue);
		ruleProposition.setRightHandSide(rightHandSide);
		return ruleProposition;
	}

	public static FactResultDTO createFact(String[] dataType, String[] value, String[] column) {
		FactResultTypeInfoDTO columnMetaData = createColumnMetaData(dataType, column);
    	FactResultDTO fact = createFactResult(value, column);
    	fact.setFactResultTypeInfo(columnMetaData);
    	return fact;
    }

	public static FactResultDTO createFactResult(String[] values, String columnName) {
		return createFactResult(values, new String[] {columnName});
	}

	public static FactResultDTO createFactResult(String[] values, String[] columnNames) {
		FactResultDTO factResult = new FactResultDTO();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for(int i=0; i<values.length;) {
			Map<String, String> row = new HashMap<String, String>();
			for(String column : columnNames) {
				row.put(column, values[i++]);
			}
			resultList.add(row);
		}

		factResult.setResultList(resultList);
		return factResult;
	}

	public static FactResultTypeInfoDTO createColumnMetaData(String dataType, String columnName) {
		return createColumnMetaData(new String[] {dataType}, new String[] {columnName});
	}
	
	public static FactResultTypeInfoDTO createColumnMetaData(String[] dataType, String[] columnName) {
    	Map<String, FactResultColumnInfoDTO> columnsInfoMap = new HashMap<String, FactResultColumnInfoDTO>();
    	for(int i=0; i<columnName.length; i++) {
	    	FactResultColumnInfoDTO columnInfo = new FactResultColumnInfoDTO();
	    	columnInfo.setKey(columnName[i]);
	    	columnInfo.setDataType(dataType[i]);
	    	columnsInfoMap.put(columnInfo.getKey(), columnInfo);
    	}
    	FactResultTypeInfoDTO typeInfo = new FactResultTypeInfoDTO();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	return typeInfo;
    }

	public static FactStructureDTO createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureDTO factStructure1 = new FactStructureDTO();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfoDTO criteriaTypeInfo1 = new FactCriteriaTypeInfoDTO();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
    }

	public static boolean containsResult(List<Map<String,String>> list, String column, String value) {
    	for(Map<String,String> map : list) {
    		if (map.get(column).equals(value)) {
    			return true;
    		}
    	}
    	return false;
    }
}
