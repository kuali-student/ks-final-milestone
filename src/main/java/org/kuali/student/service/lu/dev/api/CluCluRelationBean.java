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
	
	
	public class CluCluRelationBean
	 implements CluCluRelationInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String cluId;
		
		/**
		* Set CLU Identifier
		*
		* Unique identifier for a Canonical Learning Unit (CLU). This is the "From" or 
		* "Parent" in the relation.
		*/
		@Override
		public void setCluId(String cluId)
		{
			this.cluId = cluId;
		}
		
		/**
		* Get CLU Identifier
		*
		* Unique identifier for a Canonical Learning Unit (CLU). This is the "From" or 
		* "Parent" in the relation.
		*/
		@Override
		public String getCluId()
		{
			return this.cluId;
		}
						
		private String relatedCluId;
		
		/**
		* Set Related CLU Identifier
		*
		* Unique identifier for a Canonical Learning Unit (CLU). This is the "To" or 
		* "Child" of the relation.
		*/
		@Override
		public void setRelatedCluId(String relatedCluId)
		{
			this.relatedCluId = relatedCluId;
		}
		
		/**
		* Get Related CLU Identifier
		*
		* Unique identifier for a Canonical Learning Unit (CLU). This is the "To" or 
		* "Child" of the relation.
		*/
		@Override
		public String getRelatedCluId()
		{
			return this.relatedCluId;
		}
						
		private Boolean isCluRelationRequired;
		
		/**
		* Set Is CLU Relation Required
		*
		* Indicates if the relation is required upon instantiation of a LUI. Default is 
		* "true".
		*/
		@Override
		public void setIsCluRelationRequired(Boolean isCluRelationRequired)
		{
			this.isCluRelationRequired = isCluRelationRequired;
		}
		
		/**
		* Get Is CLU Relation Required
		*
		* Indicates if the relation is required upon instantiation of a LUI. Default is 
		* "true".
		*/
		@Override
		public Boolean isIsCluRelationRequired()
		{
			return this.isCluRelationRequired;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Date and time that this CLU to CLU relationship became effective. This is a 
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
		* Date and time that this CLU to CLU relationship became effective. This is a 
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
		* Date and time that this CLU to CLU relationship expires. This is a similar 
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
		* Date and time that this CLU to CLU relationship expires. This is a similar 
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
		* Set LU to LU Relation Type
		*
		* Unique identifier for the LU to LU relation type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get LU to LU Relation Type
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
		* Set LU to LU Relation State
		*
		* Identifier for the current status of a CLU to CLU relationship. The values for 
		* this field are constrained to those in the luLuRelationState enumeration. A 
		* separate setup operation does not exist for retrieval of the meta data around 
		* this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get LU to LU Relation State
		*
		* Identifier for the current status of a CLU to CLU relationship. The values for 
		* this field are constrained to those in the luLuRelationState enumeration. A 
		* separate setup operation does not exist for retrieval of the meta data around 
		* this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set CLU to CLU Relation Identifier
		*
		* Unique identifier for a CLU to CLU relationship. This is optional, due to the 
		* identifier being set at the time of creation. Once the relation has been 
		* created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get CLU to CLU Relation Identifier
		*
		* Unique identifier for a CLU to CLU relationship. This is optional, due to the 
		* identifier being set at the time of creation. Once the relation has been 
		* created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

