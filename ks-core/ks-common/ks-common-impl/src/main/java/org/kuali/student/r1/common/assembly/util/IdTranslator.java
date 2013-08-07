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

package org.kuali.student.r1.common.assembly.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.LookupParamMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata.WriteAccess;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.util.ContextUtils;

/**
 * Builds and issues SearchRequests parameterized with the information from LookupMetadata and the provided ID
 * 
 * @author Kuali Student Team
 *
 */
public class IdTranslator {
    
    private static final String ENUMERATION = "enumeration";
	private List<SearchParamInfo> additionalParams = new ArrayList<SearchParamInfo>();
    private SearchService searchDispatcher;
    
    final Logger LOG = Logger.getLogger(IdTranslator.class);

    public IdTranslator(SearchService searchDispatcher) throws AssemblyException {
    	this.searchDispatcher = searchDispatcher;
    }
    
	private SearchRequestInfo buildSearchRequestById(LookupMetadata lookupMetadata, String searchId) {
    	SearchRequestInfo sr = new SearchRequestInfo();
    	sr.setNeededTotalResults(false);
    	
    	sr.setSearchKey(lookupMetadata.getSearchTypeId());
    	sr.setSortColumn(lookupMetadata.getResultSortKey());


    	List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
    	if (lookupMetadata.getSearchParamIdKey() != null) {
    		//FIXME: workaround until orch dict can handle multi part keys on initiallookup defs
    		if (lookupMetadata.getSearchParamIdKey().contains(ENUMERATION)) {
    			for (LookupParamMetadata p : lookupMetadata.getParams()) {
    				if (p.getWriteAccess() != null && p.getWriteAccess().equals(WriteAccess.NEVER) && p.getDefaultValueString() != null) {
    					SearchParamInfo param = createParam(p.getKey(), p.getDefaultValueString());
    					searchParams.add(param);   							    
    				}
    				else if (p.getWriteAccess() == null || !p.getWriteAccess().equals(WriteAccess.NEVER)){
    					SearchParamInfo param = createParam(p.getKey(), searchId);
    					searchParams.add(param);   								
    				}
    			}
    		}
    		else {
    			SearchParamInfo param = createParam(lookupMetadata.getSearchParamIdKey(), searchId);
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
    
    private SearchParamInfo createParam(String key, String value) {
    	SearchParamInfo param = new SearchParamInfo();
    	
    	if(key == null) {
			param.setKey("");
		} else {
			param.setKey(key);
		}

    	if(value == null) {
			param.getValues().add("");
		} else {
			param.getValues().add(value);
		}
    	
    	return param;
    }
    
    public IdTranslation getTranslation(LookupMetadata lookupMetadata, String id) throws AssemblyException {
        SearchRequestInfo searchRequest = buildSearchRequestById(lookupMetadata, id);
        SearchResultInfo searchResults = null;

        try {
            searchResults = searchDispatcher.search(searchRequest, ContextUtils.getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        IdTranslation result = null; 
        if(searchResults != null && searchResults.getRows().size() > 0){
        	result = new IdTranslation();
        	result.setId(id);
        	
        	SearchResultRowInfo r = searchResults.getRows().get(0);
            for(SearchResultCellInfo c: r.getCells()){
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

    public String getTranslationForAtp(String value, ContextInfo contextInfo) {
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("atp.search.advancedAtpSearch");
        ArrayList<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey("atp.advancedAtpSearchParam.atpId");
        searchParam.getValues().add(value);
        searchParams.add(searchParam);
        searchRequest.setParams(searchParams);
        SearchResultInfo searchResult = null;
        try {
            searchResult = searchDispatcher.search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(searchResult!= null){
            for (SearchResultRowInfo resultRow : searchResult.getRows()) {
                for (SearchResultCellInfo searchResultCell : resultRow.getCells()) {
                    if(searchResultCell.getKey().equals("atp.resultColumn.atpDescrPlain")){
                        return searchResultCell.getValue();
                    }
                }
            }
        }
        return null;
    }
}
