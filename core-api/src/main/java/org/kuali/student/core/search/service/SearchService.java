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
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
@WebService(name = "SearchService", targetNamespace = "http://org.kuali.student/core/search")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SearchService {


    /** 
     * Retrieves the list of search types known by this service.
     * @param None No Parameters
     * @return list of search type information
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypes", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesResponse", targetNamespace="http://org.kuali.student/core/search")
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
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchType", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesResponse", targetNamespace="http://org.kuali.student/core/search")
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
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByResult", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByResultResponse", targetNamespace="http://org.kuali.student/core/search")
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
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByCriteria", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchTypesByCriteriaResponse", targetNamespace="http://org.kuali.student/core/search")
    public List<SearchTypeInfo> getSearchTypesByCriteria(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search result types known by this service. Search result types describe the return structure for a search.
     * @param None No Parameters
     * @return list of search result type information
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultTypes", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultTypesResponse", targetNamespace="http://org.kuali.student/core/search")
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
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultType", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchResultTypeResponse", targetNamespace="http://org.kuali.student/core/search")
    public SearchResultTypeInfo getSearchResultType(@WebParam(name="searchResultTypeKey")String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search criteria types known by this service.
     * @param None No parameters
     * @return list of search criteria type information
     * @throws OperationFailedException unable to complete request
	 */
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaTypes", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSSearchCriteriaTypesResponse", targetNamespace="http://org.kuali.student/core/search")
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
    @RequestWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaType", targetNamespace="http://org.kuali.student/core/search")    
    @ResponseWrapper(className="org.kuali.student.core.search.service.jaxws.GetSearchCriteriaTypeResponse", targetNamespace="http://org.kuali.student/core/search")
    public SearchCriteriaTypeInfo getSearchCriteriaType(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}
