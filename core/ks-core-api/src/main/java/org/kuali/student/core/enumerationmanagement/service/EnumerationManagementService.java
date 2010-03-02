/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.enumerationmanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationMetaInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.service.SearchService;

@WebService(name = "EnumerationManagementService", targetNamespace = "http://student.kuali.org/wsdl/enumerationmanagement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EnumerationManagementService extends SearchService {

	/**
	 * Retrieves the list of meta information for the enumerations supported by
	 * this service.
	 * 
	 * @return list of enumeration meta information
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<EnumerationMetaInfo> getEnumerationMetas()
			throws OperationFailedException;

	/**
	 * Retrieves meta information for a particular Enumeration. The meta
	 * information should describe constraints on the various fields comprising
	 * the enumeration as well as the allowed contexts.
	 * 
	 * @param enumerationKey
	 *            Identifier for the Enumeration
	 * @return Meta information about an enumeration
	 * @throws DoesNotExistException
	 *             enumerationKey not found
	 * @throws InvalidParameterException
	 *             invalid enumerationKey
	 * @throws MissingParameterException
	 *             missing enumerationKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public EnumerationMetaInfo getEnumerationMeta(
			@WebParam(name = "enumerationKey") String enumerationKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of values for a particular enumeration with a certain
	 * context for a particular date. The values returned should be those where
	 * the supplied date is between the effective and expiration dates. Certain
	 * enumerations may not support this functionality.
	 * 
	 * @param enumerationKey
	 *            Identifier for the Enumeration
	 * @param contextType
	 *            Identifier for the enumeration context type
	 * @param contextValue
	 *            Value for the enumeration context
	 * @param contextDate
	 *            date and time to get the enumeration for
	 * @return list of Codes and Values
	 * @throws DoesNotExistException
	 *             enumerationKey not found
	 * @throws InvalidParameterException
	 *             invalid enumerationKey, contextKey, contextValue, contextDate
	 * @throws MissingParameterException
	 *             missing enumerationKey, contextKey, contextValue, contextDate
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<EnumeratedValueInfo> getEnumeration(
			@WebParam(name = "enumerationKey") String enumerationKey,
			@WebParam(name = "contextType") String contextType,
			@WebParam(name = "contextValue") String contextValue,
			@WebParam(name = "contextDate") Date contextDate)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Adds an value to a particular Enumeration.
	 * 
	 * @param enumerationKey
	 *            Identifier for the Enumeration
	 * @param enumeratedValue
	 *            Value to be added
	 * @return newly created enumerated value
	 * @throws AlreadyExistsException
	 *             enumerated value already exists
	 * @throws InvalidParameterException
	 *             invalid enumerationKey, code, enumeratedValue
	 * @throws MissingParameterException
	 *             missing enumerationKey, code, enumeratedValue
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public EnumeratedValueInfo addEnumeratedValue(
			@WebParam(name = "enumerationKey") String enumerationKey,
			@WebParam(name = "enumeratedValue") EnumeratedValueInfo enumeratedValue)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Updates a value in a particular Enumeration. The pattern in this
	 * signature is different from most updates in that it is unlikely for
	 * multiple individuals or processes to be altering the same construct at
	 * the same time.
	 * 
	 * @param enumerationKey
	 *            Identifier for the Enumeration
	 * @param code
	 *            code identifying the value to be updated
	 * @param enumeratedValue
	 *            updated information on the value
	 * @return updated enumerated value
	 * @throws DoesNotExistException
	 *             enumerationKey, code not found
	 * @throws InvalidParameterException
	 *             invalid enumerationKey, enumeratedValue
	 * @throws MissingParameterException
	 *             missing enumerationKey, enumeratedValue
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public EnumeratedValueInfo updateEnumeratedValue(
			@WebParam(name = "enumerationKey") String enumerationKey,
			@WebParam(name = "code") String code,
			@WebParam(name = "enumeratedValue") EnumeratedValueInfo enumeratedValue)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Removes a value from a particular Enumeration. This particular operation
	 * should be used sparingly, as removal of a value may lead to dangling
	 * references. It is suggested that standard procedure should be to update
	 * the expiration date for the value so that it is seen as expired.
	 * 
	 * @param enumerationKey
	 *            Identifier for the Enumeration
	 * @param code
	 *            code identifying the value to be removed
	 * @return status of the operation
	 * @throws DoesNotExistException
	 *             enumerationKey, code not found
	 * @throws InvalidParameterException
	 *             invalid enumerationKey, enumeratedValue
	 * @throws MissingParameterException
	 *             missing enumerationKey, enumeratedValue
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public StatusInfo removeEnumeratedValue(
			@WebParam(name = "enumerationKey") String enumerationKey,
			@WebParam(name = "code") String code) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

}
