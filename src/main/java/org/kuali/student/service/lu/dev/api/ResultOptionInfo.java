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


public interface ResultOptionInfo
{
	
	/**
	* Set Result Option Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the result option.
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Result Option Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the result option.
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set Result Usage Type
	*
	* Type: resultUsageTypeKey
	*
	* Unique identifier for a result usage type.
	*/
	public void setResultUsageTypeKey(String resultUsageTypeKey);
	
	/**
	* Get Result Usage Type
	*
	* Type: resultUsageTypeKey
	*
	* Unique identifier for a result usage type.
	*/
	public String getResultUsageTypeKey();
	
	
	
	/**
	* Set Result Component Id
	*
	* Type: resultComponentId
	*
	* Unique identifier for a result component.
	*/
	public void setResultComponentId(String resultComponentId);
	
	/**
	* Get Result Component Id
	*
	* Type: resultComponentId
	*
	* Unique identifier for a result component.
	*/
	public String getResultComponentId();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this result option became effective. This is a similar 
	* concept to the effective date on enumerated values. When an expiration date has 
	* been specified, this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this result option became effective. This is a similar 
	* concept to the effective date on enumerated values. When an expiration date has 
	* been specified, this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this result option expires. This is a similar concept to the 
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
	* Date and time that this result option expires. This is a similar concept to the 
	* expiration date on enumerated values. If specified, this must be greater than or 
	* equal to the effective date. If this field is not specified, then no expiration 
	* date has been currently defined and should automatically be considered greater 
	* than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
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
	* Set Result Option State
	*
	* Type: string
	*
	* The current status of the result option. The values for this field are 
	* constrained to those in the resultOptionState enumeration. A separate setup 
	* operation does not exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get Result Option State
	*
	* Type: string
	*
	* The current status of the result option. The values for this field are 
	* constrained to those in the resultOptionState enumeration. A separate setup 
	* operation does not exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set Result Option Identifier
	*
	* Type: resultOptionId
	*
	* Unique identifier for a result option. This is optional, due to the identifier 
	* being set at the time of creation. Once the result option has been created, this 
	* should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get Result Option Identifier
	*
	* Type: resultOptionId
	*
	* Unique identifier for a result option. This is optional, due to the identifier 
	* being set at the time of creation. Once the result option has been created, this 
	* should be seen as required.
	*/
	public String getId();
	
	
	
}

