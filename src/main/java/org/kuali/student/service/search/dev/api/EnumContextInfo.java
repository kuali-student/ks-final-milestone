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
package org.kuali.student.service.search.dev.api;



public interface EnumContextInfo
{
	
	/**
	* Set Enumeration Context Value Descriptor
	*
	* Type: fieldDescriptorInfo
	*
	* Describes the value that can be supplied for a given context, including field 
	* lengths, allowed characters, enumerations, etc. (Note: added "Info" sufiix to 
	* type
	*/
	public void setContextValueDescriptor(FieldDescriptorInfo contextValueDescriptor);
	
	/**
	* Get Enumeration Context Value Descriptor
	*
	* Type: fieldDescriptorInfo
	*
	* Describes the value that can be supplied for a given context, including field 
	* lengths, allowed characters, enumerations, etc. (Note: added "Info" sufiix to 
	* type
	*/
	public FieldDescriptorInfo getContextValueDescriptor();
	
	
	
	/**
	* Set Enumeration Context Identifier
	*
	* Type: enumContextTypeKey
	*
	* Identifier for the context modifier for an enumeration.
	*/
	public void setType(String type);
	
	/**
	* Get Enumeration Context Identifier
	*
	* Type: enumContextTypeKey
	*
	* Identifier for the context modifier for an enumeration.
	*/
	public String getType();
	
	
	
}

