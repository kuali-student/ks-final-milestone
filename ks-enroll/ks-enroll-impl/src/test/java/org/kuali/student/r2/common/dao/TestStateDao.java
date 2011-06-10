package org.kuali.student.r2.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.model.StateProcessEntity;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestStateDao extends AbstractTransactionalDaoTest{
	@Dao(value = "org.kuali.student.r2.common.dao.StateDao", testSqlFile = "classpath:ks-common.sql")
    private StateDao dao;

	@Test
    public void testGetState(){
		try{
		StateEntity state = dao.find(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		assertNotNull(state);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Test
    public void testGetStateByKeynProcess(){
		StateEntity state = dao.getState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		assertNotNull(state);
		assertEquals(state.getId(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		assertEquals(state.getProcessKey(), AtpServiceConstants.ATP_PROCESS_KEY);
	}

	@Test
	public void testGetStatesByProcess(){
		List<StateEntity> states = dao.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY);
		assertNotNull(states);
		assertEquals(states.size(), 2);
	}
}
