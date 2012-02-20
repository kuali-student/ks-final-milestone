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

package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.LookupParamMetadata;
import org.kuali.student.common.assembly.data.Metadata.WriteAccess;
import org.kuali.student.common.search.dto.SearchParam;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultCell;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.common.search.dto.SortDirection;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.service.CachingSearchService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.service.SearchServiceFactory;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.notification.LoadingDiv;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.Widget;

public class SearchSuggestOracle extends IdableSuggestOracle{
    
    private String searchTypeKey;
    private String searchIdKey;
    private String searchTextKey;
    private String resultIdKey;
    private Callback currentCallback;
    private Request pendingRequest;
    private Callback pendingCallback;
    private HasText textWidget;
    private String resultDisplayKey;
    private String resultSortKey;
    private SortDirection sortDirection;
    private List<SearchParam> additionalParams = new ArrayList<SearchParam>();
    private List<IdableSuggestion> lastSuggestions = new ArrayList<IdableSuggestion>();
    
    private LookupMetadata lookupMetaData;
    private CachingSearchService cachingSearchService = CachingSearchService.getSearchService();
    private SearchRpcServiceAsync searchService = SearchServiceFactory.getSearchService();

    private List<org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion>> searchCompletedCallbacks = new ArrayList<org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion>>();
    
    private LoadingDiv loading = new LoadingDiv();
    
    /**
     * @deprecated
     * @param searchTypeKey the type to be search on
     * @param searchTextKey the column/key that to search on
     * @param idKey the column/key that is the primary key for this type
     */
    public SearchSuggestOracle(String searchTypeKey, String searchTextKey, String searchIdKey, String resultIdKey, String resultDisplayKey){
        this.searchTypeKey = searchTypeKey;
        this.searchTextKey = searchTextKey;
        this.searchIdKey = searchIdKey;
        this.resultIdKey = resultIdKey;
        this.resultDisplayKey = resultDisplayKey;
    }
    
    /*
     * 
     */
    public SearchSuggestOracle(LookupMetadata lookupMetadata) {
    	this.lookupMetaData = lookupMetadata;
        this.searchTypeKey = lookupMetaData.getSearchTypeId();
        
        for (LookupParamMetadata param : lookupMetadata.getParams()) {
        	if ((param.getUsage() != null) && param.getUsage().name().equals("DEFAULT")) {
        		this.searchTextKey = param.getKey();
        	}
        	//Add in any writeaccess never default values to the additional params
        	if(WriteAccess.NEVER.equals(param.getWriteAccess())||param.getDefaultValueString()!=null||param.getDefaultValueList()!=null){
        		SearchParam searchParam = new SearchParam();
        		searchParam.setKey(param.getKey());
				if(param.getDefaultValueList()==null){
					searchParam.setValue(param.getDefaultValueString());
				}else{
					searchParam.setValue(param.getDefaultValueList());
				}
        		additionalParams.add(searchParam);
        	}
        }
        if (this.searchTextKey == null) {
        	KSErrorDialog.show(new Throwable("Cannot find searchTextKey for " + searchTypeKey) );
        }
        
        this.searchIdKey = lookupMetadata.getSearchParamIdKey();
        this.resultIdKey = lookupMetadata.getResultReturnKey();
        this.resultDisplayKey = lookupMetadata.getResultDisplayKey();
        this.resultSortKey = lookupMetadata.getResultSortKey();
        this.sortDirection = lookupMetadata.getSortDirection();
    }

    public void setAdditionalSearchParams(List<SearchParam> params){
        additionalParams = params;
    }
    
    private Callback wrappedCallback = new Callback() {

        public void onSuggestionsReady(Request request, Response response) {
          if (textWidget.getText().equals(request.getQuery())) {
            currentCallback.onSuggestionsReady(request, response);
            pendingRequest = null;
            pendingCallback = null;
          }
          currentCallback = null;
          if (pendingCallback != null) {
            requestSuggestions(pendingRequest, pendingCallback);
            pendingRequest = null;
            pendingCallback = null;
          }
        }

    };
    
    @Override
    public void requestSuggestions(Request request, Callback callback) {
        // Check if the request query is smaller than the minimum size allowed
        String query = request.getQuery().trim();
        int minQuerySize = 0;
        
        //[KSCOR-225] LO's currently use the depricated constructor that does not pass in the 
        // lookupMetaData so we need to do a null check until that is fixed
        if (lookupMetaData != null && lookupMetaData.getMinQuerySize() != null){
            minQuerySize = lookupMetaData.getMinQuerySize().intValue();
        }
        if ((currentCallback == null) && (query.length() >= minQuerySize)){
          final int x = ((Widget)this.textWidget).getAbsoluteLeft() + ((Widget)this.textWidget).getOffsetWidth();
  		  final int y = ((Widget)this.textWidget).getAbsoluteTop() + ((Widget)this.textWidget).getOffsetHeight();
  		  loading.setPopupPositionAndShow(new PositionCallback(){

  				@Override
  				public void setPosition(int offsetWidth, int offsetHeight) {
  					loading.setPopupPosition(x - offsetWidth, y + 1);
  				}
  			});
          currentCallback = callback;
          sendRequest(request, wrappedCallback);
        } else {
          pendingRequest = request;
          pendingCallback = callback;
        }        
    }
    
    private SearchRequest buildSearchRequest(String query, String searchId) {
    	SearchRequest sr = new SearchRequest();
    	sr.setNeededTotalResults(false);
    	sr.setSearchKey(this.searchTypeKey);
    	sr.setSortColumn(this.resultSortKey);
        sr.setSortDirection(this.sortDirection);

		List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam param1 = createParam(this.searchTextKey, query);
		searchParams.add(param1);
		
    	sr.setParams(searchParams);
    	
    	sr.getParams().addAll(additionalParams);

        return sr;
    }
    
