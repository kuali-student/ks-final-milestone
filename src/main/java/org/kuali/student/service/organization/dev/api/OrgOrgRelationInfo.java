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
import java.util.Map;


public interface OrgOrgRelationInfo
{
	
	/**
	* Set Org Id
	*
	* Type: orgId
	*
	* Identifies the "parent" organization in the relation. The relationship is from 
	* this organization to the other organization.
	*/
	public void setOrgId(String orgId);
	
	/**
	* Get Org Id
	*
	* Type: orgId
	*
	* Identifies the "parent" organization in the relation. The relationship is from 
	* this organization to the other organization.
	*/
	public String getOrgId();
	
	
	
	/**
	* Set Related Org Id
	*
	* Type: orgId
	*
	* This identifies the "child" organization in the relation. The relationship is 
	* from the other organization to this organization.
	*/
	public void setRelatedOrgId(String relatedOrgId);
	
	/**
	* Get Related Org Id
	*
	* Type: orgId
	*
	* This identifies the "child" organization in the relation. The relationship is 
	* from the other organization to this organization.
	*/
	public String getRelatedOrgId();
	
	
	
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
	public void setEffectiveDate(Date effectiveDate);
	
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
	public Date getEffectiveDate();
	
	
	
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
	public void setExpirationDate(Date expirationDate);
	
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
	public Date getExpirationDate();
	
	
	
	/**
	* Set Dynamic attributes
	*
	* Type: attributeInfoList
	*
	* .
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Dynamic attributes
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
	* Set Org to Org Relation Type
	*
	* Type: orgOrgRelationTypeKey
	*
	* Unique identifier for an organization to organization relationship type.
	*/
	public void setType(String type);
	
	/**
	* Get Org to Org Relation Type
	*
	* Type: orgOrgRelationTypeKey
	*
	* Unique identifier for an organization to organization relationship type.
	*/
	public String getType();
	
	
	
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
	public void setState(String state);
	
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
	public String getState();
	
	
	
	/**
	* Set Org Org Relation Identifier
	*
	* Type: orgOrgRelationId
	*
	* This is optional, due to the identifier being set at the time of creation. Once 
	* the relation has been created, this should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get Org Org Relation Identifier
	*
	* Type: orgOrgRelationId
	*
	* This is optional, due to the identifier being set at the time of creation. Once 
	* the relation has been created, this should be seen as required.
	*/
	public String getId();
	
	
	
}

