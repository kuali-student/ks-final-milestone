package org.kuali.student.enrollment.class2.appointment.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;

import java.util.ArrayList;
import java.util.List;

public class RegistrationWindowsManagementForm extends UifFormBase {
    private TermInfo termInfo;
    private String termType;
    private String termYear;
    //period drop-down field in Appointment Windows page
    private String periodId;
    private String periodName;
    private String periodInfoDetails;
    private List<KeyDateInfo> periodMilestones;
    private List<AppointmentWindowWrapper> appointmentWindows;

    private AppointmentWindowWrapper selectedAppointmentWindow;

    //    private List<String> appointmentWindowIdsToDelete;
    boolean showAddWindows;

//    private Map<String, List<AppointmentWindowInfo>> periodAndWindowsMap;

    public RegistrationWindowsManagementForm() {
        super();
        termInfo = new TermInfo();
//        periodInfoDetails = new String();
        periodMilestones = new ArrayList<KeyDateInfo>();
        appointmentWindows = new ArrayList<AppointmentWindowWrapper>();
//        appointmentWindowIdsToDelete = new ArrayList<String>();
        showAddWindows = false;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermYear() {
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

    public void setPeriodMilestones(List<KeyDateInfo> periodMilestones) {
        this.periodMilestones = periodMilestones;
    }

    public List<AppointmentWindowWrapper> getAppointmentWindows() {
        return appointmentWindows;
    }

    public void setAppointmentWindows(List<AppointmentWindowWrapper> appointmentWindows) {
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

    public AppointmentWindowWrapper getSelectedAppointmentWindow() {
        AppointmentWindowWrapper window = selectedAppointmentWindow;
        selectedAppointmentWindow = null;
        return window;
    }

    public void setSelectedAppointmentWindow(AppointmentWindowWrapper appointmentWindow) {
        selectedAppointmentWindow = appointmentWindow;
    }

    public void removeSelectedAppointmentWindow(AppointmentWindowWrapper window) {
        appointmentWindows.remove(window);
    }
}
