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

import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.util.ProposalUtil;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base class for all the wrappers around CourseInfo.
 */
public class CourseInfoWrapper extends ProposalElementsWrapper implements Serializable {

    /**
     * A boolean to identify when proposal data is required for the Review Page.
     *
     * This data is not needed in the case where the display is for ViewCourse only.
     */
    private boolean proposalDataRequired = true;

    private List<CluInstructorInfoWrapper> instructorWrappers = new ArrayList<CluInstructorInfoWrapper>();

    private List<CourseJointInfoWrapper> courseJointWrappers = new ArrayList<CourseJointInfoWrapper>();
    private List<ResultValuesGroupInfoWrapper> creditOptionWrappers;

    private List<OrganizationInfoWrapper> administeringOrganizations = new ArrayList<OrganizationInfoWrapper>();
    private ReviewProposalDisplay reviewProposalDisplay = new ReviewProposalDisplay();
    private List<String> campusLocations;
    private CourseInfo courseInfo = new CourseInfo();

    private List<FormatInfo> formats = new ArrayList<FormatInfo>();

    private String crossListingDisclosureSection;
    private String userId = "";
    private String lastUpdated = "";
    private String unitsContentOwnerToAdd = "";
    private List<CourseCreateUnitsContentOwner> unitsContentOwner = new ArrayList<CourseCreateUnitsContentOwner>();
    private String finalExamStatus;
    private boolean audit;
    private boolean passFail;
    private String finalExamRationale;
    // do we need this?
    private Boolean showAll;
    private LoDisplayWrapperModel loDisplayWrapperModel = new LoDisplayWrapperModel();

    // do we need this?
    private Date effectiveDate;
    // do we need this?
    private Date expirationDate;

    private String previousSubjectCode;

    private boolean currentVersion = true;
    private String versionText;

    /**
     * This is a display property for the course version (e.g. "Version 2 (current version)") on View Course compare view.
     */
    public String getVersionText() {
        return versionText;
    }

    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }

    public String getHiddenDescr() {
        return hiddenDescr;
    }

    public void setHiddenDescr(String hiddenDescr) {
        this.hiddenDescr = hiddenDescr;
    }

    // do we need this?
    private String hiddenDescr;

    public CourseInfoWrapper() {
        super(ProposalUtil.isUserCurriculumSpecialist(CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN),
                CurriculumManagementConstants.CourseViewSections.COURSE_INFO);
    }

    /**
     * @return True if this is the current version of the course (aka the most recent course in state active). Otherwise, false.
     */
    public boolean isCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(boolean currentVersion) {
        this.currentVersion = currentVersion;
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

    /**
     * @see #proposalDataRequired
     */
    public boolean isProposalDataRequired() {
        return proposalDataRequired;
    }

    /**
     * @see #proposalDataRequired
     */
    public void setProposalDataRequired(boolean proposalDataRequired) {
        this.proposalDataRequired = proposalDataRequired;
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

    public ReviewProposalDisplay getReviewProposalDisplay() {
        return reviewProposalDisplay;
    }

    public void setReviewProposalDisplay(ReviewProposalDisplay reviewProposalDisplay) {
        this.reviewProposalDisplay = reviewProposalDisplay;
    }

    /**
     * The purpose of campusLocations in this class is to have a property that returns 'null' when not set.
     * Unfortunately, the service DTO couseInfo.getCampusLocations() return an empty list. Currently (Rice 2.4)
     * ExistenceConstraintProcessor interprets only 'null' for requiredness, resulting in ignoring requiredness
     * validation on campusLocations even when not set.
     */
    public List<String> getCampusLocations() {
        this.campusLocations = this.getCourseInfo().getCampusLocations();
        if(campusLocations.isEmpty()) {
            campusLocations = null;
        }
        return campusLocations;
    }

    /**
     * Because KRAD the reqired validation doesn't check isEmpty() for Lists and CourseInfo creates an empty List
     * in the CourseInfo#getGradingOptions() we have to have this pass-thru method to make requiredness work
     * correctly. Similar to campusLocations above.
     */
    public List<String> getGradingOptions() {
        if (getCourseInfo().getGradingOptions().isEmpty()) {
            return null;
        }
        return getCourseInfo().getGradingOptions();
    }

    public void setGradingOptions(List<String> gradingOptions) {
        getCourseInfo().setGradingOptions(gradingOptions);
    }

    public List<FormatInfo> getFormats() {
        return formats;
    }

    public void setCampusLocations(List<String> campusLocations) {
        this.campusLocations = campusLocations;
        this.getCourseInfo().setCampusLocations(campusLocations);
    }

    public void setFormats(List<FormatInfo> formats) {
        this.formats = formats;
    }

}
