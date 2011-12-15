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
import java.util.List;

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
     * Retrieves the list of locales supported by this service
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return locales supported by this service
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LocaleInfo> getLocales(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of message group keys known by the service
     *
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return message group keys
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMessageGroupKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves message information
     *
     * @param localeInfo      Identifier for the locale
     * @param messageGroupKey group to which the message belongs
     * @param messageKey      message key
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return message information
     * @throws DoesNotExistException     messageGroupKey, messageKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException messageGroupKey, messageKey, contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public MessageInfo getMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve messages associated with a locale and group
     *
     * @param localeInfo      locale information
     * @param messageGroupKey group to which the messages belong
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return information about the messages
     * @throws DoesNotExistException     messageGroupKey is not found
     * @throws InvalidParameterException messageGroupKey is not valid
     * @throws MissingParameterException messageGroupKey, contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageInfo> getMessages(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieve messages associated with a locale and specified groups
     *
     * @param localeInfo       locale information
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
    public List<MessageInfo> getMessagesByGroups(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKeys") List<String> messageGroupKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Update message associated with a locale and group
     *
     * @param localeInfo  locale information
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
    public MessageInfo updateMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Delete the message associated with a locale and group
     *
     * @param localeInfo  locale information
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
    public StatusInfo deleteMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Add a message to the locale and group
     *
     * @param localeInfo locale information
     * @param messageGroupKey message group
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
    public StatusInfo addMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
