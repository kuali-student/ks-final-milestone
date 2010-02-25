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
	package org.kuali.student.service.atp.dev.api;
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Map;
	
	
	public class AtpBean
	 implements AtpInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Academic Time Period Name
		*
		* Friendly name for the academic time period
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Academic Time Period Name
		*
		* Friendly name for the academic time period
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Academic Time Period Description
		*
		* Narrative description of an academic time period
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Academic Time Period Description
		*
		* Narrative description of an academic time period
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private Date startDate;
		
		/**
		* Set Start Date
		*
		* Date and time the academic time period became effective. This does not provide a 
		* bound on date ranges or milestones associated with this time period, but instead 
		* indicates the time period proper. This is a similar concept to the effective 
		* date on enumerated values. When an expiration date has been specified, this 
		* field must be less than or equal to the expiration date.
		*/
		@Override
		public void setStartDate(Date startDate)
		{
			this.startDate = startDate;
		}
		
		/**
		* Get Start Date
		*
		* Date and time the academic time period became effective. This does not provide a 
		* bound on date ranges or milestones associated with this time period, but instead 
		* indicates the time period proper. This is a similar concept to the effective 
		* date on enumerated values. When an expiration date has been specified, this 
		* field must be less than or equal to the expiration date.
		*/
		@Override
		public Date getStartDate()
		{
			return this.startDate;
		}
						
		private Date endDate;
		
		/**
		* Set End Date
		*
		* Date and time the academic time period expires. This does not provide a bound on 
		* date ranges or milestones associated with this time period, but instead 
		* indicates the time period proper. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public void setEndDate(Date endDate)
		{
			this.endDate = endDate;
		}
		
		/**
		* Get End Date
		*
		* Date and time the academic time period expires. This does not provide a bound on 
		* date ranges or milestones associated with this time period, but instead 
		* indicates the time period proper. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public Date getEndDate()
		{
			return this.endDate;
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
		* Set Academic Time Period Type
		*
		* Unique identifier for an academic time period type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Academic Time Period Type
		*
		* Unique identifier for an academic time period type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Academic Time Period State
		*
		* The current status of the academic time period. The values for this field are 
		* constrained to those in the atpState enumeration. A separate setup operation 
		* does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Academic Time Period State
		*
		* The current status of the academic time period. The values for this field are 
		* constrained to those in the atpState enumeration. A separate setup operation 
		* does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String key;
		
		/**
		* Set Academic Time Period Identifier
		*
		* Unique identifier for an Academic Time Period (ATP).
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Academic Time Period Identifier
		*
		* Unique identifier for an Academic Time Period (ATP).
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

