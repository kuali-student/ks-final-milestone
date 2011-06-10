package org.kuali.student.r2.common.service.impl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

@Daos( { @Dao(value = "org.kuali.student.r2.common.dao.StateDao", testSqlFile = "classpath:ks-common.sql"),
	@Dao(value = "org.kuali.student.r2.common.dao.StateProcessDao"),
	@Dao(value = "org.kuali.student.r2.common.dao.StateProcessRelationDao")} )
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestStateServiceImpl  extends AbstractServiceTest{
	@Client(value = "org.kuali.student.r2.common.service.impl.StateServiceImpl")
	
	public StateService stateService;	
	public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testClient(){
    	assertNotNull(stateService);
    }
    
    @Test
    public void testGetState()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
    	StateInfo stateInfo = stateService.getState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
    	assertNotNull(stateInfo);
		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		
		try{
			StateInfo invalid = stateService.getState("testId", AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
			assertNull(invalid);
		}catch(DoesNotExistException ex){
			//expected
		}
    }
    
    @Test
    public void testGetStatesByProcess()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
    	List<StateInfo> stateInfo = stateService.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    	assertNotNull(stateInfo);
    	assertEquals(stateInfo.size(), 2);
    }
    
    @Test
    public void testGetProcessByKey()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
    	StateProcessInfo spInfo = stateService.getProcessByKey(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    	assertNotNull(spInfo);
		assertEquals(spInfo.getKey(), AtpServiceConstants.ATP_PROCESS_KEY);    	
    }
    
	@Test
	public void testGetInitialValidStates()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		List<StateInfo> stateInfo = stateService.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
		assertNotNull(stateInfo);
		assertEquals(stateInfo.size(), 1);
	}
	
	@Test
	public void testGetNextHappyState()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		StateInfo stateInfo = stateService.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
		assertNotNull(stateInfo);
		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
	}
}
