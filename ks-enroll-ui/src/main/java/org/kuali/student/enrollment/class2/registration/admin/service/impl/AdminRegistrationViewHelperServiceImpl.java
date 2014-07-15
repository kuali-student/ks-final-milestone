package org.kuali.student.enrollment.class2.registration.admin.service.impl;

import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.service.AdminRegistrationViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegistrationUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.infc.FormatOffering;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SW Genis on 2014/07/04.
 */
public class AdminRegistrationViewHelperServiceImpl extends KSViewHelperServiceImpl implements AdminRegistrationViewHelperService {

    private final static String CACHE_NAME = "AdminRegistrationCodeCache";

    @Override
    public void getRegistrationStatus() {

    }

    @Override
    public void submitRegistrationRequest() {

        // get the regGroup
        //RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(null, termCode, courseCode, regGroupCode, regGroupId, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        //CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, rg.getTermId(), termCode);

        // verify passed credits (must be non-empty unless fixed) and grading option (can be null)
        //credits = verifyRegistrationRequestCreditsGradingOption(courseOfferingInfo, credits, gradingOptionId, contextInfo);

        //Create the request object
        //RegistrationRequestInfo regReqInfo = createRegistrationRequest(contextInfo.getPrincipalId(), rg.getTermId(), rg.getRegGroupId(), null, credits, gradingOptionId, LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, LprServiceConstants.LPRTRANS_NEW_STATE_KEY, LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, okToWaitlist);

        // persist the request object in the service
        //RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        //return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }

    @Override
    public TermInfo getTermByCode(String termCode) {

        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

            qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

            QueryByCriteria criteria = qbcBuilder.build();

            List<TermInfo> terms = AdminRegistrationUtil.getAcademicCalendarService().searchForTerms(criteria, createContextInfo());
            int firstTerm = 0;
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError("termCode", AdminRegConstants.ADMIN_REG_MSG_ERROR_MULTIPLE_TERMS);
                return null;
            }
            if (terms.isEmpty()) {
                GlobalVariables.getMessageMap().putError("termCode", AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_TERM);
                return null;
            }
            return terms.get(firstTerm);
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
    }

