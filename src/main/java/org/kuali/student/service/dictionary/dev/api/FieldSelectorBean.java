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
	
	
	public class FieldSelectorBean
	 implements FieldSelectorInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private FieldDescriptorInfo fieldDescriptor;
		
		/**
		* Set Field Descriptor
		*
		* Type: fieldDescriptorInfo
		*
		* Describes a "field" or simple type within a larger object.
		*/
		@Override
		public void setFieldDescriptor(FieldDescriptorInfo fieldDescriptor)
		{
			this.fieldDescriptor = fieldDescriptor;
		}
		
		/**
		* Get Field Descriptor
		*
		* Type: fieldDescriptorInfo
		*
		* Describes a "field" or simple type within a larger object.
		*/
		@Override
		public FieldDescriptorInfo getFieldDescriptor()
		{
			return this.fieldDescriptor;
		}
						
		private Boolean isSelector;
		
		/**
		* Set Selector Indicator
		*
		* Type: boolean
		*
		* Indicates if the field is a selector field, which indicates that changes to this 
		* field may alter the structure of the object type. This may provide insight as to 
		* frequency of calls to a validate operation, as the constraints will not be 
		* externalized.
		*/
		@Override
		public void setIsSelector(Boolean isSelector)
		{
			this.isSelector = isSelector;
		}
		
		/**
		* Get Selector Indicator
		*
		* Type: boolean
		*
		* Indicates if the field is a selector field, which indicates that changes to this 
		* field may alter the structure of the object type. This may provide insight as to 
		* frequency of calls to a validate operation, as the constraints will not be 
		* externalized.
		*/
		@Override
		public Boolean isIsSelector()
		{
			return this.isSelector;
		}
						
		private Boolean isDynamic;
		
		/**
		* Set Dynamic Indicator
		*
		* Type: boolean
		*
		* Indicates if the field is a dynamic attribute.
		*/
		@Override
		public void setIsDynamic(Boolean isDynamic)
		{
			this.isDynamic = isDynamic;
		}
		
		/**
		* Get Dynamic Indicator
		*
		* Type: boolean
		*
		* Indicates if the field is a dynamic attribute.
		*/
		@Override
		public Boolean isIsDynamic()
		{
			return this.isDynamic;
		}
						
		private String key;
		
		/**
		* Set Field Identifier
		*
		* Type: string
		*
		* Identifies the field.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Field Identifier
		*
		* Type: string
		*
		* Identifies the field.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

