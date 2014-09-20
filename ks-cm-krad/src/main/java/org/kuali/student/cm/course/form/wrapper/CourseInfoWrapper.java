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
import org.kuali.student.cm.proposal.util.ProposalUtil;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base class for all the wrappers around CourseInfo.
 */
public class CourseInfoWrapper extends CommonCourseDataWrapper implements Serializable {

    private List<CluInstructorInfoWrapper> instructorWrappers = new ArrayList<CluInstructorInfoWrapper>();

    private List<CourseJointInfoWrapper> courseJointWrappers = new ArrayList<CourseJointInfoWrapper>();
    private List<ResultValuesGroupInfoWrapper> creditOptionWrappers;

    private List<OrganizationInfoWrapper> administeringOrganizations = new ArrayList<OrganizationInfoWrapper>();
    private ReviewProposalDisplay reviewProposalDisplay = new ReviewProposalDisplay();
    private List<String> campusLocations;

    private List<FormatInfo> formats = new ArrayList<FormatInfo>();

    private String crossListingDisclosureSection;
    private String unitsContentOwnerToAdd = "";
    private String finalExamStatus;
    private boolean audit;
    private boolean passFail;
    private String finalExamRationale;

    private String retirementRationale;
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

    private String currentCourseEndTermShortName;

    private String currentCourseStartTermId;

    // decides whether to set default data on the CourseInfo object at the save point
    private boolean disableCourseDefaulting = false;

    /**
     * For modify proposals this is the text for the end term that will be applied to the currently active
     * course when/if the draft course is made active.
     */
    public String getCurrentCourseEndTermShortName() {
        return currentCourseEndTermShortName;
    }

    public void setCurrentCourseEndTermShortName(String currentCourseEndTermShortName) {
         this.currentCourseEndTermShortName = currentCourseEndTermShortName;
    }

    /**
     * Storage for the start term of the "current" version of the course. This is used on a modify with new version
     * to constrain the values of the start term on the draft course.
     */
    public String getCurrentCourseStartTermId() {
        return this.currentCourseStartTermId;
    }

    public void setCurrentCourseStartTermId(String currentCourseStartTermId) {
         this.currentCourseStartTermId = currentCourseStartTermId;
    }

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
        // cannot pass document type in here because this could be Modify or Create
        super(ProposalUtil.isUserCurriculumSpecialist(), CurriculumManagementConstants.CourseViewSections.COURSE_INFO);
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

    public boolean isDisableCourseDefaulting() {
        return disableCourseDefaulting;
    }

    public void setDisableCourseDefaulting(boolean disableCourseDefaulting) {
        this.disableCourseDefaulting = disableCourseDefaulting;
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

    public void setUnitsContentOwnerToAdd(final String unitsContentOwnerToAdd) {
        this.unitsContentOwnerToAdd = unitsContentOwnerToAdd;
    }

    public String getUnitsContentOwnerToAdd() {
        return unitsContentOwnerToAdd;
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

    public String getRetirementRationale() {
        return retirementRationale;
    }

    public void setRetirementRationale(String retirementRationale) {
        this.retirementRationale = retirementRationale;
    }

}
