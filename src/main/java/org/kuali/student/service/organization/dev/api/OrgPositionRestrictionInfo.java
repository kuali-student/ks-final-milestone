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


import java.util.Map;


public interface OrgPositionRestrictionInfo
{
	
	/**
	* Set Organization Identifier
	*
	* Type: orgId
	*
	* Organization the restriction applies to.
	*/
	public void setOrgId(String orgId);
	
	/**
	* Get Organization Identifier
	*
	* Type: orgId
	*
	* Organization the restriction applies to.
	*/
	public String getOrgId();
	
	
	
	/**
	* Set Organization to Person Relationship Type Identifier
	*
	* Type: orgPersonRelationTypeKey
	*
	* Organization to person relationship type the restriction applies to.
	*/
	public void setOrgPersonRelationTypeKey(String orgPersonRelationTypeKey);
	
	/**
	* Get Organization to Person Relationship Type Identifier
	*
	* Type: orgPersonRelationTypeKey
	*
	* Organization to person relationship type the restriction applies to.
	*/
	public String getOrgPersonRelationTypeKey();
	
	
	
	/**
	* Set Organization Position Restriction Description
	*
	* Type: richTextInfo
	*
	* Description of the restrictions and use of the relationship type within this 
	* particular organization. This should primarily focus on deviations from the 
	* standard use of the relationship type.
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Organization Position Restriction Description
	*
	* Type: richTextInfo
	*
	* Description of the restrictions and use of the relationship type within this 
	* particular organization. This should primarily focus on deviations from the 
	* standard use of the relationship type.
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set Title
	*
	* Type: string
	*
	* Title of organization person relationships of this type. This allows for 
	* distinction from the name of the relationship type itself, specific for the 
	* given organization.
	*/
	public void setTitle(String title);
	
	/**
	* Get Title
	*
	* Type: string
	*
	* Title of organization person relationships of this type. This allows for 
	* distinction from the name of the relationship type itself, specific for the 
	* given organization.
	*/
	public String getTitle();
	
	
	
	/**
	* Set Standard Duration
	*
	* Type: timeAmountInfo
	*
	* Describes the standard duration of relationships of this type.
	*/
	public void setStdDuration(TimeAmountInfo stdDuration);
	
	/**
	* Get Standard Duration
	*
	* Type: timeAmountInfo
	*
	* Describes the standard duration of relationships of this type.
	*/
	public TimeAmountInfo getStdDuration();
	
	
	
	/**
	* Set Minimum Number of Relationships
	*
	* Type: integer
	*
	* Acts as a lower bound on the number of relationships of this type expected for 
	* the organization. If specified, this should be less than or equal to the value 
	* of maxNumRelations
	*/
	public void setMinNumRelations(Integer minNumRelations);
	
	/**
	* Get Minimum Number of Relationships
	*
	* Type: integer
	*
	* Acts as a lower bound on the number of relationships of this type expected for 
	* the organization. If specified, this should be less than or equal to the value 
	* of maxNumRelations
	*/
	public Integer getMinNumRelations();
	
	
	
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
	public void setMaxNumRelations(String maxNumRelations);
	
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
	public String getMaxNumRelations();
	
	
	
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
	public void setId(String id);
	
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
	public String getId();
	
	
	
}

