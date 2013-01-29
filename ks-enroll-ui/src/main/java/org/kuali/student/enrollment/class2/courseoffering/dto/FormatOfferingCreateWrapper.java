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
 * Created by venkat on 1/28/13
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingCreateWrapper {

    private FormatOfferingInfo formatOfferingInfo;
    private CourseJointCreateWrapper jointCreateWrapper;
    private String courseCode;
    private String activitesUI;
    private boolean isJointOffering;

    public FormatOfferingCreateWrapper(){
        formatOfferingInfo = new FormatOfferingInfo();
        isJointOffering = false;
    }


    public FormatOfferingInfo getFormatOfferingInfo() {
        return formatOfferingInfo;
    }

    public void setFormatOfferingInfo(FormatOfferingInfo formatOfferingInfo) {
        this.formatOfferingInfo = formatOfferingInfo;
    }

    public CourseJointCreateWrapper getJointCreateWrapper() {
        return jointCreateWrapper;
    }

    public void setJointCreateWrapper(CourseJointCreateWrapper jointCreateWrapper) {
        this.jointCreateWrapper = jointCreateWrapper;
    }

    public String getFormatId() {
        return formatOfferingInfo.getFormatId();
    }

    public void setFormatId(String formatId) {
        this.formatOfferingInfo.setFormatId(formatId);
    }

    public String getGradeRosterLevelTypeKey() {
        return this.formatOfferingInfo.getGradeRosterLevelTypeKey();
    }

    public void setGradeRosterLevelTypeKey(String gradeRosterLevelTypeKey) {
        this.formatOfferingInfo.setGradeRosterLevelTypeKey(gradeRosterLevelTypeKey);
    }

    public String getFinalExamLevelTypeKey() {
        return this.formatOfferingInfo.getFinalExamLevelTypeKey();
    }

    public void setFinalExamLevelTypeKey(String finalExamLevelTypeKey) {
        this.formatOfferingInfo.setFinalExamLevelTypeKey(finalExamLevelTypeKey);
    }

    public boolean isJointOffering() {
        return isJointOffering;
    }

    public void setJointOffering(boolean jointOffering) {
        isJointOffering = jointOffering;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getActivitesUI() {
        return activitesUI;
    }

    public void setActivitesUI(String activitesUI) {
        this.activitesUI = activitesUI;
    }

}
