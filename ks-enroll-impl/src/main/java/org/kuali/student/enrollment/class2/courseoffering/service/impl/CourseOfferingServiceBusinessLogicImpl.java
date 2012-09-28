/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupCodeGeneratorFactory;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r2.common.permutation.PermutationUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nwright
 */
public class CourseOfferingServiceBusinessLogicImpl implements CourseOfferingServiceBusinessLogic {

    private static final Logger log = Logger.getLogger(CourseOfferingServiceBusinessLogicImpl.class);

    @Resource
    private CourseService courseService;

    @Resource
    private AcademicCalendarService acalService;

    @Resource
    private CourseOfferingService coService;

    @Resource
    private RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory;

    @Resource
    private SchedulingService schedulingService;

    @Resource
    private RoomService roomService;

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public RegistrationGroupCodeGeneratorFactory getRegistrationCodeGeneratorFactory() {
        return registrationCodeGeneratorFactory;
    }

    public void setRegistrationCodeGeneratorFactory(RegistrationGroupCodeGeneratorFactory registrationCodeGeneratorFactory) {
        this.registrationCodeGeneratorFactory = registrationCodeGeneratorFactory;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    private CourseOfferingService _getCoService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private SchedulingService _getSchedulingService() {
        if (schedulingService == null) {
            schedulingService = (SchedulingService) GlobalResourceLoader.getService(new QName(SchedulingServiceConstants.NAMESPACE,
                    SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return schedulingService;
    }

    public RoomService _getRoomService() {
        if (roomService == null){
            roomService = (RoomService)GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE,
                    RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return roomService;
    }

    private ActivityOfferingInfo _RCO_createTargetActivityOffering(ActivityOfferingInfo sourceAo, FormatOfferingInfo targetFo,
                                                                   String targetTermId, List<String> optionKeys,
                                                                   ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        ActivityOfferingInfo targetAo = new ActivityOfferingInfo(sourceAo);
        targetAo.setId(null);
        // clear out the ids on the internal sub-objects
        for (AttributeInfo attr : targetAo.getAttributes()) {
            attr.setId(null);
        }
        for (OfferingInstructorInfo instr : targetAo.getInstructors()) {
            instr.setId(null);
        }
        targetAo.setFormatOfferingId(targetFo.getId());
        targetAo.setTermId(targetTermId);
        TermInfo termInfo = acalService.getTerm(targetTermId, context);
        targetAo.setTermCode(termInfo.getCode());
        targetAo.setMeta(null);
        // Make sure to copy the activity code
        targetAo.setActivityCode(sourceAo.getActivityCode());
        //Target AO should have no actual schedule
        targetAo.setScheduleId(null);

        if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
            targetAo.getInstructors().clear();
        }
        // Rolled over AO should be in draft state
        targetAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        targetAo = this._getCoService().createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(),
                targetAo.getTypeKey(), targetAo, context);
        return targetAo;
    }

    private void _RCO_rolloverScheduleToScheduleRequest(ActivityOfferingInfo sourceAo, ActivityOfferingInfo targetAo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        //Copy the schedule to a schedule request
        ScheduleInfo sourceScheduleInfo = this._getSchedulingService().getSchedule(sourceAo.getScheduleId(), context);

        ScheduleRequestInfo targetScheduleRequest = SchedulingServiceUtil.scheduleToRequest(sourceScheduleInfo, _getRoomService(), context);
        targetScheduleRequest.setRefObjectId(targetAo.getId());
        targetScheduleRequest.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        StringBuilder nameBuilder = new StringBuilder("Schedule reqeust for ");
        nameBuilder.append(targetAo.getCourseOfferingCode()).append(" - ").append(targetAo.getActivityCode());
        targetScheduleRequest.setName(nameBuilder.toString());
        targetScheduleRequest.setDescr(sourceScheduleInfo.getDescr());

        this._getSchedulingService().createScheduleRequest(targetScheduleRequest.getTypeKey(), targetScheduleRequest, context);
    }

    private void _RCO_rolloverSeatpools(ActivityOfferingInfo sourceAo, ActivityOfferingInfo targetAo, ContextInfo context) {
        //attach SPs to the AO created
        try {
            List<SeatPoolDefinitionInfo> sourceSPList = this._getCoService().getSeatPoolDefinitionsForActivityOffering(sourceAo.getId(), context);
            if (sourceSPList != null && !sourceSPList.isEmpty()) {
                for (SeatPoolDefinitionInfo sourceSP : sourceSPList) {
                    SeatPoolDefinitionInfo targetSP = new SeatPoolDefinitionInfo(sourceSP);
                    targetSP.setId(null);
                    targetSP.setTypeKey(LuiServiceConstants.SEATPOOL_LUI_CAPACITY_TYPE_KEY);
                    targetSP.setStateKey(LuiServiceConstants.LUI_CAPACITY_ACTIVE_STATE_KEY);
                    SeatPoolDefinitionInfo seatPoolCreated = this._getCoService().createSeatPoolDefinition(targetSP, context);
                    this._getCoService().addSeatPoolDefinitionToActivityOffering(seatPoolCreated.getId(), targetAo.getId(), context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FormatOfferingInfo _RCO_createTargetFormatOffering(FormatOfferingInfo sourceFo, CourseOfferingInfo targetCo, String targetTermId, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        FormatOfferingInfo targetFo = new FormatOfferingInfo(sourceFo);
        targetFo.setId(null);
        // clear out the ids on the internal sub-objects
        for (AttributeInfo attr : targetFo.getAttributes()) {
            attr.setId(null);
        }
        targetFo.setCourseOfferingId(targetCo.getId());
        targetFo.setTermId(targetTermId);
        targetFo.setMeta(null);

        // Rolled over FO should be in planned state
        targetFo.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        targetFo = this.getCoService().createFormatOffering(targetFo.getCourseOfferingId(), targetFo.getFormatId(),
                targetFo.getTypeKey(), targetFo, context);
        return targetFo;
    }

    @Override
    public SocRolloverResultItemInfo rolloverCourseOffering(String sourceCoId,
                                                            String targetTermId,
                                                            List<String> optionKeys,
                                                            ContextInfo context) throws AlreadyExistsException,
            DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CourseOfferingInfo sourceCo = this._getCoService().getCourseOffering(sourceCoId, context);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_OPTION_KEY)) {
            throw new DataValidationErrorException("Skipped because course offering was cancelled in source term");
        }
        R1CourseServiceHelper helper = new R1CourseServiceHelper(courseService, acalService);

        CourseInfo sourceCourse = helper.getCourse(sourceCo.getCourseId());
        String sourceCourseId = sourceCourse.getId();
        List<CourseInfo> targetCourses = helper.getCoursesForTerm(sourceCourseId, targetTermId, context);
        if (targetCourses.isEmpty()) {
            throw new InvalidParameterException("Skipped because there is no valid version of the course in the target term");
        }
        if (targetCourses.size() > 1) {
            throw new InvalidParameterException(
                    "Skipped because there are more than one valid versions of the course in the target term");
        }
        CourseInfo targetCourse = targetCourses.get(0);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.SKIP_IF_ALREADY_EXISTS_OPTION_KEY)) {
            String existingCoId = this._findFirstExistingCourseOfferingIdInTargetTerm(targetCourse.getId(), targetTermId, context);
            if (existingCoId != null) {
                throw new AlreadyExistsException("Skipped because course offering already exists in target term");
            }
        }

        // TODO: Not hard code "Active" but use a constant ... except these are R1 States
        if (optionKeys.contains(CourseOfferingSetServiceConstants.STILL_OFFERABLE_OPTION_KEY)) {
            if (!targetCourse.getStateKey().equals("Active")) {
                throw new DataValidationErrorException("skipped because canonical course is no longer active");
            }
        }
        if (optionKeys.contains(CourseOfferingSetServiceConstants.IF_NO_NEW_VERSION_OPTION_KEY)) {
            if (!sourceCourse.getId().equals(targetCourse.getId())) {
                throw new DataValidationErrorException("skipped because there is a new version of the canonical course");
            }
        }
        // Create the course offering
        CourseOfferingInfo targetCo = _RCO_createTargetCourseOffering(sourceCo, targetTermId, targetCourse, optionKeys, context);
        // Get ready to rollover FOs and AOs
        List<FormatOfferingInfo> foInfos = this._getCoService().getFormatOfferingsByCourseOffering(sourceCo.getId(), context);
        int aoCount = 0;
        for (FormatOfferingInfo sourceFo : foInfos) {
            FormatOfferingInfo targetFo = _RCO_createTargetFormatOffering(sourceFo, targetCo, targetTermId, context);

            //Pass in some context attributes so these values don't need to be looked up again
            List<AttributeInfo> originalContextAttributes = context.getAttributes();
            List<AttributeInfo> newContextAttributes = new ArrayList<AttributeInfo>(originalContextAttributes);
            newContextAttributes.add(new AttributeInfo("FOId",sourceFo.getId()));
            newContextAttributes.add(new AttributeInfo("FOShortName",sourceFo.getShortName()));
            newContextAttributes.add(new AttributeInfo("COId",sourceCo.getId()));
            newContextAttributes.add(new AttributeInfo("COCode",sourceCo.getCourseCode()));
            newContextAttributes.add(new AttributeInfo("COLongName",sourceCo.getCourseOfferingTitle()));
            context.setAttributes(newContextAttributes);

            //Make the call with the additional contextAttributes
            List<ActivityOfferingInfo> aoInfoList = this.getCoService().getActivityOfferingsByFormatOffering(sourceFo.getId(), context);

            //Reset the attributes to avoid side affects
            context.setAttributes(originalContextAttributes);

            Map<String, String> sourceAoIdToTargetAoId = new HashMap<String, String>();
            for (ActivityOfferingInfo sourceAo : aoInfoList) {
                if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY) &&
                        StringUtils.equals(sourceAo.getTypeKey(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)) {
                    continue;
                }
                ActivityOfferingInfo targetAo =
                        _RCO_createTargetActivityOffering(sourceAo, targetFo, targetTermId, optionKeys, context);
                sourceAoIdToTargetAoId.put(sourceAo.getId(), targetAo.getId());

                if (!optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
                    if(sourceAo.getScheduleId() != null && !sourceAo.getScheduleId().isEmpty()) {
                        _RCO_rolloverScheduleToScheduleRequest(sourceAo, targetAo, context);
                    }
                }
                _RCO_rolloverSeatpools(sourceAo, targetAo, context);

                aoCount++;
            }
            _RCO_rolloverRegistrationGroups(targetCo, sourceFo, targetFo, context, sourceAoIdToTargetAoId);
        }
        SocRolloverResultItemInfo item = new SocRolloverResultItemInfo();
        item.setSourceCourseOfferingId(sourceCoId);
        item.setTypeKey(CourseOfferingSetServiceConstants.CREATE_RESULT_ITEM_TYPE_KEY);
        item.setStateKey(CourseOfferingSetServiceConstants.CREATED_RESULT_ITEM_STATE_KEY);
        item.setTargetCourseOfferingId(targetCo.getId());
        AttributeInfo aoCountAttr = new AttributeInfo();
        item.getAttributes().add(aoCountAttr);
        aoCountAttr.setKey(CourseOfferingSetServiceConstants.ACTIVITY_OFFERINGS_CREATED_SOC_ITEM_DYNAMIC_ATTRIBUTE);
        aoCountAttr.setValue("" + aoCount);
        return item;
    }

    private CourseOfferingInfo _RCO_createTargetCourseOffering(CourseOfferingInfo sourceCo, String targetTermId, CourseInfo targetCourse, List<String> optionKeys, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        CourseOfferingInfo targetCo = new CourseOfferingInfo(sourceCo);
        targetCo.setId(null);
        // clear out the ids on the internal sub-objects too
        for (OfferingInstructorInfo instr : targetCo.getInstructors()) {
            instr.setId(null);
        }
//        for (RevenueInfo rev : targetCo.getRevenues()) {
//            rev.setId(null);
//        }
//        for (FeeInfo fee : targetCo.getFees()) {
//            fee.setId(null);
//        }
        for (AttributeInfo attr : targetCo.getAttributes()) {
            attr.setId(null);
        }
        targetCo.setTermId(targetTermId);
        targetCo.setMeta(null);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
            targetCo.getInstructors().clear();
        }
        targetCo.setCourseId(targetCourse.getId());
        if (optionKeys.contains(CourseOfferingSetServiceConstants.USE_CANONICAL_OPTION_KEY)) {
            // copy from cannonical
            CourseOfferingTransformer coTransformer = new CourseOfferingTransformer();
            coTransformer.copyFromCanonical(targetCourse, targetCo, optionKeys, context);
        }
        // Rolled over CO should be in draft state
        targetCo.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        targetCo = this._getCoService().createCourseOffering(targetCo.getCourseId(), targetCo.getTermId(), targetCo.getTypeKey(),
                targetCo, optionKeys, context);
        return targetCo;
    }

    private void _RCO_rolloverRegistrationGroups(CourseOfferingInfo targetCo, FormatOfferingInfo sourceFo, FormatOfferingInfo targetFo, ContextInfo context, Map<String, String> sourceAoIdToTargetAoId) throws OperationFailedException {
        // re-generating Reg Groups for given FO
        try {
            List<RegistrationGroupInfo> regGroups = this._getCoService().getRegistrationGroupsByFormatOffering(sourceFo.getId(), context);
            if (regGroups != null && !regGroups.isEmpty()) {
                for (RegistrationGroupInfo sourceRg : regGroups) {
                    RegistrationGroupInfo targetRg = new RegistrationGroupInfo();
                    targetRg.setId(null);
                    targetRg.setCourseOfferingId(targetCo.getId());
                    targetRg.setDescr(sourceRg.getDescr());
                    targetRg.setActivityOfferingClusterId(sourceRg.getActivityOfferingClusterId());
                    targetRg.setFormatOfferingId(targetFo.getId());
                    targetRg.setIsGenerated(sourceRg.getIsGenerated());
                    targetRg.setName(sourceRg.getName());
                    targetRg.setRegistrationCode(null);
                    targetRg.setTermId(targetFo.getTermId());
                    targetRg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
                    targetRg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);

                    List<String> sourceAoIdList = sourceRg.getActivityOfferingIds();
                    List<String> targetAoIdList = new ArrayList<String>();
                    if (sourceAoIdList != null && !sourceAoIdList.isEmpty()) {
                        for (String sourceAoId : sourceAoIdList) {
                            String tempTargetAoId = sourceAoIdToTargetAoId.get(sourceAoId);
                            if (tempTargetAoId != null) {
                                targetAoIdList.add(tempTargetAoId);
                            }
                        }
                        targetRg.setActivityOfferingIds(targetAoIdList);
                    }

                    RegistrationGroupInfo rgInfo = this._getCoService().createRegistrationGroup(targetFo.getId(), targetRg.getActivityOfferingClusterId(),
                            LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, targetRg, context);

                }
            }
        } catch (Exception e) {
            throw new OperationFailedException("problem generating reg. groups", e);
        }
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        CourseOfferingInfo co = this._getCoService().getCourseOffering(courseOfferingId, context);
        CourseInfo course = new R1CourseServiceHelper(courseService, acalService).getCourse(co.getCourseId());
        // copy from canonical
        CourseOfferingTransformer coTransformer = new CourseOfferingTransformer();
        coTransformer.copyFromCanonical(course, co, optionKeys, context);
        try {
            return this._getCoService().updateCourseOffering(courseOfferingId, co, context);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        // TODO: continue traversing down the formats and activities updating from the canonical
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo,
                                                                          List<String> optionKeys, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        CourseInfo course = new R1CourseServiceHelper(courseService, acalService).getCourse(courseOfferingInfo.getCourseId());
        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY)) {
            results.addAll(this._compare("CourseTitle", course.getCourseTitle(), courseOfferingInfo.getCourseOfferingTitle(), null));
        }
        results.addAll(this._compare("Code", course.getCode(), courseOfferingInfo.getCourseOfferingCode(), null));

