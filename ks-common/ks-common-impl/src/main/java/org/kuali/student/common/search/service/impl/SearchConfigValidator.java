package org.kuali.student.common.search.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.search.dto.SearchTypeInfo;

public class SearchConfigValidator {
	private Map<String, SearchTypeInfo> searchInfoTypeMap;
	// private Map<String, SearchCriteriaTypeInfo> searchCriteriaTypeMap;
	// private Map<String, SearchResultTypeInfo> searchResultTypeInfoMap;
	private Map<String, String> queryMap;

	public SearchConfigValidator(Map<String, SearchTypeInfo> searchInfoTypeMap,
			Map<String, String> queryMap) {
		this.searchInfoTypeMap = searchInfoTypeMap;
		this.queryMap = queryMap;
	}

	public List<String> validate() {
		List<String> errors = new ArrayList<String>();
		// TODO: validate
		return errors;
	}
}
