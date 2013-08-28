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


import java.util.List;

import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.SubjectCodeWrapper;

/**
 * {@link CourseInfo} Maintainable interface
 *
 *
 */
public interface CourseInfoMaintainable extends Maintainable {

    /**
     * Method called when queryMethodToCall is executed for Administering Organizations in order 
     * to suggest back to the user an Administering Organization
     *
     * @param organizationName  
     * @return {@link List} of wrapper instances which get added to the {@link CourseForm}
     */
	List<OrganizationInfoWrapper> getOrganizationsForSuggest(final String organizationName);

	List<CluInstructorInfoWrapper> getInstructorsForSuggest(String instructorName);
	
	CluInstructorInfoWrapper getInstructor(String instructorName);
	
    List<SubjectCodeWrapper> getSubjectCodesForSuggest(String subjectCode);
	
	List<CourseJointInfoWrapper> getJointOfferingCourseNumbersForSuggest(String courseNumber);
	
	/**
	 * Returns the CourseJointInfoDisplay object for the specified course code.
	 * @param courseCode The entire course code should be passed.
	 * @return Only 1 CourseJointInfoDisplay result is expected and will to be returned.
	 */
	CourseJointInfoWrapper getJointOfferingCourse(String courseCode);
	
	List<LoCategoryInfo> getLoCategoriesForSuggest(String categoryName);
}
