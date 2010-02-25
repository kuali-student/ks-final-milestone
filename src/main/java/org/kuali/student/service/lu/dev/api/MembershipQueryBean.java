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
	
	
	import java.io.Serializable;
	import java.util.List;
	
	
	public class MembershipQueryBean
	 implements MembershipQueryInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String searchTypeKey;
		
		/**
		* Set search Type Key
		*
		* Identifier for a search type.
		*/
		@Override
		public void setSearchTypeKey(String searchTypeKey)
		{
			this.searchTypeKey = searchTypeKey;
		}
		
		/**
		* Get search Type Key
		*
		* Identifier for a search type.
		*/
		@Override
		public String getSearchTypeKey()
		{
			return this.searchTypeKey;
		}
						
		private List<QueryParamValue> queryParamValueList;
		
		/**
		* Set query Parameter Value List
		*
		* List of query parameter values. Not required if the search doesn't extend beyond 
		* the included object.
		*/
		@Override
		public void setQueryParamValueList(List<QueryParamValue> queryParamValueList)
		{
			this.queryParamValueList = queryParamValueList;
		}
		
		/**
		* Get query Parameter Value List
		*
		* List of query parameter values. Not required if the search doesn't extend beyond 
		* the included object.
		*/
		@Override
		public List<QueryParamValue> getQueryParamValueList()
		{
			return this.queryParamValueList;
		}
						
	}

