package org.kuali.student.core.dao;

import java.util.List;

import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchTypeInfo;

public interface SearchableDao {
	public List<Result> searchForResults(String queryString, SearchTypeInfo searchTypeInfo, List<QueryParamValue> queryParamValues);
}
