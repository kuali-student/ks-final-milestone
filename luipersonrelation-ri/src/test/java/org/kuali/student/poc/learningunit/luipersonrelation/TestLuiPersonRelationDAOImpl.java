package org.kuali.student.poc.learningunit.luipersonrelation;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public class TestLuiPersonRelationDAOImpl extends AbstractTransactionalDaoTest {
    //@Dao(value = "org.kuali.student.poc.learningunit.luipersonrelation.dao.impl.LuiPersonRelationDAOImpl", testDataFile = "classpath:test-beans.xml")
    @Dao("org.kuali.student.poc.learningunit.luipersonrelation.dao.impl.LuiPersonRelationDAOImpl")
    public LuiPersonRelationDAO dao;

    public static final String person_id = "Person 1";
    public static final String lpr_id1 = "11223344-1122-1122-1111-000000000000";
    public static final String lpr_id2 = "11223344-1122-1122-1111-000000000001";
    
    @Test
    public void testCreateLuiPersonRelation() {
        LuiPersonRelation lpr = new LuiPersonRelation();
        lpr.setEffectiveEndDate(new Date());
        lpr.setEffectiveStartDate(new Date());
        lpr.setLuiId("anything1");
        lpr.setLuiPersonRelationType("anything3");
        lpr.setPersonId("anything2");
        lpr.setRelationState("anything4");

        dao.createLuiPersonRelation(lpr);
        
        assertEquals(em.find(LuiPersonRelation.class, lpr.getId()), lpr);
       
    }
    
    @Test
    public void testFindLuiPersonRelations(){
        
        
    }
}
