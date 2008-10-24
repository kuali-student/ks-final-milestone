package org.kuali.student.rules.internal.common.statement.yvf;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.statement.propositions.Proposition;
import org.kuali.student.rules.internal.common.statement.propositions.SubsetProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

public class YVFSubsetProposition implements Proposition {

	private SubsetProposition<String> proposition;

	public YVFSubsetProposition(String propositionName, Object criteria, Object fact) {
		FactResultDTO criteriaDTO = (FactResultDTO) criteria;
		FactResultDTO factDTO = (FactResultDTO) fact;

		Set<String> criteriaSet = getSet(criteriaDTO);
		Set<String> factSet = getSet(factDTO);

        this.proposition = new SubsetProposition<String>(propositionName, criteriaSet, factSet); 
	}

	private Set<String> getSet(FactResultDTO criteria) {
		Set<String> set = new HashSet<String>();
		for( Map<String,Object> map : criteria.getResultList()) {
			for(Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey().equals("column1")) {
					String value = (String) entry.getValue();
					set.add(value);
				}
			}
		}
		return set;
	}

	public SubsetProposition<?> getProposition() {
		return this.proposition;
	}

	@Override
	public Boolean apply() {
		return proposition.apply();
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
