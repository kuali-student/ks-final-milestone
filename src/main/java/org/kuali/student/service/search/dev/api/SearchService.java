/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.service.search.dev.api;


import java.util.List;


public interface SearchService
{
	
	/**
	* Retrieves the list of searches known by this service
	* 
	* @return list of search information
	*/
	public List<SearchTypeInfo> getSearchTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves meta information about a particular search
	* 
	* @param searchTypeKey - searchTypeKey - search identifier
	* @return list of search information
	*/
	public SearchTypeInfo getSearchType(String searchTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of searches which return results in the specified format
	* 
	* @param searchResultTypeKey - searchResultTypeKey - search result identifier
	* @return list of search information
	*/
	public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of searches which use criteria in the specified format
	* 
	* @param searchCriteriaTypeKey - searchCriteriaTypeKey - search criteria 
	* identifier
	* @return list of search information
	*/
	public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of search results known by this service. Search results 
	* describe the return structure for a search.
	* 
	* @return list of search information
	*/
	public List<SearchResultTypeInfo> getSearchResultTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves meta information about a particular search result. Search results 
	* describe the return structure for a search.
	* 
	* @param searchResultTypeKey - searchResultTypeKey - search result identifier
	* @return search result information
	*/
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves the list of search criteria structures known by this service
	* 
	* @return list of search criteria information
	*/
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves meta information about a particular search criteria
	* 
	* @param searchCriteriaTypeKey - searchCriteriaTypeKey - search criteria 
	* identifier
	* @return search criteria information
	*/
	public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
	
	/**
	* Retrieves results in tabular form for the specified parameters.
	* 
	* @param searchTypeKey - searchTypeKey - search identifier
	* @param queryParamValues - queryParamValueList - list of values for search 
	* parameters
	* @return list of results from the query
	*/
	public List<Result> searchForResults(String searchTypeKey, List<QueryParamValue> queryParamValues)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	;
}

