/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.core.messages.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.student.r1.core.messages.entity.MessageEntity;
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
import org.kuali.student.r2.common.messages.service.MessageService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * This is poor man's implementation of a mock messages service to be used for UI testing, it will load gwt-messages.xml
 * configured via the set method.
 * 
 * @author Kuali Student Team
 */
public class MessageServiceMock implements MessageService {

    final Logger logger = Logger.getLogger(MessageServiceMock.class);

    List<String> messageFiles;

    Map<String, LocaleMessages> messages = new HashMap<String, LocaleMessages>();

    private static class LocaleMessages {
        Map<String, Map<String, String>> localeMessages = new HashMap<String, Map<String, String>>();

        public Map<String, String> getMessages(String groupKey) {
            return localeMessages.get(groupKey);
        }

        public Set<String> getGroups() {
            return localeMessages.keySet();
        }

        public void putMessage(String groupKey, String messageKey, String message) {
            Map<String, String> groupMessages;

            groupMessages = localeMessages.get(groupKey);
            if (groupMessages == null) {
                groupMessages = new HashMap<String, String>();
            }
            groupMessages.put(messageKey, message);
            this.localeMessages.put(groupKey, groupMessages);
        }
    }

    private void putMessage(String locale, String group, String key, String value) {

        LocaleMessages localeMessages = messages.get(locale);
        if (localeMessages == null) {
            localeMessages = new LocaleMessages();
        }
        localeMessages.putMessage(group, key, value);
        this.messages.put(locale, localeMessages);
    }

    public List<String> getMessageFiles() {
        return messageFiles;
    }

    public void setMessageFiles(List<String> messageFiles) {
        this.messageFiles = messageFiles;

        // bootstrap the data
        logger.debug("Bootstrap messages started.");
        if (this.messages.isEmpty()) {
            for (String messageFileName : messageFiles) {
                Resource res;
                try {
                    if (messageFileName.startsWith("classpath:")) {
                        res = new ClassPathResource(messageFileName.substring("classpath:".length()));
                    } else {
                        res = new FileSystemResource(messageFileName);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                XmlBeanFactory factory = new XmlBeanFactory(res);

                try {
                    Object[] beanArray = factory.getBeansOfType(MessageEntity.class).values().toArray();
                    for (Object o : beanArray) {
                        MessageEntity m = (MessageEntity) o;
                        putMessage(m.getLocale(), m.getGroupName(), m.getMessageId(), m.getValue());
                    }
                } catch (Exception e) {
                    logger.debug(e);
                }
            }
        }

        logger.debug("Bootstrap messages finished.");
    }

    /**
     * @see org.kuali.student.common.messages.service.MessageService#getLocales()
     */
    @Override
    public List<LocaleInfo> getLocales(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LocaleInfo> locales = new ArrayList<LocaleInfo>();

        if (!this.messages.isEmpty()) {
            for (String locale : this.messages.keySet()){
                LocaleInfo localeInfo = new LocaleInfo();
                localeInfo.setLocaleLanguage(locale);
                locales.add(localeInfo);
            }
        }

        return locales;
    }

    /**
     * @see org.kuali.student.common.messages.service.MessageService#getMessageGroups()
     */
    @Override
    public List<String> getMessageGroupKeys(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Iterator<String> i = messages.keySet().iterator();
        Set<String> groupKeySet = new HashSet<String>();
        while (i.hasNext()) {
            String locale = i.next();
            groupKeySet.addAll(messages.get(locale).getGroups());
        }
        return new ArrayList<String>(groupKeySet);
    }

    /**
     * @see org.kuali.student.common.messages.service.MessageService#getMessage(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public MessageInfo getMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MessageInfo m = new MessageInfo();
        m.setGroupName(messageGroupKey);
        m.setLocale(localeInfo);
        m.setKey(messageKey);
        m.setValue(this.messages.get(localeInfo.getLocaleLanguage()).getMessages(messageGroupKey).get(messageKey));

        return m;
    }

    /**
     * @see org.kuali.student.common.messages.service.MessageService#getMessages(java.lang.String, java.lang.String)
     */
    @Override
    public List<MessageInfo> getMessages(LocaleInfo localeInfo, String messageGroupKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Map<String, String> groupMessages = ((LocaleMessages) messages.get(localeInfo.getLocaleLanguage())).getMessages(messageGroupKey);
        List<MessageInfo> messageArrayList = new ArrayList<MessageInfo>();

        Iterator<String> i = groupMessages.keySet().iterator();
        while (i.hasNext()) {
            String id = i.next();
            MessageInfo m = new MessageInfo();
            m.setGroupName(messageGroupKey);
            m.setKey(id);
            m.setValue(groupMessages.get(id));
            LocaleInfo locale = new LocaleInfo();
            locale.setLocaleLanguage(localeInfo.getLocaleLanguage());
            m.setLocale(locale);
            messageArrayList.add(m);
        }
        return messageArrayList;
    }

    /**
     * @see org.kuali.student.common.messages.service.MessageService#getMessagesByGroups(java.lang.String,
     *      org.kuali.student.common.messages.dto.MessageGroupKeyList)
     */
    @Override
    public List<MessageInfo> getMessagesByGroups(LocaleInfo localeInfo, List<String> messageGroupKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MessageInfo> messageArrayList = new ArrayList<MessageInfo>();
        for (String groupKey : messageGroupKeys) {
            List<MessageInfo> messageList = getMessages(localeInfo, groupKey, contextInfo);
            messageArrayList.addAll(messageList);
        }
        return messageArrayList;    }

    /**
     * @see org.kuali.student.common.messages.service.MessageService#updateMessage(java.lang.String, java.lang.String,
     *      java.lang.String, org.kuali.student.common.messages.dto.Message)
     */
    @Override
    public MessageInfo updateMessage(LocaleInfo localeInfo, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if (getMessage(localeInfo, messageInfo.getGroupName(), messageKey, contextInfo) != null) {
            putMessage(localeInfo.getLocaleLanguage(), messageInfo.getGroupName(), messageKey, messageInfo.getValue());
        }
        return messageInfo;
    }

    @Override
    public StatusInfo deleteMessage(LocaleInfo localeInfo, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo addMessage(LocaleInfo localeInfo, String messageGroupKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleMessages localeMessages = this.messages.get(messageInfo.getLocale());
        if (localeMessages == null) {
            localeMessages = new LocaleMessages();
        }
        localeMessages.putMessage(messageInfo.getGroupName(), messageInfo.getKey(), messageInfo.getValue());
        this.messages.put(messageInfo.getLocale().getLocaleLanguage(), localeMessages);

        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

}
