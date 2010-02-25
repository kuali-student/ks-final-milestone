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
	import java.util.Map;
	
	
	public class LuCodeBean
	 implements LuCodeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String desc;
		
		/**
		* Set LU Code Description
		*
		* The description of the code.
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get LU Code Description
		*
		* The description of the code.
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private String value;
		
		/**
		* Set LU Code Value
		*
		* The code value.
		*/
		@Override
		public void setValue(String value)
		{
			this.value = value;
		}
		
		/**
		* Get LU Code Value
		*
		* The code value.
		*/
		@Override
		public String getValue()
		{
			return this.value;
		}
						
		private Map<String,String> attributes;
		
		/**
		* Set Generic/dynamic attributes
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public void setAttributes(Map<String,String> attributes)
		{
			this.attributes = attributes;
		}
		
		/**
		* Get Generic/dynamic attributes
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public Map<String,String> getAttributes()
		{
			return this.attributes;
		}
						
		private MetaInfo metaInfo;
		
		/**
		* Set Create/Update meta info
		*
		* Create and last update info for the structure. This is optional and treated as 
		* read only since the data is set by the internals of the service during 
		* maintenance operations.
		*/
		@Override
		public void setMetaInfo(MetaInfo metaInfo)
		{
			this.metaInfo = metaInfo;
		}
		
		/**
		* Get Create/Update meta info
		*
		* Create and last update info for the structure. This is optional and treated as 
		* read only since the data is set by the internals of the service during 
		* maintenance operations.
		*/
		@Override
		public MetaInfo getMetaInfo()
		{
			return this.metaInfo;
		}
						
		private String type;
		
		/**
		* Set LU Code Type
		*
		* Unique identifier for a learning unit code type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get LU Code Type
		*
		* Unique identifier for a learning unit code type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
	}

