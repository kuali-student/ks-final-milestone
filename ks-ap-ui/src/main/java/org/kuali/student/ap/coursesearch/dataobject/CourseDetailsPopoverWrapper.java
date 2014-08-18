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


import org.kuali.student.ap.coursesearch.util.ScheduledTermsPropertyEditor;

import java.util.List;

/**
 * This data object stores the data used in the display of the CourseDetailsPopover-InquiryView
 */
public class CourseDetailsPopoverWrapper extends WrapperWithRequisites {

    private String courseId;
    private String courseCode;
    private String courseTitle;
    private String courseCredits;
    private String lastOffered;
    private String planStatusMessage;
    private String bookmarkStatusMessage;

    private List<String> scheduledTerms;
    private List<String> projectedTerms;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(String courseCredits) {
        this.courseCredits = courseCredits;
    }

    public String getLastOffered() {
        return lastOffered;
    }

    public void setLastOffered(String lastOffered) {
        this.lastOffered = lastOffered;
    }

    public String getPlanStatusMessage() {
        return planStatusMessage;
    }

    public void setPlanStatusMessage(String planStatusMessage) {
        this.planStatusMessage = planStatusMessage;
    }

    public String getBookmarkStatusMessage() {
        return bookmarkStatusMessage;
    }

    public void setBookmarkStatusMessage(String bookmarkStatusMessage) {
        this.bookmarkStatusMessage = bookmarkStatusMessage;
    }

    public List<String> getScheduledTerms() {
        return scheduledTerms;
    }

    public void setScheduledTerms(List<String> scheduledTerms) {
        this.scheduledTerms = scheduledTerms;
    }

    public List<String> getProjectedTerms() {
        return projectedTerms;
    }

    public void setProjectedTerms(List<String> projectedTerms) {
        this.projectedTerms = projectedTerms;
    }

    public String getScheduledTermsForUI() {
        ScheduledTermsPropertyEditor editor = new ScheduledTermsPropertyEditor();
        editor.setValue(this.getScheduledTerms());
        return editor.getAsText();
    }

}
