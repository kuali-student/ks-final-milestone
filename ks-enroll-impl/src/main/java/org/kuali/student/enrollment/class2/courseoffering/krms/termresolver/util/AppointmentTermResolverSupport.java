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
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Support class for Appointment term resolvers
 *
 * @author Kuali Student Team
 */
public abstract class AppointmentTermResolverSupport<T> implements TermResolver<T> {
    private AppointmentService appointmentService;


    public AppointmentService getAppointmentService() {
        return appointmentService;
    }
    protected List<AppointmentSlotInfo> getAppointmentSlotsForPerson(List<MilestoneInfo> milestones, String personId,
                                                                     ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AppointmentSlotInfo> allAppointmentSlots = new ArrayList<>();
        for (MilestoneInfo milestone:milestones) {
            String milestoneId = milestone.getId();

            // Find the appointment slots for the person/period
            List<AppointmentSlotInfo> appointmentSlots =
                    appointmentService.getAppointmentSlotsByPersonAndPeriod(personId, milestoneId, contextInfo);

            if (appointmentSlots != null && !appointmentSlots.isEmpty()) {
                allAppointmentSlots.addAll(appointmentSlots);
            }
        }
        return allAppointmentSlots;
    }

    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
}
