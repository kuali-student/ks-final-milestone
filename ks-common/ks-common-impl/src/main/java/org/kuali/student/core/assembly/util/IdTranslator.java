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

package org.kuali.student.core.assembly.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.service.SearchDispatcher;

/**
 * Builds and issues SearchRequests parameterized with the information from LookupMetadata and the provided ID
 * 
 * @author Kuali Student Team
 *
 */
public class IdTranslator {
    
    private static final String ENUMERATION = "enumeration";
	private List<SearchParam> additionalParams = new ArrayList<SearchParam>();
    private SearchDispatcher searchDispatcher;
    
    final Logger LOG = Logger.getLogger(IdTranslator.class);

    public IdTranslator(SearchDispatcher searchDispatcher) throws AssemblyException {
    	this.searchDispatcher = searchDispatcher;
    }
    
	private SearchRequest buildSearchRequestById(LookupMetadata lookupMetadata, String searchId) {
    	SearchRequest sr = new SearchRequest();
    	sr.setNeededTotalResults(false);
    	
    	sr.setSearchKey(lookupMetadata.getSearchTypeId());


    	List<SearchParam> searchParams = new ArrayList<SearchParam>();
    	if (lookupMetadata.getSearchParamIdKey() != null) {
    		//FIXME: workaround until orch dict can handle multi part keys on initiallookup defs
    		if (lookupMetadata.getSearchParamIdKey().contains(ENUMERATION)) {
    			for (LookupParamMetadata p : lookupMetadata.getParams()) {
    				if (p.getWriteAccess() != null && p.getWriteAccess().equals(WriteAccess.NEVER) && p.getDefaultValueString() != null) {
    					SearchParam param = createParam(p.getKey(), p.getDefaultValueString());
    					searchParams.add(param);   							    
    				}
    				else if (p.getWriteAccess() == null || !p.getWriteAccess().equals(WriteAccess.NEVER)){
    					SearchParam param = createParam(p.getKey(), searchId);
    					searchParams.add(param);   								
    				}
    			}
    		}
    		else {
    			SearchParam param = createParam(lookupMetadata.getSearchParamIdKey(), searchId);
    			searchParams.add(param);	
    		}

    		sr.setParams(searchParams); 		
    	}	
    	else {
    		LOG.warn("Unable to build search request for " + lookupMetadata.getSearchTypeId() + " translation for id " + searchId);
    		
    	}

    	
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
