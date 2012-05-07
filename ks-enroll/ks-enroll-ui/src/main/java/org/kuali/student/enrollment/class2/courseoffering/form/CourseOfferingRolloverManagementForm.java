/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 5/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingRolloverManagementForm extends UifFormBase {

    private TermInfo termInfo;
    private String termType;
    private String termYear;
    //period drop-down field in Appointment Windows page
    private String periodId;
    private String periodName;
    private String periodInfoDetails;
    private List<KeyDateInfo> periodMilestones;
    private List<AppointmentWindowWrapper> appointmentWindows;

    //    private List<String> appointmentWindowIdsToDelete;
    boolean showAddWindows;

//    private Map<String, List<AppointmentWindowInfo>> periodAndWindowsMap;

    public CourseOfferingRolloverManagementForm(){
        termInfo = new TermInfo();
        periodInfoDetails = new String();
        periodMilestones = new ArrayList<KeyDateInfo>();
        appointmentWindows = new ArrayList<AppointmentWindowWrapper>();
//        appointmentWindowIdsToDelete = new ArrayList<String>();
        showAddWindows = false;
    }

    public TermInfo getTermInfo(){
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

    public String getTermType(){
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermYear(){
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getPeriodInfoDetails() {
        return periodInfoDetails;
    }

    public void setPeriodInfoDetails(String periodInfoDetails) {
        this.periodInfoDetails = periodInfoDetails;
    }

    public List<KeyDateInfo> getPeriodMilestones() {
        return periodMilestones;
    }

    public void setPeriodMilestones(List<KeyDateInfo> periodMilestones){
        this.periodMilestones = periodMilestones;
    }

    public List<AppointmentWindowWrapper> getAppointmentWindows(){
        return appointmentWindows;
    }

    public void setAppointmentWindows(List<AppointmentWindowWrapper> appointmentWindows){
        this.appointmentWindows = appointmentWindows;
    }

//    public List<String> getAppointmentWindowIdsToDelete() {
//        return appointmentWindowIdsToDelete;
//    }
//
//    public void setAppointmentWindowIdsToDelete(List<String> appointmentWindowIdsToDelete) {
//        this.appointmentWindowIdsToDelete = appointmentWindowIdsToDelete;
//    }

    public boolean isShowAddWindows() {
        return showAddWindows;
    }

    public void setShowAddWindows(boolean showAddWindows) {
        this.showAddWindows = showAddWindows;
    }
}
