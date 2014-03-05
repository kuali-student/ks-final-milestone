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
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Return true if student has an unsubmitted registration request that includes
 * the given course or if the student is currently enrolled for the given course.
 * <p/>
 * Example rule statement:
 * 1) Must be concurrently enrolled in all courses from <courses>
 *
 * @author Kuali Student Team
 */
public class MatchingTimeSlotTermResolver implements TermResolver<Boolean> {

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_TIMESLOTS);
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
        return Collections.unmodifiableSet(temp);
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String weekdays = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING);
        String startTime = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START);

        try {
            List<TimeSlotInfo> timeSlots = (List<TimeSlotInfo>) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_TIMESLOTS);
            for (TimeSlotInfo timeSlot : timeSlots) {
                if (weekdays.equals(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlot.getWeekdays()))
                        && Long.valueOf(startTime).equals(TimeOfDayHelper.getMillis(timeSlot.getStartTime()))) {
                    return true;
                }
            }


        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

}
