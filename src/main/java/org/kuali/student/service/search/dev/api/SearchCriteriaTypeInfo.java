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


public interface SearchCriteriaTypeInfo
{
	
	/**
	* Set Search Criteria Type Name
	*
	* Type: string
	*
	* Name of the search criteria type.
	*/
	public void setName(String name);
	
	/**
	* Get Search Criteria Type Name
	*
	* Type: string
	*
	* Name of the search criteria type.
	*/
	public String getName();
	
	
	
	/**
	* Set Search Criteria Type Description
	*
	* Type: string
	*
	* Description of the search criteria type.
	*/
	public void setDesc(String desc);
	
	/**
	* Get Search Criteria Type Description
	*
	* Type: string
	*
	* Description of the search criteria type.
	*/
	public String getDesc();
	
	
	
	/**
	* Set Query Parameters
	*
	* Type: queryParamInfoList
	*
	* List of information about query parameters.
	*/
	public void setQueryParams(List<QueryParamInfo> queryParams);
	
	/**
	* Get Query Parameters
	*
	* Type: queryParamInfoList
	*
	* List of information about query parameters.
	*/
	public List<QueryParamInfo> getQueryParams();
	
	
	
	/**
	* Set Search Criteria Type Identifier
	*
	* Type: searchCriteriaTypeKey
	*
	* Identifier for a search criteria type.
	*/
	public void setKey(String key);
	
	/**
	* Get Search Criteria Type Identifier
	*
	* Type: searchCriteriaTypeKey
	*
	* Identifier for a search criteria type.
	*/
	public String getKey();
	
	
	
}

