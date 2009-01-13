package org.kuali.student.message.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@PersistenceFileLocation("classpath:META-INF/message-persistence.xml")
public class MessageManagementDAOImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.message.dao.impl.MessageManagementDAOImpl", testDataFile = "classpath:test-beans.xml")
    public MessageManagementDAOImpl messageManagementDAO;
    
    @Test
    public void getLocalesTest(){
    	List<String> locales = messageManagementDAO.getLocales();
    	
    	for(String cur: locales){
    		System.out.println("************** Locale IS " + cur);
    	}
    	assertEquals(2, locales.size());
    }
    
    @Test
    public void getMessageGroupTest(){
    	List<String> groups = messageManagementDAO.getMessageGroups();
    	for(String cur: groups){
    		System.out.println("************** Group IS " + cur);
    	}
    	assertEquals(1, groups.size());
    }
}
