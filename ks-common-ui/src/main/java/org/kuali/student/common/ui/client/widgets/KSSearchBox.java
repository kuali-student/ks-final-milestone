package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;

public class KSSearchBox extends Composite{
	private KSTextBox searchField = new KSTextBox();
	private String searchTypeKey;
	private List<String> searchableFields = new ArrayList<String>();
	private List<String> resultColumns = new ArrayList<String>();
	private BaseRpcServiceAsync searchService;
	
	public KSSearchBox(){
		searchField.addKeyDownHandler(new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				 if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					 performSearch();
				 }
				
			}
		});
		searchField.addStyleName("KS-SearchBox");
		this.initWidget(searchField);
	}
	
	private void performSearch(){
		String query = searchField.getText();
		query = query.trim();
		if(query.length() > 0){
	        query = query.toLowerCase();
	        query = query.replace(" ", "%");
	        query = "%" + query + "%";
	        List<SearchParam> searchParams = new ArrayList<SearchParam>();
	        for(String field: searchableFields){
		        SearchParam searchParam = new SearchParam();
		        searchParam.setKey(field);
		        searchParam.setValue(query);
		        searchParams.add(searchParam);
	        }
	        if(searchService != null){
	        	SearchRequest searchRequest = new SearchRequest();
	        	searchRequest.setParams(searchParams);
	        	searchRequest.setSearchKey(searchTypeKey);
	        	searchService.search(searchRequest, new AsyncCallback<SearchResult>(){
	        		
	                @Override
	                public void onFailure(Throwable caught) {
	                    // TODO Auto-generated method stub
	                }
	    
	                @Override
	                public void onSuccess(SearchResult results) {
	                
	                }
	                
	            });
		    }
	    }
	}
	
	public void setSearchProperties(BaseRpcServiceAsync searchService, String searchTypeKey, List<String> searchableFields, List<String> resultColumns){
		this.searchTypeKey = searchTypeKey;
		this.searchableFields = searchableFields;
		this.resultColumns = resultColumns;
	}
	
	public void addRequiredSearchParameter(String parameterKey, String parameterValue){
		
	}
}
