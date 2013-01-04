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

package org.kuali.student.core.messages.dao;

import java.util.List;

import org.kuali.student.core.messages.entity.MessageEntity;


public interface MessageManagementDAO {
	public MessageEntity addMessage(MessageEntity me);
	public int getTotalMessages();
    public List<String> getLocales();
    public List<String> getMessageGroups();
    public MessageEntity getMessage(String locale, String groupKey, String messageKey);
    public List<MessageEntity> getMessages(String locale, String groupKey);
    public List<MessageEntity> getMessagesByGroups(String locale, List<String> groupKeys);
    public MessageEntity updateMessage(String locale, String groupKey, String messageKey, MessageEntity updatedMessage);
}
