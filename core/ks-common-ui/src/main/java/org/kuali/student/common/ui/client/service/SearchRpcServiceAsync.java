package org.kuali.student.common.ui.client.service;

import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchRpcServiceAsync {
	public void search(SearchRequest searchRequest, AsyncCallback<SearchResult> callback);
}
