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
	package org.kuali.student.service.dictionary.dev.api;
	
	
	import java.io.Serializable;
	import java.util.List;
	
	
	public class EnumFieldViewBean
	 implements EnumFieldViewInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private List<EnumContextInfo> contextDescriptors;
		
		/**
		* Set Context Descriptors
		*
		* Type: enumContextInfoList
		*
		* List of enumeration context descriptors. It is expected that at least one 
		* context should be listed, so this field is required.
		*/
		@Override
		public void setContextDescriptors(List<EnumContextInfo> contextDescriptors)
		{
			this.contextDescriptors = contextDescriptors;
		}
		
		/**
		* Get Context Descriptors
		*
		* Type: enumContextInfoList
		*
		* List of enumeration context descriptors. It is expected that at least one 
		* context should be listed, so this field is required.
		*/
		@Override
		public List<EnumContextInfo> getContextDescriptors()
		{
			return this.contextDescriptors;
		}
						
		private String key;
		
		/**
		* Set Enumeration Identifier
		*
		* Type: enumerationKey
		*
		* Identifier for an enumeration.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Enumeration Identifier
		*
		* Type: enumerationKey
		*
		* Identifier for an enumeration.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

