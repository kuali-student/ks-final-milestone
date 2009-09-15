package org.kuali.student.lum.nlt.naturallanguage.util;

import org.kuali.student.lum.nlt.naturallanguage.translators.StatementParser;

/**
 * This class attaches a boolean id 
 * (during statement parsing {@link StatementParser}) to 
 * a {@link CustomReqComponentInfo}.
 */
public class ReqComponentReference {
	private String booleanId;
	private CustomReqComponentInfo reqComponent;
	
	public ReqComponentReference(CustomReqComponentInfo reqComponent, String booleanId) {
		this.reqComponent = reqComponent;
		this.booleanId = booleanId;
	}

	public String getBooleanId() {
		return this.booleanId;
	}

	public CustomReqComponentInfo getReqComponent() {
		return this.reqComponent;
	}
	
	
}
