/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.process.poc;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.enrollment.class2.acal.service.assembler.AcademicCalendarAssembler;
import org.kuali.student.enrollment.class2.acal.service.assembler.TermAssembler;
import org.kuali.student.enrollment.class2.acal.service.impl.AcademicCalendarServiceImpl;
import org.kuali.student.enrollment.classI.hold.mock.HoldServiceMockImpl;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationServiceMockImpl;
import org.kuali.student.process.poc.evaluator.HoldCheckEvaluator;
import org.kuali.student.process.poc.evaluator.MilestoneCheckEvaluator;
import org.kuali.student.process.poc.evaluator.RegistrationProcessEvaluator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.classI.atp.mock.AtpServiceMockImpl;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.exemption.service.ExemptionServiceMockImpl;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.service.ProcessServiceMockImpl;

/**
 *
 * @author nwright
 */
public class ProcessPocJavaIntegrationTest {

    public ProcessPocJavaIntegrationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    private CourseRegistrationService service = null;

    @Before
    public void setUp() {
        CourseRegistrationServiceProcessCheckDecorator decorator = new CourseRegistrationServiceProcessCheckDecorator();
        decorator.setNextDecorator(new CourseRegistrationServiceMockImpl());

        HoldService holdService = new ProcessPocHoldServiceDecorator(new HoldServiceMockImpl());
        ExemptionService exemptionService = new ProcessPocExemptionServiceDecorator(new ExemptionServiceMockImpl());
        AtpService atpService = new ProcessPocAtpServiceDecorator(new AtpServiceMockImpl());
        AcademicCalendarServiceImpl acalImpl = new AcademicCalendarServiceImpl();
        acalImpl.setAtpService(atpService);
        acalImpl.setAcalAssembler(new AcademicCalendarAssembler());
        acalImpl.setTermAssembler(new TermAssembler());
        PopulationService populationService = new ProcessPocPopulationServiceMockImpl();

        RegistrationProcessEvaluator evaluator = new RegistrationProcessEvaluator();
        evaluator.setPopulationService(populationService);
        evaluator.setProcessService(new ProcessPocProcessServiceDecorator(new ProcessServiceMockImpl()));
        evaluator.setAcalService(acalImpl);
        evaluator.setExemptionService(exemptionService);

        HoldCheckEvaluator holdCheckEvaluator = new HoldCheckEvaluator();
        holdCheckEvaluator.setAtpService(atpService);
        holdCheckEvaluator.setHoldService(holdService);
        evaluator.setHoldCheckEvaluator(holdCheckEvaluator);

        MilestoneCheckEvaluator milestoneCheckEvaluator = new MilestoneCheckEvaluator();
        evaluator.setMilestoneCheckEvaluator(milestoneCheckEvaluator);

        decorator.setProcessEvaluator(evaluator);
        service = decorator;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCase1IsAlive() throws Exception {
        System.out.println("case 1: is Alive");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibility(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016, context);
        assertEquals(0, results);
    }

    @Test
    public void testCase2IsDead() throws Exception {
        System.out.println("case 2: is dead");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibility(ProcessPocConstants.PERSON_ID_KARA_STONE_2272, context);
        assertEquals(1, results);
        assertTrue(results.get(0).getIsError());
        assertEquals("A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.", results.get(0).getMessage());
    }

    @Test
    public void testCase3IsDeadShortCircuit() throws Exception {
        System.out.println("case 3: is dead short circuit");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_KARA_STONE_2272,
                ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsError());
        assertEquals("A key piece of data is wrong on your biographic record.  Please come to the Registrar's office to clear it up.", results.get(0).getMessage());
    }

    @Test
    public void testCase4TooEarly() throws Exception {
        System.out.println("case 4: Too Early");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessPocConstants.SPRING_2012_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsError());
        assertEquals("Registration period for this term has not yet begun", results.get(0).getMessage());
    }

    @Test
    public void testCase5TooLate() throws Exception {
        System.out.println("case 5: Too Late");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessPocConstants.SPRING_2011_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsError());
        assertEquals("Registration period for this term is closed", results.get(0).getMessage());
    }

    @Test
    public void testCase6HasPaidLastTermsBill() throws Exception {
        System.out.println("case 6: Has Paid Last Term's Bill");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BARBARA_HARRIS_2016,
                ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(0, results.size());
    }
    
    @Test
    public void testCase7HasNotPaidLastTermsBill() throws Exception {
        System.out.println("case 7: Has Not Paid Last Term's Bill");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_CLIFFORD_RIDDLE_2397,
                ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsError());
        assertEquals("You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter", results.get(0).getMessage());
    }
    
        
    @Test
    public void testCase8HasAnOverdueBook() throws Exception {
        System.out.println("case 8: Has an Overdue Book");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_BETTY_MARTIN_2005,
                ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsWarn());        
        assertFalse(results.get(0).getIsError());
        assertEquals("Please note: you have an overdue library book", results.get(0).getMessage());
    }
    
    
        
    @Test
    public void testCase9HasBothHolds() throws Exception {
        System.out.println("case 9: Has Both Holds");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166,
                ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(2, results.size());
        assertTrue(results.get(0).getIsWarn());        
        assertFalse(results.get(0).getIsError());
        assertEquals("Please note: you have an overdue library book", results.get(0).getMessage());
        assertTrue(results.get(1).getIsError());
        assertEquals("You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter", results.get(1).getMessage());
    }
    
         
    @Test
    public void testCase10SummerOnlyStudentCannotRegister() throws Exception {
        System.out.println("case 10: Summer Only Student Cannot Register");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_AMBER_HOPKINS_2155,
                ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsError());
        assertEquals("Summer only students cannot register for fall, winter or spring terms", results.get(0).getMessage());
    }  
    
    @Test
    public void testCase11SummerOnlyStudentCanRegisterBecauseItIsSummer() throws Exception {
        System.out.println("case 11: Summer Only Student Can Register Because it Is Summer");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_AMBER_HOPKINS_2155,
                ProcessPocConstants.SUMMER_2011_TERM_KEY, context);
        assertEquals(0, results.size());
    }
    
    
    @Test
    public void testCase12TooEarlyButHasAnExemption() throws Exception {
        System.out.println("case 12: Too Early But Has An Exemption");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_JOHNNY_MANNING_2374,
                ProcessPocConstants.SPRING_2012_TERM_KEY, context);
        assertEquals(0, results.size());
    }
    
     
    @Test
    public void testCase13TooLateButHasAnExtensionExemption() throws Exception {
        System.out.println("case 13: Too Late But Has An Extension Exemption");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_EDDIE_PITTMAN_2406,
                ProcessPocConstants.SPRING_2011_TERM_KEY, context);
        assertEquals(0, results.size());
    }
    
             
    @Test
    public void testCase14TooLateEvenWithExtensionExemption() throws Exception {
        System.out.println("case 14: Too Late Even With Extension Exemption");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_TRACY_BURTON_2132,
                ProcessPocConstants.SPRING_2011_TERM_KEY, context);
        assertEquals(1, results.size());
        assertTrue(results.get(0).getIsError());
        // TODO: Shouldn't this message say something about the fact that the student has an extention but it is even too late for that?
        assertEquals("Registration period for this term is closed", results.get(0).getMessage());
    } 
}
