package org.kuali.student.enrollment.registration.engine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationResponseItemResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationResponseResult;
import org.kuali.student.enrollment.registration.client.service.impl.CourseRegistrationClientServiceImpl;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
public class CourseRegistrationEngineIT {

    public static final String TRUE = "true";
    public static final String VALIDATION_EXCEPTION = "validationException";
    public static final String RESULT_EXCEPTION = "resultException";
    public static final String RESULT_ITEM_EXCEPTION = "resultItemException";

    public static final String[] EXCEPTIONS = {VALIDATION_EXCEPTION, RESULT_EXCEPTION, RESULT_ITEM_EXCEPTION};

    private static final String LETTER_GRADE = "kuali.resultComponent.grade.letter";
    private static final String SPRING_2012_TERM = "kuali.atp.2012Spring";
    private static final String ADMIN = "admin";
    private static final String ADMINENTITYID = "1100";
    private ContextInfo CONTEXT;

    private static boolean FIRST_TEST = true;
    private static int WAIT_TIME = 3000; // 3 seconds

    @Resource(name = "lrcService")
    private LRCService lrcService;

    @Resource(name = "courseRegistrationClientServiceImpl")
    private CourseRegistrationClientServiceImpl courseRegistrationClientService;

    @Resource(name = "CourseRegistrationService")
    private CourseRegistrationService courseRegistrationService;

    @Resource(name= "luiServiceDataLoader")
    private LuiServiceDataLoader luiServiceDataLoader;

    @Resource(name = "kimIdentityService")
    private IdentityService identityService;

    @Transactional
    @Before
    public void setUp() throws Exception {

        if (FIRST_TEST) {
            new MockLrcTestDataLoader(this.lrcService).loadData();
            luiServiceDataLoader.loadData();
            FIRST_TEST=false;
        }

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

        RegistrationRequestInfo request = buildRegRequestsFor(ADMIN, ADMINENTITYID);
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);

        System.out.println("Submitting: " + requestResult.getId());

        RegistrationRequestInfo requestInfo = courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        waitFor(WAIT_TIME);    // wait for reg engine to process

        // get status of reg request
        RegistrationResponseResult registrationResponseResult = courseRegistrationClientService.getRegistrationStatusLocal(requestInfo.getId(), CONTEXT);

        System.out.println(registrationResponseResult);

        // make sure the request and request items are successful
        assertEquals(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY, registrationResponseResult.getState());
        for(RegistrationResponseItemResult responseItemInfo : registrationResponseResult.getResponseItemResults()){
            assertEquals(LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY, responseItemInfo.getState());
        }

    }

    /**
     * This tests an exception being thrown during registration validation.
     *
     * @throws Exception
     */
    @Test
    public void testValidationException() throws Exception {

        CONTEXT.getAttributes().add(new AttributeInfo(VALIDATION_EXCEPTION, TRUE));

        RegistrationRequestInfo request = buildRegRequestsFor(ADMIN, ADMINENTITYID);
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);

        System.out.println("Submitting: " + requestResult.getId());

        RegistrationRequestInfo requestInfo = courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        waitFor(WAIT_TIME);    // wait for reg engine to process

        // get status of reg request
        RegistrationResponseResult registrationResponseResult = courseRegistrationClientService.getRegistrationStatusLocal(requestInfo.getId(), CONTEXT);

        System.out.println(registrationResponseResult);

        // make sure the request and request items have failed
        assertEquals(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY, registrationResponseResult.getState());
        for(RegistrationResponseItemResult responseItemInfo : registrationResponseResult.getResponseItemResults()){
            assertEquals(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY, responseItemInfo.getState());
        }

    }

    /**
     * This tests a result-level exception being thrown during registration update (before the split or after the join).
     *
     * @throws Exception
     */
    @Test
    public void testResultException() throws Exception {

        CONTEXT.getAttributes().add(new AttributeInfo(RESULT_EXCEPTION, TRUE));

        RegistrationRequestInfo request = buildRegRequestsFor(ADMIN, ADMINENTITYID);
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);

        System.out.println("Submitting: " + requestResult.getId());

        RegistrationRequestInfo requestInfo = courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        waitFor(WAIT_TIME);    // wait for reg engine to process

        // get status of reg request
        RegistrationResponseResult registrationResponseResult = courseRegistrationClientService.getRegistrationStatusLocal(requestInfo.getId(), CONTEXT);

        System.out.println(registrationResponseResult);

        // make sure the request and request items have failed
        assertEquals(LprServiceConstants.LPRTRANS_FAILED_STATE_KEY, registrationResponseResult.getState());
        for(RegistrationResponseItemResult responseItemInfo : registrationResponseResult.getResponseItemResults()){
            assertEquals(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY, responseItemInfo.getState());
        }

    }

    /**
     * This tests a result item-level exception being thrown during registration update.
     *
     * @throws Exception
     */
    @Test
    public void testResultItemException() throws Exception {

        CONTEXT.getAttributes().add(new AttributeInfo(RESULT_ITEM_EXCEPTION, TRUE));

        RegistrationRequestInfo request = buildRegRequestsFor(ADMIN, ADMINENTITYID);
        RegistrationRequestInfo requestResult =
                courseRegistrationService.createRegistrationRequest(request.getTypeKey(),
                        request, CONTEXT);

        System.out.println("Submitting: " + requestResult.getId());

        RegistrationRequestInfo requestInfo = courseRegistrationService.submitRegistrationRequest(requestResult.getId(), CONTEXT);

        waitFor(WAIT_TIME);    // wait for reg engine to process

        // get status of reg request
        RegistrationResponseResult registrationResponseResult = courseRegistrationClientService.getRegistrationStatusLocal(requestInfo.getId(), CONTEXT);

        System.out.println(registrationResponseResult);

        // make sure the request and request items have failed
        assertEquals(LprServiceConstants.LPRTRANS_SUCCEEDED_STATE_KEY, registrationResponseResult.getState());
        for(RegistrationResponseItemResult responseItemInfo : registrationResponseResult.getResponseItemResults()){
            assertEquals(LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY, responseItemInfo.getState());
        }

    }

    /**
     * The registration engine is asynchronous so there are times when we would want to wait.
     *
     * In production we're polling for responses.
     * @param waitTimeMS the time to wait
     */
    private void waitFor(long waitTimeMS){
        try {
            System.err.println("Waiting for [" + waitTimeMS + "ms ]");
            Thread.sleep(waitTimeMS);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    protected RegistrationRequestInfo buildRegRequestsFor(String principalId, String personId)
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
        itemInfo.setRequestedEffectiveDate(new Date());

        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setTermId(SPRING_2012_TERM);
        request.setRequestorId(principalId);
        request.setTypeKey(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY);
        request.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        // Add the item
        request.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        request.getRegistrationRequestItems().add(itemInfo);

        return request;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
