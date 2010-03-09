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


public interface LoRepositoryInfo
{
	
	/**
	* Set Repository Name
	*
	* Type: string
	*
	* Friendly name of the repository
	*/
	public void setName(String name);
	
	/**
	* Get Repository Name
	*
	* Type: string
	*
	* Friendly name of the repository
	*/
	public String getName();
	
	
	
	/**
	* Set Learning Objective Repository Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the learning objective repository
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Learning Objective Repository Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the learning objective repository
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set Learning Objective Repository Root Node Id
	*
	* Type: loId
	*
	* Identifier for the root node of the learning objective repository
	*/
	public void setRootLoId(String rootLoId);
	
	/**
	* Get Learning Objective Repository Root Node Id
	*
	* Type: loId
	*
	* Identifier for the root node of the learning objective repository
	*/
	public String getRootLoId();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this learning objective repository became effective. This is 
	* a similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: dateTime
	*
	* Date and time that this learning objective repository became effective. This is 
	* a similar concept to the effective date on enumerated values. When an expiration 
	* date has been specified, this field must be less than or equal to the expiration 
	* date.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: dateTime
	*
	* Date and time that this learning objective repository expires. This is a similar 
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
	* Date and time that this learning objective repository expires. This is a similar 
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
	* Set Learning Objective Repository Key
	*
	* Type: loRepositoryKey
	*
	* Unique identifier for a learning objective repository.
	*/
	public void setKey(String key);
	
	/**
	* Get Learning Objective Repository Key
	*
	* Type: loRepositoryKey
	*
	* Unique identifier for a learning objective repository.
	*/
	public String getKey();
	
	
	
}

