package org.kuali.student.lum.nlt.naturallanguage.util;

import org.kuali.student.lum.lu.entity.LuStatement;

public class LuStatementAnchor {
	private LuStatement luStatement;
	private String cluAnchorId;

	public LuStatementAnchor(LuStatement luStatement, String cluAnchorId) {
		this.luStatement = luStatement;
		this.cluAnchorId = cluAnchorId;
	}

	public LuStatement getLuStatement() {
		return this.luStatement;
	}

	public String getCluAnchorId() {
		return this.cluAnchorId;
	}
}
