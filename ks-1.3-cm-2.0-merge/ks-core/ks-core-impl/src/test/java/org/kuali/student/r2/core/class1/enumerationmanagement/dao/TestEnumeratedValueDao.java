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

package org.kuali.student.r2.core.class1.enumerationmanagement.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumContextValueEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumeratedValueEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationEntity;

@PersistenceFileLocation("classpath:META-INF/enumeration-persistence.xml")
public class TestEnumeratedValueDao extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumeratedValueDao", testSqlFile = "classpath:ks-em.sql")
    public EnumeratedValueDao enumeratedValueDao;

    @Dao(value = "org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumerationDao")
    public EnumerationDao enumerationDao;
    
    @Test
    public void testAddEnumeratedValue() {
        
        EnumerationEntity keyA = enumerationDao.find("kuali.lu.subjectArea");
                
        EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumeration(keyA);
        entity.setAbbrevValue("AbbrevA");
        entity.setCode("CodeA");
        entity.setEffectiveDate(new Date(System.currentTimeMillis()-10000000L));
        entity.setExpirationDate(new Date(System.currentTimeMillis()+10000000L));
        entity.setSortKey("1");
        entity.setValue("ValueA");

        EnumContextValueEntity contextEntity = new EnumContextValueEntity();
        contextEntity.setContextKey("type A");
        contextEntity.setContextValue("context value A");
        
        entity.getContextValueEntities().add(contextEntity);
        List<EnumeratedValueEntity> entityList = new ArrayList<EnumeratedValueEntity>();
        entityList.add(entity);
        contextEntity.setEnumeratedValueList(entityList);
        
        enumeratedValueDao.persist(entity);

        List<EnumeratedValueEntity> enumeratedValueList =  enumeratedValueDao.getByContextAndDate(entity.getEnumeration().getId(), 
                contextEntity.getContextKey(), contextEntity.getContextValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueList.size(), 1);
        
        EnumeratedValueEntity returnedEntity = enumeratedValueList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity.getSortKey());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        assertEquals(returnedEntity.getEnumeration().getId(), entity.getEnumeration().getId());
        int i =0;
        for(EnumContextValueEntity ce: returnedEntity.getContextValueEntities()){
            EnumContextValueEntity original = entity.getContextValueEntities().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
    }

    @Test
    public void testFetchEnumeratedValues(){
    	long baseTime = System.currentTimeMillis();

        EnumerationEntity key1 = enumerationDao.find("kuali.lu.fee.feeType");
    	
        EnumeratedValueEntity entity1 = new EnumeratedValueEntity();
        entity1.setEnumeration(key1);
        entity1.setAbbrevValue("Abbrev1");  
        entity1.setCode("Code1");
        entity1.setEffectiveDate(new Date(baseTime-10000000L));
        entity1.setExpirationDate(new Date(baseTime+10000000L));
        entity1.setSortKey("1");
        entity1.setValue("Value1");
        
        EnumeratedValueEntity entity2 = new EnumeratedValueEntity();
        entity2.setEnumeration(key1);
        entity2.setAbbrevValue("Abbrev2");
        entity2.setCode("Code2");
        entity2.setEffectiveDate(new Date(baseTime-10000000L));
        entity2.setExpirationDate(new Date(baseTime+50000000L));
        entity2.setSortKey("1");
        entity2.setValue("Value2");
        
        EnumeratedValueEntity entity3 = new EnumeratedValueEntity();
        entity3.setEnumeration(key1);
        entity3.setAbbrevValue("Abbrev3");
        entity3.setCode("Code3");
        entity3.setEffectiveDate(new Date(baseTime-10000000L));
        entity3.setExpirationDate(new Date(baseTime+10000000L));
        entity3.setSortKey("1");
        entity3.setValue("Value3");
        
        EnumeratedValueEntity entity4 = new EnumeratedValueEntity();
        entity4.setEnumeration(key1);
        entity4.setAbbrevValue("Abbrev4");
        entity4.setCode("Code4");
        entity4.setEffectiveDate(new Date(baseTime-10000000L));
        entity4.setExpirationDate(new Date(baseTime+50000000L));
        entity4.setSortKey("1");
        entity4.setValue("Value4");

        EnumContextValueEntity contextEntity1 = new EnumContextValueEntity();
        contextEntity1.setContextKey("country");
        contextEntity1.setContextValue("US");
        
        EnumContextValueEntity contextEntity2 = new EnumContextValueEntity();
        contextEntity2.setContextKey("country");
        contextEntity2.setContextValue("US");
        
        EnumContextValueEntity contextEntity3 = new EnumContextValueEntity();
        contextEntity3.setContextKey("country");
        contextEntity3.setContextValue("CA");
        
        EnumContextValueEntity contextEntity4 = new EnumContextValueEntity();
        contextEntity4.setContextKey("country");
        contextEntity4.setContextValue("CA");
        
        entity1.getContextValueEntities().add(contextEntity1);
        List<EnumeratedValueEntity> entityList1 = new ArrayList<EnumeratedValueEntity>();
        entityList1.add(entity1);
        contextEntity1.setEnumeratedValueList(entityList1);
                
        entity2.getContextValueEntities().add(contextEntity2);
        List<EnumeratedValueEntity> entityList2 = new ArrayList<EnumeratedValueEntity>();
        entityList2.add(entity2);
        contextEntity2.setEnumeratedValueList(entityList2);
        
        entity3.getContextValueEntities().add(contextEntity3);
        List<EnumeratedValueEntity> entityList3 = new ArrayList<EnumeratedValueEntity>();
        entityList3.add(entity3);
        contextEntity3.setEnumeratedValueList(entityList3);
     
        entity4.getContextValueEntities().add(contextEntity4);
        List<EnumeratedValueEntity> entityList4 = new ArrayList<EnumeratedValueEntity>();
        entityList4.add(entity4);
        contextEntity4.setEnumeratedValueList(entityList4);
        
        enumeratedValueDao.persist(entity1);
        enumeratedValueDao.persist(entity2);
        enumeratedValueDao.persist(entity3);
        enumeratedValueDao.persist(entity4);
        
        List<EnumeratedValueEntity> enumeratedValueList = enumeratedValueDao.getByEnumerationKey("kuali.lu.fee.feeType");
        assertEquals(12, enumeratedValueList.size());
        
        enumeratedValueList = enumeratedValueDao.getByContextAndDate("kuali.lu.fee.feeType", "country", "CA", new Date(baseTime+40000000L));
        assertEquals(1, enumeratedValueList.size());

        //testing accuracy
        EnumeratedValueEntity returnedEntity = enumeratedValueList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity4.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity4.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity4.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity4.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity4.getSortKey());
        assertEquals(returnedEntity.getValue(), entity4.getValue());
        assertEquals(returnedEntity.getEnumeration().getId(), entity4.getEnumeration().getId());
        int i =0;
        for(EnumContextValueEntity ce: returnedEntity.getContextValueEntities()){
            EnumContextValueEntity original = entity4.getContextValueEntities().get(i);
            assertEquals(ce.getContextKey(), original.getContextKey());
            assertEquals(ce.getId(), original.getId());
            assertEquals(ce.getContextValue(), original.getContextValue());
            i++;
        }
                
        enumeratedValueList = enumeratedValueDao.getByContextAndDate("kuali.lu.fee.feeType", "country", "US", new Date(baseTime+40000000L));
        assertEquals(1, enumeratedValueList.size());
        
        returnedEntity = enumeratedValueList.get(0); 
        
        assertEquals(returnedEntity.getAbbrevValue(), entity2.getAbbrevValue());
        assertEquals(returnedEntity.getCode(), entity2.getCode());
        assertEquals(returnedEntity.getEffectiveDate(), entity2.getEffectiveDate());
        assertEquals(returnedEntity.getExpirationDate(), entity2.getExpirationDate());
        assertEquals(returnedEntity.getSortKey(), entity2.getSortKey());
        assertEquals(returnedEntity.getValue(), entity2.getValue());
        assertEquals(returnedEntity.getEnumeration().getId(), entity2.getEnumeration().getId());
        i =0;
        for(EnumContextValueEntity ce: returnedEntity.getContextValueEntities()){
            EnumContextValueEntity original = entity2.getContextValueEntities().get(i);
        	assertEquals(ce.getContextKey(), original.getContextKey());
        	assertEquals(ce.getId(), original.getId());
        	assertEquals(ce.getContextValue(), original.getContextValue());
        	i++;
        }
        
        enumeratedValueList = enumeratedValueDao.getByDate("kuali.lu.fee.feeType", new Date(baseTime));
        assertEquals(enumeratedValueList.size(), 4);
        
        enumeratedValueList = enumeratedValueDao.getByDate("kuali.lu.fee.feeType", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueList.size(), 2);
        
         enumeratedValueList = enumeratedValueDao.getByContextAndDate("kuali.lu.fee.feeType", "country", "US", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueList.size(), 1);
        
        enumeratedValueList = enumeratedValueDao.getByContextAndDate("kuali.lu.fee.feeType", "country", "CA", new Date(baseTime+40000000L));
        assertEquals(enumeratedValueList.size(), 1);
        
        enumeratedValueList = enumeratedValueDao.getByContextAndDate("kuali.lu.fee.feeType", "country", "CA", new Date(baseTime));
        assertEquals(enumeratedValueList.size(), 2);

    }
    
    @Test
    public void testUpdateEnumeratedValue() {
        
        EnumerationEntity existing = enumerationDao.find("kuali.lu.subjectArea");
        EnumerationEntity keyA = new EnumerationEntity();
        keyA.setId("KeyA");
        keyA.setDescrPlain("KeyA plain description");
        keyA.setEnumerationType(existing.getEnumerationType());
        keyA.setEnumerationState(existing.getEnumerationState());
        keyA.setName("KeyA");
        
    	EnumeratedValueEntity entity = new EnumeratedValueEntity();
        entity.setEnumeration(keyA);
        entity.setAbbrevValue("AbbrevA");
        entity.setCode("CodeA");
        entity.setEffectiveDate(new Date());
        entity.setExpirationDate(new Date());
        entity.setSortKey("1");
        entity.setValue("ValueA");
        
        EnumContextValueEntity contextEntity = new EnumContextValueEntity();
        contextEntity.setContextKey("type testA");
        contextEntity.setContextValue("context value testA");
        
        entity.getContextValueEntities().add(contextEntity);
        List<EnumeratedValueEntity> entityList = new ArrayList<EnumeratedValueEntity>();
        entityList.add(entity);
        contextEntity.setEnumeratedValueList(entityList);

        enumeratedValueDao.persist(entity);
        
        entity.setAbbrevValue("newAbbrev");
        entity.setValue("newValue");
        
        EnumeratedValueEntity returnedEntity = enumeratedValueDao.merge(entity);
        assertEquals(returnedEntity.getAbbrevValue(), entity.getAbbrevValue());
        assertEquals(returnedEntity.getValue(), entity.getValue());
        
    }
    
    
    @Test
    public void testRemoveEnumeratedValue(){
        
        EnumerationEntity existing = enumerationDao.find("kuali.lu.subjectArea");
        EnumerationEntity keyB = new EnumerationEntity();
        keyB.setId("KeyB");
        keyB.setName("KeyB");
        keyB.setDescrPlain("KeyB plain description");
        keyB.setEnumerationType(existing.getEnumerationType());
        keyB.setEnumerationState(existing.getEnumerationState());
        
        EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name3");
        entity.setId("Key3");
        entity.setDescrPlain("entity plain description");
        entity.setEnumerationType(existing.getEnumerationType());
        entity.setEnumerationState(existing.getEnumerationState());
        
    	EnumeratedValueEntity enumValue = new EnumeratedValueEntity();
        enumValue.setEnumeration(keyB);
        enumValue.setAbbrevValue("AbbrevB");
        enumValue.setCode("CodeB");
        enumValue.setEffectiveDate(new Date());
        enumValue.setExpirationDate(new Date());
        enumValue.setSortKey("1");
        enumValue.setValue("ValueB");
        enumValue.setEnumeration(entity);
        
        EnumContextValueEntity contextEntity = new EnumContextValueEntity();
        contextEntity.setContextKey("type testB");
        contextEntity.setContextValue("context value testB");
        
        enumValue.getContextValueEntities().add(contextEntity);
        List<EnumeratedValueEntity> entityList = new ArrayList<EnumeratedValueEntity>();
        entityList.add(enumValue);
        contextEntity.setEnumeratedValueList(entityList);
                
        enumeratedValueDao.persist(enumValue);
        EnumeratedValueEntity enumeratedValue = enumeratedValueDao.getByEnumerationKeyAndCode("Key3", "CodeB");
        assertNotNull(enumeratedValue);
        enumeratedValueDao.remove(enumeratedValue);
        try{
            enumeratedValueDao.getByEnumerationKeyAndCode("Key3", "CodeB");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
        
        List<EnumeratedValueEntity> enumeratedValueList =  enumeratedValueDao.getByContextAndDate(enumValue.getEnumeration().getId(), 
                contextEntity.getContextKey(), contextEntity.getContextValue(), new Date(System.currentTimeMillis()));
        assertEquals(enumeratedValueList.size(), 0);
    }
}
