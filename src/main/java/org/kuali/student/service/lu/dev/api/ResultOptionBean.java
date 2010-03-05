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
	import java.util.Date;
	
	
	public class ResultOptionBean
	 implements ResultOptionInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private RichTextInfo desc;
		
		/**
		* Set Result Option Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the result option.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Result Option Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the result option.
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private String resultUsageTypeKey;
		
		/**
		* Set Result Usage Type
		*
		* Type: resultUsageTypeKey
		*
		* Unique identifier for a result usage type.
		*/
		@Override
		public void setResultUsageTypeKey(String resultUsageTypeKey)
		{
			this.resultUsageTypeKey = resultUsageTypeKey;
		}
		
		/**
		* Get Result Usage Type
		*
		* Type: resultUsageTypeKey
		*
		* Unique identifier for a result usage type.
		*/
		@Override
		public String getResultUsageTypeKey()
		{
			return this.resultUsageTypeKey;
		}
						
		private String resultComponentId;
		
		/**
		* Set Result Component Id
		*
		* Type: resultComponentId
		*
		* Unique identifier for a result component.
		*/
		@Override
		public void setResultComponentId(String resultComponentId)
		{
			this.resultComponentId = resultComponentId;
		}
		
		/**
		* Get Result Component Id
		*
		* Type: resultComponentId
		*
		* Unique identifier for a result component.
		*/
		@Override
		public String getResultComponentId()
		{
			return this.resultComponentId;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this result option became effective. This is a similar 
		* concept to the effective date on enumerated values. When an expiration date has 
		* been specified, this field must be less than or equal to the expiration date.
		*/
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		/**
		* Get Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this result option became effective. This is a similar 
		* concept to the effective date on enumerated values. When an expiration date has 
		* been specified, this field must be less than or equal to the expiration date.
		*/
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		/**
		* Set Expiration Date
		*
		* Type: dateTime
		*
		* Date and time that this result option expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		/**
		* Get Expiration Date
		*
		* Type: dateTime
		*
		* Date and time that this result option expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
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
						
		private String state;
		
		/**
		* Set Result Option State
		*
		* Type: string
		*
		* The current status of the result option. The values for this field are 
		* constrained to those in the resultOptionState enumeration. A separate setup 
		* operation does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Result Option State
		*
		* Type: string
		*
		* The current status of the result option. The values for this field are 
		* constrained to those in the resultOptionState enumeration. A separate setup 
		* operation does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set Result Option Identifier
		*
		* Type: resultOptionId
		*
		* Unique identifier for a result option. This is optional, due to the identifier 
		* being set at the time of creation. Once the result option has been created, this 
		* should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Result Option Identifier
		*
		* Type: resultOptionId
		*
		* Unique identifier for a result option. This is optional, due to the identifier 
		* being set at the time of creation. Once the result option has been created, this 
		* should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

