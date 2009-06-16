package org.kuali.student.common.ui.client.widgets.suggestbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.suggestbox.IdableSuggestOracle.IdableSuggestion;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.widgetideas.client.RPCSuggestOracle;

public class SearchSuggestOracle extends IdableSuggestOracle{
    
    private BaseRpcServiceAsync searchService;
    private String searchTypeKey;
    private String searchKey;
    private String resultIdKey;
    private Callback currentCallback;
    private Request pendingRequest;
    private Callback pendingCallback;
    private HasText textWidget;
    private String resultDisplayKey;
    private List<QueryParamValue> additionalParams = new ArrayList<QueryParamValue>();
    
    /**
     * @param searchTypeKey the type to be search on
     * @param searchKey the column/key that to search on
     * @param idKey the column/key that is the primary key for this type
     */
    public SearchSuggestOracle(BaseRpcServiceAsync searchService, String searchTypeKey, String searchKey, String resultIdKey, String resultDisplayKey){
        this.searchService = searchService;
        this.searchTypeKey = searchTypeKey;
        this.searchKey = searchKey;
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
        qv.setKey(searchKey);
        qv.setValue(query + "%");
        queryParamValues.add(qv);
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
                    final List<IdableSuggestion> suggestions = createSuggestions(results, request.getLimit());
                    Response response = new Response(suggestions);
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

    @Override
    public List<String> getAttrKeys() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IdableSuggestion getSuggestion(String id) {
        return null;
    }
    
    public void setTextWidget(HasText widget){
        textWidget = widget;
    }
}