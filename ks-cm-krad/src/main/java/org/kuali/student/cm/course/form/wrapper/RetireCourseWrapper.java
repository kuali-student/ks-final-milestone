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
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.util.ProposalUtil;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;

/**
 * Wrapper for Retire Course
 *
 * @author Kuali Student Team
 */
public class RetireCourseWrapper extends ProposalElementsWrapper implements Serializable {

    private CourseInfo courseInfo = new CourseInfo();
    private ReviewProposalDisplay reviewProposalDisplay = new ReviewProposalDisplay();

    private String lastTerm;
    private String publicationYear;
    private RichTextInfo otherComment;
    private String retireEndTerm;

    private String userId = "";

    private String lastUpdated;

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getLastTerm() {
        return lastTerm;
    }

    public void setLastTerm(String lastTerm) {
        this.lastTerm = lastTerm;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public RichText getOtherComment() {
        if (otherComment == null) {
            otherComment = new RichTextInfo();
        }
        return otherComment;
    }

    public void setOtherComment(RichTextInfo otherComment) {
        this.otherComment = otherComment;
    }

    public RetireCourseWrapper() {
        super(ProposalUtil.isUserCurriculumSpecialist(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_RETIRE),
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

    public void setReviewProposalDisplay(ReviewProposalDisplay reviewProposalDisplay) {
        this.reviewProposalDisplay = reviewProposalDisplay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
