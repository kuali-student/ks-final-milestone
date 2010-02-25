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


public interface LuiLuiRelationInfo
{
	
	/**
	* Set LUI Identifier
	*
	* Unique identifier for a Learning Unit Instance (LUI).
	*/
	public void setLuiId(String luiId);
	
	/**
	* Get LUI Identifier
	*
	* Unique identifier for a Learning Unit Instance (LUI).
	*/
	public String getLuiId();
	
	
	
	/**
	* Set Related LUI Identifier
	*
	* Unique identifier for a Learning Unit Instance (LUI).
	*/
	public void setRelatedLuiId(String relatedLuiId);
	
	/**
	* Get Related LUI Identifier
	*
	* Unique identifier for a Learning Unit Instance (LUI).
	*/
	public String getRelatedLuiId();
	
	
	
	/**
	* Set Effective Date
	*
	* Date and time that this LUI to LUI relationship type became effective. This is a 
	* similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Date and time that this LUI to LUI relationship type became effective. This is a 
	* similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Date and time that this LUI to LUI relationship type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this should 
	* be greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Date and time that this LUI to LUI relationship type expires. This is a similar 
	* concept to the expiration date on enumerated values. If specified, this should 
	* be greater than or equal to the effective date. If this field is not specified, 
	* then no expiration date has been currently defined and should automatically be 
	* considered greater than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set Generic/dynamic attributes
	*
	* dynaamic attribiutes
	*/
	public void setAttributes(Map<String,String> attributes);
	
	/**
	* Get Generic/dynamic attributes
	*
	* dynaamic attribiutes
	*/
	public Map<String,String> getAttributes();
	
	
	
	/**
	* Set Create/Update meta info
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public void setMetaInfo(MetaInfo metaInfo);
	
	/**
	* Get Create/Update meta info
	*
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public MetaInfo getMetaInfo();
	
	
	
	/**
	* Set LU to LU Relation Type
	*
	* Unique identifier for the LU to LU relation type.
	*/
	public void setType(String type);
	
	/**
	* Get LU to LU Relation Type
	*
	* Unique identifier for the LU to LU relation type.
	*/
	public String getType();
	
	
	
	/**
	* Set LU to LU Relation State
	*
	* The current status of the LUI to LUI relationship. The values for this field are 
	* constrained to those in the luLuRelationState enumeration. A separate setup 
	* operation does not exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get LU to LU Relation State
	*
	* The current status of the LUI to LUI relationship. The values for this field are 
	* constrained to those in the luLuRelationState enumeration. A separate setup 
	* operation does not exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set LUI to LUI Relation Identifier
	*
	* Unique identifier for a LUI to LUI relation. This is optional, due to the 
	* identifier being set at the time of creation. Once the relation has been 
	* created, this should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get LUI to LUI Relation Identifier
	*
	* Unique identifier for a LUI to LUI relation. This is optional, due to the 
	* identifier being set at the time of creation. Once the relation has been 
	* created, this should be seen as required.
	*/
	public String getId();
	
	
	
}

