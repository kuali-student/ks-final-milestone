package org.kuali.student.common.ui.client.widgets.search;

import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;

public interface SearchBaseRpc{
	public SearchResult search(SearchRequest request);
}
