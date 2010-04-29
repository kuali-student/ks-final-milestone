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

package org.kuali.student.brms.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.brms.factfinder.dto.FactResultColumnInfo;
import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactResultTypeInfo;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;

public abstract class AbstractFunction<T> implements Function<T> {

	String operation;

	//public abstract T compute();

	public void setOperation(String operationType) {
		this.operation = operationType;
	}

	public Boolean containsFactColumnKey(FactResultInfo factDTO, String factColumnKey) {
		if(factDTO.getFactResultTypeInfo().getResultColumnsMap().containsKey(factColumnKey)) {
			return true;
		}
		return false;
	}

	public Collection<String> getCollectionString(FactResultInfo factResult, String column) {
		Set<String> set = new HashSet<String>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = entry.getValue();
					set.add(value);
				}
			}
		}
		return set;
	}
    
	public Collection<T> getCollection(FactResultInfo factResult, String column) {
		Map<String, FactResultColumnInfo> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		List<T> list = new ArrayList<T>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = entry.getValue();
					FactResultColumnInfo info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						@SuppressWarnings("unchecked")
						T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
						list.add(obj);
					} catch(NumberFormatException e) {
						throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
					}
				}
			}
		}
		return list;
	}
	
	public Number convertToNumber(Class<?> type, Number value) {
    	if (value == null) {
    		return null;
    	}
    	else if (type.isPrimitive()) {
        	throw new RuntimeException("Number type conversion error. Primitives cannot be converted: " + type);
    	}
    	else if (type.equals(String.class)) {
    		return value;
    	}
    	else if (type.equals(Integer.class)) {
    		return Integer.valueOf(value.intValue());
    	}
    	else if (type.equals(Double.class)) {
    		return Double.valueOf(value.doubleValue());
    	}
    	else if (type.equals(Long.class)) {
    		return Long.valueOf(value.longValue());
    	}
    	else if (type.equals(Float.class)) {
    		return Float.valueOf(value.floatValue());
    	}
    	else if (type.equals(Short.class)) {
    		return Short.valueOf(value.shortValue());
    	}
    	else if (type.equals(Byte.class)) {
    		return Byte.valueOf(value.byteValue());
    	}
    	else if (type.equals(BigDecimal.class)) {
    		return new BigDecimal(value.toString());
    	}
    	else if (type.equals(BigInteger.class)) {
    		return new BigDecimal(value.toString()).toBigIntegerExact();
    	}
    	throw new RuntimeException("Number type conversion error. Type not found: " + type);
	}

    /**
     * Creates a single column fact result.
     * 
     * @param columnInfo Column info
     * @param factList 
     * @return
     */
    public static <T> FactResultInfo createFactResult(FactResultColumnInfo columnInfo, Collection<T> factList) {
    	FactResultInfo factResult = new FactResultInfo();
    	
    	FactResultTypeInfo factResultTypeInfo = new FactResultTypeInfo();
    	Map<String, FactResultColumnInfo> columnMap = new HashMap<String, FactResultColumnInfo>();
    	columnMap.put(columnInfo.getKey(), columnInfo);
    	factResultTypeInfo.setResultColumnsMap(columnMap);
    	factResult.setFactResultTypeInfo(factResultTypeInfo);

    	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(T value : factList) {
    		Map<String,String> row = new HashMap<String, String>();
    		row.put(columnInfo.getKey(), value.toString());
    		resultList.add(row);
    	}
    	
    	factResult.setResultList(resultList);
    	return factResult;
    }

}
