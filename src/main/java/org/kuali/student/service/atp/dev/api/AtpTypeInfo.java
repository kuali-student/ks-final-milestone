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
package org.kuali.student.service.atp.dev.api;


import java.util.Date;
import java.util.Map;


public interface AtpTypeInfo
{
	
	/**
	* Set Academic Time Period Type Name
	*
	* Type: string
	*
	* Friendly name of the academic time period type.
	*/
	public void setName(String name);
	
	/**
	* Get Academic Time Period Type Name
	*
	* Type: string
	*
	* Friendly name of the academic time period type.
	*/
	public String getName();
	
	
	
	/**
	* Set Academic Time Period Type Description
	*
	* Type: string
	*
	* Narrative description of an academic time period type.
	*/
	public void setDesc(String desc);
	
	/**
	* Get Academic Time Period Type Description
	*
	* Type: string
	*
	* Narrative description of an academic time period type.
	*/
	public String getDesc();
	
	
	
	/**
	* Set Academic Time Period Duration Type
	*
	* Type: atpDurationTypeKey
	*
	* Unique identifier for an academic time period duration type.
	*/
	public void setDurationType(String durationType);
	
	/**
	* Get Academic Time Period Duration Type
	*
	* Type: atpDurationTypeKey
	*
	* Unique identifier for an academic time period duration type.
	*/
	public String getDurationType();
	
	
	
	/**
	* Set Academic Time Period Seasonal Type
	*
	* Type: atpSeasonalTypeKey
	*
	* Unique identifier for an academic time period seasonal type.
	*/
	public void setSeasonalType(String seasonalType);
	
	/**
	* Get Academic Time Period Seasonal Type
	*
	* Type: atpSeasonalTypeKey
	*
	* Unique identifier for an academic time period seasonal type.
	*/
	public String getSeasonalType();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this academic time period type became effective. This is a 
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
	* Date and time that this academic time period type became effective. This is a 
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
	* Date and time that this academic time period type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this must be 
	* greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this academic time period type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this must be 
	* greater than or equal to the effective date. If this field is not specified, 
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
	* Set Academic Time Period Type
	*
	* Type: atpTypeKey
	*
	* Unique identifier for an academic time period type.
	*/
	public void setKey(String key);
	
	/**
	* Get Academic Time Period Type
	*
	* Type: atpTypeKey
	*
	* Unique identifier for an academic time period type.
	*/
	public String getKey();
	
	
	
}

