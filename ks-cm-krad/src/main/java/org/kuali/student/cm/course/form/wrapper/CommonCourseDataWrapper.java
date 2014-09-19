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
 * Created by delyea on 9/16/14
 */
package org.kuali.student.cm.course.form.wrapper;

import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for common data elements across course wrapper objects
 */
public abstract class CommonCourseDataWrapper extends ProposalElementsWrapper {

    private CourseInfo courseInfo = new CourseInfo();

    /**
     * A boolean to identify when proposal data is required for the Review Page.
     *
     * This data is not needed in the case where the display is for ViewCourse only.
     */
    private boolean proposalDataUsed = true;

    private String lastTerm;
    private String publicationYear;
    private RichTextInfo retirementComment;
    private List<CourseCreateUnitsContentOwner> unitsContentOwner = new ArrayList<CourseCreateUnitsContentOwner>();

    private String userId = "";

    private String lastUpdated = "";

    public CommonCourseDataWrapper(boolean curriculumSpecialistUser, CurriculumManagementConstants.UserInterfaceSections selectedSection) {
        super(curriculumSpecialistUser, selectedSection);
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    /**
     * @see #proposalDataUsed
     */
    public boolean isProposalDataUsed() {
        return proposalDataUsed;
    }

    /**
     * @see #proposalDataUsed
     */
    public void setProposalDataUsed(boolean proposalDataUsed) {
        this.proposalDataUsed = proposalDataUsed;
    }

    /**
     * Gets the value of lastUpdated
     *
     * @return the value of lastUpdated
     */
    public String getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Sets the value of lastUpdated
     *
     * @param argLastUpdated Value to assign to this.lastUpdated
     */
    public void setLastUpdated(final String argLastUpdated) {
        this.lastUpdated = argLastUpdated;
    }

    /**
     * Gets the value of userId
     *
     * @return the value of userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Sets the value of userId
     *
     * @param argUserId Value to assign to this.userId
     */
    public void setUserId(final String argUserId) {
        this.userId = argUserId;
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

    public RichText getRetirementComment() {
        if (retirementComment == null) {
            retirementComment = new RichTextInfo();
        }
        return retirementComment;
    }

    public void setRetirementComment(RichTextInfo retirementComment) {
        this.retirementComment = retirementComment;
    }

    public List<CourseCreateUnitsContentOwner> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwner(List<CourseCreateUnitsContentOwner> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

}
