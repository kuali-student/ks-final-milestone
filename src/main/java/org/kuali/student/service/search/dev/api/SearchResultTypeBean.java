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
	
	
	public class SearchResultTypeBean
	 implements SearchResultTypeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Search Result Type Name
		*
		* Type: string
		*
		* Name of the Search Result Type
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Search Result Type Name
		*
		* Type: string
		*
		* Name of the Search Result Type
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		/**
		* Set Search Result Type Description
		*
		* Type: string
		*
		* Description of the Search Result Type
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Search Result Type Description
		*
		* Type: string
		*
		* Description of the Search Result Type
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private List<ResultColumnInfo> resultColumns;
		
		/**
		* Set Result Columns
		*
		* Type: resultColumnInfoList
		*
		* List of information about result columns.
		*/
		@Override
		public void setResultColumns(List<ResultColumnInfo> resultColumns)
		{
			this.resultColumns = resultColumns;
		}
		
		/**
		* Get Result Columns
		*
		* Type: resultColumnInfoList
		*
		* List of information about result columns.
		*/
		@Override
		public List<ResultColumnInfo> getResultColumns()
		{
			return this.resultColumns;
		}
						
		private String key;
		
		/**
		* Set Search Result Type Identifier
		*
		* Type: searchResultTypeKey
		*
		* Identifier for a search result type.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Search Result Type Identifier
		*
		* Type: searchResultTypeKey
		*
		* Identifier for a search result type.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

