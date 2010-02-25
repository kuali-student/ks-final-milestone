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
package org.kuali.student.service.lu.dev.api;


import java.util.List;


public interface MembershipQueryInfo
{
	
	/**
	* Set search Type Key
	*
	* Identifier for a search type.
	*/
	public void setSearchTypeKey(String searchTypeKey);
	
	/**
	* Get search Type Key
	*
	* Identifier for a search type.
	*/
	public String getSearchTypeKey();
	
	
	
	/**
	* Set query Parameter Value List
	*
	* List of query parameter values. Not required if the search doesn't extend beyond 
	* the included object.
	*/
	public void setQueryParamValueList(List<QueryParamValue> queryParamValueList);
	
	/**
	* Get query Parameter Value List
	*
	* List of query parameter values. Not required if the search doesn't extend beyond 
	* the included object.
	*/
	public List<QueryParamValue> getQueryParamValueList();
	
	
	
}

