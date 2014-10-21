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

    /**
     * Returns the RetireCourseSectionWrapper instance
     * @return
     */
    public RetireCourseSectionWrapper getRetireCourseSection() {
        if (retireCourseSection == null) {
            retireCourseSection = new RetireCourseSectionWrapper();
        }
        return retireCourseSection;
    }

    /**
     * Sets the RetireCourseSectionWrapper instance
     * @param retireCourseSection
     */
    public void setRetireCourseSection(RetireCourseSectionWrapper retireCourseSection) {
        this.retireCourseSection = retireCourseSection;
    }

    /**
     * Returns the CourseReferenceDataSectionWrapper instance
     * @return
     */
    public CourseReferenceDataSectionWrapper getReferenceDataSection() {
        if (referenceDataSection == null) {
            referenceDataSection = new CourseReferenceDataSectionWrapper();
        }
        return referenceDataSection;
    }

    /**
     * Sets the CourseReferenceDataSectionWrapper instance
     * @param referenceDataSection
     */
    public void setReferenceDataSection(CourseReferenceDataSectionWrapper referenceDataSection) {
        this.referenceDataSection = referenceDataSection;
    }

    /**
     *  Holds the Retire Course Review section fields
     */
    public class RetireCourseSectionWrapper {
        private String endTerm;
        private String lastTerm;
        private String publicationYear;

        /**
         *  Set the course end term
         * @param endTerm
         */
        public void setEndTerm(final String endTerm) {
            this.endTerm = endTerm;
        }

        /**
         * get the course end term
         * @return String
         */
        public String getEndTerm() {
            return endTerm;
        }

        /**
         *  Get the course last term
         * @return String
         */
        public String getLastTerm() {
            return lastTerm;
        }

        /**
         * set the Course last term
         * @param lastTerm
         */
        public void setLastTerm(String lastTerm) {
            this.lastTerm = lastTerm;
        }

        /**
         *  Returns the Publication year
         * @return String
         */
        public String getPublicationYear() {
            return publicationYear;
        }

        /**
         * Set the Publication year
         * @param publicationYear
         */
        public void setPublicationYear(String publicationYear) {
            this.publicationYear = publicationYear;
        }

    }

    /**
     *  Holds the Retire Course Reference Data section fields
     */
    public class CourseReferenceDataSectionWrapper {
        private List<String> crossListings;
        private List<String> jointlyOfferedCourses;
        private List<String> curriculumOversight;

        /**
         *  Returns the cross listed courses
         * @return List<String>
         */
        public List<String> getCrossListings() {
            if (this.crossListings == null) {
                crossListings = new LinkedList<String>();
            }
            return crossListings;
        }

        /**
         *  Sets the cross listed courses
         * @param crossListings
         */
        public void setCrossListings(List<String> crossListings) {
            this.crossListings = crossListings;
        }

        /**
         *  Returns Jointly Offered Courses
         * @return List<String>
         */
        public List<String> getJointlyOfferedCourses() {
            if (this.jointlyOfferedCourses == null) {
                jointlyOfferedCourses = new LinkedList<String>();
            }
            return jointlyOfferedCourses;
        }

        /**
         * Set the Jointly Offered Courses
         * @param jointlyOfferedCourses
         */
        public void setJointlyOfferedCourses(List<String> jointlyOfferedCourses) {
            this.jointlyOfferedCourses = jointlyOfferedCourses;
        }

        /**
         *  Returns the list of Curriculum oversight values.
         * @return List<String>
         */
        public List<String> getCurriculumOversight() {
            if (this.curriculumOversight == null) {
                curriculumOversight = new LinkedList<String>();
            }
            return curriculumOversight;
        }

        /**
         *  Set the curriculum oversight list
         * @param curriculumOversight
         */
        public void setCurriculumOversight(List<String> curriculumOversight) {
            this.curriculumOversight = curriculumOversight;
        }

        /**
         *  Returns the curriculum oversight as semi-colon separated values
         * @return String
         */
        public String getCurriculumOversightAsString() {
            return StringUtils.join(getCurriculumOversight(), CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }

        /**
         * Combines the cross listed and jointly offered courses and returns it as semi-colon separated values.
         * @return String
         */
        public String getJointlyOfferedAndCrossListedCoursesAsString() {
            List<String> crossListedJointlyOfferedCourseList = new LinkedList<>();
            crossListedJointlyOfferedCourseList.addAll(getJointlyOfferedCourses());
            crossListedJointlyOfferedCourseList.addAll(getCrossListings());
            return StringUtils.join(crossListedJointlyOfferedCourseList, CurriculumManagementConstants.COLLECTION_ITEMS_DELIMITER);
        }
    }
}
