package org.kuali.student.core.search.service.impl;

import java.util.HashMap;
import java.util.Map;

public class CriteriaParseResult {

	private Map<String, Object> bindings;
	private String queryString;

	public Map<String, Object> getBindings() {
		if (bindings == null) {
			bindings = new HashMap<String, Object>();
		}
		return bindings;
	}

	public void setBindings(Map<String, Object> bindings) {
		this.bindings = bindings;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
