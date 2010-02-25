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
	package org.kuali.student.service.proposal.dev.api;
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Map;
	
	
	public class ProposalDocRelationBean
	 implements ProposalDocRelationInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String proposalId;
		
		/**
		* Set Proposal Identifier
		*
		* Unique identifier for a Proposal.
		*/
		@Override
		public void setProposalId(String proposalId)
		{
			this.proposalId = proposalId;
		}
		
		/**
		* Get Proposal Identifier
		*
		* Unique identifier for a Proposal.
		*/
		@Override
		public String getProposalId()
		{
			return this.proposalId;
		}
						
		private String documentId;
		
		/**
		* Set Document Identifier
		*
		* Unique identifier for a document.
		*/
		@Override
		public void setDocumentId(String documentId)
		{
			this.documentId = documentId;
		}
		
		/**
		* Get Document Identifier
		*
		* Unique identifier for a document.
		*/
		@Override
		public String getDocumentId()
		{
			return this.documentId;
		}
						
		private String title;
		
		/**
		* Set Document Title
		*
		* The title of the document usage in the context of the Proposal.
		*/
		@Override
		public void setTitle(String title)
		{
			this.title = title;
		}
		
		/**
		* Get Document Title
		*
		* The title of the document usage in the context of the Proposal.
		*/
		@Override
		public String getTitle()
		{
			return this.title;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Document Description
		*
		* The description of the document usage in the context of the Proposal.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Document Description
		*
		* The description of the document usage in the context of the Proposal.
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Date and time that this Proposal Doc Relation became effective. This is a 
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
		* Date and time that this Proposal Doc Relation became effective. This is a 
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
		* Date and time that this Proposal Doc Relation expires. This is a similar concept 
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
		* Date and time that this Proposal Doc Relation expires. This is a similar concept 
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
		* Set Proposal Document Relation Type Key
		*
		* Unique identifier for a proposal document relation type. Describes the type of 
		* usage of the document.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Proposal Document Relation Type Key
		*
		* Unique identifier for a proposal document relation type. Describes the type of 
		* usage of the document.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Proposal Document Relation State Key
		*
		* The current status of the Proposal to document relationship. The values for this 
		* field are constrained to those in the proposalDocRelationState enumeration. A 
		* separate setup operation does not exist for retrieval of the meta data around 
		* this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Proposal Document Relation State Key
		*
		* The current status of the Proposal to document relationship. The values for this 
		* field are constrained to those in the proposalDocRelationState enumeration. A 
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
		* Set Proposal Document Relation Identifier
		*
		* Unique identifier for a proposal to document relation. This is optional, due to 
		* the identifier being set at the time of creation. Once the connection has been 
		* created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Proposal Document Relation Identifier
		*
		* Unique identifier for a proposal to document relation. This is optional, due to 
		* the identifier being set at the time of creation. Once the connection has been 
		* created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

