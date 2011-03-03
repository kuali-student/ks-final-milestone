	/*
	 * Copyright 2011 The Kuali Foundation
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
	package org.kuali.student.enrollment.lui.infc;
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Map;
	import org.kuali.student.enrollment.common.infc.MetaInfc;
	
	
	public class LuiLuiRelationBean
	 implements LuiLuiRelationInfc	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String luiId;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* Unique identifier for a Learning Unit Instance (LUI).
		*/
		@Override
		public void setLuiId(String luiId)
		{
			this.luiId = luiId;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* Unique identifier for a Learning Unit Instance (LUI).
		*/
		@Override
		public String getLuiId()
		{
			return this.luiId;
		}
						
		private String relatedLuiId;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* Unique identifier for a Learning Unit Instance (LUI).
		*/
		@Override
		public void setRelatedLuiId(String relatedLuiId)
		{
			this.relatedLuiId = relatedLuiId;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* Unique identifier for a Learning Unit Instance (LUI).
		*/
		@Override
		public String getRelatedLuiId()
		{
			return this.relatedLuiId;
		}
						
		private Date effectiveDate;
		
		/**
		* Set ????
		*
		* Type: Date
		*
		* Date and time that this LUI to LUI relationship type 
		* effective. This is a similar concept to the effective date 
		* enumerated values. When an expiration date has been 
		* this field must be less than or equal to the expiration date.
		*/
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		/**
		* Get ????
		*
		* Type: Date
		*
		* Date and time that this LUI to LUI relationship type 
		* effective. This is a similar concept to the effective date 
		* enumerated values. When an expiration date has been 
		* this field must be less than or equal to the expiration date.
		*/
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		/**
		* Set ????
		*
		* Type: Date
		*
		* Date and time that this LUI to LUI relationship 
		* expires. This is a similar concept to the expiration date 
		* enumerated values. If specified, this should be greater than 
		* equal to the effective date. If this field is not 
		* then no expiration date has been currently defined and 
		* automatically be considered greater than the effective date.
		*/
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		/**
		* Get ????
		*
		* Type: Date
		*
		* Date and time that this LUI to LUI relationship 
		* expires. This is a similar concept to the expiration date 
		* enumerated values. If specified, this should be greater than 
		* equal to the effective date. If this field is not 
		* then no expiration date has been currently defined and 
		* automatically be considered greater than the effective date.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private Map<String, String> attributes;
		
		/**
		* Set ????
		*
		* Type: Map<String, String>
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public void setAttributes(Map<String, String> attributes)
		{
			this.attributes = attributes;
		}
		
		/**
		* Get ????
		*
		* Type: Map<String, String>
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public Map<String, String> getAttributes()
		{
			return this.attributes;
		}
						
		private MetaInfc metaInfo;
		
		/**
		* Set ????
		*
		* Type: MetaInfo
		*
		* Create and last update info for the structure. This is 
		* and treated as read only since the data is set by the 
		* of the service during maintenance operations.
		*/
		@Override
		public void setMetaInfo(MetaInfc metaInfo)
		{
			this.metaInfo = metaInfo;
		}
		
		/**
		* Get ????
		*
		* Type: MetaInfo
		*
		* Create and last update info for the structure. This is 
		* and treated as read only since the data is set by the 
		* of the service during maintenance operations.
		*/
		@Override
		public MetaInfc getMetaInfo()
		{
			return this.metaInfo;
		}
						
		private String type;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* Unique identifier for the LU to LU relation type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* Unique identifier for the LU to LU relation type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* The current status of the LUI to LUI relationship. The 
		* for this field are constrained to those in 
		* luLuRelationState enumeration. A separate setup operation 
		* not exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* The current status of the LUI to LUI relationship. The 
		* for this field are constrained to those in 
		* luLuRelationState enumeration. A separate setup operation 
		* not exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* Unique identifier for a LUI to LUI relation. This is 
		* due to the identifier being set at the time of creation. 
		* the relation has been created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* Unique identifier for a LUI to LUI relation. This is 
		* due to the identifier being set at the time of creation. 
		* the relation has been created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

