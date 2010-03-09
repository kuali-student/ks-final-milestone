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
	package org.kuali.student.service.enumerable.dev.api;
	
	
	import java.io.Serializable;
	
	
	public class EnumContextValueBean
	 implements EnumContextValueInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String type;
		
		/**
		* Set Enumeration Context Type
		*
		* Type: enumContextTypeKey
		*
		* Identifier for the context modifier for an enumeration.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Enumeration Context Type
		*
		* Type: enumContextTypeKey
		*
		* Identifier for the context modifier for an enumeration.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String value;
		
		/**
		* Set Enumeration Context Value
		*
		* Type: string
		*
		* Value for the enumeration context
		*/
		@Override
		public void setValue(String value)
		{
			this.value = value;
		}
		
		/**
		* Get Enumeration Context Value
		*
		* Type: string
		*
		* Value for the enumeration context
		*/
		@Override
		public String getValue()
		{
			return this.value;
		}
						
	}

