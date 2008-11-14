package org.kuali.student.enumeration.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.junit.Test;
import org.kuali.student.enumeration.entity.ContextEntity;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

@PersistenceFileLocation("classpath:META-INF/enumerationmanagement-persistence.xml")
public class EnumerationManagementDAOImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.enumeration.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:test-beans.xml")
    public EnumerationManagementDAOImpl enumerationManagementDAO;

    @Test
    public void testFindEnumerationMetas(){
        List<EnumerationMetaEntity> list = enumerationManagementDAO.findEnumerationMetas();
        
        assertEquals(list.size(),1);
        
        EnumerationMetaEntity returnedEntity = list.get(0);
        
        assertEquals(returnedEntity.getName(), "name 1");
        assertEquals(returnedEntity.getEnumerationKey(), "key 1");
        assertEquals(returnedEntity.getEnumerationMetaKeyDesc(), "desc 1");
       
    }    

    @Test
    public void testFetchEnumerationMeta(){
    	EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name3");
        entity.setEnumerationKey("Key3");
        entity.setEnumerationMetaKeyDesc("desc3");
        entity.prePersist();
        
        enumerationManagementDAO.addEnumerationMeta(entity);
        
        EnumerationMetaEntity returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key3");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
        assertEquals(returnedEntity.getEnumerationMetaKeyDesc(), entity.getEnumerationMetaKeyDesc());
        assertEquals(returnedEntity.getId(), entity.getId());
    }

    @Test
    public void testRemoveEnumerationMeta(){
    	EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name4");
        entity.setEnumerationKey("Key4");
        entity.setEnumerationMetaKeyDesc("desc4");
        entity.prePersist();
        
        enumerationManagementDAO.addEnumerationMeta(entity);
        
        EnumerationMetaEntity returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key4");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
        assertEquals(returnedEntity.getEnumerationMetaKeyDesc(), entity.getEnumerationMetaKeyDesc());
        assertEquals(returnedEntity.getId(), entity.getId());
        
        enumerationManagementDAO.removeEnumerationMeta(entity);
        List<EnumerationMetaEntity> list = enumerationManagementDAO.findEnumerationMetas();
        for(EnumerationMetaEntity e: list){
        	assertTrue("EnumerationMetaEntity still exists after remove", !e.getEnumerationKey().equals("Key4"));
        }
        

        returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key4");
        assertTrue("EnumerationMetaEntity still exists after remove", returnedEntity == null);

        //try to fetch it directly too, testing for null return
        returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Does not Exist");
        assertNull(returnedEntity);
    }

    @Test
    public void testFetchEnumeration(){
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("Key");
        entity.setAbbrevValue("Abbrev");
        entity.setCode("Code");
        entity.setEffectiveDate(new Date(System.currentTimeMillis()-10000000L));
        entity.setExpirationDate(new Date(System.currentTimeMillis()+10000000L));
        entity.setSortKey(1);
        entity.setValue("Value");

        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("type 1");
        contextEntity.setValue("context value 1");
        
        entity.getContextEntityList().add(contextEntity);
        contextEntity.getEnumeratedValueEntityList().add(entity);
        
        enumerationManagementDAO.addEnumeratedValue("Key", entity);

        List<EnumeratedValueEntity> enumeratedValueEntityList =  enumerationManagementDAO.fetchEnumeration(entity.getEnumerationKey(), 
                contextEntity.getContextKey(), contextEntity.getValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueEntityList.size(), 1);
        
        EnumeratedValueEntity returnedEntity = enumeratedValueEntityList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        
        // fetch enumeration needs to behave differently when
        //null values are passed into the method, as in dictionary
        //List<EnumeratedValueEntity> evList = enumerationManagementDAO.fetchEnumeration(key, code)
    }
  
    @Test
    public void testAddEnumeratedValue()
    {
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("Key1");
        entity.setAbbrevValue("Abbrev1");
        entity.setCode("Code1");
        entity.setEffectiveDate(new Date(System.currentTimeMillis()-10000000L));
        entity.setExpirationDate(new Date(System.currentTimeMillis()+10000000L));
        entity.setSortKey(1);
        entity.setValue("enum value 1");

        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("type 1");
        contextEntity.setValue("context value 1");
        
        entity.getContextEntityList().add(contextEntity);
        contextEntity.getEnumeratedValueEntityList().add(entity);
        
        //also addEnumeratedValue should return the value from the db not what was passed in
        enumerationManagementDAO.addEnumeratedValue("Key1", entity);
        
        List<EnumeratedValueEntity> enumeratedValueEntityList =  enumerationManagementDAO.fetchEnumeration(entity.getEnumerationKey(), 
                contextEntity.getContextKey(), contextEntity.getValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueEntityList.size(), 1);
        
        EnumeratedValueEntity returnedEntity = enumeratedValueEntityList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        
        //no way to check without the fetch working
        //EnumeratedValueEntity returnedEntity = 
    }
    
    /*
    @Test
    public void testUpdateEnumeratedValue()
    {
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("Key2");
        entity.setAbbrevValue("Abbrev2");
        entity.setCode("Code2");
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey(1);
        entity.setValue("Value2");
        //need to do this?
        entity.prePersist();
        //why is there context in enumerated value or a way to add a context through the DAO methods?
        enumerationManagementDAO.addEnumeratedValue("Key2", entity);
        
        entity.setAbbrevValue("newAbbrev");
        entity.setValue("newValue");
        
        EnumeratedValueEntity returnedEntity = enumerationManagementDAO.updateEnumeratedValue("Key2", "Code2", entity);
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        
    }
    */
    
    @Test
    public void testRemoveEnumeratedValue(){
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("Key3");
        entity.setAbbrevValue("Abbrev3");
        entity.setCode("Code3");
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey(1);
        entity.setValue("Value3");
        //need to do this?
        entity.prePersist();
        //why is there context in enumerated value or a way to add a context through the DAO methods?
        enumerationManagementDAO.addEnumeratedValue("Key3", entity);
        enumerationManagementDAO.removeEnumeratedValue("Key3", "Code3");
        //no way to check if it is still there after without fetch working
    }
}