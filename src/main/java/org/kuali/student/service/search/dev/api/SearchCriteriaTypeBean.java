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
	
	
	import java.io.Serializable;
	import java.util.List;
	
	
	public class SearchCriteriaTypeBean
	 implements SearchCriteriaTypeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Search Criteria Type Name
		*
		* Type: string
		*
		* Name of the search criteria type.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Search Criteria Type Name
		*
		* Type: string
		*
		* Name of the search criteria type.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		/**
		* Set Search Criteria Type Description
		*
		* Type: string
		*
		* Description of the search criteria type.
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Search Criteria Type Description
		*
		* Type: string
		*
		* Description of the search criteria type.
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private List<QueryParamInfo> queryParams;
		
		/**
		* Set Query Parameters
		*
		* Type: queryParamInfoList
		*
		* List of information about query parameters.
		*/
		@Override
		public void setQueryParams(List<QueryParamInfo> queryParams)
		{
			this.queryParams = queryParams;
		}
		
		/**
		* Get Query Parameters
		*
		* Type: queryParamInfoList
		*
		* List of information about query parameters.
		*/
		@Override
		public List<QueryParamInfo> getQueryParams()
		{
			return this.queryParams;
		}
						
		private String key;
		
		/**
		* Set Search Criteria Type Identifier
		*
		* Type: searchCriteriaTypeKey
		*
		* Identifier for a search criteria type.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Search Criteria Type Identifier
		*
		* Type: searchCriteriaTypeKey
		*
		* Identifier for a search criteria type.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

