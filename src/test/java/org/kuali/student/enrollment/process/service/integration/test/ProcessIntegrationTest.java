/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.kuali.student.enrollment.courseregistration.dto.CreditLoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.rules.credit.limit.CourseRegistrationServiceTypeStateConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * @author nwright
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-integration-test-context.xml"})
public class ProcessIntegrationTest {

    public ProcessIntegrationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    @Resource
    private CourseRegistrationService courseRegistrationService;

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    private ContextInfo getContextInfoAsOf12302011() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 11, 30);
        return getContextInfo(cal.getTime());
    }

    private ContextInfo getContextInfo(Date asOfDate) {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("testPrincipal1");
        contextInfo.setCurrentDate(asOfDate);
        return contextInfo;

    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private List<ValidationResultInfo> getErrorsOrWarnings(List<ValidationResultInfo> results) {
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();
        for (ValidationResultInfo vr : results) {
            if (vr.isError()) {
                errors.add(vr);
            } else if (vr.isWarn()) {
                errors.add(vr);
            }
        }
        return errors;
    }

    @Test
    public void testPhase1Case1IsAlive() throws Exception {
        System.out.println("Phase 1 case 1: is Alive");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibility(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016,
                getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testPhase1Case2IsDead() throws Exception {
        System.out.println("Phase 1 case 2: is dead");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibility(ProcessIntegrationTestConstants.PERSON_ID_KARA_STONE_2272,
                getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals(
                "A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.",
                results.get(0).getMessage());
    }

    @Test
    public void testPhase1Case3IsDeadShortCircuit() throws Exception {
        System.out.println("Phase 1 case 3: is dead short circuit");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_KARA_STONE_2272,
                ProcessIntegrationTestConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());

        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(2, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals(
                "A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.",
                results.get(0).getMessage());
        assertTrue(errors.get(1).isError());
        assertEquals(
                "You failed one or more of the basic eligibility checks",
                results.get(1).getMessage());
    }

    @Test
    public void testPhase1Case4TooEarly() throws Exception {
        System.out.println("Phase 1 case 4: Too Early");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessIntegrationTestConstants.SPRING_2012_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("Registration period for this term has not yet begun", errors.get(0).getMessage());
    }

    @Test
    public void testPhase1Case5TooLate() throws Exception {
        System.out.println("Phase 1 case 5: Too Late");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessIntegrationTestConstants.SPRING_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("Registration period for this term is closed", errors.get(0).getMessage());
    }

    @Test
    public void testPhase1Case6HasPaidLastTermsBill() throws Exception {
        System.out.println("Phase 1 case 6: Has Paid Last Term's Bill");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessIntegrationTestConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testPhase1Case7HasNotPaidLastTermsBill() throws Exception {
        System.out.println("Phase 1 case 7: Has Not Paid Last Term's Bill");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_CLIFFORD_RIDDLE_2397,
                ProcessIntegrationTestConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(2, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter",
                errors.get(0).getMessage());
        assertTrue(errors.get(1).isError());
        assertEquals("You have one or more holds that need to be cleared",
                errors.get(1).getMessage());
    }

    @Test
    public void testPhase1Case8WarningHasAnOverdueBook() throws Exception {
        System.out.println("Phase 1 case 8: Warning Has an Overdue Book");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005,
                ProcessIntegrationTestConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isWarn());
        assertFalse(errors.get(0).isError());
        assertEquals("Please note: you have an overdue library book", errors.get(0).getMessage());
    }

    @Test
    public void testPhase1Case9HasBothHolds() throws Exception {
        System.out.println("Phase 1 case 9: Has Both Holds");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166,
                ProcessIntegrationTestConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(3, errors.size());
        assertTrue(errors.get(0).isWarn());
        assertFalse(errors.get(0).isError());
        assertEquals("Please note: you have an overdue library book", errors.get(0).getMessage());
        assertTrue(errors.get(1).isError());
        assertEquals("You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter",
                errors.get(1).getMessage());
        assertTrue(errors.get(2).isError());
        assertEquals("You have one or more holds that need to be cleared",
                errors.get(2).getMessage());
    }

    @Test
    public void testPhase1Case10SummerOnlyStudentCannotRegister() throws Exception {
        System.out.println("Phase 1 case 10: Summer Only Student Cannot Register");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_AMBER_HOPKINS_2155,
                ProcessIntegrationTestConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("Summer only students cannot register for fall, winter or spring terms", errors.get(0).getMessage());
    }

    @Test
    public void testPhase1Case11SummerOnlyStudentCanRegisterBecauseItIsSummer() throws Exception {
        System.out.println("Phase 1 case 11: Summer Only Student Can Register Because it Is Summer");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_AMBER_HOPKINS_2155,
                ProcessIntegrationTestConstants.SUMMER_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testPhase1Case12TooEarlyButHasAnExemption() throws Exception {
        System.out.println("Phase 1 case 12: Too Early But Has An Exemption");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_JOHNNY_MANNING_2374,
                ProcessIntegrationTestConstants.SPRING_2012_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testPhase1Case13TooLateButHasAnExtensionExemption() throws Exception {
        System.out.println("Phase 1 case 13: Too Late But Has An Extension Exemption");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_EDDIE_PITTMAN_2406,
                ProcessIntegrationTestConstants.SPRING_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testPhase1Case14TooLateEvenWithExtensionExemption() throws Exception {
        System.out.println("Phase 1 case 14: Too Late Even With Extension Exemption");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessIntegrationTestConstants.PERSON_ID_TRACY_BURTON_2132,
                ProcessIntegrationTestConstants.SPRING_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());

        assertEquals("Registration period for this term is closed", errors.get(0).getMessage());
    }

    @Test
    public void testPhase2Case1NotRegisteredForAnythingSoFailsMinimumCredit() throws Exception {
        System.out.println("Phase 2 case 1: Not registered for anything so fails minimum credit");

        ContextInfo contextInfo = this.getContextInfoAsOf12302011();
        RegistrationRequestInfo req = new RegistrationRequestInfo();
        req.setRequestorId(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016);
        req.setTermId(ProcessIntegrationTestConstants.FALL_2011_TERM_KEY);
        req.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        req.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        req = this.courseRegistrationService.createRegistrationRequest(req.getTypeKey(),
                req,
                contextInfo);

        CreditLoadInfo load = this.courseRegistrationService.calculateCreditLoadForStudentRegistrationRequest(req.getId(),
                req.getRequestorId(), contextInfo);
        assertEquals(req.getRequestorId(), load.getStudentId());
        assertEquals(0, load.getCreditLoad().intValue());
        assertEquals(0, load.getAdditionalCredits().intValue());
        assertEquals(9, load.getCreditLimit().intValue());
        assertEquals(1, load.getCreditMinimum().intValue());
        System.out.println ("load=" + load);
        
        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.verifyRegistrationRequestForSubmission(req.getId(), contextInfo);
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
    }

    @Test
    public void testPhase2Case2OneCourseInRequestSoPassesMin() throws Exception {
        System.out.println("Phase 2 case 2: One Course in request os passes the min check");

        ContextInfo contextInfo = this.getContextInfoAsOf12302011();
        RegistrationRequestInfo req = new RegistrationRequestInfo();
        req.setRequestorId(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016);
        req.setTermId(ProcessIntegrationTestConstants.FALL_2011_TERM_KEY);
        req.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        req.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);

        RegistrationRequestItemInfo item = new RegistrationRequestItemInfo();
        item.setPersonId(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016);
        item.setTypeKey(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_ADD_TYPE_KEY);
        item.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        item.setRegistrationGroupId("ENGL101-FA2011");
        req.getRegistrationRequestItems().add(item);

        req = this.courseRegistrationService.createRegistrationRequest(req.getTypeKey(), req, contextInfo);

        
        CreditLoadInfo load = this.courseRegistrationService.calculateCreditLoadForStudentRegistrationRequest(req.getId(),
                req.getRequestorId(), contextInfo);
        assertEquals(req.getRequestorId(), load.getStudentId());
        assertEquals(0, load.getCreditLoad().intValue());
        assertEquals(3, load.getAdditionalCredits().intValue());
        assertEquals(9, load.getCreditLimit().intValue());
        assertEquals(1, load.getCreditMinimum().intValue());
        System.out.println ("load=" + load);
        
        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.verifyRegistrationRequestForSubmission(req.getId(), contextInfo);
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }
}
