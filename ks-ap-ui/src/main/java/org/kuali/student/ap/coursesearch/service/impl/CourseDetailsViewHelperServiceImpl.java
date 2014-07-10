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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.GroupBase;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.widget.Disclosure;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
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
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
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
import org.kuali.student.r2.lum.course.dto.CourseInfo;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class CourseDetailsViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseDetailsViewHelperService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseDetailsViewHelperServiceImpl.class);

    // Text Keys matching the Template values of the Natural Language Templates used in the requisite rules
    private static final String PREREQUISITE_KEY = "Student Eligibility & Prerequisite";
    private static final String COREQUISITE_KEY = "Corequisite";
    private static final String ANTIREQUISITE_KEY = "Antirequisite";

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCourseSectionDetails(UifFormBase form, String courseId)  {
        load((CourseSectionDetailsForm) form, courseId);
    }

    private void load(CourseSectionDetailsForm form, String courseId)  {
        CourseInfo courseInfo = KsapFrameworkServiceLocator.getCourseHelper().getCourseInfo(courseId);
        form.setCourseTitle(courseInfo.getCourseTitle());
        form.setCourseCode(courseInfo.getCode());
        List<String> termIds = KsapFrameworkServiceLocator.getCourseHelper().getScheduledTermsForCourse(courseInfo);
        form.setCourseTermDetailsWrappers(getScheduledTerms(termIds, courseId));
    }

    private List<CourseTermDetailsWrapper> getScheduledTerms(List<String> scheduledTermsList, String courseId)  {

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

            List<String> courseIds = new ArrayList<String>();
            courseIds.add(courseId);
            Map<String, List<CourseOfferingDetailsWrapper>> courseOfferingsByTerm = processCourseOfferingsByTerm(courseIds, terms);

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
    public class CourseOfferingInfoComparator implements Comparator<CourseOfferingInfo> {

        @Override
        public int compare(CourseOfferingInfo o1, CourseOfferingInfo o2) {
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
        List<CourseOfferingInfo> courseOfferings = KsapFrameworkServiceLocator.getCourseHelper().getCourseOfferingsForCoursesAndTerms(courseIds, terms);
        Collections.sort(courseOfferings, new CourseOfferingInfoComparator());
        Map<String, List<CourseOfferingDetailsWrapper>> map = new HashMap<String, List<CourseOfferingDetailsWrapper>>();
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

        for (CourseOfferingInfo offering : courseOfferings) {
            String termId = offering.getTermId();
            List<CourseOfferingDetailsWrapper> offeringsByTerm = map.get(termId);
            if (offeringsByTerm == null)
                offeringsByTerm = new ArrayList<CourseOfferingDetailsWrapper>();

            List<RegistrationGroupInfo> validRegGroups = getValidRegGroups(offering.getId(),new HashMap<Object,Object>());
            List<String> validFormatOfferings = new ArrayList<String>();
            List<String> validActivities = new ArrayList<String>();
            for(RegistrationGroupInfo group : validRegGroups){
                validActivities.addAll(group.getActivityOfferingIds());
                if(!validFormatOfferings.contains(group.getFormatOfferingId())){
                    validFormatOfferings.add(group.getFormatOfferingId());
                }
            }

            CourseOfferingDetailsWrapper courseOfferingDetailsWrapper = new CourseOfferingDetailsWrapper(offering);
            List<FormatOfferingInfo> formatOfferings = null;
            try {
                formatOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOfferingsByCourseOffering(offering.getId(), contextInfo);
                List<FormatOfferingInfoWrapper> formatOfferingWrappers = new ArrayList<FormatOfferingInfoWrapper>(formatOfferings.size());
                Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>>
                        aosByFormat = getAOData(offering.getId(),validActivities);

                List<PlannedRegistrationGroupDetailsWrapper> plannedActivityOfferings = new ArrayList<PlannedRegistrationGroupDetailsWrapper>();

                for (FormatOfferingInfo formatOffering : formatOfferings) {
                    FormatOfferingInfoWrapper formatOfferingInfo = new FormatOfferingInfoWrapper(formatOffering, courseOfferingDetailsWrapper.getCourseOfferingCode());
                    formatOfferingInfo.setValidFormat(validFormatOfferings.contains(formatOfferingInfo.getFormatOfferingId()));
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
                        plannedActivityOfferings.addAll(getPlannedPlannedRegistrationGroups(tempActivityOfferingDetailWrappers));
                    }

                    formatOfferingInfo.setActivityFormatDetailsWrappers(activityFormatDetailsWrappers);
                    formatOfferingWrappers.add(formatOfferingInfo);

                }
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
    public ActivityOfferingDetailsWrapper convertAOInfoToWrapper(ActivityOfferingInfo aoInfo)  {
        ActivityOfferingDetailsWrapper wrapper = new ActivityOfferingDetailsWrapper(aoInfo, false, true);
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();

        int firstValue = 0;

        FormatOfferingInfo fo = null;
        try {
            fo = KsapFrameworkServiceLocator.getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), contextInfo);
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
            wrapper.setActivityOfferingRequisites(aoRequisites);

        wrapper.setInPlan(false);
        List<RegistrationGroupInfo> regGroups = null;
        try {
            regGroups = KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroupsByActivityOffering(wrapper.getActivityOfferingId(), contextInfo);
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
        String planId = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan().getId();
        if(regGroups!=null){
            for(RegistrationGroupInfo regGroup : regGroups){
                List<PlanItemInfo> items = null;
                try {
                    items = KsapFrameworkServiceLocator.getAcademicPlanService()
                            .getPlanItemsInPlanByRefObjectIdByRefObjectType(planId, regGroup.getId(),
                                    PlanConstants.REG_GROUP_TYPE, contextInfo);
                } catch (InvalidParameterException e) {
                    throw new IllegalArgumentException("Academic Plan Service lookup error", e);
                } catch (MissingParameterException e) {
                    throw new IllegalArgumentException("Academic Plan Service lookup error", e);
                } catch (OperationFailedException e) {
                    throw new IllegalArgumentException("Academic Plan Service lookup error", e);
                } catch (PermissionDeniedException e) {
                    throw new IllegalArgumentException("Academic Plan Service lookup error", e);
                }
                if(!items.isEmpty()){
                    wrapper.setInPlan(true);
                    wrapper.setRegGroupCode(regGroup.getName());
                    wrapper.setRegGroupId(regGroup.getId());
                }
            }
        }

        try {
            wrapper.setCurrentEnrollment(KsapFrameworkServiceLocator.getCourseSeatCountService()
                    .getSeatCountForActivityOffering(aoInfo.getId(), contextInfo).getAvailableSeats());
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
     * Offered Status
     * List of selected AOs
     * Status in the plan
     *
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#getValidRegGroups(String, java.util.Map)
     */
    @Override
    public List<RegistrationGroupInfo> getValidRegGroups(String courseOfferingId, Map<Object,Object> additionalRestrictions){
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();
        // Retrieve reg groups for the Course Offering
        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();
        try {
            List<FormatOfferingInfo> formats = KsapFrameworkServiceLocator.getCourseOfferingService()
                    .getFormatOfferingsByCourseOffering(courseOfferingId,
                            contextInfo);
            for(FormatOfferingInfo format : formats){
                regGroups.addAll(KsapFrameworkServiceLocator.getCourseOfferingService().getRegistrationGroupsByFormatOffering(
                        format.getId(), contextInfo));
            }
        } catch (DoesNotExistException e) {
            // If no reg groups exit for course offering return null
            LOG.debug("No Registration Groups found for Course Offering "+courseOfferingId);
            return null;
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CO lookup error", e);
        }

        // Validate Reg Groups based on them being offered
        List<RegistrationGroupInfo> offeredRegGroups = new ArrayList<RegistrationGroupInfo>();
        for(RegistrationGroupInfo regGroup : regGroups){
            if(regGroup.getStateKey().equals(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY)){
                offeredRegGroups.add(regGroup);
            }
        }
        regGroups = offeredRegGroups;

        // Validate Reg Groups based on selected AOs
        List<String> selectedActivities = (List<String>) additionalRestrictions.get("selectedActivities");
        regGroups = getValidRegGroupsFilteredBySelectedActivities(regGroups, selectedActivities);

        // Validate Reg Groups based on if they are already in plan
        regGroups = getValidRegGroupsFilteredByPlan(regGroups);

        return regGroups;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#createAddSectionEvent(String, String, String, java.util.List, javax.json.JsonObjectBuilder)
     */
    @Override
    public JsonObjectBuilder createAddSectionEvent(String termId, String courseOfferingCode, String courseOfferingId, List<ActivityOfferingDetailsWrapper> activities, JsonObjectBuilder eventList){
        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("courseOfferingId", courseOfferingId);
        addEvent.add("termId", termId.replace(".", "-"));
        addEvent.add("courseOfferingCode", courseOfferingCode);
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
            List<String> requisites = new ArrayList<String>();

            // activities in the reg group will have the same reg group code.
            regGroupCode=activity.getRegGroupCode();

            // if activity value is null use empty string
            if(activity.getInstructorName()!=null) instructor = activity.getInstructorName();
            if(activity.getDays()!=null) days = activity.getDays();
            if(activity.getTime()!=null) time = activity.getTime();
            if(activity.getLocation()!=null) location = activity.getLocation();
            if(activity.getActivityOfferingRequisites()!=null) requisites = activity.getActivityOfferingRequisites();
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
            for (String requisit : requisites) {
                jsonArrayBuilder.add(requisit);
            }

            activityEvent.add("requisites", jsonArrayBuilder);
            activityEvents.add(activityEvent);
        }
        addEvent.add("activities", activityEvents);
        addEvent.add("regGroupCode", regGroupCode);

        eventList.add("COURSE_SECTION_ADDED", addEvent);
        return eventList;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.service.CourseDetailsViewHelperService#createFilterValidRegGroupsEvent(String, String, String, java.util.List, javax.json.JsonObjectBuilder)
     */
    @Override
    public JsonObjectBuilder createFilterValidRegGroupsEvent(String termId, String courseOfferingCode, String formatOfferingId, List<RegistrationGroupInfo> regGroups, JsonObjectBuilder eventList){
        JsonObjectBuilder filterEvent = Json.createObjectBuilder();
        filterEvent.add("termId", termId.replace(".", "-"));
        filterEvent.add("courseOfferingCode", courseOfferingCode);
        filterEvent.add("formatOfferingId", formatOfferingId);
        if(regGroups.size()==1){
            try {
                filterEvent.add("regGroupId",KSCollectionUtils.getRequiredZeroElement(regGroups).getId());
            } catch (OperationFailedException e) {
                throw new IllegalArgumentException("Failure retrieving registration group", e);
            }
        }else{
            filterEvent.add("regGroupId","");
        }

        // Deconstruct reg groups into list of AO and FO ids
        List<String> validFormatOfferings = new ArrayList<String>();
        List<String> validActivities = new ArrayList<String>();
        for(RegistrationGroupInfo group : regGroups){
            validActivities.addAll(group.getActivityOfferingIds());
            if(!validFormatOfferings.contains(group.getFormatOfferingId())){
                validFormatOfferings.add(group.getFormatOfferingId());
            }
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
        if(requisitesMap.containsKey(PREREQUISITE_KEY)){
            form.setPrerequisites(requisitesMap.get(PREREQUISITE_KEY));
        }else{
            form.setPrerequisites(new ArrayList<String>());
        }
        if(requisitesMap.containsKey(COREQUISITE_KEY)){
            form.setCorequisites(requisitesMap.get(COREQUISITE_KEY));
        }else{
            form.setCorequisites(new ArrayList<String>());
        }
        if(requisitesMap.containsKey(ANTIREQUISITE_KEY)){
            form.setAntirequisites(requisitesMap.get(ANTIREQUISITE_KEY));
        }else{
            form.setAntirequisites(new ArrayList<String>());
        }

        return form;
    }

    /**
     * Filters a list of registration groups based on a list of activity offering ids
     *
     * @param regGroups - List of registration groups to filter
     * @param selectedActivities - List of activity ids to be found in valid reg groups
     * @return A list of filtered registration groups
     */
    private List<RegistrationGroupInfo> getValidRegGroupsFilteredBySelectedActivities(
            List<RegistrationGroupInfo> regGroups, List<String> selectedActivities){
        // If no activities are sent skip filtering
        if(selectedActivities != null && !selectedActivities.isEmpty()){
            List<RegistrationGroupInfo> validAOGroups = new ArrayList<RegistrationGroupInfo>();
            for(RegistrationGroupInfo group : regGroups){
                boolean valid = true;

                // Check if reg group activities contain a selected activity.
                for(String activityId : selectedActivities){
                    if(!group.getActivityOfferingIds().contains(activityId)){
                        valid = false;
                        break;
                    }
                }
                if(valid){
                    validAOGroups.add(group);
                }
            }

            regGroups = validAOGroups;
        }
        return regGroups;
    }

    /**
     * Filter a list of registration groups based on if it is already in the default learning plan
     *
     * @param regGroups - The list of registration groups to filter
     * @return A list of filtered reg groups not already in plan
     */
    private List<RegistrationGroupInfo> getValidRegGroupsFilteredByPlan(List<RegistrationGroupInfo> regGroups){
        LearningPlan learningPlan = KsapFrameworkServiceLocator.getPlanHelper().getDefaultLearningPlan();
        List<RegistrationGroupInfo> validGroups = new ArrayList<RegistrationGroupInfo>();
        for(RegistrationGroupInfo group : regGroups){
            //Check if there exist a plan item in the plan for the reg group
            try {
                List<PlanItemInfo> item = KsapFrameworkServiceLocator.getAcademicPlanService()
                        .getPlanItemsInPlanByRefObjectIdByRefObjectType(learningPlan.getId(), group.getId(),
                                PlanConstants.REG_GROUP_TYPE, KsapFrameworkServiceLocator.getContext().getContextInfo());
                if(item ==null || item.isEmpty()){
                    // If plan item does not exist reg group is valid
                    validGroups.add(group);
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
        return validGroups;
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

    private void updateScheduleToAOWrapperForDisplay(ActivityOfferingDetailsWrapper aoWrapper, Boolean isTBA,
                                                     RoomInfo roomInfo, TimeSlotInfo timeSlot) {
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

    private List<PlannedRegistrationGroupDetailsWrapper> getPlannedPlannedRegistrationGroups(
            List<ActivityOfferingDetailsWrapper> activities){

        List<ActivityOfferingDetailsWrapper> activityOfferings = new ArrayList<ActivityOfferingDetailsWrapper>();
        for(ActivityOfferingDetailsWrapper activityOfferingDetailsWrapper : activities){
            if(activityOfferingDetailsWrapper.isInPlan()){
                activityOfferings.add(activityOfferingDetailsWrapper);
            }
        }

        List<PlannedRegistrationGroupDetailsWrapper> plannedRegistrationGroupDetailsWrappers = new ArrayList<PlannedRegistrationGroupDetailsWrapper>();
        for(ActivityOfferingDetailsWrapper activityFormatDetailsWrapper : activityOfferings){
            boolean found = false;
            for(PlannedRegistrationGroupDetailsWrapper plannedRegistrationGroupDetailsWrapper : plannedRegistrationGroupDetailsWrappers){
                if(plannedRegistrationGroupDetailsWrapper.getRegGroupCode().equals(activityFormatDetailsWrapper.getRegGroupCode())){
                    found = true;
                    plannedRegistrationGroupDetailsWrapper.addActivities(activityFormatDetailsWrapper);
                }
            }
            if(!found){
                PlannedRegistrationGroupDetailsWrapper newPlanReg = new PlannedRegistrationGroupDetailsWrapper();
                newPlanReg.setRegGroupCode(activityFormatDetailsWrapper.getRegGroupCode());
                newPlanReg.addActivities(activityFormatDetailsWrapper);
                plannedRegistrationGroupDetailsWrappers.add(newPlanReg);
            }
        }

        return plannedRegistrationGroupDetailsWrappers;
    }

    /**
     * Loads the activity data for a CO and then creates a map to group it by the activity type and format offerings
     * @param courseOfferingId - Id of the CO activities are being retrieved for
     * @param validActivityOfferings - List of valid AO ids based on the registration groups available
     * @return - The activity offerings grouped by there format id and then grouped related format type
     */
    private Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>> getAOData(String courseOfferingId, List<String> validActivityOfferings) {
        Map<String, Map<String, List<ActivityOfferingDetailsWrapper>>> aoMapByFormatName = new HashMap<String, Map<String, List<ActivityOfferingDetailsWrapper>>>();
        List<ActivityOfferingInfo>  activityOfferings = null;

        // Retrieve and sort all activities for the CO
        try {
            activityOfferings = KsapFrameworkServiceLocator.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, KsapFrameworkServiceLocator.getContext().getContextInfo());
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

        for (ActivityOfferingInfo activityOffering : activityOfferings) {
            if (activityOffering.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) {
                // Retrieve current group by the format id, if entry is missing create it
                Map<String, List<ActivityOfferingDetailsWrapper>> aosByFormat = aoMapByFormatName.get(activityOffering.getFormatOfferingId());
                if (aosByFormat == null) {
                    aosByFormat = new HashMap<String, List<ActivityOfferingDetailsWrapper>>();
                }

                // Convert into wrapper used on page
                ActivityOfferingDetailsWrapper wrapper = convertAOInfoToWrapper(activityOffering);

                // Retrieve current group by the format type, if entry is missing create it
                String typeKey = activityOffering.getTypeKey();
                List<ActivityOfferingDetailsWrapper> aosByType = aosByFormat.get(typeKey);
                if (aosByType == null) {
                    aosByType = new ArrayList<ActivityOfferingDetailsWrapper>();
                }

                // Set whether activity is considered  valid
                wrapper.setValidActivity(validActivityOfferings.contains(wrapper.getActivityOfferingId()));

                //Add entry into map
                aosByType.add(wrapper);
                aosByFormat.put(typeKey, aosByType);
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

}
