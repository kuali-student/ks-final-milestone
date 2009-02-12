package org.kuali.student.rules.internal.common.statement.propositions.rules;

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
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.Proposition;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRuleProposition<T> implements Proposition {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(AbstractRuleProposition.class);

	public final static String STATIC_FACT_COLUMN = "static.column";

	protected Proposition proposition;

	protected FactResultDTO criteriaDTO = null;
	protected FactResultDTO factDTO = null;
	protected String factColumn = STATIC_FACT_COLUMN;
	
	public FactResultColumnInfoDTO getFactResultColumnInfo(FactResultDTO factResult, String columnKey) {
		if (factResult == null) {
			throw new PropositionException("FactResultDTO cannot be null");
		}
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		return columnMetaData.get(columnKey);
    }

    public FactResultDTO createFactResult(FactResultColumnInfoDTO columnInfo, Collection<?> factList) {
    	FactResultDTO factResult = new FactResultDTO();
    	FactResultTypeInfoDTO factResultTypeInfo = new FactResultTypeInfoDTO();
    	Map<String, FactResultColumnInfoDTO> columnMap = new HashMap<String, FactResultColumnInfoDTO>();

    	FactResultColumnInfoDTO newColumnInfo = new FactResultColumnInfoDTO();
    	newColumnInfo.setKey(columnInfo.getKey());
    	newColumnInfo.setDescription(columnInfo.getDescription());
    	newColumnInfo.setDataType(columnInfo.getDataType());
    	newColumnInfo.setName(columnInfo.getName());
    	columnMap.put(newColumnInfo.getKey(), newColumnInfo);
    	factResultTypeInfo.setResultColumnsMap(columnMap);
    	factResult.setFactResultTypeInfo(factResultTypeInfo);

    	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(Object value : factList) {
    		Map<String,String> row = new HashMap<String, String>();
    		row.put(columnInfo.getKey(), value.toString());
    		resultList.add(row);
    	}
    	
    	factResult.setResultList(resultList);
    	return factResult;
    }

    /**
     * Creates a fact result. This method is usually called from static facts.
     * 
     * @param columnDataType Column data type
     * @param factList Comma separated list of values
     * @return Fact result
     */
    public FactResultDTO createStaticFactResult(String columnDataType, String factList) {
    	FactResultDTO factResult = new FactResultDTO();
    	
    	FactResultTypeInfoDTO factResultTypeInfo = new FactResultTypeInfoDTO();
    	Map<String, FactResultColumnInfoDTO> columnMap = new HashMap<String, FactResultColumnInfoDTO>();
    	FactResultColumnInfoDTO columnInfo = new FactResultColumnInfoDTO();
    	columnInfo.setKey(STATIC_FACT_COLUMN);
    	columnInfo.setDescription("Static Fact Column");
    	columnInfo.setDataType(columnDataType);
    	columnInfo.setName(STATIC_FACT_COLUMN);
    	columnMap.put(columnInfo.getKey(), columnInfo);
    	factResultTypeInfo.setResultColumnsMap(columnMap);
    	factResult.setFactResultTypeInfo(factResultTypeInfo);

    	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		for(String value : factList.split("\\s*,\\s*")) {
    		Map<String,String> row = new HashMap<String, String>();
    		row.put(STATIC_FACT_COLUMN, value);
    		resultList.add(row);
    	}
    	
    	factResult.setResultList(resultList);
    	return factResult;
    }

	@SuppressWarnings("unchecked")
	public Set<T> getSet(String dataType, String stringList) {
		Set<T> set = new HashSet<T>();
		for(String value : stringList.split("\\s*,\\s*")) {
			try {
				T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
				set.add(obj);
			} catch(NumberFormatException e) {
				logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
				throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
			}
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<T> getSet(FactResultDTO factResult, String column) {
		if (factResult == null) {
			throw new PropositionException("FactResultDTO cannot be null");
		}
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		Set<T> set = new HashSet<T>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
						set.add(obj);
					} catch(NumberFormatException e) {
						logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
						throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
					}
				}
			}
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(String dataType, String stringList) {
		List<T> list = new ArrayList<T>();
		for(String value : stringList.split("\\s*,\\s*")) {
			try {
				T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
				list.add(obj);
			} catch(NumberFormatException e) {
				logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
				throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(FactResultDTO factResult, String column) {
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		List<T> list = new ArrayList<T>();
		for(Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = (String) entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						T obj = (T) BusinessRuleUtil.convertToDataType(dataType, value);
						list.add(obj);
					} catch(NumberFormatException e) {
						logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
						throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
					}
				}
			}
		}
		return list;
	}

	public Proposition getProposition() {
		return this.proposition;
	}

	@Override
	public Boolean apply() {
		Boolean b = proposition.apply();
		if(logger.isDebugEnabled()) {
			logger.debug("apply()"
					+ "\nProposition id="+this.proposition.getId()
					+ "\nProposition name="+this.proposition.getPropositionName()
					+ "\nProposition result="+this.proposition.getResult());
		}
		FactResultColumnInfoDTO columnInfo = getFactResultColumnInfo(factDTO, factColumn);
		FactResultDTO factResult = createFactResult(columnInfo, this.proposition.getResultValues());
		this.proposition.getReport().setPropositionResult(factResult);
		this.proposition.getReport().setCriteriaResult(criteriaDTO);
		this.proposition.getReport().setFactResult(factDTO);
		return b;
	}
	
	@Override
	public PropositionReport buildReport() {
		return this.proposition.buildReport();
	}

	@Override
	public String getId() {
		return this.proposition.getId();
	}

	@Override
	public String getPropositionName() {
		return this.proposition.getPropositionName();
	}

	@Override
	public PropositionReport getReport() {
		return this.proposition.getReport();
	}

	@Override
	public Boolean getResult() {
		return this.proposition.getResult();
	}
	
	@Override
	public PropositionType getType() {
		return this.proposition.getType();
	}

	@Override
	public Collection<?> getResultValues() {
		return this.proposition.getResultValues();
	}

    public String toString() {
    	return "YVFProposition[id=" + this.proposition.getId() 
    		+ ", propositionName=" + this.proposition.getPropositionName() 
    		+ ", type=" + this.proposition.getType() 
    		+ ", result="+this.proposition.getResult() + "]";
    }
}
