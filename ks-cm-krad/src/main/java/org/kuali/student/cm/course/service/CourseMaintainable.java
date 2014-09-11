/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.cm.course.service;

import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.student.cm.course.form.wrapper.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.LoCategoryInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.SubjectCodeWrapper;
import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.List;

/**
 * {@link CourseInfo} Maintainable interface
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public interface CourseMaintainable extends CommonCourseMaintainable {

    /**
     * Method called when queryMethodToCall is executed for Administering Organizations in order 
     * to suggest back to the user an Administering Organization
     *
     * @param organizationName  
     * @return {@link List} of wrapper instances which get added to the {@link org.kuali.student.cm.course.form.StartProposalForm}
     */
	List<OrganizationInfoWrapper> getOrganizationsForSuggest(final String organizationName);

	List<CluInstructorInfoWrapper> getInstructorsForSuggest(String instructorName);

    List<SubjectCodeWrapper> getSubjectCodesForSuggest(String subjectCode);
	
	List<CourseJointInfoWrapper> searchForJointOfferingCourses(String courseNumber);
	
	List<LoCategoryInfoWrapper> searchForLoCategories(String categoryName);

    List<AgendaEditor> getAgendasForRef(String discriminatorType, String refObjectId);

    public void setLOActions();

    public List<CluInformation> getCoursesInRange(MembershipQueryInfo membershipQuery);

    /**
     * Loads course data into the data object.
     * @param courseId The id of the course to load.
     * @param courseWrapper The data object to populate.
     */
    public void populateCourseAndReviewData(String courseId, CourseInfoWrapper courseWrapper) throws Exception;

    /**
     * Loads course data into the data object with the added option of populating version data.
     * @param courseId The id of the course to load.
     * @param courseWrapper The data object to populate.
     * @param loadVersionData If true, version data (currentVersion, versionDisplay) for the course will be loaded. Otherwise, it will not be loaded.
     */
    public void populateCourseAndReviewData(String courseId, CourseInfoWrapper courseWrapper, boolean loadVersionData) throws Exception;

    public CourseInfoWrapper copyCourse(String sourceCourseId) throws Exception;

    public CourseInfo getCurrentVersionOfCourse(String versionIndId,ContextInfo contextInfo) throws Exception;

    public boolean isModifiableCourse(CourseInfo course, ContextInfo contextInfo) throws Exception;

    /**
     * We can only have one retire proposal in workflow at a time.  This method
     * will call the proposal webservice and run a custom search that will look
     * for any retire proposals that are in the saved or enroute state.  A
     * count is returned and, if the count is > 0, this method will return false.
     */
    public boolean hasInProgressProposalForCourse() throws Exception;
}