    @Override
    public List<RegistrationCourse> getCourseRegForStudentAndTerm(String studentId, String termCode) {

        List<RegistrationCourse> registeredCourses = new ArrayList<RegistrationCourse>();

        try {

            List<CourseRegistrationInfo> courseRegistrationInfos = AdminRegistrationUtil.getCourseRegistrationService().getCourseRegistrationsByStudentAndTerm(studentId, termCode, createContextInfo());

            for (CourseRegistrationInfo courseRegInfo : courseRegistrationInfos) {
                RegistrationCourse registeredCourse = createRegistrationCourse(courseRegInfo);

                List<ActivityRegistrationInfo> activityRegistrations = AdminRegistrationUtil.getCourseRegistrationService().getActivityRegistrationsForCourseRegistration(courseRegInfo.getId(), createContextInfo());
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
            List<CourseRegistrationInfo> courseWaitListInfos = AdminRegistrationUtil.getCourseWaitlistService().getCourseWaitListRegistrationsByStudentAndTerm(studentId, termCode, createContextInfo());

            for (CourseRegistrationInfo courseWaitListInfo : courseWaitListInfos) {
                RegistrationCourse waitListCourse = createRegistrationCourse(courseWaitListInfo);

                List<ActivityRegistrationInfo> activityRegistrations = AdminRegistrationUtil.getCourseWaitlistService().getActivityWaitListRegistrationsForCourseRegistration(courseWaitListInfo.getId(), createContextInfo());
                waitListCourse.setActivities(createRegistrationActivitiesFromList(activityRegistrations));
                waitListCourses.add(waitListCourse);
            }

        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
        return waitListCourses;
    }

    private RegistrationCourse createRegistrationCourse(CourseRegistrationInfo courseWaitListInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CourseOfferingInfo coInfo = AdminRegistrationUtil.getCourseOfferingService().getCourseOffering(courseWaitListInfo.getCourseOfferingId(), createContextInfo());

        RegistrationCourse registrationCourse = new RegistrationCourse();
        registrationCourse.setCode(coInfo.getCourseOfferingCode());
        registrationCourse.setTitle(coInfo.getCourseOfferingTitle());
        registrationCourse.setCredits(Integer.parseInt(coInfo.getCreditCnt()));
        registrationCourse.setRegDate(courseWaitListInfo.getEffectiveDate());

        registrationCourse.setSection(AdminRegistrationUtil.getCourseOfferingService().getRegistrationGroup(courseWaitListInfo.getRegistrationGroupId(), createContextInfo()).getRegistrationCode());
        return registrationCourse;
    }

    /**
     * Method accpets RegistrationCourse and CourseRegistrationInfo
     * to retrieve the ActivitiesInfo
     *
     * @param activityRegistrations
     * @return
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
            ActivityOfferingInfo aoInfo = AdminRegistrationUtil.getCourseOfferingService().getActivityOffering(activityRegInfo.getActivityOfferingId(), createContextInfo());
            registrationActivities.add(createRegistrationActivity(aoInfo));
        }
        return registrationActivities;
    }

    @Override
    public List<RegistrationActivity> getRegistrationActivitiesForRegistrationCourse(RegistrationCourse registrationCourse, String termCode) {

        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        try {
            CourseOfferingInfo courseOffering = this.getCourseOfferingByCodeAndTerm(termCode, registrationCourse.getCode());
            List<FormatOfferingInfo> formatOfferings = AdminRegistrationUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(
                    courseOffering.getId(), ContextUtils.createDefaultContextInfo());
            for(FormatOfferingInfo formatOffering : formatOfferings) {
                regGroups.addAll(AdminRegistrationUtil.getCourseOfferingService().getRegistrationGroupsByFormatOffering(
                        formatOffering.getId(), ContextUtils.createDefaultContextInfo()));
            }
        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }

        RegistrationGroupInfo registrationGroup = null;
        for(RegistrationGroupInfo regGroup : regGroups){
            if(registrationCourse.getSection().equals(regGroup.getName())){
                registrationGroup = regGroup;
            }
        }

        List<RegistrationActivity> registrationActivities = new ArrayList<RegistrationActivity>();
        try {
            List<ActivityOfferingInfo> activityOfferings = AdminRegistrationUtil.getCourseOfferingService().getActivityOfferingsByIds(
                    registrationGroup.getActivityOfferingIds(), createContextInfo());
            for (ActivityOfferingInfo activityOffering : activityOfferings) {
                registrationActivities.add(createRegistrationActivity(activityOffering));
            }
        } catch (Exception e){
            throw convertServiceExceptionsToUI(e);
        }

        return registrationActivities;
    }

    private RegistrationActivity createRegistrationActivity(ActivityOfferingInfo activityOffering)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        RegistrationActivity regActivity = new RegistrationActivity();

        // Assume only zero or one (should never be more than 1 until we support partial colo)
        OfferingInstructorInfo offeringInstructorInfo = CourseOfferingViewHelperUtil.findDisplayInstructor(activityOffering.getInstructors());
        regActivity.setInstructor(offeringInstructorInfo.getPersonName());
        regActivity.setType(activityOffering.getName());

        List<ScheduleInfo> scheduleInfos = AdminRegistrationUtil.getSchedulingService().getSchedulesByIds(activityOffering.getScheduleIds(), createContextInfo());

        StringBuilder dateTimeSchedule = new StringBuilder();
        StringBuilder roomBuildInfo = new StringBuilder();

        for (ScheduleInfo scheduleInfo : scheduleInfos) {
            /**
             * Until we implement external scheduler, there is going to be only one Schedule component for every scheduleinfo
             * and the UI doesn't allow us to add multiple components to a schedule request.
             */
            ScheduleComponentInfo componentInfo = KSCollectionUtils.getOptionalZeroElement(scheduleInfo.getScheduleComponents());

            List<TimeSlotInfo> timeSlotInfos = AdminRegistrationUtil.getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), createContextInfo());
            // Assume only zero or one (should never be more than 1 until we support partial colo)
            TimeSlotInfo timeSlotInfo = KSCollectionUtils.getOptionalZeroElement(timeSlotInfos);

