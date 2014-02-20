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

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.maintenance.Maintainable;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CollaboratorWrapper;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.CourseRuleManagementWrapper;
import org.kuali.student.cm.course.form.LoCategoryInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayWrapperModel;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.ReviewInfo;
import org.kuali.student.cm.course.form.SubjectCodeWrapper;
import org.kuali.student.cm.course.form.SupportingDocumentInfoWrapper;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.DecisionInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

/**
 * {@link CourseInfo} Maintainable interface
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
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
	
	List<CourseJointInfoWrapper> searchForJointOfferingCourses(String courseNumber);
	
	List<LoCategoryInfoWrapper> searchForLoCategories(String categoryName);

    List<AgendaEditor> getAgendasForRef(String discriminatorType, String refObjectId);

    /**
     * 
     * Retrieve the {@link CourseRuleManagementWrapper} instance that is specifically used for KRMS (Course Requisites) purposes.
     * 
     * @return {@link CourseRuleManagementWrapper}
     */
    CourseRuleManagementWrapper getCourseRuleManagementWrapper();

}
