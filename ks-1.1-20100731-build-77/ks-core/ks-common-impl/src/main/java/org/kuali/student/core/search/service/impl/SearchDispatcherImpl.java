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

package org.kuali.student.core.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchDispatcher;
import org.kuali.student.core.search.service.SearchService;

public class SearchDispatcherImpl implements SearchDispatcher{
	final Logger LOG = Logger.getLogger(SearchDispatcherImpl.class);
	
	private List<SearchService> services;
	
	//Map of search key->service
	private Map<String,SearchService> serviceMap = null;
	
	
	
	public SearchDispatcherImpl() {
		super();
	}

	public SearchDispatcherImpl(SearchService... services){
		super();
		this.services = new ArrayList<SearchService>();
		if(services!=null){
			for(SearchService service:services){
				this.services.add(service);
			}
		}
//		init();
	}
	
	public void init(){
		serviceMap = new HashMap<String,SearchService>();
		
		//Look through each service, grab it's search keys and add them to the map
		for(SearchService service:services){
			if(null==service){
				LOG.warn("Null service passed to SearchDelegator");
			}else{
				try {
					List<SearchTypeInfo> searchTypes = service.getSearchTypes();
					if(searchTypes!=null){
						for(SearchTypeInfo searchType:searchTypes){
							serviceMap.put(searchType.getKey(),service);
						}
					}
				} catch (OperationFailedException e) {
					LOG.warn("Error getting searchTypes",e);
				}
			}
		}
	}
	
	
	/**
	 * Delegates to the service responsible for the given search type key
	 * @param searchRequest
	 * @return The searchResult from the delegated search or null
	 */
	public SearchResult dispatchSearch(SearchRequest searchRequest) {
		//Lazy Load service map.  THis might cause synchronization issues?
		//Needed because of circular bean dependencies... dispatch->serviceImpl->searchMgr->crossSvcMgr->dispatch
		if(serviceMap==null){
			init();
		}
		//Lookup which service to call for given search key and do the search
		if(searchRequest != null){
			String searchKey = searchRequest.getSearchKey();
			SearchService searchService = serviceMap.get(searchKey);
			if(searchService != null){
				SearchResult searchResult;
				try {
					searchResult = searchService.search(searchRequest);
				} catch (Exception e) {
					LOG.warn("Error invoking search",e);
					return null;
				}
				return searchResult;
			}
			LOG.error("Error Dispatching, Search Service not found for search key:"+searchRequest.getSearchKey());
		}
		return null;
	}

	public void setServices(List<SearchService> services) {
		this.services = services;
	}
}
