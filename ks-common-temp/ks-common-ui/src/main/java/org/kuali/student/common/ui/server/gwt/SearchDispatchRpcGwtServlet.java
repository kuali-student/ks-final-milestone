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

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.kuali.student.common.assembly.transform.IdTranslatorFilter;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.search.dto.*;
import org.kuali.student.common.search.service.SearchDispatcher;
import org.kuali.student.common.ui.client.service.SearchRpcService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SearchDispatchRpcGwtServlet extends RemoteServiceServlet implements SearchRpcService {

    private static final long serialVersionUID = 1L;

    private IdTranslatorFilter idTranslatorFilter;

    private SearchDispatcher searchDispatcher;

    private ConcurrentHashMap<SearchRequest, SearchResult> cache = new ConcurrentHashMap<SearchRequest, SearchResult>();

    public SearchDispatchRpcGwtServlet() {
        super();
    }

    /**
     * Delegates to the service responsible for the given search type key
     *
     * @param searchRequest
     * @return The searchResult from the delegated search or null
     * @throws MissingParameterException
     */
    @Override
    public SearchResult search(SearchRequest searchRequest) {
        SearchResult searchResult = searchDispatcher.dispatchSearch(searchRequest);
        List<SearchParam> params  = searchRequest.getParams();
        if(params != null && params.size() > 0){
            SearchParam firstParam = params.get(0);
            if(firstParam.getKey().equals("lu.queryParam.cluVersionIndId")){
                doIdTranslation(searchResult);

            }
        }
        return searchResult;
    }

    @Override
    public SearchResult cachingSearch(SearchRequest searchRequest) {
        if(cache.containsKey(searchRequest)){
            return cache.get(searchRequest);
        }
        SearchResult searchResult = search(searchRequest);
        cache.putIfAbsent(searchRequest, searchResult);
        return searchResult;
    }

    private void doIdTranslation(SearchResult searchResult) {
        for (SearchResultRow searchResultRow : searchResult.getRows()) {
            for (SearchResultCell searchResultCell : searchResultRow.getCells()) {
                String value = searchResultCell.getValue();
                if (value != null && value.startsWith("kuali.atp")) {
                    String newValue = idTranslatorFilter.getTranslationForAtp(value);
                    if (newValue != null) {
                        searchResultCell.setValue(newValue);
                    }
                }
            }
        }
    }

    public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    public void setIdTranslatorFilter(IdTranslatorFilter idTranslatorFilter) {
        this.idTranslatorFilter = idTranslatorFilter;
    }
}
