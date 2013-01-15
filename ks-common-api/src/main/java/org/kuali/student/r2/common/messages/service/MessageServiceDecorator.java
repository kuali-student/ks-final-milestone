/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

import java.util.List;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;

/**
 * Refer to service contract javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class MessageServiceDecorator implements MessageService {

    private MessageService nextDecorator;

    public MessageService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(MessageService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<LocaleInfo> getLocales(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getLocales(contextInfo);
    }

    @Override
    public List<String> getMessageGroupKeys(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessageGroupKeys(contextInfo);
    }

    @Override
    public MessageInfo getMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessage(localeInfo, messageGroupKey, messageKey, contextInfo);
    }

    @Override
    public List<MessageInfo> getMessagesByGroup(LocaleInfo localeInfo, String messageGroupKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessagesByGroup(localeInfo, messageGroupKey, contextInfo);
    }

    @Override
    public List<MessageInfo> getMessagesByGroups(LocaleInfo localeInfo, List<String> messageGroupKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMessagesByGroups(localeInfo, messageGroupKeys, contextInfo);
    }

    @Override
    public MessageInfo updateMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateMessage(localeInfo, messageGroupKey, messageKey, messageInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteMessage(localeInfo, messageGroupKey, messageKey, contextInfo);
    }

    @Override
    public StatusInfo createMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().createMessage(localeInfo, messageGroupKey, messageKey, messageInfo, contextInfo);
    }

    public List<ValidationResultInfo> validateProposal(String validationTypeKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateProposal(validationTypeKey, messageInfo, contextInfo);
    }
    
    
}
