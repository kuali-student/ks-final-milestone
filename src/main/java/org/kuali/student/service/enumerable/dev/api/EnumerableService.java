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
package org.kuali.student.service.enumerable.dev.api;


import java.util.Date;
import java.util.List;


public interface EnumerableService
{
	
	/**
	* Retrieves the list of Enumeration Values for a particular Enumeration with a 
	* certain Context for a particular date. The values returned should be those where 
	* the supplied date is between the effective and expiration dates. Certain 
	* enumerations may not support this functionality (if for example the value is 
	* derived and does not have effective and/or expiration dates).
	* 
	* @param enumerationKey - enumerationKey - Identifier for the Enumeration
	* @param contextType - enumContextTypeKey - Identifier for the enumeration context 
	* type
	* @param contextValue - string - Value for the enumeration context
	* @param contextDate - dateTime - date and time to get the enumeration for
	* @return list of Codes and Values
	*/
	public List<EnumeratedValueInfo> getEnumeration(String enumerationKey, String contextType, String contextValue, Date contextDate)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	;
}

