/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfoExtended;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
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

/**
 *
 * @author nwright
 */
public class CourseOfferingServiceBusinessLogicImpl implements CourseOfferingServiceBusinessLogic  {

	private static final Logger log = Logger.getLogger(CourseOfferingServiceBusinessLogicImpl.class);
	
	@Resource
    private CourseService courseService;
    
	@Resource
	private AcademicCalendarService acalService;
    
	@Resource
	private CourseOfferingService coService;
    
    @Resource
	private RegistrationGroupCodeGenerator registrationCodeGenerator;
    
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
    public CourseOfferingInfo rolloverCourseOffering(String sourceCoId,
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
            String existingCoId = this.findFirstExistingCourseOfferingIdInTargetTerm(targetCourse.getId(), targetTermId, context);
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
            List<ActivityOfferingInfo> aoInfoList = locoService.getActivityOfferingsByFormatOffering(sourceFo.getId(), context);
            for (ActivityOfferingInfo sourceAo : aoInfoList) {
                if (optionKeys.contains(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY) &&
                    StringUtils.equals(sourceAo.getTypeKey(),LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY)){
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
                aoCount++;
            }
        }
        // TODO: Need to get more info out of this method.  Services may need better way to allow for flexibility in
        // TODO: returning content to adjust for changes in service calls
        CourseOfferingInfoExtended targetCoX = new CourseOfferingInfoExtended(targetCo);
        Map<String, Object> properties = targetCoX.getProperties();
        properties.put(CourseOfferingInfoExtended.ACTIVITY_OFFERINGS_CREATED, new Integer(aoCount));
        return targetCoX;
    }

    private CourseOfferingInfo generateTargetCourseOffering(CourseOfferingInfo sourceCo, String targetTermId, List<String> optionKeys, ContextInfo context)
        throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException{
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
            // Rolled over FO should be in planned state
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
            results.addAll(this.compare("CourseTitle", course.getCourseTitle(), courseOfferingInfo.getCourseOfferingTitle(), null));
        }
        results.addAll(this.compare("Code", course.getCode(), courseOfferingInfo.getCourseOfferingCode(), null));

        if (optionKeys.contains(CourseOfferingSetServiceConstants.CREDITS_MATCH_SCHEDULED_HOURS_OPTION_KEY)) {
            results.addAll(compareCreditsToSchedule(course, courseOfferingInfo));
        }
        return results;
    }

    private List<ValidationResultInfo> compare(String element, String courseValue, String coValue, String message) {
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

    private String findFirstExistingCourseOfferingIdInTargetTerm(String targetCourseId, String targetTermId, ContextInfo context)
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
     * Used to create a unique string for each activity offering id permutation.
     */
	private String createPermutationKey(List<String>activityOfferingIds) {
		
		Collections.sort(activityOfferingIds);
		
		String key = StringUtils.join(activityOfferingIds, "-");
		
		return key;
	}

    /*
	 * The core generation logic should work with in the impl aswell.
	 */
	@Override
	public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, AlreadyExistsException {

		// check for any existing registration groups
		
		List<RegistrationGroupInfo> existingRegistrationGroups = coService.getRegistrationGroupsByFormatOffering(formatOfferingId, context);
		
		if (existingRegistrationGroups.size() > 0)
			throw new AlreadyExistsException("Registration groups already exist for formatOfferingId=" + formatOfferingId);
		
		
		FormatOfferingInfo formatOffering = coService.getFormatOffering(formatOfferingId,
				context);

		
		List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();

		Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap = new HashMap<String, List<String>>();

		List<ActivityOfferingInfo> aoList = coService.getActivityOfferingsByFormatOffering(
				formatOfferingId, context);

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

		List<List<String>> generatedPermutations = new ArrayList<List<String>>();

		PermutationUtils.generatePermutations(new ArrayList<String>(
				activityOfferingTypeToAvailableActivityOfferingMap.keySet()),
				new ArrayList<String>(),
				activityOfferingTypeToAvailableActivityOfferingMap,
				generatedPermutations);

		CourseOfferingInfo courseOffering = coService.getCourseOffering(formatOffering.getCourseOfferingId(), context);
		
		
		for (List<String> activityOfferingPermuation : generatedPermutations) {

			String registrationCode = registrationCodeGenerator.generateRegistrationGroupCode(formatOffering, aoList);
			
			// Honours Offering and max enrollment is out of scope for M4 so this hard set is ok.
			String name = registrationCode;
			
			RegistrationGroupInfo rg = new RegistrationGroupInfo();

			rg.setActivityOfferingIds(activityOfferingPermuation);

			rg.setCourseOfferingId(formatOfferingId);
			rg.setDescr(new RichTextInfo(name, name));

			rg.setFormatOfferingId(formatOfferingId);

			rg.setIsGenerated(true);
			
			rg.setName(name);
			rg.setRegistrationCode(registrationCode);

			rg.setTermId(formatOffering.getTermId());

			rg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OPEN_STATE_KEY);
			
			rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
			
			// out of scope for M4.  Defined for the purposes of satisfying the dictionary
			rg.setIsHonorsOffering(false);
			rg.setMaximumEnrollment(100);
			

			try {
				coService.createRegistrationGroup(formatOfferingId,
						LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY, rg,
						context);

				regGroupList.add(rg);

			} catch (DataValidationErrorException e) {
				throw new OperationFailedException(
						"Failed to validate registration group", e);

			} catch (ReadOnlyException e) {
				throw new OperationFailedException(
						"Failed to write registration group", e);
			}


		}
		
		return regGroupList;
	}

   
}