        if (optionKeys.contains(CourseOfferingSetServiceConstants.CREDITS_MATCH_SCHEDULED_HOURS_OPTION_KEY)) {
            results.addAll(compareCreditsToSchedule(course, courseOfferingInfo));
        }
        return results;
    }

    private List<ValidationResultInfo> _compare(String element, String courseValue, String coValue, String message) {
        if (courseValue == null && coValue == null) {
            return Collections.emptyList();
        }
        if (courseValue.equals(coValue)) {
            return Collections.emptyList();
        }
        ValidationResultInfo result = new ValidationResultInfo();
        result.setElement(element);
        result.setLevel(ErrorLevel.ERROR);
        result.setMessage(message);
        return Arrays.asList(result);
    }

    protected List<ValidationResultInfo> compareCreditsToSchedule(CourseInfo course, CourseOfferingInfo co) {
        // TODO: implement this complex logic
        // This is protected because it is explected that implementing instituations will vary widely in this implementation
        return Collections.emptyList();
    }

    private String _findFirstExistingCourseOfferingIdInTargetTerm(String targetCourseId, String targetTermId, ContextInfo context)
            throws DoesNotExistException, OperationFailedException {
        List<CourseOfferingInfo> list;
        try {
            list = this._getCoService().getCourseOfferingsByCourseAndTerm(targetCourseId, targetTermId, context);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0).getId();
    }

    /*
     * Note: The Registration Group Code is what the admnin's want to see the reg groups on a per course offering basis.
     * 
     * The Registration Code will be a globally unique key used during registration and exactly how this works is not yet defined (see the null below).
     */
    private RegistrationGroupInfo _makeRegGroup(String regGroupCode, List<String> activityOfferingPermutation, FormatOfferingInfo formatOffering, String activityOfferingClusterId) {
        RegistrationGroupInfo rg = new RegistrationGroupInfo();

        rg.setActivityOfferingIds(activityOfferingPermutation);
        rg.setCourseOfferingId(formatOffering.getCourseOfferingId());
        rg.setDescr(new RichTextInfo(regGroupCode, regGroupCode));
        rg.setFormatOfferingId(formatOffering.getId());
        rg.setActivityOfferingClusterId(activityOfferingClusterId);
        rg.setIsGenerated(true);
        rg.setName(regGroupCode);
        rg.setRegistrationCode(null);
        rg.setTermId(formatOffering.getTermId());
        rg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        return rg;
    }

    /*
    * The core generation logic should work with in the impl as well.
    */
    @Override
    public StatusInfo generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
                   MissingParameterException, OperationFailedException,
                   PermissionDeniedException, DataValidationErrorException {

        // check for any existing registration groups
        this._getCoService(); // Make sure coService gets set

        // verify we are allowed to do this.
        boolean generated = false;
        List<String> aocIdList = coService.getActivityOfferingClustersIdsByFormatOffering(formatOfferingId, contextInfo);

        if (aocIdList.isEmpty()) {
            throw new DoesNotExistException("No ActivityOfferingCluster's exist for formatOfferingId = " + formatOfferingId);
        }

        for (String aocId : aocIdList) {
            try {
                StatusInfo status = generateRegistrationGroupsForCluster(aocId, contextInfo);
                generated = status.getIsSuccess();
            } catch (Exception e) {
                throw new OperationFailedException("formatOfferingId = " + formatOfferingId + ": failed to generate reg groups for activityOfferingClusterId = " + aocId, e);
            }
        }

        StatusInfo success = new StatusInfo();
        success.setSuccess(generated);
        return success;
    }

    private boolean _clusterHasIncompleteRegGroups(ActivityOfferingClusterInfo cluster,
                                                   List<RegistrationGroupInfo> currentRGs) {
        List<Integer> permutationCounter = new ArrayList<Integer>(cluster.getActivityOfferingSets().size());
        for (int i = 0; i < permutationCounter.size(); i++) {
            // set index to all 0's to start
            permutationCounter.set(i, 0);
        }
        return false;
    }

    @Override
    public StatusInfo generateRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
                   MissingParameterException, OperationFailedException, PermissionDeniedException {
        // check for any existing registration groups
        this._getCoService(); // Make sure coService gets set

        // Run a basic validation to see if each AOset is non-empty.  If there is an empty set, throw exception.
        AOClusterVerifyResultsInfo result =
                coService.verifyActivityOfferingClusterForGeneration(activityOfferingClusterId, contextInfo);
        List<ValidationResultInfo> resultInfos = result.getValidationResults();
        for (ValidationResultInfo vri: resultInfos) {
            if (vri.isError()) {
                throw new DataValidationErrorException("One or more AOsets in the cluster is empty--can't generate reg groups");
            }
        }

        // TODO: this should be moved to the validation decorator in the verify method
        List<RegistrationGroupInfo> existingRegistrationGroups =
                coService.getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);

        if (!existingRegistrationGroups.isEmpty()) {
            // for M4 compatibility
            // should be removed once M5 work starts as the delta add should be supported
            // and cascaded delete on an AO should remove the reg group.
            coService.deleteRegistrationGroupsForCluster(activityOfferingClusterId, contextInfo);

        }

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();
        ActivityOfferingClusterInfo aoc = coService.getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        List<String> typeList = extractTypes(aoc.getActivityOfferingSets());
        Map<String, List<String>> activityOfferingTypeToOfferingMap = _extractActivityOfferingMap(aoc.getActivityOfferingSets());
        List<List<String>> generatedPermutations = new ArrayList<List<String>>();

        PermutationUtils.generatePermutations(typeList,
                new ArrayList<String>(),
                activityOfferingTypeToOfferingMap,
                generatedPermutations);

        // New instance created each time if desired
        RegistrationGroupCodeGenerator generator =
                registrationCodeGeneratorFactory.makeCodeGenerator();

        FormatOfferingInfo fo = coService.getFormatOffering(aoc.getFormatOfferingId(), contextInfo);
        List<ActivityOfferingInfo> aoList = coService.getActivityOfferingsByCluster(activityOfferingClusterId, contextInfo);

        generator.initializeGenerator(coService, fo, contextInfo, null);
        for (List<String> activityOfferingPermutation : generatedPermutations) {
            String regGroupCode = generator.generateRegistrationGroupCode(fo, aoList, null);
            // Honours Offering and max enrollment is out of scope for M4 so this hard set is ok.
            String name = regGroupCode;
            RegistrationGroupInfo rg = _makeRegGroup(regGroupCode, activityOfferingPermutation, fo, aoc.getId());

            try {
                RegistrationGroupInfo rgInfo = coService.createRegistrationGroup(aoc.getFormatOfferingId(), aoc.getId(),
                        LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rg, contextInfo);

                regGroupList.add(rgInfo);
            } catch (DataValidationErrorException e) {
                throw new OperationFailedException("Failed to validate registration group", e);

            } catch (ReadOnlyException e) {
                throw new OperationFailedException("Failed to write registration group", e);
            }
        }

        StatusInfo success = new StatusInfo();
        success.setSuccess(true);
        return success;
    }

    private Map<String, List<String>> _extractActivityOfferingMap(List<ActivityOfferingSetInfo> activityOfferingSets) {
        Map<String, List<String>> aoTypeToListMap = new LinkedHashMap<String, List<String>>();

        for (ActivityOfferingSetInfo aoSet : activityOfferingSets) {
            for (String aoId : aoSet.getActivityOfferingIds()) {
                List<String> aoIdList = aoTypeToListMap.get(aoSet.getActivityOfferingType());

                if (aoIdList == null) {
                    aoIdList = new ArrayList<String>();
                    aoTypeToListMap.put(aoSet.getActivityOfferingType(), aoIdList);
                }

                aoIdList.add(aoId);
            }
        }
        return aoTypeToListMap;
    }

    private List<String> extractTypes(
            List<ActivityOfferingSetInfo> activityOfferingSets) {
        List<String> typeList = new ArrayList<String>();

        for (ActivityOfferingSetInfo activityOfferingSetInfo : activityOfferingSets) {

            typeList.add(activityOfferingSetInfo.getActivityOfferingType());
        }
        return typeList;
    }


}
