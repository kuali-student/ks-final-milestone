package org.kuali.student.enumeration.dao.impl;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enumeration.entity.EnumerationMetaEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//TODO remove the comment
//@PersistenceFileLocation("classpath:META-INF/enumeration-persistence.xml")
public class EnumerationManagementDAOImplTest extends TestCase{
//    @Dao(value = "org.kuali.student.enumeration.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:test-beans.xml")
    public EnumerationManagementDAOImpl enumerationManagementDAO;

    //TODO delete all records from tables 
    public void setup(){
        
    }
    @Test
    public void testFindEnumerationMetas(){
        EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name");
        entity.setEnumerationKey("Key");
        entity.setDesc("desc");
        
        enumerationManagementDAO.addEnumerationMeta(entity);

        List<EnumerationMetaEntity> list = enumerationManagementDAO.findEnumerationMetas();
        EnumerationMetaEntity returnedEntity = list.get(0);
        
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
        assertEquals(returnedEntity.getDesc(), entity.getDesc());
        
        
    }    
    
    
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