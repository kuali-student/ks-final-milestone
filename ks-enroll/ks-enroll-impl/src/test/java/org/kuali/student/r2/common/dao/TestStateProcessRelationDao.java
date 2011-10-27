package org.kuali.student.r2.common.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.model.StateProcessRelationEntity;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

@Ignore
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestStateProcessRelationDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.r2.common.dao.StateProcessRelationDao", testSqlFile = "classpath:ks-common.sql")
    private StateProcessRelationDao dao;
	
	@Test	
	public void testGetStateProcessRelation(){
		StateProcessRelationEntity sp = dao.find("PROCESS-1");
		assertNotNull(sp);
	}
	
	@Test
	public void testGetInitialValidStates(){
		List<StateEntity> states = dao.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY);
		assertNotNull(states);
		assertEquals(states.size(), 1);
	}
	
	@Test
	public void testGetNextHappyState(){
		StateEntity state = dao.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		assertNotNull(state);
		assertEquals(state.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
	}
}
