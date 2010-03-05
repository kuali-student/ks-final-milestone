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
	
	
	public class AtpSeasonalTypeBean
	 implements AtpSeasonalTypeInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Academic Time Period Seasonal Type Name
		*
		* Type: string
		*
		* Friendly name of the academic time period seasonal type.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Academic Time Period Seasonal Type Name
		*
		* Type: string
		*
		* Friendly name of the academic time period seasonal type.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		/**
		* Set Academic Time Period Seasonal Type Description
		*
		* Type: string
		*
		* Description of the academic time period seasonal type.
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Academic Time Period Seasonal Type Description
		*
		* Type: string
		*
		* Description of the academic time period seasonal type.
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this academic time period seasonal type became effective. 
		* This is a similar concept to the effective date on enumerated values. When an 
		* expiration date has been specified, this field must be less than or equal to the 
		* expiration date.
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
		* Date and time that this academic time period seasonal type became effective. 
		* This is a similar concept to the effective date on enumerated values. When an 
		* expiration date has been specified, this field must be less than or equal to the 
		* expiration date.
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
		* Date and time that this academic time period seasonal type expires. This is a 
		* similar concept to the expiration date on enumerated values. If specified, this 
		* must be greater than or equal to the effective date. If this field is not 
		* specified, then no expiration date has been currently defined and should 
		* automatically be considered greater than the effective date.
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
		* Date and time that this academic time period seasonal type expires. This is a 
		* similar concept to the expiration date on enumerated values. If specified, this 
		* must be greater than or equal to the effective date. If this field is not 
		* specified, then no expiration date has been currently defined and should 
		* automatically be considered greater than the effective date.
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
		* Set Academic Time Period Seasonal Type Identifier
		*
		* Type: atpSeasonalTypeKey
		*
		* Unique identifier for an academic time period seasonal type.
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get Academic Time Period Seasonal Type Identifier
		*
		* Type: atpSeasonalTypeKey
		*
		* Unique identifier for an academic time period seasonal type.
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

