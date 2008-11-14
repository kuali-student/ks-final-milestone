package org.kuali.student.enumeration.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.*;
import org.kuali.student.enumeration.entity.*;
import org.kuali.student.enumeration.service.impl.util.POJOConverter;

import junit.framework.TestCase;

public class DozerTest extends TestCase {
    public void testContext(){
        Context context = new Context();
        context.setType("type");
        context.setValue("value");
        
        ContextEntity contextEntity = new ContextEntity();
        POJOConverter.map(context, contextEntity);
        
        assertEquals(context.getValue(), contextEntity.getContextValue());
        assertEquals(context.getType(), contextEntity.getContextKey());
    }
    public void testDAOtoDTOEnumeratedValue(){
        EnumeratedValueEntity dao = new EnumeratedValueEntity();
        dao.setId("1");
        dao.setCode("c");
        dao.setEffectiveDate(new Date());
        dao.setEnumerationKey("key");
        dao.setSortKey(1);
        dao.setValue("v");
        dao.setAbbrevValue("a");
        
        EnumeratedValue dto = new EnumeratedValue();
        
        POJOConverter.map(dao, dto);
        assertEquals(dao.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(dao.getExpirationDate(), dto.getExpirationDate());
        assertEquals(dao.getValue(), dto.getValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getSortKey(), dto.getSortKey());
        
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
            assertEquals(daoItem.getSortKey(), dtoItem.getSortKey());
        	i++;
        }
    }
    
    public void testDTOtoDAOEnumeratedValue(){
        
    	EnumeratedValue dto = new EnumeratedValue();
        dto.setCode("c");
        dto.setEffectiveDate(new Date());
        dto.setSortKey(1);
        dto.setValue("v");
        dto.setAbbrevValue("a");

        EnumeratedValueEntity dao = new EnumeratedValueEntity();
        
        POJOConverter.map(dto, dao);
        assertEquals(dao.getAbbrevValue(), dto.getAbbrevValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getEffectiveDate(), dto.getEffectiveDate());
        assertEquals(dao.getExpirationDate(), dto.getExpirationDate());
        assertEquals(dao.getValue(), dto.getValue());
        assertEquals(dao.getCode(), dto.getCode());
        assertEquals(dao.getSortKey(), dto.getSortKey());
        
        List<EnumeratedValue> listDto = new ArrayList<EnumeratedValue>();
        listDto.add(dto);
        
        EnumeratedValue dto2 = new EnumeratedValue();
        dto2.setCode("c2");
        dto2.setEffectiveDate(new Date());
        dto2.setSortKey(1);
        dto2.setValue("v2");
        dto2.setAbbrevValue("a2");
        
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
            assertEquals(daoItem.getSortKey(), dtoItem.getSortKey());
        	i++;
        }
        
    }
    
    public void testDAOtoDTOContext(){
    	ContextEntity dao = new ContextEntity();
    	dao.setContextKey("ck");
    	dao.setId("id");
    	dao.setContextKey("v");
    	
    	Context dto = new Context();
    	
    	/*
    	POJOConverter.map(dao, dto);
        assertEquals(dao.getContextKey(), dto.getType());
        assertEquals(dao.getValue(), dto.getValue());
		*/
    }
    
    public void testDTOtoDAOContext(){
    	
    }
    
    public void testDAOtoDTOContextMeta(){
    	ContextMetaEntity dao = new ContextMetaEntity();
    	dao.setDataType("d");
    	dao.setMaxLength(5);
    	dao.setMinLength(1);
    	//spelling is wrong
    	dao.setMinOccors(1);
    	dao.setMaxOccors(2);
    	dao.setEnumerationId("e");
    	dao.setType("t");
    	dao.setId("id");
    	
    	ContextValueDescriptor dto = new ContextValueDescriptor();
    	
    	POJOConverter.map(dao, dto);
    	assertEquals(dao.getDataType(), dto.getDataType());
    	assertEquals(dao.getMaxLength(), dto.getMaxLength().intValue());
    	assertEquals(dao.getMinLength(), dto.getMinLength().intValue());
    	//assertEquals(dao.getMinOccors(), dto.getMinOccurs().intValue());
    	//assertEquals(dao.getMaxOccors(), dto.getMaxOccurs().intValue());
    	
    }
    
    public void testDTOtoDAOContextMeta(){
    	
    }
}
