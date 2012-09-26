/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseofferingset.service.impl.CourseOfferingSetSchedulingRunnerDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for methods in CourseOfferingServiceStateHelper
 *
 * @author andrewlubbers
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-state-helper-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceStateHelper {

    @Resource(name = "socService")
    private CourseOfferingSetService socService;

    @Resource(name = "coService")
    private CourseOfferingService coService;

    @Resource(name = "schedulingService")
    private SchedulingService schedulingService;

    @Resource
    private AcademicCalendarService acalService;

    @Resource
    private AtpService atpService;

    @Resource
    private CourseService canonicalCourseService;

    @Resource
    private CourseOfferingSetSchedulingRunnerDataLoader dataLoader;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    private String socId;
    private static final String CO_KEY = "courseOffering";
    private static final String FO_KEY = "formatOffering";
    private static final String AO_KEY = "activityOffering";

    private static final String POST_PUBLISH_DRAFT_AO_WITH_REQUESTS = "CO-2:LEC-ONLY:LEC-A";

    private static final String POST_PUBLISH_DRAFT_AO_NO_REQUESTS = "CO-2:LEC-ONLY:LEC-B";
    private static final String SPRING_TERM_ID = "2012SP";

    private String springSoc = null;

    @Before
    public void setUp() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, VersionMismatchException, PermissionDeniedException, OperationFailedException {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);

        dataLoader.setContext(callContext);

        try {
            dataLoader.beforeTest();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        socId = dataLoader.getSocId();

        // other data updates for this test
        // update the state of another AO to Approved (and it's FO and CO to Planned)
        dataLoader.updateAOFOCOState(CourseOfferingSetSchedulingRunnerDataLoader.DRAFT_AO_ID, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);

        // Make sure the post-publish test AOs are in draft state
        dataLoader.updateAOFOCOState(POST_PUBLISH_DRAFT_AO_WITH_REQUESTS, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        dataLoader.updateAOFOCOState(POST_PUBLISH_DRAFT_AO_NO_REQUESTS, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);

        // add scheduling data for two other activity offerings

        // create a SoC for the spring term
        // create soc
        SocInfo soc = new SocInfo();
        soc.setName("test name");
        soc.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        soc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        soc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        soc.setTermId(SPRING_TERM_ID);

        SocInfo info = socService.createSoc(soc.getTermId(), soc.getTypeKey(), soc, callContext);

        this.springSoc = info.getId();


        // create a schedule request for the AO that is draft and should be set to offered after scheduling
        ScheduleRequestInfo request4 = CourseOfferingSetSchedulingRunnerDataLoader.setupScheduleRequestInfo("request4", POST_PUBLISH_DRAFT_AO_WITH_REQUESTS, "requestComponent4", "request4", CourseOfferingSetSchedulingRunnerDataLoader.TIME_SLOT_1_ID);
        schedulingService.createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, request4, callContext);
    }

    @Test
    public void testUpdateScheduledActivityOffering() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        // set the SOC state to final edits
        socService.updateSocState(socId, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, callContext);


        // Activity Offering Chain 1
        Map<String, String> beforeStates = new HashMap<String, String>();
        Map<String, String> afterStates = new HashMap<String, String>();

        beforeStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        beforeStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        beforeStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);

        afterStates.putAll(beforeStates);

        checkStatesBeforeAndAfterScheduling(beforeStates, afterStates, CourseOfferingSetSchedulingRunnerDataLoader.SCHEDULED_AO_ID);

        // Activity Offering Chain 2:  Same CO as chain 1, but starting with a Draft AO and FO
        beforeStates.clear();
        afterStates.clear();

        beforeStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        beforeStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        beforeStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);

        afterStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        afterStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        afterStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);

        OfferingChain postScheduleDraftChain = checkStatesBeforeAndAfterScheduling(beforeStates, afterStates, CourseOfferingSetSchedulingRunnerDataLoader.DRAFT_AO_ID);
        // assert that a schedule was created for the DRAFT AO
        assertNotNull(postScheduleDraftChain.getAo().getScheduleId());
        assertEquals(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY, postScheduleDraftChain.getAo().getSchedulingStateKey());


        // now test scheduling an AO after the SoC is set to Published
        socService.updateSocState(springSoc, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY, callContext);

        // Activity Offering Chain 3: Different CO, FO
        beforeStates.clear();
        afterStates.clear();

        beforeStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        beforeStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        beforeStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);

        afterStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        afterStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        afterStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);

        OfferingChain postSchedulePublishedChain = checkStatesBeforeAndAfterScheduling(beforeStates, afterStates, POST_PUBLISH_DRAFT_AO_WITH_REQUESTS);
        assertNotNull(postScheduleDraftChain.getAo().getScheduleId());
        assertEquals(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY, postSchedulePublishedChain.getAo().getSchedulingStateKey());

        // Activity Offering Chain 4: Same CO and FO as Chain 3, but with no schedule requests, so AO state should remain DRAFT
        beforeStates.clear();
        afterStates.clear();

        beforeStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        beforeStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        beforeStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);

        afterStates.put(CO_KEY, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        afterStates.put(FO_KEY, LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        afterStates.put(AO_KEY, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);

        OfferingChain postSchedulePublishedDraftChain = checkStatesBeforeAndAfterScheduling(beforeStates, afterStates, POST_PUBLISH_DRAFT_AO_NO_REQUESTS);
        assertNull(postSchedulePublishedDraftChain.getAo().getScheduleId());
        assertEquals(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY, postSchedulePublishedDraftChain.getAo().getSchedulingStateKey());

    }

    private OfferingChain checkStatesBeforeAndAfterScheduling(Map<String, String> beforeStates, Map<String, String> afterStates, String aoId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        // assert our assumptions about the states of the offering chain before scheduling
        OfferingChain preScheduleChain = getOfferingChain(aoId, callContext);
        assertEquals(beforeStates.get(AO_KEY), preScheduleChain.getAo().getStateKey());
        assertEquals(beforeStates.get(FO_KEY), preScheduleChain.getFo().getStateKey());
        assertEquals(beforeStates.get(CO_KEY), preScheduleChain.getCo().getStateKey());

        // submit the activity offering to be scheduled
        StatusInfo status = coService.scheduleActivityOffering(aoId, callContext);
        assertTrue(status.getIsSuccess());

        // call the state updating utility
        CourseOfferingServiceStateHelper.updateScheduledActivityOffering(coService.getActivityOffering(aoId, callContext), coService, socService, callContext);

        // get the activity offering with the updated schedule information
        OfferingChain postScheduleChain = getOfferingChain(aoId, callContext);

        // assert the expected offering states after update
        assertEquals(afterStates.get(AO_KEY), postScheduleChain.getAo().getStateKey());
        assertEquals(afterStates.get(FO_KEY), postScheduleChain.getFo().getStateKey());
        assertEquals(afterStates.get(CO_KEY), postScheduleChain.getCo().getStateKey());

        return postScheduleChain;
    }

    private OfferingChain getOfferingChain(String aoId, ContextInfo callContext) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ActivityOfferingInfo ao = coService.getActivityOffering(aoId, callContext);

        FormatOfferingInfo fo = coService.getFormatOffering(ao.getFormatOfferingId(), callContext);

        CourseOfferingInfo co = coService.getCourseOffering(fo.getCourseOfferingId(), callContext);

        return new OfferingChain(co, fo, ao);
    }

    private class OfferingChain {
        ActivityOfferingInfo ao;

        FormatOfferingInfo fo;

        CourseOfferingInfo co;

        public OfferingChain() {
        }

        public OfferingChain(CourseOfferingInfo co, FormatOfferingInfo fo, ActivityOfferingInfo ao) {
            this.co = co;
            this.fo = fo;
            this.ao = ao;
        }

        public ActivityOfferingInfo getAo() {
            return ao;
        }

        public void setAo(ActivityOfferingInfo ao) {
            this.ao = ao;
        }

        public CourseOfferingInfo getCo() {
            return co;
        }

        public void setCo(CourseOfferingInfo co) {
            this.co = co;
        }

        public FormatOfferingInfo getFo() {
            return fo;
        }

        public void setFo(FormatOfferingInfo fo) {
            this.fo = fo;
        }
    }
}
