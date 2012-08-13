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

package org.kuali.student.r1.core.personsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchService;
/**
 * Proxy Search service to the rice PersonService that adds primitive support for the search() and searchForResult()
 * search methods.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
@WebService(endpointInterface = "org.kuali.student.r1.common.search.service.SearchService", name = "PersonSearchService", serviceName = "PersonSearchService", portName = "PersonSearchService", targetNamespace = "http://student.kuali.org/wsdl/personsearch")
public class PersonSearchServiceImpl implements SearchService {
    protected static final Logger LOG = Logger.getLogger(PersonSearchServiceImpl.class);

    private IdentityService identityService;

    public static final String PERSON_ENTITY_TYPE = "PERSON";

    static final public Map<String,String> PERSON_CRITERIA = new HashMap<String,String>();

    static final public Map<String, SearchOperation> searchOperations = new HashMap<String, SearchOperation>();
    
    static {
        PERSON_CRITERIA.put("entityTypeContactInfos.active", "Y");
        PERSON_CRITERIA.put("principals.active", "Y");
        PERSON_CRITERIA.put("active", "Y");
        PERSON_CRITERIA.put("entityTypeContactInfos.entityTypeCode", "PERSON|SYSTEM");
        searchOperations.put(QuickViewByGivenName.SEARCH_TYPE, new QuickViewByGivenName());
    }


    public PersonSearchServiceImpl() {
    }

    /**
     * Return the list of searches we recognize
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypes()
     */
    @Override
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException {
        final List<SearchTypeInfo> searchTypes =  new ArrayList<SearchTypeInfo>(searchOperations.size());
        for (String searchKey : searchOperations.keySet()) {
            SearchOperation so = searchOperations.get (searchKey);
            searchTypes.add(so.getType ());
        }
        return searchTypes;
    }


    @Override
    public SearchResult search(SearchRequest searchRequest) {
        final SearchOperation search = searchOperations.get(searchRequest.getSearchKey());
        if (search != null) {
            return search.search(identityService, searchRequest);
        }
        return new SearchResult();
    }


    /**
     * This overridden method ...
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaType(java.lang.String)
     */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
       throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaTypes()
     */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchResultType(java.lang.String)
     */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchResultTypes()
     */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchType(java.lang.String)
     */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * 
     * This method ...
     * 
     * @return
     * @
     */
    @Deprecated
    public IdentityService getIdentityService()  {
        return identityService;
    }
    
    /**
     * 
     * This method ...
     * 
     * @param identityService
     * @
     */
    @Deprecated
    public void setIdentityService(IdentityService identityService)  {
        this.identityService = identityService;
    }

}