    private SearchRequest buildSearchRequestById(String query, String searchId) {
    	SearchRequest sr = new SearchRequest();
    	sr.setNeededTotalResults(false);
    	sr.setSearchKey(this.searchTypeKey);
    	sr.setSortColumn(this.resultSortKey);
        sr.setSortDirection(this.sortDirection);

		List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam param2 = createParam(this.searchIdKey, searchId);
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

    public void sendRequest(final Request request, final Callback callback){
        String query = request.getQuery().trim();
        SearchRequest searchRequest = buildSearchRequest(query, null);
        
        //case-sensitive?
        if(query.length() > 0){
        	searchService.search(searchRequest, new KSAsyncCallback<SearchResult>(){
    
                @Override
                public void onSuccess(SearchResult results) {
                    lastSuggestions = createSuggestions(results, request.getLimit());
                    Response response = new Response(lastSuggestions);
                    loading.hide();
                    callback.onSuggestionsReady(request, response);
                    if (searchCompletedCallbacks != null &&
                            lastSuggestions != null && lastSuggestions.size() == 1) {
                        for (org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion> callback : searchCompletedCallbacks) {
                            callback.exec(lastSuggestions.get(0));
                        }
                    }
                }
                
                @Override
                public void onFailure(Throwable caught) {
                	loading.hide();
                	super.onFailure(caught);
                }
                
                private List<IdableSuggestion> createSuggestions(SearchResult results, int limit){
                    List<IdableSuggestion> suggestionsList = new ArrayList<IdableSuggestion>();
                    String query = request.getQuery();
                    query = query.trim();
                    int count = 0;
                    if(results != null){
                        for (SearchResultRow r: results.getRows()){
                            if(count == limit){
                                break;
                            }

                            IdableSuggestion theSuggestion = new IdableSuggestion();
                            for(SearchResultCell c: r.getCells()){
                                if(c.getKey().equals(resultDisplayKey)){
                                    String itemText = c.getValue();
                                    theSuggestion.addAttr(c.getKey(), c.getValue());
                                    int index = (" " + itemText).toLowerCase().indexOf(" " + query.toLowerCase().trim());
                                    
                                    if (index < 0) {
                                        //temporary fix to stop index out of bound exception in hosted mode
//                                        continue;
                                        //Including fuzzy search results in the display list.
                                        theSuggestion.setDisplayString(itemText);
                                        theSuggestion.setReplacementString(itemText);
                                    	//FIXME handle case when search for text is not appearing within search result - should not happen (misconfiguration)
                                        continue;
                                    }
                                    
                                    String htmlString = itemText.substring(0,index) + "<b>" + itemText.substring(index, index + query.length()) + "</b>" + itemText.substring(index + query.length(), itemText.length());
                                    theSuggestion.setDisplayString(htmlString);
                                    theSuggestion.setReplacementString(itemText);
                                    if (c.getKey().equals(resultIdKey)){
                                        theSuggestion.setId(c.getValue());
                                    }
                                } else if(c.getKey().equals(resultIdKey)){
                                     theSuggestion.setId(c.getValue());
                                     theSuggestion.addAttr(c.getKey(), c.getValue());
                                } else{
                                    theSuggestion.addAttr(c.getKey(), c.getValue());
                                }
                            }
                            suggestionsList.add(theSuggestion);
                            count++;
                        }
                    }
                    return suggestionsList;
                }
            });
        }
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

/*    
    public IdableSuggestion getSuggestionById(String id) {
        IdableSuggestion suggestion = null;
        if(!(lastSuggestions.isEmpty())){
            for(IdableSuggestion is: lastSuggestions){
                if(is.getId().equals(id)){
                    suggestion = is;
                    break;
                }
            }
        }
        if(suggestion == null){
            searchOnId(id);
        }
        return suggestion;
    }*/

    @Override
    public IdableSuggestion getSuggestionByText(String text){
        IdableSuggestion suggestion = null;
        for(IdableSuggestion is: lastSuggestions){
            if(is.getReplacementString().trim().equalsIgnoreCase(text.trim())){
                suggestion = is;
                break;
            }
        }
        return suggestion;
    }
    
    public void setTextWidget(HasText widget){
        textWidget = widget;
    }

    @Override
    public void getSuggestionByIdSearch(String id, final org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion> callback) {
        SearchRequest searchRequest = buildSearchRequestById(null, id);
        cachingSearchService.search(searchRequest, new KSAsyncCallback<SearchResult>(){
            @Override
            public void onSuccess(SearchResult results) {
                IdableSuggestion theSuggestion = null;
                if(results != null && !results.getRows().isEmpty()){
                	SearchResultRow r = results.getRows().get(0);
                    theSuggestion = new IdableSuggestion();
                    for(SearchResultCell c: r.getCells()){
                        if(c.getKey().equals(resultDisplayKey)){
                            String itemText = c.getValue();
                            theSuggestion.addAttr(c.getKey(), c.getValue());
                            theSuggestion.setDisplayString(itemText);
                            theSuggestion.setReplacementString(itemText);
                        } else if(c.getKey().equals(resultIdKey)){
                             theSuggestion.setId(c.getValue());
                             theSuggestion.addAttr(c.getKey(), c.getValue());
                        } else {
                            theSuggestion.addAttr(c.getKey(), c.getValue());
                        }
                    }
                }
                callback.exec(theSuggestion);
            }
        });
    }

    @Override
    public void addSearchCompletedCallback(org.kuali.student.common.ui.client.mvc.Callback<IdableSuggestion> callback) {
        searchCompletedCallbacks.add(callback);
    }
    
}
