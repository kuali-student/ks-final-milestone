package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private SocInfo socInfo;

    public ManageSOCForm()  {
        statusHistory = new ArrayList<ManageSOCStatusHistory> ();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

        String date1 = "11-01-2011 12:12 am";
        String date2 = "11-01-2012 12:12 am";
        String date3 = "01-01-2012 12:12 am";
        String date4 = "12-02-2011 12:12 am";

        try{
            ManageSOCStatusHistory history = new ManageSOCStatusHistory("Draft",date1,dateFormat.parse(date1));
            statusHistory.add(history);
            history = new ManageSOCStatusHistory("Closed","",null);
            statusHistory.add(history);
            history = new ManageSOCStatusHistory("Locked",date3,dateFormat.parse(date3));
            statusHistory.add(history);
            history = new ManageSOCStatusHistory("Open",date4,dateFormat.parse(date4));
            statusHistory.add(history);
            history = new ManageSOCStatusHistory("Final Edits","",null);
            statusHistory.add(history);
            history = new ManageSOCStatusHistory("Publishing",date2,dateFormat.parse(date2));
            statusHistory.add(history);
            history = new ManageSOCStatusHistory("Published",date1,dateFormat.parse(date1));
            statusHistory.add(history);
            socStatus= "OPEN";
            socSchedulingStatus= "Not Started";
        }catch (Exception e){
            e.printStackTrace();;
        }
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

    public SocInfo getSocInfo() {
        return socInfo;
    }

    public void setSocInfo(SocInfo socInfo) {
        this.socInfo = socInfo;
    }

    public boolean isEnableLockButton(){
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                return true;
            }
        }
        return false;
    }

    public boolean isShowFinalEditButton(){
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED)){
                return true;
            }
        }
        return false;
    }

    public boolean isShowPublishSetButton(){
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)){
                return true;
            }
        }
        return false;
    }

    public boolean isShowCloseSetButton(){
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)){
                return true;
            }
        }
        return false;
    }

    /**
     * This will decide whether to display the section below the termcode section. If we have a valid term and SOC exists, the detailed section
     * will be displayed
     *
     * @return
     */
    public boolean isRenderDetailsSection(){
        return getTermInfo() != null;
    }

    public void clear(){
        setTermInfo(null);
        setSocInfo(null);
//        statusHistory.clear();
    }

}
