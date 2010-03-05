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
	
	
	public class MilestoneBean
	 implements MilestoneInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Milestone Name
		*
		* Type: string
		*
		* Name of the milestone.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Milestone Name
		*
		* Type: string
		*
		* Name of the milestone.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Milestone Description
		*
		* Type: richTextInfo
		*
		* Description of the milestone.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Milestone Description
		*
		* Type: richTextInfo
		*
		* Description of the milestone.
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
		* Type: atpKey
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
		* Type: atpKey
		*
		* Unique identifier for an Academic Time Period (ATP).
		*/
		@Override
		public String getAtpKey()
		{
			return this.atpKey;
		}
						
		private Date milestoneDate;
		
		/**
		* Set Milestone Date
		*
		* Type: dateTime
		*
		* Date and time of the milestone.
		*/
		@Override
		public void setMilestoneDate(Date milestoneDate)
		{
			this.milestoneDate = milestoneDate;
		}
		
		/**
		* Get Milestone Date
		*
		* Type: dateTime
		*
		* Date and time of the milestone.
		*/
		@Override
		public Date getMilestoneDate()
		{
			return this.milestoneDate;
		}
						
		private Map<String,String> attributes;
		
		/**
		* Set Generic/dynamic attributes
		*
		* Type: attributeInfoList
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
		* Type: attributeInfoList
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
		* Type: metaInfo
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
		* Type: metaInfo
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
		* Set Milestone Type
		*
		* Type: milestoneTypeKey
		*
		* Unique identifier for a milestone type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Milestone Type
		*
		* Type: milestoneTypeKey
		*
		* Unique identifier for a milestone type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Milestone State
		*
		* Type: string
		*
		* The current status of the milestone. The values for this field are constrained 
		* to those in the milestoneState enumeration. A separate setup operation does not 
		* exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Milestone State
		*
		* Type: string
		*
		* The current status of the milestone. The values for this field are constrained 
		* to those in the milestoneState enumeration. A separate setup operation does not 
		* exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String key;
		
		/**
		* Set Milestone Key
		*
		* Type: milestoneKey
		*
		* Unique identifier for a milestone.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Milestone Key
		*
		* Type: milestoneKey
		*
		* Unique identifier for a milestone.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

