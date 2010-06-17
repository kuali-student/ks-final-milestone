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

package org.kuali.student.core.search.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
@WebService(name = "SearchService", targetNamespace = "http://student.kuali.org/wsdl/search")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SearchService {

    /** 
     * Retrieves the list of search types known by this service.
     * @param None No Parameters
     * @return list of search type information
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypes", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public List<SearchTypeInfo> getSearchTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular search type.
     * @param searchTypeKey identifier of the search type
     * @return information on the search type
     * @throws DoesNotExistException specified searchTypeKey not found
     * @throws InvalidParameterException invalid searchTypeKey
     * @throws MissingParameterException searchTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchType", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypeResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public SearchTypeInfo getSearchType(@WebParam(name="searchTypeKey")String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types which return results in the specified format.
     * @param searchResultTypeKey identifier of the search result type
     * @return list of search type information
     * @throws DoesNotExistException specified searchResultTypeKey not found
     * @throws InvalidParameterException invalid searchResultTypeKey
     * @throws MissingParameterException searchResultTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByResult", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByResultResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public List<SearchTypeInfo> getSearchTypesByResult(@WebParam(name="searchResultTypeKey")String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types which use criteria in the specified format.
     * @param searchCriteriaTypeKey identifier of the search criteria
     * @return list of search type information
     * @throws DoesNotExistException specified searchCriteriaTypeKey not found
     * @throws InvalidParameterException invalid searchCriteriaTypeKey
     * @throws MissingParameterException searchCriteriaTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByCriteria", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByCriteriaResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public List<SearchTypeInfo> getSearchTypesByCriteria(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search result types known by this service. Search result types describe the return structure for a search.
     * @param None No Parameters
     * @return list of search result type information
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultTypes", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultTypesResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public List<SearchResultTypeInfo> getSearchResultTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular search result type. Search result types describe the return structure for a search.
     * @param searchResultTypeKey identifier of the search result type
     * @return information on the search result type
     * @throws DoesNotExistException specified searchResultTypeKey not found
     * @throws InvalidParameterException invalid searchResultTypeKey
     * @throws MissingParameterException searchResultTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultType", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultTypeResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public SearchResultTypeInfo getSearchResultType(@WebParam(name="searchResultTypeKey")String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search criteria types known by this service.
     * @param None No parameters
     * @return list of search criteria type information
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaTypes", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaTypesResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular search criteria type.
     * @param searchCriteriaTypeKey identifier of the search criteria type
     * @return information on the search criteria type
     * @throws DoesNotExistException specified searchCriteriaTypeKey not found
     * @throws InvalidParameterException invalid searchCriteriaTypeKey
     * @throws MissingParameterException searchCriteriaTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaType", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaTypeResponse", targetNamespace="http://student.kuali.org/wsdl/search")
    public SearchCriteriaTypeInfo getSearchCriteriaType(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.Search", targetNamespace="http://student.kuali.org/wsdl/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.SearchResponse", targetNamespace="http://student.kuali.org/wsdl/search")
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException;

}
