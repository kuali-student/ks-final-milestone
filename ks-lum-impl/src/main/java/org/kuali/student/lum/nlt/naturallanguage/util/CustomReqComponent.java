package org.kuali.student.lum.nlt.naturallanguage.util;

import org.kuali.student.lum.lu.entity.ReqComponent;

public class CustomReqComponent {
	private String booleanId;
	private ReqComponent reqComponent;
	
	public CustomReqComponent(ReqComponent reqComponent, String booleanId) {
		this.reqComponent = reqComponent;
		this.booleanId = booleanId;
	}

	public String getBooleanId() {
		return this.booleanId;
	}

	public ReqComponent getReqComponent() {
		return this.reqComponent;
	}
	
	
}
