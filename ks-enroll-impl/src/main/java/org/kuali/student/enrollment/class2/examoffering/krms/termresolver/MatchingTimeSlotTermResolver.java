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

package org.kuali.student.enrollment.class2.examoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Return true if student has an unsubmitted registration request that includes
 * the given course or if the student is currently enrolled for the given course.
 *
 * Example rule statement:
 * 1) Must be concurrently enrolled in all courses from <courses>
 *
 * @author Kuali Student Team
 */
public class MatchingTimeSlotTermResolver implements TermResolver<Boolean> {

    private CourseOfferingService courseOfferingService;
    private SchedulingService schedulingService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_AO_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_MATCHINGTIMESLOT;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> temp = new HashSet<String>(3);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END);
        return Collections.unmodifiableSet(temp);
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);

        String weekdays = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING);
        String startTime = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START);

        try {
            ActivityOffering ao = this.retrieveActivityOffering(resolvedPrereqs, context);
            List<ScheduleInfo> schedules = this.getSchedulingService().getSchedulesByIds(ao.getScheduleIds(), context);
            for(ScheduleInfo schedule : schedules){
                for(ScheduleComponentInfo scheduleComponent : schedule.getScheduleComponents()){
                    List<TimeSlotInfo> timeSlots = this.getSchedulingService().getTimeSlotsByIds(scheduleComponent.getTimeSlotIds(), context);
                    for(TimeSlotInfo timeSlot : timeSlots){
                        if(weekdays.equals(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlot.getWeekdays()))
                                && Long.valueOf(startTime).equals(timeSlot.getStartTime().getMilliSeconds())){
                            return true;
                        }
                    }
                }
            }


        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

    private ActivityOffering retrieveActivityOffering(Map<String, Object> resolvedPrereqs, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        ActivityOffering ao = (ActivityOffering) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_AO);
        if(ao == null){
            String aoId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_AO_ID);
            ao = this.getCourseOfferingService().getActivityOffering(aoId, context);
            resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_AO, ao);
        }

        return ao;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
}
