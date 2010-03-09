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
	
	
	public class EnumContextBean
	 implements EnumContextInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private FieldDescriptorInfo contextValueDescriptor;
		
		/**
		* Set Enumeration Context Value Descriptor
		*
		* Type: fieldDescriptorInfo
		*
		* Describes the value that can be supplied for a given context, including field 
		* lengths, allowed characters, enumerations, etc. (Note: added "Info" sufiix to 
		* type
		*/
		@Override
		public void setContextValueDescriptor(FieldDescriptorInfo contextValueDescriptor)
		{
			this.contextValueDescriptor = contextValueDescriptor;
		}
		
		/**
		* Get Enumeration Context Value Descriptor
		*
		* Type: fieldDescriptorInfo
		*
		* Describes the value that can be supplied for a given context, including field 
		* lengths, allowed characters, enumerations, etc. (Note: added "Info" sufiix to 
		* type
		*/
		@Override
		public FieldDescriptorInfo getContextValueDescriptor()
		{
			return this.contextValueDescriptor;
		}
						
		private String type;
		
		/**
		* Set Enumeration Context Identifier
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
		* Get Enumeration Context Identifier
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
						
	}

