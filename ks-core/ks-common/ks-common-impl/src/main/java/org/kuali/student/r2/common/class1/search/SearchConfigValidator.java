package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.core.search.dto.SearchTypeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