            dateTimeSchedule.append(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlotInfo.getWeekdays()));
            dateTimeSchedule.append(" ");

            dateTimeSchedule.append(TimeOfDayHelper.makeFormattedTimeForAOSchedules(timeSlotInfo.getStartTime()));
            dateTimeSchedule.append(" - ");
            dateTimeSchedule.append(TimeOfDayHelper.makeFormattedTimeForAOSchedules(timeSlotInfo.getEndTime()));
            regActivity.setDateTime(dateTimeSchedule.toString());

            try {
                RoomInfo room = AdminRegistrationUtil.getRoomService().getRoom(componentInfo.getRoomId(), createContextInfo());
                //retrieve the buildingInfo from the Room.
                BuildingInfo buildingInfo = AdminRegistrationUtil.getRoomService().getBuilding(room.getBuildingId(), createContextInfo());
                roomBuildInfo.append(buildingInfo.getBuildingCode());
                roomBuildInfo.append(" ");
                roomBuildInfo.append(room.getRoomCode());
                regActivity.setRoom(roomBuildInfo.toString());

            } catch (Exception e) {
                throw new RuntimeException("Could not retrieve Room RoomService for " + e);
            }

        }
        return regActivity;
    }

    @Override
    public void populateStudentInfo(AdminRegistrationForm form) throws Exception {

        Person person = AdminRegistrationUtil.getPersonService().getPerson(form.getStudentId());
        if ((person != null)) {

            if (!person.hasAffiliationOfType(AdminRegConstants.STUDENT_AFFILIATION_TYPE_CODE)) {
//                GlobalVariables.getMessageMap().putError(AdminRegConstants.STUDENT_INFO_SECTION_STUDENT_ID, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_STUDENT,form.getStudentId());
//                return;
            }
            form.setStudentName(person.getFirstName() + " " + person.getLastName());
        } else {
            GlobalVariables.getMessageMap().putError(AdminRegConstants.STUDENT_INFO_SECTION_STUDENT_ID, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_STUDENT, form.getStudentId());
        }
    }

    public List<String> retrieveCourseCodes(String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        if (courseCode == null || courseCode.isEmpty()) {
            return new ArrayList<String>();   // if nothing passed in, return empty list
        }

        courseCode = courseCode.toUpperCase(); // force toUpper
        return this.retrieveCourseCodesFromCache(termCode, courseCode);
    }

    public String retrieveCourseTitle(RegistrationCourse course, String termCode) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        String courseCode = course.getCode();
        if (courseCode == null || courseCode.isEmpty()) {
            course.setTitle(StringUtils.EMPTY);
        } else {

            CourseOfferingInfo courseOffering = this.getCourseOfferingByCodeAndTerm(termCode, courseCode);
            if (courseOffering != null) {
                course.setTitle(courseOffering.getCourseOfferingTitle());
            } else {
                course.setTitle(StringUtils.EMPTY);
            }
        }

        return course.getTitle();
    }

    /**
     * The premise of this is rather simple. Return a distinct list of course code. At a minimum there needs to
     * be one character. It then does a char% search. so E% will return all ENGL or any E* codes.
     * <p/>
     * This implementation is a little special. It's both cached and recursive.
     * <p/>
     * Because this is a structured search and course codes don't update often we can cache this pretty heavily and make
     * some assumptions that allow us to make this very efficient.
     * <p/>
     * So a user wants to type and see the type ahead results very quickly. The server wants as few db calls as possible.
     * The "bad" way to do this is to search on Every character entered. If we cache the searches then we'll get much
     * better performance. But we can go one step further because ths is a structured search. The first letter E in
     * ENGL will return EVERY course that starts with an E. So when you search for EN... why would you call the DB if
     * you have already called a search for E. So this uses recursion to build the searches. So, in the average case
     * you will only have to call a db search Once for Every first letter of the course codes.
     *
     * @return List of distinct course codes or an empty list
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    public List<String> retrieveCourseCodesFromCache(String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        List<String> results = new ArrayList<String>();
        MultiKey cacheKey = new MultiKey("suggest", termCode, courseCode);
        Element cachedResult = AdminRegistrationUtil.getCacheManager().getCache(CACHE_NAME).get(cacheKey);

        // only one character. This is the base search.
        if (cachedResult == null) {
            if (courseCode.length() == 1) {
                List<CourseOfferingInfo> searchResult = searchCourseOfferingsByCodeAndTerm(termCode, courseCode, true);
                for(CourseOfferingInfo courseOffering : searchResult){
                    results.add(courseOffering.getCourseOfferingCode());
                }
                AdminRegistrationUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, results));
                return results;
            }

            // This is where the recursion happens. If you entered CHEM and it didn't find anything it will
            // recurse and search for CHE -> CH -> C (C is the base). Each time building up the cache.
            // This for loop is the worst part of this method. I'd love to use some logic to remove the for loop.
            for (String searchedCode : retrieveCourseCodes(termCode, courseCode.substring(0, courseCode.length() - 1))) {
                // for every course code, see if it's part of the Match.
                if (searchedCode.startsWith(courseCode)) {
                    results.add(searchedCode);
                }
            }

            AdminRegistrationUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, results));
        } else {
            return (List<String>) cachedResult.getValue();
        }

        return results;
    }

    private CourseOfferingInfo getCourseOfferingByCodeAndTerm(String termCode, String courseCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(termCode, courseCode);
        Element cachedResult = AdminRegistrationUtil.getCacheManager().getCache(CACHE_NAME).get(cacheKey);
        if (cachedResult == null) {
            List<CourseOfferingInfo> courseOfferings = searchCourseOfferingsByCodeAndTerm(termCode, courseCode, false);
            return KSCollectionUtils.getOptionalZeroElement(courseOfferings);
        }

        return (CourseOfferingInfo) cachedResult.getValue();
    }

    /**
     * Does a search Query for course codes used for auto suggest
     *
     * @param courseCode the starting characters of a course code
     * @return a list of CourseCodeSuggestResults containing matching course codes
     */
    private List<CourseOfferingInfo> searchCourseOfferingsByCodeAndTerm(String termCode, String courseCode, boolean addWildCard)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        ContextInfo context = ContextUtils.createDefaultContextInfo();
        TermInfo term = this.getTermByCode(termCode);

        String searchCode = addWildCard ? courseCode + "*" : courseCode;

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", searchCode),
                PredicateFactory.equalIgnoreCase("atpId", term.getId())));
        QueryByCriteria criteria = qbcBuilder.build();

        List<CourseOfferingInfo> results = AdminRegistrationUtil.getCourseOfferingService().searchForCourseOfferings(criteria, context);
        for (CourseOfferingInfo result : results) {
            MultiKey cacheKey = new MultiKey(termCode, result.getCourseOfferingCode());
            AdminRegistrationUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, result));
        }
        return results;
    }

}
