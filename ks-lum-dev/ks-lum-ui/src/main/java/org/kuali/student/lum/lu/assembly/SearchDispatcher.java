package org.kuali.student.lum.lu.assembly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.search.service.SearchService;

public class SearchDispatcher {
	final Logger LOG = Logger.getLogger(SearchDispatcher.class);
	
	//Map of search key->service
	private Map<String,SearchService> serviceMap;
	
	public SearchDispatcher(SearchService... services){
		super();
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
					// TODO Auto-generated catch block
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
		//Lookup which service to call for given search key and do the search
		if(searchRequest != null){
			String searchKey = searchRequest.getSearchKey();
			SearchService searchService = serviceMap.get(searchKey);
			if(searchService != null){
				SearchResult searchResult;
				try {
					searchResult = searchService.search(searchRequest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOG.warn("Error invoking search",e);
					return null;
				}
				return searchResult;
			}
		}
		return null;
	}
}
