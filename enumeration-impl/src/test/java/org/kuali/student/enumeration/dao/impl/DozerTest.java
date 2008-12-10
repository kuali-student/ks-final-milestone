package org.kuali.student.enumeration.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.kuali.student.enumeration.dto.*;
import org.kuali.student.enumeration.entity.*;
import org.kuali.student.enumeration.service.impl.util.POJOConverter;

public class DozerTest {
	@Test
	public void testContext(){
        Context context = new Context();
        context.setType("type");
        context.setValue("value");
        
        ContextEntity contextEntity = new ContextEntity();
        POJOConverter.map(context, contextEntity);
        
        assertEquals(context.getValue(), contextEntity.getContextValue());
        assertEquals(context.getType(), contextEntity.getContextKey());
    }
	@Test
    public void testDAOtoDTOEnumeratedValue(){
        EnumeratedValueEntity dao = new EnumeratedValueEntity();
        dao.setId("1");
        dao.setCode("c");
        dao.setEffectiveDate(new Date());
        dao.setEnumerationKey("key");
        dao.setSortKey(1);
        dao.setValue("v");
        dao.setAbbrevValue("a");
        ContextEntity contextEntity = new ContextEntity();
        contextEntity.setContextKey("context key");
        dao.getContextEntityList().add(contextEntity);
        
        
        EnumeratedValue dto = new EnumeratedValue();
        
        POJOConverter.map(dao, dto);
        
        assertEquals(dao.getContextEntityList().get(0).getContextKey() ,dto.getContexts().getContext().get(0).getType());

        
        assertEquals(dao.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(dao.getExpirationDate(), dto.getExpirationDate());
        assertEquals(dao.getValue(), dto.getValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getSortKey(), dto.getSortKey().intValue());
        
        List<EnumeratedValueEntity> listDao = new ArrayList<EnumeratedValueEntity>();
        listDao.add(dao);
        
        EnumeratedValueEntity dao2 = new EnumeratedValueEntity();
        dao2.setId("2");
        dao2.setCode("c2");
        dao2.setEffectiveDate(new Date());
        dao2.setExpirationDate(new Date());
        dao2.setEnumerationKey("key2");
        dao2.setSortKey(1);
        dao2.setValue("v2");
        dao2.setAbbrevValue("a2");
        
        listDao.add(dao2);
        
        List<EnumeratedValue> listDto = (List<EnumeratedValue>)POJOConverter.mapList(listDao, dto.getClass());
        
        int i = 0;
        for(EnumeratedValue dtoItem: listDto){
        	EnumeratedValueEntity daoItem = listDao.get(i);
            assertEquals(daoItem.getAbbrevValue(), dtoItem.getAbbrevValue());
            assertEquals(daoItem.getEffectiveDate(), dtoItem.getEffectiveDate());
            assertEquals(daoItem.getExpirationDate(), dtoItem.getExpirationDate());
            assertEquals(daoItem.getValue(), dtoItem.getValue());
            assertEquals(daoItem.getCode(), dtoItem.getCode());
            assertEquals(daoItem.getSortKey(), dtoItem.getSortKey().intValue());
        	i++;
        }
    }
	@Test
    public void testDTOtoDAOEnumeratedValue(){
        
    	EnumeratedValue dto = new EnumeratedValue();
        dto.setCode("c");
        dto.setEffectiveDate(new Date());
        dto.setSortKey(1);
        dto.setValue("v");
        dto.setAbbrevValue("a");
        dto.setContexts(new Contexts());
        
        //dto context
        List<Context> dtoContext = new ArrayList();
        Context newContext = new Context();
        newContext.setType("ContextA");
        newContext.setValue("1");
        dtoContext.add(newContext);
        dto.getContexts().setContext(dtoContext);

        EnumeratedValueEntity dao = new EnumeratedValueEntity();
        
        POJOConverter.map(dto, dao);
        assertEquals(dao.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(dao.getExpirationDate(), dto.getExpirationDate());
        assertEquals(dao.getValue(), dto.getValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getSortKey(), dto.getSortKey().intValue());
        
        //context value check
        List<ContextEntity> contexts = dao.getContextEntityList();
        assertEquals(contexts.size(), 1);
        ContextEntity c = contexts.get(0);
        assertEquals(c.getContextKey(), newContext.getType());
        assertEquals(c.getContextValue(), newContext.getValue());
        
        //List
        List<EnumeratedValue> listDto = new ArrayList<EnumeratedValue>();
        listDto.add(dto);
        
        EnumeratedValue dto2 = new EnumeratedValue();
        dto2.setCode("c2");
        dto2.setEffectiveDate(new Date());
        dto2.setSortKey(1);
        dto2.setValue("v2");
        dto2.setAbbrevValue("a2");
        dto2.setContexts(new Contexts());
        dto2.getContexts().setContext(dtoContext);
        
        listDto.add(dto2);
        
        List<EnumeratedValueEntity> listDao = (List<EnumeratedValueEntity>)POJOConverter.mapList(listDto, dao.getClass());
        
        int i = 0;
        for(EnumeratedValueEntity daoItem: listDao){
        	EnumeratedValue dtoItem = listDto.get(i);
            assertEquals(daoItem.getAbbrevValue(), dtoItem.getAbbrevValue());
            assertEquals(daoItem.getCode(), dtoItem.getCode());
            assertEquals(daoItem.getEffectiveDate(), dtoItem.getEffectiveDate());
            assertEquals(daoItem.getExpirationDate(), dtoItem.getExpirationDate());
            assertEquals(daoItem.getValue(), dtoItem.getValue());
            assertEquals(daoItem.getCode(), dtoItem.getCode());
            assertEquals(daoItem.getSortKey(), dtoItem.getSortKey().intValue());
            contexts = daoItem.getContextEntityList();
            assertEquals(contexts.size(), 1);
            c = contexts.get(0);
            assertEquals(c.getContextKey(), newContext.getType());
            assertEquals(c.getContextValue(), newContext.getValue());
        	i++;
        }
        
    }
	@Test
    public void testDAOtoDTOContext(){
    	ContextEntity dao = new ContextEntity();
    	dao.setContextKey("ck");
    	dao.setId("id");
    	dao.setContextKey("v");
    	
    	Context dto = new Context();
    	
    	POJOConverter.map(dao, dto);
        assertEquals(dao.getContextKey(), dto.getType());
        assertEquals(dao.getContextValue(), dto.getValue());
		
    }
	@Test
    public void testDTOtoDAOContext(){
    	Context dto = new Context();
    	dto.setType("TypeA");
    	dto.setValue("ValueA");
    	
    	ContextEntity dao = new ContextEntity();
    	
    	POJOConverter.map(dto, dao);
        assertEquals(dao.getContextKey(), dto.getType());
        assertEquals(dao.getContextValue(), dto.getValue());
    }
	@Test
    public void testDAOtoDTOContextMeta(){
    	ContextMetaEntity dao = new ContextMetaEntity();
    	dao.setDataType("d");
    	dao.setMaxLength(5);
    	dao.setMinLength(1);
    	dao.setMinOccurs(1);
    	dao.setMaxOccurs(2);
    	dao.setEnumerationId("e");
    	dao.setType("t");
    	dao.setId("id");
    	
    	ContextValueDescriptor dto = new ContextValueDescriptor();
    	
    	POJOConverter.map(dao, dto);
    	assertEquals(dao.getDataType(), dto.getDataType());
    	assertEquals(dao.getMaxLength(), dto.getMaxLength());
    	assertEquals(dao.getMinLength(), dto.getMinLength());
    	assertEquals(dao.getMinOccurs(), dto.getMinOccurs());
    	assertEquals(dao.getMaxOccurs(), dto.getMaxOccurs());
    	
    }
	@Test
    public void testDTOtoDAOContextMeta(){
    	ContextValueDescriptor dto = new ContextValueDescriptor();
    	dto.setDataType("d");
    	dto.setMaxLength(5);
    	dto.setMinLength(1);
    	dto.setMinOccurs(1);
    	dto.setMaxOccurs(2);
    	
    	ContextMetaEntity dao = new ContextMetaEntity();
    	
    	POJOConverter.map(dto, dao);
    	assertEquals(dao.getDataType(), dto.getDataType());
    	assertEquals(dao.getMaxLength(), dto.getMaxLength());
    	assertEquals(dao.getMinLength(), dto.getMinLength());
    	assertEquals(dao.getMinOccurs(), dto.getMinOccurs());
    	assertEquals(dao.getMaxOccurs(), dto.getMaxOccurs());
    }
	@Test
    public void testDAOtoDTOEnumerationMeta(){
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
    	//fieldDao.setEnumerationId("1");
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
    	
    	EnumerationMeta dto = new EnumerationMeta();
    	POJOConverter.map(dao, dto);
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
    }
	@Test
    public void testDTOtoDAOEnumerationMeta(){
    	EnumerationMeta dto = new EnumerationMeta();
    	dto.setKey("metaKey1");
    	dto.setDesc("metaDesc1");
    	dto.setName("Name1");
    	dto.setEnumeratedValueFields(new EnumeratedValueFields());
    	EnumeratedValueField fieldDto = new EnumeratedValueField();
    	fieldDto.setFieldDescriptor(new FieldDescriptor());
    	fieldDto.getFieldDescriptor().setInvalidChars("f");
    	fieldDto.getFieldDescriptor().setValidChars("ab");
    	fieldDto.setKey("key4");
    	fieldDto.getFieldDescriptor().setMaxLength(2);
    	fieldDto.getFieldDescriptor().setMinLength(0);
    	fieldDto.getFieldDescriptor().setMaxOccurs(2);
    	fieldDto.getFieldDescriptor().setMinOccurs(1);
    	fieldDto.getFieldDescriptor().setDataType("string");

    	dto.getEnumeratedValueFields().getEnumeratedValueField().add(fieldDto);
    	
    	EnumeratedValueField fieldDto2 = new EnumeratedValueField();
    	fieldDto2.setFieldDescriptor(new FieldDescriptor());
    	fieldDto2.getFieldDescriptor().setInvalidChars("1");
    	fieldDto2.getFieldDescriptor().setValidChars("2");
    	fieldDto2.setKey("key3");
    	fieldDto2.getFieldDescriptor().setMaxLength(2);
    	fieldDto2.getFieldDescriptor().setMinLength(0);
    	fieldDto2.getFieldDescriptor().setMaxOccurs(2);
    	fieldDto2.getFieldDescriptor().setMinOccurs(1);
    	fieldDto2.getFieldDescriptor().setDataType("int");

    	dto.getEnumeratedValueFields().getEnumeratedValueField().add(fieldDto2);
    	
    	EnumerationMetaEntity dao = new EnumerationMetaEntity();
    	POJOConverter.map(dto, dao);
    	assertEquals(dao.getEnumerationKey(), dto.getKey());
    	assertEquals(dao.getName(), dto.getName());
    	assertEquals(dao.getEnumerationMetaKeyDesc(), dto.getDesc());
    	List<EnumeratedValueFieldEntity> fields = dao.getEnumeratedValueFieldList();
    	assertEquals(fields.size(), 2);
    	int i = 0;
    	for(EnumeratedValueFieldEntity fieldDao: fields){
    		fieldDto = dto.getEnumeratedValueFields().getEnumeratedValueField().get(i);
    		assertEquals(fieldDao.getFieldKey(), fieldDto.getKey());
    		assertEquals(fieldDao.getMaxLength(), fieldDto.getFieldDescriptor().getMaxLength().intValue());
    		assertEquals(fieldDao.getMinLength(), fieldDto.getFieldDescriptor().getMinLength().intValue());
    		assertEquals(fieldDao.getValidChars(), fieldDto.getFieldDescriptor().getValidChars());
    		assertEquals(fieldDao.getInvalidChars(), fieldDto.getFieldDescriptor().getInvalidChars());
    		assertEquals(fieldDao.getMaxOccurs(), fieldDto.getFieldDescriptor().getMaxOccurs().intValue());
    		assertEquals(fieldDao.getMinOccurs(), fieldDto.getFieldDescriptor().getMinOccurs().intValue());
    		assertEquals(fieldDao.getDataType(), fieldDto.getFieldDescriptor().getDataType());
    		i++;
    	}
    }
}
