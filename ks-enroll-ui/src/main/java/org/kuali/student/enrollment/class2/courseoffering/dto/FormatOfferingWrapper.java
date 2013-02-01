/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

/**
 * Wrapper class around {@link FormatOfferingInfo}. This wrapper can represent a format offering
 * for a regular course as well as the joint courses. This is used in displaying the format
 * offerings at the create co screen.
 *
 * @author Kuali Student Team
 */
public class FormatOfferingWrapper {

    private FormatOfferingInfo formatOfferingInfo;
    private JointCourseWrapper jointCreateWrapper;
    private String courseCode;
    private FormatInfo formatInfo;

    private boolean isJointOffering;
    private boolean selectedToCopy;

    private String activitesUI;
    private String gradeRosterUI;
    private String finalExamUI;

    public FormatOfferingWrapper(){
        formatOfferingInfo = new FormatOfferingInfo();
        isJointOffering = false;
    }

    /**
     *
     * @param formatInfo format info associated with this wrapper
     * @param courseCode course code of the associated format
     * @param jointCreateWrapper joint course wrapper
     */
    public FormatOfferingWrapper(FormatInfo formatInfo, String courseCode, JointCourseWrapper jointCreateWrapper){
        this();
        this.formatInfo = formatInfo;
        this.courseCode = courseCode;
        this.formatOfferingInfo.setFormatId(formatInfo.getId());
        this.jointCreateWrapper = jointCreateWrapper;
        if (jointCreateWrapper != null){
            this.isJointOffering = true;
        }
    }

    /**
     * Returns the <code>FormatOfferingInfo</code> dto for this wrapper
     *
     * @return formatOfferingInfo
     */
    public FormatOfferingInfo getFormatOfferingInfo() {
        return formatOfferingInfo;
    }

    /**
     * Format Offering dto associated with this wrapper
     *
     * @param formatOfferingInfo format offering dto
     */
    public void setFormatOfferingInfo(FormatOfferingInfo formatOfferingInfo) {
        this.formatOfferingInfo = formatOfferingInfo;
    }

    /**
     * Returns the course wrapper for a joint course
     *
     * @return jointCreateWrapper joint course wrapper
     */
    public JointCourseWrapper getJointCreateWrapper() {
        return jointCreateWrapper;
    }

    public void setJointCreateWrapper(JointCourseWrapper jointCreateWrapper) {
        this.jointCreateWrapper = jointCreateWrapper;
    }

    public String getFormatId() {
        return formatOfferingInfo.getFormatId();
    }

    /**
     * Format id associated with a format offering
     *
     * @param formatId
     */
    public void setFormatId(String formatId) {
        this.formatOfferingInfo.setFormatId(formatId);
    }

    public String getGradeRosterLevelTypeKey() {
        return this.formatOfferingInfo.getGradeRosterLevelTypeKey();
    }

    /**
     * Grade roster level for a format offering
     *
     * @param gradeRosterLevelTypeKey
     */
    public void setGradeRosterLevelTypeKey(String gradeRosterLevelTypeKey) {
        this.formatOfferingInfo.setGradeRosterLevelTypeKey(gradeRosterLevelTypeKey);
    }

    public String getFinalExamLevelTypeKey() {
        return this.formatOfferingInfo.getFinalExamLevelTypeKey();
    }

    /**
     * Final exam level type for a format offering
     *
     * @param finalExamLevelTypeKey
     */
    public void setFinalExamLevelTypeKey(String finalExamLevelTypeKey) {
        this.formatOfferingInfo.setFinalExamLevelTypeKey(finalExamLevelTypeKey);
    }

    /**
     * Returns true if this is for a format offering associated with a joint course
     *
     * @return
     */
    public boolean isJointOffering() {
        return isJointOffering;
    }

    /**
     * If this wrapper is for a joint course, this indicates that
     * @param jointOffering
     */
    public void setJointOffering(boolean jointOffering) {
        isJointOffering = jointOffering;
    }

    /**
     * Used at the ui
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Course code for a format offering
     *
     * @param courseCode
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Reference at the xml.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getActivitesUI() {
        return activitesUI;
    }

    /**
     * This displays all the activities type for a format.
     * Ex: Lecture / Lab
     *
     * @param activitesUI
     */
    public void setActivitesUI(String activitesUI) {
        this.activitesUI = activitesUI;
    }

    /**
     *
     * @see #setSelectedToCopy(boolean)
     * @return
     */
    public boolean isSelectedToCopy() {
        return selectedToCopy;
    }

    /**
     * If user selects a format offering to copy, this flag will be set.
     * @param selectedToCopy whether it's selected or not to copy
     */
    @SuppressWarnings("unused")
    public void setSelectedToCopy(boolean selectedToCopy) {
        this.selectedToCopy = selectedToCopy;
    }

    /**
     * @see #setFormatInfo(org.kuali.student.r2.lum.course.dto.FormatInfo)
     * @return
     */
    public FormatInfo getFormatInfo() {
        return formatInfo;
    }

    /**
     * Format dto associated with a format offering.
     *
     * @param formatInfo
     */
    public void setFormatInfo(FormatInfo formatInfo) {
        this.formatInfo = formatInfo;
    }

    /**
     *
     * @see #setGradeRosterUI(String)
     * @return
     */
    @SuppressWarnings("unused")
    public String getGradeRosterUI() {
        return gradeRosterUI;
    }

    /**
     * Grade Roster text to display at the ui. This is a activity type name.
     *
     * This is set at {@link org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingCreateMaintainableImpl::loadCourseJointInfos()}
     *
     * @param gradeRosterUI
     */
    public void setGradeRosterUI(String gradeRosterUI) {
        this.gradeRosterUI = gradeRosterUI;
    }

    /**
     * @see #setFinalExamUI(String)
     * @return
     */
    @SuppressWarnings("unused")
    public String getFinalExamUI() {
        return finalExamUI;
    }

    /**
     * Final Exam driver text to display at the ui. This is set at
     * {@link org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingCreateMaintainableImpl::loadCourseJointInfos()}
     *
     * @param finalExamUI
     */
    public void setFinalExamUI(String finalExamUI) {
        this.finalExamUI = finalExamUI;
    }

}
