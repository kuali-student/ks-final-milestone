package org.kuali.student.r2.common.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;
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

    public ContextInfo callContext = null;


    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testClient(){
    	assertNotNull(stateService);
    }
    
    @Test
    public void testGetState()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	StateInfo stateInfo = stateService.getState(AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
    	assertNotNull(stateInfo);
		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
		
		try{
			StateInfo invalid = stateService.getState("invalid.state.key", callContext);
			assertNull(invalid);
		}catch(DoesNotExistException ex){
			//expected
		}
    }
    
    @Test
    public void testGetStatesByProcess()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	List<StateInfo> stateInfo = stateService.getStatesByLifecycle(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    	assertNotNull(stateInfo);
    	assertEquals(stateInfo.size(), 2);
    }
    
    @Test
    public void testGetProcessByKey()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	LifecycleInfo spInfo = stateService.getLifecycle(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    	assertNotNull(spInfo);
		assertEquals(spInfo.getKey(), AtpServiceConstants.ATP_PROCESS_KEY);    	
    }
    
//	@Test
//	public void testGetInitialValidStates()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
//		List<StateInfo> stateInfo = stateService.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
//		assertNotNull(stateInfo);
//		assertEquals(stateInfo.size(), 1);
//	}
//	
//	@Test
//	public void testGetNextHappyState()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
//		StateInfo stateInfo = stateService.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
//		assertNotNull(stateInfo);
//		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
//	}
}
