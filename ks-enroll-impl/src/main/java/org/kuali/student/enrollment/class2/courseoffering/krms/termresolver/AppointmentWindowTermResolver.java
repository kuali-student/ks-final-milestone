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
 * Created by Paul Richardson on 9/12/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is a term resolver that checks to see if the current date (as defined in the
 * context) falls within an "advanced registration" appointment slot for the person/term.
 *
 * @author Kuali Student Team
 */
public class AppointmentWindowTermResolver implements TermResolver<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentWindowTermResolver.class);

    private AppointmentService appointmentService;
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
        return KSKRMSServiceConstants.TERM_RESOLVER_APPOINTMENT_WINDOW;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        // Resolve pre-requisite terms
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());
        String atpId = (String) resolvedPrereqs.get(RulesExecutionConstants.ATP_ID_TERM.getName());

        DateTime currentDate = new DateTime(contextInfo.getCurrentDate());

        Boolean slotFound = false;

        try {
            // Find the milestones for the term's advanced registration period
            String keydateTypeParameter = AtpServiceConstants.MILESTONE_EARLY_REGISTRATION_PERIOD_TYPE_KEY;
            List<MilestoneInfo> milestones = atpService.getMilestonesByTypeForAtp(atpId, keydateTypeParameter, contextInfo);
            for (MilestoneInfo milestone:milestones) {
                String milestoneId = milestone.getId();

                // Find the appointment slots for the person/period
                List<AppointmentSlotInfo> appointmentSlots = appointmentService.getAppointmentSlotsByPersonAndPeriod(personId, milestoneId, contextInfo);

                for (AppointmentSlotInfo appointmentSlot:appointmentSlots) {
                    // See if the current date falls in the appointment slot
                    DateTime startDate = new DateTime(appointmentSlot.getStartDate());
                    if (appointmentSlot.getEndDate() != null) {
                        DateTime endDate = new DateTime(appointmentSlot.getEndDate());
                        slotFound = (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0);
                    } else {
                        slotFound = currentDate.compareTo(startDate) >= 0;
                    }
                    if (slotFound) {
                        break;
                    }
                }
                if (slotFound) {
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Exception trying to evaluate activity window", ex);
            slotFound = null;
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, ex, this);
        }

        // return the result
        return slotFound;
    }

    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
}
