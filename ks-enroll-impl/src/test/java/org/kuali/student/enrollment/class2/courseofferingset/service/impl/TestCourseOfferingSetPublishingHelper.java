package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceMockImpl;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *  Test mass publishing event state change logic.
 */
public class TestCourseOfferingSetPublishingHelper {

    private static final ContextInfo context = new ContextInfo();
    private static final String termId = "termId";
    private static final  String socId = "socId";

    /**
     * Create a data set.
     */
    @BeforeClass
    public static void setup() throws Exception {
        //  Create a SOC in state "publishing".
        SocInfo socInfo = new SocInfo();
        socInfo.setId(socId);
        //  Set SOC state to "completed" and scheduling state to "publishing".
        socInfo.setStateKey(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY);
        socInfo.setSchedulingStateKey(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED);
        socInfo.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        socServiceMock.createSoc(termId, CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, socInfo, context);

        /*
         * Create a CourseOfferings ...
         *  CO1 will have AOs that cause a state change to "offered"
         *  CO2 will have AOs that do not cause a state change
         */
        CourseOfferingInfo co1 = new CourseOfferingInfo();
        co1.setId("co1");
        co1.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        co1.setStateKey(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        coServiceMock.createCourseOffering(co1.getCourseId(), termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co1, new ArrayList<String>(), context);

        CourseOfferingInfo co2 = new CourseOfferingInfo();
        co2.setId("co2");
        co2.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        co2.setStateKey(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        coServiceMock.createCourseOffering(co2.getCourseId(), termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, co2, new ArrayList<String>(), context);

        //  Create a FormatOfferings (2 for CO1, 1 for CO2)
        FormatOfferingInfo co1fo1 = new FormatOfferingInfo();
        co1fo1.setId("co1.fo1");
        co1fo1.setFormatId(co1fo1.getId());
        co1fo1.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        co1fo1.setCourseOfferingId(co1.getId());
        co1fo1.setTermId(termId);
        co1fo1.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        coServiceMock.createFormatOffering(co1.getId(), co1fo1.getFormatId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, co1fo1, context);

        FormatOfferingInfo co1fo2 = new FormatOfferingInfo();
        co1fo2.setId("co1.fo2");
        co1fo2.setFormatId(co1fo2.getId());
        co1fo2.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        co1fo2.setCourseOfferingId(co1.getId());
        co1fo2.setTermId(termId);
        co1fo2.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        coServiceMock.createFormatOffering(co1.getId(), co1fo2.getFormatId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, co1fo2, context);

        FormatOfferingInfo co2fo1 = new FormatOfferingInfo();
        co2fo1.setId("co2.fo1");
        co2fo1.setFormatId(co2fo1.getId());
        co2fo1.setStateKey(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        co2fo1.setCourseOfferingId(co2.getId());
        co2fo1.setTermId(termId);
        co2fo1.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        coServiceMock.createFormatOffering(co2.getId(), co2fo1.getFormatId(), LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, co2fo1, context);

        /*
         *  Create Activity Offerings for CO1
         *      AO1 state == "approved", scheduling state == "scheduled" -> final state == offered
         *      AO2 state == "approved", scheduling state == "exempt" -> offered
         *      A03 state == "draft", scheduling state == "unscheduled" -> daft
         */
        ActivityOfferingInfo co1ao1 = new ActivityOfferingInfo();
        co1ao1.setId("co1.ao1");
        co1ao1.setCourseOfferingId(co1.getId());
        co1ao1.setActivityId(co1ao1.getId());
        //  !!! Set AO state and scheduling state.
        co1ao1.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
        co1ao1.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY);
        co1ao1.setTypeKey(LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY);
        co1ao1.setAttributes(new ArrayList<AttributeInfo>());
        co1ao1.setFormatOfferingId(co1fo1.getId());
        coServiceMock.createActivityOffering(co1fo1.getId(), co1ao1.getActivityId(), LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY, co1ao1, context);

        ActivityOfferingInfo co1ao2 = new ActivityOfferingInfo();
        co1ao2.setId("co1.ao2");
        co1ao2.setCourseOfferingId(co1.getId());
        co1ao2.setActivityId(co1ao2.getId());
        //  Set states.
        co1ao2.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
        co1ao2.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY);
        co1ao2.setTypeKey(LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY);
        co1ao2.setFormatOfferingId(co1fo2.getId());
        coServiceMock.createActivityOffering(co1fo2.getId(), co1ao2.getActivityId(), LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY, co1ao2, context);

        ActivityOfferingInfo co1ao3 = new ActivityOfferingInfo();
        co1ao3.setId("co1.ao3");
        co1ao3.setCourseOfferingId(co1.getId());
        co1ao3.setActivityId(co1ao3.getId());
        //  Set states.
        co1ao3.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        co1ao3.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY);
        co1ao3.setTypeKey(LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY);
        co1ao3.setFormatOfferingId(co1fo2.getId());
        coServiceMock.createActivityOffering(co1fo2.getId(), co1ao3.getActivityId(), LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY, co1ao3, context);

        ActivityOfferingInfo co2ao1 = new ActivityOfferingInfo();
        co2ao1.setId("co2.ao1");
        co2ao1.setCourseOfferingId(co2.getId());
        co2ao1.setActivityId(co2ao1.getId());
        //  Set states. (approved and unscheduled)
        co2ao1.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
        co2ao1.setSchedulingStateKey(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY);
        co2ao1.setTypeKey(LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY);
        co2ao1.setFormatOfferingId(co2fo1.getId());
        coServiceMock.createActivityOffering(co2fo1.getId(), co2ao1.getActivityId(), LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY, co2ao1, context);
    }

