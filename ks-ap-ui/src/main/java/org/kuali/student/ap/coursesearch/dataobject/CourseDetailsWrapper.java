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

import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditor;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;
import org.kuali.student.ap.coursesearch.util.ScheduledTermsPropertyEditor;

import java.util.List;
import java.util.Map;

/**
 * This data object stores the data used in the display of the CourseDetails-InquiryView
 */
public class CourseDetailsWrapper {

    private String courseId;
    private String courseTitle;
    private String courseCode;
    private String courseCredits;
    private String subject;
    private String courseDescription;
    private String lastOffered;

    private List<String> courseRequisites;
    private Map<String, List<String>> courseRequisitesMap;
    private List<String> scheduledTerms;
    private List<String> projectedTerms;
    private List<String> courseGenEdRequirements;

    private String bookmarkMessage;
    private String plannedMessage;

    private boolean isBookmarked;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(String courseCredits) {
        this.courseCredits = courseCredits;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getLastOffered() {
        return lastOffered;
    }

    public void setLastOffered(String lastOffered) {
        this.lastOffered = lastOffered;
    }

    public List<String> getCourseRequisites() {
        return courseRequisites;
    }

    public void setCourseRequisites(List<String> courseRequisites) {
        this.courseRequisites = courseRequisites;
    }

    public Map<String, List<String>> getCourseRequisitesMap() {
        return courseRequisitesMap;
    }

    public void setCourseRequisitesMap(Map<String, List<String>> courseRequisitesMap) {
        this.courseRequisitesMap = courseRequisitesMap;
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

    public List<String> getCourseGenEdRequirements() {
        return courseGenEdRequirements;
    }

    public void setCourseGenEdRequirements(List<String> courseGenEdRequirements) {
        this.courseGenEdRequirements = courseGenEdRequirements;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public String getBookmarkMessage() {
        return bookmarkMessage;
    }

    public void setBookmarkMessage(String bookmarkMessage) {
        this.bookmarkMessage = bookmarkMessage;
    }

    public String getPlannedMessage() {
        return plannedMessage;
    }

    public void setPlannedMessage(String plannedMessage) {
        this.plannedMessage = plannedMessage;
    }

    public String getCourseIdXmlSafe(){
        if(getCourseId()==null)return "";
        return getCourseId().replace('.', '_');
    }

    public String getScheduledTermsForUI() {
        ScheduledTermsPropertyEditor editor = new ScheduledTermsPropertyEditor();
        editor.setValue(this.getScheduledTerms());
        return editor.getAsText();
    }

    public String getCourseGenEdRequirementsForUI() {
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(this.getCourseGenEdRequirements());
        return editor.getAsText();
    }

    public String getCourseRequisitesForUI() {
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(this.getCourseRequisites());
        return editor.getAsText();
    }

    private String getRequisitesForUI(String key) {
        String returnValue = null;
        if (courseRequisitesMap.containsKey(key)) {
            CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
            editor.setValue(courseRequisitesMap.get(key));
            returnValue =  key + ": " + editor.getAsText();
        }
        return returnValue;
    }

    public String getPreRequisitesForUI() {
        return getRequisitesForUI(CourseDetailsUtil.PREREQUISITE_KEY);
    }

    public String getCoRequisitesForUI() {
        return getRequisitesForUI(CourseDetailsUtil.COREQUISITE_KEY);
    }

    public String getAntiRequisitesForUI() {
        return getRequisitesForUI(CourseDetailsUtil.ANTIREQUISITE_KEY);
    }
}
