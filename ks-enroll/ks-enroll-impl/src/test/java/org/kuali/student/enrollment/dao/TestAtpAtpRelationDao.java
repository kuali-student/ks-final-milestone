package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationTypeEntity;

@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestAtpAtpRelationDao extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao", testSqlFile = "classpath:ks-atp.sql")
    AtpAtpRelationDao dao;
    
    @Test 
    public void testGetAtpAtpRelation()
    {
        AtpAtpRelationEntity atpRel = dao.find("ATPATPREL-1");
        assertNotNull(atpRel);
        AtpAtpRelationTypeEntity type = atpRel.getAtpAtpRelationType();
        assertEquals("kuali.atp.atp.relation.includes", type.getName());      
    }
    
    @Test
    public void testGetAtpAtpRelationsByAtp()
    {
        List<AtpAtpRelationEntity> rels = dao.getAtpAtpRelationsByAtp("testAtpId1");
        assertNotNull(rels);
        assertEquals("testAtpId1", rels.get(0).getAtp().getId());
    }
}
