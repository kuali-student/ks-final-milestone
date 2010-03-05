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


public interface MilestoneInfo
{
	
	/**
	* Set Milestone Name
	*
	* Type: string
	*
	* Name of the milestone.
	*/
	public void setName(String name);
	
	/**
	* Get Milestone Name
	*
	* Type: string
	*
	* Name of the milestone.
	*/
	public String getName();
	
	
	
	/**
	* Set Milestone Description
	*
	* Type: richTextInfo
	*
	* Description of the milestone.
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Milestone Description
	*
	* Type: richTextInfo
	*
	* Description of the milestone.
	*/
	public RichTextInfo getDesc();
	
	
	
	/**
	* Set Academic Time Period Identifier
	*
	* Type: atpKey
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public void setAtpKey(String atpKey);
	
	/**
	* Get Academic Time Period Identifier
	*
	* Type: atpKey
	*
	* Unique identifier for an Academic Time Period (ATP).
	*/
	public String getAtpKey();
	
	
	
	/**
	* Set Milestone Date
	*
	* Type: dateTime
	*
	* Date and time of the milestone.
	*/
	public void setMilestoneDate(Date milestoneDate);
	
	/**
	* Get Milestone Date
	*
	* Type: dateTime
	*
	* Date and time of the milestone.
	*/
	public Date getMilestoneDate();
	
	
	
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
	* Set Milestone Type
	*
	* Type: milestoneTypeKey
	*
	* Unique identifier for a milestone type.
	*/
	public void setType(String type);
	
	/**
	* Get Milestone Type
	*
	* Type: milestoneTypeKey
	*
	* Unique identifier for a milestone type.
	*/
	public String getType();
	
	
	
	/**
	* Set Milestone State
	*
	* Type: string
	*
	* The current status of the milestone. The values for this field are constrained 
	* to those in the milestoneState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get Milestone State
	*
	* Type: string
	*
	* The current status of the milestone. The values for this field are constrained 
	* to those in the milestoneState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set Milestone Key
	*
	* Type: milestoneKey
	*
	* Unique identifier for a milestone.
	*/
	public void setKey(String key);
	
	/**
	* Get Milestone Key
	*
	* Type: milestoneKey
	*
	* Unique identifier for a milestone.
	*/
	public String getKey();
	
	
	
}

