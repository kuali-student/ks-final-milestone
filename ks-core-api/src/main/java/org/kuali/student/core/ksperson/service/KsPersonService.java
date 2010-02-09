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
package org.kuali.student.core.ksperson.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.service.SearchService;
@WebService(name = "KsPersonService", targetNamespace = "http://student.kuali.org/wsdl/ksperson")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface KsPersonService extends SearchService {

    /**
     * Retrieves results in tabular form for the specified parameters.
     * 
     * Have to redefine method signature to include the @WebParam annotation (I think). The RPC service call breaks otherwise
     * @param searchTypeKey search identifier
     * @param queryParamValues list of values for search parameters
     * @return list of results from the query
     * @throws DoesNotExistException specified search type not found
     * @throws InvalidParameterException invalid searchTypeKey, queryParamValueList
     * @throws MissingParameterException searchTypeKey, queryParamValueList not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<Result> searchForResults(@WebParam(name="searchTypeKey")String searchTypeKey, @WebParam(name="queryParamValues")List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
