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

package org.kuali.student.r1.core.messages.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.r1.common.messages.MessageException;
import org.kuali.student.r1.core.messages.dao.MessageManagementDAO;
import org.kuali.student.r1.core.messages.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Deprecated
public class MessageManagementDAOImpl implements MessageManagementDAO {
    private EntityManager entityManager;
    
    final static Logger logger = LoggerFactory.getLogger(MessageManagementDAOImpl.class);
    
    @PersistenceContext(unitName = "MessageManagement")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public EntityManager getEntityManager(){
        return this.entityManager; 
    }
    
    public MessageEntity addMessage(MessageEntity me){
    	MessageEntity result = null;
    	try{
	        entityManager.persist(me);
	        result = this.getMessage(me.getLocale(), me.getGroupName(), me.getId());
    	}
		catch(Exception e){
		    throw new MessageException("adding message data query failed.", e);
		}
		return result;
    }
    
    public int getTotalMessages(){
    	int count = 0;
    	try{
	        Query query = entityManager.createQuery("SELECT COUNT(m) FROM MessageEntity m");
	        count = ((Long)query.getSingleResult()).intValue();
    	}
        catch(Exception e){
	    	logger.error("count query failed.", e);
			throw new MessageException("count query failed.", e);
	    }
        return count;
    }
    
	@SuppressWarnings("unchecked")
	public List<String> getLocales() {
		List<String> locales;
    	try{
	        Query query = entityManager.createQuery("SELECT distinct m.locale FROM MessageEntity m");
	        locales = (List<String>) query.getResultList();
    	}
        catch(Exception e){
	    	logger.error("getLocales query failed.", e);
			throw new MessageException("getLocales query failed.", e);
	    }
		return locales;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getMessageGroups() {
		List<String> groups;
    	try{
	        Query query = entityManager.createQuery("SELECT distinct m.groupName FROM MessageEntity m");
	        groups = (List<String>) query.getResultList();
    	}
        catch(Exception e){
	    	logger.error("getMessageGroups query failed.", e);
			throw new MessageException("getMessageGroups query failed.", e);
	    }
		return groups;
	}
	
	public MessageEntity getMessage(String locale, String groupKey, String messageKey) {

		MessageEntity entity = null;
		try{
			Query query = entityManager.createQuery(
			        "select m from MessageEntity m " +
			        "where m.locale = :locale and " +
			        "m.groupName = :groupKey and " +
			        "m.messageId = :messageKey");
			 query.setParameter("locale", locale);
			 query.setParameter("groupKey", groupKey);
			 query.setParameter("messageKey", messageKey);
			 
			 if(!query.getResultList().isEmpty()){
			    entity = (MessageEntity)query.getResultList().get(0);
			 }else{
			    return null;
			 }
		}
		catch(Exception e){
			logger.error("getMessage query failed.", e);
			throw new MessageException("getMessage query failed.", e);
		}
		return entity;
	}
	

	@SuppressWarnings("unchecked")
	public List<MessageEntity> getMessages(String locale, String groupKey) {
		List<MessageEntity> list;
		try{
			Query query = entityManager.createQuery(
		            "select m from MessageEntity m " +
		            "where m.locale = :locale and " +
		            "m.groupName = :groupKey ");
			 query.setParameter("locale", locale);
			 query.setParameter("groupKey", groupKey);
			 list = (List<MessageEntity>)query.getResultList();
		}
		catch(Exception e){
			logger.error("getMessages query failed.", e);
			throw new MessageException("getMessages query failed.", e);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageEntity> getMessagesByGroups(String locale, List<String> groupKeys) {
		List<MessageEntity> list = new ArrayList<MessageEntity>();
		try{
			for(String currentKey: groupKeys){
				Query query = entityManager.createQuery(
			            "select m from MessageEntity m " +
			            "where m.locale = :locale and " +
			            "m.groupName = :groupKey ");
				 query.setParameter("locale", locale);
				 query.setParameter("groupKey", currentKey);
				 list.addAll((List<MessageEntity>)query.getResultList());
			}
		}
		catch(Exception e){
			logger.error("getMessagesByGroups query failed.", e);
			throw new MessageException("getMessagesByGroups query failed.", e);
		}
		return list;
	}
	
	//assuming all keys are staying the same
	public MessageEntity updateMessage(String locale, String groupKey, String messageKey, MessageEntity updatedMessage) {
		MessageEntity returnValue = null;
    	try{
	    	MessageEntity theEntity = this.getMessage(locale, groupKey, messageKey);
	        if(theEntity != null){
        		theEntity.setValue(updatedMessage.getValue());
        		theEntity.setMessageId(updatedMessage.getMessageId());
        		theEntity.setLocale(updatedMessage.getLocale());
        		theEntity.setGroupName(updatedMessage.getGroupName());
        		entityManager.merge(theEntity);
        		returnValue = theEntity;
        	}
    	}
        catch(Exception e){
			throw new MessageException("updateMessage query failed.", e);
        }
        return returnValue;
	}
    

}
