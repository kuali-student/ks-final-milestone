package org.kuali.student.brms.internal.common.statement.propositions;

import org.kuali.student.brms.factfinder.dto.FactResultDTO;

public class Fact {
	FactResultDTO factDTO; 
	String factColumn;
	
	public Fact(FactResultDTO factDTO, String factColumn) {
		this.factDTO = factDTO;
		this.factColumn = factColumn;
	}

	public FactResultDTO getFactDTO() {
		return factDTO;
	}

	public String getFactColumn() {
		return factColumn;
	}

}
