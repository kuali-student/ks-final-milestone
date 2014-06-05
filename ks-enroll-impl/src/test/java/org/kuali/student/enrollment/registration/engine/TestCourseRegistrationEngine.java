package org.kuali.student.enrollment.registration.engine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationResponseItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationResponseResult;
import org.kuali.student.enrollment.registration.client.service.impl.CourseRegistrationClientServiceImpl;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by swedev on 4/7/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:courseregistration-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = false)
public class TestCourseRegistrationEngine {

    private static final String LETTER_GRADE = "kuali.resultComponent.grade.letter";
    private static final String SPRING_2012_TERM = "kuali.atp.2012Spring";
    private ContextInfo CONTEXT;

    @Resource(name = "lrcService")
    private LRCService lrcService;

    @Resource(name = "courseRegistrationClientServiceImpl")
    private CourseRegistrationClientServiceImpl courseRegistrationClientService;

    @Resource(name = "CourseRegistrationService")
    private CourseRegistrationService courseRegistrationService;

    @Resource(name= "luiServiceDataLoader")
    private LuiServiceDataLoader luiServiceDataLoader;

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;


    @Transactional
    @Before
    public void setUp() throws Exception {
        new MockLrcTestDataLoader(this.lrcService).loadData();
        luiServiceDataLoader.loadData();


        CONTEXT = ContextUtils.createDefaultContextInfo();
        CONTEXT.setPrincipalId("admin");
        CONTEXT.setCurrentDate(new Date());
        assertNotNull(courseRegistrationService);
    }

    /**
     * This tests creating an add reg request and submitting it to the registration engine. It then checks to make sure
     * the request was successfully processed.
     *
     * @throws Exception
     */
    @Test
    public void testSimpleCourseRegistration() throws Exception {

        RegistrationRequestInfo request = buildRegRequestsFor("admin");
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);

        System.out.println("Submitting: " + requestResult.getId());

        RegistrationRequestInfo requestInfo = courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        waitFor(3000);    // wait for reg engine to process

        // get status of reg request
        RegistrationResponseResult registrationResponseResult = courseRegistrationClientService.getRegistrationStatusLocal(requestInfo.getId(), CONTEXT);

        System.out.println(registrationResponseResult);

        // make sure the request and request itemes are successful
        assertEquals(registrationResponseResult.getState(), LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY );
        for(RegistrationResponseItemResult responseItemInfo : registrationResponseResult.getResponseItemResults()){
            assertEquals(responseItemInfo.getState(), LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY);
        }

    }

    /**
     * The registration engine is asynchronous so there are times when we would want to wait.
     *
     * In production we're polling for responses.
     * @param waitTimeMS
     */
    private void waitFor(long waitTimeMS){
        try {
            System.err.println("Waiting for [" + waitTimeMS + "ms ]");
            Thread.sleep(waitTimeMS);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    protected RegistrationRequestInfo buildRegRequestsFor(String personId)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException,
            DataValidationErrorException, AlreadyExistsException {
        RegistrationRequestItemInfo itemInfo = new RegistrationRequestItemInfo();
        itemInfo.setPersonId(personId);
        itemInfo.setRegistrationGroupId(LuiServiceDataLoader.RG_ID_1);    // this must match a value from LuiServiceDataLoader.java
        itemInfo.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        itemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        itemInfo.setCredits(new KualiDecimal("3.5"));
        itemInfo.setGradingOptionId(LETTER_GRADE); // Fill in

        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setTermId(SPRING_2012_TERM);
        request.setRequestorId(personId);
        request.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        request.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        // Add the item
        request.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        request.getRegistrationRequestItems().add(itemInfo);

        return request;
                           /*
        // Now create and test
        CourseRegistrationService courseRegistrationService =
                this.getCourseRegistrationService();
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);
        regRequestIds.add(requestResult.getId());
        System.err.println("DONE");
        */
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
