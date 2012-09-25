package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String socPublishingStatus;
    private String termCode;
    private List<ManageSOCStatusHistory> statusHistory;

    private TermInfo termInfo;
    private SocInfo socInfo;

    private Map<String,String> socStateKeys2Names;

    public ManageSOCForm()  {
        statusHistory = new ArrayList<ManageSOCStatusHistory> ();
        socStateKeys2Names = new HashMap<String, String>();
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

    public String getSocPublishingStatus() {
        return socPublishingStatus;
    }

    public void setSocPublishingStatus(String socPublishingStatus) {
        this.socPublishingStatus = socPublishingStatus;
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

    public boolean isEnablePublishButton() {

        if (socInfo != null){
            if (!StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    public boolean isShowLockButton(){

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    public boolean isShowFinalEditButton(){

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    public boolean isEnableFinalEditButton(){

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED) &&
                    StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    public boolean isShowPublishSetButton(){
        boolean retVal = false;
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)){
                retVal = true;
            }
        }
        if(StringUtils.equals(getSocPublishingStatus(), "In Progress") ||
                StringUtils.equals(getSocPublishingStatus(), "Completed"))   {
            retVal  = false;
        }
        return retVal;
    }

    public boolean isShowCloseSetButton(){
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)){
                return true;
            }
            if(StringUtils.equals(getSocPublishingStatus(), "In Progress") ||
                    StringUtils.equals(getSocPublishingStatus(), "Completed"))   {
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
        statusHistory.clear();
    }

    public boolean isEnableMSEButton(){
        boolean retVal = false;
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                if(socInfo.getSchedulingStateKey() == null || socInfo.getSchedulingStateKey().isEmpty() || StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED) ) {
                    retVal =  true;
                }
            }
        }
        if(StringUtils.equals(getSocSchedulingStatus(), "In Progress") ||
                StringUtils.equals(getSocSchedulingStatus(), "Completed"))   {
            retVal  = false;
        }
        return retVal;
    }

    public Map<String, String> getSocStateKeys2Names() {
        return socStateKeys2Names;
    }

    public void setSocStateKeys2Names(Map<String, String> socStateKeys2Names) {
        this.socStateKeys2Names = socStateKeys2Names;
    }

}
