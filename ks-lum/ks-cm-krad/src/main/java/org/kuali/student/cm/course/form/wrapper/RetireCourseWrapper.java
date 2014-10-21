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
 * Created by prasannag on 1/9/14
 */
package org.kuali.student.cm.course.form.wrapper;

import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.proposal.form.wrapper.ReviewProposalDisplay;
import org.kuali.student.cm.proposal.util.ProposalUtil;

import java.io.Serializable;

/**
 * Wrapper for Retire Course
 *
 * @author Kuali Student Team
 */
public class RetireCourseWrapper extends CommonCourseDataWrapper implements Serializable {

    private RetireCourseReviewProposalDisplay reviewProposalDisplay =  new RetireCourseReviewProposalDisplay();

    private String retireEndTerm;
    private String retireStartTerm;

    public RetireCourseWrapper() {
        super(ProposalUtil.isUserCurriculumSpecialist(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_RETIRE_ADMIN),
                CurriculumManagementConstants.CourseRetireSections.RETIRE_INFO);
    }

    public RetireCourseWrapper(boolean curriculumSpecialistUser, CurriculumManagementConstants.UserInterfaceSections selectedSection) {
        super(curriculumSpecialistUser, selectedSection);
    }

    public String getRetireEndTerm() {
        return retireEndTerm;
    }

    public void setRetireEndTerm(String retireEndTerm) {
        this.retireEndTerm = retireEndTerm;
    }

    @Override
    public ReviewProposalDisplay getReviewProposalDisplay() {
        return reviewProposalDisplay;
    }

    public void setReviewProposalDisplay(RetireCourseReviewProposalDisplay reviewProposalDisplay) {
        this.reviewProposalDisplay = reviewProposalDisplay;
    }

    public String getRetireStartTerm() {
        return retireStartTerm;
    }

    public void setRetireStartTerm(String retireStartTerm) {
        this.retireStartTerm = retireStartTerm;
    }

}
