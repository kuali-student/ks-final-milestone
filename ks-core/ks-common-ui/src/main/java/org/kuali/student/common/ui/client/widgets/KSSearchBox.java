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
