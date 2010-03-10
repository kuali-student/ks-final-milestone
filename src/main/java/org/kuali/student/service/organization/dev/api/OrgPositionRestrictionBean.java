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
	import java.util.Map;
	
	
	public class OrgPositionRestrictionBean
	 implements OrgPositionRestrictionInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String orgId;
		
		/**
		* Set Organization Identifier
		*
		* Type: orgId
		*
		* Organization the restriction applies to.
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
		* Organization the restriction applies to.
		*/
		@Override
		public String getOrgId()
		{
			return this.orgId;
		}
						
		private String orgPersonRelationTypeKey;
		
		/**
		* Set Organization to Person Relationship Type Identifier
		*
		* Type: orgPersonRelationTypeKey
		*
		* Organization to person relationship type the restriction applies to.
		*/
		@Override
		public void setOrgPersonRelationTypeKey(String orgPersonRelationTypeKey)
		{
			this.orgPersonRelationTypeKey = orgPersonRelationTypeKey;
		}
		
		/**
		* Get Organization to Person Relationship Type Identifier
		*
		* Type: orgPersonRelationTypeKey
		*
		* Organization to person relationship type the restriction applies to.
		*/
		@Override
		public String getOrgPersonRelationTypeKey()
		{
			return this.orgPersonRelationTypeKey;
		}
						
		private RichTextInfo desc;
		
		/**
		* Set Organization Position Restriction Description
		*
		* Type: richTextInfo
		*
		* Description of the restrictions and use of the relationship type within this 
		* particular organization. This should primarily focus on deviations from the 
		* standard use of the relationship type.
		*/
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Organization Position Restriction Description
		*
		* Type: richTextInfo
		*
		* Description of the restrictions and use of the relationship type within this 
		* particular organization. This should primarily focus on deviations from the 
		* standard use of the relationship type.
		*/
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private String title;
		
		/**
		* Set Title
		*
		* Type: string
		*
		* Title of organization person relationships of this type. This allows for 
		* distinction from the name of the relationship type itself, specific for the 
		* given organization.
		*/
		@Override
		public void setTitle(String title)
		{
			this.title = title;
		}
		
		/**
		* Get Title
		*
		* Type: string
		*
		* Title of organization person relationships of this type. This allows for 
		* distinction from the name of the relationship type itself, specific for the 
		* given organization.
		*/
		@Override
		public String getTitle()
		{
			return this.title;
		}
						
		private TimeAmountInfo stdDuration;
		
		/**
		* Set Standard Duration
		*
		* Type: timeAmountInfo
		*
		* Describes the standard duration of relationships of this type.
		*/
		@Override
		public void setStdDuration(TimeAmountInfo stdDuration)
		{
			this.stdDuration = stdDuration;
		}
		
		/**
		* Get Standard Duration
		*
		* Type: timeAmountInfo
		*
		* Describes the standard duration of relationships of this type.
		*/
		@Override
		public TimeAmountInfo getStdDuration()
		{
			return this.stdDuration;
		}
						
		private Integer minNumRelations;
		
		/**
		* Set Minimum Number of Relationships
		*
		* Type: integer
		*
		* Acts as a lower bound on the number of relationships of this type expected for 
		* the organization. If specified, this should be less than or equal to the value 
		* of maxNumRelations
		*/
		@Override
		public void setMinNumRelations(Integer minNumRelations)
		{
			this.minNumRelations = minNumRelations;
		}
		
		/**
		* Get Minimum Number of Relationships
		*
		* Type: integer
		*
		* Acts as a lower bound on the number of relationships of this type expected for 
		* the organization. If specified, this should be less than or equal to the value 
		* of maxNumRelations
		*/
		@Override
		public Integer getMinNumRelations()
		{
			return this.minNumRelations;
		}
						
		private String maxNumRelations;
		
		/**
		* Set Maximum Number of Relationships
		*
		* Type: string
		*
		* Acts as an upper bound on the number of relationships of this type expected for 
		* the organization. The values of this field are restricted to integer values and 
		* the string "unbounded". If specified, this should be greater than or equal to 
		* the value of minNumRelations, with the value "unbounded" being automatically 
		* assumed to be greater.
		*/
		@Override
		public void setMaxNumRelations(String maxNumRelations)
		{
			this.maxNumRelations = maxNumRelations;
		}
		
		/**
		* Get Maximum Number of Relationships
		*
		* Type: string
		*
		* Acts as an upper bound on the number of relationships of this type expected for 
		* the organization. The values of this field are restricted to integer values and 
		* the string "unbounded". If specified, this should be greater than or equal to 
		* the value of minNumRelations, with the value "unbounded" being automatically 
		* assumed to be greater.
		*/
		@Override
		public String getMaxNumRelations()
		{
			return this.maxNumRelations;
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
						
		private String id;
		
		/**
		* Set Organization Position Restriction Identifier
		*
		* Type: string
		*
		* Unique identifier for the organization position restriction record. This is set 
		* by the service to be able to determine changes and alterations to the structure 
		* as well as provides a handle for searches. Once set by the service, this should 
		* be seen as read-only and immutable. This structure is not retrievable by this 
		* identifier to limit the number of active organization position restriction 
		* records visible through the service. It is strongly recommended that this 
		* identifier not be referenced by outside consumers.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Organization Position Restriction Identifier
		*
		* Type: string
		*
		* Unique identifier for the organization position restriction record. This is set 
		* by the service to be able to determine changes and alterations to the structure 
		* as well as provides a handle for searches. Once set by the service, this should 
		* be seen as read-only and immutable. This structure is not retrievable by this 
		* identifier to limit the number of active organization position restriction 
		* records visible through the service. It is strongly recommended that this 
		* identifier not be referenced by outside consumers.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

