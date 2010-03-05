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



public interface FieldInfo
{
	
	/**
	* Set Field Value
	*
	* Type: string
	*
	* The value for this field.
	*/
	public void setValue(String value);
	
	/**
	* Get Field Value
	*
	* Type: string
	*
	* The value for this field.
	*/
	public String getValue();
	
	
	
	/**
	* Set Field Identifier
	*
	* Type: string
	*
	* The identifier for this field.
	*/
	public void setKey(String key);
	
	/**
	* Get Field Identifier
	*
	* Type: string
	*
	* The identifier for this field.
	*/
	public String getKey();
	
	
	
}

