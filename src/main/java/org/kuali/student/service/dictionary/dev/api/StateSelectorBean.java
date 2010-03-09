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
	
	
	public class StateSelectorBean
	 implements StateSelectorInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private List<FieldSelectorInfo> field;
		
		/**
		* Set Field Selector
		*
		* Type: fieldSelectorInfoList
		*
		* Information related to a field.
		*/
		@Override
		public void setField(List<FieldSelectorInfo> field)
		{
			this.field = field;
		}
		
		/**
		* Get Field Selector
		*
		* Type: fieldSelectorInfoList
		*
		* Information related to a field.
		*/
		@Override
		public List<FieldSelectorInfo> getField()
		{
			return this.field;
		}
						
		private String key;
		
		/**
		* Set State Identifier
		*
		* Type: string
		*
		* Value for the state field on the object structure, which alters the field level 
		* validation expressed in the structure. If the structure does not possess a state 
		* field, but the structure still needs to be defined (perhaps due to the presence 
		* of dynamic attributes), the value here should read as "default".
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get State Identifier
		*
		* Type: string
		*
		* Value for the state field on the object structure, which alters the field level 
		* validation expressed in the structure. If the structure does not possess a state 
		* field, but the structure still needs to be defined (perhaps due to the presence 
		* of dynamic attributes), the value here should read as "default".
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

