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



public interface ValidationResultInfo
{
	
	/**
	* Set Element
	*
	* Type: string
	*
	* Indication of which element the validation result pertains to. In the case of 
	* validation results which are stem from a cross type constraint, the element 
	* should be the containing object. Ex. if two citizenships (which are embedded in 
	* the person) fail a uniqueness constraint, the element indicated should be the 
	* person.
	*/
	public void setElement(String element);
	
	/**
	* Get Element
	*
	* Type: string
	*
	* Indication of which element the validation result pertains to. In the case of 
	* validation results which are stem from a cross type constraint, the element 
	* should be the containing object. Ex. if two citizenships (which are embedded in 
	* the person) fail a uniqueness constraint, the element indicated should be the 
	* person.
	*/
	public String getElement();
	
	
	
	/**
	* Set Error Level
	*
	* Type: string
	*
	* Indication of the error level of the validation result. Allowed values are OK, 
	* WARN, and ERROR.
	*/
	public void setErrorLevel(String errorLevel);
	
	/**
	* Get Error Level
	*
	* Type: string
	*
	* Indication of the error level of the validation result. Allowed values are OK, 
	* WARN, and ERROR.
	*/
	public String getErrorLevel();
	
	
	
	/**
	* Set Message
	*
	* Type: string
	*
	* Message generated for the validation. Describes failure conditions and success 
	* aspects. May have additional meta about the specifics of the constraint 
	* violated.
	*/
	public void setMessage(String message);
	
	/**
	* Get Message
	*
	* Type: string
	*
	* Message generated for the validation. Describes failure conditions and success 
	* aspects. May have additional meta about the specifics of the constraint 
	* violated.
	*/
	public String getMessage();
	
	
	
	/**
	* Set Suggested Task
	*
	* Type: string
	*
	* Indication of a potential task stemming from the current validation result. In 
	* the case of a warning or error, this may be a corrective action
	*/
	public void setTask(String task);
	
	/**
	* Get Suggested Task
	*
	* Type: string
	*
	* Indication of a potential task stemming from the current validation result. In 
	* the case of a warning or error, this may be a corrective action
	*/
	public String getTask();
	
	
	
}

