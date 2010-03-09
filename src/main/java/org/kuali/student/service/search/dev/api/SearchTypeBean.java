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
	
	
	public class SearchTypeBean
	 implements SearchTypeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Search Type Name
		*
		* Type: string
		*
		* Name of the search type.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Search Type Name
		*
		* Type: string
		*
		* Name of the search type.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		/**
		* Set Search Type Desc
		*
		* Type: string
		*
		* Description of the search type.
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Search Type Desc
		*
		* Type: string
		*
		* Description of the search type.
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private SearchResultTypeInfo searchResultTypeInfo;
		
		/**
		* Set Search Result Type Information
		*
		* Type: searchResultTypeInfo
		*
		* Information about a search result type.
		*/
		@Override
		public void setSearchResultTypeInfo(SearchResultTypeInfo searchResultTypeInfo)
		{
			this.searchResultTypeInfo = searchResultTypeInfo;
		}
		
		/**
		* Get Search Result Type Information
		*
		* Type: searchResultTypeInfo
		*
		* Information about a search result type.
		*/
		@Override
		public SearchResultTypeInfo getSearchResultTypeInfo()
		{
			return this.searchResultTypeInfo;
		}
						
		private SearchCriteriaTypeInfo searchCriteriaTypeInfo;
		
		/**
		* Set Search Criteria Type Information
		*
		* Type: searchCriteriaTypeInfo
		*
		* Information about a search criteria type.
		*/
		@Override
		public void setSearchCriteriaTypeInfo(SearchCriteriaTypeInfo searchCriteriaTypeInfo)
		{
			this.searchCriteriaTypeInfo = searchCriteriaTypeInfo;
		}
		
		/**
		* Get Search Criteria Type Information
		*
		* Type: searchCriteriaTypeInfo
		*
		* Information about a search criteria type.
		*/
		@Override
		public SearchCriteriaTypeInfo getSearchCriteriaTypeInfo()
		{
			return this.searchCriteriaTypeInfo;
		}
						
		private String key;
		
		/**
		* Set Search Type Identifier
		*
		* Type: searchTypeKey
		*
		* Identifier for a search type.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Search Type Identifier
		*
		* Type: searchTypeKey
		*
		* Identifier for a search type.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

