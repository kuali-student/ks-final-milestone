package org.kuali.student.lum.nlt.naturallanguage.util;

/**
 * This class attaches a CLU id anchor to a {@link CustomLuStatementInfo}.
 */
public class LuStatementAnchor {
	private CustomLuStatementInfo luStatement;
	private String cluAnchorId;

	public LuStatementAnchor(CustomLuStatementInfo luStatement, String cluAnchorId) {
		this.luStatement = luStatement;
		this.cluAnchorId = cluAnchorId;
	}

	public CustomLuStatementInfo getLuStatement() {
		return this.luStatement;
	}

	public String getCluAnchorId() {
		return this.cluAnchorId;
	}
}
