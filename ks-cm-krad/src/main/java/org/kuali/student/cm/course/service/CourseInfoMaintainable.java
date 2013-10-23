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
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CollaboratorWrapper;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.LoCategoryInfoWrapper;
import org.kuali.student.cm.course.form.LoDisplayInfoWrapper;
import org.kuali.student.cm.course.form.LoItem;
import org.kuali.student.cm.course.form.LoItemModel;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.form.ResultValuesGroupInfoWrapper;
import org.kuali.student.cm.course.form.SubjectCodeWrapper;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.DecisionInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

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
	
	List<CourseJointInfoWrapper> searchForJointOfferingCourses(String courseNumber);
	
	List<LoCategoryInfoWrapper> searchForLoCategories(String categoryName);

    ProposalInfo getProposal();

    void setProposal(final ProposalInfo proposal);
        
    /**
     * Gets the value of audit
     * 
     * @return the value of audit
     */
    boolean isAudit();

    /**
     * Sets the value of audit
     * 
     * @param argAudit Value to assign to this.audit
     */
    void setAudit(final boolean argAudit);

    /**
     * Gets the value of passFail
     * 
     * @return the value of passFail
     */
    boolean isPassFail();

    /**
     * Sets the value of passFail
     * 
     * @param argPassFail Value to assign to this.passFail
     */
    void setPassFail(final boolean argPassFail);

    /**
     * Gets the value of finalExamStatus
     * 
     * @return the value of finalExamStatus
     */
    String getFinalExamStatus();

    /**
     * Sets the value of finalExamStatus
     * 
     * @param argFinalExamStatus Value to assign to this.finalExamStatus
     */
    void setFinalExamStatus(final String argFinalExamStatus);

    /**
     * Gets the value of finalExamRationale
     * 
     * @return the value of finalExamRationale
     */
    String getFinalExamRationale();

    /**
     * Sets the value of finalExamRationale
     * 
     * @param argFinalExamRationale Value to assign to this.finalExamRationale
     */
    void setFinalExamRationale(final String argFinalExamRationale);

    /**
     * Gets the value of showAll
     * 
     * @return the value of showAll
     */
    Boolean getShowAll();

    /**
     * Sets the value of showAll
     * 
     * @param argShowAll Value to assign to this.showAll
     */
    void setShowAll(final Boolean argShowAll);

    /**
     * Gets the value of userId
     * 
     * @return the value of userId
     */
    String getUserId();

    /**
     * Sets the value of userId
     * 
     * @param argUserId Value to assign to this.userId
     */
    void setUserId(final String argUserId);

    /**
     * Gets the value of lastUpdated
     * 
     * @return the value of lastUpdated
     */
    String getLastUpdated();

    /**
     * Sets the value of lastUpdated
     * 
     * @param argLastUpdated Value to assign to this.lastUpdated
     */
    void setLastUpdated(final String argLastUpdated);

    /**
     * Gets the list of Instructor wrappers
     * 
     * @return the list of {@link CluInstructorInfoWrapper}
     */
    List<CluInstructorInfoWrapper> getInstructorWrappers();

    /**
     * Sets the list of Instructor wrappers
     * 
     * @param instructorWrappers List of {@link CluInstructorInfoWrapper}
     */
    void setInstructorWrappers(List<CluInstructorInfoWrapper> instructorWrappers);

    /**
     * Gets the list of Course Joint wrappers
     * 
     * @return the list of {@link CourseJointInfoWrapper}
     */
    List<CourseJointInfoWrapper> getCourseJointWrappers();

    /**
     * Sets the list of Course Joint wrappers
     * 
     * @param courseJointWrappers List of {@link CourseJointInfoWrapper}
     */
    void setCourseJointWrappers(List<CourseJointInfoWrapper> courseJointWrappers);

    /**
     * Gets the list of Credit Option wrappers
     * 
     * @return the list of {@link ResultValuesGroupInfoWrapper}
     */
    List<ResultValuesGroupInfoWrapper> getCreditOptionWrappers();

    /**
     * Sets the list of Credit Option wrappers
     * 
     * @param creditOptionWrappers List of {@link ResultValuesGroupInfoWrapper}
     */
    void setCreditOptionWrappers(List<ResultValuesGroupInfoWrapper> creditOptionWrappers);

    /**
     * Gets the list of Comments
     * 
     * @return the list of {@link CommentInfo}
     */
    List<CommentInfo> getCommentInfos();

    /**
     * Sets the list of Comments
     * 
     * @param commentInfos List of {@link CommentInfo}
     */
    void setCommentInfos(List<CommentInfo> commentInfos);
    
    /**
     * Gets the list of CollaboratorWrapper
     * 
     * @return the list of {@link CollaboratorWrapper}
     */
    List<CollaboratorWrapper> getCollaboratorWrappers();

    /**
     * Sets the list of CollaboratorWrapper
     * 
     * @param CollaboratorWrapper List of {@link CollaboratorWrapper}
     */
    void setCollaboratorWrappers(List<CollaboratorWrapper> collaboratorWrappers);

    /**
     * Gets the list of Decisions
     * 
     * @return the list of {@link DecisionInfo}
     */
    List<DecisionInfo> getDecisions();

    /**
     * Sets the list of Decisions
     * 
     * @param decisions List of {@link DecisionInfo}
     */
    void setDecisions(List<DecisionInfo> decisions);

    /**
     * Gets the list of Administering Organizations
     * 
     * @return the list of {@link OrganizationInfoWrapper}
     */
    List<OrganizationInfoWrapper> getAdministeringOrganizations();

    /**
     * Sets the list of Administering Organizations
     * 
     * @param administeringOrganizations List of {@link OrganizationInfoWrapper}
     */
    void setAdministeringOrganizations(List<OrganizationInfoWrapper> administeringOrganizations);

    CourseInfo getCourse();
    
    void setCourse(final CourseInfo course);
    
    String getUnitsContentOwnerToAdd();

    void setUnitsContentOwnerToAdd(final String unitsContentOwnerToAdd);

    List<KeyValue> getUnitsContentOwner();

    void setUnitsContentOwner(final List<KeyValue> unitsContentOwner);
    
    /**
     * 
     * This method is used to check for DisclosureSection 
     * of Cross List, Offer Jointly and Version Codes
     * 
     * @return
     */
    String getCrossListingDisclosureSection();
    
    void setCrossListingDisclosureSection(final String argCrossListingDisclosureSection);
    

    /**
     * 
     * Retrieve the {@link LoItemModel} that is used for Learning Objectives.
     * 
     * @return The {@link LoItemModel} that is used to render {@link LoDisplayInfoWrapper} instances.
     */
    LoItemModel getLoItemModel();
}
