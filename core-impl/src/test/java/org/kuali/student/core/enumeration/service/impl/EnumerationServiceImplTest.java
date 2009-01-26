package org.kuali.student.core.enumeration.service.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.enumeration.dao.impl.EnumerationManagementDAOImpl;
import org.kuali.student.core.enumeration.dto.Context;
import org.kuali.student.core.enumeration.dto.Contexts;
import org.kuali.student.core.enumeration.dto.EnumeratedValue;
import org.kuali.student.core.enumeration.dto.EnumeratedValueField;
import org.kuali.student.core.enumeration.dto.EnumeratedValueFields;
import org.kuali.student.core.enumeration.dto.EnumeratedValueList;
import org.kuali.student.core.enumeration.dto.EnumerationMeta;
import org.kuali.student.core.enumeration.dto.EnumerationMetaList;
import org.kuali.student.core.enumeration.dto.FieldDescriptor;
import org.kuali.student.core.enumeration.dto.mock.DataGenerator;
import org.kuali.student.core.enumeration.entity.ContextEntity;
import org.kuali.student.core.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumeration.entity.EnumeratedValueFieldEntity;
import org.kuali.student.core.enumeration.entity.EnumerationMetaEntity;
@PersistenceFileLocation("classpath:META-INF/enumeration-persistence.xml")
public class EnumerationServiceImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.enumeration.dao.impl.EnumerationManagementDAOImpl", testDataFile = "classpath:enumeration-test-beans.xml")
    public EnumerationManagementDAOImpl enumerationManagementDAO;
	
    public EnumerationServiceImpl enumService = new EnumerationServiceImpl();
    
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
	public void testFetchAndFindEnumerationMetas(){
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
    	fieldDao.setMaxLength(2);
    	fieldDao.setMinLength(0);
    	fieldDao.setMaxOccurs(2);
    	fieldDao.setMinOccurs(1);
    	fieldDao.setEnumerationMetaEntity(dao);
    	fieldDao.setDataType("string");
    	dao.getEnumeratedValueFieldList().add(fieldDao);
    	
    	EnumeratedValueFieldEntity fieldDao2 = new EnumeratedValueFieldEntity();
    	fieldDao2.setInvalidChars("456");
    	fieldDao2.setValidChars("123");
    	fieldDao2.setId("id2");
    	fieldDao2.setFieldKey("key2");
    	fieldDao2.setMaxLength(2);
    	fieldDao2.setMinLength(0);
    	fieldDao2.setMaxOccurs(2);
    	fieldDao2.setMinOccurs(1);
    	fieldDao2.setEnumerationMetaEntity(dao);
    	fieldDao2.setDataType("int");
    	dao.getEnumeratedValueFieldList().add(fieldDao2);
    	
    	enumerationManagementDAO.addEnumerationMeta(dao);
		enumService.setEnumDAO(enumerationManagementDAO);
		
		//fetchEnumerationMeta
		EnumerationMeta dto = enumService.fetchEnumerationMeta("metaKey1");
    	assertEquals(dao.getEnumerationKey(), dto.getKey());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getEnumerationMetaKeyDesc(), dto.getDesc());
    	List<EnumeratedValueField> fields = dto.getEnumeratedValueFields().getEnumeratedValueField();
    	assertEquals(fields.size(), 2);
    	int i = 0;
    	for(EnumeratedValueField field: fields){
    		fieldDao = dao.getEnumeratedValueFieldList().get(i);
    		assertEquals(fieldDao.getFieldKey(), field.getKey());
    		assertEquals(fieldDao.getMaxLength(), field.getFieldDescriptor().getMaxLength().intValue());
    		assertEquals(fieldDao.getMinLength(), field.getFieldDescriptor().getMinLength().intValue());
    		assertEquals(fieldDao.getValidChars(), field.getFieldDescriptor().getValidChars());
    		assertEquals(fieldDao.getInvalidChars(), field.getFieldDescriptor().getInvalidChars());
    		assertEquals(fieldDao.getMaxOccurs(), field.getFieldDescriptor().getMaxOccurs().intValue());
    		assertEquals(fieldDao.getMinOccurs(), field.getFieldDescriptor().getMinOccurs().intValue());
    		assertEquals(fieldDao.getDataType(), field.getFieldDescriptor().getDataType());
    		i++;
    	}
    	
    	//findEnumerationMetas
    	EnumerationMetaList result = enumService.findEnumerationMetas();
    	List<EnumerationMeta> list = result.getEnumerationMeta();
    	//there is one in test-beans... so find 2
    	assertEquals(list.size(), 2);
    	dto = list.get(0);
    	if(!dto.getKey().equals("metaKey1")){
    		dto = list.get(1);
    	}
    	assertEquals(dao.getEnumerationKey(), dto.getKey());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getEnumerationMetaKeyDesc(), dto.getDesc());
    	fields = dto.getEnumeratedValueFields().getEnumeratedValueField();
    	assertEquals(fields.size(), 2);
    	i = 0;
    	for(EnumeratedValueField field: fields){
    		fieldDao = dao.getEnumeratedValueFieldList().get(i);
    		assertEquals(fieldDao.getFieldKey(), field.getKey());
    		assertEquals(fieldDao.getMaxLength(), field.getFieldDescriptor().getMaxLength().intValue());
    		assertEquals(fieldDao.getMinLength(), field.getFieldDescriptor().getMinLength().intValue());
    		assertEquals(fieldDao.getValidChars(), field.getFieldDescriptor().getValidChars());
    		assertEquals(fieldDao.getInvalidChars(), field.getFieldDescriptor().getInvalidChars());
    		assertEquals(fieldDao.getMaxOccurs(), field.getFieldDescriptor().getMaxOccurs().intValue());
    		assertEquals(fieldDao.getMinOccurs(), field.getFieldDescriptor().getMinOccurs().intValue());
    		assertEquals(fieldDao.getDataType(), field.getFieldDescriptor().getDataType());
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
        
        EnumeratedValueList result = enumService.fetchEnumeration("Key1", "country", "US", new Date(baseTime));
        List<EnumeratedValue> list = result.getEnumeratedValue();
        assertEquals(list.size(), 2);
        
        result =  
        	enumService.fetchEnumeration("Key1", null, null, null);
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 4);
        
        result =  
        	enumService.fetchEnumeration("Key1" , "country", "CA", null);
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 2);
        

        
        result =  
        	enumService.fetchEnumeration("Key1" , "country", "US", null);
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 2);
        
        //testing accuracy
        EnumeratedValue listItem = list.get(0); 
        
        assertEquals(listItem.getAbbrevValue(), entity1.getAbbrevValue());
        assertEquals(listItem.getCode(), entity1.getCode());
        assertEquals(listItem.getEffectiveDate(), entity1.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), entity1.getExpirationDate());
        assertEquals(listItem.getSortKey().intValue(), entity1.getSortKey());
        assertEquals(listItem.getValue(), entity1.getValue());
        int i =0;
        for(Context c: listItem.getContexts().getContext()){
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
        assertEquals(listItem.getSortKey().intValue(), entity2.getSortKey());
        assertEquals(listItem.getValue(), entity2.getValue());
        i =0;
        for(Context c: listItem.getContexts().getContext()){
        	ContextEntity original = entity2.getContextEntityList().get(i);
        	assertEquals(c.getType(), original.getContextKey());
        	assertEquals(c.getValue(), original.getContextValue());
        	i++;
        }
        
        result =  
        	enumService.fetchEnumeration("Key1", null, null, new Date(baseTime));
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 4);
        
        result =  
        	enumService.fetchEnumeration("Key1", null, null, new Date(baseTime+40000000L));
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 2);
        
        result =  
        	enumService.fetchEnumeration("Key1" , "country", "US", new Date(baseTime+40000000L));
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 1);
        
        result =  
        	enumService.fetchEnumeration("Key1" , "country", "CA", new Date(baseTime+40000000L));
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 1);
        
        result =  
        	enumService.fetchEnumeration("Key1" , "country", "CA", new Date(baseTime));
        list = result.getEnumeratedValue();
        assertEquals(list.size(), 2);
        
        result = enumService.fetchEnumeration(null, null, null, null);
        list = result.getEnumeratedValue();
        assertTrue(list.isEmpty());
        
	}
	
	@Test
	public void testAddEnumeratedValue(){
		enumService.setEnumDAO(enumerationManagementDAO);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValue dto = new EnumeratedValue();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey(1);
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setContexts(new Contexts());
        
        //dto context
        List<Context> dtoContext = new ArrayList<Context>();
        Context newContext = new Context();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.getContexts().setContext(dtoContext);
		enumService.addEnumeratedValue("Key2", dto);
		EnumeratedValueList result = enumService.fetchEnumeration("Key2", "ContextA", "1", new Date(baseTime));
		List<EnumeratedValue> list = result.getEnumeratedValue();
		assertEquals(list.size(), 1);
		EnumeratedValue listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        int i =0;
        for(Context c: listItem.getContexts().getContext()){
        	Context original = newContext;
        	assertEquals(c.getType(), original.getType());
        	assertEquals(c.getValue(), original.getValue());
        	i++;
        }
	}
	
	@Test
	public void testUpdateEnumeratedValue(){
		enumService.setEnumDAO(enumerationManagementDAO);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValue dto = new EnumeratedValue();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey(1);
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setContexts(new Contexts());
        
        //dto context
        List<Context> dtoContext = new ArrayList<Context>();
        Context newContext = new Context();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.getContexts().setContext(dtoContext);
        //add first
		enumService.addEnumeratedValue("Key3", dto);
		EnumeratedValueList result = enumService.fetchEnumeration("Key3", "ContextA", "1", new Date(baseTime));
		List<EnumeratedValue> list = result.getEnumeratedValue();
		assertEquals(list.size(), 1);
		EnumeratedValue listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        int i =0;
        for(Context c: listItem.getContexts().getContext()){
        	Context original = newContext;
        	assertEquals(c.getType(), original.getType());
        	assertEquals(c.getValue(), original.getValue());
        	i++;
        }
        
        //update
        //update currently fails on context updates
        dto.setCode("newCode");
        dto.setValue("newValue");
        dto.getContexts().getContext().get(0).setType("newType");
        dto.getContexts().getContext().get(0).setValue("newContextValue");
        enumService.updateEnumeratedValue("Key3", "c", dto);
		
        result = enumService.fetchEnumeration("Key3", "newType", "newContextValue", new Date(baseTime));
		list = result.getEnumeratedValue();
		assertEquals(list.size(), 1);
		listItem = list.get(0);
		
        assertEquals(listItem.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(listItem.getCode(), dto.getCode());
        assertEquals(listItem.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(listItem.getExpirationDate(), dto.getExpirationDate());
        assertEquals(listItem.getSortKey(), dto.getSortKey());
        assertEquals(listItem.getValue(), dto.getValue());
        i =0;
        for(Context c: listItem.getContexts().getContext()){
        	assertEquals(c.getType(), "newType");
        	assertEquals(c.getValue(), "newContextValue");
        	i++;
        }
        
	}
	
	@Test
	public void testRemoveEnumeratedValue(){
		enumService.setEnumDAO(enumerationManagementDAO);
		
		long baseTime = System.currentTimeMillis();
		
		EnumeratedValue dto = new EnumeratedValue();
        dto.setCode("c");
        dto.setEffectiveDate(new Date(baseTime-10000000L));
        dto.setExpirationDate(new Date(baseTime+10000000L));
        dto.setSortKey(1);
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setContexts(new Contexts());
        
        //dto context
        List<Context> dtoContext = new ArrayList<Context>();
        Context newContext = new Context();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.getContexts().setContext(dtoContext);
        //add first
		enumService.addEnumeratedValue("Key4", dto);
		enumService.removeEnumeratedValue("Key4", "c");
		EnumeratedValueList result = enumService.fetchEnumeration("Key4", "ContextA", "1", new Date(baseTime));
		List<EnumeratedValue> list = result.getEnumeratedValue();
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testAutoGenerate(){
		//long baseTime = System.currentTimeMillis();
		enumService.setEnumDAO(enumerationManagementDAO);

		DataGenerator.generate(enumService);
		
		EnumeratedValueList result = enumService.fetchEnumeration("SemesterEnum", null, null, null);
		List<EnumeratedValue> list = result.getEnumeratedValue();
		assertEquals(list.size(), 10);
		for(EnumeratedValue e: list){
			assertTrue(e.getValue().contains("Semester"));
		}
		
		result = enumService.fetchEnumeration("CityEnum", null, null, null);
		list = result.getEnumeratedValue();
		assertEquals(list.size(), 10);
		for(EnumeratedValue e: list){
			assertTrue(e.getValue().contains("City"));
		}
	}
	
	@Test
	public void testValidate(){
		enumService.setEnumDAO(enumerationManagementDAO);
		
		//long baseTime = System.currentTimeMillis();
		
		EnumerationMeta em = new EnumerationMeta();
		em.setKey("KeyV");
		em.setName("Validation Meta");
		em.setDesc("Meta used for validation");
		EnumeratedValueFields evFields = new EnumeratedValueFields();
		List<EnumeratedValueField> evFieldList = new ArrayList<EnumeratedValueField>();
		
		EnumeratedValueField evf1 = new EnumeratedValueField();
		evf1.setKey("code");
		FieldDescriptor fd1 = new FieldDescriptor();
		fd1.setDataType("string");
		fd1.setMaxLength(6);
		fd1.setMinLength(0);
		fd1.setValidChars("cdoeCDOE");
		fd1.setInvalidChars("in");
		evf1.setFieldDescriptor(fd1);
		evFieldList.add(evf1);
		
		EnumeratedValueField evf2 = new EnumeratedValueField();
		evf2.setKey("abbrevValue");
		FieldDescriptor fd2 = new FieldDescriptor();
		fd2.setDataType("string");
		fd2.setMaxLength(2);
		fd2.setMinLength(1);
		fd2.setValidChars("AV");
		fd2.setInvalidChars("");
		evf2.setFieldDescriptor(fd2);
		evFieldList.add(evf2);
		
		EnumeratedValueField evf3 = new EnumeratedValueField();
		evf3.setKey("value");
		FieldDescriptor fd3 = new FieldDescriptor();
		fd3.setDataType("string");
		fd3.setMaxLength(15);
		fd3.setMinLength(1);
		fd3.setValidChars("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		fd3.setInvalidChars("0123456789");
		evf3.setFieldDescriptor(fd3);
		evFieldList.add(evf3);
		
		EnumeratedValueField evf4 = new EnumeratedValueField();
		evf4.setKey("effectiveDate");
		FieldDescriptor fd4 = new FieldDescriptor();
		fd4.setDataType("Date");
		fd4.setMaxValue("2009-12-31");
		fd4.setMinValue("2008-12-31");
		evf4.setFieldDescriptor(fd4);
		evFieldList.add(evf4);
		
		EnumeratedValueField evf5 = new EnumeratedValueField();
		evf5.setKey("expirationDate");
		FieldDescriptor fd5 = new FieldDescriptor();
		fd5.setDataType("Date");
		fd5.setMaxValue("2010-01-01T06:12:30,001");
		fd5.setMinValue("2009-01-01T18:30:15,025");
		evf5.setFieldDescriptor(fd5);
		evFieldList.add(evf5);
		
		EnumeratedValueField evf6 = new EnumeratedValueField();
		evf6.setKey("sortKey");
		FieldDescriptor fd6 = new FieldDescriptor();
		fd6.setDataType("integer");
		fd6.setMaxValue("5");
		fd6.setMinValue("1");
		evf6.setFieldDescriptor(fd6);
		evFieldList.add(evf6);
		
		evFields.setEnumeratedValueField(evFieldList);
		em.setEnumeratedValueFields(evFields);
		
		enumService.addEnumerationMeta(em);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String effectiveDate = "2009-03-07";
		String expirationDate = "2009-12-18";
		
		EnumeratedValue dto = new EnumeratedValue();
        dto.setCode("CODE");
        dto.setValue("theValue");
        dto.setAbbrevValue("V");
        try{
	        dto.setEffectiveDate(format.parse(effectiveDate));
	        dto.setExpirationDate(format.parse(expirationDate));
        }
        catch(ParseException e){
        	
        }
        dto.setSortKey(1);
        dto.setContexts(new Contexts());
        
        //dto context
        List<Context> dtoContext = new ArrayList<Context>();
        Context newContext = new Context();
        newContext.setType("TheContext");
        newContext.setValue("A");
        dtoContext.add(newContext);
        dto.getContexts().setContext(dtoContext);
		
//		EnumeratedValue dto = new EnumeratedValue();
//        dto.setCode("CODE");
//        dto.setValue("vvv");
//        dto.setAbbrevValue("VA");
//        try{
//	        dto.setEffectiveDate(format.parse(effectiveDate));
//	        dto.setExpirationDate(format.parse(expirationDate));
//        }
//        catch(ParseException e){
//        	
//        }
//        dto.setSortKey(3);
//        dto.setContexts(new Contexts());
//        
//        //dto context
//        List<Context> dtoContext = new ArrayList();
//        Context newContext = new Context();
//        newContext.setType("TheContext");
//        newContext.setValue("A");
//        dtoContext.add(newContext);
//        dto.getContexts().setContext(dtoContext);
        
        enumService.addEnumeratedValue("KeyV", dto);
	}
}
