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
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.core.exemption.service.impl.ExemptionServiceMapImpl;
import org.kuali.student.core.process.service.impl.ProcessServiceDataLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author nwright
 */
//@Ignore // TODO: re-enable after refactoring
public class ProcessIntegrationTestExemptionServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestExemptionServiceDataLoadingDecoratorTest() {
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
    @Test
    public void testSomeMethod() throws Exception {
        ContextInfo context = this.getContextInfoAsOf12302011();

        ExemptionService exemptionService = new ExemptionServiceMapImpl();
        exemptionService = new ProcessIntegrationTestExemptionServiceDataLoadingDecorator(exemptionService);
        List<ExemptionInfo> exemptions = null;
        exemptions = exemptionService.getExemptionsForPerson(ProcessIntegrationTestConstants.PERSON_ID_JOHNNY_MANNING_2374, context);
        assertEquals(1, exemptions.size());

        exemptions = exemptionService.getExemptionsForPerson(ProcessIntegrationTestConstants.PERSON_ID_EDDIE_PITTMAN_2406, context);
        assertEquals(1, exemptions.size());

        exemptions = exemptionService.getExemptionsForPerson(ProcessIntegrationTestConstants.PERSON_ID_TRACY_BURTON_2132, context);
        assertEquals(1, exemptions.size());

        exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                ExemptionServiceConstants.CHECK_EXEMPTION_TYPE_KEY,
                ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM,
                ProcessServiceDataLoader.CHECK_ID_REGISTRATION_PERIOD_IS_OPEN,
                ProcessIntegrationTestConstants.PERSON_ID_JOHNNY_MANNING_2374, 
                context.getCurrentDate(), context);
        assertEquals(1, exemptions.size()); 
        
        exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY,
                ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM,
                ProcessServiceDataLoader.CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED,
                ProcessIntegrationTestConstants.PERSON_ID_EDDIE_PITTMAN_2406, 
                context.getCurrentDate(), 
                context);
        assertEquals(1, exemptions.size()); 
        assertEquals (exemptions.get(0).getDateOverride().getEffectiveEndDate(), new SimpleDateFormat ("yyyy-MM-dd").parse("2011-12-31"));
        assertEquals (exemptions.get(0).getDateOverride().getMilestoneId(), AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        
        exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY,
                ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM,
                ProcessServiceDataLoader.CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED,
                ProcessIntegrationTestConstants.PERSON_ID_TRACY_BURTON_2132,
                context.getCurrentDate(), 
                context);
        assertEquals(1, exemptions.size()); 
        assertEquals (exemptions.get(0).getDateOverride().getEffectiveEndDate(), new SimpleDateFormat ("yyyy-MM-dd").parse("2011-11-30"));
        assertEquals (exemptions.get(0).getDateOverride().getMilestoneId(), AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);        
    }
}
