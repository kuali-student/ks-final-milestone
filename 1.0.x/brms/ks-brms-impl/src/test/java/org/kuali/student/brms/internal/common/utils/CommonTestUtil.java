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

package org.kuali.student.brms.internal.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.brms.factfinder.dto.FactCriteriaTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactResultColumnInfo;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

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

	public static RulePropositionInfo createRuleProposition(YieldValueFunctionInfo yvf, String expectedValue, String comparisonOperator) {
		return createRuleProposition(yvf, expectedValue, comparisonOperator, null);
	}
	
	public static RulePropositionInfo createRuleProposition(YieldValueFunctionInfo yvf, String expectedValue, String comparisonOperator, String comparisonDataType) {
		RulePropositionInfo ruleProposition = new RulePropositionInfo();
		LeftHandSideInfo leftHandSide = new LeftHandSideInfo();
		ruleProposition.setLeftHandSide(leftHandSide);
		ruleProposition.setComparisonOperatorTypeKey(comparisonOperator);
		ruleProposition.setComparisonDataTypeKey(comparisonDataType);
		leftHandSide.setYieldValueFunction(yvf);
		RightHandSideInfo rightHandSide = new RightHandSideInfo();
		rightHandSide.setExpectedValue(expectedValue);
		ruleProposition.setRightHandSide(rightHandSide);
		return ruleProposition;
	}

	public static FactResultInfo createFact(String[] dataType, String[] value, String[] column) {
		FactResultTypeInfo columnMetaData = createColumnMetaData(dataType, column);
    	FactResultInfo fact = createFactResult(value, column);
    	fact.setFactResultTypeInfo(columnMetaData);
    	return fact;
    }

	public static FactResultInfo createFactResult(String[] values, String columnName) {
		return createFactResult(values, new String[] {columnName});
	}

	public static FactResultInfo createFactResult(String[] values, String[] columnNames) {
		FactResultInfo factResult = new FactResultInfo();
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

	public static FactResultTypeInfo createColumnMetaData(String dataType, String columnName) {
		return createColumnMetaData(new String[] {dataType}, new String[] {columnName});
	}
	
	public static FactResultTypeInfo createColumnMetaData(String[] dataType, String[] columnName) {
    	Map<String, FactResultColumnInfo> columnsInfoMap = new HashMap<String, FactResultColumnInfo>();
    	for(int i=0; i<columnName.length; i++) {
	    	FactResultColumnInfo columnInfo = new FactResultColumnInfo();
	    	columnInfo.setKey(columnName[i]);
	    	columnInfo.setDataType(dataType[i]);
	    	columnsInfoMap.put(columnInfo.getKey(), columnInfo);
    	}
    	FactResultTypeInfo typeInfo = new FactResultTypeInfo();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	return typeInfo;
    }

	public static FactStructureInfo createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureInfo factStructure1 = new FactStructureInfo();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfo criteriaTypeInfo1 = new FactCriteriaTypeInfo();
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
