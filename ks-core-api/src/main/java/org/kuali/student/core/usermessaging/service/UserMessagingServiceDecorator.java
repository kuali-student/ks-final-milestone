/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import java.util.List;

public class UserMessagingServiceDecorator implements UserMessagingService {

    private UserMessagingService nextDecorator;

    public UserMessagingService getNextDecorator() throws OperationFailedException {
        if (nextDecorator == null){
            throw  new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(UserMessagingService nextDecorator){
        this.nextDecorator = nextDecorator;
    }

    @Override
    public MessageCategoryInfo getMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessageCategory(messageCategoryKey,contextInfo);
    }

    @Override
    public List<MessageCategoryInfo> getMessageCategorysByKeys(@WebParam(name = "messageCategoryKeys") List<String> messageCategoryKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessageCategorysByKeys(messageCategoryKeys,contextInfo);
    }

    @Override
    public List<String> getMessageCategoryKeysByType(@WebParam(name = "messageCategoryTypeKey") String messageCategoryTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessageCategoryKeysByType(messageCategoryTypeKey,contextInfo);
    }

    @Override
    public List<String> searchForMessageCategoryKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForMessageCategoryKeys(criteria,contextInfo);
    }

    @Override
    public List<MessageCategoryInfo> searchForMessageCategorys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForMessageCategorys(criteria,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateMessageCategory(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "messageCategoryTypeKey") String messageCategoryTypeKey, @WebParam(name = "messageCategoryInfo") MessageCategoryInfo messageCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateMessageCategory(validationTypeKey,messageCategoryTypeKey,messageCategoryInfo,contextInfo);
    }

    @Override
    public MessageCategoryInfo createMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey, @WebParam(name = "messageCategoryTypeKey") String messageCategoryTypeKey, @WebParam(name = "messageCategoryInfo") MessageCategoryInfo messageCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createMessageCategory(messageCategoryKey,messageCategoryTypeKey,messageCategoryInfo,contextInfo);
    }

    @Override
    public MessageCategoryInfo updateMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey, @WebParam(name = "messageCategoryInfo") MessageCategoryInfo messageCategoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateMessageCategory(messageCategoryKey,messageCategoryInfo,contextInfo);
    }

    @Override
    public StatusInfo deleteMessageCategory(@WebParam(name = "messageCategoryKey") String messageCategoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteMessageCategory(messageCategoryKey,contextInfo);
    }

    @Override
    public MessageInfo getMessage(@WebParam(name = "messageId") String messageId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessage(messageId,contextInfo);
    }

    @Override
    public List<MessageInfo> getMessagesByIds(@WebParam(name = "messageIds") List<String> messageIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessagesByIds(messageIds,contextInfo);
    }

    @Override
    public List<String> getMessageIdsByType(@WebParam(name = "messageTypeKey") String messageTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessageIdsByType(messageTypeKey,contextInfo);
    }

    @Override
    public List<String> searchForMessageIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForMessageIds(criteria,contextInfo);
    }

    @Override
    public List<MessageInfo> searchForMessages(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForMessages(criteria,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateMessage(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "messageTypeKey") String messageTypeKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateMessage(validationTypeKey,messageTypeKey,messageInfo,contextInfo);
    }

    @Override
    public MessageInfo createMessage(@WebParam(name = "messageTypeKey") String messageTypeKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createMessage(messageTypeKey,messageInfo,contextInfo);
    }

    @Override
    public MessageInfo updateMessage(@WebParam(name = "messageId") String messageId, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateMessage(messageId,messageInfo,contextInfo);
    }

    @Override
    public StatusInfo deleteMessage(@WebParam(name = "messageId") String messageId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteMessage(messageId,contextInfo);
    }

    @Override
    public TemplateInfo getTemplate(@WebParam(name = "templateId") String templateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTemplate(templateId,contextInfo);
    }

    @Override
    public List<TemplateInfo> getTemplatesByIds(@WebParam(name = "templateIds") List<String> templateIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTemplatesByIds(templateIds,contextInfo);
    }

    @Override
    public List<String> getTemplateIdsByType(@WebParam(name = "templateTypeKey") String templateTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTemplateIdsByType(templateTypeKey,contextInfo);
    }

    @Override
    public List<String> searchForTemplateIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForTemplateIds(criteria,contextInfo);
    }

    @Override
    public List<TemplateInfo> searchForTemplates(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForTemplates(criteria,contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateTemplate(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "templateTypeKey") String templateTypeKey, @WebParam(name = "templateInfo") TemplateInfo templateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateTemplate(validationTypeKey,templateTypeKey,templateInfo,contextInfo);
    }

    @Override
    public TemplateInfo createTemplate(@WebParam(name = "templateTypeKey") String templateTypeKey, @WebParam(name = "templateInfo") TemplateInfo templateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createTemplate(templateTypeKey,templateInfo,contextInfo);
    }

    @Override
    public TemplateInfo updateTemplate(@WebParam(name = "templateId") String templateId, @WebParam(name = "templateInfo") TemplateInfo templateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateTemplate(templateId,templateInfo,contextInfo);
    }

    @Override
    public StatusInfo deleteTemplate(@WebParam(name = "templateId") String templateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteTemplate(templateId,contextInfo);
    }
}
