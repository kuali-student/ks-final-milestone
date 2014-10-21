/*
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
 */
package org.kuali.student.ap.coursesearch.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.GroupBase;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.widget.Disclosure;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.dataobject.ActivityFormatDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseTermDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.FormatOfferingInfoWrapper;
import org.kuali.student.ap.coursesearch.dataobject.PlannedRegistrationGroupDetailsWrapper;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsDialogForm;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsForm;
import org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.planner.service.impl.PlanEventViewHelperServiceImpl;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class CourseDetailsViewHelperServiceImpl extends PlanEventViewHelperServiceImpl implements CourseDetailsViewHelperService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseDetailsViewHelperServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCourseSectionDetails(UifFormBase form, String courseId)  {
        load((CourseSectionDetailsForm) form, courseId);
    }

    /**
     * Load information on the course and its offerings into the form
     *
     * @param form - Page form to load information onto
     * @param courseId - Id of the course being loaded
     */
    private void load(CourseSectionDetailsForm form, String courseId)  {
        Course courseInfo = KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourse(courseId);
        form.setCourseTitle(courseInfo.getCourseTitle());
        form.setCourseCode(courseInfo.getCode());
        List<String> termIds = KsapFrameworkServiceLocator.getCourseHelper().getScheduledTermsForCourse(courseInfo);
        form.setCourseTermDetailsWrappers(getScheduledTerms(termIds, courseInfo.getVersion().getVersionIndId()));
    }

    /**
     * Load data for terms with scheduled course offerings for the course
     *
     * @param scheduledTermsList - List of term ids
     * @param versionIndId - Version independent Id of the course data is being loaded for
     * @return A list of filled in terms to display
     */
    private List<CourseTermDetailsWrapper> getScheduledTerms(List<String> scheduledTermsList, String versionIndId)  {

        List<CourseTermDetailsWrapper> courseTermDetailsList = new ArrayList<CourseTermDetailsWrapper>();

        //Return only the scheduled terms
        if (scheduledTermsList != null && scheduledTermsList.size() > 0) {

            List<TermInfo> scheduledTerms;
            try {
                scheduledTerms = KsapFrameworkServiceLocator.getAcademicCalendarService().getTermsByIds(scheduledTermsList, KsapFrameworkServiceLocator.getContext().getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("ATP lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("ATP lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("ATP lookup error", e);
            }

            //sort scheduledTermsListIds
            List<Term> terms = new ArrayList<Term>(scheduledTerms);
            List<Term> scheduledTermsListSorted = sortTerms(terms);

            // Create and load information of the course offerings for the term
            List<String> courseIds = new ArrayList<String>();

            // Get all versions of the course
            courseIds.addAll(KsapFrameworkServiceLocator.getCourseHelper().getAllCourseIdsByVersionIndependentId(versionIndId));
            Map<String, List<CourseOfferingDetailsWrapper>> courseOfferingsByTerm = processCourseOfferingsByTerm(courseIds, terms);

            // Create the term details wrapper and load data for it.
            for (Term scheduledTermId : scheduledTermsListSorted) {

                CourseTermDetailsWrapper courseTerm = new CourseTermDetailsWrapper();
                courseTerm.setTermName(scheduledTermId.getName());
                courseTerm.setTermId(scheduledTermId.getId());
                courseTerm.setCourseOfferingDetailsWrappers(courseOfferingsByTerm.get(scheduledTermId.getId()));

                courseTermDetailsList.add(courseTerm);
            }
        }

        return courseTermDetailsList;
    }

    /**
     * {@inheritDoc}
     * This implementation is sorting by the date that the Soc was released/published
     */
    @Override
    public List<Term> sortTerms(List<Term> terms) {
        return KsapFrameworkServiceLocator.getTermHelper().sortTermsBySocReleaseDate(terms, false);
    }

    /**
     * Comparator implementation so that I can sort CourseOfferingInfo objects by the course offering code
     */
    public class CourseOfferingInfoComparator implements Comparator<CourseOffering> {

        @Override
        public int compare(CourseOffering o1, CourseOffering o2) {
            return o1.getCourseOfferingCode().compareTo(o2.getCourseOfferingCode());
        }
    }

    /**
     * Comparator implementation so that I can sort ActivityOfferingInfo objects by the activity code
     */
    public class ActivityOfferingInfoComparator implements Comparator<ActivityOfferingInfo> {

        @Override
        public int compare(ActivityOfferingInfo o1, ActivityOfferingInfo o2) {
            return o1.getActivityCode().compareTo(o2.getActivityCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<CourseOfferingDetailsWrapper>> processCourseOfferingsByTerm(List<String> courseIds, List<Term> terms) {
        List<CourseOffering> courseOfferings = KsapFrameworkServiceLocator.getCourseHelper().getCourseOfferingsForCoursesAndTerms(courseIds, terms);
        Collections.sort(courseOfferings, new CourseOfferingInfoComparator());
        Map<String, List<CourseOfferingDetailsWrapper>> map = new HashMap<String, List<CourseOfferingDetailsWrapper>>();
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

        for (CourseOffering offering : courseOfferings) {
            String termId = offering.getTermId();
            List<CourseOfferingDetailsWrapper> offeringsByTerm = map.get(termId);
            if (offeringsByTerm == null)
                offeringsByTerm = new ArrayList<CourseOfferingDetailsWrapper>();

            List<String> validRegGroups = getValidRegGroupIds(offering.getId(), new HashMap<Object, Object>());
            List<String> validRegGroupsToRemain = getValidRegGroupIdsToRemain(offering.getId(), new HashMap<Object, Object>());

            List<String> validFormatOfferings = new ArrayList<String>();
            List<String> validActivities = new ArrayList<String>();
            List<String> validActivitiesToRemain = new ArrayList<String>();

            // Get valid activities that are of reg groups able to be added
            for(String id : validRegGroups){
                List<String> activityIds = new ArrayList<String>();
                List<String> formatIds = new ArrayList<String>();
                try {
                    SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants
                            .KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
                    request.addParam(CourseSearchConstants.SearchParameters.REG_GROUP_ID, id);
                    List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                            KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                    for( SearchResultRowInfo row : rows){
                        activityIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                                .ACTIVITY_OFFERING_ID));
                    }

                    request = new SearchRequestInfo(CourseSearchConstants
                            .KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
                    request.addParam(CourseSearchConstants.SearchParameters.REG_GROUP_ID, id);
                    rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                            KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                    for( SearchResultRowInfo row : rows){
                        formatIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                                .FORMAT_OFFERING_ID));
                    }
                } catch (InvalidParameterException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (MissingParameterException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (OperationFailedException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (PermissionDeniedException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                }
                if(activityIds != null && !activityIds.isEmpty()){
                    validActivities.addAll(activityIds);
                }

                if(formatIds != null && !formatIds.isEmpty()){
                    validFormatOfferings.addAll(formatIds);
                }
            }

            // Get valid activities to keep showing on the page
            for(String id : validRegGroupsToRemain){
                List<String> activityIds = new ArrayList<String>();
                try {
                    SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants
                            .KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
                    request.addParam(CourseSearchConstants.SearchParameters.REG_GROUP_ID, id);
                    List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                            KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                    for( SearchResultRowInfo row : rows){
                        activityIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                                .ACTIVITY_OFFERING_ID));
                    }
                } catch (InvalidParameterException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (MissingParameterException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (OperationFailedException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (PermissionDeniedException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                }
                if(activityIds != null && !activityIds.isEmpty()){
                    validActivitiesToRemain.addAll(activityIds);
                }
            }

            CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = new CourseOfferingDetailsWrapper(offering);
            List<FormatOfferingInfo> formatOfferings = null;
            try {
                formatOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOfferingsByCourseOffering(offering.getId(), contextInfo);
                List<FormatOfferingInfoWrapper> formatOfferingWrappers = new ArrayList<FormatOfferingInfoWrapper>(formatOfferings.size());
                //Figure out if the CO is a variable credit course
                boolean isCourseOfferingVariableCredit = isVariableCreditCourse(offering);

                courseOfferingDetailsWrapper.setVariableCredit(isCourseOfferingVariableCredit);

                Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>>
                        aosByFormat = getAOData(offering.getId(),validActivities, validActivitiesToRemain, isCourseOfferingVariableCredit);

                List<PlannedRegistrationGroupDetailsWrapper> plannedActivityOfferings = new ArrayList<PlannedRegistrationGroupDetailsWrapper>();

                for (FormatOfferingInfo formatOffering : formatOfferings) {

                    //Ignore non-offered FO's
                    if (!LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY.equalsIgnoreCase(formatOffering.getStateKey()))
                        continue;

                    FormatOfferingInfoWrapper formatOfferingInfo = new FormatOfferingInfoWrapper(formatOffering, courseOfferingDetailsWrapper.getCourseOfferingCode());
                    formatOfferingInfo.setValidFormat(validFormatOfferings.contains(formatOfferingInfo.getFormatOfferingId()));

                    formatOfferingInfo.setVariableCredit(isCourseOfferingVariableCredit);

                    List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers = new ArrayList<ActivityFormatDetailsWrapper>();
                    Map<String, List<ActivityOfferingDetailsWrapper>> aosByTypeMap = aosByFormat.get(formatOfferingInfo.getFormatOfferingId());

                    if (aosByTypeMap != null) {
                        List<ActivityOfferingDetailsWrapper> tempActivityOfferingDetailWrappers = new ArrayList<ActivityOfferingDetailsWrapper>();
                        for (Map.Entry<String, List<ActivityOfferingDetailsWrapper>> aosByType : aosByTypeMap.entrySet()) {
                            //TypeService is cached, so this should be safe to have inside the loop here
                            TypeInfo typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(aosByType.getKey(), contextInfo);
                            ActivityFormatDetailsWrapper activityFormatDetailsWrapper = new ActivityFormatDetailsWrapper(
                                    termId, offering.getCourseOfferingCode(), formatOfferingInfo.getFormatOfferingId(), typeInfo.getName(), typeInfo.getKey());

                            activityFormatDetailsWrapper.setActivityOfferingDetailsWrappers(aosByType.getValue());
                            activityFormatDetailsWrappers.add(activityFormatDetailsWrapper);

                            tempActivityOfferingDetailWrappers.addAll(aosByType.getValue());

                        }
                        plannedActivityOfferings.addAll(getPlannedPlannedRegistrationGroups(termId, tempActivityOfferingDetailWrappers));
                    }

                    formatOfferingInfo.setActivityFormatDetailsWrappers(activityFormatDetailsWrappers);
                    formatOfferingWrappers.add(formatOfferingInfo);

                }
                Collections.sort(formatOfferingWrappers);
                courseOfferingDetailsWrapper.setFormatOfferingInfoWrappers(formatOfferingWrappers);
                courseOfferingDetailsWrapper.setPlannedActivityDetailsWrappers(plannedActivityOfferings);
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("FO lookup error", e);
            }

            offeringsByTerm.add(courseOfferingDetailsWrapper);
            map.put(termId, offeringsByTerm);
        }

        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityOfferingDetailsWrapper convertAOInfoToWrapper(ActivityOfferingInfo aoInfo, boolean isCourseOfferingVariableCredit, Map<String, FormatOfferingInfo> formatOfferingInfoMap)  {
        ActivityOfferingDetailsWrapper wrapper = new ActivityOfferingDetailsWrapper(aoInfo, false, true);
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

        int firstValue = 0;

        FormatOfferingInfo fo = null;
        try {
            fo = formatOfferingInfoMap.get(aoInfo.getFormatOfferingId());
            if (fo == null) {
                fo = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), contextInfo);
                formatOfferingInfoMap.put(aoInfo.getFormatOfferingId(), fo);
            }
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        }

        TypeInfo typeInfo = null;
        try {
            typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(aoInfo.getTypeKey(), contextInfo);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Type Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Type Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Type Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Type Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Type Service lookup error", e);
        }

        wrapper.setActivityFormatName(typeInfo.getName());
        if (fo.getActivityOfferingTypeKeys().size()>1) {
            wrapper.setSingleFormatOffering(false);
        }else{
            List<RegistrationGroupInfo> regGroups = null;
            try {
                regGroups = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroupsByActivityOffering(aoInfo.getId(),contextInfo);
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("CO Service lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("CO Service lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("CO Service lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("CO Service lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("CO Service lookup error", e);
            }
            RegistrationGroupInfo regGroup;
            try{
                regGroup = KSCollectionUtils.getRequiredZeroElement(regGroups);
                wrapper.setRegGroupCode(regGroup.getName());
                wrapper.setRegGroupId(regGroup.getId());
            }catch(OperationFailedException e){
                throw new IllegalArgumentException("Multiple Registration Groups Found for Single Format Activity Offering",e);
            }
        }

        //From Bonnie: we need to better understand firstInstructor vs.multiple instructors cases -- pull in the logic from manage CO
        OfferingInstructorInfo displayInstructor = findDisplayInstructor(aoInfo.getInstructors());

        if (displayInstructor != null) {
            wrapper.setFirstInstructorDisplayName(displayInstructor.getPersonName());
            wrapper.setInstructorName(displayInstructor.getPersonName());
        }

        //for multiple instructor display
        List<OfferingInstructorInfo> instructorInfos = aoInfo.getInstructors();
        if (instructorInfos != null) {
            for (OfferingInstructorInfo offeringInstructorInfo : instructorInfos) {
                wrapper.setInstructorDisplayNames(offeringInstructorInfo.getPersonName(), true);
            }
        }

        //This section is to display either schedule actuals assume that when an AO is offered, actuals are always available
        if (aoInfo.getScheduleIds() != null && aoInfo.getScheduleIds().size() > 0) {
            //FIXME: Use display object once we get the TBA with ScheduleComponentDisplay
            List<ScheduleInfo> scheduleInfoList = null;
            try {
                scheduleInfoList = KsapFrameworkServiceLocator.getSchedulingService().getSchedulesByIds(aoInfo.getScheduleIds(), contextInfo);
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Scheduling Service lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Scheduling Service lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Scheduling Service lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Scheduling Service lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("Scheduling Service lookup error", e);
            }

            if (!scheduleInfoList.isEmpty()) {
                for (ScheduleInfo scheduleInfo : scheduleInfoList) {
                    if (!scheduleInfo.getScheduleComponents().isEmpty()) {

                        for (ScheduleComponentInfo scheduleComponentInfo : scheduleInfo.getScheduleComponents()) {

                            String roomId = scheduleComponentInfo.getRoomId();
                            // JIRA Fix : KSENROLL-8726. Added isEmpty check
                            TimeSlotInfo timeSlotInfo = null;
                            try {
                                timeSlotInfo = KsapFrameworkServiceLocator.getSchedulingService().getTimeSlot(scheduleComponentInfo.getTimeSlotIds().isEmpty() ? StringUtils.EMPTY : scheduleComponentInfo.getTimeSlotIds().get(firstValue), contextInfo);
                            } catch (DoesNotExistException e) {
                                throw new IllegalArgumentException("Scheduling Service lookup error", e);
                            } catch (InvalidParameterException e) {
                                throw new IllegalArgumentException("Scheduling Service lookup error", e);
                            } catch (MissingParameterException e) {
                                throw new IllegalArgumentException("Scheduling Service lookup error", e);
                            } catch (OperationFailedException e) {
                                throw new IllegalArgumentException("Scheduling Service lookup error", e);
                            } catch (PermissionDeniedException e) {
                                throw new IllegalArgumentException("Scheduling Service lookup error", e);
                            }

                            updateScheduleToAOWrapperForDisplay(wrapper, scheduleComponentInfo.getIsTBA(), roomId, timeSlotInfo);

                        }

                    }
                }
            }

        }
        wrapper.setClassUrl(aoInfo.getActivityOfferingURL());

        List<String> aoRequisites = CourseDetailsUtil.getActivityOfferingRequisites(aoInfo);


        if (aoRequisites.size()>0)
            wrapper.setHasActivityOfferingRequisites(true);

        wrapper.setInPlan(false);

        try {
            SeatCount seatCount = KsapFrameworkServiceLocator.getCourseSeatCountService()
                    .getSeatCountForActivityOffering(aoInfo.getId(), contextInfo);
            if(seatCount!=null && seatCount.getAvailableSeats() != null){
                wrapper.setCurrentEnrollment(seatCount.getAvailableSeats());
            }else{
                LOG.error("Unable to get seat counts as returned value is null for Activity: "+aoInfo.getActivityId());
                wrapper.setCurrentEnrollment(0);
            }

        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Academic Plan Service lookup error", e);
        }

        wrapper.setVariableCredit(isCourseOfferingVariableCredit);
        return wrapper;

    }

    /**
     * This is the finalizeMethodToCall which builds the disclosure widgets.
     * This is really a workaround until KULRICE-9003 gets addressed, allowing me to use #parentLine in the
     * layoutManager.lineGroupPrototype.disclosure properties.
     * @param disclosure The disclosure component that we are working in
     * @param model The form backing object
     */
    public void determineDisclosureRendering(Disclosure disclosure, Object model) {
        CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = (CourseOfferingDetailsWrapper)disclosure.getContext().get(UifConstants.ContextVariableNames.LINE);
        GroupBase parentGroup = (GroupBase)disclosure.getContext().get(UifConstants.ContextVariableNames.PARENT);
        GroupBase grandparent = (GroupBase)parentGroup.getContext().get(UifConstants.ContextVariableNames.PARENT);
        CourseTermDetailsWrapper courseTermDetailsWrapper = (CourseTermDetailsWrapper)grandparent.getContext().get(UifConstants.ContextVariableNames.LINE);

        int size = courseTermDetailsWrapper.getCourseOfferingDetailsWrappers().size();

        if (size <= 1)
            disclosure.setRender(false);

        // Set the id based off of the term id and course offering code
        disclosure.setId(courseTermDetailsWrapper.getTermId() + "_" + courseOfferingDetailsWrapper.getCourseOfferingCode());
    }

    /**
     * Validates the Reg groups by:
     * List of selected AOs
     * Status in the plan
     *
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#getValidRegGroupIds(String, java.util.Map)
     */
    @Override
    public List<String> getValidRegGroupIds(String courseOfferingId, Map<Object,Object> additionalRestrictions){
        // Retrieve reg groups for the Course Offering
        List<String> regGroupIds = new ArrayList<String>();
        try {
            SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.COURSE_OFFERING_ID, courseOfferingId);
            List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                    KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
            for( SearchResultRowInfo row : rows){
                regGroupIds.add(
                        KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.REG_GROUP_ID));
            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        }

        // Validate Reg Groups based on if they are already in plan
        regGroupIds = getValidRegGroupsFilteredByPlan(regGroupIds);

        // Validate Reg Groups based on selected AOs
        List<String> selectedActivities = (List<String>) additionalRestrictions.get("selectedActivities");
        regGroupIds = getValidRegGroupsFilteredBySelectedActivities(regGroupIds, selectedActivities);

        //Validate Offered - Shouldn't be needed since only offered reg groups are added to the page.

        return regGroupIds;
    }

    /**
     * Validates the Reg groups by:
     * Status in the plan
     *
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#getValidRegGroupIdsToRemain(String, java.util.Map)
     */
    @Override
    public List<String> getValidRegGroupIdsToRemain(String courseOfferingId, Map<Object,Object> additionalRestrictions){
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

        // Retrieve reg groups for the Course Offering
        List<String> regGroupIds = new ArrayList<String>();
        try {
            SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.COURSE_OFFERING_ID, courseOfferingId);
            List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                    contextInfo).getRows();
            for( SearchResultRowInfo row : rows){
                regGroupIds.add(
                        KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.REG_GROUP_ID));
            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        }

        // Validate Reg Groups based on if they are already in plan
        regGroupIds = getValidRegGroupsFilteredByPlan(regGroupIds);


        return regGroupIds;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#createAddSectionEvent(String, String, String, String, java.util.List, javax.json.JsonObjectBuilder)
     */
    @Override
    public JsonObjectBuilder createAddSectionEvent(String termId, String courseOfferingCode, String courseOfferingId, String formatOfferingId, List<ActivityOfferingDetailsWrapper> activities, JsonObjectBuilder eventList){
        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("courseOfferingId", courseOfferingId);
        addEvent.add("termId", termId.replace(".", "-"));
        addEvent.add("courseOfferingCode", courseOfferingCode);
        addEvent.add("formatOfferingId", formatOfferingId);
        addEvent.add("uid", UUID.randomUUID().toString());

        // Create json array of activity to add and add it to event
        String regGroupCode="";
        JsonArrayBuilder activityEvents = Json.createArrayBuilder();
        for(ActivityOfferingDetailsWrapper activity : activities){
            JsonObjectBuilder activityEvent = Json.createObjectBuilder();
            String instructor = "";
            String days = "";
            String time = "";
            String location = "";
            String classUrl = "";

            // activities in the reg group will have the same reg group code.
            regGroupCode=activity.getRegGroupCode();

            // if activity value is null use empty string
            if(activity.getInstructorName()!=null) instructor = activity.getInstructorName();
            if(activity.getDays()!=null) days = activity.getDays();
            if(activity.getTime()!=null) time = activity.getTime();
            if(activity.getLocation()!=null) location = activity.getLocation();
            if(activity.getClassUrl()!=null) classUrl = activity.getClassUrl();

            // Add data to json for activity
            activityEvent.add("activityOfferingId", activity.getActivityOfferingId());
            activityEvent.add("activityFormatName", activity.getActivityFormatName());
            activityEvent.add("activityOfferingCode", activity.getActivityOfferingCode());

            activityEvent.add("instructor", instructor);
            activityEvent.add("days",days);
            activityEvent.add("time", time);
            activityEvent.add("location", location);
            activityEvent.add("currentEnrollment", activity.getCurrentEnrollment());
            activityEvent.add("maxEnrollment", activity.getMaxEnrollment());
            activityEvent.add("honors", activity.isHonors());
            activityEvent.add("classUrl", classUrl);
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            activityEvent.add("activityOfferingRequisites", activity.isHasActivityOfferingRequisites());
            activityEvents.add(activityEvent);
        }
        addEvent.add("activities", activityEvents);
        addEvent.add("regGroupCode", regGroupCode);

        eventList.add("COURSE_SECTION_ADDED", addEvent);
        return eventList;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#createFilterValidRegGroupsEvent
     */
    @Override
    public JsonObjectBuilder createFilterValidRegGroupsEvent(String termId, String courseOfferingCode,
            String formatOfferingId, List<String> regGroups, JsonObjectBuilder eventList,
            Map<Object, Object> additionalRestrictions){
        JsonObjectBuilder filterEvent = Json.createObjectBuilder();
        filterEvent.add("termId", termId.replace(".", "-"));
        filterEvent.add("courseOfferingCode", courseOfferingCode);
        filterEvent.add("formatOfferingId", formatOfferingId);

        // Deconstruct reg groups into list of AO and FO ids
        List<String> validFormatOfferings = new ArrayList<String>();
        List<String> validActivities = new ArrayList<String>();

        boolean singleRegGroupIdentified=false;
        for(String id : regGroups){
            List<String> activityIds = new ArrayList<>();
            List<String> formatIds = new ArrayList<>();
            try {
                SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants
                        .KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.REG_GROUP_ID, id);
                List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                boolean hasSelectedActivities=false;
                for( SearchResultRowInfo row : rows){
                    String activityId = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                            .ACTIVITY_OFFERING_ID);
                    activityIds.add(activityId);

                    //Determine if any activities of a single reg group have been selected
                    if (regGroups.size()==1 && additionalRestrictions !=null
                       && additionalRestrictions.get("selectedActivities")!=null
                       && ((List<String>) additionalRestrictions.get("selectedActivities")).contains(activityId))
                        singleRegGroupIdentified=true;
                }

                request = new SearchRequestInfo(CourseSearchConstants
                        .KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.REG_GROUP_ID, id);
                rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                for( SearchResultRowInfo row : rows){
                    formatIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                            .FORMAT_OFFERING_ID));
                }
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            }
            if(activityIds != null && !activityIds.isEmpty()){
                validActivities.addAll(activityIds);
            }
            if(formatIds != null && !formatIds.isEmpty()){
                validFormatOfferings.addAll(formatIds);
            }
        }

        //Send single regGroupId identified (i.e. selected) event
        if(singleRegGroupIdentified){
            try {
                filterEvent.add("regGroupId",KSCollectionUtils.getRequiredZeroElement(regGroups));
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Failure retrieving registration group", e);
            }
        }else{
            filterEvent.add("regGroupId","");
        }

        // Create json array of valid activity ids and add it to event
        JsonArrayBuilder activities = Json.createArrayBuilder();
        for(String activity : validActivities){
            activities.add(activity);

        }
        filterEvent.add("activities", activities);

        // Create json array of valid format ids and add it to event
        JsonArrayBuilder formats = Json.createArrayBuilder();
        for(String format : validFormatOfferings){
            formats.add(format);

        }
        filterEvent.add("formatOfferings", formats);

        eventList.add("FILTER_COURSE_OFFERING", filterEvent);
        return eventList;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#createFilterValidRegGroupsForRemovalEvent(String, String, String, java.util.List, javax.json.JsonObjectBuilder)
     */
    @Override
    public JsonObjectBuilder createFilterValidRegGroupsForRemovalEvent(String termId, String courseOfferingCode, String formatOfferingId, List<String> regGroupIds, JsonObjectBuilder eventList) {
        JsonObjectBuilder filterEvent = Json.createObjectBuilder();
        filterEvent.add("termId", termId.replace(".", "-"));
        filterEvent.add("courseOfferingCode", courseOfferingCode);
        filterEvent.add("formatOfferingId", formatOfferingId);

        // Deconstruct reg groups into list of AO and FO ids
        List<String> validActivities = new ArrayList<String>();
        for(String id : regGroupIds){
            List<String> activityIds = new ArrayList<>();
            try {
                SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants
                        .KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.REG_GROUP_ID, id);
                List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                for( SearchResultRowInfo row : rows){
                    activityIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                            .ACTIVITY_OFFERING_ID));
                }
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("Lui Service lookup error", e);
            }
            if(activityIds != null && !activityIds.isEmpty()){
                validActivities.addAll(activityIds);
            }
        }

        // Create json array of valid activity ids and add it to event
        JsonArrayBuilder activities = Json.createArrayBuilder();
        for(String activity : validActivities){
            activities.add(activity);

        }
        filterEvent.add("activities", activities);

        eventList.add("FILTER_COURSE_OFFERING_FOR_REMOVAL", filterEvent);
        return eventList;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#setupActivityRequisitesDialog(String, org.kuali.student.ap.coursesearch.form.CourseSectionDetailsDialogForm)
     */
    @Override
    public CourseSectionDetailsDialogForm setupActivityRequisitesDialog(String activityOfferingId, CourseSectionDetailsDialogForm form){
        // Fill in addition information needed by the add dialog
        ActivityOfferingInfo activity = null;
        try {
            activity = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOffering(activityOfferingId, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO Service lookup error", e);
        }
        form.setCourseOfferingCode(activity.getCourseOfferingCode());
        form.setCourseOfferingTitle(activity.getCourseOfferingTitle());

        // Retrieve a map of requisites for the activity offerings
        Map<String,List<String>> requisitesMap = CourseDetailsUtil.getActivityOfferingRequisitesMap(activity);

        // Fill in the different types of requisites from the map
        if(requisitesMap.containsKey(CourseDetailsUtil.PREREQUISITE_KEY)){
            form.setPrerequisites(requisitesMap.get(CourseDetailsUtil.PREREQUISITE_KEY));
        }else{
            form.setPrerequisites(new ArrayList<String>());
        }
        if(requisitesMap.containsKey(CourseDetailsUtil.COREQUISITE_KEY)){
            form.setCorequisites(requisitesMap.get(CourseDetailsUtil.COREQUISITE_KEY));
        }else{
            form.setCorequisites(new ArrayList<String>());
        }
        if(requisitesMap.containsKey(CourseDetailsUtil.ANTIREQUISITE_KEY)){
            form.setAntirequisites(requisitesMap.get(CourseDetailsUtil.ANTIREQUISITE_KEY));
        }else{
            form.setAntirequisites(new ArrayList<String>());
        }

        return form;
    }

    /**
     * Filters a list of registration groups based on a list of activity offering ids
     *
     * @param regGroupIds - List of registration ids to filter
     * @param selectedActivities - List of activity ids to be found in valid reg groups
     * @return A list of filtered registration groups
     */
    private List<String> getValidRegGroupsFilteredBySelectedActivities(
            List<String> regGroupIds, List<String> selectedActivities){
        // If no activities are sent skip filtering
        if(selectedActivities != null && !selectedActivities.isEmpty()){
            List<String> regGroupIdsFromSelectedAOs = new ArrayList<String>();
            for(String activityId : selectedActivities){
                try {
                    SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants
                            .KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY);
                    request.addParam(CourseSearchConstants.SearchParameters.ACTIVITY_OFFERING_ID, activityId);
                    List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                            KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                    List<String> tempIds = new ArrayList<>();
                    for( SearchResultRowInfo row : rows){
                        tempIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns
                                .REG_GROUP_ID));
                    }
                    if(regGroupIdsFromSelectedAOs.isEmpty()){
                        regGroupIdsFromSelectedAOs.addAll(tempIds);
                    }else{
                        regGroupIdsFromSelectedAOs= (List<String>)CollectionUtils.intersection(regGroupIdsFromSelectedAOs,tempIds);
                    }
                } catch (InvalidParameterException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (MissingParameterException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (OperationFailedException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                } catch (PermissionDeniedException e) {
                    throw new IllegalArgumentException("Lui Service lookup error", e);
                }
            }
            List<String> validAOGroups = new ArrayList<String>();
            for(String id : regGroupIds){
                if(regGroupIdsFromSelectedAOs.contains(id)){
                    validAOGroups.add(id);
                }
            }

            regGroupIds = validAOGroups;
        }
        return regGroupIds;
    }

    /**
     * Filter a list of registration groups based on if it is already in the default learning plan
     *
     * @param regGroupIds - The list of registration ids to filter
     * @return A list of filtered reg groups not already in plan
     */
    private List<String> getValidRegGroupsFilteredByPlan(List<String> regGroupIds){
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
        List<String> validGroupIds = new ArrayList<String>();
        for(String id : regGroupIds){
            //Check if there exist a plan item in the plan for the reg group
            try {
                List<PlanItemInfo> item = KsapFrameworkServiceLocator.getAcademicPlanService()
                        .getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlan.getId(), id,
                                PlanConstants.REG_GROUP_TYPE, KsapFrameworkServiceLocator.getContext().getContextInfo());
                if(item ==null || item.isEmpty()){
                    // If plan item does not exist reg group is valid
                    validGroupIds.add(id);
                }
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("AP lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("AP lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("AP lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("AP lookup error", e);
            }
        }
        return validGroupIds;
    }

    /**
     * Finds the information on the primary instructor from a list of instructors
     *
     * @param instructors - List of instructors participating in activity
     * @return Information on the primary instructor from the list
     */
    private OfferingInstructorInfo findDisplayInstructor(List<OfferingInstructorInfo> instructors) {
        OfferingInstructorInfo result = null;

        // If list of instructors is empty return null
        if (instructors != null && !instructors.isEmpty()) {

            // Build the display name for the Instructor
            Collection<OfferingInstructorInfo> highestInstEffortInstructors = new ArrayList<OfferingInstructorInfo>();
            float highestInstEffortComparison = 0f;

            // find instructors with highest participation from the list
            for (OfferingInstructorInfo instructor : instructors) {

                // Only instructors with participation are considered
                if (instructor.getPercentageEffort() != null) {

                    // If participation is higher than current list, reset with higher participation instructor
                    if (instructor.getPercentageEffort() > highestInstEffortComparison) {
                        highestInstEffortInstructors.clear();
                        highestInstEffortComparison = instructor.getPercentageEffort();
                        highestInstEffortInstructors.add(instructor);
                    }

                    // If participation is equal to current highest add instructor to current list
                    else if (instructor.getPercentageEffort() == highestInstEffortComparison) {
                        highestInstEffortInstructors.add(instructor);
                    }
                }
            }

            // Select instructor
            if(highestInstEffortInstructors.isEmpty()){
                return result;
            }else if (highestInstEffortInstructors.size() == 1) {
                // If only one participate return first
                result = highestInstEffortInstructors.iterator().next();
            } else {

                // If multiple instructors with highest participation get first alphabetically
                List<String> names = new ArrayList<String>(highestInstEffortInstructors.size());
                Map<String, OfferingInstructorInfo> nameMap = new HashMap<String, OfferingInstructorInfo>(highestInstEffortInstructors.size());
                for (OfferingInstructorInfo oiInfo : highestInstEffortInstructors) {
                    names.add(oiInfo.getPersonName());
                    nameMap.put(oiInfo.getPersonName(), oiInfo);
                }
                Collections.sort(names);
                result = nameMap.get(names.get(0));
            }
        }

        return result;
    }

    /**
     * Add room and schedule information to an existing Activity Offering wrapper
     *
     * @param aoWrapper - Activity to update
     * @param isTBA - If information schedule status is TBA
     * @param roomId - Id of the room location
     * @param timeSlot - The time slot
     */
    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingDetailsWrapper aoWrapper, Boolean isTBA, String roomId, TimeSlotInfo timeSlot) {
        RoomInfo roomInfo = null;
        if (StringUtils.isNotBlank(roomId)) {
            try {
                roomInfo = KsapFrameworkServiceLocator.getRoomService().getRoom(roomId, ContextUtils.createDefaultContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            }
        }
        updateScheduleToAOWrapperForDisplay(aoWrapper, isTBA, roomInfo, timeSlot);
    }

    /**
     * Update an activity wrapper to include scheduling information
     *
     * @param aoWrapper - Activity wraper being updated
     * @param isTBA - If activity scheduling data is TBA
     * @param roomInfo - Room informationto load
     * @param timeSlot - Time slot information to load
     */
    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingDetailsWrapper aoWrapper, Boolean isTBA,
                                                     RoomInfo roomInfo, TimeSlotInfo timeSlot) {
        // Update time slot information
        if (timeSlot != null) {

            TimeOfDayInfo startTime = timeSlot.getStartTime();
            TimeOfDayInfo endTime = timeSlot.getEndTime();
            List<Integer> days = timeSlot.getWeekdays();

            if ((startTime != null && startTime.getHour() != null) && (endTime != null && endTime.getHour() != null)) {
                aoWrapper.setTime(TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime) + " - " + TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime));
            }

            if (days != null && days.size() > 0) {
                aoWrapper.setDays(getDays(days));
            }
        }

        // Update room information
        if (roomInfo != null && StringUtils.isNotBlank(roomInfo.getBuildingId())) {

            BuildingInfo buildingInfo = null;
            try {
                buildingInfo = KsapFrameworkServiceLocator.getRoomService().getBuilding(roomInfo.getBuildingId(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("Room Service lookup error", e);
            }
            aoWrapper.setLocation(buildingInfo.getBuildingCode() + " " + roomInfo.getRoomCode());

        }
    }

    /**
     * Finds the already planned registration group and creates the details for them
     *
     * @param termId - Id of the course offering being loaded
     * @param activities - List of activities for the course offering
     * @return A populated list of planned registration groups
     */
    private List<PlannedRegistrationGroupDetailsWrapper> getPlannedPlannedRegistrationGroups(String termId,
            List<ActivityOfferingDetailsWrapper> activities){

        // Get registration group plan items
        String learningPlanId = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan().getId();
        List<PlanItem> regItems = new ArrayList<PlanItem>();
        List<RegistrationGroupInfo> regGroups = null;
        List<PlanItem> items = KsapFrameworkServiceLocator.getPlanHelper().getPlanItems(learningPlanId);
        for(PlanItem item : items){
            if(!item.getRefObjectType().equals(PlanConstants.REG_GROUP_TYPE)){
                continue;
            }
            if(!item.getPlanTermIds().contains(termId)){
                continue;
            }
            regItems.add(item);
        }



        List<String> ids = new ArrayList<String>();
        for(PlanItem item : regItems){
            ids.add(item.getRefObjectId());
        }

        // Get registration groups from plan items
        try {
            regGroups = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroupsByIds(
                    ids,KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("CO service lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO service lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO service lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO service lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO service lookup error", e);
        }

        // Load planned registration group details
        List<PlannedRegistrationGroupDetailsWrapper> plannedRegistrationGroupDetailsWrappers = new ArrayList<PlannedRegistrationGroupDetailsWrapper>();
        for(RegistrationGroupInfo regGroup : regGroups){

            //ignore non-offered Reg Groups
            if (!LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY.equals(regGroup.getStateKey())) continue;

            PlannedRegistrationGroupDetailsWrapper plannedRegistrationGroup = new PlannedRegistrationGroupDetailsWrapper();
            plannedRegistrationGroup.setRegGroupCode(regGroup.getName());
            for(String id : regGroup.getActivityOfferingIds()){
                for(ActivityOfferingDetailsWrapper activityOfferingDetailsWrapper : activities){
                    if(activityOfferingDetailsWrapper.getActivityOfferingId().equals(id)){
                        // Set activity in plan status
                        activityOfferingDetailsWrapper.setInPlan(true);
                        plannedRegistrationGroup.addActivities(activityOfferingDetailsWrapper);
                        break;
                    }
                }
            }
            // If no activities are filled then it is a bad registration group and don't add it to the list
            if(plannedRegistrationGroup.getActivities() == null || plannedRegistrationGroup.getActivities().isEmpty()) continue;

            Collections.sort(plannedRegistrationGroup.getActivities(),new Comparator<ActivityOfferingDetailsWrapper>(){
                @Override
                public int compare(ActivityOfferingDetailsWrapper a1, ActivityOfferingDetailsWrapper a2){
                    if(a1.getFormatIndex() == a2.getFormatIndex()) return 0;
                    if(a1.getFormatIndex() > a2.getFormatIndex()) return 1;
                    return -1;
                }
            });

            plannedRegistrationGroupDetailsWrappers.add(plannedRegistrationGroup);
        }

        return plannedRegistrationGroupDetailsWrappers;
    }

    /**
     * Loads the activity data for a CO and then creates a map to group it by the activity type and format offerings
     * @param courseOfferingId - Id of the CO activities are being retrieved for
     * @param validActivityOfferings - List of valid AO ids based on the registration groups available
     * @param isCourseOfferingVariableCredit - Flag indicating if the course offering is a variable credit course
     * @return - The activity offerings grouped by there format id and then grouped related format type
     */
    private Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>> getAOData(String courseOfferingId, List<String> validActivityOfferings, List<String> validActivityOfferingsToRemain, boolean isCourseOfferingVariableCredit) {
        Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>> aoMapByFormatName = new HashMap<String, Map<String, List<ActivityOfferingDetailsWrapper>>>();
        List<ActivityOfferingInfo>  activityOfferings = new ArrayList<>();

        // Retrieve and sort all activities for the CO
        try {
            List<ActivityOfferingInfo>  activityOfferingsTemp = KsapFrameworkServiceLocator
                    .getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, KsapFrameworkServiceLocator.getContext().getContextInfo());

            for (ActivityOfferingInfo ao : activityOfferingsTemp) {
                SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants
                        .KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.ACTIVITY_OFFERING_ID, ao.getId());
                List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                if (!rows.isEmpty()) {
                    activityOfferings.add(ao);
                }
            }

            Collections.sort(activityOfferings, new ActivityOfferingInfoComparator());

        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("AO lookup error", e);
        }

        Map<String, FormatOfferingInfo> formatOfferingInfoMap = new HashMap<String, FormatOfferingInfo>();
        for (ActivityOfferingInfo activityOffering : activityOfferings) {
            if (activityOffering.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
                // Retrieve current group by the format id, if entry is missing create it
                Map<String, List<ActivityOfferingDetailsWrapper>> aosByFormat = aoMapByFormatName.get(activityOffering.getFormatOfferingId());
                if (aosByFormat == null) {
                    aosByFormat = new LinkedHashMap<String, List<ActivityOfferingDetailsWrapper>>();
                }

                // Convert into wrapper used on page
                ActivityOfferingDetailsWrapper wrapper = convertAOInfoToWrapper(activityOffering, isCourseOfferingVariableCredit, formatOfferingInfoMap);

                // Retrieve current group by the format type, if entry is missing create it
                String typeKey = activityOffering.getTypeKey();
                List<ActivityOfferingDetailsWrapper> aosByType = aosByFormat.get(typeKey);
                if (aosByType == null) {
                    aosByType = new ArrayList<ActivityOfferingDetailsWrapper>();
                }

                // Set whether activity is considered  valid
                wrapper.setValidActivity(validActivityOfferings.contains(wrapper.getActivityOfferingId()));
                wrapper.setValidActivityToRemain(validActivityOfferingsToRemain.contains(wrapper.getActivityOfferingId()));

                //Add entry into map
                aosByType.add(wrapper);
                aosByFormat.put(typeKey, aosByType);

                String keys[] = aosByFormat.keySet().toArray(new String[aosByFormat.keySet().size()]);
                for(int i = 0; i<keys.length; i++){
                    if(typeKey.equals(keys[i])){
                        wrapper.setFormatIndex(i);
                    }
                }

                aoMapByFormatName.put(activityOffering.getFormatOfferingId(), aosByFormat);
            }
        }
        return aoMapByFormatName;
    }


    /**
     * Converts a list of integer representations of a list of day into a string based
     * If translation is needed outside this class move into a common util
     *
     * @param intList - The list of day ints to translate
     * @return String value of the day list
     */
    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if (intList == null) return sb.toString();

        for (Integer d : intList) {
            sb.append(convertIntoDays(d));
        }
        return sb.toString();
    }

    /**
     * Converts a integer representation of a day into a string based on the SchedulingService
     * If translation is needed outside this class move into a common util
     *
     * @param day - The day int to translate
     * @return String value of the day
     */
    private String convertIntoDays(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        return dayOfWeek;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public boolean isVariableCreditCourse(CourseOffering courseOffering) {

        CreditsFormatter.Range range = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().getRange(courseOffering);

        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()) return true;
        return !range.getMax().equals(range.getMin());
    }

}
