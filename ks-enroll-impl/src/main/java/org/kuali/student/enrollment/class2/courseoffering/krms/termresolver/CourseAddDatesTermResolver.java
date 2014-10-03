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
 * Created by Paul Richardson on 9/16/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.dto.CourseAddDates;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.AppointmentTermResolverSupport;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns relevant dates for registration request date check failures. Dates will come back as a JSON-string that can
 * be easily parsed and read by any client.
 *
 * If the student has an appointment slot in the future, the only field provided will be "appointmentSlot", which will
 * be the slot date/time formatted as such: March 19th, 2012, 09:00AM
 *
 * If the student is attempting to register during the early registration period, but does not have an appointment, the
 * only field provided will be "noAppointment", set as a boolean to true.
 *
 * If the student is attempting to register too early, the only field provided will be "startDate", which will be set as
 * the first available registration date formatted as a String M/dd/yyyy.
 *
 * If the student is attempting to register too late, the only field provided will be "endDate", which will be set as
 * the last available registration date formatted as a String M/dd/yyyy.
 *
 * @author Kuali Student Team
 */
public class CourseAddDatesTermResolver extends AppointmentTermResolverSupport<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseAddDatesTermResolver.class);

    private AtpService atpService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>();
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.ATP_ID_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.COURSE_ADD_DATES_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters)
            throws TermResolutionException {
        // Resolve pre-requisite terms
        ContextInfo contextInfo =
                (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());
        String atpId = (String) resolvedPrereqs.get(RulesExecutionConstants.ATP_ID_TERM.getName());

        CourseAddDates courseAddDates = new CourseAddDates();

        DateTime currentDate = new DateTime(contextInfo.getCurrentDate());

        try {
            /*
             * Checking the following scenarios:
             *
             * 1. If the student has an appointment slot in the future
             * 2. If the student is attempting to register during the early registration period, but does not have an
             * appointment
             */
            checkEarlyRegistration(courseAddDates, personId, atpId, currentDate, contextInfo);

            // if neither of the first two scenarios applies, determine if we are too late or too early for registration
            checkMilestoneDates(courseAddDates, atpId, currentDate, contextInfo);
        } catch (Exception ex) {
            LOGGER.error("Unable to resolve course add dates", ex);
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, ex, this);
        }
        
        // Marshall the CourseAddDates object as JSON
        ObjectMapper mapper = new ObjectMapper();
        String courseAddDatesJson = null;
        try {
            courseAddDatesJson = mapper.writeValueAsString(courseAddDates);
        } catch (IOException ex) {
            LOGGER.error("Unable to marshall course add dates", ex);
        }
        return courseAddDatesJson;
    }

    private void checkEarlyRegistration(CourseAddDates courseAddDates, String personId, String atpId,
                                        DateTime currentDate, ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException {
        // getting the early registration period, if it exists for this term
        List<MilestoneInfo> earlyRegMilestones =
                atpService.getMilestonesByTypeForAtp(atpId,
                        AtpServiceConstants.MILESTONE_EARLY_REGISTRATION_PERIOD_TYPE_KEY, contextInfo);

        // now let's see if the student has any appointment slots
        List<AppointmentSlotInfo> appointmentSlots =
                getAppointmentSlotsForPerson(earlyRegMilestones, personId, contextInfo);
        if (appointmentSlots != null && !appointmentSlots.isEmpty()) {
            // see if the student has a future appointment
            DateTime appointmentStart = null;
            for (AppointmentSlotInfo appointmentSlot:appointmentSlots) {
                DateTime startDate = new DateTime(appointmentSlot.getStartDate());
                if (currentDate.isBefore(startDate) &&
                        (appointmentStart == null || startDate.isBefore(appointmentStart))) {
                    appointmentStart = startDate;
                }
            }
            if (appointmentStart != null) {
                // set the formatted appointment start date to be sent back to the client
                KSDateTimeFormatter dateFormatter = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_TIME_FORMATTER;
                courseAddDates.setAppointmentSlot(dateFormatter.format(appointmentStart));
            }
        } else {
            // see if we are still in the early reg period, if we are set the "no appointment" flag to true
            if (dateInMilestones(currentDate, earlyRegMilestones)) {
                courseAddDates.setNoAppointment(true);
            }
        }
    }

    private void checkMilestoneDates(CourseAddDates courseAddDates, String atpId, DateTime currentDate,
                                     ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException {
        if (courseAddDates.getNoAppointment() == null && courseAddDates.getAppointmentSlot() == null) {
            List<MilestoneInfo> milestones = new ArrayList<>();
            // getting the "non-appointment" early registration period, if it exists for this term
            List<MilestoneInfo> nonApptMilestones =
                    atpService.getMilestonesByTypeForAtp(atpId,
                            AtpServiceConstants.MILESTONE_EARLY_REGISTRATION_PERIOD_NONAPPT_TYPE_KEY, contextInfo);
            if (nonApptMilestones != null && !nonApptMilestones.isEmpty()) {
                milestones.addAll(nonApptMilestones);
            }
            // getting the schedule adjustment period, if it exists for this term
            List<MilestoneInfo> scheduleAdjustmentMilestones =
                    atpService.getMilestonesByTypeForAtp(atpId,
                            AtpServiceConstants.MILESTONE_SCHEDULE_ADJUSTMENT_PERIOD_TYPE_KEY, contextInfo);
            if (scheduleAdjustmentMilestones != null && !scheduleAdjustmentMilestones.isEmpty()) {
                milestones.addAll(scheduleAdjustmentMilestones);
            }
            DateTime startDate = null;
            DateTime endDate = null;
            for (MilestoneInfo milestone:milestones) {
                if (milestone.getStartDate() != null) {
                    DateTime milestoneStart = new DateTime(milestone.getStartDate());
                    if (startDate == null || milestoneStart.isBefore(startDate)) {
                        startDate = milestoneStart;
                    }
                }
                if (milestone.getEndDate() != null) {
                    DateTime milestoneEnd = new DateTime(milestone.getEndDate());
                    if (endDate == null || milestoneEnd.isAfter(endDate)) {
                        endDate = milestoneEnd;
                    }
                }
            }
            KSDateTimeFormatter dateFormatter = DateFormatters.MONTH_NOZERO_DAY_YEAR_DATE_FORMATTER;
            if (startDate != null && startDate.isAfter(currentDate)) {
                // too early
                courseAddDates.setStartDate(dateFormatter.format(startDate));
            }
            if (endDate != null && endDate.isBefore(currentDate)) {
                // too late
                courseAddDates.setEndDate(dateFormatter.format(endDate));
            }
        }
    }

    // helper method to see if the target date falls within one of the given milestones
    private boolean dateInMilestones(DateTime date, List<MilestoneInfo> milestones) {
        boolean dateInMilestones = false;

        for (MilestoneInfo milestone: milestones) {
            DateTime startDate = new DateTime(milestone.getStartDate());
            DateTime endDate = new DateTime(milestone.getEndDate());
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                dateInMilestones = true;
                break;
            }
        }

        return dateInMilestones;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

}
