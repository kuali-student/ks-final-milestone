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
	
	
	public class DateRangeBean
	 implements DateRangeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Date Range Name
		*
		* Friendly name of the date range.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Date Range Name
		*
		* Friendly name of the date range.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Date Range Description
		*
		* Narrative description of the date range.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Date Range Description
		*
		* Narrative description of the date range.
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private String atpKey;
		
		/**
		* Set Academic Time Period Identifier
		*
		* Unique identifier for an Academic Time Period (ATP).
		*/
		@Override
		public void setAtpKey(String atpKey)
		{
			this.atpKey = atpKey;
		}
		
		/**
		* Get Academic Time Period Identifier
		*
		* Unique identifier for an Academic Time Period (ATP).
		*/
		@Override
		public String getAtpKey()
		{
			return this.atpKey;
		}
						
		private Date startDate;
		
		/**
		* Set Range Start Date
		*
		* Start date and time for the date range. This must be less than or equal to the 
		* end date of this range.
		*/
		@Override
		public void setStartDate(Date startDate)
		{
			this.startDate = startDate;
		}
		
		/**
		* Get Range Start Date
		*
		* Start date and time for the date range. This must be less than or equal to the 
		* end date of this range.
		*/
		@Override
		public Date getStartDate()
		{
			return this.startDate;
		}
						
		private Date endDate;
		
		/**
		* Set Range End Date
		*
		* End date and time for the date range. This must be greater than or equal to the 
		* start date of this range.
		*/
		@Override
		public void setEndDate(Date endDate)
		{
			this.endDate = endDate;
		}
		
		/**
		* Get Range End Date
		*
		* End date and time for the date range. This must be greater than or equal to the 
		* start date of this range.
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
		* Set Date Range Type
		*
		* Unique identifier for a date range type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Date Range Type
		*
		* Unique identifier for a date range type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Date Range State
		*
		* The current status of the date range. The values for this field are constrained 
		* to those in the dateRangeState enumeration. A separate setup operation does not 
		* exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Date Range State
		*
		* The current status of the date range. The values for this field are constrained 
		* to those in the dateRangeState enumeration. A separate setup operation does not 
		* exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String key;
		
		/**
		* Set Date Range Key
		*
		* Unique identifier for a date range.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Date Range Key
		*
		* Unique identifier for a date range.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

