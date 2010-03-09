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



public interface SearchTypeInfo
{
	
	/**
	* Set Search Type Name
	*
	* Type: string
	*
	* Name of the search type.
	*/
	public void setName(String name);
	
	/**
	* Get Search Type Name
	*
	* Type: string
	*
	* Name of the search type.
	*/
	public String getName();
	
	
	
	/**
	* Set Search Type Desc
	*
	* Type: string
	*
	* Description of the search type.
	*/
	public void setDesc(String desc);
	
	/**
	* Get Search Type Desc
	*
	* Type: string
	*
	* Description of the search type.
	*/
	public String getDesc();
	
	
	
	/**
	* Set Search Result Type Information
	*
	* Type: searchResultTypeInfo
	*
	* Information about a search result type.
	*/
	public void setSearchResultTypeInfo(SearchResultTypeInfo searchResultTypeInfo);
	
	/**
	* Get Search Result Type Information
	*
	* Type: searchResultTypeInfo
	*
	* Information about a search result type.
	*/
	public SearchResultTypeInfo getSearchResultTypeInfo();
	
	
	
	/**
	* Set Search Criteria Type Information
	*
	* Type: searchCriteriaTypeInfo
	*
	* Information about a search criteria type.
	*/
	public void setSearchCriteriaTypeInfo(SearchCriteriaTypeInfo searchCriteriaTypeInfo);
	
	/**
	* Get Search Criteria Type Information
	*
	* Type: searchCriteriaTypeInfo
	*
	* Information about a search criteria type.
	*/
	public SearchCriteriaTypeInfo getSearchCriteriaTypeInfo();
	
	
	
	/**
	* Set Search Type Identifier
	*
	* Type: searchTypeKey
	*
	* Identifier for a search type.
	*/
	public void setKey(String key);
	
	/**
	* Get Search Type Identifier
	*
	* Type: searchTypeKey
	*
	* Identifier for a search type.
	*/
	public String getKey();
	
	
	
}

