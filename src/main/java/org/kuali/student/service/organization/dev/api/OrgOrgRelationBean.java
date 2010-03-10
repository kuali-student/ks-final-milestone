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
	package org.kuali.student.service.organization.dev.api;
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Map;
	
	
	public class OrgOrgRelationBean
	 implements OrgOrgRelationInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String orgId;
		
		/**
		* Set Org Id
		*
		* Type: orgId
		*
		* Identifies the "parent" organization in the relation. The relationship is from 
		* this organization to the other organization.
		*/
		@Override
		public void setOrgId(String orgId)
		{
			this.orgId = orgId;
		}
		
		/**
		* Get Org Id
		*
		* Type: orgId
		*
		* Identifies the "parent" organization in the relation. The relationship is from 
		* this organization to the other organization.
		*/
		@Override
		public String getOrgId()
		{
			return this.orgId;
		}
						
		private String relatedOrgId;
		
		/**
		* Set Related Org Id
		*
		* Type: orgId
		*
		* This identifies the "child" organization in the relation. The relationship is 
		* from the other organization to this organization.
		*/
		@Override
		public void setRelatedOrgId(String relatedOrgId)
		{
			this.relatedOrgId = relatedOrgId;
		}
		
		/**
		* Get Related Org Id
		*
		* Type: orgId
		*
		* This identifies the "child" organization in the relation. The relationship is 
		* from the other organization to this organization.
		*/
		@Override
		public String getRelatedOrgId()
		{
			return this.relatedOrgId;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this organization to organization relationship became 
		* effective. This is a similar concept to the effective date on enumerated values. 
		* When an expiration date has been specified, this field must be less than or 
		* equal to the expiration date.
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
		* Date and time that this organization to organization relationship became 
		* effective. This is a similar concept to the effective date on enumerated values. 
		* When an expiration date has been specified, this field must be less than or 
		* equal to the expiration date.
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
		* Date and time that this organization to organization relationship expires. This 
		* is a similar concept to the expiration date on enumerated values. If specified, 
		* this must be greater than or equal to the effective date. If this field is not 
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
		* Date and time that this organization to organization relationship expires. This 
		* is a similar concept to the expiration date on enumerated values. If specified, 
		* this must be greater than or equal to the effective date. If this field is not 
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
		* Set Dynamic attributes
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
		* Get Dynamic attributes
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
						
		private String type;
		
		/**
		* Set Org to Org Relation Type
		*
		* Type: orgOrgRelationTypeKey
		*
		* Unique identifier for an organization to organization relationship type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Org to Org Relation Type
		*
		* Type: orgOrgRelationTypeKey
		*
		* Unique identifier for an organization to organization relationship type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Org to Org Relation State
		*
		* Type: string
		*
		* The current status of the organization to organization relationship. The values 
		* for this field are constrained to those in the orgOrgRelationState enumeration. 
		* A separate setup operation does not exist for retrieval of the meta data around 
		* this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Org to Org Relation State
		*
		* Type: string
		*
		* The current status of the organization to organization relationship. The values 
		* for this field are constrained to those in the orgOrgRelationState enumeration. 
		* A separate setup operation does not exist for retrieval of the meta data around 
		* this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set Org Org Relation Identifier
		*
		* Type: orgOrgRelationId
		*
		* This is optional, due to the identifier being set at the time of creation. Once 
		* the relation has been created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Org Org Relation Identifier
		*
		* Type: orgOrgRelationId
		*
		* This is optional, due to the identifier being set at the time of creation. Once 
		* the relation has been created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

