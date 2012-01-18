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

package org.kuali.student.r2.core.class1.enumerationmanagement.service.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumContextValueEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumeratedValueEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationRichTextEntity;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumContextValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Ignore
public class TestEnumerationManagementServiceImpl extends AbstractTransactionalDaoTest{
    
    public EnumerationManagementService enumService;
    
    public static String principalId = "123";

    public ContextInfo callContext = null;
    
    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }
    
    /*@Test
    public void testSearch() throws OperationFailedException, MissingParameterException{
        
    	//SearchManager searchManager = new SearchManagerImpl("classpath:em-search-config.xml");
    	//enumService.setSearchManager(searchManager);
    	//enumService.setEnumDAO(enumerationManagementDAO);
    	
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
    	assertEquals(34,searchResult.getRows().size());
    }*/
    
	@Test
	public void testSetEnumDAO(){
		long baseTime = System.currentTimeMillis();
    	
		EnumerationEntity key1 = new EnumerationEntity();
		key1.setId("Key1");
		key1.setName("Key1");
		
		//Enumeration
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
        
        EnumContextValueEntity contextEntity3 = new EnumContextValueEntity();
        contextEntity3.setContextKey("country");
        contextEntity3.setContextValue("US");
        
        EnumContextValueEntity contextEntity2 = new EnumContextValueEntity();
        contextEntity2.setContextKey("country");
        contextEntity2.setContextValue("CA");
        
        EnumContextValueEntity contextEntity4 = new EnumContextValueEntity();
        contextEntity4.setContextKey("country");
        contextEntity4.setContextValue("CA");
        
        entity1.getContextValueEntities().add(contextEntity1);
        List<EnumeratedValueEntity> entityList1 = new ArrayList<EnumeratedValueEntity>();
        entityList1.add(entity1);
        contextEntity1.setEnumeratedValueList(entityList1);
        
        entity2.getContextValueEntities().add(contextEntity3);
        List<EnumeratedValueEntity> entityList2 = new ArrayList<EnumeratedValueEntity>();
        entityList2.add(entity2);
        contextEntity3.setEnumeratedValueList(entityList2);
        
        entity3.getContextValueEntities().add(contextEntity2);
        List<EnumeratedValueEntity> entityList3 = new ArrayList<EnumeratedValueEntity>();
        entityList3.add(entity3);
        contextEntity2.setEnumeratedValueList(entityList3);
     
        entity4.getContextValueEntities().add(contextEntity4);
        List<EnumeratedValueEntity> entityList4 = new ArrayList<EnumeratedValueEntity>();
        entityList4.add(entity4);
        contextEntity4.setEnumeratedValueList(entityList4);
        
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity1);
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity2);
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity3);
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity4);
            	
		//enumService.setEnumDAO(enumerationManagementDAO);
	}
	
	@Test
	public void testFetchAndFindEnumerationMetas() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException{
        //Enumeration Meta
    	EnumerationEntity dao = new EnumerationEntity();
    	dao.setId("metaKey1");
    	dao.setDescr(new EnumerationRichTextEntity("metaDesc1", "metaDesc1"));
    	dao.setName("Name1");
    	    	
    	//enumerationManagementDAO.addEnumeration(dao);
		//enumService.setEnumDAO(enumerationManagementDAO);
		
		//fetchEnumerationMeta
		EnumerationInfo dto = enumService.getEnumeration("metaKey1", callContext);
    	assertEquals(dao.getId(), dto.getKey());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getDescr(), dto.getDescr());
    	
    	//findEnumerationMetas
    	List<EnumerationInfo> list = enumService.getEnumerations(callContext);
    	
    	//there is one in test-beans and 11 in ks-em.sql. so find 13
    	assertEquals(list.size(), 13);
    	boolean foundMeta = false;
    	
    	for(EnumerationInfo ei : list) {
    	    if(ei.getKey().equals("metaKey1")) {
    	        foundMeta = true;
    	        dto = ei;
    	    }
    	}
    	
    	assertTrue(foundMeta);
    	
    	assertEquals(dao.getId(), dto.getKey());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getDescr(), dto.getDescr());
    	
    	//fetchEnumerationMeta for "NULL" key
    	EnumerationInfo dto_null = null;
		try {
			dto_null = enumService.getEnumeration("NULL", callContext);
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
		assertEquals(dto_null, null);
		
	}
	
	@Test
	public void testFetchEnumeratedValue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	long baseTime = System.currentTimeMillis();
    	
        EnumerationEntity key1 = new EnumerationEntity();
        key1.setId("Key1");
        key1.setName("Key1");
    	
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
        entity3.setExpirationDate(null);
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
        
        EnumContextValueEntity contextEntity3 = new EnumContextValueEntity();
        contextEntity3.setContextKey("country");
        contextEntity3.setContextValue("US");
        
        EnumContextValueEntity contextEntity2 = new EnumContextValueEntity();
        contextEntity2.setContextKey("country");
        contextEntity2.setContextValue("CA");
        
        EnumContextValueEntity contextEntity4 = new EnumContextValueEntity();
        contextEntity4.setContextKey("country");
        contextEntity4.setContextValue("CA");
        
        entity1.getContextValueEntities().add(contextEntity1);
        List<EnumeratedValueEntity> entityList1 = new ArrayList<EnumeratedValueEntity>();
        entityList1.add(entity1);
        contextEntity1.setEnumeratedValueList(entityList1);
        
        entity2.getContextValueEntities().add(contextEntity3);
        List<EnumeratedValueEntity> entityList2 = new ArrayList<EnumeratedValueEntity>();
        entityList2.add(entity2);
        contextEntity3.setEnumeratedValueList(entityList2);
        
        entity3.getContextValueEntities().add(contextEntity2);
        List<EnumeratedValueEntity> entityList3 = new ArrayList<EnumeratedValueEntity>();
        entityList3.add(entity3);
        contextEntity2.setEnumeratedValueList(entityList3);
     
        entity4.getContextValueEntities().add(contextEntity4);
        List<EnumeratedValueEntity> entityList4 = new ArrayList<EnumeratedValueEntity>();
        entityList4.add(entity4);
        contextEntity4.setEnumeratedValueList(entityList4);
        
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity1);
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity2);
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity3);
        //enumerationManagementDAO.addEnumeratedValue("Key1", entity4);
        
        List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("Key1", "country", "US", new Date(baseTime), callContext);
         
        assertEquals(list.size(), 2);
        
        	
        list = enumService.getEnumeratedValues("Key1", null, null, null, callContext);
        assertEquals(list.size(), 4);
        
        list = enumService.getEnumeratedValues("Key1" , "country", "CA", null, callContext);
        assertEquals(list.size(), 2);
        	
        list = enumService.getEnumeratedValues("Key1" , "country", "US", null, callContext);
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
            EnumContextValueEntity original = entity2.getContextValueEntities().get(i);
        	assertEquals(c.getKey(), original.getContextKey());
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
        	EnumContextValueEntity original = entity2.getContextValueEntities().get(i);
        	assertEquals(c.getKey(), original.getContextKey());
        	assertEquals(c.getValue(), original.getContextValue());
        	i++;
        }

        list = enumService.getEnumeratedValues("Key1", null, null, new Date(baseTime), callContext);
        assertEquals(list.size(), 4);

        list = enumService.getEnumeratedValues("Key1", null, null, new Date(baseTime+40000000L), callContext);
        assertEquals(list.size(), 3);
        
        list = enumService.getEnumeratedValues("Key1" , "country", "US", new Date(baseTime+40000000L), callContext);
        assertEquals(list.size(), 1);
        
        list = enumService.getEnumeratedValues("Key1" , "country", "CA", new Date(baseTime+40000000L), callContext);
        assertEquals(list.size(), 2);
        
        list = enumService.getEnumeratedValues("Key1" , "country", "CA", new Date(baseTime), callContext);
        assertEquals(list.size(), 2);
        
        list = enumService.getEnumeratedValues(null, null, null, null, callContext);
        assertTrue(list.isEmpty());
        
	}
	
	@Test
	public void testAddEnumeratedValue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException{
				
		EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name2");
        entity.setId("Key2");
		//enumerationManagementDAO.addEnumeration(entity);
        
		long baseTime = System.currentTimeMillis();

		EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setEnumerationKey("Key2");
        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setKey("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
		enumService.addEnumeratedValue("Key2", dto.getCode(), dto, callContext);

		List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("Key2", "ContextA", "1", new Date(baseTime), callContext);
		assertEquals(list.size(), 1);
		EnumeratedValueInfo listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        
        for(EnumContextValueInfo c: listItem.getContexts()){
        	EnumContextValueInfo original = newContext;
        	assertEquals(c.getKey(), original.getKey());
        	assertEquals(c.getValue(), original.getValue());
        }
	}
	
	@Test
	public void testUpdateEnumeratedValue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, ReadOnlyException, VersionMismatchException{
		
        EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name3");
        entity.setId("Key3");
        //enumerationManagementDAO.addEnumeration(entity);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setEnumerationKey("Key3");
		dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");

        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setKey("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        //add first
		enumService.addEnumeratedValue("Key3", dto.getCode(), dto, callContext);

		List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("Key3", "ContextA", "1", new Date(baseTime), callContext);
		assertEquals(list.size(), 1);
		EnumeratedValueInfo listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        
        for(EnumContextValueInfo c: listItem.getContexts()){
        	EnumContextValueInfo original = newContext;
        	assertEquals(c.getKey(), original.getKey());
        	assertEquals(c.getValue(), original.getValue());
        }
        
        //update
        //update currently fails on context updates
        dto.setCode("newCode");
        dto.setValue("newValue");
        dto.getContexts().get(0).setKey("newType");
        dto.getContexts().get(0).setValue("newContextValue");
        enumService.updateEnumeratedValue("Key3", "c", dto, callContext);

		list = enumService.getEnumeratedValues("Key3", "newType", "newContextValue", new Date(baseTime), callContext);
		assertEquals(list.size(), 1);
		listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        
        for(EnumContextValueInfo c: listItem.getContexts()){
        	assertEquals(c.getKey(), "newType");
        	assertEquals(c.getValue(), "newContextValue");
        }
        
	}
	
	@Test
	public void testRemoveEnumeratedValue() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException{
		
        EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name3");
        entity.setId("Key3");
        //enumerationManagementDAO.addEnumeration(entity);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValueInfo dto = new EnumeratedValueInfo();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey("1");
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setEnumerationKey("Key3");
        
        //dto context
        List<EnumContextValueInfo> dtoContext = new ArrayList<EnumContextValueInfo>();
        EnumContextValueInfo newContext = new EnumContextValueInfo();
        newContext.setKey("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.setContexts(dtoContext);
        //add first
		enumService.addEnumeratedValue("Key3", dto.getCode(), dto, callContext);
		enumService.deleteEnumeratedValue("Key3", "c", callContext);

		List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("Key3", "ContextA", "1", new Date(baseTime), callContext);
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testAutoGenerate() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AlreadyExistsException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException{
		//long baseTime = System.currentTimeMillis();

	    //enumService.setEnumDAO(enumerationManagementDAO);

		EnumerationEntity semesterEnum = new EnumerationEntity();
		semesterEnum.setId("SemesterEnum");
		semesterEnum.setDescr(new EnumerationRichTextEntity("Semester Enum", "Semester Enum"));
		semesterEnum.setName("SemesterEnum");

		EnumerationEntity cityEnum = new EnumerationEntity();
        cityEnum.setId("CityEnum");
        cityEnum.setDescr(new EnumerationRichTextEntity("CityEnum", "CityEnum"));
        cityEnum.setName("CityEnum");
		
		//enumerationManagementDAO.addEnumeration(semesterEnum);
		//enumerationManagementDAO.addEnumeration(cityEnum);

		EnumDataGenerator.generate(enumService, callContext);
		
		List<EnumeratedValueInfo> list = enumService.getEnumeratedValues("SemesterEnum", null, null, null, callContext);
		assertEquals(list.size(), 10);
		for(EnumeratedValueInfo e: list){
			assertTrue(e.getValue().contains("Semester"));
		}
		
		list = enumService.getEnumeratedValues("CityEnum", null, null, null, callContext);
		assertEquals(list.size(), 10);
		for(EnumeratedValueInfo e: list){
			assertTrue(e.getValue().contains("City"));
		}
	}
	
	@Test
	public void testValidate(){
		
		EnumerationInfo em = new EnumerationInfo();
		em.setKey("KeyV");
		em.setName("Validation Meta");
		RichTextInfo rt = new RichTextInfo();
        rt.setPlain("Meta used for validation");
		em.setDescr(rt);
	}
}
