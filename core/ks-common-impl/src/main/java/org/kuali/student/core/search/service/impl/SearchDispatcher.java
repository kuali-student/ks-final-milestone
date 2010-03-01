package org.kuali.student.core.search.service.impl;

import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

public interface SearchDispatcher {
	public SearchResult dispatchSearch(SearchRequest searchRequest);
}
