/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

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
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * @author nwright
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ProcessPocKrmsIntegrationTest-context.xml"})
public class ProcessPocKrmsIntegrationTest {

    public ProcessPocKrmsIntegrationTest() {
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
    public void testCase1IsAlive() throws Exception {
        System.out.println("case 1: is Alive");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibility(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testCase2IsDead() throws Exception {
        System.out.println("case 2: is dead");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibility(ProcessPocConstants.PERSON_ID_KARA_STONE_2272,
                getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals(
                "A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.",
                results.get(0).getMessage());
    }

    @Test
    public void testCase3IsDeadShortCircuit() throws Exception {
        System.out.println("case 3: is dead short circuit");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_KARA_STONE_2272,
                ProcessPocConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());

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
    public void testCase4TooEarly() throws Exception {
        System.out.println("case 4: Too Early");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessPocConstants.SPRING_2012_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("Registration period for this term has not yet begun", errors.get(0).getMessage());
    }

    @Test
    public void testCase5TooLate() throws Exception {
        System.out.println("case 5: Too Late");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessPocConstants.SPRING_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("Registration period for this term is closed", errors.get(0).getMessage());
    }

    @Test
    public void testCase6HasPaidLastTermsBill() throws Exception {
        System.out.println("case 6: Has Paid Last Term's Bill");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessPocConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testCase7HasNotPaidLastTermsBill() throws Exception {
        System.out.println("case 7: Has Not Paid Last Term's Bill");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_CLIFFORD_RIDDLE_2397,
                ProcessPocConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
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
    public void testCase8WarningHasAnOverdueBook() throws Exception {
        System.out.println("case 8: Warning Has an Overdue Book");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BETTY_MARTIN_2005,
                ProcessPocConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isWarn());
        assertFalse(errors.get(0).isError());
        assertEquals("Please note: you have an overdue library book", errors.get(0).getMessage());
    }

    @Test
    public void testCase9HasBothHolds() throws Exception {
        System.out.println("case 9: Has Both Holds");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166,
                ProcessPocConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
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
    public void testCase10SummerOnlyStudentCannotRegister() throws Exception {
        System.out.println("case 10: Summer Only Student Cannot Register");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_AMBER_HOPKINS_2155,
                ProcessPocConstants.FALL_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());
        assertEquals("Summer only students cannot register for fall, winter or spring terms", errors.get(0).getMessage());
    }

    @Test
    public void testCase11SummerOnlyStudentCanRegisterBecauseItIsSummer() throws Exception {
        System.out.println("case 11: Summer Only Student Can Register Because it Is Summer");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_AMBER_HOPKINS_2155,
                ProcessPocConstants.SUMMER_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testCase12TooEarlyButHasAnExemption() throws Exception {
        System.out.println("case 12: Too Early But Has An Exemption");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_JOHNNY_MANNING_2374,
                ProcessPocConstants.SPRING_2012_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testCase13TooLateButHasAnExtensionExemption() throws Exception {
        System.out.println("case 13: Too Late But Has An Extension Exemption");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_EDDIE_PITTMAN_2406,
                ProcessPocConstants.SPRING_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }

    @Test
    public void testCase14TooLateEvenWithExtensionExemption() throws Exception {
        System.out.println("case 14: Too Late Even With Extension Exemption");

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_TRACY_BURTON_2132,
                ProcessPocConstants.SPRING_2011_TERM_KEY, getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).isError());

        assertEquals("Registration period for this term is closed", errors.get(0).getMessage());
    }

//    @Test
    public void testPhase2Case1DoesNotExceedCreditLimit() throws Exception {
        System.out.println("Phase 2 case 1: Does Not Exceed Credit Limit");

        RegistrationRequestInfo orig = new RegistrationRequestInfo();
        orig.setRequestorId(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016);
        orig.setTermId(ProcessPocConstants.FALL_2011_TERM_KEY);
        orig.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        orig.setStateKey(LprServiceConstants.LPRTRANS_NEW_STATE_KEY);
        RegistrationRequestInfo request = this.courseRegistrationService.createRegistrationRequest(orig.getTypeKey(), orig,
                getContextInfoAsOf12302011());

        List<ValidationResultInfo> results = null;
        results = courseRegistrationService.verifyRegistrationRequestForSubmission(request.getId(), getContextInfoAsOf12302011());
        List<ValidationResultInfo> errors = getErrorsOrWarnings(results);
        assertEquals(0, errors.size());
    }
}
