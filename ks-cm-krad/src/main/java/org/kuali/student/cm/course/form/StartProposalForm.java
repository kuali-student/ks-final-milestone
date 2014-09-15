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
 * Created by chongzhu on 2/6/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.web.bind.RequestProtected;
import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * This form is being used at the initial create course screen view. This captures
 * data related to course and based on the user selection displays the create course
 * maintenace document.
 *
 */
public class StartProposalForm extends UifFormBase {

    private String startProposalCourseAction;

    private String courseCode;

    private String courseId;

    private boolean useReviewProcess;

    private String proposalId;

    private String proposalTitle;

    private String versionIndId;

    private String versionComment;

    private boolean isModifiableCourse;

    private boolean isCourseWithVersion;

    // disallow the curriculumSpecialistUser property to be set by the request
    @RequestProtected
    private boolean curriculumSpecialistUser;

    public StartProposalForm() {
        super();
        // assume user is not a Curriculum Specialist (CS) user
        curriculumSpecialistUser = false;
        // default to true as only CS users are able to disable curriculum review
        useReviewProcess = true;
    }

    public String getStartProposalCourseAction() {
        return startProposalCourseAction;
    }

    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

    public String getVersionIndId() {
        return versionIndId;
    }

    public void setVersionIndId(String versionIndId) {
        this.versionIndId = versionIndId;
    }

    public void setStartProposalCourseAction(String startProposalCourseAction) {
        this.startProposalCourseAction = startProposalCourseAction;
    }

    public boolean isUseReviewProcess() {
        return useReviewProcess;
    }

    public void setUseReviewProcess(boolean useReviewProcess) {
        this.useReviewProcess = useReviewProcess;
    }

    public boolean isCurriculumSpecialistUser() {
        return curriculumSpecialistUser;
    }

    public void setCurriculumSpecialistUser(boolean curriculumSpecialistUser) {
        this.curriculumSpecialistUser = curriculumSpecialistUser;
    }

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

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public void setProposalTitle(String proposalTitle) {
        this.proposalTitle = proposalTitle;
    }

    public boolean isModifiableCourse() {
        return this.isModifiableCourse;
    }

    public void setModifiableCourse(boolean isModifiableCourse) {
        this.isModifiableCourse = isModifiableCourse;
    }

    public boolean isCourseWithVersion() {
        return this.isCourseWithVersion;
    }

    public void setCourseWithVersion(boolean isCourseWithVersion) {
        this.isCourseWithVersion = isCourseWithVersion;
    }

}
