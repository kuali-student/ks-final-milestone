/*
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
 */
package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;

import java.util.List;

public class CourseOfferingDetailsWrapper {

    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingTitle;
    private String courseOfferingDescription;
    private boolean multipleFormatOfferings;
    private List<String> courseRequisites;
    private boolean honors;
    private String creditsDisplay;
    private String selectedFormatOfferingId;
    private String termId;
    private boolean variableCredit;

    private List<FormatOfferingInfoWrapper> formatOfferingInfoWrappers;


    private List<PlannedRegistrationGroupDetailsWrapper> plannedActivityDetailsWrappers;

    public CourseOfferingDetailsWrapper (CourseOffering courseOfferingInfo) {
        courseOfferingId = courseOfferingInfo.getId();
        courseOfferingCode = courseOfferingInfo.getCourseOfferingCode();
        courseOfferingTitle = courseOfferingInfo.getCourseOfferingTitle();
        courseOfferingDescription = courseOfferingInfo.getDescr().getPlain();

        honors = Boolean.TRUE.equals(courseOfferingInfo.getIsHonorsOffering());
        courseRequisites = CourseDetailsUtil.getCourseOfferingRequisites(courseOfferingInfo);
        creditsDisplay = courseOfferingInfo.getCreditCnt();
        termId = courseOfferingInfo.getTermId();
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }

    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public String getCourseOfferingDescription() {
        return courseOfferingDescription;
    }

    public void setCourseOfferingDescription(String courseOfferingDescription) {
        this.courseOfferingDescription = courseOfferingDescription;
    }

    public boolean isMultipleFormatOfferings() {
        return multipleFormatOfferings;
    }

    public void setMultipleFormatOfferings(boolean multipleFormatOfferings) {
        this.multipleFormatOfferings = multipleFormatOfferings;
    }

    public List<PlannedRegistrationGroupDetailsWrapper> getPlannedActivityDetailsWrappers() {
        return plannedActivityDetailsWrappers;
    }

    public void setPlannedActivityDetailsWrappers(List<PlannedRegistrationGroupDetailsWrapper>
            plannedActivityDetailsWrappers) {
        this.plannedActivityDetailsWrappers = plannedActivityDetailsWrappers;
    }

    public int getPlannedGroupsCount() {
        int count=0;
        String lastCountedRegGroupCode="";
        for (PlannedRegistrationGroupDetailsWrapper plannedActivities : plannedActivityDetailsWrappers ) {
            if (!lastCountedRegGroupCode.equals(plannedActivities.getRegGroupCode())) {
                count++;
                lastCountedRegGroupCode=plannedActivities.getRegGroupCode();
            }
        }
        return count;
    }

    public boolean isHonors() {
        return honors;
    }

    public void setHonors(boolean honors) {
        this.honors = honors;
    }

    public List<String> getCourseRequisites() {
        return courseRequisites;
    }

    public void setCourseRequisites(List<String> courseRequisites) {
        this.courseRequisites = courseRequisites;
    }

    public String getCreditsDisplay() {
        return creditsDisplay;
    }

    public void setCreditsDisplay(String creditsDisplay) {
        this.creditsDisplay = creditsDisplay;
    }

    public List<FormatOfferingInfoWrapper> getFormatOfferingInfoWrappers() {
        return formatOfferingInfoWrappers;
    }

    public void setFormatOfferingInfoWrappers(List<FormatOfferingInfoWrapper> formatOfferingInfoWrappers) {
        this.formatOfferingInfoWrappers = formatOfferingInfoWrappers;
    }

    public String getSelectedFormatOfferingId() {
        return selectedFormatOfferingId;
    }

    public void setSelectedFormatOfferingId(String selectedFormatOfferingId) {
        this.selectedFormatOfferingId = selectedFormatOfferingId;
    }

    public String getTermId() {
        return termId;
    }

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public boolean isVariableCredit() {
        return variableCredit;
    }

    public void setVariableCredit(boolean variableCredit) {
        this.variableCredit = variableCredit;
    }
}
