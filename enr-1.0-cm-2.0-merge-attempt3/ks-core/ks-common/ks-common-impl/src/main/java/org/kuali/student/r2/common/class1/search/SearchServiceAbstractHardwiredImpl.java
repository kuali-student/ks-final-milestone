/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * Created by Daniel on 4/26/12
 */
package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract class for hard wired impls
 *
 * @author Kuali Student Team
 */
public abstract class SearchServiceAbstractHardwiredImpl
        implements SearchService {

    private GenericEntityDao genericEntityDao;

    public GenericEntityDao getGenericEntityDao() {
        return genericEntityDao;
    }

    public void setGenericEntityDao(GenericEntityDao genericEntityDao) {
        this.genericEntityDao = genericEntityDao;
    }

    /**
     * Get the search type that the sub class implements.
     */
    public abstract TypeInfo getSearchType();

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        TypeInfo st = this.getSearchType();
        if (!searchTypeKey.equals(st.getKey())) {
            throw new DoesNotExistException(searchTypeKey);
        }
        return st;
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(this.getSearchType());
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo contextInfo)
            throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method is not implemented.");
    }

    @Override
    public abstract SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
