package org.kuali.student.brms.internal.common.statement.propositions;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;

public class Fact {
	FactResultInfo factDTO; 
	String factColumn;
	
	public Fact(FactResultInfo factDTO, String factColumn) {
		this.factDTO = factDTO;
		this.factColumn = factColumn;
	}

	public FactResultInfo getFactDTO() {
		return factDTO;
	}

	public String getFactColumn() {
		return factColumn;
	}

}
