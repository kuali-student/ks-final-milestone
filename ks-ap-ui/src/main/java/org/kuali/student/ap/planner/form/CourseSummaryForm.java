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

package org.kuali.student.ap.planner.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.planner.dataobject.CourseSummaryPopoverDetailsWrapper;

/**
 * Data Object class for displaying the add to plan dialog for courses
 * Used for beans:
 * PlannerDialogUI.xml#KSAP-CourseSummaryDialog-FormView
 */
public class CourseSummaryForm extends UifFormBase{

    // Hidden Values
    private String courseId;

    // Display Values
    private CourseSummaryPopoverDetailsWrapper courseSummaryDetails;
    private String courseCode;
    private String courseTitle;
    private String creditsDisplay;
    private String plannedMessage;
    private String bookmarkMessage;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public CourseSummaryPopoverDetailsWrapper getCourseSummaryDetails() {
        return courseSummaryDetails;
    }

    public void setCourseSummaryDetails(CourseSummaryPopoverDetailsWrapper courseSummaryDetails) {
        this.courseSummaryDetails = courseSummaryDetails;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCreditsDisplay() {
        return creditsDisplay;
    }

    public void setCreditsDisplay(String creditsDisplay) {
        this.creditsDisplay = creditsDisplay;
    }

    public String getPlannedMessage() {
        return plannedMessage;
    }

    public void setPlannedMessage(String plannedMessage) {
        this.plannedMessage = plannedMessage;
    }

    public String getBookmarkMessage() {
        return bookmarkMessage;
    }

    public void setBookmarkMessage(String bookmarkMessage) {
        this.bookmarkMessage = bookmarkMessage;
    }
}
