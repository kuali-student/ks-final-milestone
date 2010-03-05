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
	import java.util.Map;
	
	
	public class AccreditationBean
	 implements AccreditationInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String orgId;
		
		/**
		* Set Organization Identifier
		*
		* Type: orgId
		*
		* Unique identifier for an organization. This organization is the body behind the 
		* accreditation.
		*/
		@Override
		public void setOrgId(String orgId)
		{
			this.orgId = orgId;
		}
		
		/**
		* Get Organization Identifier
		*
		* Type: orgId
		*
		* Unique identifier for an organization. This organization is the body behind the 
		* accreditation.
		*/
		@Override
		public String getOrgId()
		{
			return this.orgId;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time the accreditation became effective. This is a similar concept to 
		* the effective date on enumerated values. When an expiration date has been 
		* specified, this field must be less than or equal to the expiration date.
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
		* Date and time the accreditation became effective. This is a similar concept to 
		* the effective date on enumerated values. When an expiration date has been 
		* specified, this field must be less than or equal to the expiration date.
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
		* Date and time that this accreditation expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this should be greater than 
		* or equal to the effective date. If this field is not specified, then no 
		* expiration date has been currently defined and should automatically be 
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
		* Date and time that this accreditation expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this should be greater than 
		* or equal to the effective date. If this field is not specified, then no 
		* expiration date has been currently defined and should automatically be 
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
						
		private String id;
		
		/**
		* Set Accreditation Identifier
		*
		* Type: string
		*
		* Identifier for the accreditation.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Accreditation Identifier
		*
		* Type: string
		*
		* Identifier for the accreditation.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

