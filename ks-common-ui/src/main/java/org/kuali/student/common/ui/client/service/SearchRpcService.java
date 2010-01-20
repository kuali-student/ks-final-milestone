package org.kuali.student.common.ui.client.service;

import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/SearchRpcService")
public interface SearchRpcService {
	public SearchResult search(SearchRequest searchRequest) throws Exception;
}
