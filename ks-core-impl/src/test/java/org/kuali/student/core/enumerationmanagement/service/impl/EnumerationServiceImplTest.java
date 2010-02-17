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
package org.kuali.student.core.enumerationmanagement.service.impl;
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
import org.kuali.student.core.enumerationmanagement.dao.impl.EnumerationManagementDAOImpl;
import org.kuali.student.core.enumerationmanagement.dto.EnumContextValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueFieldInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationMetaInfo;
import org.kuali.student.core.enumerationmanagement.dto.FieldDescriptorInfo;
import org.kuali.student.core.enumerationmanagement.dto.mock.DataGenerator;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueFieldEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.search.service.impl.SearchManagerImpl;
@PersistenceFileLocation("classpath:META-INF/enumeration-persistence.xml")
public class EnumerationServiceImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.enumerationmanagement.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:enumeration-test-beans.xml", testSqlFile="classpath:ks-em.sql")
    public EnumerationManagementDAOImpl enumerationManagementDAO;
	
    public EnumerationManagementServiceImpl enumService = new EnumerationManagementServiceImpl();
    
    @Test
    public void testSearch() throws OperationFailedException, MissingParameterException{
    	SearchManager searchManager = new SearchManagerImpl("classpath:em-search-config.xml");
    	enumService.setSearchManager(searchManager);
    	enumService.setEnumDAO(enumerationManagementDAO);
    	
    	List<SearchTypeInfo> searchTypes = enumService.getSearchTypes();
    	assertNotNull(searchTypes);
    	assertEquals(1,searchTypes.size());
    	String searchKey = searchTypes.get(0).getKey();
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.setSearchKey(searchKey);
    	searchRequest.setParams(new ArrayList<SearchParam>());
    	SearchParam searchParam = new SearchParam();
    	searchParam.setKey("enumeration.queryParam.enumerationType");
    	searchParam.setValue("kuali.lu.subjectArea");
    	searchRequest.getParams().add(searchParam);
    	SearchResult searchResult = enumService.search(searchRequest);
    	assertNotNull(searchResult);
    	assertEquals(32,searchResult.getRows().size());
    }
    
	@Test
	public void testSetEnumDAO(){
		long baseTime = System.currentTimeMillis();
    	
		//Enumeration
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
        entity2.setExpirationDate(new Date(baseTime+50000000L));
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
        

    	
		enumService.setEnumDAO(enumerationManagementDAO);
	}
	
	@Test
	public void testFetchAndFindEnumerationMetas() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException{
        //Enumeration Meta
    	EnumerationMetaEntity dao = new EnumerationMetaEntity();
    	dao.setEnumerationKey("metaKey1");
    	dao.setEnumerationMetaKeyDesc("metaDesc1");
    	dao.setName("Name1");
    	dao.setId("id1");
    	
    	EnumeratedValueFieldEntity fieldDao = new EnumeratedValueFieldEntity();
    	fieldDao.setInvalidChars("f");
    	fieldDao.setValidChars("ab");
    	fieldDao.setId("id");
    	fieldDao.setFieldKey("key");
    	fieldDao.setMaxLength("2");
    	fieldDao.setMinLength(0);
    	fieldDao.setMaxOccurs("2");
    	fieldDao.setMinOccurs(1);
    	fieldDao.setEnumerationMetaEntity(dao);
    	fieldDao.setDataType("string");
    	dao.getEnumeratedValueFieldList().add(fieldDao);
    	
    	EnumeratedValueFieldEntity fieldDao2 = new EnumeratedValueFieldEntity();
    	fieldDao2.setInvalidChars("456");
    	fieldDao2.setValidChars("123");
    	fieldDao2.setId("id2");
    	fieldDao2.setFieldKey("key2");
    	fieldDao2.setMaxLength("2");
    	fieldDao2.setMinLength(0);
    	fieldDao2.setMaxOccurs("2");
    	fieldDao2.setMinOccurs(1);
    	fieldDao2.setEnumerationMetaEntity(dao);
    	fieldDao2.setDataType("int");
    	dao.getEnumeratedValueFieldList().add(fieldDao2);
    	
    	enumerationManagementDAO.addEnumerationMeta(dao);
		enumService.setEnumDAO(enumerationManagementDAO);
		
		//fetchEnumerationMeta
		EnumerationMetaInfo dto = enumService.getEnumerationMeta("metaKey1");
    	assertEquals(dao.getEnumerationKey(), dto.getId());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getEnumerationMetaKeyDesc(), dto.getDesc());
    	List<EnumeratedValueFieldInfo> fields = dto.getEnumeratedValueFields();
    	assertEquals(fields.size(), 2);
    	int i = 0;
    	for(EnumeratedValueFieldInfo field: fields){
    		fieldDao = dao.getEnumeratedValueFieldList().get(i);
    		assertEquals(fieldDao.getFieldKey(), field.getId());
    		assertEquals(fieldDao.getMaxLength(), field.getFieldDescriptor().getMaxLength());
    		assertEquals(fieldDao.getMinLength(), field.getFieldDescriptor().getMinLength());
    		assertEquals(fieldDao.getValidChars(), field.getFieldDescriptor().getValidChars());
    		assertEquals(fieldDao.getInvalidChars(), field.getFieldDescriptor().getInvalidChars());
    		assertEquals(fieldDao.getMaxOccurs(), field.getMaxOccurs());
    		assertEquals(fieldDao.getMinOccurs(), field.getMinOccurs());
    		assertEquals(fieldDao.getDataType(), field.getFieldDescriptor().getDataType());
    		i++;
    	}
    	
    	//findEnumerationMetas
    	List<EnumerationMetaInfo> list = enumService.getEnumerationMetas();
    	
    	//there is one in test-beans... so find 2
    	assertEquals(list.size(), 2);
    	dto = list.get(0);
    	if(!dto.getId().equals("metaKey1")){
    		dto = list.get(1);
    	}
    	assertEquals(dao.getEnumerationKey(), dto.getId());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getEnumerationMetaKeyDesc(), dto.getDesc());
    	fields = dto.getEnumeratedValueFields();
    	assertEquals(fields.size(), 2);
    	i = 0;
    	for(EnumeratedValueFieldInfo field: fields){
    		fieldDao = dao.getEnumeratedValueFieldList().get(i);
    		assertEquals(fieldDao.getFieldKey(), field.getId());
    		assertEquals(fieldDao.getMaxLength(), field.getFieldDescriptor().getMaxLength());
    		assertEquals(fieldDao.getMinLength(), field.getFieldDescriptor().getMinLength());
    		assertEquals(fieldDao.getValidChars(), field.getFieldDescriptor().getValidChars());
    		assertEquals(fieldDao.getInvalidChars(), field.getFieldDescriptor().getInvalidChars());
    		assertEquals(fieldDao.getMaxOccurs(), field.getMaxOccurs());
    		assertEquals(fieldDao.getMinOccurs(), field.getMinOccurs());
    		assertEquals(fieldDao.getDataType(), field.getFieldDescriptor().getDataType());
    		i++;
    	}
	}
	
	@Test
	public void testFetchEnumeration() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
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
        entity2.setExpirationDate(new Date(baseTime+50000000L));
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
        enumService.setEnumDAO(enumerationManagementDAO);
        
        List<EnumeratedValueInfo> list = enumService.getEnumeration("Key1", "country", "US", new Date(baseTime));
         
        assertEquals(list.size(), 2);
        
        	
        list = enumService.getEnumeration("Key1", null, null, null);
        assertEquals(list.size(), 4);
        
        list = enumService.getEnumeration("Key1" , "country", "CA", null);
        assertEquals(list.size(), 2);
        	
        list = enumService.getEnumeration("Key1" , "country", "US", null);
        assertEquals(list.size(), 2);
        
        //testing accuracy
        EnumeratedValueInfo listItem = list.get(0); 
        
        assertEquals(listItem.getAbbrevValue(), entity1.getAbbrevValue());
        assertEquals(listItem.getCode(), entity1.getCode());
        assertEquals(listItem.getEffectiveDate(), entity1.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), entity1.getExpirationDate());
        assertEquals(Integer.parseInt(listItem.getSortKey()), entity1.getSortKey());
        assertEquals(listItem.getValue(), entity1.getValue());
        int i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
        	ContextEntity original = entity2.getContextEntityList().get(i);
        	assertEquals(c.getType(), original.getContextKey());
        	assertEquals(c.getValue(), original.getContextValue());
        	i++;
        }
        
        listItem = list.get(1); 
        
        assertEquals(listItem.getAbbrevValue(), entity2.getAbbrevValue());
        assertEquals(listItem.getCode(), entity2.getCode());
        assertEquals(listItem.getEffectiveDate(), entity2.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), entity2.getExpirationDate());
        assertEquals(Integer.parseInt(listItem.getSortKey()), entity2.getSortKey());
        assertEquals(listItem.getValue(), entity2.getValue());
        i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
        	ContextEntity original = entity2.getContextEntityList().get(i);
        	assertEquals(c.getType(), original.getContextKey());
        	assertEquals(c.getValue(), original.getContextValue());
        	i++;
        }

        list = enumService.getEnumeration("Key1", null, null, new Date(baseTime));
        assertEquals(list.size(), 4);

        list = enumService.getEnumeration("Key1", null, null, new Date(baseTime+40000000L));
        assertEquals(list.size(), 2);
        
        list = enumService.getEnumeration("Key1" , "country", "US", new Date(baseTime+40000000L));
        assertEquals(list.size(), 1);
        
        list = enumService.getEnumeration("Key1" , "country", "CA", new Date(baseTime+40000000L));
        assertEquals(list.size(), 1);
        
        list = enumService.getEnumeration("Key1" , "country", "CA", new Date(baseTime));
        assertEquals(list.size(), 2);
        
        list = enumService.getEnumeration(null, null, null, null);
        assertTrue(list.isEmpty());
        
	}
	
	@Test
	public void testAddEnumeratedValue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException{
		enumService.setEnumDAO(enumerationManagementDAO);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
		enumService.addEnumeratedValue("Key2", dto);

		List<EnumeratedValueInfo> list = enumService.getEnumeration("Key2", "ContextA", "1", new Date(baseTime));
		assertEquals(list.size(), 1);
		EnumeratedValueInfo listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        int i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
        	EnumContextValueInfo original = newContext;
        	assertEquals(c.getType(), original.getType());
        	assertEquals(c.getValue(), original.getValue());
        	i++;
        }
	}
	
	@Test
	public void testUpdateEnumeratedValue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException{
		enumService.setEnumDAO(enumerationManagementDAO);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");

        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        //add first
		enumService.addEnumeratedValue("Key3", dto);

		List<EnumeratedValueInfo> list = enumService.getEnumeration("Key3", "ContextA", "1", new Date(baseTime));
		assertEquals(list.size(), 1);
		EnumeratedValueInfo listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        int i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
        	EnumContextValueInfo original = newContext;
        	assertEquals(c.getType(), original.getType());
        	assertEquals(c.getValue(), original.getValue());
        	i++;
        }
        
        //update
        //update currently fails on context updates
        dto.setCode("newCode");
        dto.setValue("newValue");
        dto.getContexts().get(0).setType("newType");
        dto.getContexts().get(0).setValue("newContextValue");
        enumService.updateEnumeratedValue("Key3", "c", dto);

		list = enumService.getEnumeration("Key3", "newType", "newContextValue", new Date(baseTime));
		assertEquals(list.size(), 1);
		listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        i =0;
        for(EnumContextValueInfo c: listItem.getContexts()){
        	assertEquals(c.getType(), "newType");
        	assertEquals(c.getValue(), "newContextValue");
        	i++;
        }
        
	}
	
	@Test
	public void testRemoveEnumeratedValue() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException{
		enumService.setEnumDAO(enumerationManagementDAO);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        //add first
		enumService.addEnumeratedValue("Key4", dto);
		enumService.removeEnumeratedValue("Key4", "c");

		List<EnumeratedValueInfo> list = enumService.getEnumeration("Key4", "ContextA", "1", new Date(baseTime));
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testAutoGenerate() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException{
		//long baseTime = System.currentTimeMillis();
		enumService.setEnumDAO(enumerationManagementDAO);

		DataGenerator.generate(enumService);
		
		List<EnumeratedValueInfo> list = enumService.getEnumeration("SemesterEnum", null, null, null);
		assertEquals(list.size(), 10);
		for(EnumeratedValueInfo e: list){
			assertTrue(e.getValue().contains("Semester"));
		}
		
		list = enumService.getEnumeration("CityEnum", null, null, null);
		assertEquals(list.size(), 10);
		for(EnumeratedValueInfo e: list){
			assertTrue(e.getValue().contains("City"));
		}
	}
	
	@Test
	public void testValidate(){
		enumService.setEnumDAO(enumerationManagementDAO);
		
		//long baseTime = System.currentTimeMillis();
		
		EnumerationMetaInfo em = new EnumerationMetaInfo();
		em.setId("KeyV");
		em.setName("Validation Meta");
		em.setDesc("Meta used for validation");

		List<EnumeratedValueFieldInfo> evFieldList = new ArrayList<EnumeratedValueFieldInfo>();
		
		EnumeratedValueFieldInfo evf1 = new EnumeratedValueFieldInfo();
		evf1.setId("code");
		FieldDescriptorInfo fd1 = new FieldDescriptorInfo();
		fd1.setDataType("string");
		fd1.setMaxLength("6");
		fd1.setMinLength(0);
		fd1.setValidChars("cdoeCDOE");
		fd1.setInvalidChars("in");
		evf1.setFieldDescriptor(fd1);
		evFieldList.add(evf1);
		
		EnumeratedValueFieldInfo evf2 = new EnumeratedValueFieldInfo();
		evf2.setId("abbrevValue");
		FieldDescriptorInfo fd2 = new FieldDescriptorInfo();
		fd2.setDataType("string");
		fd2.setMaxLength("2");
		fd2.setMinLength(1);
		fd2.setValidChars("AV");
		fd2.setInvalidChars("");
		evf2.setFieldDescriptor(fd2);
		evFieldList.add(evf2);
		
		EnumeratedValueFieldInfo evf3 = new EnumeratedValueFieldInfo();
		evf3.setId("value");
		FieldDescriptorInfo fd3 = new FieldDescriptorInfo();
		fd3.setDataType("string");
		fd3.setMaxLength("15");
		fd3.setMinLength(1);
		fd3.setValidChars("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		fd3.setInvalidChars("0123456789");
		evf3.setFieldDescriptor(fd3);
		evFieldList.add(evf3);
		
		EnumeratedValueFieldInfo evf4 = new EnumeratedValueFieldInfo();
		evf4.setId("effectiveDate");
		FieldDescriptorInfo fd4 = new FieldDescriptorInfo();
		fd4.setDataType("Date");
		fd4.setMaxValue("2009-12-31");
		fd4.setMinValue("2008-12-31");
		evf4.setFieldDescriptor(fd4);
		evFieldList.add(evf4);
		
		EnumeratedValueFieldInfo evf5 = new EnumeratedValueFieldInfo();
		evf5.setId("expirationDate");
		FieldDescriptorInfo fd5 = new FieldDescriptorInfo();
		fd5.setDataType("Date");
		fd5.setMaxValue("2010-01-01T06:12:30,001");
		fd5.setMinValue("2009-01-01T18:30:15,025");
		evf5.setFieldDescriptor(fd5);
		evFieldList.add(evf5);
		
		EnumeratedValueFieldInfo evf6 = new EnumeratedValueFieldInfo();
		evf6.setId("sortKey");
		FieldDescriptorInfo fd6 = new FieldDescriptorInfo();
		fd6.setDataType("integer");
		fd6.setMaxValue("5");
		fd6.setMinValue("1");
		evf6.setFieldDescriptor(fd6);
		evFieldList.add(evf6);
		
		em.setEnumeratedValueFields(evFieldList);
		
//		enumService.addEnumerationMeta(em);
//		
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		String effectiveDate = "2009-03-07";
//		String expirationDate = "2009-12-18";
//		
//		EnumeratedValue dto = new EnumeratedValue();
//        dto.setCode("CODE");
//        dto.setValue("theValue");
//        dto.setAbbrevValue("V");
//        try{
//	        dto.setEffectiveDate(format.parse(effectiveDate));
//	        dto.setExpirationDate(format.parse(expirationDate));
//        }
//        catch(ParseException e){
//        	
//        }
//        dto.setSortKey(1);
//        
//        //dto context
//        List<Context> dtoContext = new ArrayList<Context>();
//        Context newContext = new Context();
//        newContext.setType("TheContext");
//        newContext.setValue("A");
//        dtoContext.add(newContext);
//        dto.setContexts(dtoContext);
//		
////		EnumeratedValue dto = new EnumeratedValue();
////        dto.setCode("CODE");
////        dto.setValue("vvv");
////        dto.setAbbrevValue("VA");
////        try{
////	        dto.setEffectiveDate(format.parse(effectiveDate));
////	        dto.setExpirationDate(format.parse(expirationDate));
////        }
////        catch(ParseException e){
////        	
////        }
////        dto.setSortKey(3);
////        dto.setContexts(new Contexts());
////        
////        //dto context
////        List<Context> dtoContext = new ArrayList();
////        Context newContext = new Context();
////        newContext.setType("TheContext");
////        newContext.setValue("A");
////        dtoContext.add(newContext);
////        dto.getContexts().setContext(dtoContext);
//        
//        enumService.addEnumeratedValue("KeyV", dto);
	}
}
