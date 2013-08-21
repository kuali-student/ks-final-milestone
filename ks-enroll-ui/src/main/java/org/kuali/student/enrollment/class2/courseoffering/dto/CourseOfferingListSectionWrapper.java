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
 * Created by vgadiyak on 5/25/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* TODO : This class needs refactoring; should inherit from CourseOfferingWrapper; see https://jira.kuali.org/browse/KSENROLL-5931
 * (note: not sure whether this class should actually be part of the refactoring or not)
 */

/**
 * This wrapper is used to display a list of {@link org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo} at
 * Manage CO View.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingListSectionWrapper implements Serializable{

    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingDesc;
    private String courseOfferingStateKey;
    private String courseOfferingStateDisplay;
    private String courseOfferingCreditOptionKey;
    private String courseOfferingGradingOptionKey;
    private String courseOfferingCreditOptionDisplay;
    private String courseOfferingGradingOptionDisplay;
    private String subjectArea;
    private String adminOrg;

    private List<String> alternateCOCodes;
    private String ownerCode;
    private List<String> ownerAliases;

    private boolean isCrossListed;
    private boolean isColocated;
    private boolean isJointDefined;

    private boolean isLegalToDelete = true;
    private boolean isChecked = false;

    //hidden columns for toolbar
    private boolean enableAddButton = false;
    private boolean enableApproveButton = false;
    private boolean enableSuspendButton = false;
    private boolean enableCancelButton = false;
    private boolean enableReinstateButton = false;
    private boolean enableDeleteButton = false;
    private boolean enableCopyCOActionLink = false;
    private boolean enableEditCOActionLink = false;

    private String crossListedCoList;

    private String colocatedCoCode = "";
    private String jointDefinedCoCode = "";

    List<ActivityOfferingDisplayWrapper> aoToBeDeletedList;
    private boolean coHasAoToDelete = true;

    public CourseOfferingListSectionWrapper () {
        this.alternateCOCodes = new ArrayList<String>();
        this.ownerAliases = new ArrayList<String>();
        aoToBeDeletedList =  new ArrayList<ActivityOfferingDisplayWrapper>();
    }

    public List<ActivityOfferingDisplayWrapper> getAoToBeDeletedList() {
        return aoToBeDeletedList;
    }

    public void setAoToBeDeletedList(List<ActivityOfferingDisplayWrapper> aoToBeDeletedList) {
        this.aoToBeDeletedList = aoToBeDeletedList;
    }

    public boolean isCoHasAoToDelete() {
        return coHasAoToDelete;
    }

    public void setCoHasAoToDelete(boolean coHasAoToDelete) {
        this.coHasAoToDelete = coHasAoToDelete;
    }

    public boolean isEnableAddButton() {
        return enableAddButton;
    }

    public void setEnableAddButton(boolean enableAddButton) {
        this.enableAddButton = enableAddButton;
    }

    public boolean isEnableApproveButton() {
        return enableApproveButton;
    }

    public void setEnableApproveButton(boolean enableApproveButton) {
        this.enableApproveButton = enableApproveButton;
    }

    public boolean isEnableSuspendButton() {
        return enableSuspendButton;
    }

    public void setEnableSuspendButton(boolean enableSuspendButton) {
        this.enableSuspendButton = enableSuspendButton;
    }

    public boolean isEnableCancelButton() {
        return enableCancelButton;
    }

    public void setEnableCancelButton(boolean enableCancelButton) {
        this.enableCancelButton = enableCancelButton;
    }

    public boolean isEnableReinstateButton() {
        return enableReinstateButton;
    }

    public void setEnableReinstateButton(boolean enableReinstateButton) {
        this.enableReinstateButton = enableReinstateButton;
    }

    public boolean isEnableDeleteButton() {
        return enableDeleteButton;
    }

    public void setEnableDeleteButton(boolean enableDeleteButton) {
        this.enableDeleteButton = enableDeleteButton;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }


    public boolean isLegalToDelete() {

        if (this.getCourseOfferingStateKey() != null &&
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_DRAFT_STATE_KEY) ||
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) ||
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY) ||
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY) ||
                StringUtils.equals(this.getCourseOfferingStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY) ) {
            return true;
        }

        return false;
    }

    public void setLegalToDelete(boolean legalToDelete) {
        isLegalToDelete = legalToDelete;
    }

    public boolean isEnableCopyCOActionLink() {
        return enableCopyCOActionLink;
    }

    public void setEnableCopyCOActionLink(boolean enableCopyCOActionLink) {
        this.enableCopyCOActionLink = enableCopyCOActionLink;
    }

    public boolean isEnableEditCOActionLink() {
        return enableEditCOActionLink;
    }

    public void setEnableEditCOActionLink(boolean enableEditCOActionLink) {
        this.enableEditCOActionLink = enableEditCOActionLink;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingCreditOptionKey() {
        return courseOfferingCreditOptionKey;
    }

    public void setCourseOfferingCreditOptionKey(String courseOfferingCreditOptionKey) {
        this.courseOfferingCreditOptionKey = courseOfferingCreditOptionKey;
    }

    public String getCourseOfferingDesc() {
        return courseOfferingDesc;
    }

    public void setCourseOfferingDesc(String courseOfferingDesc) {
        this.courseOfferingDesc = courseOfferingDesc;
    }

    public String getCourseOfferingGradingOptionKey() {
        return courseOfferingGradingOptionKey;
    }

    public void setCourseOfferingGradingOptionKey(String courseOfferingGradingOptionKey) {
        this.courseOfferingGradingOptionKey = courseOfferingGradingOptionKey;
    }

    public String getCourseOfferingStateKey() {
        return courseOfferingStateKey;
    }

    public void setCourseOfferingStateKey(String courseOfferingStateKey) {
        this.courseOfferingStateKey = courseOfferingStateKey;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getCrossListedCoList() {
        return crossListedCoList;
    }

    public void setCrossListedCoList(String crossListedCoList) {
        this.crossListedCoList = crossListedCoList;
    }

    public String getCourseOfferingStateDisplay() {
        return courseOfferingStateDisplay;
    }

    public void setCourseOfferingStateDisplay(String courseOfferingStateDisplay) {
        this.courseOfferingStateDisplay = courseOfferingStateDisplay;
    }

    public String getCourseOfferingCreditOptionDisplay() {
        return courseOfferingCreditOptionDisplay;
    }

    public void setCourseOfferingCreditOptionDisplay(String courseOfferingCreditOptionDisplay) {
        this.courseOfferingCreditOptionDisplay = courseOfferingCreditOptionDisplay;
    }

    public String getCourseOfferingGradingOptionDisplay() {
        return courseOfferingGradingOptionDisplay;
    }

    public void setCourseOfferingGradingOptionDisplay(String courseOfferingGradingOptionDisplay) {
        this.courseOfferingGradingOptionDisplay = courseOfferingGradingOptionDisplay;
    }

    /**
     * @see #setCrossListed(boolean)
     * @return
     */
    public boolean isCrossListed() {
        return isCrossListed;
    }

    /**
     * Sets true is this wrapper is for a cross listed course
     *
     * @param crossListed
     */
    public void setCrossListed(boolean crossListed) {
        isCrossListed = crossListed;
    }

    public boolean isColocated() {
        return isColocated;
    }

    public void setColocated(boolean colocated) {
        isColocated = colocated;
    }

    public String getJointDefinedCoCode() {
        return jointDefinedCoCode;
    }

    public void setJointDefinedCoCode(String jointDefinedCoCode) {
        this.jointDefinedCoCode = jointDefinedCoCode;
    }

    public boolean isJointDefined() {
        return isJointDefined;
    }

    public void setJointDefined(boolean jointDefined) {
        isJointDefined = jointDefined;
    }

    /**
     * @see #setAlternateCOCodes(List<String>)
     * @return
     */
    public List<String> getAlternateCOCodes() {
        return alternateCOCodes;
    }

    /**
     * List of alternate Course offering codes (either cross list or owner).
     * @param alternateCOCodes
     */
    public void setAlternateCOCodes(List<String> alternateCOCodes) {
        this.alternateCOCodes = alternateCOCodes;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public List<String> getOwnerAliases() {
        return ownerAliases;
    }

    public void setOwnerAliases(List<String> ownerAliases) {
        this.ownerAliases = ownerAliases;
    }

    public String getColocatedCoCode() {
        return colocatedCoCode;
    }

    public void setColocatedCoCode(String colocatedCoCode) {
        this.colocatedCoCode = colocatedCoCode;
    }

    /**
     * This method returns a list of crosslisted/official course code for a course. This will
     * be displayed as the tooltip (if crosslisted cos exists) at Manage CO screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getCrossListedCodesUI(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("This course is crosslisted with:<br>");
        for (String code : alternateCOCodes){
            buffer.append(code + "<br>");
        }

        return StringUtils.removeEnd(buffer.toString(),"<br>");
    }

    /**
     * This method returns a list of crosslisted/official course code for a course. This will
     * be displayed as the tooltip (if crosslisted cos exists) at Manage CO screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getJointDefinedCodesUI(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("This course is joint defined with:<br>");
        buffer.append(jointDefinedCoCode + "<br>");

        return StringUtils.removeEnd(buffer.toString(),"<br>");
    }

    /**
     * This method returns a coocated official course code for a course. This will
     * be displayed as the tooltip (if colocated co exists) at Manage CO screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getColocatedInfoUI(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("One or more activities in this course is colocated with activities in:<br>");
        buffer.append(colocatedCoCode + "<br>");

        return StringUtils.removeEnd(buffer.toString(),"<br>");
    }

    public String getAdminOrg(){
        return adminOrg;
    }

    public void setAdminOrg(String adminOrg){
        this.adminOrg=adminOrg;
    }

}

