package org.kuali.student.rules.internal.common.statement.propositions.functions;

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

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;

public abstract class AbstractFunction<T> implements Function<T> {

	String operation;

	public abstract T compute();

	public void setOperation(String operationType) {
		this.operation = operationType;
	}

	public Boolean containsFactColumnKey(FactResultDTO factDTO, String factColumnKey) {
		if(factDTO.getFactResultTypeInfo().getResultColumnsMap().containsKey(factColumnKey)) {
			return true;
		}
		return false;
	}

	public Collection<String> getCollectionString(FactResultDTO factResult, String column) {
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
    
	public Collection<T> getCollection(FactResultDTO factResult, String column) {
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		List<T> list = new ArrayList<T>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
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
    		return new Integer(value.intValue());
    	}
    	else if (type.equals(Double.class)) {
    		return new Double(value.doubleValue());
    	}
    	else if (type.equals(Long.class)) {
    		return new Long(value.longValue());
    	}
    	else if (type.equals(Float.class)) {
    		return new Float(value.floatValue());
    	}
    	else if (type.equals(Short.class)) {
    		return new Short(value.shortValue());
    	}
    	else if (type.equals(Byte.class)) {
    		return new Byte(value.byteValue());
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
    public static <T> FactResultDTO createFactResult(FactResultColumnInfoDTO columnInfo, Collection<T> factList) {
    	FactResultDTO factResult = new FactResultDTO();
    	
    	FactResultTypeInfoDTO factResultTypeInfo = new FactResultTypeInfoDTO();
    	Map<String, FactResultColumnInfoDTO> columnMap = new HashMap<String, FactResultColumnInfoDTO>();
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
