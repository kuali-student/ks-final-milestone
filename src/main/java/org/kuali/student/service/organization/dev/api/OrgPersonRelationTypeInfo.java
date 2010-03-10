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


public interface OrgPersonRelationTypeInfo
{
	
	/**
	* Set Organization to Person Relation Type Name
	*
	* Type: string
	*
	* Short name for the organization to person relationship type. This is primarily 
	* to be used by developers and may end up translated in the end system.
	*/
	public void setName(String name);
	
	/**
	* Get Organization to Person Relation Type Name
	*
	* Type: string
	*
	* Short name for the organization to person relationship type. This is primarily 
	* to be used by developers and may end up translated in the end system.
	*/
	public String getName();
	
	
	
	/**
	* Set Organization to Person Relation Type Description
	*
	* Type: string
	*
	* Description of the organization to person relationship type.
	*/
	public void setDesc(String desc);
	
	/**
	* Get Organization to Person Relation Type Description
	*
	* Type: string
	*
	* Description of the organization to person relationship type.
	*/
	public String getDesc();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this organization to person relationship type became 
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
	* Date and time that this organization to person relationship type became 
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
	* Date and time that this organization to person relationship type expires. This 
	* is a similar concept to the expiration date on enumerated values. If specified, 
	* this should be greater than or equal to the effective date. If this field is not 
	* specified, then no expiration date has been currently defined and should 
	* automatically be considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this organization to person relationship type expires. This 
	* is a similar concept to the expiration date on enumerated values. If specified, 
	* this should be greater than or equal to the effective date. If this field is not 
	* specified, then no expiration date has been currently defined and should 
	* automatically be considered greater than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
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
	* Set Organization to Person Relation Type Identifier
	*
	* Type: orgPersonRelationTypeKey
	*
	* Unique identifier for an organization to person relationship type.
	*/
	public void setKey(String key);
	
	/**
	* Get Organization to Person Relation Type Identifier
	*
	* Type: orgPersonRelationTypeKey
	*
	* Unique identifier for an organization to person relationship type.
	*/
	public String getKey();
	
	
	
}

