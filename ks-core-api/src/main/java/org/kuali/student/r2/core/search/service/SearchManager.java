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

package org.kuali.student.r2.core.search.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import java.util.List;

@Deprecated
public interface SearchManager {

    /**
     * Retrieves the list of search types known by this service.
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return list of search type information
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves information about a particular search type.
     *
     * @param searchTypeKey identifier of the search type
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return information on the search type
     * @throws DoesNotExistException specified searchTypeKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException searchTypeKey or contextInfo
     *         is missing or null
     * @throws OperationFailedException unable to complete request
     */
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Performs a search.
     *
     * @param searchRequestInfo the search request
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the results of the search
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException searchRequestInfo or
     *         contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, 
            InvalidParameterException, OperationFailedException, PermissionDeniedException;
}
