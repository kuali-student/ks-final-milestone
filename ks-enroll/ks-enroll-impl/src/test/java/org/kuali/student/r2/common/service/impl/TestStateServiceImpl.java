package org.kuali.student.r2.common.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestStateServiceImpl {
	
    @Autowired
    private StateService stateService;

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
