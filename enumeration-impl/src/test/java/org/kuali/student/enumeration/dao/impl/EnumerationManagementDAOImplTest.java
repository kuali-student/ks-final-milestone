package org.kuali.student.enumeration.dao.impl;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@PersistenceFileLocation("classpath:META-INF/enumerationmanagement-persistence.xml")

public class EnumerationManagementDAOImplTest extends TestCase{
  //  @Dao(value = "org.kuali.student.enumeration.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:test-beans.xml")
    public EnumerationManagementDAOImpl enumerationManagementDAO;

    //TODO delete all records from tables 
    public void setup(){
        
    }

    /*
    @Test
    public void testFindEnumerationMetas(){
        EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name1");
        entity.setEnumerationKey("Key1");
        entity.setDesc("desc1");
        
        enumerationManagementDAO.addEnumerationMeta(entity);
        
        EnumerationMetaEntity entity2 = new EnumerationMetaEntity();
        entity2.setName("Name2");
        entity2.setEnumerationKey("Key2");
        entity2.setDesc("desc2");
        
        enumerationManagementDAO.addEnumerationMeta(entity2);

        List<EnumerationMetaEntity> list = enumerationManagementDAO.findEnumerationMetas();
        EnumerationMetaEntity returnedEntity = list.get(0);
        
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
        assertEquals(returnedEntity.getDesc(), entity.getDesc());
        
        returnedEntity = list.get(1);
        
        assertEquals(returnedEntity.getName(), entity2.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity2.getEnumerationKey());
        assertEquals(returnedEntity.getDesc(), entity2.getDesc());
        
        
    }    

    @Test
    public void testFetchEnumerationMeta(){
    	EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name3");
        entity.setEnumerationKey("Key3");
        entity.setDesc("desc3");
        
        enumerationManagementDAO.addEnumerationMeta(entity);
        
        EnumerationMetaEntity returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key3");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
        assertEquals(returnedEntity.getDesc(), entity.getDesc());
    }
    
    @Test
    public void testRemoveEnumerationMeta(){
    	EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name4");
        entity.setEnumerationKey("Key4");
        entity.setDesc("desc4");
        
        enumerationManagementDAO.addEnumerationMeta(entity);
        
        EnumerationMetaEntity returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key4");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
        assertEquals(returnedEntity.getDesc(), entity.getDesc());
        
        enumerationManagementDAO.removeEnumerationMeta(entity);
        returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key4");
        assertTrue("EnumerationMetaEntity still exists after remove", returnedEntity == null);
    }
    
    @Test
    public void testFetchEnumeration(){
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("Key");
        entity.setAbbrevValue("Abbrev");
        entity.setCode("Code");
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey(1);
        entity.setValue("Value");
        //need to do this?
        entity.prePersist();
        
        enumerationManagementDAO.addEnumeratedValue("Key", entity);
        
        //No way to add context so no way to fetch anything, ALSO fetch enumeration needs to behave differently when
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
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey(1);
        entity.setValue("Value1");
        //need to do this?
        entity.prePersist();
        //why is there context in enumerated value or a way to add a context through the DAO methods?
        //also addEnumeratedValue should return the value from the db not what was passed in
        enumerationManagementDAO.addEnumeratedValue("Key1", entity);
        //no way to check without the fetch working
        //EnumeratedValueEntity returnedEntity = 
    }
    
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
*/    
/*
    @Test
    public void testCreateEnumerations(){
        Enumerations entry = new Enumerations();
        String myName = "my name";
        entry.setName(myName);
        entry = enumerationManagementDAO.createEnumerations(entry);
        
        entry = enumerationManagementDAO.lookupEnumerations(entry.getId());
        assertEquals(entry.getName(), myName);
    }
    
    @Test
    public void testLookupEnumerations(){
        Enumerations entry = new Enumerations();
        String myName = "my name";
        entry.setName(myName);
        entry = enumerationManagementDAO.createEnumerations(entry);
        
        entry = enumerationManagementDAO.lookupEnumerations(entry.getId());
        assertEquals(entry.getName(), myName);
        
        entry = enumerationManagementDAO.lookupEnumerations("");
        assertNull(entry);
    }
    
    @Test 
    public void testUpdateEnumerations(){
        Enumerations entry = new Enumerations();
        String myName = "my name";
        String myNewName = "new Name";
        
        String myDescription = "my desc";
        String myNewDescription = "my new desc";
        
        entry.setName(myName);
        entry.setDescription(myDescription);
        entry = enumerationManagementDAO.createEnumerations(entry);

        entry.setName(myNewName);
        entry.setDescription(myNewDescription);
        
        enumerationManagementDAO.updateEnumerations(entry);
        
        entry = enumerationManagementDAO.lookupEnumerations(entry.getId());
        assertEquals(entry.getName(), myNewName);
        assertEquals(entry.getDescription(), myNewDescription);
        
    }
    
    @Test 
    public void testDeleteEnumerations(){
        Enumerations entry = new Enumerations();
        String myName = "my name";
        
        entry.setName(myName);
        entry = enumerationManagementDAO.createEnumerations(entry);

        enumerationManagementDAO.deleteEnumerations(entry);
        
        entry = enumerationManagementDAO.lookupEnumerations(entry.getId());
        assertNull(entry);
        
    }
    
    @Test 
    public void testCreateEnumeratedValue(){
        EnumeratedValue value = new EnumeratedValue();
        String myValue = "value"; 
        value.setValue(myValue);
        
        value = enumerationManagementDAO.createEnumeratedValue(value);
        
        enumerationManagementDAO.lookupEnumeratedValue(value.getId());
        assertEquals(value.getValue(), myValue);
    }
    
    @Test 
    public void testLookupEnumeratedValue(){
        EnumeratedValue value = new EnumeratedValue();
        String myValue = "value"; 
        value.setValue(myValue);
        
        value = enumerationManagementDAO.createEnumeratedValue(value);
        
        value = enumerationManagementDAO.lookupEnumeratedValue(value.getId());
        assertEquals(value.getValue(), myValue);
        
        value = enumerationManagementDAO.lookupEnumeratedValue("");
        assertNull(value);
    }
    
    @Test 
    public void testUpdateEnumeratedValue(){
        EnumeratedValue value = new EnumeratedValue();
        String myValue = "value";
        String myNewValue = "new value";
        
        value.setValue(myValue);
        
        value = enumerationManagementDAO.createEnumeratedValue(value);
        
        value.setValue(myNewValue);
        
        value = enumerationManagementDAO.updateEnumeratedValue(value);
        
        assertEquals(value.getValue(), myNewValue);
    }
    
    @Test 
    public void testDeleteEnumeratedValue(){
        EnumeratedValue value = new EnumeratedValue();
        String myValue = "value"; 
        value.setValue(myValue);
        
        value = enumerationManagementDAO.createEnumeratedValue(value);
        
        enumerationManagementDAO.deleteEnumeratedValue(value);
        
        value = enumerationManagementDAO.lookupEnumeratedValue(value.getId());
        
        assertNull(value);
    }
    
    @Test
    public void testCreateEnumeratedValueContext(){
        EnumeratedValueContext context = new EnumeratedValueContext();
        String myValue = "value";
        
        context.setContextValue(myValue);
        context = enumerationManagementDAO.createEnumeratedValueContext(context);
        
        
        context = enumerationManagementDAO.lookupEnumeratedValueContext(context.getId());
        assertEquals(context.getContextValue(),myValue);
    }
    
    @Test
    public void testLookupEnumeratedValueContext(){
        EnumeratedValueContext context = new EnumeratedValueContext();
        String myValue = "value";
        
        context.setContextValue(myValue);
        context = enumerationManagementDAO.createEnumeratedValueContext(context);
        
        
        context = enumerationManagementDAO.lookupEnumeratedValueContext(context.getId());
        assertEquals(context.getContextValue(),myValue);
        
        context = enumerationManagementDAO.lookupEnumeratedValueContext("");
        assertNull(context);
    }
    @Test
    public void testUpdateEnumeratedValueContext(){
        EnumeratedValueContext context = new EnumeratedValueContext();
        String myValue = "value";
        String myNewValue = "new value";
        
        context.setContextValue(myValue);
        context = enumerationManagementDAO.createEnumeratedValueContext(context);
        
        context.setContextValue(myNewValue);
        
        context = enumerationManagementDAO.updateEnumeratedValueContext(context);
        
        context = enumerationManagementDAO.lookupEnumeratedValueContext(context.getId());
        assertEquals(context.getContextValue(),myNewValue);
        
    }
    @Test
    public void testDeleteEnumeratedValueContext(){
        EnumeratedValueContext context = new EnumeratedValueContext();
        String myValue = "value";
        
        context.setContextValue(myValue);
        context = enumerationManagementDAO.createEnumeratedValueContext(context);
        
        enumerationManagementDAO.deleteEnumeratedValueContext(context);
        
        context = enumerationManagementDAO.lookupEnumeratedValueContext(context.getId());
        assertNull(context);
        
    }
    @Test
    public void  testGetEnumeratedValueContexts() {
        EnumeratedValueContext context = new EnumeratedValueContext();
        context.setEnumeratedValueId("id1");
        context.setContextValue("myValue");
        enumerationManagementDAO.createEnumeratedValueContext(context);
        
        context = new EnumeratedValueContext();
        context.setEnumeratedValueId("id2");
        context.setContextValue("myValue2");
        enumerationManagementDAO.createEnumeratedValueContext(context);        
        
        List<EnumeratedValueContext> list =  enumerationManagementDAO.getEnumeratedValueContexts("");
        assertEquals(list.size(),2);
    }

    @Test
    public void testGetEnumeratedValues() {
        EnumeratedValue value = new EnumeratedValue();
        value.setValue("myValue1");
        value.setEnumerationId("id1");
        enumerationManagementDAO.createEnumeratedValue(value);
        
        value = new EnumeratedValue();
        value.setValue("myValue2");
        value.setEnumerationId("id2");
        enumerationManagementDAO.createEnumeratedValue(value);
        
        List<EnumeratedValue> list = enumerationManagementDAO.getEnumeratedValues("");
        assertEquals(list.size(),2);
    }
*/    
}