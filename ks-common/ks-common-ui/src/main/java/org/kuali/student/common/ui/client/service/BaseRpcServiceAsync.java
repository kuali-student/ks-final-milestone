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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.List;

/**
 * Async methods for dictionary and search operations. 
 * 
 * @author Kuali Student Team
 *
 */
public interface BaseRpcServiceAsync {

    public void getObjectTypes(AsyncCallback<List<String>> callback);

    public void getObjectStructure(String objectTypeKey, AsyncCallback<ObjectStructure> callback);

    void getSearchCriteriaTypes(AsyncCallback<List<TypeInfo>> callback);
    
    void getSearchResultTypes(AsyncCallback<List<TypeInfo>> callback);
    
    void getSearchType(String searchTypeKey, AsyncCallback<TypeInfo> callback);
    
    void getSearchTypes(AsyncCallback<List<TypeInfo>> callback);
    
    void getSearchTypesByCriteria(String searchCriteriaTypeKey, AsyncCallback<List<TypeInfo>> callback);
    
    void getSearchTypesByResult(String searchResultTypeKey, AsyncCallback<List<TypeInfo>> callback);
    
    public void search(SearchRequestInfo searchRequest, AsyncCallback<SearchResultInfo> callback);
}
