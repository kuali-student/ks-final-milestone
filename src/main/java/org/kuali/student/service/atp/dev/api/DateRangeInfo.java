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


public interface DateRangeInfo
{
	
	/**
	* Set Date Range Name
	*
	* Type: string
	*
	* Friendly name of the date range.
	*/
	public void setName(String name);
	
	/**
	* Get Date Range Name
	*
	* Type: string
	*
	* Friendly name of the date range.
	*/
	public String getName();
	
	
	
	/**
	* Set Date Range Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the date range.
	*/
	public void setDesc(RichTextInfo desc);
	
	/**
	* Get Date Range Description
	*
	* Type: richTextInfo
	*
	* Narrative description of the date range.
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
	* Set Range Start Date
	*
	* Type: dateTime
	*
	* Start date and time for the date range. This must be less than or equal to the 
	* end date of this range.
	*/
	public void setStartDate(Date startDate);
	
	/**
	* Get Range Start Date
	*
	* Type: dateTime
	*
	* Start date and time for the date range. This must be less than or equal to the 
	* end date of this range.
	*/
	public Date getStartDate();
	
	
	
	/**
	* Set Range End Date
	*
	* Type: dateTime
	*
	* End date and time for the date range. This must be greater than or equal to the 
	* start date of this range.
	*/
	public void setEndDate(Date endDate);
	
	/**
	* Get Range End Date
	*
	* Type: dateTime
	*
	* End date and time for the date range. This must be greater than or equal to the 
	* start date of this range.
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
	* Set Date Range Type
	*
	* Type: dateRangeTypeKey
	*
	* Unique identifier for a date range type.
	*/
	public void setType(String type);
	
	/**
	* Get Date Range Type
	*
	* Type: dateRangeTypeKey
	*
	* Unique identifier for a date range type.
	*/
	public String getType();
	
	
	
	/**
	* Set Date Range State
	*
	* Type: string
	*
	* The current status of the date range. The values for this field are constrained 
	* to those in the dateRangeState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public void setState(String state);
	
	/**
	* Get Date Range State
	*
	* Type: string
	*
	* The current status of the date range. The values for this field are constrained 
	* to those in the dateRangeState enumeration. A separate setup operation does not 
	* exist for retrieval of the meta data around this value.
	*/
	public String getState();
	
	
	
	/**
	* Set Date Range Key
	*
	* Type: dateRangeKey
	*
	* Unique identifier for a date range.
	*/
	public void setKey(String key);
	
	/**
	* Get Date Range Key
	*
	* Type: dateRangeKey
	*
	* Unique identifier for a date range.
	*/
	public String getKey();
	
	
	
}

