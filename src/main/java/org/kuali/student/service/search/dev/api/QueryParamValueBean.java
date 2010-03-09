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
	
	
	public class QueryParamValueBean
	 implements QueryParamValue	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String value;
		
		/**
		* Set Query Parameter Value
		*
		* Type: string
		*
		* Value(s) for the query parameter.
		*/
		@Override
		public void setValue(String value)
		{
			this.value = value;
		}
		
		/**
		* Get Query Parameter Value
		*
		* Type: string
		*
		* Value(s) for the query parameter.
		*/
		@Override
		public String getValue()
		{
			return this.value;
		}
						
		private String key;
		
		/**
		* Set Query Parameter Identifier
		*
		* Type: queryParamKey
		*
		* Identifier for a query parameter.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Query Parameter Identifier
		*
		* Type: queryParamKey
		*
		* Identifier for a query parameter.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

