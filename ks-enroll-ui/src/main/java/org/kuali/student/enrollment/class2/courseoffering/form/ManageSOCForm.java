package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageSOCForm extends UifFormBase {

    private String scheduleInitiatedDate;
    private String scheduleCompleteDate;
    private String scheduleDuration;

    private String publishInitiatedDate;
    private String publishCompleteDate;
    private String publishDuration;

    private String socStatus;
    private String socSchedulingStatus;
    private String termCode;
    private List<ManageSOCStatusHistory> statusHistory;

    private TermInfo termInfo;

    public ManageSOCForm(){
        statusHistory = new ArrayList<ManageSOCStatusHistory> ();
        ManageSOCStatusHistory history = new ManageSOCStatusHistory("OPEN","12/12/2012");
        statusHistory.add(history);
        history = new ManageSOCStatusHistory("CLOSED","12/12/2012");
        statusHistory.add(history);
        socStatus= "OPEN";
        socSchedulingStatus= "Not Started";

    }

    public String getScheduleInitiatedDate() {
        return scheduleInitiatedDate;
    }

    public void setScheduleInitiatedDate(String scheduleInitiatedDate) {
        this.scheduleInitiatedDate = scheduleInitiatedDate;
    }

    public String getScheduleCompleteDate() {
        return scheduleCompleteDate;
    }

    public void setScheduleCompleteDate(String scheduleCompleteDate) {
        this.scheduleCompleteDate = scheduleCompleteDate;
    }

    public String getScheduleDuration() {
        return scheduleDuration;
    }

    public void setScheduleDuration(String scheduleDuration) {
        this.scheduleDuration = scheduleDuration;
    }

    public String getPublishInitiatedDate() {
        return publishInitiatedDate;
    }

    public void setPublishInitiatedDate(String publishInitiatedDate) {
        this.publishInitiatedDate = publishInitiatedDate;
    }

    public String getPublishCompleteDate() {
        return publishCompleteDate;
    }

    public void setPublishCompleteDate(String publishCompleteDate) {
        this.publishCompleteDate = publishCompleteDate;
    }

    public String getPublishDuration() {
        return publishDuration;
    }

    public void setPublishDuration(String publishDuration) {
        this.publishDuration = publishDuration;
    }

    public String getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(String socStatus) {
        this.socStatus = socStatus;
    }

    public List<ManageSOCStatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<ManageSOCStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getSocSchedulingStatus() {
        return socSchedulingStatus;
    }

    public void setSocSchedulingStatus(String socSchedulingStatus) {
        this.socSchedulingStatus = socSchedulingStatus;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

}
