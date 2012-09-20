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
import org.kuali.student.enrollment.lui.dto.LuiInfo;
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

    private CourseOfferingService _getCoService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    @Override
    public SocRolloverResultItemInfo rolloverCourseOffering(String sourceCoId,
                                                            String targetTermId,
                                                            List<String> optionKeys,
                                                            ContextInfo context) throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
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
            if (!targetCourse.getState().equals("Active")) {
                throw new DataValidationErrorException("skipped because canonical course is no longer active");
            }
        }
        if (optionKeys.contains(CourseOfferingSetServiceConstants.IF_NO_NEW_VERSION_OPTION_KEY)) {
            if (!sourceCourse.getId().equals(targetCourse.getId())) {
                throw new DataValidationErrorException("skipped because there is a new version of the canonical course");
            }
        }
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
        List<FormatOfferingInfo> foInfos = this._getCoService().getFormatOfferingsByCourseOffering(sourceCo.getId(), context);
        int aoCount = 0;
        for (FormatOfferingInfo sourceFo : foInfos) {
            FormatOfferingInfo targetFo = new FormatOfferingInfo(sourceFo);
            targetFo.setId(null);
            // clear out the ids on the internal sub-objects
            for (AttributeInfo attr : targetFo.getAttributes()) {
                attr.setId(null);
            }
            targetFo.setCourseOfferingId(targetCo.getId());
            targetFo.setTermId(targetTermId);
            targetFo.setMeta(null);
            CourseOfferingService locoService = this.getCoService();
            // Rolled over FO should be in planned state
            targetFo.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
            targetFo = locoService.createFormatOffering(targetFo.getCourseOfferingId(), targetFo.getFormatId(),
                    targetFo.getTypeKey(), targetFo, context);

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
            List<ActivityOfferingInfo> aoInfoList = locoService.getActivityOfferingsByFormatOffering(sourceFo.getId(), context);

            //Reset the attributes to avoid side affects
            context.setAttributes(originalContextAttributes);

            Map<String, String> sourceAoIdToTargetAoId = new HashMap<String, String>();
            for (ActivityOfferingInfo sourceAo : aoInfoList) {
                if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY) &&
                        StringUtils.equals(sourceAo.getTypeKey(), LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)) {
                    continue;
                }
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
                if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
                    targetAo.setScheduleId(null);
                    // TODO: set the schedule request to null as well
                }
                if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
                    targetAo.getInstructors().clear();
                }
                // Rolled over AO should be in draft state
                targetAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                targetAo = this._getCoService().createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(),
                        targetAo.getTypeKey(), targetAo, context);
                sourceAoIdToTargetAoId.put(sourceAo.getId(), targetAo.getId());
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
                aoCount++;
            }
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

    private CourseOfferingInfo generateTargetCourseOffering(CourseOfferingInfo sourceCo, String targetTermId, List<String> optionKeys, ContextInfo context)
            throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException {
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

        // Rolled over CO should be in draft state
        targetCo.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        targetCo = this._getCoService().createCourseOffering(targetCo.getCourseId(), targetCo.getTermId(), targetCo.getTypeKey(),
                targetCo, optionKeys, context);
        List<FormatOfferingInfo> foInfos = this._getCoService().getFormatOfferingsByCourseOffering(sourceCo.getId(), context);

        for (FormatOfferingInfo sourceFo : foInfos) {
            FormatOfferingInfo targetFo = new FormatOfferingInfo(sourceFo);
            targetFo.setId(null);
            // clear out the ids on the internal sub-objects
            for (AttributeInfo attr : targetFo.getAttributes()) {
                attr.setId(null);
            }
            targetFo.setCourseOfferingId(targetCo.getId());
            targetFo.setTermId(targetTermId);
            targetFo.setMeta(null);
            CourseOfferingService locoService = this.getCoService();
            // Rolled over FO should be in draft state
            targetFo.setStateKey(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
            targetFo = locoService.createFormatOffering(targetFo.getCourseOfferingId(), targetFo.getFormatId(),
                    targetFo.getTypeKey(), targetFo, context);
            List<ActivityOfferingInfo> aoInfoList = locoService.getActivityOfferingsByFormatOffering(sourceFo.getId(), context);
            for (ActivityOfferingInfo sourceAo : aoInfoList) {
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
                targetAo.setMeta(null);
                if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
                    targetAo.setScheduleId(null);
                    // TODO: set the schedule request to null as well
                }
                if (optionKeys.contains(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY)) {
                    targetAo.getInstructors().clear();
                }
                // Rolled over AO should be in draft state
                targetAo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                targetAo = this._getCoService().createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(),
                        targetAo.getTypeKey(), targetAo, context);
            }
        }

        return targetCo;
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        CourseOfferingInfo co = this._getCoService().getCourseOffering(courseOfferingId, context);
        CourseInfo course = new R1CourseServiceHelper(courseService, acalService).getCourse(co.getCourseId());
        // copy from cannonical
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
            return Collections.EMPTY_LIST;
        }
        if (courseValue.equals(coValue)) {
            return Collections.EMPTY_LIST;
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
        return Collections.EMPTY_LIST;
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

    // TODO: Delete method once M5 is further completed
    private Map<String, List<String>> _m4ConstructActivityOfferingTypeToAvailableActivityOfferingMap(List<ActivityOfferingInfo> aoList) {
        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap = new HashMap<String, List<String>>();

        for (ActivityOfferingInfo info : aoList) {
            String activityType = info.getTypeKey();
            List<String> activityList = activityOfferingTypeToAvailableActivityOfferingMap
                    .get(activityType);

            if (activityList == null) {
                activityList = new ArrayList<String>();
                activityOfferingTypeToAvailableActivityOfferingMap.put(
                        activityType, activityList);
            }

            activityList.add(info.getId());

        }
        return activityOfferingTypeToAvailableActivityOfferingMap;
    }

    // TODO: Delete method once M5 is further completed
    private RegistrationGroupInfo _m4MakeRegGroup(String regGroupCode, List<String> activityOfferingPermuation, FormatOfferingInfo formatOffering) {
        RegistrationGroupInfo rg = new RegistrationGroupInfo();

        rg.setActivityOfferingIds(activityOfferingPermuation);
        rg.setCourseOfferingId(formatOffering.getCourseOfferingId());
        rg.setDescr(new RichTextInfo(regGroupCode, regGroupCode));
        rg.setFormatOfferingId(formatOffering.getId());
        rg.setIsGenerated(true);
        rg.setName(regGroupCode);
        rg.setRegistrationCode(null);
        rg.setTermId(formatOffering.getTermId());
        rg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        return rg;
    }

    // TODO: Delete method once M5 is further completed
    private StatusInfo _m4GenerateRegGroups(String formatOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        this._getCoService();
        List<RegistrationGroupInfo> existingRegistrationGroups =
                coService.getRegistrationGroupsByFormatOffering(formatOfferingId, context);
        if (existingRegistrationGroups.size() > 0) {
            //throw new AlreadyExistsException("Registration groups already exist for formatOfferingId=" + formatOfferingId);
            coService.deleteRegistrationGroupsByFormatOffering(formatOfferingId, context);
        }
        FormatOfferingInfo formatOffering = coService.getFormatOffering(formatOfferingId, context);

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();
        List<ActivityOfferingInfo> aoList = coService.getActivityOfferingsByFormatOffering(
                formatOfferingId, context);

        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap =
                _m4ConstructActivityOfferingTypeToAvailableActivityOfferingMap(aoList);

        List<List<String>> generatedPermutations = new ArrayList<List<String>>();

        PermutationUtils.generatePermutations(new ArrayList<String>(
                activityOfferingTypeToAvailableActivityOfferingMap.keySet()),
                new ArrayList<String>(),
                activityOfferingTypeToAvailableActivityOfferingMap,
                generatedPermutations);

        CourseOfferingInfo courseOffering = coService.getCourseOffering(formatOffering.getCourseOfferingId(), context);

        // New instance created each time if desired
        RegistrationGroupCodeGenerator generator =
                registrationCodeGeneratorFactory.makeCodeGenerator();
        generator.initializeGenerator(coService, formatOffering, context, null);

        for (List<String> activityOfferingPermutation : generatedPermutations) {

            String regGroupCode = generator.generateRegistrationGroupCode(formatOffering, aoList, null);
            // Honours Offering and max enrollment is out of scope for M4 so this hard set is ok.
            String name = regGroupCode;
            RegistrationGroupInfo rg = _m4MakeRegGroup(regGroupCode, activityOfferingPermutation, formatOffering);

            try {
                // createRegistrationGroup now takes a cluster id, but it is currently unused.
                RegistrationGroupInfo rgInfo = coService.createRegistrationGroup(formatOfferingId, null,
                        LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rg, context);

                regGroupList.add(rgInfo);
            } catch (DataValidationErrorException e) {
                throw new OperationFailedException(
                        "Failed to validate registration group", e);

            } catch (ReadOnlyException e) {
                throw new OperationFailedException(
                        "Failed to write registration group", e);
            }
        }
        StatusInfo success = new StatusInfo();
        success.setSuccess(Boolean.TRUE);
        return success;
    }
    /*
    * The core generation logic should work with in the impl as well.
    */
    @Override
    public StatusInfo generateRegistrationGroupsForFormatOffering(
            String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException {

//        if (true) { // TODO: Remove this if-block when M5 is further down the line
//            return _m4GenerateRegGroups(formatOfferingId, contextInfo);
//        }
        // TODO: Original code is below--see above TODO
        // check for any existing registration groups
        this._getCoService(); // Make sure coService gets set

        // verify we are allowed to do this.

        boolean generated = false;
        List<String> aocIdList = coService.getActivityOfferingClustersIdsByFormatOffering(formatOfferingId, contextInfo);

        if (aocIdList.size() == 0)
            throw new DoesNotExistException("No ActivityOfferingCluster's exist for formatOfferingId = " + formatOfferingId);

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

    @Override
    public StatusInfo generateRegistrationGroupsForCluster(
            @WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // check for any existing registration groups
        this._getCoService(); // Make sure coService gets set

        // TODO: this should be moved to the validation decorator in the verify method
        List<RegistrationGroupInfo> existingRegistrationGroups =
                coService.getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);

        if (existingRegistrationGroups.size() > 0) {
            // for M4 compatibility
            // should be removed once M5 work starts as the delta add should be supported
            // and cascaded delete on an AO should remove the reg group.
            coService.deleteRegistrationGroupsForCluster(activityOfferingClusterId, contextInfo);

        }

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();

        ActivityOfferingClusterInfo aoc = coService.getActivityOfferingCluster(activityOfferingClusterId, contextInfo);

        List<String> typeList = extractTypes(aoc.getActivityOfferingSets());

        Map<String, List<String>> activityOfferingTypeToOfferingMap = extractActivityOfferingMap(aoc.getActivityOfferingSets());

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
                throw new OperationFailedException(
                        "Failed to validate registration group", e);

            } catch (ReadOnlyException e) {
                throw new OperationFailedException(
                        "Failed to write registration group", e);
            }
        }

        StatusInfo success = new StatusInfo();

        success.setSuccess(true);

        return success;
    }

    private Map<String, List<String>> extractActivityOfferingMap(
            List<ActivityOfferingSetInfo> activityOfferingSets) {
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
