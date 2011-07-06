package org.kuali.student.enrollment.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpTypeEntity;

@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestAtpAtpRelationDao extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao", testSqlFile = "classpath:ks-atp.sql")
    private AtpAtpRelationDao dao;
    
    @Test 
    public void testGetAtpAtpRelation()
    {
        AtpAtpRelationEntity atpRel = dao.find("ATPATPREL-1");
        assertNotNull(atpRel);
        AtpTypeEntity type = atpRel.getAtpType();
        assertEquals(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, type.getName());      
    }
    @Ignore
    @Test
    public void testGetAtpAtpRelationsByAtp()
    {
    	//id is atp
        List<AtpAtpRelationEntity> rels = dao.getAtpAtpRelationsByAtp("testAtpId1");
        assertNotNull(rels);
        assertEquals(4, rels.size());
        for(AtpAtpRelationEntity rel : rels){
        	assertEquals("testAtpId1", rel.getAtp().getId());
        }
        // testAtpId2 is relatedAtp's ID
        rels = dao.getAtpAtpRelationsByAtp("testAtpId2");
        assertNotNull(rels);
        assertEquals(1, rels.size());
    }
    
    @Test
    public void testGetAtpAtpRelationsByAtp2()
    {
    	//id is either atp or relatedAtp
        List<AtpAtpRelationEntity> rels = dao.getAtpAtpRelationsByAtp("testTermId1");
        assertNotNull(rels);
        assertTrue(rels.size() == 2);
        for(AtpAtpRelationEntity rel : rels){
        	String atpKey = rel.getAtp().getId();
        	String relatedAtpKey = rel.getRelatedAtp().getId();
        	assertTrue(atpKey.equals("testTermId1") || relatedAtpKey.equals("testTermId1"));
        }
    }
    
    @Test 
    public void testGetAtpAtpRelationsByAtpAndRelationType(){
    	 List<AtpAtpRelationEntity> rels = dao.getAtpAtpRelationsByAtpAndRelationType("testAtpId1", AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
         assertNotNull(rels);
         assertEquals(1, rels.size());
    }
}
