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
package org.kuali.student.ap.coursesearch.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditor;

import java.util.List;

/**
 * This data object stores the data used in the display of the KSAP-CourseSectionDetailsDialog-FormView
 */
public class CourseSectionDetailsDialogForm extends UifFormBase {

    private String credits;
    private String courseNote;
    private String regGroupId;

    private String courseOfferingCode;
    private String courseOfferingTitle;
    private List<String> prerequisites;
    private List<String> corequisites;
    private List<String> antirequisites;

    public CourseSectionDetailsDialogForm() {
        super();
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
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

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<String> getCorequisites() {
        return corequisites;
    }

    public void setCorequisites(List<String> corequisites) {
        this.corequisites = corequisites;
    }

    public List<String> getAntirequisites() {
        return antirequisites;
    }

    public void setAntirequisites(List<String> antirequisites) {
        this.antirequisites = antirequisites;
    }

    public String getPrerequisitesForUI() {
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(this.getPrerequisites());
        return editor.getAsText();
    }

    public String getAntirequisitesForUI() {
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(this.getAntirequisites());
        return editor.getAsText();
    }

    public String getCorequisitesForUI() {
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(this.getCorequisites());
        return editor.getAsText();
    }
}
