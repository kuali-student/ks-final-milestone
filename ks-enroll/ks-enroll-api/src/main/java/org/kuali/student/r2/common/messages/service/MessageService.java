/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.messages.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.messages.dto.LocaleKeysInfo;
import org.kuali.student.r2.common.messages.dto.MessageGroupKeysInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.dto.MessagesInfo;
import org.kuali.student.r2.common.util.constants.MessageServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * The Message Service allows for the creation and management of messages.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

@WebService(name = "MessageService", targetNamespace = MessageServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface MessageService {

    /**
     * Retrieves information about locales
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return locale keys
     * @throws MissingParameterException contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public LocaleKeysInfo getLocales(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves message group keys
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return information about a comment
     * @throws MissingParameterException contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MessageGroupKeysInfo getMessageGroups(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves message information
     *
     * @param localeKey       locale to which the message belongs
     * @param messageGroupKey group to which the message belongs
     * @param messageKey      message key
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return message information
     * @throws DoesNotExistException     specified localeKey, messageGroupKey, messageKey not found
     * @throws InvalidParameterException invalid localeKey, messageGroupKey, messageKey
     * @throws MissingParameterException localeKey, messageGroupKey, messageKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MessageInfo getMessage(@WebParam(name = "localeKey") String localeKey, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve messages associated with a locale and group
     *
     * @param localeKey       locale to which the messages belong
     * @param messageGroupKey group to which the messages belong
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return information about the messages
     * @throws DoesNotExistException     specified localeKey, messageGroupKey not found
     * @throws InvalidParameterException invalid localeKey, messageGroupKey
     * @throws MissingParameterException localeKey, messageGroupKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MessagesInfo getMessages(@WebParam(name = "localeKey") String localeKey, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve messages associated with a locale and specified groups
     *
     * @param localeKey        locale to which the messages belong
     * @param messageGroupKeys groups to which the messages belong
     * @param contextInfo      Context information containing the principalId and
     *                         locale information about the caller of service
     *                         operation
     * @return information about the messages
     * @throws DoesNotExistException     specified localeKey, any of messageGroupKeys not found
     * @throws InvalidParameterException invalid localeKey, messageGroupKeys
     * @throws MissingParameterException localeKey, messageGroupKeys, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MessagesInfo getMessagesByGroups(@WebParam(name = "localeKey") String localeKey, @WebParam(name = "messageGroupKeys") MessageGroupKeysInfo messageGroupKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update message
     *
     * @param messageKey  message key
     * @param messageInfo message information
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return message information
     * @throws DoesNotExistException     specified messageKey not found
     * @throws InvalidParameterException invalid messageKey, messageInfo
     * @throws MissingParameterException messageKey, messageInfo, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws ReadOnlyException         attempted update of readonly data
     * @throws VersionMismatchException  action was attempted on an out of date version
     */
    public MessageInfo updateMessage(@WebParam(name = "messageKey") String messageKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Delete the message
     *
     * @param messageKey  message key
     * @param contextInfo Context information containing the principalId and
     *                    information about the caller of service operation
     * @return status of the operation
     * @throws DoesNotExistException     specified messageKey not found
     * @throws InvalidParameterException invalid messageKey
     * @throws MissingParameterException messageKey not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMessage(@WebParam(name = "messageKey") String messageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Add a message to the locale and group
     *
     * @param localeKey       locale to which the messages belongs
     * @param messageGroupKey group to which the messages belong
     * @param messageInfo     message information
     * @param contextInfo     Context information containing the principalId and locale
     *                        information about the caller of service operation
     * @return status of the operation
     * @throws DoesNotExistException     specified localeKey, messageGroupKey not found
     * @throws InvalidParameterException invalid localeKey, messageGroupKey
     * @throws MissingParameterException localeKey, messageGroupKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addMessage(@WebParam(name = "localeKey") String localeKey, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
