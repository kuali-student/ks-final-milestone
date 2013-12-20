/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import java.text.DateFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
//import org.kuali.student.r2.core.acal.service.assembler.AcademicCalendarAssembler;
//import org.kuali.student.r2.core.acal.service.assembler.TermAssembler;
//import org.kuali.student.r2.core.acal.service.impl.AcademicCalendarServiceImpl;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.atp.service.impl.AtpServiceMapImpl;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author nwright
 */
//@Ignore // TODO: re-enable after refactoring
public class ProcessPocAtpServiceDecoratorTest {

    public ProcessPocAtpServiceDecoratorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAtpMethods() throws Exception {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-tester");

        AtpService service = new AtpServiceMapImpl();
        service = new ProcessPocAtpServiceDecorator(service);

        AtpInfo atp = null;
        List<MilestoneInfo> regPeriods = null;

        atp = service.getAtp(ProcessPocConstants.SPRING_2011_TERM_KEY, context);
        regPeriods = service.getMilestonesByTypeForAtp(ProcessPocConstants.SPRING_2011_TERM_KEY,
                AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
        assertEquals(1, regPeriods.size());
        compare("2011-01-20", regPeriods.get(0).getStartDate());
        compare("2011-02-01", regPeriods.get(0).getEndDate());

        atp = service.getAtp(ProcessPocConstants.SUMMER_2011_TERM_KEY, context);
        regPeriods = service.getMilestonesByTypeForAtp(ProcessPocConstants.SUMMER_2011_TERM_KEY,
                AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
        assertEquals(1, regPeriods.size());
        compare("2011-06-01", regPeriods.get(0).getStartDate());
        compare("2011-12-31", regPeriods.get(0).getEndDate());

        atp = service.getAtp(ProcessPocConstants.FALL_2011_TERM_KEY, context);
        regPeriods = service.getMilestonesByTypeForAtp(ProcessPocConstants.FALL_2011_TERM_KEY,
                AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
        assertEquals(1, regPeriods.size());
        compare("2011-09-01", regPeriods.get(0).getStartDate());
        compare("2011-12-31", regPeriods.get(0).getEndDate());

        atp = service.getAtp(ProcessPocConstants.SPRING_2012_TERM_KEY, context);
        regPeriods = service.getMilestonesByTypeForAtp(ProcessPocConstants.SPRING_2012_TERM_KEY,
                AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
        assertEquals(1, regPeriods.size());
        compare("2012-01-20", regPeriods.get(0).getStartDate());
        compare("2012-02-01", regPeriods.get(0).getEndDate());
    }
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private void compare(String date1, Date date2) {
        assertEquals(date1, df.format(date2));
    }
//    @Test
//    public void testAcalMethods() throws Exception {
//        ContextInfo context = new ContextInfo();
//        context.setPrincipalId("POC-tester");
//        
//        AtpService atpService = new ProcessPocAtpServiceDecorator(new AtpServiceMockImpl());
//        AcademicCalendarServiceImpl acalImpl = new AcademicCalendarServiceImpl();
//        acalImpl.setAtpService(atpService);
//        acalImpl.setAcalAssembler(new AcademicCalendarAssembler());
//        acalImpl.setTermAssembler(new TermAssembler());
//        
//        List<TypeInfo> terms = acalImpl.getTermTypes(context);
//        assertEquals (4, terms.size());
//        
//
//    }
}
