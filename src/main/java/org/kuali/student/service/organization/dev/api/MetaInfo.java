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
package org.kuali.student.service.organization.dev.api;


import java.util.Date;


public interface MetaInfo
{
	
	/**
	* Set Version Indicator
	*
	* Type: string
	*
	* An indicator of the version of the thing being described with this meta 
	* information. This is set by the service implementation and will be used to 
	* determine conflicts in updates.
	*/
	public void setVersionInd(String versionInd);
	
	/**
	* Get Version Indicator
	*
	* Type: string
	*
	* An indicator of the version of the thing being described with this meta 
	* information. This is set by the service implementation and will be used to 
	* determine conflicts in updates.
	*/
	public String getVersionInd();
	
	
	
	/**
	* Set Date/Time Created
	*
	* Type: dateTime
	*
	* The date and time the thing being described with this meta information was 
	* created
	*/
	public void setCreateTime(Date createTime);
	
	/**
	* Get Date/Time Created
	*
	* Type: dateTime
	*
	* The date and time the thing being described with this meta information was 
	* created
	*/
	public Date getCreateTime();
	
	
	
	/**
	* Set Created By Identifier
	*
	* Type: principalId
	*
	* The principal who created the thing being described with this meta information
	*/
	public void setCreateId(String createId);
	
	/**
	* Get Created By Identifier
	*
	* Type: principalId
	*
	* The principal who created the thing being described with this meta information
	*/
	public String getCreateId();
	
	
	
	/**
	* Set Date/Time Last Updated
	*
	* Type: dateTime
	*
	* The date and time the thing being described with this meta information was last 
	* updated. This would be filled in on initial creation as well.
	*/
	public void setUpdateTime(Date updateTime);
	
	/**
	* Get Date/Time Last Updated
	*
	* Type: dateTime
	*
	* The date and time the thing being described with this meta information was last 
	* updated. This would be filled in on initial creation as well.
	*/
	public Date getUpdateTime();
	
	
	
	/**
	* Set Updated By Identifier
	*
	* Type: principalId
	*
	* The principal who last updated the thing being described with this meta 
	* information. This would be filled in on initial creation as well.
	*/
	public void setUpdateId(String updateId);
	
	/**
	* Get Updated By Identifier
	*
	* Type: principalId
	*
	* The principal who last updated the thing being described with this meta 
	* information. This would be filled in on initial creation as well.
	*/
	public String getUpdateId();
	
	
	
}

