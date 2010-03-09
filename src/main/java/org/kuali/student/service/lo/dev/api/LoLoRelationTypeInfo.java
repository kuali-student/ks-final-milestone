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
package org.kuali.student.service.lo.dev.api;


import java.util.Date;
import java.util.Map;


public interface LoLoRelationTypeInfo
{
	
	/**
	* Set LO to LO Relation Type Name
	*
	* Type: string
	*
	* Short name of the LO to LO relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public void setName(String name);
	
	/**
	* Get LO to LO Relation Type Name
	*
	* Type: string
	*
	* Short name of the LO to LO relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public String getName();
	
	
	
	/**
	* Set LO to LO Relation Type Description
	*
	* Type: string
	*
	* Narrative description of the LO to LO relationship type.
	*/
	public void setDesc(String desc);
	
	/**
	* Get LO to LO Relation Type Description
	*
	* Type: string
	*
	* Narrative description of the LO to LO relationship type.
	*/
	public String getDesc();
	
	
	
	/**
	* Set LO to LO Relation Type Reverse Name
	*
	* Type: string
	*
	* Name for the reverse LO to LO relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public void setRevName(String revName);
	
	/**
	* Get LO to LO Relation Type Reverse Name
	*
	* Type: string
	*
	* Name for the reverse LO to LO relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public String getRevName();
	
	
	
	/**
	* Set LO to LO Relation Type Reverse Description
	*
	* Type: string
	*
	* Description of the reverse of the LO to LO relationship type
	*/
	public void setRevDesc(String revDesc);
	
	/**
	* Get LO to LO Relation Type Reverse Description
	*
	* Type: string
	*
	* Description of the reverse of the LO to LO relationship type
	*/
	public String getRevDesc();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this LO to LO relationship type became effective. This is a 
	* similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this LO to LO relationship type became effective. This is a 
	* similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this LO to LO relationship type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this should 
	* be greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this LO to LO relationship type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this should 
	* be greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set Generic/dynamic attributes
	*
	* Type: attributeInfoList
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Generic/dynamic attributes
	*
	* Type: attributeInfoList
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public Map<String,String> getAttributes();
	
	
	
	/**
	* Set LO to LO Relation Type Key
	*
	* Type: loLoRelationId
	*
	* Unique identifier for a LO to LO relationship. This is optional, due to the 
	* identifier being set at the time of creation. Once the relation has been 
	* created, this should be seen as required.
	*/
	public void setKey(String key);
	
	/**
	* Get LO to LO Relation Type Key
	*
	* Type: loLoRelationId
	*
	* Unique identifier for a LO to LO relationship. This is optional, due to the 
	* identifier being set at the time of creation. Once the relation has been 
	* created, this should be seen as required.
	*/
	public String getKey();
	
	
	
}

