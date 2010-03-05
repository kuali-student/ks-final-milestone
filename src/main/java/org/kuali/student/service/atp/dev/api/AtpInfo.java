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


public interface AtpInfo
{
	
	/**
	* Set Academic Time Period Name
	*
	* Type: string
	*
	* Friendly name for the academic time period
	*/
	public void setName(String name);
	
	/**
	* Get Academic Time Period Name
	*
	* Type: string
	*
	* Friendly name for the academic time period
	*/
	public String getName();
	
	
	
	/**
	* Set Academic Time Period Description
	*
	* Type: richTextInfo
	*
	* Narrative description of an academic time period
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Academic Time Period Description
	*
	* Type: richTextInfo
	*
	* Narrative description of an academic time period
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set Start Date
	*
	* Type: dateTime
	*
	* Date and time the academic time period became effective. This does not provide a 
	* bound on date ranges or milestones associated with this time period, but instead 
	* indicates the time period proper. This is a similar concept to the effective 
	* date on enumerated values. When an expiration date has been specified, this 
	* field must be less than or equal to the expiration date.
	*/
	public void setStartDate(Date startDate);
	
	/**
	* Get Start Date
	*
	* Type: dateTime
	*
	* Date and time the academic time period became effective. This does not provide a 
	* bound on date ranges or milestones associated with this time period, but instead 
	* indicates the time period proper. This is a similar concept to the effective 
	* date on enumerated values. When an expiration date has been specified, this 
	* field must be less than or equal to the expiration date.
	*/
	public Date getStartDate();
	
	
	
	/**
	* Set End Date
	*
	* Type: dateTime
	*
	* Date and time the academic time period expires. This does not provide a bound on 
	* date ranges or milestones associated with this time period, but instead 
	* indicates the time period proper. If specified, this must be greater than or 
	* equal to the effective date. If this field is not specified, then no expiration 
	* date has been currently defined and should automatically be considered greater 
	* than the effective date.
	*/
	public void setEndDate(Date endDate);
	
	/**
	* Get End Date
	*
	* Type: dateTime
	*
	* Date and time the academic time period expires. This does not provide a bound on 
	* date ranges or milestones associated with this time period, but instead 
	* indicates the time period proper. If specified, this must be greater than or 
	* equal to the effective date. If this field is not specified, then no expiration 
	* date has been currently defined and should automatically be considered greater 
	* than the effective date.
	*/
	public Date getEndDate();
	
	
	
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
	* Set Academic Time Period Type
	*
	* Type: atpTypeKey
	*
	* Unique identifier for an academic time period type.
	*/
	public void setType(String type);
	
	/**
	* Get Academic Time Period Type
	*
	* Type: atpTypeKey
	*
	* Unique identifier for an academic time period type.
	*/
	public String getType();
	
	
	
	/**
	* Set Academic Time Period State
	*
	* Type: string
	*
	* The current status of the academic time period. The values for this field are 
	* constrained to those in the atpState enumeration. A separate setup operation 
	* does not exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get Academic Time Period State
	*
	* Type: string
	*
	* The current status of the academic time period. The values for this field are 
	* constrained to those in the atpState enumeration. A separate setup operation 
	* does not exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set Academic Time Period Identifier
	*
	* Type: atpKey
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public void setKey(String key);
	
	/**
	* Get Academic Time Period Identifier
	*
	* Type: atpKey
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public String getKey();
	
	
	
}

