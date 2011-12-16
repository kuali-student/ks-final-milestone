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
public class ProcessPocIntegrationTest {

    public ProcessPocIntegrationTest() {
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
    }

    @Test
    public void testCase3IsDeadShortCircuit() throws Exception {
        System.out.println("case 3: is dead short circuit");
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipal1");

        List<ValidationResultInfo> results = null;
        results = service.checkStudentEligibilityForTerm(ProcessPocConstants.PERSON_ID_KARA_STONE_2272, ProcessPocConstants.FALL_2011_TERM_KEY, context);
        assertEquals(1, results);
    }
}
