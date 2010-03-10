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
	import java.util.List;
	import java.util.Map;
	
	
	public class OrgBean
	 implements OrgInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String longName;
		
		/**
		* Set Organization Long Name
		*
		* Type: string
		*
		* Full name of the organization.
		*/
		@Override
		public void setLongName(String longName)
		{
			this.longName = longName;
		}
		
		/**
		* Get Organization Long Name
		*
		* Type: string
		*
		* Full name of the organization.
		*/
		@Override
		public String getLongName()
		{
			return this.longName;
		}
						
		private String shortName;
		
		/**
		* Set Organization Short Name
		*
		* Type: string
		*
		* Shortened format or abbreviation of the organization's name.
		*/
		@Override
		public void setShortName(String shortName)
		{
			this.shortName = shortName;
		}
		
		/**
		* Get Organization Short Name
		*
		* Type: string
		*
		* Shortened format or abbreviation of the organization's name.
		*/
		@Override
		public String getShortName()
		{
			return this.shortName;
		}
						
		private String sortName;
		
		/**
		* Set Organization Sort Name
		*
		* Type: string
		*
		* The name used for sorting, for cases when alphabetical sorting by shortName or 
		* longName is undesirable.
		*/
		@Override
		public void setSortName(String sortName)
		{
			this.sortName = sortName;
		}
		
		/**
		* Get Organization Sort Name
		*
		* Type: string
		*
		* The name used for sorting, for cases when alphabetical sorting by shortName or 
		* longName is undesirable.
		*/
		@Override
		public String getSortName()
		{
			return this.sortName;
		}
						
		private RichTextInfo shortDesc;
		
		/**
		* Set Organization Short Description
		*
		* Type: richTextInfo
		*
		* A brief description of the organization.
		*/
		@Override
		public void setShortDesc(RichTextInfo shortDesc)
		{
			this.shortDesc = shortDesc;
		}
		
		/**
		* Get Organization Short Description
		*
		* Type: richTextInfo
		*
		* A brief description of the organization.
		*/
		@Override
		public RichTextInfo getShortDesc()
		{
			return this.shortDesc;
		}
						
		private RichTextInfo longDesc;
		
		/**
		* Set Organization Long Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the organization.
		*/
		@Override
		public void setLongDesc(RichTextInfo longDesc)
		{
			this.longDesc = longDesc;
		}
		
		/**
		* Get Organization Long Description
		*
		* Type: richTextInfo
		*
		* Narrative description of the organization.
		*/
		@Override
		public RichTextInfo getLongDesc()
		{
			return this.longDesc;
		}
						
		private Date effectiveDate;
		
		/**
		* Set Effective Date
		*
		* Type: dateTime
		*
		* Date and time that this organization became effective. This is a similar concept 
		* to the effective date on enumerated values. When an expiration date has been 
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
		* Date and time that this organization became effective. This is a similar concept 
		* to the effective date on enumerated values. When an expiration date has been 
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
		* Date and time that this organization expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
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
		* Date and time that this organization expires. This is a similar concept to the 
		* expiration date on enumerated values. If specified, this must be greater than or 
		* equal to the effective date. If this field is not specified, then no expiration 
		* date has been currently defined and should automatically be considered greater 
		* than the effective date.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private List<OrgCodeInfo> orgCodes;
		
		/**
		* Set Org Code Info
		*
		* Type: orgCodeInfoList
		*
		* These are structures so that many different types of codes can be associated 
		* with the org. This allows them to be put into categories.
		*/
		@Override
		public void setOrgCodes(List<OrgCodeInfo> orgCodes)
		{
			this.orgCodes = orgCodes;
		}
		
		/**
		* Get Org Code Info
		*
		* Type: orgCodeInfoList
		*
		* These are structures so that many different types of codes can be associated 
		* with the org. This allows them to be put into categories.
		*/
		@Override
		public List<OrgCodeInfo> getOrgCodes()
		{
			return this.orgCodes;
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
						
		private String type;
		
		/**
		* Set Organization Type
		*
		* Type: orgTypeKey
		*
		* Unique identifier for an organization type.
		*/
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		/**
		* Get Organization Type
		*
		* Type: orgTypeKey
		*
		* Unique identifier for an organization type.
		*/
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		/**
		* Set Organization State
		*
		* Type: string
		*
		* The current status of the organization. The values for this field are 
		* constrained to those in the orgState enumeration. A separate setup operation 
		* does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		/**
		* Get Organization State
		*
		* Type: string
		*
		* The current status of the organization. The values for this field are 
		* constrained to those in the orgState enumeration. A separate setup operation 
		* does not exist for retrieval of the meta data around this value.
		*/
		@Override
		public String getState()
		{
			return this.state;
		}
						
		private String id;
		
		/**
		* Set Organization Id
		*
		* Type: orgId
		*
		* This is optional, due to the identifier being set at the time of creation. Once 
		* the organization has been created, this should be seen as required.
		*/
		@Override
		public void setId(String id)
		{
			this.id = id;
		}
		
		/**
		* Get Organization Id
		*
		* Type: orgId
		*
		* This is optional, due to the identifier being set at the time of creation. Once 
		* the organization has been created, this should be seen as required.
		*/
		@Override
		public String getId()
		{
			return this.id;
		}
						
	}

