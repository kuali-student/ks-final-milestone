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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationRichTextEntity;

@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
//@Ignore
public class TestEnumerationDao extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumerationDao")
    public EnumerationDao enumerationDao;

    @Test
    public void testFindEnumerations(){
        List<EnumerationEntity> list = enumerationDao.findAll();
        
        assertEquals(list.size(),1);
        
        EnumerationEntity returnedEntity = list.get(0);
        
        assertEquals(returnedEntity.getName(), "name 1");
        assertEquals(returnedEntity.getId(), "key 1");
        assertEquals(returnedEntity.getDescr(), "desc 1");
       
    }    

    @Test
    public void testFetchEnumeration() throws DoesNotExistException{
    	EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name3");
        entity.setId("Key3");

        entity.setDescr(new EnumerationRichTextEntity("desc3", "desc3"));
        
        enumerationDao.persist(entity);
        
        EnumerationEntity returnedEntity = enumerationDao.find("Key3");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getId(), entity.getId());
        assertEquals(returnedEntity.getDescr(), entity.getDescr());
        
        returnedEntity = enumerationDao.find("Does not Exist");
        assertTrue(false);
    }

    @Test
    public void testRemoveEnumeration() throws DoesNotExistException{
        EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name4");
        entity.setId("Key4");
                
        enumerationDao.persist(entity);
        
        enumerationDao.remove(entity);
        List<EnumerationEntity> list = enumerationDao.findAll();
        for(EnumerationEntity e: list){
        	assertTrue("EnumerationMetaEntity still exists after remove", !e.getId().equals("Key4"));
        }
        
        enumerationDao.find("Key4");
        assertTrue("EnumerationMetaEntity still exists after remove",  false);
    }
    
}
