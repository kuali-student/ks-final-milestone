package org.kuali.student.enumeration.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.entity.EnumeratedValueEntity;
import org.kuali.student.enumeration.service.impl.util.POJOConverter;

import junit.framework.TestCase;

public class DozerTest extends TestCase {

    public void testA(){
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
        
        List<EnumeratedValue> listDto = (List<EnumeratedValue>)POJOConverter.mapList(listDao, dto.getClass());
        
        int i = 0;
        for(EnumeratedValue dtoItem: listDto){
        	EnumeratedValueEntity daoItem = listDao.get(i);
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
}
