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
 * Created by Karthikeyanb on 9/3/14
 */
package org.kuali.student.cm.course.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;

import java.util.LinkedList;
import java.util.List;

/**
 * Review Proposal Display Wrapper for Retire Course
 *
 * @author Kuali Student Team
 */
public class RetireCourseReviewProposalDisplay extends org.kuali.student.cm.proposal.form.wrapper.ReviewProposalDisplay {

    private CourseReferenceDataSectionWrapper referenceDataSection;
    private RetireCourseSectionWrapper retireCourseSection;

    public RetireCourseSectionWrapper getRetireCourseSection() {
        if (retireCourseSection == null) {
            retireCourseSection = new RetireCourseSectionWrapper();
        }
        return retireCourseSection;
    }

    public void setRetireCourseSection(RetireCourseSectionWrapper retireCourseSection) {
        this.retireCourseSection = retireCourseSection;
    }

    public CourseReferenceDataSectionWrapper getReferenceDataSection() {
        if (referenceDataSection == null) {
            referenceDataSection = new CourseReferenceDataSectionWrapper();
        }
        return referenceDataSection;
    }

    public void setReferenceDataSection(CourseReferenceDataSectionWrapper referenceDataSection) {
        this.referenceDataSection = referenceDataSection;
    }

    public class RetireCourseSectionWrapper {
        private String endTerm;
        private String lastTerm;
        private String publicationYear;

        public void setEndTerm(final String endTerm) {
            this.endTerm = endTerm;
        }

        public String getEndTerm() {
            return endTerm;
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

    }

    public class CourseReferenceDataSectionWrapper {
        private List<String> crossListings;
        private List<String> jointlyOfferedCourses;
        private List<String> curriculumOversight;

        public List<String> getCrossListings() {
            if (this.crossListings == null) {
                crossListings = new LinkedList<String>();
            }
            return crossListings;
        }

        public void setCrossListings(List<String> crossListings) {
            this.crossListings = crossListings;
        }

        public List<String> getJointlyOfferedCourses() {
            if (this.jointlyOfferedCourses == null) {
                jointlyOfferedCourses = new LinkedList<String>();
            }
            return jointlyOfferedCourses;
        }

        public void setJointlyOfferedCourses(List<String> jointlyOfferedCourses) {
            this.jointlyOfferedCourses = jointlyOfferedCourses;
        }

        public List<String> getCurriculumOversight() {
            if (this.curriculumOversight == null) {
                curriculumOversight = new LinkedList<String>();
            }
            return curriculumOversight;
        }

        public void setCurriculumOversight(List<String> curriculumOversight) {
            this.curriculumOversight = curriculumOversight;
        }

        public String getCurriculumOversightAsString() {
            return StringUtils.join(getCurriculumOversight(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        public String getJointlyOfferedAndCrossListedCoursesAsString() {
            List<String> crossListedJointlyOfferedCourseList = new LinkedList<>();
            crossListedJointlyOfferedCourseList.addAll(jointlyOfferedCourses);
            crossListedJointlyOfferedCourseList.addAll(crossListings);
            return StringUtils.join(crossListedJointlyOfferedCourseList, CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
    }
}
