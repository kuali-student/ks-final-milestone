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
	
	
	public class LoLoRelationTypeBean
	 implements LoLoRelationTypeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set LO to LO Relation Type Name
		*
		* Type: string
		*
		* Short name of the LO to LO relationship type. This is primarily to be used by 
		* developers and may end up translated in the end system.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get LO to LO Relation Type Name
		*
		* Type: string
		*
		* Short name of the LO to LO relationship type. This is primarily to be used by 
		* developers and may end up translated in the end system.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		/**
		* Set LO to LO Relation Type Description
		*
		* Type: string
		*
		* Narrative description of the LO to LO relationship type.
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get LO to LO Relation Type Description
		*
		* Type: string
		*
		* Narrative description of the LO to LO relationship type.
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private String revName;
		
		/**
		* Set LO to LO Relation Type Reverse Name
		*
		* Type: string
		*
		* Name for the reverse LO to LO relationship type. This is primarily to be used by 
		* developers and may end up translated in the end system.
		*/
		@Override
		public void setRevName(String revName)
		{
			this.revName = revName;
		}
		
		/**
		* Get LO to LO Relation Type Reverse Name
		*
		* Type: string
		*
		* Name for the reverse LO to LO relationship type. This is primarily to be used by 
		* developers and may end up translated in the end system.
		*/
		@Override
		public String getRevName()
		{
			return this.revName;
		}
						
		private String revDesc;
		
		/**
		* Set LO to LO Relation Type Reverse Description
		*
		* Type: string
		*
		* Description of the reverse of the LO to LO relationship type
		*/
		@Override
		public void setRevDesc(String revDesc)
		{
			this.revDesc = revDesc;
		}
		
		/**
		* Get LO to LO Relation Type Reverse Description
		*
		* Type: string
		*
		* Description of the reverse of the LO to LO relationship type
		*/
		@Override
		public String getRevDesc()
		{
			return this.revDesc;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this LO to LO relationship type became effective. This is a 
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
		* Date and time that this LO to LO relationship type became effective. This is a 
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
		* Date and time that this LO to LO relationship type expires. This is a similar 
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
		* Date and time that this LO to LO relationship type expires. This is a similar 
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
						
		private String key;
		
		/**
		* Set LO to LO Relation Type Key
		*
		* Type: loLoRelationId
		*
		* Unique identifier for a LO to LO relationship. This is optional, due to the 
		* identifier being set at the time of creation. Once the relation has been 
		* created, this should be seen as required.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get LO to LO Relation Type Key
		*
		* Type: loLoRelationId
		*
		* Unique identifier for a LO to LO relationship. This is optional, due to the 
		* identifier being set at the time of creation. Once the relation has been 
		* created, this should be seen as required.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

