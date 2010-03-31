/*
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

package org.kuali.student.core.assembly.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.service.impl.SearchDispatcher;

/**
 * Builds and issues SearchRequests parameterized with the information from LookupMetadata and the provided ID
 * 
 * @author Kuali Student Team
 *
 */
public class IdTranslator {
    
    private List<SearchParam> additionalParams = new ArrayList<SearchParam>();
    private SearchDispatcher searchDispatcher;
    public IdTranslator(SearchDispatcher searchDispatcher) throws AssemblyException {
    	this.searchDispatcher = searchDispatcher;
    }
    
	private SearchRequest buildSearchRequestById(LookupMetadata lookupMetadata, String searchId) {
    	SearchRequest sr = new SearchRequest();
    	sr.setNeededTotalResults(false);
    	
    	sr.setSearchKey(lookupMetadata.getSearchTypeId());

		List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam param2 = createParam(lookupMetadata.getSearchParamIdKey(), searchId);
		searchParams.add(param2);
		
    	sr.setParams(searchParams);
    	
    	sr.getParams().addAll(additionalParams);

        return sr;
    }
    
    private SearchParam createParam(String key, String value) {
    	SearchParam param = new SearchParam();
    	
    	if(key == null) {
			param.setKey("");
		} else {
			param.setKey(key);
		}

    	if(value == null) {
			param.setValue("");
		} else {
			param.setValue(value);
		}
    	
    	return param;
    }
    
    public IdTranslation getTranslation(LookupMetadata lookupMetadata, String id) throws AssemblyException {
        SearchRequest searchRequest = buildSearchRequestById(lookupMetadata, id);
        SearchResult searchResults = null;
		
		searchResults = searchDispatcher.dispatchSearch(searchRequest);
			
		
        IdTranslation result = null; 
        if(searchResults != null && searchResults.getRows().size() > 0){
        	result = new IdTranslation();
        	result.setId(id);
        	
        	SearchResultRow r = searchResults.getRows().get(0);
            for(SearchResultCell c: r.getCells()){
                if(c.getKey().equals(lookupMetadata.getResultDisplayKey())){
                    result.addAttribute(c.getKey(), c.getValue());
                    result.setDisplay(c.getValue());
                } else {
                    result.addAttribute(c.getKey(), c.getValue());
                }
            }
        }
        return result;
        
    }
    
}
