/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 1/20/14
 */
package org.kuali.student.enrollment.registration.client.service.impl.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.identity.principal.PrincipalQueryResults;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.rice.kim.impl.identity.entity.EntityBo;
import org.kuali.rice.kim.impl.identity.name.EntityNameBo;
import org.kuali.rice.kim.impl.identity.principal.PrincipalBo;
import org.kuali.rice.krad.data.DataObjectService;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesServiceConstants;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleComponentResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleActivityOfferingResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.enrollment.registration.client.service.exception.CourseDoesNotExistException;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.infc.TypeTypeRelation;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.SearchServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is util class with the common methods to be used in CourseRegistrationClientService and ScheduleOfClassesService
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationAndScheduleOfClassesUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationAndScheduleOfClassesUtil.class);

    /*
    Message keys
     */
    private static final String COURSE_CODE_AND_SECTION_REQUIRED_MESSAGE_KEY="kuali.cr.cart.message.course.code.and.section.required";
    private static final String COURSE_CODE_NOT_FOUND_MESSAGE_KEY="kuali.cr.cart.message.course.code.not.found";
    private static final String COURSE_CODE_REQUIRED_MESSAGE_KEY="kuali.cr.cart.message.course.code.required";
    private static final String SECTION_REQUIRED_MESSAGE_KEY="kuali.cr.cart.message.section.required";

    private static SearchService searchService;
    private static LprService lprService;
    private static IdentityService identityService;
    private static AtpService atpService;
    private static CourseService courseService;
    private static CourseOfferingService courseOfferingService;
    private static TypeService typeService;
    private static CourseOfferingSetService courseOfferingSetService;
    private static LRCService lrcService;
    private static ScheduleOfClassesService scheduleOfClassesService;
    private static CourseRegistrationService courseRegistrationService;
    private static RuleManagementService ruleManagementService;

    private static Map<String, Integer> activityPriorityMap = null;

    public static String getTermId(String termId, String termCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!StringUtils.isEmpty(termId)) {
            return termId;
        }

        if (StringUtils.isEmpty(termCode)) {
            return null;
        }

        return KSCollectionUtils.getRequiredZeroElement(getTermsByTermCode(termCode)).getTermId();
    }

    public static List<TermSearchResult> getTermsByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<AtpInfo> atpInfos = getAtpService().getAtpsByCode(termCode, ContextUtils.createDefaultContextInfo());
        return getTermSearchResultsFromAtpInfos(atpInfos);
    }

    public static List<TermSearchResult> getTermSearchResultsFromAtpInfos(List<AtpInfo> atpInfos) {
        List<TermSearchResult> result = new ArrayList<>();

        for (AtpInfo atpInfo : atpInfos) {
            TermSearchResult ts = new TermSearchResult();
            ts.setTermId(atpInfo.getId());
            ts.setTermName(atpInfo.getName());
            ts.setTermCode(atpInfo.getCode());
            ts.setStartDate(atpInfo.getStartDate());
            ts.setEndDate(atpInfo.getEndDate());
            result.add(ts);
        }

        return result;
    }

    /**
     * This method takes in a list of activity offering type keys and sorts them in priority order. The priority
     * order comes from the priority established in the Activity Types.
     *
     * @param typeKeys    list of activity offering type keys
     * @param contextInfo context of the call
     */
    public static void sortActivityOfferingTypeKeyList(List<String> typeKeys, final ContextInfo contextInfo) {
        Collections.sort(typeKeys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    int val1 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o1);
                    int val2 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o2);
                    return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));
                } catch (Exception ex) {
                    // I'm not sure if this is the correct thing to do here.
                    throw new RuntimeException("Failed to sort activity offering types", ex);
                }
            }
        });
    }

    /**
     * This method takes in a list of activity offering results and sorts them in priority order. The priority
     * order comes from the priority established in the Activity Types.
     *
     * @param activityOfferingResults list of StudentScheduleActivityOfferingResult
     * @param contextInfo             context of the call
     */
    public static void sortActivityOfferingReslutList(List<StudentScheduleActivityOfferingResult> activityOfferingResults, final ContextInfo contextInfo) {
        Collections.sort(activityOfferingResults, new Comparator<StudentScheduleActivityOfferingResult>() {
            @Override
            public int compare(StudentScheduleActivityOfferingResult o1, StudentScheduleActivityOfferingResult o2) {
                try {
                    int val1 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o1.getActivityOfferingType());
                    int val2 = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(o2.getActivityOfferingType());
                    return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));
                } catch (Exception ex) {
                    // I'm not sure if this is the correct thing to do here.
                    throw new RuntimeException("Failed to sort activity offering types", ex);
                }
            }
        });
    }

    /* This method transforms start/end time from MS to user-friendly presentation, and set days of the week for
       schedule component for Activity Offering
     */
    public static ActivityOfferingScheduleComponentResult getActivityOfferingScheduleComponent(String isTBA, String roomCode, String buildingCode,
                                                                                               String weekdays, String startTimeMs, String endTimeMs) throws InvalidParameterException {
        ActivityOfferingScheduleComponentResult scheduleComponent = new ActivityOfferingScheduleComponentResult();
        scheduleComponent.setRoomCode(roomCode);
        scheduleComponent.setBuildingCode(buildingCode);

        if (StringUtils.equals(isTBA, "1")) {
            scheduleComponent.setIsTBA(true);
        } else {
            scheduleComponent.setIsTBA(false);
        }

        scheduleComponent.setDays(StringUtils.isEmpty(weekdays) ? "" : dayDisplayHelper(weekdays));
        String startTime = StringUtils.isEmpty(startTimeMs) ? "" : TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs)));
        String endTime = StringUtils.isEmpty(endTimeMs) ? "" : TimeOfDayHelper.formatTimeOfDay(TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs)));
        scheduleComponent.setStartTime(startTime);
        scheduleComponent.setEndTime(endTime);
        if (!StringUtils.isEmpty(startTimeMs) && !StringUtils.isEmpty(endTimeMs)) {
            scheduleComponent.setDisplayTime(TimeOfDayHelper.makeFormattedTimeForAOScheduleComponent(startTimeMs, endTimeMs));
        }

        if (!StringUtils.isEmpty(weekdays)) {
            scheduleComponent.setMon(weekdays.contains(SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE));
            scheduleComponent.setTue(weekdays.contains(SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE));
            scheduleComponent.setWed(weekdays.contains(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE));
            scheduleComponent.setThu(weekdays.contains(SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE));
            scheduleComponent.setFri(weekdays.contains(SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE));
            scheduleComponent.setSat(weekdays.contains(SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE));
            scheduleComponent.setSun(weekdays.contains(SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE));
        }

        return scheduleComponent;
    }

    /**
     * This method grabs all kuali.lu.type.grouping.activity types. These types have attributes with priority values.
     * Since we are interested in the priority of the lui types, not the lu types we then need to get the type type
     * relation between lu and lui.
     * <p/>
     * Ex:
     * Lu.Lucture priority = 1; Lu.Lecture has a 1:1 relation with Lui.Lecture so we say
     * the Lui.Lecture.priority = Lu.Lecture.priority
     * <p/>
     * These values never change so I'm storing them in a map inside the service for performance reasons.
     * <p/>
     * So, if a course offering has Lec, Lab, Discussion and you want to know which is the Primary type, just get the
     * lowest priority of the ao type.
     *
     * @param contextInfo context of the call
     * @return mapping of activity type key to the priority
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public static Map<String, Integer> getActivityPriorityMap(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        if (activityPriorityMap == null) {
            initActivityPriorityMap(contextInfo);
        }

        return Collections.unmodifiableMap(activityPriorityMap);
    }

    /**
     * This is an internal method that will return a map of aoId, InstructorSearchResult. We are using a map object
     * so it is easier to build up complex objects in a more performant way
     *
     * @param aoIds       list of activity offering ids
     * @param contextInfo context of the call
     * @return mapping of activity id to list of instructors related to that id
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    public static Map<String, List<InstructorSearchResult>> searchForInstructorsByAoIds(List<String> aoIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        Map<String, List<InstructorSearchResult>> resultList = new HashMap<>();
        Map<String, InstructorSearchResult> principalId2aoIdMap = new HashMap<>();

        // We want to pull only active instructors for the passed in AO's
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(PredicateFactory.in("luiId", aoIds.toArray()));
        predicates.add(PredicateFactory.in("personRelationTypeId", LprServiceConstants.COURSE_INSTRUCTOR_TYPE_KEYS));  // allow all instructor types
        predicates.add(PredicateFactory.equal("personRelationStateId", LprServiceConstants.ACTIVE_STATE_KEY));

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria criteria = qbcBuilder.build();

        List<LprInfo> lprInfos = CourseRegistrationAndScheduleOfClassesUtil.getLprService().searchForLprs(criteria, contextInfo);
        if (lprInfos != null) {

            for (LprInfo lprInfo : lprInfos) {
                InstructorSearchResult result = new InstructorSearchResult();

                String aoId = lprInfo.getLuiId();
                //  Only include the main instructor.
                if (!StringUtils.equals(lprInfo.getTypeKey(), LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                    result.setPrimary(false);
                } else {
                    result.setPrimary(true);
                }
                result.setPrincipalId(lprInfo.getPersonId());
                principalId2aoIdMap.put(lprInfo.getPersonId(), result);
                result.setActivityOfferingId(aoId);

                if (resultList.containsKey(aoId)) {
                    resultList.get(aoId).add(result);
                } else {
                    List<InstructorSearchResult> newList = new ArrayList<>();
                    newList.add(result);
                    resultList.put(aoId, newList);
                }
            }

            // if we have a list of instructors
            if (!resultList.isEmpty()) {
                // get the instructor entities from KIM.
                EntityDefaultQueryResults results = getInstructorsInfoFromKim(new ArrayList<>(principalId2aoIdMap.keySet()));

                for (EntityDefault entity : results.getResults()) {
                    // Each KIM entity can have multiple principals. So we need to loop through the principals
                    for (Principal principal : entity.getPrincipals()) {
                        if (principalId2aoIdMap.containsKey(principal.getPrincipalId())) {  // does this principal match the ks instructor
                            InstructorSearchResult instructor = principalId2aoIdMap.get(principal.getPrincipalId());
                            populateInstructorWithEntityInfo(instructor, entity);  // populate the instructor with KIM information
                        }
                    }
                }

            }
        }
        return resultList;
    }

    /**
     * Based on the input, get the RegGroupSearchResult. There are two ways to find it (termCode, courseCode, regGroupName)
     * or just the regGroupId.
     *
     * @param termCode the term code
     * @param courseCode the course code
     * @param regGroupCode the reg group code
     * @param regGroupId the reg group id
     * @param contextInfo context info
     * @return RegGroupSearchResult
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    public static RegGroupSearchResult getRegGroup(String termId, String termCode, String courseCode, String regGroupCode, String regGroupId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        RegGroupSearchResult rg = null;

        if (!StringUtils.isEmpty(regGroupId)) {
            rg = getScheduleOfClassesService().getRegGroup(regGroupId);
        } else {
            if(courseCode == null || courseCode.isEmpty()){
                if(regGroupCode == null || regGroupCode.isEmpty()) {
                    throw new CourseDoesNotExistException(COURSE_CODE_AND_SECTION_REQUIRED_MESSAGE_KEY, "Course Code cannot be empty");
                } else {
                    throw new CourseDoesNotExistException(COURSE_CODE_REQUIRED_MESSAGE_KEY, "Course Code cannot be empty");
                }
            }
            if(regGroupCode == null || regGroupCode.isEmpty()){
                throw new CourseDoesNotExistException(SECTION_REQUIRED_MESSAGE_KEY, courseCode, "Section cannot be empty");
            }
            // get the registration group
            rg = getScheduleOfClassesService().searchForRegistrationGroupByTermAndCourseAndRegGroup(termId, termCode, courseCode, regGroupCode);
        }

        if (rg == null) {
            if (courseCode != null && !StringUtils.isEmpty(courseCode)) {
                String technicalInfo = String.format("getRegGroup. Cannot find Course. Technical Info:(termId:[%s] termCode:[%s] courseCode:[%s] regGroupCode:[%s] regGroupId:[%s])",
                        termId, termCode, courseCode, regGroupCode, regGroupId);
                LOGGER.warn(technicalInfo);
                throw new CourseDoesNotExistException(COURSE_CODE_NOT_FOUND_MESSAGE_KEY, courseCode, "Cannot find the course " + courseCode + " in the selected term");
            } else {
                throw new CourseDoesNotExistException(COURSE_CODE_REQUIRED_MESSAGE_KEY, "Course Code cannot be empty");
            }
        }

        return rg;
    }

    private synchronized static void initActivityPriorityMap(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        if (activityPriorityMap == null) {    // this may seem silly, but this prevents a race condition on threads calling this method.
            activityPriorityMap = new HashMap<>();
            List<TypeInfo> activityTypes = getTypeService().getTypesForGroupType("kuali.lu.type.grouping.activity", contextInfo);

            for (TypeInfo typeInfo : activityTypes) {
                List<AttributeInfo> attributes = typeInfo.getAttributes();
                if (attributes != null && !attributes.isEmpty()) {
                    for (AttributeInfo attribute : attributes) {
                        if (TypeServiceConstants.ACTIVITY_SELECTION_PRIORITY_ATTR.equals(attribute.getKey())) {
                            TypeTypeRelation typeTypeRelation = KSCollectionUtils.getRequiredZeroElement(getTypeService().getTypeTypeRelationsByOwnerAndType(typeInfo.getKey(), TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, contextInfo));
                            activityPriorityMap.put(typeTypeRelation.getRelatedTypeKey(), Integer.valueOf(attribute.getValue()));
                        }
                    }
                }
            }
        }
    }

    public static CourseOfferingInfo getCourseOfferingIdCreditGrading(String courseOfferingId, String courseCode, String termId, String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if (!StringUtils.isEmpty(courseOfferingId)) {
            return searchForCreditsGradingByCourseOfferingId(courseOfferingId);
        }

        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        List<CourseOfferingInfo> courseOfferingInfos = searchForCourseOfferingIdCreditsGradingByCourseCodeAndTerm(courseCode, termId);

        return KSCollectionUtils.getRequiredZeroElement(courseOfferingInfos);
    }

    /**
     * This method creates a registration request for the add operation of a single registration group.
     *
     * @param principalId     principal id
     * @param regGroupId      Registration Group id
     * @param masterLprId     masterLprId
     * @param credits         credits
     * @param gradingOptionId gradingOptionId
     * @param okToWaitlist    flag to set if student should automatically be waitlisted when adding to a full reg group
     *                        with available waitlist
     * @return registration request
     */
    public static RegistrationRequestItemInfo createNewRegistrationRequestItem(String principalId, String regGroupId, String masterLprId, String credits, String gradingOptionId, String typeKey, String stateKey, boolean okToWaitlist) {

        RegistrationRequestItemInfo registrationRequestItem = new RegistrationRequestItemInfo();
        registrationRequestItem.setTypeKey(typeKey);
        registrationRequestItem.setStateKey(stateKey);
        registrationRequestItem.setRegistrationGroupId(regGroupId);
        registrationRequestItem.setExistingCourseRegistrationId(masterLprId);
        registrationRequestItem.setPersonId(principalId);
        registrationRequestItem.setCredits(new KualiDecimal(credits));
        registrationRequestItem.setGradingOptionId(gradingOptionId);
        registrationRequestItem.setOkToWaitlist(okToWaitlist);
        return registrationRequestItem;
    }

    public static String translateGradingOptionKeyToName(String gradingOptionKey) {
        if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
            return LrcServiceConstants.RESULT_GROUP_VALUE_GRADE_AUDIT;
        } else if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER)) {
            return LrcServiceConstants.RESULT_GROUP_VALUE_GRADE_LETTER;
        } else if (StringUtils.equals(gradingOptionKey, LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
            return LrcServiceConstants.RESULT_GROUP_VALUE_GRADE_PASSFAIL;
        }
        return null;
    }

    private static CourseOfferingInfo searchForCreditsGradingByCourseOfferingId(String courseOfferingId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CREDIT_REGGRADING_BY_COID_SEARCH_KEY);

        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CO_ID, courseOfferingId);

        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());

        CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
        courseOfferingInfo.setId(courseOfferingId);
        courseOfferingInfo.setStudentRegistrationGradingOptions(new ArrayList<String>());

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for (SearchResultCellInfo cellInfo : row.getCells()) {
                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }
                if (CourseOfferingManagementSearchImpl.SearchResultColumns.RES_VAL_GROUP_KEY.equals(cellInfo.getKey())) {
                    if (value != null && value.startsWith("kuali.creditType.credit")) {
                        courseOfferingInfo.setCreditOptionId(value);
                    } else if (value != null && ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, value)) {
                        courseOfferingInfo.getStudentRegistrationGradingOptions().add(value);
                    }
                }
            }
        }

        return courseOfferingInfo;
    }

    private static List<CourseOfferingInfo> searchForCourseOfferingIdCreditsGradingByCourseCodeAndTerm(String courseCode, String atpId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<CourseOfferingInfo> resultList = new ArrayList<>();
        HashMap<String, CourseOfferingInfo> hm = new HashMap<>();

        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CREDIT_REGGRADING_COID_BY_TERM_AND_COURSE_CODE_SEARCH_KEY);

        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, atpId);

        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());

        for (SearchResultRowInfo row : searchResult.getRows()) {
            String courseOfferingId = "", resValGroupKey = "";
            for (SearchResultCellInfo cellInfo : row.getCells()) {
                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }
                if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    courseOfferingId = value;
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.RES_VAL_GROUP_KEY.equals(cellInfo.getKey())) {
                    resValGroupKey = value;
                }
            }
            CourseOfferingInfo courseOfferingInfo = new CourseOfferingInfo();
            if (!hm.containsKey(courseOfferingId)) {
                courseOfferingInfo.setId(courseOfferingId);
                courseOfferingInfo.setStudentRegistrationGradingOptions(new ArrayList<String>());
            } else {
                courseOfferingInfo = hm.get(courseOfferingId);
            }
            if (resValGroupKey != null && resValGroupKey.startsWith("kuali.creditType.credit")) {
                courseOfferingInfo.setCreditOptionId(resValGroupKey);
            } else if (resValGroupKey != null && ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resValGroupKey)) {
                courseOfferingInfo.getStudentRegistrationGradingOptions().add(resValGroupKey);
            }
            hm.put(courseOfferingId, courseOfferingInfo);
        }

        if (!hm.isEmpty()) {
            for (Map.Entry<String, CourseOfferingInfo> pair : hm.entrySet()) {
                CourseOfferingInfo courseOfferingInfo = pair.getValue();
                resultList.add(courseOfferingInfo);
            }
        }

        return resultList;
    }

    /**
     * A faster way to get default names
     *
     * @param principalIds a List of principal id strings
     * @return EntityDefaultQueryResults with only default names populated
     */
    private static EntityDefaultQueryResults getInstructorsInfoFromKimFast(List<String> principalIds) {

        //Find all the principals
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("principalId", principalIds.toArray())
        );

        QueryByCriteria criteria = qbcBuilder.build();

        PrincipalQueryResults principalResults = CourseRegistrationAndScheduleOfClassesUtil.getIdentityService().findPrincipals(criteria);

        //Get a mapping of entity Ids to principals
        Map<String, Principal> entityId2Principal = new HashMap<>();
        for (Principal principal : principalResults.getResults()) {
            entityId2Principal.put(principal.getEntityId(), principal);
        }

        //Create a query to get default names
        qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in(KIMPropertyConstants.Entity.ENTITY_ID, entityId2Principal.keySet().toArray()),
                PredicateFactory.equal("defaultValue", Boolean.TRUE),
                PredicateFactory.equal(KIMPropertyConstants.Entity.ACTIVE, Boolean.TRUE)
        );

        criteria = qbcBuilder.build();

        //Get a handle to the dataObjectService. If this doesnt work then an exception will be thrown and
        // the normal search for default entities will take over
        DataObjectService dataObjectService = GlobalResourceLoader.getService("dataObjectService");

        //Do the search
        QueryResults<EntityNameBo> results = dataObjectService.findMatching(EntityNameBo.class, criteria);

        //Build up entityDefault structure
        EntityDefaultQueryResults.Builder entityDefaultBuilder = EntityDefaultQueryResults.Builder.create();
        entityDefaultBuilder.setMoreResultsAvailable(results.isMoreResultsAvailable());
        entityDefaultBuilder.setTotalRowCount(results.getTotalRowCount());

        final List<EntityDefault.Builder> entityDefaultBuilders = new ArrayList<>();
        for (EntityNameBo entityName : results.getResults()) {
            EntityBo bo = new EntityBo();
            bo.getNames().add(entityName);
            bo.getPrincipals().add(PrincipalBo.from(entityId2Principal.get(entityName.getEntityId())));
            bo.setId(entityName.getEntityId());
            entityDefaultBuilders.add(EntityDefault.Builder.create(bo));
        }

        entityDefaultBuilder.setResults(entityDefaultBuilders);

        return entityDefaultBuilder.build();
    }

    private static EntityDefaultQueryResults getInstructorsInfoFromKim(List<String> principalIds) {

        //Try the fast way first
        try {
            return getInstructorsInfoFromKimFast(principalIds);
        } catch (Exception ex) {
            LOGGER.warn("Error getting ids", ex);
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("principals.principalId", principalIds.toArray())
        );

        QueryByCriteria criteria = qbcBuilder.build();

        return CourseRegistrationAndScheduleOfClassesUtil.getIdentityService().findEntityDefaults(criteria);
    }

    /**
     * This method populates the InstructorSearchResult object with infor from the Kim entity
     *
     * @param instructor Kuali Student Instructor
     * @param entity     Kim Entity object
     */
    private static void populateInstructorWithEntityInfo(InstructorSearchResult instructor, EntityDefault entity) {
        if (entity.getName() != null) {
            instructor.setDisplayName(StringUtils.trim(entity.getName().getCompositeName()));
            instructor.setFirstName(StringUtils.trim(entity.getName().getFirstName()));
            instructor.setLastName(StringUtils.trim(entity.getName().getLastName()));
        }
    }

    /**
     * This method converts Timeslot Day Codes to Timeslot Day Display Codes
     *
     * @param day - Timeslot Day Code
     * @return dayDisplay - Timeslot Day Display Code
     */
    public static String dayDisplayHelper(String day) {
        StringBuilder dayDisplay = new StringBuilder();
        for (char c : day.toCharArray()) {
            switch (c) {
                case 'M':
                    dayDisplay.append(SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                case 'T':
                    dayDisplay.append(SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                case 'W':
                    dayDisplay.append(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                case 'H':
                    dayDisplay.append(SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                case 'F':
                    dayDisplay.append(SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                case 'S':
                    dayDisplay.append(SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                case 'U':
                    dayDisplay.append(SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE);
                    break;
                default:
                    break;
            }
        }
        return dayDisplay.toString();
    }


    /**
     * SERVICES *
     */
    public static SearchService getSearchService() {
        if (searchService == null) {
            searchService = GlobalResourceLoader.getService(new QName(SearchServiceConstants.NAMESPACE, SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public static LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public static void setLprService(LprService lprService) {
        CourseRegistrationAndScheduleOfClassesUtil.lprService = lprService;
    }

    public static IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    @SuppressWarnings("unused")
    public static void setIdentityService(IdentityService identityService) {
        CourseRegistrationAndScheduleOfClassesUtil.identityService = identityService;
    }

    public static AtpService getAtpService() {
        if (atpService == null) {
            atpService = GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public static void setAtpService(AtpService atpService) {
        CourseRegistrationAndScheduleOfClassesUtil.atpService = atpService;
    }

    public static CourseService getCourseService() {
        if (courseService == null) {
            courseService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }

    public static void setCourseService(CourseService courseService) {
        CourseRegistrationAndScheduleOfClassesUtil.courseService = courseService;
    }

    public static CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public static void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        CourseRegistrationAndScheduleOfClassesUtil.courseOfferingService = courseOfferingService;
    }

    public static TypeService getTypeService() {
        if (typeService == null) {
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public static void setTypeService(TypeService typeService) {
        CourseRegistrationAndScheduleOfClassesUtil.typeService = typeService;
    }

    public static CourseOfferingSetService getCourseOfferingSetService() {
        if (courseOfferingSetService == null) {
            courseOfferingSetService = GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public static LRCService getLrcService() {
        if (lrcService == null) {
            lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        CourseRegistrationAndScheduleOfClassesUtil.lrcService = lrcService;
    }

    public static ScheduleOfClassesService getScheduleOfClassesService() {
        if (scheduleOfClassesService == null) {
            scheduleOfClassesService = GlobalResourceLoader.getService(new QName(ScheduleOfClassesServiceConstants.NAMESPACE, ScheduleOfClassesServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return scheduleOfClassesService;
    }

    @SuppressWarnings("unused")
    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        CourseRegistrationAndScheduleOfClassesUtil.scheduleOfClassesService = scheduleOfClassesService;
    }

    public static CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        CourseRegistrationAndScheduleOfClassesUtil.courseRegistrationService = courseRegistrationService;
    }

    public static RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        CourseRegistrationAndScheduleOfClassesUtil.ruleManagementService = ruleManagementService;
    }

}
