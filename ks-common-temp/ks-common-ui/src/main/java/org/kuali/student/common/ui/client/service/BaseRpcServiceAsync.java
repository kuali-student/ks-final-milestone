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

package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResult;
import org.kuali.student.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.common.search.dto.SearchTypeInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async methods for dictionary and search operations. 
 * 
 * @author Kuali Student Team
 *
 */
public interface BaseRpcServiceAsync {

    public void getObjectTypes(AsyncCallback<List<String>> callback);

    public void getObjectStructure(String objectTypeKey, AsyncCallback<ObjectStructure> callback);

    void getSearchCriteriaType(String searchCriteriaTypeKey, ContextInfo contextInfo, AsyncCallback<SearchCriteriaTypeInfo> callback);
    
    void getSearchCriteriaTypes(ContextInfo contextInfo, AsyncCallback<List<SearchCriteriaTypeInfo>> callback);
    
    void getSearchResultType(String searchResultTypeKey, ContextInfo contextInfo, AsyncCallback<SearchResultTypeInfo> callback);
    
    void getSearchResultTypes(ContextInfo contextInfo, AsyncCallback<List<SearchResultTypeInfo>> callback);
    
    void getSearchType(String searchTypeKey, ContextInfo contextInfo, AsyncCallback<SearchTypeInfo> callback);
    
    void getSearchTypes(ContextInfo contextInfo, AsyncCallback<List<SearchTypeInfo>> callback);
    
    void getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo contextInfo, AsyncCallback<List<SearchTypeInfo>> callback);
    
    void getSearchTypesByResult(String searchResultTypeKey, ContextInfo contextInfo, AsyncCallback<List<SearchTypeInfo>> callback);
    
    public void search(SearchRequest searchRequest, ContextInfo contextInfo, AsyncCallback<SearchResult> callback);
}
