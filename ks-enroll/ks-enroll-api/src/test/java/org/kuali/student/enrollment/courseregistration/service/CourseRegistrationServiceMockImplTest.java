/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.courseregistration.service;

import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

/**
 *
 * @author nwright
 */
public class CourseRegistrationServiceMockImplTest {
    
    public CourseRegistrationServiceMockImplTest() {
    }
    
    private CourseRegistrationService service = new CourseRegistrationServiceMockImpl ();

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
    public void testATPCrud() throws Exception {
        ContextInfo context = new ContextInfo ();
        context.setPrincipalId("principal1");

        RegRequestInfo regRequest = new RegRequestInfo ();
        regRequest.setTermId("test.term.fall.2011");
        regRequest.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        regRequest.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_NEW_STATE_KEY);
        regRequest.setName("Test Cart"); 

        // Test create
        RegRequestInfo createdRegRequest = service.createRegRequest(regRequest, context);

        // Test read ATP
        RegRequestInfo  readRegRequest = service.getRegRequest(createdRegRequest.getId(), context);
            
               
        
    }
    
}
