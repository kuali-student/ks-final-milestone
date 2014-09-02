/**
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
 *
 * Created by prasannag on 2/9/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.web.bind.RequestProtected;
import org.kuali.rice.krad.web.form.UifFormBase;

/**
        * This form is being used at the initial retire course screen view. This captures
        * data related to course and based on the retire option selected displays the retire course
        * maintenace document.
        *
        */
public class StartRetireCourseForm extends UifFormBase {

    private String startRetireCourseAction;

    private String courseCode;

    private String courseId;

    private String proposalId;

    private String proposalTitle;

    // disallow the curriculumSpecialistUser property to be set by the request
    @RequestProtected
    private boolean curriculumSpecialistUser;

    public StartRetireCourseForm() {
        super();
        // assume user is not a Curriculum Specialist (CS) user
        curriculumSpecialistUser = false;
    }

    public String getStartRetireCourseAction() {
        return startRetireCourseAction;
    }

    public void setStartRetireCourseAction(String startRetireCourseAction) {
        this.startRetireCourseAction = startRetireCourseAction;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public void setProposalTitle(String proposalTitle) {
        this.proposalTitle = proposalTitle;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public boolean isCurriculumSpecialistUser() {
        return curriculumSpecialistUser;
    }

    public void setCurriculumSpecialistUser(boolean curriculumSpecialistUser) {
        this.curriculumSpecialistUser = curriculumSpecialistUser;
    }
}
