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


import java.util.Date;
import java.util.Map;


public interface LuLuRelationTypeInfo
{
	
	/**
	* Set LU to LU Relation Type Name
	*
	* Short name of the LU to LU relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public void setName(String name);
	
	/**
	* Get LU to LU Relation Type Name
	*
	* Short name of the LU to LU relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public String getName();
	
	
	
	/**
	* Set LU to LU Relation Type Description
	*
	* Narrative description of the LU to LU relationship type.
	*/
	public void setDesc(String desc);
	
	/**
	* Get LU to LU Relation Type Description
	*
	* Narrative description of the LU to LU relationship type.
	*/
	public String getDesc();
	
	
	
	/**
	* Set LU to LU Relation Type Reverse Name
	*
	* Name for the reverse LU to LU relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public void setRevName(String revName);
	
	/**
	* Get LU to LU Relation Type Reverse Name
	*
	* Name for the reverse LU to LU relationship type. This is primarily to be used by 
	* developers and may end up translated in the end system.
	*/
	public String getRevName();
	
	
	
	/**
	* Set LU to LU Relation Type Reverse Description
	*
	* Description of the reverse of the LU to LU relationship type
	*/
	public void setRevDesc(String revDesc);
	
	/**
	* Get LU to LU Relation Type Reverse Description
	*
	* Description of the reverse of the LU to LU relationship type
	*/
	public String getRevDesc();
	
	
	
	/**
	* Set Effective Date
	*
	* Date and time that this LU to LU relationship type became effective. This is a 
	* similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Date and time that this LU to LU relationship type became effective. This is a 
	* similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Date and time that this LU to LU relationship type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this should 
	* be greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Date and time that this LU to LU relationship type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this should 
	* be greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set Generic/dynamic attributes
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Generic/dynamic attributes
	*
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public Map<String,String> getAttributes();
	
	
	
	/**
	* Set LU to LU Relation Type Key
	*
	* Unique identifier for the LU to LU relation type.
	*/
	public void setKey(String key);
	
	/**
	* Get LU to LU Relation Type Key
	*
	* Unique identifier for the LU to LU relation type.
	*/
	public String getKey();
	
	
	
}

