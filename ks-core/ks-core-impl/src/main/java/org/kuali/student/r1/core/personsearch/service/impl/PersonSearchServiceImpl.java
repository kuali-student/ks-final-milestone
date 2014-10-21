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

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchTypeInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proxy Search service to the rice PersonService that adds primitive support for the search() and searchForResult()
 * search methods.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.r2.core.search.service.SearchService", name = "PersonSearchService", serviceName = "PersonSearchService", portName = "PersonSearchService", targetNamespace = "http://student.kuali.org/wsdl/personsearch")
public class PersonSearchServiceImpl implements SearchService {

    private IdentityService identityService;

    public static final String PERSON_ENTITY_TYPE = "PERSON";
    public static final String NAMESPACE_PERSONSEACH = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "personsearch";
    public static final String PERSONSEACH_SERVICE_NAME_LOCAL_PART = "PersonSearchService";

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
        super();
    }

    /**
     * Return the list of searches we recognize
     *
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchTypes(org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws OperationFailedException {
        final List<TypeInfo> searchTypes =  new ArrayList<TypeInfo>(searchOperations.size());
        for (Map.Entry<String, SearchOperation> entry: searchOperations.entrySet()) {
            SearchOperation so = entry.getValue();
            searchTypes.add(toTypeInfo(so.getType()));
        }
        return searchTypes;
    }

    private TypeInfo toTypeInfo(SearchTypeInfo searchTypeInfo){
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setKey(searchTypeInfo.getKey());
        typeInfo.setName(searchTypeInfo.getName());
        RichTextInfo textInfo = new RichTextInfo();
        textInfo.setPlain(searchTypeInfo.getDesc());
        textInfo.setFormatted(searchTypeInfo.getDesc());
        typeInfo.setDescr(textInfo);
        return typeInfo;
    }


    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo) {
        final SearchOperation search = searchOperations.get(searchRequest.getSearchKey());
        if (search != null) {
            return search.search(identityService, searchRequest);
        }
        return new SearchResultInfo();
    }



    /**
     * This overridden method ...
     *
     * @see org.kuali.student.r2.common.search.service.SearchService#getSearchType(String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        SearchOperation so = searchOperations.get (searchTypeKey);
        if (so != null){
            return toTypeInfo(so.getType());
        }
        return null;
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
