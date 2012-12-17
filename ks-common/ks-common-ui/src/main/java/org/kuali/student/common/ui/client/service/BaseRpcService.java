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

import com.google.gwt.user.client.rpc.RemoteService;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.List;

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
    
    /* Search Operations */
    public List<TypeInfo> getSearchTypes();

    public TypeInfo getSearchType(String searchTypeKey);

    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey);

    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey);

    public List<TypeInfo> getSearchResultTypes();

    public List<TypeInfo> getSearchCriteriaTypes();

    public SearchResultInfo search(SearchRequestInfo searchRequest);

}
