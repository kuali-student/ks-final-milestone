package org.kuali.student.message.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.message.entity.MessageEntity;

public interface MessageManagementDAO {
	public void addMessage(MessageEntity me);
	public int getTotalMessages();
    public List<String> getLocales();
    public List<String> getMessageGroups();
    public MessageEntity getMessage(String locale, String groupKey, String messageKey);
    public List<MessageEntity> getMessages(String locale, String groupKey);
    public List<MessageEntity> getMessagesByGroups(String locale, List<String> groupKeys);
    public MessageEntity updateMessage(String locale, String groupKey, String messageKey, MessageEntity updatedMessage);
}