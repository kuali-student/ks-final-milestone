package org.kuali.student.enrollment.class2.registration.admin.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.messages.MessageService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.service.impl.PersonServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationIssueItem;
import org.kuali.student.enrollment.class2.registration.admin.service.AdminRegistrationViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegClientCache;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegResourceLoader;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegistrationUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.registration.client.service.dto.ConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationValidationConflictCourseResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationValidationResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * <p/>
 * Implementation of the AdminRegistrationViewHelperService that contains helper methods that support the Admin Reg Controller.
 */
public class AdminRegistrationViewHelperServiceImpl extends KSViewHelperServiceImpl implements AdminRegistrationViewHelperService {

    @Override
    public PersonInfo getStudentById(String studentId) {

        if (StringUtils.isBlank(studentId)) {
            GlobalVariables.getMessageMap().putError(AdminRegConstants.PERSON_ID, AdminRegConstants.ADMIN_REG_MSG_ERROR_STUDENT_REQUIRED);
            return null;
        }

        try {
            PersonInfo personInfo = AdminRegResourceLoader.getPersonService().getPerson(studentId.toUpperCase(), createContextInfo());

            Boolean validStudent = false;
            List<PersonAffiliationInfo> personAffiliationInfos = AdminRegResourceLoader.getPersonService().getPersonAffiliationsByPerson(personInfo.getId(), createContextInfo());
            for (PersonAffiliationInfo personAffiliationInfo : personAffiliationInfos) {
                if (personAffiliationInfo.getTypeKey().equals(PersonServiceConstants.PERSON_AFFILIATION_TYPE_PREFIX + AdminRegConstants.STUDENT_AFFILIATION_TYPE_CODE.toLowerCase())) {
                    validStudent = true;
                }
            }
            if (!validStudent) {
                GlobalVariables.getMessageMap().putError(AdminRegConstants.PERSON_ID, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_STUDENT, studentId);
                return null;
            }
            return personInfo;
        } catch (DoesNotExistException dne) {
            GlobalVariables.getMessageMap().putError(AdminRegConstants.PERSON_ID, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_STUDENT, studentId);
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }

        return null;
    }

