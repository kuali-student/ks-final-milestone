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
 * @author Kuali Student Team
 */

package org.kuali.student.cm.course.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.bind.RequestProtected;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleManagementWrapper;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base class for all the wrappers around CourseInfo.
 */
public class CourseInfoWrapper extends LURuleManagementWrapper implements Serializable {

    private static final String DEFAULT_REQUIRED_WORKFLOW_MODE = "Submit";

    private ProposalInfo proposalInfo = new ProposalInfo();
    private List<CluInstructorInfoWrapper> instructorWrappers = new ArrayList<CluInstructorInfoWrapper>();

    private List<CourseJointInfoWrapper> courseJointWrappers = new ArrayList<CourseJointInfoWrapper>();
    private List<ResultValuesGroupInfoWrapper> creditOptionWrappers;

    private List<OrganizationInfoWrapper> administeringOrganizations = new ArrayList<OrganizationInfoWrapper>();
    private List<CollaboratorWrapper> collaboratorWrappers = new ArrayList<CollaboratorWrapper>();
    private List<String> deletedCollaboratorWrapperActionRequestIds = new ArrayList<String>();
    private List<SupportingDocumentInfoWrapper> supportingDocs = new ArrayList<SupportingDocumentInfoWrapper>();
    private List<SupportingDocumentInfoWrapper> supportingDocsToDelete = new ArrayList<SupportingDocumentInfoWrapper>();
    private ReviewProposalDisplay reviewProposalDisplay = new ReviewProposalDisplay();
    private CourseInfo courseInfo = new CourseInfo();
    private List<FormatInfo> formats = new ArrayList<FormatInfo>();

    private String crossListingDisclosureSection;
    private String userId = "";
    private String lastUpdated = "";
    private String unitsContentOwnerToAdd = "";
    private List<CourseCreateUnitsContentOwner> unitsContentOwner = new ArrayList<CourseCreateUnitsContentOwner>();
    private String requiredWorkflowMode = DEFAULT_REQUIRED_WORKFLOW_MODE;
    private String finalExamStatus;
    private boolean audit;
    private boolean passFail;
    private String finalExamRationale;
    private Boolean showAll;
    private LoDisplayWrapperModel loDisplayWrapperModel = new LoDisplayWrapperModel();

    private Date effectiveDate;
    private Date expirationDate;

    private boolean agendaDirty;

    public boolean isAgendaDirty() {
        return agendaDirty;
    }

    public void setAgendaDirty(boolean agendaDirty) {
        this.agendaDirty = agendaDirty;
    }

    private String previousSubjectCode;

    public String getHiddenDescr() {
        return hiddenDescr;
    }

    public void setHiddenDescr(String hiddenDescr) {
        this.hiddenDescr = hiddenDescr;
    }

    private String hiddenDescr;

    private boolean missingRequiredFields;

    private transient CreateCourseUIHelper uiHelper = new CreateCourseUIHelper();

    public CourseInfoWrapper() {
    }

    /**
     * Flag used on the Review Course Proposal page to indicate whether the "yellow bar" should be displayed.
     *
     * @return True if the course is missing required fields for the next state or routing node. Otherwise, false.
     */
    public boolean isMissingRequiredFields() {
        return missingRequiredFields;
    }

