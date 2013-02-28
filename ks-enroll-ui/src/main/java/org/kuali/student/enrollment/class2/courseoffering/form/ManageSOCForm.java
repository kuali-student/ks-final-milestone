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
 */

package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for manage soc view.
 *
 * @author Kuali Student Team
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

    public ManageSOCForm()  {
        statusHistory = new ArrayList<ManageSOCStatusHistory> ();
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

    /**
     * This enables the lock button when the doc is in OPEN STATE.
     *
     * @return
     */
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

    /**
     * The lock button will be shown only when the SOC state is Draft or Open. Enabling the button is handled at
     * <code>isEnableLockButton()</code> which will return true only when the SOC is in OPEN state.
     *
     * @return
     */
    public boolean isShowLockButton(){

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    /**
     * When the SOC is in LOCKED state, we can render the final edits button but not enabled.
     * Enabling the final edit button is only when the schedule is complete
     *
     * @return boolean
     */
    public boolean isShowFinalEditButton(){

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    /**
     * Final Edits button will be enabled when the Scheduling state is completed and the soc state is locked.
     *
     * @return boolean
     */
    public boolean isEnableFinalEditButton(){

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED) &&
                StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                return true;
            }
        }

        return false;
    }

    /**
     * If the SOC is in FINAL EDITS, then the SOC is allowed to publish. This method handles the rendering logic for
     * the publish button
     *
     * @return boolean
     */
    public boolean isShowPublishSetButton(){
        boolean retVal = false;
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)){
                retVal = true;
            }
        }
        if(StringUtils.equals(getSocPublishingStatus(), ManageSocConstants.SOC_IN_PROGRESS_PUBLISHING_STATUS_UI) ||
           StringUtils.equals(getSocPublishingStatus(), ManageSocConstants.SOC_COMPLETED_PUBLISHING_STATUS_UI))   {
            retVal  = false;
        }
        return retVal;
    }

    /**
     * To Close a SOC, SOC must be either PUBLISHED OR PUBLISHING State.
     *
     * @return boolean
     */
    public boolean isShowCloseSetButton(){
        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)){
                return true;
            }
            if(StringUtils.equals(getSocPublishingStatus(), ManageSocConstants.SOC_IN_PROGRESS_PUBLISHING_STATUS_UI) ||
                    StringUtils.equals(getSocPublishingStatus(), ManageSocConstants.SOC_COMPLETED_PUBLISHING_STATUS_UI))   {
                return true;
            }

        }
        return false;
    }

    /**
     * This will decide whether to display the section below the termcode section. If we have a valid term and SOC exists, the detailed section
     * will be displayed
     *
     * @return boolean
     */
    public boolean isRenderDetailsSection(){
        return getTermInfo() != null;
    }

    /**
     * This method decides whether to display the mass scheduling button. The SOC should be in LOCKED state to render this
     * button
     *
     * @return boolean
     */
    public boolean isEnableMSEButton(){

        boolean retVal = false;

        if (socInfo != null){
            if (StringUtils.equals(socInfo.getStateKey(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)){
                if(socInfo.getSchedulingStateKey() == null || socInfo.getSchedulingStateKey().isEmpty() || StringUtils.equals(socInfo.getSchedulingStateKey(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED) ) {
                    retVal =  true;
                }
            }
        }

        if(StringUtils.equals(getSocSchedulingStatus(), ManageSocConstants.SOC_IN_PROGRESS_PUBLISHING_STATUS_UI) ||
           StringUtils.equals(getSocSchedulingStatus(), ManageSocConstants.SOC_COMPLETED_PUBLISHING_STATUS_UI))   {
            retVal  = false;
        }

        return retVal;
    }

    /**
     * This method will clear all the form fields so that different term search will not show previous values.
     * This is called from viewHelper.buildModel() which will populate all the fields.
     */
    public void clear(){
        setTermInfo(null);
        setSocInfo(null);
        statusHistory.clear();
        setScheduleDuration(StringUtils.EMPTY);
        setPublishDuration(StringUtils.EMPTY);
        setSocStatus(StringUtils.EMPTY);
        setSocPublishingStatus(StringUtils.EMPTY);
        setSocSchedulingStatus(StringUtils.EMPTY);
    }


}