    @Override
    public TermInfo getTermByCode(String termCode) {

        if (StringUtils.isBlank(termCode)) {
            GlobalVariables.getMessageMap().putError(AdminRegConstants.TERM_CODE, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_TERM);
            return null;
        }

        try {
            TermInfo term = AdminRegClientCache.getTermByCode(termCode);
            if (term == null) {
                GlobalVariables.getMessageMap().putError(AdminRegConstants.TERM_CODE, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_TERM);
            }
            return term;
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
    }

    @Override
    public List<RegistrationCourse> getCourseRegForStudentAndTerm(String studentId, String termCode) {

        List<RegistrationCourse> registeredCourses = new ArrayList<RegistrationCourse>();

        try {
            //Uses the StudentId and Term entered on the screen to retrieve existing CourseRegistrations for that specific student
            List<CourseRegistrationInfo> courseRegistrationInfos = AdminRegResourceLoader.getCourseRegistrationService().getCourseRegistrationsByStudentAndTerm(
                    studentId, termCode, createContextInfo());

            for (CourseRegistrationInfo courseRegInfo : courseRegistrationInfos) {
                RegistrationCourse registeredCourse = createRegistrationCourse(courseRegInfo);
                //retrieves ActivityRegistrations for the existing RegisteredCourse
                List<ActivityRegistrationInfo> activityRegistrations = AdminRegResourceLoader.getCourseRegistrationService().getActivityRegistrationsForCourseRegistration(
                        courseRegInfo.getId(), createContextInfo());
                registeredCourse.setActivities(createRegistrationActivitiesFromList(activityRegistrations));
                registeredCourses.add(registeredCourse);
            }

        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
        return registeredCourses;
    }

    @Override
    public List<RegistrationCourse> getCourseWaitListForStudentAndTerm(String studentId, String termCode) {

        List<RegistrationCourse> waitListCourses = new ArrayList<RegistrationCourse>();

        try {
            //Using the student Id and term info to retrieve the existing waitlisted courses for that student
            List<CourseRegistrationInfo> courseWaitListInfos = AdminRegResourceLoader.getCourseWaitlistService().getCourseWaitListRegistrationsByStudentAndTerm(
                    studentId, termCode, createContextInfo());

            for (CourseRegistrationInfo courseWaitListInfo : courseWaitListInfos) {
                RegistrationCourse waitListCourse = createRegistrationCourse(courseWaitListInfo);

                //Getting the list of existing activities for waitlisted courses and adding it
                List<ActivityRegistrationInfo> activityRegistrations = AdminRegResourceLoader.getCourseWaitlistService().getActivityWaitListRegistrationsForCourseRegistration(
                        courseWaitListInfo.getId(), createContextInfo());
                waitListCourse.setActivities(createRegistrationActivitiesFromList(activityRegistrations));
                waitListCourses.add(waitListCourse);
            }

        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
        return waitListCourses;
    }

    /**
     * Creates Registration Course info based on CourseRegistrationInfo.
     *
     * @param courseRegistrationInfo
     * @return registrationCourse
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ParseException
     */
    private RegistrationCourse createRegistrationCourse(CourseRegistrationInfo courseRegistrationInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ParseException {

        CourseOfferingInfo coInfo = AdminRegResourceLoader.getCourseOfferingService().getCourseOffering(courseRegistrationInfo.getCourseOfferingId(), createContextInfo());

        RegistrationCourse registrationCourse = new RegistrationCourse();
        registrationCourse.setCode(coInfo.getCourseOfferingCode());
        registrationCourse.setTitle(coInfo.getCourseOfferingTitle());
        registrationCourse.setCredits(Integer.parseInt(coInfo.getCreditCnt()));
        registrationCourse.setTransactionalDate(courseRegistrationInfo.getMeta().getCreateTime());
        registrationCourse.setEffectiveDate(courseRegistrationInfo.getEffectiveDate());
        registrationCourse.setSection(AdminRegResourceLoader.getCourseOfferingService().getRegistrationGroup(
                courseRegistrationInfo.getRegistrationGroupId(), createContextInfo()).getRegistrationCode());
        registrationCourse.setGradingOption(CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(courseRegistrationInfo.getGradingOptionId()));
        return registrationCourse;
    }

    /**
     * Create Registration Activities based on the given list of activity registrations.
     *
     * @param activityRegistrations
     * @return List<RegistrationActivity> registrationActivities
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    private List<RegistrationActivity> createRegistrationActivitiesFromList(List<ActivityRegistrationInfo> activityRegistrations)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<RegistrationActivity> registrationActivities = new ArrayList<RegistrationActivity>();
        for (ActivityRegistrationInfo activityRegInfo : activityRegistrations) {
            ActivityOfferingInfo aoInfo = AdminRegResourceLoader.getCourseOfferingService().getActivityOffering(activityRegInfo.getActivityOfferingId(), createContextInfo());
            registrationActivities.add(createRegistrationActivity(aoInfo));
        }
        return registrationActivities;
    }

    @Override
    public void validateForRegistration(AdminRegistrationForm form) {

        for (int i = 0; i < form.getPendingCourses().size(); i++) {
            RegistrationCourse course = form.getPendingCourses().get(i);

            List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
            try {
                CourseOfferingInfo courseOffering = AdminRegClientCache.getCourseOfferingByCodeAndTerm(form.getTerm().getId(), course.getCode());
                if (courseOffering == null) {
                    GlobalVariables.getMessageMap().putError(AdminRegConstants.PENDING_COURSES + "[" + i + "]." + AdminRegConstants.CODE,
                            AdminRegConstants.ADMIN_REG_MSG_ERROR_COURSE_CODE_TERM_INVALID);
                    continue;
                }

                //First get the format offerings for the course offering to get the registration groups linked to the FO.
                List<FormatOfferingInfo> formatOfferings = AdminRegResourceLoader.getCourseOfferingService().getFormatOfferingsByCourseOffering(
                        courseOffering.getId(), ContextUtils.createDefaultContextInfo());
                for (FormatOfferingInfo formatOffering : formatOfferings) {
                    regGroups.addAll(AdminRegResourceLoader.getCourseOfferingService().getRegistrationGroupsByFormatOffering(
                            formatOffering.getId(), ContextUtils.createDefaultContextInfo()));
                }
            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }

            //Check if the input section matches a registration group.
            for (RegistrationGroupInfo regGroup : regGroups) {
                if (course.getSection().equals(regGroup.getName())) {
                    course.setRegGroup(regGroup);
                    break;
                }
            }

            //Add error message when no registration group was found for given section.
            if (course.getRegGroup() == null) {
                GlobalVariables.getMessageMap().putError(AdminRegConstants.PENDING_COURSES + "[" + i + "]" + AdminRegConstants.SECTION,
                        AdminRegConstants.ADMIN_REG_MSG_ERROR_SECTION_CODE_INVALID);
            }
        }
    }

    @Override
    public List<RegistrationActivity> getRegistrationActivitiesForRegistrationCourse(RegistrationCourse registrationCourse, String termCode) {

        List<RegistrationActivity> registrationActivities = new ArrayList<RegistrationActivity>();
        try {
            List<ActivityOfferingInfo> activityOfferings = AdminRegResourceLoader.getCourseOfferingService().getActivityOfferingsByIds(
                    registrationCourse.getRegGroup().getActivityOfferingIds(), createContextInfo());
            for (ActivityOfferingInfo activityOffering : activityOfferings) {
                registrationActivities.add(createRegistrationActivity(activityOffering));
            }
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }

        return registrationActivities;
    }

    /**
     * Create a single Registration Activity based on a activity offering.
     *
     * @param activityOffering
     * @return registrationActivity
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private RegistrationActivity createRegistrationActivity(ActivityOfferingInfo activityOffering)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationActivity regActivity = new RegistrationActivity();

        // Assume only zero or one (should never be more than 1 until we support partial colo)
        OfferingInstructorInfo offeringInstructorInfo = CourseOfferingViewHelperUtil.findDisplayInstructor(activityOffering.getInstructors());
        regActivity.setInstructor(offeringInstructorInfo.getPersonName());
        regActivity.setType(activityOffering.getName());

        List<ScheduleInfo> scheduleInfos = AdminRegResourceLoader.getSchedulingService().getSchedulesByIds(activityOffering.getScheduleIds(), createContextInfo());

        StringBuilder dateTimeSchedule = new StringBuilder();
        StringBuilder roomBuildInfo = new StringBuilder();

        for (ScheduleInfo scheduleInfo : scheduleInfos) {
            /**
             * Until we implement external scheduler, there is going to be only one Schedule component for every scheduleinfo
             * and the UI doesn't allow us to add multiple components to a schedule request.
             */
            ScheduleComponentInfo componentInfo = KSCollectionUtils.getOptionalZeroElement(scheduleInfo.getScheduleComponents());

            if(!componentInfo.getIsTBA()){
                List<TimeSlotInfo> timeSlotInfos = AdminRegResourceLoader.getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), createContextInfo());
                // Assume only zero or one (should never be more than 1 until we support partial colo)
                TimeSlotInfo timeSlotInfo = KSCollectionUtils.getOptionalZeroElement(timeSlotInfos);

                dateTimeSchedule.append(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlotInfo.getWeekdays()));
                dateTimeSchedule.append(" ");

                dateTimeSchedule.append(TimeOfDayHelper.makeFormattedTimeForAOSchedules(timeSlotInfo.getStartTime()));
                dateTimeSchedule.append(" - ");
                dateTimeSchedule.append(TimeOfDayHelper.makeFormattedTimeForAOSchedules(timeSlotInfo.getEndTime()));
                regActivity.setDateTime(dateTimeSchedule.toString());
            }
            try {
                //Check if the room ID is null, if not get the buildingInfo from the room
                if(componentInfo.getRoomId() != null) {
                    RoomInfo room = AdminRegResourceLoader.getRoomService().getRoom(componentInfo.getRoomId(), createContextInfo());
                    //retrieve the buildingInfo from the Room.
                    BuildingInfo buildingInfo = AdminRegResourceLoader.getRoomService().getBuilding(room.getBuildingId(), createContextInfo());
                    roomBuildInfo.append(buildingInfo.getBuildingCode());
                    roomBuildInfo.append(" ");
                    roomBuildInfo.append(room.getRoomCode());
                    regActivity.setRoom(roomBuildInfo.toString());
                }

            } catch (Exception e) {
                throw new RuntimeException("Could not retrieve Room RoomService for " + e);
            }

        }
        return regActivity;
    }

    @Override
    public String submitRegistrationRequest(String studentId, String termId, List<RegistrationCourse> registrationCourses) {

        // get the regGroup
        //RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(null, termCode, courseCode, regGroupCode, regGroupId, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        //CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, rg.getTermId(), termCode);

        // verify passed credits (must be non-empty unless fixed) and grading option (can be null)
        //credits = verifyRegistrationRequestCreditsGradingOption(courseOfferingInfo, credits, gradingOptionId, contextInfo);

        //Create the request object
        RegistrationRequestInfo regRequest = AdminRegistrationUtil.createRegistrationRequest(studentId, termId, registrationCourses);

        try {
            // persist the request object in the service
            regRequest = AdminRegResourceLoader.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY, regRequest, createContextInfo());

            // submit the request to the registration engine.
            return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(regRequest.getId(), createContextInfo()).getId();
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

        return null;
    }

    @Override
    public RegistrationRequestInfo getRegistrationRequest(String regRequestId) {

        try {
            return AdminRegResourceLoader.getCourseRegistrationService().getRegistrationRequest(regRequestId, createContextInfo());
        } catch (Exception e) {
            convertServiceExceptionsToUI(e);
        }

        return null;
    }

    public List<RegistrationIssueItem> createIssueItemsFromResults(List<ValidationResultInfo> results) {
        List<RegistrationIssueItem> issueItems = new ArrayList<RegistrationIssueItem>();
        // Add the messages to the issue items list.
        for (ValidationResult validationResult : results) {
            RegistrationValidationResult result = RegistrationValidationResultsUtil.unmarshallResult(validationResult.getMessage());

            MessageService messageService = KRADServiceLocatorWeb.getMessageService();

            String message = messageService.getMessageText(null, null, result.getMessageKey());

            if (result instanceof RegistrationValidationConflictCourseResult) {
                RegistrationValidationConflictCourseResult conflictCourseResult = (RegistrationValidationConflictCourseResult)result;
                StringBuilder conflictCourses = new StringBuilder();
                for(ConflictCourseResult conflictCourse : conflictCourseResult.getConflictingCourses()){
                    if(conflictCourses.length()>0){
                        conflictCourses.append(", ");
                    }
                    conflictCourses.append(conflictCourse.getCourseCode());
                }
                message = MessageFormat.format(message, conflictCourses.toString());
            }

            issueItems.add(new RegistrationIssueItem(message));
        }
        return issueItems;
    }

    /**
     * This method is used for the course code suggest field on the input section on the client.
     *
     * @param termCode
     * @param courseCode
     * @return List<String> retrieveCourseCodes
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public List<String> retrieveCourseCodes(String termCode, String courseCode)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        if (courseCode == null || courseCode.isEmpty()) {
            return new ArrayList<String>();   // if nothing passed in, return empty list
        }

        TermInfo term = getTermByCode(termCode);
        if (term == null) {
            return new ArrayList<String>(); // cannot do search on an invalid term code
        }

        courseCode = courseCode.toUpperCase(); // force toUpper
        return AdminRegClientCache.retrieveCourseCodes(term.getId(), courseCode);
    }

    /**
     * This method is called on an ajax call from the client when a course code is entered in the input section.
     *
     * @param course
     * @param termId
     * @return String retrieveCourseTitle
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public String retrieveCourseTitle(RegistrationCourse course, String termId)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        String courseCode = course.getCode();
        if (courseCode == null || courseCode.isEmpty()) {
            course.setTitle(StringUtils.EMPTY);
        } else {

            CourseOfferingInfo courseOffering = AdminRegClientCache.getCourseOfferingByCodeAndTerm(termId, courseCode);
            if (courseOffering != null) {
                course.setTitle(courseOffering.getCourseOfferingTitle());
            } else {
                course.setTitle(StringUtils.EMPTY);
            }
        }

        return course.getTitle();
    }

}
