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

public abstract class YVFProposition<E> implements Proposition {

	Proposition proposition;
	
	@SuppressWarnings("unchecked")
	public Set<E> getSet(String dataType, String stringList) {
		Set<E> set = new HashSet<E>();
		for(String value : stringList.split(",")) {
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
				if (entry.getKey().equals("column1")) {
					String value = entry.getValue();
					FactResultColumnInfoDTO info = columnMetaData.get(entry.getKey());
					String dataType = info.getDataType();
					E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
					set.add(obj);
				}
			}
		}
		return set;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getList(String dataType, String stringList) {
		List<E> list = new ArrayList<E>();
		for(String value : stringList.split(",")) {
			E obj = (E) BusinessRuleUtil.convertToDataType(dataType, value);
			list.add(obj);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<E> getList(FactResultDTO criteria) {
		Map<String, FactResultColumnInfoDTO> columnMetaData = criteria.getFactResultTypeInfo().getResultColumnsMap();
		List<E> set = new ArrayList<E>();
		for( Map<String,String> map : criteria.getResultList()) {
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
		return proposition.apply();
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
}
