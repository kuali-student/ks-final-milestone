package org.kuali.student.rules.internal.common.statement.propositions;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;

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
