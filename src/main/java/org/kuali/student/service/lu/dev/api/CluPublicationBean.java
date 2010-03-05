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
	import java.util.List;
	import java.util.Map;
	
	
	public class CluPublicationBean
	 implements CluPublicationInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String cluId;
		
		/**
		* Set CLU Identifier
		*
		* Type: cluId
		*
		* The identifier for the canonical learning unit which is described by this 
		* publication information.
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
		* The identifier for the canonical learning unit which is described by this 
		* publication information.
		*/
		@Override
		public String getCluId()
		{
			return this.cluId;
		}
						
		private List<FieldInfo> variants;
		
		/**
		* Set Publication Variants
		*
		* Type: fieldInfoList
		*
		* Fields in cluInfo whose values are overridden as part of this publication.
		*/
		@Override
		public void setVariants(List<FieldInfo> variants)
		{
			this.variants = variants;
		}
		
		/**
		* Get Publication Variants
		*
		* Type: fieldInfoList
		*
		* Fields in cluInfo whose values are overridden as part of this publication.
		*/
		@Override
		public List<FieldInfo> getVariants()
		{
			return this.variants;
		}
						
		private String startCycle;
		
		/**
		* Set Publication Start Cycle
		*
		* Type: atpKey
		*
		* The start academic time period for when the CLU should be published in this type 
		* of usage. Should be less than or equal to endCycle.
		*/
		@Override
		public void setStartCycle(String startCycle)
		{
			this.startCycle = startCycle;
		}
		
		/**
		* Get Publication Start Cycle
		*
		* Type: atpKey
		*
		* The start academic time period for when the CLU should be published in this type 
		* of usage. Should be less than or equal to endCycle.
		*/
		@Override
		public String getStartCycle()
		{
			return this.startCycle;
		}
						
		private String endCycle;
		
		/**
		* Set Publication End Cycle
		*
		* Type: atpKey
		*
		* The end academic time period for when the CLU should be published in this type 
		* of usage. If specified, should be greater than or equal to startCycle.
		*/
		@Override
		public void setEndCycle(String endCycle)
		{
			this.endCycle = endCycle;
		}
		
		/**
		* Get Publication End Cycle
		*
		* Type: atpKey
		*
		* The end academic time period for when the CLU should be published in this type 
		* of usage. If specified, should be greater than or equal to startCycle.
		*/
		@Override
		public String getEndCycle()
		{
			return this.endCycle;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this LU publication type became effective. This is a similar 
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
		* Date and time that this LU publication type became effective. This is a similar 
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
		* Date and time that this LU publication type expires. This is a similar concept 
		* to the expiration date on enumerated values. If specified, this should be 
		* greater than or equal to the effective date. If this field is not specified, 
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
		* Date and time that this LU publication type expires. This is a similar concept 
		* to the expiration date on enumerated values. If specified, this should be 
		* greater than or equal to the effective date. If this field is not specified, 
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
		* Set Publication Type
		*
		* Type: luPublicationTypeKey
		*
		* Type of publication for which this information should be used.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Publication Type
		*
		* Type: luPublicationTypeKey
		*
		* Type of publication for which this information should be used.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Publication State
		*
		* Type: string
		*
		* Current state of the information for this publication type. This value should be 
		* constrained to those within the cluPublishingState enumeration. In general, an 
		* "active" record for a type indicates that the clu should be published within 
		* that media, though that may be further constrained by the cycle information 
		* included.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Publication State
		*
		* Type: string
		*
		* Current state of the information for this publication type. This value should be 
		* constrained to those within the cluPublishingState enumeration. In general, an 
		* "active" record for a type indicates that the clu should be published within 
		* that media, though that may be further constrained by the cycle information 
		* included.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set Publishing Information Identifier
		*
		* Type: cluPublicationId
		*
		* Identifier for the publishing information. This is set by the service to be able 
		* to determine changes and alterations to the structure as well as provides a 
		* handle for searches. This structure is not currently accessible through unique 
		* operations, and it is strongly recommended that no external references to this 
		* particular identifier be maintained.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Publishing Information Identifier
		*
		* Type: cluPublicationId
		*
		* Identifier for the publishing information. This is set by the service to be able 
		* to determine changes and alterations to the structure as well as provides a 
		* handle for searches. This structure is not currently accessible through unique 
		* operations, and it is strongly recommended that no external references to this 
		* particular identifier be maintained.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

