package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.classI.atp.dao.AtpDao;
import org.kuali.student.r2.core.classI.atp.model.AtpEntity;

@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestAtpDao extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.r2.core.classI.atp.dao.AtpDao", testSqlFile = "classpath:ks-atp.sql")
    private AtpDao dao;
    
    @Ignore
    @Test
    public void testGetAtp() {
        AtpEntity atp = dao.find("testAtpId1");
        assertNotNull(atp);
        assertEquals("testAtp1", atp.getName());         
        assertEquals("Desc", atp.getDescr().getPlain());   
    }
}
