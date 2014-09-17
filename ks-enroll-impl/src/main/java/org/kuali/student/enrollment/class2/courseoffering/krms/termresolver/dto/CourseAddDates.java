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
 * Created by pauldanielrichardson on 9/16/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * This class is is a DTO containing fields for use by CourseAddDatesTermResolver.java.
 *
 * @author Kuali Student Team
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CourseAddDates {
    private String appointmentSlot;

    private Boolean noAppointment;

    private String startDate;

    private String endDate;

    public String getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(String appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public Boolean getNoAppointment() {
        return noAppointment;
    }

    public void setNoAppointment(Boolean noAppointment) {
        this.noAppointment = noAppointment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
