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


import java.util.Date;
import java.util.List;
import java.util.Map;


public interface OrgInfo
{
	
	/**
	* Set Organization Long Name
	*
	* Type: string
	*
	* Full name of the organization.
	*/
	public void setLongName(String longName);
	
	/**
	* Get Organization Long Name
	*
	* Type: string
	*
	* Full name of the organization.
	*/
	public String getLongName();
	
	
	
	/**
	* Set Organization Short Name
	*
	* Type: string
	*
	* Shortened format or abbreviation of the organization's name.
	*/
	public void setShortName(String shortName);
	
	/**
	* Get Organization Short Name
	*
	* Type: string
	*
	* Shortened format or abbreviation of the organization's name.
	*/
	public String getShortName();
	
	
	
	/**
	* Set Organization Sort Name
	*
	* Type: string
	*
	* The name used for sorting, for cases when alphabetical sorting by shortName or 
	* longName is undesirable.
	*/
	public void setSortName(String sortName);
	
	/**
	* Get Organization Sort Name
	*
	* Type: string
	*
	* The name used for sorting, for cases when alphabetical sorting by shortName or 
	* longName is undesirable.
	*/
	public String getSortName();
	
	
	
	/**
	* Set Organization Short Description
	*
	* Type: richTextInfo
	*
	* A brief description of the organization.
	*/
	public void setShortDesc(RichTextInfo shortDesc);
	
	/**
	* Get Organization Short Description
	*
	* Type: richTextInfo
	*
	* A brief description of the organization.
	*/
	public RichTextInfo getShortDesc();
	
	
	
	/**
	* Set Organization Long Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the organization.
	*/
	public void setLongDesc(RichTextInfo longDesc);
	
	/**
	* Get Organization Long Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the organization.
	*/
	public RichTextInfo getLongDesc();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this organization became effective. This is a similar concept 
	* to the effective date on enumerated values. When an expiration date has been 
	* specified, this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this organization became effective. This is a similar concept 
	* to the effective date on enumerated values. When an expiration date has been 
	* specified, this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
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
	public void setExpirationDate(Date expirationDate);
	
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
	public Date getExpirationDate();
	
	
	
	/**
	* Set Org Code Info
	*
	* Type: orgCodeInfoList
	*
	* These are structures so that many different types of codes can be associated 
	* with the org. This allows them to be put into categories.
	*/
	public void setOrgCodes(List<OrgCodeInfo> orgCodes);
	
	/**
	* Get Org Code Info
	*
	* Type: orgCodeInfoList
	*
	* These are structures so that many different types of codes can be associated 
	* with the org. This allows them to be put into categories.
	*/
	public List<OrgCodeInfo> getOrgCodes();
	
	
	
	/**
	* Set Generic/dynamic attributes
	*
	* Type: attributeInfoList
	*
	* .
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Generic/dynamic attributes
	*
	* Type: attributeInfoList
	*
	* .
	*/
	public Map<String,String> getAttributes();
	
	
	
	/**
	* Set Create/Update meta info
	*
	* Type: metaInfo
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public void setMetaInfo(MetaInfo metaInfo);
	
	/**
	* Get Create/Update meta info
	*
	* Type: metaInfo
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public MetaInfo getMetaInfo();
	
	
	
	/**
	* Set Organization Type
	*
	* Type: orgTypeKey
	*
	* Unique identifier for an organization type.
	*/
	public void setType(String type);
	
	/**
	* Get Organization Type
	*
	* Type: orgTypeKey
	*
	* Unique identifier for an organization type.
	*/
	public String getType();
	
	
	
	/**
	* Set Organization State
	*
	* Type: string
	*
	* The current status of the organization. The values for this field are 
	* constrained to those in the orgState enumeration. A separate setup operation 
	* does not exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get Organization State
	*
	* Type: string
	*
	* The current status of the organization. The values for this field are 
	* constrained to those in the orgState enumeration. A separate setup operation 
	* does not exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set Organization Id
	*
	* Type: orgId
	*
	* This is optional, due to the identifier being set at the time of creation. Once 
	* the organization has been created, this should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get Organization Id
	*
	* Type: orgId
	*
	* This is optional, due to the identifier being set at the time of creation. Once 
	* the organization has been created, this should be seen as required.
	*/
	public String getId();
	
	
	
}

