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
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationEntity;

/**
 * Enumeration Dao test class.
 *
 * @Version 2.0
 */
@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestEnumerationDao extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumerationDao", testSqlFile = "classpath:ks-em.sql")
    public EnumerationDao enumerationDao;

    @Test
    public void testFindEnumerations(){
        List<EnumerationEntity> list = enumerationDao.findAll();
        
        assertEquals(11, list.size());
        
        EnumerationEntity returnedEntity = list.get(0);
        
        assertEquals(returnedEntity.getId(), "kuali.lu.subjectArea");
        assertEquals(returnedEntity.getName(), "Subject Area Enumeration");
        assertEquals(returnedEntity.getDescrPlain(), "subjectArea.descr");
    }    

    @Test
    public void testFindEnumeration() throws DoesNotExistException{
        EnumerationEntity existingEntity = enumerationDao.find("kuali.lu.subjectArea");
        
    	EnumerationEntity entity = new EnumerationEntity();
        entity.setName("Name3");
        entity.setEnumerationState(existingEntity.getEnumerationState());
        entity.setEnumerationType(existingEntity.getEnumerationType());
        entity.setId("Key3");
        entity.setCreateId("SYSTEM");
        entity.setCreateTime(new Date());

        entity.setDescrPlain("desc3");
        
        enumerationDao.persist(entity);
        
        EnumerationEntity returnedEntity = enumerationDao.find("Key3");
        assertEquals(returnedEntity.getName(), entity.getName());
        assertEquals(returnedEntity.getId(), entity.getId());
        assertEquals(returnedEntity.getDescrPlain(), entity.getDescrPlain());
        
        returnedEntity = enumerationDao.find("Does not Exist");
        assertNull(returnedEntity);
    }

    @Test
    public void testRemoveEnumeration() throws DoesNotExistException{
        EnumerationEntity enumerationState = enumerationDao.find("kuali.state");
        assertNotNull(enumerationState);
        enumerationDao.remove(enumerationState);
        enumerationState = enumerationDao.find("state.descr");
        assertNull(enumerationState);
    }
    
}
