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
	
	
	public class LuiBean
	 implements LuiInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String luiCode;
		
		/**
		* Set LUI Code
		*
		* Type: string
		*
		* Code identifier/name for the LUI. This is typically used in human readable form 
		* (e.g. ENGL 100 section 123).
		*/
		@Override
		public void setLuiCode(String luiCode)
		{
			this.luiCode = luiCode;
		}
		
		/**
		* Get LUI Code
		*
		* Type: string
		*
		* Code identifier/name for the LUI. This is typically used in human readable form 
		* (e.g. ENGL 100 section 123).
		*/
		@Override
		public String getLuiCode()
		{
			return this.luiCode;
		}
						
		private String cluId;
		
		/**
		* Set CLU Identifier
		*
		* Type: cluId
		*
		* Unique identifier for a Canonical Learning Unit (CLU).
		*/
		@Override
		public void setCluId(String cluId)
		{
			this.cluId = cluId;
		}
		
		/**
		* Get CLU Identifier
		*
		* Type: cluId
		*
		* Unique identifier for a Canonical Learning Unit (CLU).
		*/
		@Override
		public String getCluId()
		{
			return this.cluId;
		}
						
		private String atpKey;
		
		/**
		* Set ATP Identifier
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
		* Get ATP Identifier
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
						
		private Integer maxSeats;
		
		/**
		* Set Maximum Number of Seats
		*
		* Type: integer
		*
		* Maximum number of "seats" that the LUI will hold for registration.
		*/
		@Override
		public void setMaxSeats(Integer maxSeats)
		{
			this.maxSeats = maxSeats;
		}
		
		/**
		* Get Maximum Number of Seats
		*
		* Type: integer
		*
		* Maximum number of "seats" that the LUI will hold for registration.
		*/
		@Override
		public Integer getMaxSeats()
		{
			return this.maxSeats;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this LUI became effective. This is a similar concept to the 
		* effective date on enumerated values. When an expiration date has been specified, 
		* this field must be less than or equal to the expiration date.
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
		* Date and time that this LUI became effective. This is a similar concept to the 
		* effective date on enumerated values. When an expiration date has been specified, 
		* this field must be less than or equal to the expiration date.
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
		* Date and time that this LUI expires. This is a similar concept to the expiration 
		* date on enumerated values. If specified, this should be greater than or equal to 
		* the effective date. If this field is not specified, then no expiration date has 
		* been currently defined and should automatically be considered greater than the 
		* effective date.
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
		* Date and time that this LUI expires. This is a similar concept to the expiration 
		* date on enumerated values. If specified, this should be greater than or equal to 
		* the effective date. If this field is not specified, then no expiration date has 
		* been currently defined and should automatically be considered greater than the 
		* effective date.
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
		* .
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
		* .
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
						
		private String state;
		
		/**
		* Set CLU State Identifier
		*
		* Type: string
		*
		* The current status of the LUI. The values for this field are constrained to 
		* those in the luState enumeration. A separate setup operation does not exist for 
		* retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get CLU State Identifier
		*
		* Type: string
		*
		* The current status of the LUI. The values for this field are constrained to 
		* those in the luState enumeration. A separate setup operation does not exist for 
		* retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set LUI Identifier
		*
		* Type: luiId
		*
		* This is optional, due to the identifier being set at the time of creation. Once 
		* the LUI has been created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get LUI Identifier
		*
		* Type: luiId
		*
		* This is optional, due to the identifier being set at the time of creation. Once 
		* the LUI has been created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

