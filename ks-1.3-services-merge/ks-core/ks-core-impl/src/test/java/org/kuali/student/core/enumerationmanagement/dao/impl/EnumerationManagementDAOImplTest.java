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

package org.kuali.student.core.enumerationmanagement.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.entity.Enumeration;

@PersistenceFileLocation("classpath:META-INF/enumeration-persistence.xml")
public class EnumerationManagementDAOImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.enumerationmanagement.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:enumeration-test-beans.xml")
    public EnumerationManagementDAOImpl enumerationManagementDAO;

    @Test
    public void testFindEnumerations(){
        List<Enumeration> list = enumerationManagementDAO.findEnumerations();
        
        assertEquals(list.size(),1);
        
        Enumeration returnedEntity = list.get(0);
        
        assertEquals(returnedEntity.getName(), "name 1");
        assertEquals(returnedEntity.getId(), "key 1");
        assertEquals(returnedEntity.getDescr(), "desc 1");
       
    }    

    @Test
    public void testFetchEnumeration() throws DoesNotExistException{
    	Enumeration entity = new Enumeration();
        entity.setName("Name3");
        entity.setId("Key3");

        entity.setDescr("desc3");
        
        enumerationManagementDAO.addEnumeration(entity);
        
        Enumeration returnedEntity = enumerationManagementDAO.fetch(Enumeration.class,"Key3");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getId(), entity.getId());
        assertEquals(returnedEntity.getDescr(), entity.getDescr());
        
        try {
            returnedEntity = enumerationManagementDAO.fetch(Enumeration.class, "Does not Exist");
            assertTrue(false);
        } catch(DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testRemoveEnumeration() throws DoesNotExistException{
    	Enumeration entity = new Enumeration();
        entity.setName("Name4");
        entity.setId("Key4");
                
        enumerationManagementDAO.addEnumeration(entity);
        
        enumerationManagementDAO.removeEnumeration("Key4");
        List<Enumeration> list = enumerationManagementDAO.findEnumerations();
        for(Enumeration e: list){
        	assertTrue("EnumerationMetaEntity still exists after remove", !e.getId().equals("Key4"));
        }
        
        try {
            enumerationManagementDAO.fetch(Enumeration.class, "Key4");
            assertTrue("EnumerationMetaEntity still exists after remove",  false);
        } catch (DoesNotExistException e)  {
            assertTrue(true);
        }
    }
    
    @Test
    public void testAddEnumeratedValue()
    {
        Enumeration keyA = new Enumeration();
        keyA.setId("KeyA");
        keyA.setName("KeyA");
        
        
    	EnumeratedValue entity = new EnumeratedValue();
        entity.setEnumeration(keyA);
        entity.setAbbrevValue("AbbrevA");
        entity.setCode("CodeA");
        entity.setEffectiveDate(new Date(System.currentTimeMillis()-10000000L));
        entity.setExpirationDate(new Date(System.currentTimeMillis()+10000000L));
        entity.setSortKey(1);
        entity.setValue("ValueA");

        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("type A");
        contextEntity.setContextValue("context value A");
        
        entity.getContextEntityList().add(contextEntity);
        List<EnumeratedValue> entityList = new ArrayList<EnumeratedValue>();
        entityList.add(entity);
        contextEntity.setEnumeratedValueList(entityList);
        
        enumerationManagementDAO.addEnumeratedValue("KeyA", entity);

        List<EnumeratedValue> enumeratedValueList =  enumerationManagementDAO.fetchEnumeratedValuesWithContextAndDate(entity.getEnumeration().getId(), 
                contextEntity.getContextKey(), contextEntity.getContextValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueList.size(), 1);
        
        EnumeratedValue returnedEntity = enumeratedValueList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity.getSortKey());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        assertEquals(returnedEntity.getEnumeration().getId(), entity.getEnumeration().getId());
        int i =0;
        for(ContextEntity ce: returnedEntity.getContextEntityList()){
        	ContextEntity original = entity.getContextEntityList().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
    }

    @Test
    public void testFetchEnumeratedValues(){
    	long baseTime = System.currentTimeMillis();

        Enumeration key1 = new Enumeration();
        key1.setId("Key1");
        key1.setName("Key1");
    	
    	EnumeratedValue entity1 = new EnumeratedValue();
        entity1.setEnumeration(key1);
        entity1.setAbbrevValue("Abbrev1");  
        entity1.setCode("Code1");
        entity1.setEffectiveDate(new Date(baseTime-10000000L));
        entity1.setExpirationDate(new Date(baseTime+10000000L));
        entity1.setSortKey(1);
        entity1.setValue("Value1");
        
    	EnumeratedValue entity2 = new EnumeratedValue();
        entity2.setEnumeration(key1);
        entity2.setAbbrevValue("Abbrev2");
        entity2.setCode("Code2");
        entity2.setEffectiveDate(new Date(baseTime-10000000L));
        //entity2.setExpirationDate(new Date(baseTime+50000000L));
        entity2.setSortKey(1);
        entity2.setValue("Value2");
        
    	EnumeratedValue entity3 = new EnumeratedValue();
        entity3.setEnumeration(key1);
        entity3.setAbbrevValue("Abbrev3");
        entity3.setCode("Code3");
        entity3.setEffectiveDate(new Date(baseTime-10000000L));
        entity3.setExpirationDate(new Date(baseTime+10000000L));
        entity3.setSortKey(1);
        entity3.setValue("Value3");
        
    	EnumeratedValue entity4 = new EnumeratedValue();
        entity4.setEnumeration(key1);
        entity4.setAbbrevValue("Abbrev4");
        entity4.setCode("Code4");
        entity4.setEffectiveDate(new Date(baseTime-10000000L));
        entity4.setExpirationDate(new Date(baseTime+50000000L));
        entity4.setSortKey(1);
        entity4.setValue("Value4");

        ContextEntity contextEntity1 = new ContextEntity();
        contextEntity1.setContextKey("country");
        contextEntity1.setContextValue("US");
        
        ContextEntity contextEntity3 = new ContextEntity();
        contextEntity3.setContextKey("country");
        contextEntity3.setContextValue("US");
        
        ContextEntity contextEntity2 = new ContextEntity();
        contextEntity2.setContextKey("country");
        contextEntity2.setContextValue("CA");
        
        ContextEntity contextEntity4 = new ContextEntity();
        contextEntity4.setContextKey("country");
        contextEntity4.setContextValue("CA");
        
        entity1.getContextEntityList().add(contextEntity1);
        List<EnumeratedValue> entityList1 = new ArrayList<EnumeratedValue>();
        entityList1.add(entity1);
        contextEntity1.setEnumeratedValueList(entityList1);
                
        entity2.getContextEntityList().add(contextEntity3);
        List<EnumeratedValue> entityList2 = new ArrayList<EnumeratedValue>();
        entityList2.add(entity2);
        contextEntity3.setEnumeratedValueList(entityList2);
        
        entity3.getContextEntityList().add(contextEntity2);
        List<EnumeratedValue> entityList3 = new ArrayList<EnumeratedValue>();
        entityList3.add(entity3);
        contextEntity2.setEnumeratedValueList(entityList3);
     
        entity4.getContextEntityList().add(contextEntity4);
        List<EnumeratedValue> entityList4 = new ArrayList<EnumeratedValue>();
        entityList4.add(entity4);
        contextEntity4.setEnumeratedValueList(entityList4);
        
        enumerationManagementDAO.addEnumeratedValue("Key1", entity1);
        enumerationManagementDAO.addEnumeratedValue("Key1", entity2);
        enumerationManagementDAO.addEnumeratedValue("Key1", entity3);
        enumerationManagementDAO.addEnumeratedValue("Key1", entity4);

        
        List<EnumeratedValue> enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValues("Key1");
        assertEquals(enumeratedValueList.size(), 4);
        
        enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithContext("Key1" , "country", "CA");
        assertEquals(enumeratedValueList.size(), 2);
        

        
        enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithContext("Key1" , "country", "US");
        assertEquals(enumeratedValueList.size(), 2);
        
        //testing accuracy
        EnumeratedValue returnedEntity = enumeratedValueList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity1.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity1.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity1.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity1.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity1.getSortKey());
        assertEquals(returnedEntity.getValue(), entity1.getValue());
        assertEquals(returnedEntity.getEnumeration().getId(), entity1.getEnumeration().getId());
        int i =0;
        for(ContextEntity ce: returnedEntity.getContextEntityList()){
        	ContextEntity original = entity1.getContextEntityList().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
        
        returnedEntity = enumeratedValueList.get(1); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity2.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity2.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity2.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity2.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity2.getSortKey());
        assertEquals(returnedEntity.getValue(), entity2.getValue());
        assertEquals(returnedEntity.getEnumeration().getId(), entity2.getEnumeration().getId());
        i =0;
        for(ContextEntity ce: returnedEntity.getContextEntityList()){
        	ContextEntity original = entity2.getContextEntityList().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
        
        enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithDate("Key1", new Date(baseTime));
        assertEquals(enumeratedValueList.size(), 4);
        
        enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithDate("Key1", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueList.size(), 2);
        
         enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithContextAndDate("Key1" , "country", "US", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueList.size(), 1);
        
        enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithContextAndDate("Key1" , "country", "CA", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueList.size(), 1);
        
        enumeratedValueList =  
        	enumerationManagementDAO.fetchEnumeratedValuesWithContextAndDate("Key1" , "country", "CA", new Date(baseTime));
        assertEquals(enumeratedValueList.size(), 2);
        

    }
  
    
    
    
    @Test
    public void testUpdateEnumeratedValue()
    {
        
        Enumeration keyA = new Enumeration();
        keyA.setId("KeyA");
        keyA.setName("KeyA");
        
    	EnumeratedValue entity = new EnumeratedValue();
        entity.setEnumeration(keyA);
        entity.setAbbrevValue("AbbrevA");
        entity.setCode("CodeA");
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey(1);
        entity.setValue("ValueA");
        
        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("type testA");
        contextEntity.setContextValue("context value testA");
        
        entity.getContextEntityList().add(contextEntity);
        List<EnumeratedValue> entityList = new ArrayList<EnumeratedValue>();
        entityList.add(entity);
        contextEntity.setEnumeratedValueList(entityList);

        enumerationManagementDAO.addEnumeratedValue("KeyA", entity);
        
        entity.setAbbrevValue("newAbbrev");
        entity.setValue("newValue");
        
        EnumeratedValue returnedEntity = enumerationManagementDAO.updateEnumeratedValue(keyA, "CodeA", entity);
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        
    }
    
    
    @Test
    public void testRemoveEnumeratedValue(){
        
        Enumeration keyB = new Enumeration();
        keyB.setId("KeyB");
        keyB.setName("KeyB");
        
        Enumeration entity = new Enumeration();
        entity.setName("Name3");
        entity.setId("Key3");
        
    	EnumeratedValue enumValue = new EnumeratedValue();
        enumValue.setEnumeration(keyB);
        enumValue.setAbbrevValue("AbbrevB");
        enumValue.setCode("CodeB");
        enumValue.setEffectiveDate(new Date());
        enumValue.setExpirationDate(new Date());
        enumValue.setSortKey(1);
        enumValue.setValue("ValueB");
        enumValue.setEnumeration(entity);
        
        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("type testB");
        contextEntity.setContextValue("context value testB");
        
        enumValue.getContextEntityList().add(contextEntity);
        List<EnumeratedValue> entityList = new ArrayList<EnumeratedValue>();
        entityList.add(enumValue);
        contextEntity.setEnumeratedValueList(entityList);
                
        enumerationManagementDAO.addEnumeratedValue("Key3", enumValue);
        enumerationManagementDAO.removeEnumeratedValue("Key3", "CodeB");
        List<EnumeratedValue> enumeratedValueList =  enumerationManagementDAO.fetchEnumeratedValuesWithContextAndDate(enumValue.getEnumeration().getId(), 
                contextEntity.getContextKey(), contextEntity.getContextValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueList.size(), 0);
    }
}
