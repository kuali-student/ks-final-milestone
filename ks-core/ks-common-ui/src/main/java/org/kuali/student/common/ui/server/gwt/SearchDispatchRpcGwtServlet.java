/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.gwt;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.service.SearchDispatcher;

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
