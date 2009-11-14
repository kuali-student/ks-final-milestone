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
package org.kuali.student.common.ui.client.widgets.selectors;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;


public class SearchComponentConfiguration {
    
	//one or more search criteria that are not visible to the user but are set to certain value(s)
	//based on search context i.e. this limits the possible search to a subset of values upfront
	private List<QueryParamValue> contextCriteria = new ArrayList<QueryParamValue>(); 
	
	//criteria available for user in basic search window
	private List<String> basicCriteria = new ArrayList<String>();
	
	//criteria available for user in advanced search window		
	private List<String> advancedCriteria = new ArrayList<String>();  	
	
	//which fields (columns) will be visible in the search result table	
	private List<String> fieldsReturned = new ArrayList<String>();
	
	//which columns the results will be sorted on	
	//private List<QueryParamValue> resultsSortSequence = new ArrayList<QueryParamValue>();	
	
	private String searchDialogTitle = new String();
	private BaseRpcServiceAsync searchService;
	private String searchTypeKey = new String();
	
	private String resultIdKey;
	//when user selects a row, which column value will be returned from lightbox
	private String retrievedColumnKey;  
	
	//is 'Search' link available?
	private boolean searchLink = false;
	
	//is 'Browse' link available?
	private boolean browseLink = false;	
	
    public SearchComponentConfiguration(List<QueryParamValue> contextCriteria, List<String> basicCriteria, List<String> advancedCriteria) {
    	this.contextCriteria = contextCriteria;
    	this.basicCriteria = basicCriteria;
    	this.advancedCriteria = advancedCriteria;
    }

	public List<QueryParamValue> getContextCriteria() {
		return contextCriteria;
	}

	public List<String> getBasicCriteria() {
		return basicCriteria;
	}

	public List<String> getAdvancedCriteria() {
		return advancedCriteria;
	}

	public String getSearchDialogTitle() {
		return searchDialogTitle;
	}

	public void setSearchDialogTitle(String searchDialogTitle) {
		this.searchDialogTitle = searchDialogTitle;
	}

	public BaseRpcServiceAsync getSearchService() {
		return searchService;
	}

	public void setSearchService(BaseRpcServiceAsync searchService) {
		this.searchService = searchService;
	}

	public String getResultIdKey() {
		return resultIdKey;
	}

	public void setResultIdKey(String resultIdKey) {
		this.resultIdKey = resultIdKey;
	}

	public String getSearchTypeKey() {
		return searchTypeKey;
	}

	public void setSearchTypeKey(String searchTypeKey) {
		this.searchTypeKey = searchTypeKey;
	}

	public String getRetrievedColumnKey() {
		return retrievedColumnKey;
	}

	public void setRetrievedColumnKey(String retrievedColumnKey) {
		this.retrievedColumnKey = retrievedColumnKey;
	}

	
}