    public void setMissingRequiredFields(boolean missingRequiredFields) {
        this.missingRequiredFields = missingRequiredFields;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public boolean isPassFail() {
        return passFail;
    }

    public void setPassFail(boolean passFail) {
        this.passFail = passFail;
    }

    public String getFinalExamRationale() {
        return finalExamRationale;
    }

    public void setFinalExamRationale(String finalExamRationale) {
        this.finalExamRationale = finalExamRationale;
    }

    public Boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(Boolean showAll) {
        this.showAll = showAll;
    }

    /**
     * Since only one organization can be added for the Curriculum Oversight, a newly added one should replace the
     * existing one. This property is used to remember which was the old one when the time comes to persist.
     */
    public String getPreviousSubjectCode() {
        return previousSubjectCode;
    }

    public void setPreviousSubjectCode(String previousSubjectCode) {
        this.previousSubjectCode = previousSubjectCode;
    }

    public LoDisplayWrapperModel getLoDisplayWrapperModel() {
        return loDisplayWrapperModel;
    }

    public void setLoDisplayWrapperModel(LoDisplayWrapperModel loDisplayWrapperModel) {
        this.loDisplayWrapperModel = loDisplayWrapperModel;
    }

    /**
     * Gets the value of finalExamStatus
     *
     * @return the value of finalExamStatus
     */
    public String getFinalExamStatus() {
        return this.finalExamStatus;
    }

    /**
     * Sets the value of finalExamStatus
     *
     * @param argFinalExamStatus Value to assign to this.finalExamStatus
     */
    public void setFinalExamStatus(final String argFinalExamStatus) {
        this.finalExamStatus = argFinalExamStatus;
    }

    public String getCrossListingDisclosureSection() {
        this.crossListingDisclosureSection = "true";

        if (getCourseInfo().getCrossListings().isEmpty() && getCourseJointWrappers().isEmpty() && getCourseInfo().getVariations().isEmpty()) {
            this.crossListingDisclosureSection = "false";
        }
        return this.crossListingDisclosureSection;
    }

    public void setCrossListingDisclosureSection(String argCrossListingDisclosureSection) {
        this.crossListingDisclosureSection = argCrossListingDisclosureSection;
    }

    public String getRequiredWorkflowMode() {
        return requiredWorkflowMode;
    }

    public void setRequiredWorkflowMode(final String requiredWorkflowMode) {
        this.requiredWorkflowMode = requiredWorkflowMode;
    }

    public void setUnitsContentOwner(List<CourseCreateUnitsContentOwner> unitsContentOwner) {
        this.unitsContentOwner = unitsContentOwner;
    }

    public List<CourseCreateUnitsContentOwner> getUnitsContentOwner() {
        return unitsContentOwner;
    }

    public void setUnitsContentOwnerToAdd(final String unitsContentOwnerToAdd) {
        this.unitsContentOwnerToAdd = unitsContentOwnerToAdd;
    }

    public String getUnitsContentOwnerToAdd() {
        return unitsContentOwnerToAdd;
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

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public ProposalInfo getProposalInfo() {
        return proposalInfo;
    }

    public void setProposalInfo(ProposalInfo proposalInfo) {
        this.proposalInfo = proposalInfo;
    }

    public List<CluInstructorInfoWrapper> getInstructorWrappers() {
        return instructorWrappers;
    }

    public void setInstructorWrappers(List<CluInstructorInfoWrapper> instructorWrappers) {
        this.instructorWrappers = instructorWrappers;
    }

    public List<CourseJointInfoWrapper> getCourseJointWrappers() {
        return courseJointWrappers;
    }

    public void setCourseJointWrappers(List<CourseJointInfoWrapper> courseJointWrappers) {
        this.courseJointWrappers = courseJointWrappers;
    }

    public List<ResultValuesGroupInfoWrapper> getCreditOptionWrappers() {
        if (creditOptionWrappers == null) {
            creditOptionWrappers = new ArrayList<ResultValuesGroupInfoWrapper>();
        }
        return creditOptionWrappers;
    }

    public void setCreditOptionWrappers(List<ResultValuesGroupInfoWrapper> creditOptionWrappers) {
        this.creditOptionWrappers = creditOptionWrappers;
    }

    public List<OrganizationInfoWrapper> getAdministeringOrganizations() {
        return administeringOrganizations;
    }

    public void setAdministeringOrganizations(List<OrganizationInfoWrapper> administeringOrganizations) {
        this.administeringOrganizations = administeringOrganizations;
    }

    public List<CollaboratorWrapper> getCollaboratorWrappers() {
        return collaboratorWrappers;
    }

    public void setCollaboratorWrappers(List<CollaboratorWrapper> collaboratorWrappers) {
        this.collaboratorWrappers = collaboratorWrappers;
    }

    public List<String> getDeletedCollaboratorWrapperActionRequestIds() {
        return deletedCollaboratorWrapperActionRequestIds;
    }

    public void setDeletedCollaboratorWrapperActionRequestIds(List<String> deletedCollaboratorWrapperActionRequestIds) {
        this.deletedCollaboratorWrapperActionRequestIds = deletedCollaboratorWrapperActionRequestIds;
    }

    public List<SupportingDocumentInfoWrapper> getSupportingDocs() {
        return supportingDocs;
    }

    public void setSupportingDocs(List<SupportingDocumentInfoWrapper> supportingDocs) {
        this.supportingDocs = supportingDocs;
    }

    public ReviewProposalDisplay getReviewProposalDisplay() {
        return reviewProposalDisplay;
    }

    public void setReviewProposalDisplay(ReviewProposalDisplay reviewProposalDisplay) {
        this.reviewProposalDisplay = reviewProposalDisplay;
    }

    public CreateCourseUIHelper getUiHelper() {
        return uiHelper;
    }

    public void setUiHelper(CreateCourseUIHelper uiHelper) {
        this.uiHelper = uiHelper;
    }

    public List<FormatInfo> getFormats() {
        return formats;
    }

    public void setFormats(List<FormatInfo> formats) {
        this.formats = formats;
    }

    public List<SupportingDocumentInfoWrapper> getSupportingDocsToDelete() {
        if (supportingDocsToDelete == null){
            supportingDocsToDelete = new ArrayList<>();
        }
        return supportingDocsToDelete;
    }

    public void setSupportingDocsToDelete(List<SupportingDocumentInfoWrapper> supportingDocsToDelete) {
        this.supportingDocsToDelete = supportingDocsToDelete;
    }

    /**
     * A helper class which holds all the properties needed for display at the ui but not part of the model.
     * As <class>CourseInfoWrapper</class> is just a wrapper for <class>CourseInfo</class> and most of the
     * properties are involved in interacting with services later, it's confussing to mix up the ui only
     * properties with those. This seperation would help to easily identify which are the ui only properties.
     * Also, the same <class>CourseInfoWrapper</class> class can be used at other views, we can have multiple ui helper
     * implementation if needed to support multiple ways to display the same data.
     * <p/>
     * For example, <method>getHeaderText</method> is used to display the header text at Create Course view only and not
     * involved in data persistance.
     */
    public class CreateCourseUIHelper {

        CurriculumManagementConstants.CourseViewSections selectedSection;

        // disallow the curriculumSpecialistUser property to be set by the request
        @RequestProtected
        boolean curriculumSpecialistUser;

        // disallow the useReviewProcess property to be set by the request
        @RequestProtected
        boolean useReviewProcess;

        public CreateCourseUIHelper() {
            curriculumSpecialistUser = CourseProposalUtil.isUserCurriculumSpecialist();
            selectedSection = CurriculumManagementConstants.CourseViewSections.COURSE_INFO;
        }

        /**
         * A CS not using workflow gets an admin workflow document type. Some UI elements/behavior are conditional based on doc type.
         *
         * @return True if an admin doc type is being used. Otherwise, false.
         */
        public boolean isAdminProposal() {
            return curriculumSpecialistUser && !isUseReviewProcess();
        }

        public void setCurriculumSpecialistUser(boolean curriculumSpecialistUser) {
            this.curriculumSpecialistUser = curriculumSpecialistUser;
        }

        public boolean isCurriculumSpecialistUser() {
            return curriculumSpecialistUser;
        }

        public String getProposalName() {
            return getProposalInfo() != null ? getProposalInfo().getName() : "";
        }

        public boolean isUseReviewProcess() {
            return useReviewProcess;
        }

        public void setUseReviewProcess(boolean useReviewProcess) {
            this.useReviewProcess = useReviewProcess;
        }

        public CurriculumManagementConstants.CourseViewSections getSelectedSection() {
            return selectedSection;
        }

        public void setSelectedSection(CurriculumManagementConstants.CourseViewSections selectedSection) {
            this.selectedSection = selectedSection;
        }

        public String getHeaderText() {
            String headerSuffixText;

            if (!isUseReviewProcess()) {
                headerSuffixText = " (Admin Proposal)";
            } else {
                headerSuffixText = " (Proposal)";
            }

            if (proposalInfo != null && StringUtils.isNotBlank(proposalInfo.getName())) {
                return proposalInfo.getName() + headerSuffixText;
            } else {
                return "New Course" + headerSuffixText;
            }
        }
    }
}
