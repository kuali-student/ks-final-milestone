package org.kuali.student.core.search.service.impl;

import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;

public interface SearchDispatcher {
	public SearchResult dispatchSearch(SearchRequest searchRequest);
}
