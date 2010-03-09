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
package org.kuali.student.service.dictionary.dev.api;



public interface FieldSelectorInfo
{
	
	/**
	* Set Field Descriptor
	*
	* Type: fieldDescriptorInfo
	*
	* Describes a "field" or simple type within a larger object.
	*/
	public void setFieldDescriptor(FieldDescriptorInfo fieldDescriptor);
	
	/**
	* Get Field Descriptor
	*
	* Type: fieldDescriptorInfo
	*
	* Describes a "field" or simple type within a larger object.
	*/
	public FieldDescriptorInfo getFieldDescriptor();
	
	
	
	/**
	* Set Selector Indicator
	*
	* Type: boolean
	*
	* Indicates if the field is a selector field, which indicates that changes to this 
	* field may alter the structure of the object type. This may provide insight as to 
	* frequency of calls to a validate operation, as the constraints will not be 
	* externalized.
	*/
	public void setIsSelector(Boolean isSelector);
	
	/**
	* Get Selector Indicator
	*
	* Type: boolean
	*
	* Indicates if the field is a selector field, which indicates that changes to this 
	* field may alter the structure of the object type. This may provide insight as to 
	* frequency of calls to a validate operation, as the constraints will not be 
	* externalized.
	*/
	public Boolean isIsSelector();
	
	
	
	/**
	* Set Dynamic Indicator
	*
	* Type: boolean
	*
	* Indicates if the field is a dynamic attribute.
	*/
	public void setIsDynamic(Boolean isDynamic);
	
	/**
	* Get Dynamic Indicator
	*
	* Type: boolean
	*
	* Indicates if the field is a dynamic attribute.
	*/
	public Boolean isIsDynamic();
	
	
	
	/**
	* Set Field Identifier
	*
	* Type: string
	*
	* Identifies the field.
	*/
	public void setKey(String key);
	
	/**
	* Get Field Identifier
	*
	* Type: string
	*
	* Identifies the field.
	*/
	public String getKey();
	
	
	
}

