/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.messages.service;

import java.util.List;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.util.constants.MessageServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;

/**
 * The Message Service allows for the creation and management of
 * messages.
 *
 * @version 1.0
 * @author Kuali Student Services Team
 */

@WebService(name = "MessageService", targetNamespace = MessageServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface MessageService {

    /**
     * Retrieves the list of locales supported by this service.
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of locales supported by this service
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LocaleInfo> getLocales(@WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieves the list of message group keys known by the service
     *
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of message group keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMessageGroupKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieves message information.
     *
     * @param localeInfo the locale information
     * @param messageGroupKey an identifier for the message group to
     *        which the message belongs
     * @param messageKey the identifier for the requested message
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the requested message
     * @throws DoesNotExistException messageGroupKey or messageKey is
     *         not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException localeInfo, messageGroupKey,
     *         messageKey, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public MessageInfo getMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, 
                                  @WebParam(name = "messageGroupKey") String messageGroupKey, 
                                  @WebParam(name = "messageKey") String messageKey, 
                                  @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve messages associated with a locale and group.
     *
     * @param localeInfo the locale information
     * @param messageGroupKey an identifier for the message group to
     *        which the messages belong
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return a list of Messages
     * @throws DoesNotExistException messageGroupKey is not found
     * @throws InvalidParameterException loacleInfo or contextInfo is
     *         not valid
     * @throws MissingParameterException localeInfo, messageGroupKey,
     *         or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageInfo> getMessagesByGroup(@WebParam(name = "localeInfo") LocaleInfo localeInfo, 
                                                @WebParam(name = "messageGroupKey") String messageGroupKey, 
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Retrieve messages associated with a locale from a specified
     * list of groups.
     *
     * @param localeInfo the locale information
     * @param messageGroupKeys a list of identifiers for the message
     *        groups
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the list of Messages belonging to the list of message groups
     * @throws DoesNotExistException localeInfo or a messageGroupKey
     *         in messageGroupKeys is not found
     * @throws InvalidParameterException localeInfo or contextInfo is
     *          not valid
     * @throws MissingParameterException localeInfo, messageGroupKeys,
     *         a messageGroupKey in messagegroupKey, or contextInfo is
     *         missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageInfo> getMessagesByGroups(@WebParam(name = "localeInfo") LocaleInfo localeInfo, 
                                                 @WebParam(name = "messageGroupKeys") List<String> messageGroupKeys, 
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Validates a message.
     * 
     * Depending on the value of validationType, this validation could
     * be limited to tests on just the current object and its directly
     * contained subobjects or expanded to perform all tests related
     * to this object. If an identifier is present for the message and
     * a record is found for that identifier, the validation checks if
     * the message can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting
     * the validationType to the current object. This is a slightly
     * different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the
     * server assigning an identifier.
     * 
     * @param validationTypeKey Identifier of the extent of validation
     * @param messageInfo The message information to be tested.
     * @return Results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, messageInfo
     * @throws MissingParameterException missing validationTypeKey, messageInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateMessage(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                      @WebParam(name = "messageInfo") MessageInfo messageInfo,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException;

    /**
     * Create a new message for a locale and group.
     *
     * @param localeInfo the locale information
     * @param messageGroupKey an identifier for the message group
     * @param messageKey an identifier for the message within the group
     * @param messageInfo the message information to be added
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException messageGroupKey is not found
     * @throws InvalidParameterException localeInfo or contetInfo is
     *         not valid
     * @throws MissingParameterException localeInfo, messageGroupKey,
     *         or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException 
     */

    public StatusInfo createMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo,             
                                    @WebParam(name = "messageGroupKey") String messageGroupKey,             
                                    @WebParam(name = "messageKey") String messageKey, 
                                    @WebParam(name = "messageInfo") MessageInfo messageInfo, 
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
        throws DataValidationErrorException,
               DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;

    /**
     * Update message associated with a locale and group.
     *
     * @param localeInfo the locale information
     * @param messageGroupKey an identifier for the message group to
     *        which the messages belong
     * @param messageKey the indentifier for the message
     * @param messageInfo the message information to be updated
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return message information
     * @throws DoesNotExistException messageKey is not found
     * @throws DataValidationErrorException if invalid data
     * @throws InvalidParameterException localeInfo, messageInfo, or
     *         contextInfo is not valid
     * @throws MissingParameterException localeInfo, messageKey, messageInfo, 
     *         or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information
     *         designated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public MessageInfo updateMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, 
                                     @WebParam(name = "messageGroupKey") String messageGroupKey,
                                     @WebParam(name = "messageKey") String messageKey, 
                                     @WebParam(name = "messageInfo") MessageInfo messageInfo, 
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DataValidationErrorException,
               DoesNotExistException,
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException,
               ReadOnlyException, 
               VersionMismatchException;

    /**
     * Deletes the message associated with a locale and group for a
     * message key
     *
     * @param localeInfo the locale information
     * @param messageGroupKey an identifier for the message group to
     *        which the messages belong
     * @param messageKey an identifier for the Message to be deleted
     * @param contextInfo information containing the principalId and
     *        information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException messageKey not found for locale
     * @throws InvalidParameterException localeInfo or contextInfo is 
     *         not valid
     * @throws MissingParameterException localeInfo, messageKey, or
     *         contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, 
                                    @WebParam(name = "messageGroupKey") String messageGroupKey,
                                    @WebParam(name = "messageKey") String messageKey, 
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo) 
        throws DoesNotExistException, 
               InvalidParameterException, 
               MissingParameterException, 
               OperationFailedException, 
               PermissionDeniedException;    
}
