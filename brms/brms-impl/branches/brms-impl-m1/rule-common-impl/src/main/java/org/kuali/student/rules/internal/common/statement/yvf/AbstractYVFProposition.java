package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.Proposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractYVFProposition<E> implements Proposition {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(AbstractYVFProposition.class);

    protected Proposition proposition;
	
	@SuppressWarnings("unchecked")
	public Set<E> getSet(String dataType, String stringList) {
		Set<E> set = new HashSet<E>();
		for(String value : stringList.split("\\s*,\\s*")) {
			E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
			set.add(obj);
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	public Set<E> getSet(FactResultDTO factResult) {
		if (factResult == null) {
			throw new PropositionException("FactResultDTO cannot be null");
		}
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		Set<E> set = new HashSet<E>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				// Get only the first column (column 1)
				String value = entry.getValue();
				FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
				String dataType = info.getDataType();
				E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
				set.add(obj);
			}
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getList(String dataType, String stringList) {
		List<E> list = new ArrayList<E>();
		for(String value : stringList.split("\\s*,\\s*")) {
			E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
			list.add(obj);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<E> getList(FactResultDTO factResult) {
		Map<String, FactResultColumnInfoDTO> columnMetaData = factResult.getFactResultTypeInfo().getResultColumnsMap();
		List<E> set = new ArrayList<E>();
		for( Map<String,String> map : factResult.getResultList()) {
			for(Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().equals("column1")) {
					String value = (String) entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
					set.add(obj);
				}
			}
		}
		return set;
	}

	public Proposition getProposition() {
		return this.proposition;
	}

	@Override
	public Boolean apply() {
		Boolean b = proposition.apply();
		if(logger.isDebugEnabled()) {
			logger.debug("Proposition id="+this.proposition.getId());
			logger.debug("Proposition name="+this.proposition.getPropositionName());
			logger.debug("Proposition result="+this.proposition.getResult());
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

    public String toString() {
    	return "YVFProposition[id=" + this.proposition.getId() 
    		+ ", propositionName=" + this.proposition.getPropositionName() 
    		+ ", result="+this.proposition.getResult() + "]";
    }
}
