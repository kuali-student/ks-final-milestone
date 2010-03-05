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


public interface LuiInfo
{
	
	/**
	* Set LUI Code
	*
	* Type: string
	*
	* Code identifier/name for the LUI. This is typically used in human readable form 
	* (e.g. ENGL 100 section 123).
	*/
	public void setLuiCode(String luiCode);
	
	/**
	* Get LUI Code
	*
	* Type: string
	*
	* Code identifier/name for the LUI. This is typically used in human readable form 
	* (e.g. ENGL 100 section 123).
	*/
	public String getLuiCode();
	
	
	
	/**
	* Set CLU Identifier
	*
	* Type: cluId
	*
	* Unique identifier for a Canonical Learning Unit (CLU).
	*/
	public void setCluId(String cluId);
	
	/**
	* Get CLU Identifier
	*
	* Type: cluId
	*
	* Unique identifier for a Canonical Learning Unit (CLU).
	*/
	public String getCluId();
	
	
	
	/**
	* Set ATP Identifier
	*
	* Type: atpKey
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public void setAtpKey(String atpKey);
	
	/**
	* Get ATP Identifier
	*
	* Type: atpKey
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public String getAtpKey();
	
	
	
	/**
	* Set Maximum Number of Seats
	*
	* Type: integer
	*
	* Maximum number of "seats" that the LUI will hold for registration.
	*/
	public void setMaxSeats(Integer maxSeats);
	
	/**
	* Get Maximum Number of Seats
	*
	* Type: integer
	*
	* Maximum number of "seats" that the LUI will hold for registration.
	*/
	public Integer getMaxSeats();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this LUI became effective. This is a similar concept to the 
	* effective date on enumerated values. When an expiration date has been specified, 
	* this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this LUI became effective. This is a similar concept to the 
	* effective date on enumerated values. When an expiration date has been specified, 
	* this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this LUI expires. This is a similar concept to the expiration 
	* date on enumerated values. If specified, this should be greater than or equal to 
	* the effective date. If this field is not specified, then no expiration date has 
	* been currently defined and should automatically be considered greater than the 
	* effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this LUI expires. This is a similar concept to the expiration 
	* date on enumerated values. If specified, this should be greater than or equal to 
	* the effective date. If this field is not specified, then no expiration date has 
	* been currently defined and should automatically be considered greater than the 
	* effective date.
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
	* Set CLU State Identifier
	*
	* Type: string
	*
	* The current status of the LUI. The values for this field are constrained to 
	* those in the luState enumeration. A separate setup operation does not exist for 
	* retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get CLU State Identifier
	*
	* Type: string
	*
	* The current status of the LUI. The values for this field are constrained to 
	* those in the luState enumeration. A separate setup operation does not exist for 
	* retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set LUI Identifier
	*
	* Type: luiId
	*
	* This is optional, due to the identifier being set at the time of creation. Once 
	* the LUI has been created, this should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get LUI Identifier
	*
	* Type: luiId
	*
	* This is optional, due to the identifier being set at the time of creation. Once 
	* the LUI has been created, this should be seen as required.
	*/
	public String getId();
	
	
	
}

