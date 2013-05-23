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

package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.OfferingInstructorTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lu.entity.CluResultType;
import org.kuali.student.r2.lum.lu.entity.LuLuRelationType;
import org.kuali.student.r2.lum.lu.entity.LuType;

/**
 * Created with IntelliJ IDEA.
 * User: andy
 * Date: 9/18/12
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingSetSchedulingRunnerDataLoader extends CourseOfferingServiceTestDataLoader {

    @Resource
    private TypeService typeService;

    @Resource(name = "schedulingService")
    private SchedulingService schedulingService;

    @Resource(name = "socService")
    private CourseOfferingSetService socService;

    @Resource
    private PersonService personService;

    @Resource
    private EntityManager em;

    final static String TERM_ID = "2012FA";
    public final static Long START_TIME_MILLIS_8_00_AM = (long) (8 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_10_00_AM = (long) (10 * 60 * 60 * 1000);
    public final static Long END_TIME_MILLIS_8_50_AM = (long) (8 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_10_50_AM = (long) (10 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public static final String TIME_SLOT_1_ID = "timeSlot1";
    public static final String TIME_SLOT_2_ID = "timeSlot2";
    public static final String ROOM_ID = "42";
    public static final String SCHEDULED_AO_ID = "CO-1:LEC-ONLY:LEC-A";
    public static final String EXEMPT_AO_ID = "CO-1:LEC-ONLY:LEC-B";
    public static final String DRAFT_AO_ID = "CO-1:LEC-AND-LAB:LEC-A";
    public static final ArrayList<Integer> DOW_M_W_F = new ArrayList<Integer>();
    public static final ArrayList<Integer> DOW_T_TH = new ArrayList<Integer>();

    private ContextInfo context;

    private String socId;

    public ContextInfo getContext() {
        return context;
    }

    public void setContext(ContextInfo context) {
        this.context = context;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

   

        /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader#initializeData()
     */
    @Override
    protected void initializeData() throws Exception {
       
        OfferingInstructorTransformer.setPersonService(personService);

        loadTypes();
        
        super.initializeData();

        tweakOfferingData();

        loadSchedulingData();

    }

    private void tweakOfferingData() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        // update the state of one AO to Approved (and it's FO and CO to Planned)
        updateAOFOCOState(SCHEDULED_AO_ID, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);

        // update the state of another AO to Approved (and it's FO and CO to Planned)
        updateAOFOCOState(EXEMPT_AO_ID, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
    }

    public void updateAOFOCOState(String aoId, String aoState, String foState, String coState) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        ActivityOfferingInfo ao = coService.getActivityOffering(aoId, context);
        FormatOfferingInfo fo = coService.getFormatOffering(ao.getFormatOfferingId(), context);
        CourseOfferingInfo co = coService.getCourseOffering(fo.getCourseOfferingId(), context);

        coService.changeActivityOfferingState(ao.getId(), aoState, context);
        coService.changeFormatOfferingState(fo.getId(), foState, context);
        coService.changeCourseOfferingState(co.getId(), coState, context);
    }

    private void loadTypes() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, AlreadyExistsException, ReadOnlyException, PermissionDeniedException, OperationFailedException, DoesNotExistException {

        TypeInfo fallType = createType(AtpServiceConstants.ATP_FALL_TYPE_KEY, "Fall term type");
        typeService.createType(fallType.getKey(), fallType, context);

        TypeInfo springType = createType(AtpServiceConstants.ATP_SPRING_TYPE_KEY, "Spring term type");
        typeService.createType(springType.getKey(), springType, context);

        TypeInfo aoLabType = createType(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, "AO Lab type");
        typeService.createType(aoLabType.getKey(), aoLabType, context);

        TypeInfo aoLectureType = createType(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, "AO Lecture type");
        typeService.createType(aoLectureType.getKey(), aoLectureType, context);

        // create typetype relations
        TypeTypeRelationInfo tt = createTypeTypeRelation(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, fallType.getKey(), 1);
        typeService.createTypeTypeRelation(tt.getTypeKey(), tt.getOwnerTypeKey(), tt.getRelatedTypeKey(), tt, context);

        tt = createTypeTypeRelation(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, springType.getKey(), 2);
        typeService.createTypeTypeRelation(tt.getTypeKey(), tt.getOwnerTypeKey(), tt.getRelatedTypeKey(), tt, context);

        tt = createTypeTypeRelation(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, aoLabType.getKey(), 1);
        typeService.createTypeTypeRelation(tt.getTypeKey(), tt.getOwnerTypeKey(), tt.getRelatedTypeKey(), tt, context);

        tt = createTypeTypeRelation(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, aoLectureType.getKey(), 2);
        typeService.createTypeTypeRelation(tt.getTypeKey(), tt.getOwnerTypeKey(), tt.getRelatedTypeKey(), tt, context);

        // hack in types for CLUs
        hackCluType(LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        hackCluType(LuServiceConstants.COURSE_FORMAT_TYPE_KEY);
        hackCluType(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY);
        hackCluType(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY);

        hackCluCluRelationType(CourseAssemblerConstants.COURSE_ACTIVITY_RELATION_TYPE);
        hackCluCluRelationType(CourseAssemblerConstants.FORMAT_RELATION_TYPE);

        CluResultType crt = new CluResultType();
        crt.setId(CourseAssemblerConstants.COURSE_RESULT_TYPE_GRADE);
        crt.setName(crt.getId());
        em.persist(crt);
    }

    private TypeTypeRelationInfo createTypeTypeRelation(String ttType, String ownerType, String relatedType, int rank) {
        TypeTypeRelationInfo tt = new TypeTypeRelationInfo();
        tt.setTypeKey(ttType);
        tt.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        tt.setOwnerTypeKey(ownerType);
        tt.setRelatedTypeKey(relatedType);
        tt.setRank(rank);

        return tt;
    }

    /**
     * Hack to get R1 type data into the DB
     *
     * @param key String to uniquely identify the type
     */
    private void hackCluType(String key) {
        LuType cluType = new LuType();
        cluType.setId(key);
        cluType.setName(key);
        em.persist(cluType);
    }

    private void hackCluCluRelationType(String key) {
        LuLuRelationType relType = new LuLuRelationType();
        relType.setId(key);
        relType.setName(key);
        em.persist(relType);
    }

    private TypeInfo createType(String key, String name) {
        TypeInfo type = new TypeInfo();
        type.setKey(key);
        type.setName(name);
        type.setDescr(new RichTextInfo(name, name));
        type.setEffectiveDate(new Date());

        return type;
    }

    private void loadSchedulingData() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        // create soc
        SocInfo soc = new SocInfo();
        soc.setName("test name");
        soc.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        soc.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        soc.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        soc.setTermId(CourseOfferingSetSchedulingRunnerDataLoader.TERM_ID);

        SocInfo info = socService.createSoc(soc.getTermId(), soc.getTypeKey(), soc, context);
        setSocId(info.getId());

        //  Do state changes so that log entries are correct and state is correct for scheduling.
        socService.changeSocState(info.getId(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, context);
        socService.changeSocState(info.getId(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS, context);

        // days of week M W F
        CourseOfferingSetSchedulingRunnerDataLoader.DOW_M_W_F.add(Calendar.MONDAY);
        CourseOfferingSetSchedulingRunnerDataLoader.DOW_M_W_F.add(Calendar.WEDNESDAY);
        CourseOfferingSetSchedulingRunnerDataLoader.DOW_M_W_F.add(Calendar.FRIDAY);

        // days of week T TH
        CourseOfferingSetSchedulingRunnerDataLoader.DOW_T_TH.add(Calendar.TUESDAY);
        CourseOfferingSetSchedulingRunnerDataLoader.DOW_T_TH.add(Calendar.THURSDAY);

        // add scheduling request data to AOs
        CommonServiceConstants.setIsIdAllowedOnCreate(context, true);
        loadTimeSlotInfo(TIME_SLOT_1_ID, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, CourseOfferingSetSchedulingRunnerDataLoader.DOW_M_W_F, CourseOfferingSetSchedulingRunnerDataLoader.START_TIME_MILLIS_8_00_AM, CourseOfferingSetSchedulingRunnerDataLoader.END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo(TIME_SLOT_2_ID, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, CourseOfferingSetSchedulingRunnerDataLoader.DOW_T_TH, CourseOfferingSetSchedulingRunnerDataLoader.START_TIME_MILLIS_10_00_AM, CourseOfferingSetSchedulingRunnerDataLoader.END_TIME_MILLIS_10_50_AM);
        CommonServiceConstants.setIsIdAllowedOnCreate(context, false);

        // create a schedule request for the AO intended to be scheduled
        ScheduleRequestInfo request1 = setupScheduleRequestInfo("request1", CourseOfferingSetSchedulingRunnerDataLoader.SCHEDULED_AO_ID, "requestComponent1-1", "request1", TIME_SLOT_1_ID);

        // create a second schedule request component and add it to the first schedule request
        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo(request1.getScheduleRequestComponents().get(0));
        componentInfo.setId("requestComponent1-2");
        componentInfo.getTimeSlotIds().clear();
        componentInfo.getTimeSlotIds().add(TIME_SLOT_2_ID);
        componentInfo.setIsTBA(true);

        request1.getScheduleRequestComponents().add(componentInfo);
        
        
        schedulingService.createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, request1, context);

        // create a schedule request for the AO intended to have a TBA request
        ScheduleRequestInfo request2 = setupScheduleRequestInfo("request2", CourseOfferingSetSchedulingRunnerDataLoader.EXEMPT_AO_ID, "requestComponent2", "request2", TIME_SLOT_2_ID);
        request2.getScheduleRequestComponents().get(0).setIsTBA(true);
        schedulingService.createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, request2, context);

        // create a schedule request for the AO that is draft and will not have an actual schedule
        ScheduleRequestInfo request3 = setupScheduleRequestInfo("request3", CourseOfferingSetSchedulingRunnerDataLoader.DRAFT_AO_ID, "requestComponent3", "request3", TIME_SLOT_1_ID);
        schedulingService.createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, request3, context);
    }

    private void loadTimeSlotInfo (String ts_id, String stateKey, String typeKey, List<Integer> weekdays, Long startTimeInMillisecs, Long endTimeInMillisecs)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        TimeSlotInfo ts = new TimeSlotInfo();
        ts.setId(ts_id);
        ts.setWeekdays(weekdays);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(startTimeInMillisecs);
        ts.setStartTime(startTime);
        TimeOfDayInfo endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(endTimeInMillisecs);
        ts.setEndTime(endTime);
        ts.setStateKey(stateKey);
        ts.setTypeKey(typeKey);
        schedulingService.createTimeSlot(typeKey, ts, context);
    }

    public static ScheduleRequestInfo setupScheduleRequestInfo(String scheduleRequestInfoId, String scheduleRequestInfoRefObjectId,
                                                               String ScheduleRequestComponentInfoId, String scheduleRequestInfoName, String timeSlotId) {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setId(scheduleRequestInfoId);

// TODOSSR       scheduleRequestInfo.setRefObjectId(scheduleRequestInfoRefObjectId);
// TODOSSR       scheduleRequestInfo.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);

        scheduleRequestInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        scheduleRequestInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        scheduleRequestInfo.setName(scheduleRequestInfoName);

        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setId(ScheduleRequestComponentInfoId);
        componentInfo.getBuildingIds().add("1");
        componentInfo.getCampusIds().add("MAIN");
        componentInfo.getOrgIds().add("1001");
        componentInfo.getRoomIds().add(CourseOfferingSetSchedulingRunnerDataLoader.ROOM_ID);
        componentInfo.getTimeSlotIds().add(timeSlotId);
        componentInfo.setIsTBA(false);

        scheduleRequestInfo.getScheduleRequestComponents().add(componentInfo);

        return scheduleRequestInfo;
    }

}
