/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.internal.common.statement.yvf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.PropositionException;
import org.kuali.student.rules.internal.common.statement.propositions.Proposition;
import org.kuali.student.rules.internal.common.statement.propositions.SumProposition;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;

public class YVFSumProposition<E extends Number> implements Proposition {

	private SumProposition<E> proposition;
	
	public YVFSumProposition(String id, String propositionName, ComparisonOperator comparisonOperator, BigDecimal expectedValue, Object fact) {
		if (id == null || id.isEmpty()) {
			throw new PropositionException("Proposition id cannot be null");
		} else if (propositionName == null || propositionName.isEmpty()) {
			throw new PropositionException("Proposition name cannot be null");
		} else if (comparisonOperator == null) {
			throw new PropositionException("Comparison operator name cannot be null");
		} else if (expectedValue == null) {
			throw new PropositionException("Expected value cannot be null");
		} else if (fact == null) {
			throw new PropositionException("Fact cannot be null");
		} else if (!(fact instanceof FactResultDTO)) {
			throw new PropositionException("Fact must be an instance of org.kuali.student.rules.factfinder.dto.FactResultDTO");
		}

		FactResultDTO factDTO = (FactResultDTO) fact;
		List<E> factSet = getList(factDTO);

        this.proposition = new SumProposition<E>(id, propositionName, 
        		comparisonOperator, expectedValue, factSet); 
	}

	private List<E> getList(FactResultDTO criteria) {
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
	
	public SumProposition<E> getProposition() {
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
