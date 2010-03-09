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


import java.util.List;


public interface DictionaryService
{
	
	/**
	* Retrieves the list of Object Types used that are known by this service. Examples 
	* cluinfo,...
	* 
	* @return list of Objects
	*/
	public List<String> getObjectTypes()
		throws OperationFailedException
	;
	
	/**
	* Retrieves the basic Dictionary information about a particular object Structure. 
	* Including all variations based on a certain Type and State. Example: Given that 
	* a Clu is of type Course and in the state of Proposed, tell which fields are read 
	* only, mandatory, not applicable, have enumerations available, etc.
	* 
	* @param objectTypeKey - objectTypeKey - The identifier of the Object
	* @return objectStructure
	*/
	public ObjectStructureInfo getObjectStructure(String objectTypeKey)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
}

