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

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 *  A base RPC service interface that exposes dictionary and search interfaces
 *  of a service endpoint interface SEI
 * 
 * @author Kuali Student Team
 *
 */
public interface BaseRpcService extends RemoteService{

    /* Dictionary Operations */
    
    public List<String> getObjectTypes();

    public ObjectStructure getObjectStructure(String objectTypeKey);

    public boolean validateObject(String objectTypeKey, String stateKey, String info);

    public boolean validateStructureData(String objectTypeKey, String stateKey, String info);
    
    
    /* Search Operations */
    public List<SearchTypeInfo> getSearchTypes();

    public SearchTypeInfo getSearchType(String searchTypeKey);

    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey);

    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey);

    public List<SearchResultTypeInfo> getSearchResultTypes();

    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey);

    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes();

    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey);

    public SearchResult search(SearchRequest searchRequest);

}
