package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lpr.service.decorators.LuiPersonRelationServiceValidationDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lpr-test-context.xml"})
public class TestLuiPersonRelationServiceImplRemote {
    private LuiPersonRelationServiceValidationDecorator lprService;

    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    @Autowired
    public void setLprService(LuiPersonRelationServiceValidationDecorator lprService) {
        this.lprService = lprService;
    }

    @Before
    public void setUp() {
        principalId = "123";
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testLprServiceSetup() {
        assertNotNull(lprService);
    }

    @Test
    public void testGetInitialValidStates() throws InvalidParameterException, MissingParameterException,
            DoesNotExistException, OperationFailedException {

        List<StateInfo> validStates = lprService.getInitialValidStates(
                LuiPersonRelationServiceConstants.STUDENT_COURSE_REGISTRATION_PROCESS_KEY, callContext);

        assertNotNull(validStates);
        assertEquals(1, validStates.size());

        StateInfo state = validStates.get(0);
        assertEquals(LuiPersonRelationServiceConstants.PLANNED_STATE_KEY, state.getKey());

        // assert that an invalid process throws the expected exception
        List<StateInfo> fakeValidStates = null;
        try {
            fakeValidStates = lprService.getInitialValidStates("bogusProcess", callContext);
            fail("Did not get an expected DoesNotExistException");
        } catch (DoesNotExistException e) {
            assertNull(fakeValidStates);
        }
    }

}
