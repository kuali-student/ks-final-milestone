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


import java.util.Map;


public interface CluInstructorInfo
{
	
	/**
	* Set Organization Identifier
	*
	* Type: orgId
	*
	* Unique identifier for an organization. This indicates which organization this 
	* individual is associated with for the purposes of this clu.
	*/
	public void setOrgId(String orgId);
	
	/**
	* Get Organization Identifier
	*
	* Type: orgId
	*
	* Unique identifier for an organization. This indicates which organization this 
	* individual is associated with for the purposes of this clu.
	*/
	public String getOrgId();
	
	
	
	/**
	* Set Person Identifier
	*
	* Type: personId
	*
	* Unique identifier for a person record.
	*/
	public void setPersonId(String personId);
	
	/**
	* Get Person Identifier
	*
	* Type: personId
	*
	* Unique identifier for a person record.
	*/
	public String getPersonId();
	
	
	
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
	
	
	
}

