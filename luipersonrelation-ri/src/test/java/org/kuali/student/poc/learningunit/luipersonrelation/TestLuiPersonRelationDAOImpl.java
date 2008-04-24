package org.kuali.student.poc.learningunit.luipersonrelation;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;

public class TestLuiPersonRelationDAOImpl extends AbstractTransactionalDaoTest {
    @Dao("org.kuali.student.poc.learningunit.luipersonrelation.dao.impl.LuiPersonRelationDAOImpl")
    public LuiPersonRelationDAO dao;

    @Before
    public void before() {
    }
    
    @Test
    public void testCreateLuiPersonRelation() {
        
    }
}
