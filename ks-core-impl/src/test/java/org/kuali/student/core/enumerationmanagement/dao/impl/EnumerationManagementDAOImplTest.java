/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.enumerationmanagement.dao.impl.EnumerationManagementDAOImpl;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;

@PersistenceFileLocation("classpath:META-INF/enumeration-persistence.xml")
public class EnumerationManagementDAOImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.enumerationmanagement.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:enumeration-test-beans.xml")
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
        
        returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Does not Exist");
        assertNull(returnedEntity);
    }

    @Test
    public void testRemoveEnumerationMeta(){
    	EnumerationMetaEntity entity = new EnumerationMetaEntity();
        entity.setName("Name4");
        entity.setEnumerationKey("Key4");
        entity.setEnumerationMetaKeyDesc("desc4");
        entity.prePersist();
        
        enumerationManagementDAO.addEnumerationMeta(entity);
        
        enumerationManagementDAO.removeEnumerationMeta("Key4");
        List<EnumerationMetaEntity> list = enumerationManagementDAO.findEnumerationMetas();
        for(EnumerationMetaEntity e: list){
        	assertTrue("EnumerationMetaEntity still exists after remove", !e.getEnumerationKey().equals("Key4"));
        }
        

        EnumerationMetaEntity returnedEntity = enumerationManagementDAO.fetchEnumerationMeta("Key4");
        assertTrue("EnumerationMetaEntity still exists after remove", returnedEntity == null);
    }
    
    @Test
    public void testAddEnumeratedValue()
    {
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("KeyA");
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
        contextEntity.setEnumeratedValueEntity(entity);
        
        enumerationManagementDAO.addEnumeratedValue("KeyA", entity);

        List<EnumeratedValueEntity> enumeratedValueEntityList =  enumerationManagementDAO.fetchEnumerationWithContextAndDate(entity.getEnumerationKey(), 
                contextEntity.getContextKey(), contextEntity.getContextValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueEntityList.size(), 1);
        
        EnumeratedValueEntity returnedEntity = enumeratedValueEntityList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity.getSortKey());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        assertEquals(returnedEntity.getEnumerationKey(), entity.getEnumerationKey());
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
    public void testFetchEnumeration(){
    	long baseTime = System.currentTimeMillis();
    	
    	EnumeratedValueEntity entity1 = new EnumeratedValueEntity();
        entity1.setEnumerationKey("Key1");
        entity1.setAbbrevValue("Abbrev1");
        entity1.setCode("Code1");
        entity1.setEffectiveDate(new Date(baseTime-10000000L));
        entity1.setExpirationDate(new Date(baseTime+10000000L));
        entity1.setSortKey(1);
        entity1.setValue("Value1");
        
    	EnumeratedValueEntity entity2 = new EnumeratedValueEntity();
        entity2.setEnumerationKey("Key1");
        entity2.setAbbrevValue("Abbrev2");
        entity2.setCode("Code2");
        entity2.setEffectiveDate(new Date(baseTime-10000000L));
        //entity2.setExpirationDate(new Date(baseTime+50000000L));
        entity2.setSortKey(1);
        entity2.setValue("Value2");
        
    	EnumeratedValueEntity entity3 = new EnumeratedValueEntity();
        entity3.setEnumerationKey("Key1");
        entity3.setAbbrevValue("Abbrev3");
        entity3.setCode("Code3");
        entity3.setEffectiveDate(new Date(baseTime-10000000L));
        entity3.setExpirationDate(new Date(baseTime+10000000L));
        entity3.setSortKey(1);
        entity3.setValue("Value3");
        
    	EnumeratedValueEntity entity4 = new EnumeratedValueEntity();
        entity4.setEnumerationKey("Key1");
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
        contextEntity1.setEnumeratedValueEntity(entity1);
        
        //bad
        //entity1.getContextEntityList().add(contextEntity3);
        //contextEntity3.setEnumeratedValueEntity(entity1);
        
        entity2.getContextEntityList().add(contextEntity3);
        contextEntity3.setEnumeratedValueEntity(entity2);
        
        entity3.getContextEntityList().add(contextEntity2);
        contextEntity2.setEnumeratedValueEntity(entity3);
     
        entity4.getContextEntityList().add(contextEntity4);
        contextEntity4.setEnumeratedValueEntity(entity4);
        
        enumerationManagementDAO.addEnumeratedValue("Key1", entity1);
        enumerationManagementDAO.addEnumeratedValue("Key1", entity2);
        enumerationManagementDAO.addEnumeratedValue("Key1", entity3);
        enumerationManagementDAO.addEnumeratedValue("Key1", entity4);

        
        List<EnumeratedValueEntity> enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumeration("Key1");
        assertEquals(enumeratedValueEntityList.size(), 4);
        
        enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithContext("Key1" , "country", "CA");
        assertEquals(enumeratedValueEntityList.size(), 2);
        

        
        enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithContext("Key1" , "country", "US");
        assertEquals(enumeratedValueEntityList.size(), 2);
        
        //testing accuracy
        EnumeratedValueEntity returnedEntity = enumeratedValueEntityList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity1.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity1.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity1.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity1.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity1.getSortKey());
        assertEquals(returnedEntity.getValue(), entity1.getValue());
        assertEquals(returnedEntity.getEnumerationKey(), entity1.getEnumerationKey());
        int i =0;
        for(ContextEntity ce: returnedEntity.getContextEntityList()){
        	ContextEntity original = entity1.getContextEntityList().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
        
        returnedEntity = enumeratedValueEntityList.get(1); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity2.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity2.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity2.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity2.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity2.getSortKey());
        assertEquals(returnedEntity.getValue(), entity2.getValue());
        assertEquals(returnedEntity.getEnumerationKey(), entity2.getEnumerationKey());
        i =0;
        for(ContextEntity ce: returnedEntity.getContextEntityList()){
        	ContextEntity original = entity2.getContextEntityList().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
        
        enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithDate("Key1", new Date(baseTime));
        assertEquals(enumeratedValueEntityList.size(), 4);
        
        enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithDate("Key1", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueEntityList.size(), 2);
        
         enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithContextAndDate("Key1" , "country", "US", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueEntityList.size(), 1);
        
        enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithContextAndDate("Key1" , "country", "CA", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueEntityList.size(), 1);
        
        enumeratedValueEntityList =  
        	enumerationManagementDAO.fetchEnumerationWithContextAndDate("Key1" , "country", "CA", new Date(baseTime));
        assertEquals(enumeratedValueEntityList.size(), 2);
        

    }
  
    
    
    
    @Test
    public void testUpdateEnumeratedValue()
    {
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("KeyA");
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
        
        contextEntity.setEnumeratedValueEntity(entity);

        enumerationManagementDAO.addEnumeratedValue("KeyA", entity);
        
        entity.setAbbrevValue("newAbbrev");
        entity.setValue("newValue");
        
        EnumeratedValueEntity returnedEntity = enumerationManagementDAO.updateEnumeratedValue("KeyA", "CodeA", entity);
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        
    }
    
    
    @Test
    public void testRemoveEnumeratedValue(){
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumerationKey("KeyB");
        entity.setAbbrevValue("AbbrevB");
        entity.setCode("CodeB");
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey(1);
        entity.setValue("ValueB");
        
        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("type testB");
        contextEntity.setContextValue("context value testB");
        
        entity.getContextEntityList().add(contextEntity);
        contextEntity.setEnumeratedValueEntity(entity);

        enumerationManagementDAO.addEnumeratedValue("Key3", entity);
        enumerationManagementDAO.removeEnumeratedValue("Key3", "CodeB");
        List<EnumeratedValueEntity> enumeratedValueEntityList =  enumerationManagementDAO.fetchEnumerationWithContextAndDate(entity.getEnumerationKey(), 
                contextEntity.getContextKey(), contextEntity.getContextValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueEntityList.size(), 0);
    }
}
