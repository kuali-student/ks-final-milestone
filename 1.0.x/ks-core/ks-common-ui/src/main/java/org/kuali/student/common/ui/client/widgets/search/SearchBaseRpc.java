package org.kuali.student.common.ui.client.widgets.search;

import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

public interface SearchBaseRpc{
	public SearchResult search(SearchRequest request);
}
