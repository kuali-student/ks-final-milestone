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
import java.util.List;


public interface CluResultInfo
{
	
	/**
	* Set CLU Result Description
	*
	* Narrative description of the CLU result.
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get CLU Result Description
	*
	* Narrative description of the CLU result.
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set CLU Identifier
	*
	* The cluId to which the CLU Result is linked.Unique identifier for a Canonical 
	* Learning Unit (CLU).
	*/
	public void setCluId(String cluId);
	
	/**
	* Get CLU Identifier
	*
	* The cluId to which the CLU Result is linked.Unique identifier for a Canonical 
	* Learning Unit (CLU).
	*/
	public String getCluId();
	
	
	
	/**
	* Set Result Options
	*
	* List of learning result option information.
	*/
	public void setResultOptions(List<ResultOptionInfo> resultOptions);
	
	/**
	* Get Result Options
	*
	* List of learning result option information.
	*/
	public List<ResultOptionInfo> getResultOptions();
	
	
	
	/**
	* Set Effective Date
	*
	* Date and time that this CLU result became effective. This is a similar concept 
	* to the effective date on enumerated values. When an expiration date has been 
	* specified, this field must be less than or equal to the expiration date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Date and time that this CLU result became effective. This is a similar concept 
	* to the effective date on enumerated values. When an expiration date has been 
	* specified, this field must be less than or equal to the expiration date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Date and time that this CLU result expires. This is a similar concept to the 
	* expiration date on enumerated values. If specified, this must be greater than or 
	* equal to the effective date. If this field is not specified, then no expiration 
	* date has been currently defined and should automatically be considered greater 
	* than the effective date.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Date and time that this CLU result expires. This is a similar concept to the 
	* expiration date on enumerated values. If specified, this must be greater than or 
	* equal to the effective date. If this field is not specified, then no expiration 
	* date has been currently defined and should automatically be considered greater 
	* than the effective date.
	*/
	public Date getExpirationDate();
	
	
	
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
	* Set Clu Result Type
	*
	* Unique identifier for a clu learning result object type.
	*/
	public void setType(String type);
	
	/**
	* Get Clu Result Type
	*
	* Unique identifier for a clu learning result object type.
	*/
	public String getType();
	
	
	
	/**
	* Set CLU Result State
	*
	* The current status of the CLU Result. The values for this field are constrained 
	* to those in the cluResultState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get CLU Result State
	*
	* The current status of the CLU Result. The values for this field are constrained 
	* to those in the cluResultState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set CLU Result Identifier
	*
	* Unique identifier for a CLU result. This is optional, due to the identifier 
	* being set at the time of creation. Once the result set has been created, this 
	* should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get CLU Result Identifier
	*
	* Unique identifier for a CLU result. This is optional, due to the identifier 
	* being set at the time of creation. Once the result set has been created, this 
	* should be seen as required.
	*/
	public String getId();
	
	
	
}