    @Test
    public void test() throws Exception {
        CourseOfferingSetPublishingHelper socPub = new CourseOfferingSetPublishingHelper();
        socPub.setCoService(coServiceMock);
        socPub.setSocService(socServiceMock);
        socServiceMock.setCoService(coServiceMock);

        List<String> options = new ArrayList<String>();
        options.add(CourseOfferingSetServiceConstants.RUN_SYNCHRONOUSLY_OPTION_KEY);
        socPub.startMassPublishingEvent(socId, options, context);

        SocInfo soc = socServiceMock.getSoc(socId, context);
        assertEquals(socId, soc.getId());
        //  SOC state should have progressed from publishing to published.
        assertEquals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY, soc.getStateKey());

        //  Validate CO states
        List<String> coIds = socServiceMock.getCourseOfferingIdsBySoc(socId, context);
        Collections.sort(coIds);
        assertEquals(2, coIds.size());

        //  Verify CO1 state was changed to "offered".
        CourseOfferingInfo co1 = coServiceMock.getCourseOffering(coIds.get(0), context);
        assertEquals(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY, co1.getStateKey());

        //  Verify CO1 AO states
        List<ActivityOfferingInfo> aos = coServiceMock.getActivityOfferingsByCourseOffering(co1.getId(), context);
        assertEquals(3, aos.size());
        Collections.sort(aos, new Comparator<ActivityOfferingInfo>() {
            public int compare(ActivityOfferingInfo ao1, ActivityOfferingInfo ao2) {
                return ao1.getId().compareTo(ao2.getId());
            }
        });

        ActivityOfferingInfo ao = aos.get(0);
        assertEquals("co1.ao1", ao.getId());
        assertEquals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, ao.getStateKey());
        FormatOfferingInfo fo = coServiceMock.getFormatOffering(ao.getFormatOfferingId(), context);
        assertEquals(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY, fo.getStateKey());

        ao = aos.get(1);
        assertEquals("co1.ao2", ao.getId());
        assertEquals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, ao.getStateKey());
        fo = coServiceMock.getFormatOffering(ao.getFormatOfferingId(), context);
        assertEquals(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY, fo.getStateKey());

        ao = aos.get(2);
        assertEquals("co1.ao3", ao.getId());
        assertEquals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, ao.getStateKey());

        //  Verify CO2 state was remained "planned".
        CourseOfferingInfo co2 = coServiceMock.getCourseOffering(coIds.get(1), context);
        assertEquals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY, co2.getStateKey());

        //  Verify CO2 AO state
        aos = coServiceMock.getActivityOfferingsByCourseOffering(co2.getId(), context);
        assertEquals(1, aos.size());
        ao = aos.get(0);
        assertEquals("co2.ao1", ao.getId());
        assertEquals(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, ao.getStateKey());
        fo = coServiceMock.getFormatOffering(ao.getFormatOfferingId(), context);
        assertEquals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, fo.getStateKey());
    }

    /**
     * Override these to simplify initiali
     */
    private static CourseOfferingSetServiceMockImpl socServiceMock = new CourseOfferingSetServiceMockImpl() {
        @Override
        public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context)
                throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            List<String> coIds = new ArrayList<String>();
            coIds.add("co1");
            coIds.add("co2");
            return coIds;
        }
    };

    private static CourseOfferingServiceMockImpl coServiceMock = new CourseOfferingServiceMockImpl() {
        @Override
        public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey, CourseOfferingInfo courseOfferingInfo,
                                                   List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException, ReadOnlyException {
            courseOfferingMap.put(courseOfferingInfo.getId(), courseOfferingInfo);
            return courseOfferingInfo;
        }
    };
}
