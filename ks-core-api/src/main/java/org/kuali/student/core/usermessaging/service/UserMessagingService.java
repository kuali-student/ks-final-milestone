/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.usermessaging.service;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.core.usermessaging.dto.MessageCategoryInfo;
import org.kuali.student.core.usermessaging.dto.MessageInfo;
import org.kuali.student.core.usermessaging.dto.TemplateInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService(name = "UserMessagingService", serviceName = "GUserMessagingService", portName = "UserMessagingService", targetNamespace = UserMessagingServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface UserMessagingService {

    /**
     * Retrieves a single MessageCategory by MessageCategory Key.
     *
     * @param messageCategoryKey the identifier for the messageCategory to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the messageCategory requested
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     messageCategoryKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException messageCategoryKey or
     *                                   contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public MessageCategoryInfo getMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of MessageCategorys from a list of
     * MessageCategory Keys. The returned list may be in any order and
     * if duplicates Keys are supplied, a unique set may or may not be
     * returned.
     *
     * @param messageCategoryKeys a list of MessageCategory identifiers
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of MessageCategorys
     * @throws DoesNotExistException     a messageCategoryKey in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException messageCategoryKeys, a Key in
     *                                   messageCategoryKeys, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageCategoryInfo> getMessageCategorysByKeys(@WebParam(name = "messageCategoryKeys") List<String> messageCategoryKeys,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of MessageCategory Keys by MessageCategory Type.
     *
     * @param messageCategoryTypeKey an identifier for a
     *                         MessageCategory Type
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of the service operation
     * @return a list of MessageCategory identifiers matching
     *         messageCategoryTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException messageCategoryTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMessageCategoryKeysByType(@WebParam(name = "messageCategoryTypeKey") String messageCategoryTypeKey,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for MessageCategorys based on the criteria and returns
     * a list of MessageCategory keys which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of MessageCategory Keys matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForMessageCategoryKeys(@WebParam(name = "criteria") QueryByCriteria criteria,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for MessageCategory based on the criteria and returns
     * a list of MessageCategorys which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of MessageCategorys matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageCategoryInfo> searchForMessageCategorys(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a MessageCategory. Depending on the MessageCategory of
     * validationType, this validation could be limited to tests on
     * just the current MessageCategory and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * MessageCategory. If an identifier is present for the
     * MessageCategory (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the MessageCategory can be updated to the new MessageCategorys. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param messageCategoryTypeKey  the identifier for the messageCategory Type
     * @param messageCategoryInfo     the Parameter information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey, messageCategoryTypeKey
     *                                    is not found
     * @throws InvalidParameterException messageCategoryInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, messageCategoryTypeKey,contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateMessageCategory(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "messageCategoryTypeKey") String messageCategoryTypeKey,
                                                        @WebParam(name = "messageCategoryInfo") MessageCategoryInfo messageCategoryInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new MessageCategory. The MessageCategory Key, Value Type, Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param messageCategoryTypeKey the identifier for the Type of
     *                         the new MessageCategory
     * @param messageCategoryKey     the key of the messageCategory - for example, 'registration'
     * @param messageCategoryInfo    the data with which to create the
     *                         MessageCategory
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return the new MessageCategory
     * @throws DoesNotExistException     messageCategoryTypeKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException messageCategoryInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException messageCategoryTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     */
    public MessageCategoryInfo createMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey,
                                         @WebParam(name = "messageCategoryTypeKey") String messageCategoryTypeKey,
                                         @WebParam(name = "messageCategoryInfo") MessageCategoryInfo messageCategoryInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing MessageCategory. The MessageCategory Key,
     * Type, and Meta information may not be changed.
     *
     * @param messageCategoryKey   the key for the MessageCategory
     *                      to be updated
     * @param messageCategoryInfo the new data for the MessageCategory
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of service operation
     * @return the updated MessageCategory
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        messageCategoryKey not found
     * @throws InvalidParameterException    messageCategoryInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    messageCategoryKey,
     *                                      messageCategoryInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public MessageCategoryInfo updateMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey,
                                         @WebParam(name = "messageCategoryInfo") MessageCategoryInfo messageCategoryInfo,
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
     * Deletes an existing MessageCategory.
     *
     * @param messageCategoryKey the key for the MessageCategory
     *                    to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     messageCategoryKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException messageCategoryKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;



    /**
     * Retrieves a single Message by Message Id.
     *
     * @param messageId   the identifier for the message to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the Message requested
     * @throws DoesNotExistException     messageId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException messageId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public MessageInfo getMessage(@WebParam(name = "messageId") String messageId,
                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Messages from a list of
     * Message Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param messageIds    a list of Message identifiers
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Messages
     * @throws DoesNotExistException     a messageId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException messageIds, an Id in
     *                                   messageIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageInfo> getMessagesByIds(@WebParam(name = "messageIds") List<String> messageIds,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Message Ids by Message Type.
     *
     * @param messageTypeKey an identifier for a
     *                     Message Type
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of Message identifiers matching
     *         messageTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException messageTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMessageIdsByType(@WebParam(name = "messageTypeKey") String messageTypeKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Messages based on the criteria and returns
     * a list of Message identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Message Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForMessageIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Messages based on the criteria and returns
     * a list of Messages which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Messages matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MessageInfo> searchForMessages(@WebParam(name = "criteria") QueryByCriteria criteria,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Message. Depending on the message of
     * validationType, this validation could be limited to tests on
     * just the current Message and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * Message. If an identifier is present for the
     * Message (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the Message can be updated to the new messages. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param messageTypeKey      the identifier for the message Type
     * @param messageInfo         the Message information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey,messageTypeKey is not found
     * @throws InvalidParameterException messageInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, messageTypeKey,
     *                                   messageInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateMessage(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                    @WebParam(name = "messageTypeKey") String messageTypeKey,
                                                    @WebParam(name = "messageInfo") MessageInfo messageInfo,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Message. The Message Id, Message Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param messageTypeKey the identifier for the Type of
     *                     the new Message
     * @param messageInfo    the data with which to create the
     *                     Message
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the new Message
     * @throws DoesNotExistException     messageTypeKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException messageInfo
     *                                   contextInfo is not valid
     * @throws MissingParameterException messageTypeKey, messageInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public MessageInfo createMessage(@WebParam(name = "messageTypeKey") String messageTypeKey,
                                 @WebParam(name = "messageInfo") MessageInfo messageInfo,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Message. The Message Id,
     * Type, and Meta information may not be changed.
     *
     * @param messageId     the identifier for the Message
     *                    to be updated
     * @param messageInfo   the new data for the Message
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the updated Message
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        messageId not found
     * @throws InvalidParameterException    messageInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    messageId, messageInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public MessageInfo updateMessage(@WebParam(name = "messageId") String messageId,
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
     * Deletes an existing Message.
     *
     * @param messageId     the identifier for the Message
     *                      to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     messageId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException messageId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteMessage(@WebParam(name = "messageId") String messageId,
                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single Template by Template Id.
     *
     * @param templateId     the identifier for the template to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the Template requested
     * @throws DoesNotExistException     templateId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException templateId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TemplateInfo getTemplate(@WebParam(name = "templateId") String templateId,
                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Templates from a list of
     * Template Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param templateIds    a list of Template identifiers
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Templates
     * @throws DoesNotExistException     a templateId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException templateIds, an Id in
     *                                   templateIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TemplateInfo> getTemplatesByIds(@WebParam(name = "templateIds") List<String> templateIds,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Template Ids by Template Type.
     *
     * @param templateTypeKey an identifier for a
     *                     Template Type
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of Template identifiers matching
     *         templateTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException templateTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getTemplateIdsByType(@WebParam(name = "templateTypeKey") String templateTypeKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Templates based on the criteria and returns
     * a list of Template identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Template Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForTemplateIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Templates based on the criteria and returns
     * a list of Templates which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Templates matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TemplateInfo> searchForTemplates(@WebParam(name = "criteria") QueryByCriteria criteria,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Template. Depending on the template of
     * validationType, this validation could be limited to tests on
     * just the current Template and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * Template. If an identifier is present for the
     * Template (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the Template can be updated to the new templates. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param templateTypeKey      the identifier for the template Type
     * @param templateInfo         the Template information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey,templateTypeKey is not found
     * @throws InvalidParameterException templateInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, templateTypeKey,
     *                                   templateInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateTemplate(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                    @WebParam(name = "templateTypeKey") String templateTypeKey,
                                                    @WebParam(name = "templateInfo") TemplateInfo templateInfo,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Template. The Template Id, Template Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param templateTypeKey the identifier for the Type of
     *                     the new Template
     * @param templateInfo    the data with which to create the
     *                        Template
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the new Template
     * @throws DoesNotExistException     templateTypeKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException templateInfo
     *                                   contextInfo is not valid
     * @throws MissingParameterException templateTypeKey, templateInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public TemplateInfo createTemplate(@WebParam(name = "templateTypeKey") String templateTypeKey,
                                 @WebParam(name = "templateInfo") TemplateInfo templateInfo,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Template. The Template Id,
     * Type, and Meta information may not be changed.
     *
     * @param templateId     the identifier for the Template
     *                       to be updated
     * @param templateInfo   the new data for the Template
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the updated Template
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        templateId not found
     * @throws InvalidParameterException    templateInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    templateId, templateInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public TemplateInfo updateTemplate(@WebParam(name = "templateId") String templateId,
                                 @WebParam(name = "templateInfo") TemplateInfo templateInfo,
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
     * Deletes an existing Template.
     *
     * @param templateId     the identifier for the Template
     *                       to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     templateId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException templateId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteTemplate(@WebParam(name = "templateId") String templateId,
                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


}







