package org.kuali.student.common.ui.client.service;

import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/SearchRpcService")
public interface SearchRpcService extends RemoteService {
	public SearchResult search(SearchRequest searchRequest);
}
