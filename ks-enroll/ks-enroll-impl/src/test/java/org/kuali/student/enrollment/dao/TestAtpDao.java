package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.atp.dao.AtpDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAttributeEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpRichTextEntity;

@Ignore
@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestAtpDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpDao", testSqlFile = "classpath:ks-atp.sql")
    private AtpDao dao;
    
    @Test
    public void testGetAtp() {
        AtpEntity atp = dao.find("testAtpId1");
        assertNotNull(atp);
        assertEquals("testAtp1", atp.getName());         
        assertEquals("Desc 101", atp.getDescr().getPlain());   
    }
    
    @Test
    public void testCreateAtp() 
    {
        AtpEntity existingEntity = dao.find("testAtpId1");
        
        AtpEntity atp = new AtpEntity();
        atp.setName("atpTest");
        atp.setDescr(new AtpRichTextEntity("plain", "formatted"));
        atp.setAtpState(existingEntity.getAtpState());
        atp.setAtpType(existingEntity.getAtpType());
        AtpAttributeEntity attr = new AtpAttributeEntity();
        attr.setKey("CredentialProgramType");
        attr.setValue("kuali.lu.type.credential.Baccalaureate");
        attr.setOwner(atp);
        atp.getAttributes().add(attr);
        dao.persist(atp);
        assertNotNull(atp.getId());
        
        AtpEntity atp2 = dao.find(atp.getId());
        assertEquals("atpTest", atp2.getName());         
        assertEquals("plain", atp2.getDescr().getPlain());   
        assertEquals(1, atp2.getAttributes().size());
        assertEquals("kuali.lu.type.credential.Baccalaureate", atp2.getAttributes().get(0).getValue());
    }
    
    @Test
    public void testDeleteAtp() 
    {
        AtpEntity atp = dao.find("testAtpId2");
        assertNotNull(atp);
        dao.remove(atp);
        atp = dao.find("testAtpId2");
        assertNull(atp);
    }
    
    @Test
    public void testUpdateAtp() 
    {
        AtpEntity atp = dao.find("testAtpId2");
        assertNotNull(atp);
        AtpAttributeEntity attr = new AtpAttributeEntity("foo", "bar");
        attr.setOwner(atp);
        atp.getAttributes().add(attr);
        dao.update(atp);
        
        AtpEntity atp2 = dao.find("testAtpId2");
        assertNotNull(atp2);
        assertEquals(1, atp2.getAttributes().size());
        assertEquals("bar", atp2.getAttributes().get(0).getValue());
    }
}
