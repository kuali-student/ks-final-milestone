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

    private String lastTerm;

    private String publicationYear;

    private RichText otherComment;

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
        return otherComment;
    }

    public void setOtherComment(RichText otherComment) {
        this.otherComment = otherComment;
    }

    public RetireCourseWrapper(boolean curriculumSpecialistUser, CurriculumManagementConstants.UserInterfaceSections selectedSection) {
        super(curriculumSpecialistUser, selectedSection);
    }
}
