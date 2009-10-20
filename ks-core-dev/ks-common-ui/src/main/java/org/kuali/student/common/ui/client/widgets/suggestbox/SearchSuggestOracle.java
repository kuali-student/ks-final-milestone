/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

public class SearchSuggestOracle extends IdableSuggestOracle{
    
    private BaseRpcServiceAsync searchService;
    private String searchTypeKey;
    private String searchIdKey;
    private String searchTextKey;
    private String resultIdKey;
    private Callback currentCallback;
    private Request pendingRequest;
    private Callback pendingCallback;
    private HasText textWidget;
    private String resultDisplayKey;
    private List<QueryParamValue> additionalParams = new ArrayList<QueryParamValue>();
    //List of the suggestions appearing in the last query
    private List<IdableSuggestion> lastSuggestions = new ArrayList<IdableSuggestion>();
    
    /**
     * @param searchTypeKey the type to be search on
     * @param searchTextKey the column/key that to search on
     * @param idKey the column/key that is the primary key for this type
     */
    public SearchSuggestOracle(BaseRpcServiceAsync searchService, String searchTypeKey, String searchTextKey, String searchIdKey, String resultIdKey, String resultDisplayKey){
        this.searchService = searchService;
        this.searchTypeKey = searchTypeKey;
        this.searchTextKey = searchTextKey;
        this.searchIdKey = searchIdKey;
        this.resultIdKey = resultIdKey;
        this.resultDisplayKey = resultDisplayKey;
    }
    
    public void setAdditionalQueryParams(List<QueryParamValue> params){
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
        if (currentCallback == null) {
          currentCallback = callback;
          sendRequest(request, wrappedCallback);
        } else {
          pendingRequest = request;
          pendingCallback = callback;
        }
        
    }
    
    public void sendRequest(final Request request, final Callback callback){
        String query = request.getQuery();
        query = query.trim();
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        QueryParamValue qv = new QueryParamValue();
        qv.setKey(searchTextKey);
        qv.setValue(query + "%");
        queryParamValues.add(qv);
        QueryParamValue qv2 = new QueryParamValue();
        qv2.setKey(searchIdKey);
        qv2.setValue(" ");
        queryParamValues.add(qv2);
        if(!(additionalParams.isEmpty())){
            queryParamValues.addAll(additionalParams);
        }
        //case-sensitive?
        if(query.length() > 0){
            searchService.searchForResults(searchTypeKey, queryParamValues, new AsyncCallback<List<Result>>(){
    
                @Override
                public void onFailure(Throwable caught) {
                    // TODO Auto-generated method stub
                    
                }
    
                @Override
                public void onSuccess(List<Result> results) {
                    lastSuggestions = createSuggestions(results, request.getLimit());
                    Response response = new Response(lastSuggestions);
                    callback.onSuggestionsReady(request, response);
                    
                }
                
                private List<IdableSuggestion> createSuggestions(List<Result> results, int limit){
                    List<IdableSuggestion> suggestionsList = new ArrayList<IdableSuggestion>();
                    String query = request.getQuery();
                    query = query.trim();
                    int count = 0;
                    if(results != null){
                        for(Result r: results){
                            if(count == limit){
                                break;
                            }
                            IdableSuggestion theSuggestion = new IdableSuggestion();
                            for(ResultCell c: r.getResultCells()){
                                //System.out.println("Cell: " + c.getKey() + "-" + c.getValue());
                                if(c.getKey().equals(resultDisplayKey)){
                                    String itemText = c.getValue();
                                    theSuggestion.addAttr(c.getKey(), c.getValue());
                                    int index = itemText.toLowerCase().indexOf(query.toLowerCase().trim());
                                    String htmlString = itemText.substring(0,index) + "<b>" + itemText.substring(index, query.length()) + "</b>" + itemText.substring(index + query.length(), itemText.length());
                                    theSuggestion.setDisplayString(htmlString);
                                    theSuggestion.setReplacementString(itemText);
                                }
                                else if(c.getKey().equals(resultIdKey)){
                                     theSuggestion.setId(c.getValue());
                                     theSuggestion.addAttr(c.getKey(), c.getValue());
                                }
                                else{
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
        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
        QueryParamValue qv = new QueryParamValue();
        qv.setKey(searchTextKey);
        qv.setValue("");
        queryParamValues.add(qv);
        QueryParamValue qv2 = new QueryParamValue();
        qv2.setKey(searchIdKey);
        qv2.setValue(id);
        queryParamValues.add(qv2);
        if(!(additionalParams.isEmpty())){
            queryParamValues.addAll(additionalParams);
        }
        searchService.searchForResults(searchTypeKey, queryParamValues, new AsyncCallback<List<Result>>(){
            
            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onSuccess(List<Result> results) {
                IdableSuggestion theSuggestion = null;
                if(results != null){
                    Result r = results.get(0);
                    theSuggestion = new IdableSuggestion();
                    for(ResultCell c: r.getResultCells()){
                        if(c.getKey().equals(resultDisplayKey)){
                            String itemText = c.getValue();
                            theSuggestion.addAttr(c.getKey(), c.getValue());
                            theSuggestion.setDisplayString(itemText);
                            theSuggestion.setReplacementString(itemText);
                        }
                        else if(c.getKey().equals(resultIdKey)){
                             theSuggestion.setId(c.getValue());
                             theSuggestion.addAttr(c.getKey(), c.getValue());
                        }
                        else{
                            theSuggestion.addAttr(c.getKey(), c.getValue());
                        }
                    }
                    
                }
                callback.exec(theSuggestion);
            }
        });
        
    }
}
