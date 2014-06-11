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
//import org.kuali.student.r2.core.acal.service.assembler.AcademicCalendarAssembler;
//import org.kuali.student.r2.core.acal.service.assembler.TermAssembler;
//import org.kuali.student.r2.core.acal.service.impl.AcademicCalendarServiceImpl;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.service.impl.GesServiceMapImpl;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;

/**
 *
 * @author nwright
 */
//@Ignore
public class ProcessIntegrationTestGesServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestGesServiceDataLoadingDecoratorTest() {
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
    public void testGesMethods() throws Exception {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("Test-tester");

        GesService service = new GesServiceMapImpl();
        service = new ProcessIntegrationTestGesServiceDataLoadingDecorator(service);

        ParameterInfo param = null;
        List<ValueInfo> values = null;

        param = service.getParameter(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, contextInfo);
        assertEquals (GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, param.getKey());
        values = service.getValuesByParameter(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT,contextInfo);
        assertEquals(6, values.size());
        
        
        param = service.getParameter(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, contextInfo);
        assertEquals (GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, param.getKey());
        values = service.getValuesByParameter(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM,contextInfo);
        assertEquals(4, values.size());
    }
}
