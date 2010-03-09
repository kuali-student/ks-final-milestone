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
	package org.kuali.student.service.lo.dev.api;
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Map;
	
	
	public class LoCategoryBean
	 implements LoCategoryInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Learning Objective Category Name
		*
		* Type: string
		*
		* Friendly name of the category
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Learning Objective Category Name
		*
		* Type: string
		*
		* Friendly name of the category
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Learning Objective Category Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the learning objective category
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Learning Objective Category Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the learning objective category
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private String loRepository;
		
		/**
		* Set Learning Objective Repository Key
		*
		* Type: loRepositoryKey
		*
		* Unique identifier for a learning objective repository. Once set in creation, 
		* this is immutable.
		*/
		@Override
		public void setLoRepository(String loRepository)
		{
			this.loRepository = loRepository;
		}
		
		/**
		* Get Learning Objective Repository Key
		*
		* Type: loRepositoryKey
		*
		* Unique identifier for a learning objective repository. Once set in creation, 
		* this is immutable.
		*/
		@Override
		public String getLoRepository()
		{
			return this.loRepository;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this learning objective category became effective. This is a 
		* similar concept to the effective date on enumerated values. When an expiration 
		* date has been specified, this field must be less than or equal to the expiration 
		* date.
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
		* Date and time that this learning objective category became effective. This is a 
		* similar concept to the effective date on enumerated values. When an expiration 
		* date has been specified, this field must be less than or equal to the expiration 
		* date.
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
		* Date and time that this learning objective category expires. This is a similar 
		* concept to the expiration date on enumerated values. If specified, this should 
		* be greater than or equal to the effective date. If this field is not specified, 
		* then no expiration date has been currently defined and should automatically be 
		* considered greater than the effective date.
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
		* Date and time that this learning objective category expires. This is a similar 
		* concept to the expiration date on enumerated values. If specified, this should 
		* be greater than or equal to the effective date. If this field is not specified, 
		* then no expiration date has been currently defined and should automatically be 
		* considered greater than the effective date.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
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
		* Set Learning Objective Category Type
		*
		* Type: loCategoryTypeKey
		*
		* Unique identifier for a learning objective category type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Learning Objective Category Type
		*
		* Type: loCategoryTypeKey
		*
		* Unique identifier for a learning objective category type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Learning Objective Category State
		*
		* Type: string
		*
		* The current status of the learning objective category. The values for this field 
		* are constrained to those in the loCategoryState enumeration. A separate setup 
		* operation does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Learning Objective Category State
		*
		* Type: string
		*
		* The current status of the learning objective category. The values for this field 
		* are constrained to those in the loCategoryState enumeration. A separate setup 
		* operation does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set Learning Objective Category Identifier
		*
		* Type: loCategoryId
		*
		* Unique identifier for a learning objective category record. This is optional, 
		* due to the identifier being set at the time of creation. Once the learning 
		* objective category has been created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Learning Objective Category Identifier
		*
		* Type: loCategoryId
		*
		* Unique identifier for a learning objective category record. This is optional, 
		* due to the identifier being set at the time of creation. Once the learning 
		* objective category has been created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

