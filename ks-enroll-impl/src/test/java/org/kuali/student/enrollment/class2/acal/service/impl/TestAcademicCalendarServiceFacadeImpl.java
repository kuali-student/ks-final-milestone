/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 6/13/13
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.acal.service.facade.AcademicCalendarServiceFacade;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Tests AcademicCalendarServiceFacadeImpl
 *
 * @author Kuali Student Team (cclin@umd.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-service-facade-test-class2-mock-context.xml"})
public class TestAcademicCalendarServiceFacadeImpl {
    private static final Logger log = KSLog4JConfigurer
            .getLogger(TestAcademicCalendarServiceFacadeImpl.class);

    @Resource(name="CourseOfferingService")
    protected CourseOfferingService coService;

    @Resource
    protected CourseService courseService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource(name = "LrcService")
    protected LRCService lrcService;

    @Resource
    protected AcademicCalendarService acalService;

    @Resource
    protected AtpService atpService;

    @Resource
    private AcademicCalendarServiceFacade acalServiceFacade;

    // Local instance variables
    private ContextInfo contextInfo;
    private AcademicCalendarInfo cal;
    private TermInfo parentTerm;
    private TermInfo childTerm;
    private TermInfo childTerm2;

    public TestAcademicCalendarServiceFacadeImpl() {
    }

    @Before
    public void beforeTest() throws Exception {
        dataLoader.beforeTest();

        contextInfo = new ContextInfo();

        contextInfo.setCurrentDate(new Date());

        contextInfo.setPrincipalId("test");
        contextInfo.setAuthenticatedPrincipalId("test");

        // Create an acal, plus a term and subterm
        cal = new AcademicCalendarInfo();
        cal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        cal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        cal = acalService.createAcademicCalendar(cal.getTypeKey(), cal, contextInfo);
        // Then, create a term
        parentTerm = new TermInfo();
        parentTerm.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        parentTerm.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        parentTerm = acalService.createTerm(parentTerm.getTypeKey(), parentTerm, contextInfo);
        // Attach term to calendar
        acalService.addTermToAcademicCalendar(cal.getId(), parentTerm.getId(), contextInfo);
        // Create child term
        childTerm = new TermInfo();
        childTerm.setTypeKey(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY);
        childTerm.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        childTerm = acalService.createTerm(childTerm.getTypeKey(), childTerm, contextInfo);
        // Attach term to calendar
        acalService.addTermToTerm(parentTerm.getId(), childTerm.getId(), contextInfo);
        // Create child term (second)
        childTerm2 = new TermInfo();
        childTerm2.setTypeKey(AtpServiceConstants.ATP_HALF_FALL_2_TYPE_KEY);
        childTerm2.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        childTerm2 = acalService.createTerm(childTerm2.getTypeKey(), childTerm2, contextInfo);
        // Attach term to calendar
        acalService.addTermToTerm(parentTerm.getId(), childTerm2.getId(), contextInfo);
    }

    @After
    public void afterTest() throws Exception {
        dataLoader.afterTest();
    }

    @Test
    public void testMakeTermOfficialCascaded() throws Exception {
        // KSENROLL-7251 (testing making a term offiical)
        acalServiceFacade.makeTermOfficialCascaded(childTerm.getId(), contextInfo);
        // Refetch all relevant parts
        cal = acalService.getAcademicCalendar(cal.getId(), contextInfo);
        parentTerm = acalService.getTerm(parentTerm.getId(), contextInfo);
        childTerm = acalService.getTerm(childTerm.getId(), contextInfo);
        childTerm2 = acalService.getTerm(childTerm2.getId(), contextInfo);
        assertEquals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, cal.getStateKey());
        assertEquals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, parentTerm.getStateKey());
        assertEquals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, childTerm.getStateKey());
        // The only one that should stay draft
        assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, childTerm2.getStateKey());
    }

    @Test
    public void testDeleteTermSucceed() throws Exception {
        // KSENROLL-7701
        StatusInfo statusInfo = acalServiceFacade.deleteTermCascaded(parentTerm.getId(), contextInfo);
        assert(statusInfo.getIsSuccess());
        boolean threwException = false;
        try {
            acalService.getTerm(parentTerm.getId(), contextInfo);
        } catch (DoesNotExistException e) {
            threwException = true;
        }
        assert(threwException);

        threwException = false;
        try {
            acalService.getTerm(childTerm.getId(), contextInfo);
        } catch (DoesNotExistException e) {
            threwException = true;
        }
        assert(threwException);

        threwException = false;
        try {
            acalService.getTerm(childTerm2.getId(), contextInfo);
        } catch (DoesNotExistException e) {
            threwException = true;
        }
        assert(threwException);
    }

    @Test
    public void testDeleteTermFailed() throws Exception {
        boolean threwException = false;

        acalServiceFacade.makeTermOfficialCascaded(parentTerm.getId(), contextInfo);
        try {
            StatusInfo statusInfo = acalServiceFacade.deleteTermCascaded(parentTerm.getId(), contextInfo);
        } catch (OperationFailedException e) {
            threwException = true;
        }
        assert(threwException);

        threwException = false;
        try {
            acalService.getTerm(parentTerm.getId(), contextInfo);
        } catch (DoesNotExistException e) {
            threwException = true;
        }
        assert(!threwException);

        // KSENROLL-7927
        // The code below fails because mocks don't do rollbacks.  deleteTermCascaded deletes child
        // terms prior to the parent node.  Since child terms can be draft, and parents need not be,
        // we have no good mechanism to do this.

//        threwException = false;
//        try {
//            acalService.getTerm(childTerm.getId(), contextInfo);
//        } catch (DoesNotExistException e) {
//            threwException = true;
//        }
//        assert(!threwException);
//
//        threwException = false;
//        try {
//            acalService.getTerm(childTerm2.getId(), contextInfo);
//        } catch (DoesNotExistException e) {
//            threwException = true;
//        }
//        assert(!threwException);
    }

    private TermInfo _setup2(int level) throws Exception {
        // Initial data setup
        TermInfo subSubTerm = new TermInfo();
        subSubTerm.setTypeKey(AtpServiceConstants.ATP_HALF_FALL_2_TYPE_KEY);
        subSubTerm.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        subSubTerm = acalService.createTerm(subSubTerm.getTypeKey(), subSubTerm, contextInfo);
        acalService.addTermToTerm(childTerm2.getId(), subSubTerm.getId(), contextInfo);
        if (level == 0) {
            acalService.changeTermState(parentTerm.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        } else if (level == 1) {
            acalService.changeTermState(childTerm.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        } else if (level == 2) {
            acalService.changeTermState(childTerm2.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        } else if (level == 3) {
            acalService.changeTermState(subSubTerm.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        }
        return subSubTerm;
    }

    @Test
    public void testValidateTerm0() throws Exception {
        _setup2(0);
        // Now test for invalidity
        assertTrue(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));

    }

    @Test
    public void testValidateTerm1() throws Exception {
        _setup2(1);
        // Now test for invalidity
        assertFalse(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
    }

    @Test
    public void testValidateTerm2() throws Exception {
        _setup2(2);
        // Now test for invalidity
        assertFalse(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
    }

    @Test
    public void testValidateTerm3() throws Exception {
        _setup2(3);
        // Now test for invalidity
        assertFalse(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
    }

    @Test
    public void testValidateCalendar0() throws Exception {
        _setup2(0);

        // Now test for invalidity
        assertFalse(acalServiceFacade.validateCalendar(cal.getId(), contextInfo));
        acalService.changeAcademicCalendarState(cal.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        assertTrue(acalServiceFacade.validateCalendar(cal.getId(), contextInfo));
    }

    @Test
    public void testValidateCalendar1() throws Exception {
        _setup2(1);
        // Now test for invalidity
        assertFalse(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
        acalService.changeAcademicCalendarState(cal.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        assertFalse(acalServiceFacade.validateCalendar(cal.getId(), contextInfo));
    }

    @Test
    public void testValidateCalendar2() throws Exception {
        _setup2(2);
        // Now test for invalidity
        assertFalse(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
        acalService.changeAcademicCalendarState(cal.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        assertFalse(acalServiceFacade.validateCalendar(cal.getId(), contextInfo));
    }

    @Test
    public void testValidateCalendar3() throws Exception {
        _setup2(3);
        // Now test for invalidity
        assertFalse(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
        acalService.changeAcademicCalendarState(cal.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        assertFalse(acalServiceFacade.validateCalendar(cal.getId(), contextInfo));
    }

    @Test
    public void testValidateCalendar4() throws Exception {
        _setup2(4);  // all terms draft
        // Now test for invalidity
        assertTrue(acalServiceFacade.validateTerm(parentTerm.getId(), contextInfo));
        acalService.changeAcademicCalendarState(cal.getId(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
        assertTrue(acalServiceFacade.validateCalendar(cal.getId(), contextInfo));
    }
}