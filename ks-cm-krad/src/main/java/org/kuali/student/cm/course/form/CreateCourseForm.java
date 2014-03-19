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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.CourseViewSections;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

/**
 * Form for create course.
 */
public class CreateCourseForm extends MaintenanceDocumentForm {

    private String createCourseInitialAction;

    private boolean useReviewProcess;

    private boolean curriculumSpecialistUser;

    private CourseViewSections selectedSection;

    private String proposalName;

    public CreateCourseForm() {
        super();
        // assume user is not a Curriculum Specialist (CS) user
        curriculumSpecialistUser = false;
        // default to true as only CS users are able to disable curriculum review
        useReviewProcess = true;
        selectedSection = CourseViewSections.COURSE_INFO;
    }

    public String getCreateCourseInitialAction() {
        return createCourseInitialAction;
    }

    public void setCreateCourseInitialAction(String createCourseInitialAction) {
        this.createCourseInitialAction = createCourseInitialAction;
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

    public String getProposalName() {
        return proposalName;
    }

    public void setProposalName(String proposalName) {
        this.proposalName = proposalName;
    }

    public CourseViewSections getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(CourseViewSections selectedSection) {
        this.selectedSection = selectedSection;
    }

    public String getHeaderText() {
        String headerSuffixText;

        if (!isUseReviewProcess()) {
            headerSuffixText = " (Admin Proposal)";
        } else {
            headerSuffixText = " (Proposal)";
        }
        ProposalInfo proposalInfo = ((CourseInfoWrapper)
                getDocument().getNewMaintainableObject().getDataObject()).getProposalInfo();

        if (proposalInfo != null && StringUtils.isNotBlank(proposalInfo.getName())){
            return proposalInfo.getName() + headerSuffixText;
        } else {
            return "New Course" + headerSuffixText;
        }
    }
}
