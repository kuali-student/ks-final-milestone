package org.kuali.student.common.ui.server.gwt;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.service.impl.SearchDispatcher;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchDispatchRpcGwtServlet extends RemoteServiceServlet implements SearchRpcService{

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(SearchDispatchRpcGwtServlet.class);
	private SearchDispatcher searchDispatcher;
	public SearchDispatchRpcGwtServlet() {
		super();
	}
	
	/**
	 * Delegates to the service responsible for the given search type key
	 * @param searchRequest
	 * @return The searchResult from the delegated search or null
	 * @throws MissingParameterException 
	 */
	@Override
	public SearchResult search(SearchRequest searchRequest) {
		return searchDispatcher.dispatchSearch(searchRequest);
	}

	public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
		this.searchDispatcher = searchDispatcher;
	}
}
