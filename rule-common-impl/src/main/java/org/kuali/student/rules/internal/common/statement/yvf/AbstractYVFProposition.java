package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.ArrayList;
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

public abstract class AbstractYVFProposition<E> implements Proposition {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(AbstractYVFProposition.class);

	public final static String STATIC_FACT_COLUMN = "static.column";

	protected Proposition proposition;
	
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
	public Set<E> getSet(String dataType, String stringList) {
		Set<E> set = new HashSet<E>();
		for(String value : stringList.split("\\s*,\\s*")) {
			try {
				E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
				set.add(obj);
			} catch(NumberFormatException e) {
				logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
				throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
			}
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<E> getSet(FactResultDTO factResult, String column) {
		if (factResult == null) {
			throw new PropositionException("FactResultDTO cannot be null");
		}
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		Set<E> set = new HashSet<E>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
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
	public List<E> getList(String dataType, String stringList) {
		List<E> list = new ArrayList<E>();
		for(String value : stringList.split("\\s*,\\s*")) {
			try {
				E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
				list.add(obj);
			} catch(NumberFormatException e) {
				logger.error("DataType conversion failed. dataType=" + dataType + ", value=", e);
				throw new NumberFormatException(e.getMessage() + ": dataType=" + ", value=");
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<E> getList(FactResultDTO factResult, String column) {
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		List<E> list = new ArrayList<E>();
		for(Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals(column)) {
					String value = (String) entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					try {
						E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
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
		return b;
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

    public String toString() {
    	return "YVFProposition[id=" + this.proposition.getId() 
    		+ ", propositionName=" + this.proposition.getPropositionName() 
    		+ ", type=" + this.proposition.getType() 
    		+ ", result="+this.proposition.getResult() + "]";
    }
}
