package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationEntity;

@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestAtpAtpRelationDao extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao", testSqlFile = "classpath:ks-atp.sql")
    private AtpAtpRelationDao dao;

    @Test
    public void testGetAtpAtpRelation()
    {
        AtpAtpRelationEntity atpRel = dao.find("ATPATPREL-2");
        assertNotNull(atpRel);
        String atpType = atpRel.getAtpType();
        assertNotNull(atpType);
        assertEquals(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, atpType);
    }

    @Test
    public void testGetAtpAtpRelationsByAtp()
    {
        //id is atp
        List<AtpAtpRelationEntity> rels = dao.getAtpAtpRelationsByAtp("testAtpId1");
        assertNotNull(rels);
        assertEquals(3, rels.size());
        for (AtpAtpRelationEntity rel : rels) {
            assertEquals("testAtpId1", rel.getAtp().getId());
        }
        // testAtpId2 is relatedAtp's ID
        rels = dao.getAtpAtpRelationsByAtp("testAtpId2");
        assertNotNull(rels);
        assertEquals(0, rels.size());
    }

    @Test
    public void testGetAtpAtpRelationsByAtp2()
    {
        //id is either atp or relatedAtp
        List<AtpAtpRelationEntity> rels = dao.getAtpAtpRelationsByAtp("testTermId1");
        assertNotNull(rels);
        assertEquals(2, rels.size());
        for (AtpAtpRelationEntity rel : rels) {
            String atpId = rel.getAtp().getId();
            String relatedAtpId = rel.getRelatedAtp().getId();
            assertTrue(atpId.equals("testTermId1") || relatedAtpId.equals("testTermId1"));
        }
    }

    @Test
    public void testGetAtpAtpRelationsByAtpAndRelationType()
    {
        List<AtpAtpRelationEntity> rels =
                dao.getAtpAtpRelationsByAtpAndRelationType("testAtpId1",
                        AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY);
        assertNotNull(rels);
        assertEquals(3, rels.size());
    }
}