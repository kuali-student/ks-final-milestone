package org.kuali.student.r2.common.dao;

import org.kuali.student.r2.core.class1.state.dao.StateLifecycleRelationDao;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.class1.state.model.LifecycleEntity;
import org.kuali.student.r2.core.class1.state.model.StateLifecycleRelationEntity;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestStateProcessRelationDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.r2.core.class1.state.dao.StateLifecycleRelationDao", testSqlFile = "classpath:ks-common.sql")
    private StateLifecycleRelationDao dao;
	
	@Test	
	public void testGetStateProcessRelation(){
		StateLifecycleRelationEntity spr = dao.find("PROCESS-1");
		assertNotNull(spr);
        LifecycleEntity sp = spr.getProcess();
        assertNotNull(sp);
        assertEquals(AtpServiceConstants.ATP_PROCESS_KEY, sp.getId());
        StateEntity state = spr.getPriorState();
        assertNull("priorState should be null", state);
        state = spr.getNextState();
        assertNotNull("nextState should not be null", state);
        assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, state.getId());
	}
	
	@Test
	public void testGetInitialValidStates(){
		List<StateEntity> states = dao.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY);
		assertNotNull(states);
		assertEquals(1, states.size());
        assertEquals(AtpServiceConstants.ATP_PROCESS_KEY, states.get(0).getProcessKey());
        assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, states.get(0).getId());
	}
	
	@Test
	public void testGetNextHappyState(){
		StateEntity state = dao.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		assertNotNull(state);
		assertEquals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, state.getId());
	}
}
