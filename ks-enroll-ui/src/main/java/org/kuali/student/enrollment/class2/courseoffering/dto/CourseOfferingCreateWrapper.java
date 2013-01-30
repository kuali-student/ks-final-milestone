/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;

import java.util.ArrayList;
import java.util.List;

/**
 * This is being used at the Create CO screen. This is a wrapper class to hold all the
 * UI rendering properties and the service dtos related to courseoffering and formatoffering
 *
 */
public class CourseOfferingCreateWrapper extends CourseOfferingWrapper {

    private String targetTermCode;
    private String catalogCourseCode;
    private boolean createFromCatalog;

    private String creditCount;

    private boolean showTermOfferingLink;
    private boolean showCatalogLink;
    private boolean showAllSections;
    private boolean enableCreateButton;

    private String courseOfferingSuffix;

    private int noOfTermOfferings;

    private List<FormatOfferingCreateWrapper> formatOfferingWrappers;
    private List<ExistingCourseOffering> existingOfferingsInCurrentTerm;
    private List<ExistingCourseOffering> existingTermOfferings;

    private List<String> coListedCOs;
    private Boolean selectCrossListingAllowed;
    private boolean crossListedCo;


    private boolean excludeCancelledActivityOfferings;
    private boolean excludeSchedulingInformation;
    private boolean excludeInstructorInformation;

    private List<CourseJointCreateWrapper> jointCourses;
    private boolean showJointOption;
    private String jointCourseCodes;

    public CourseOfferingCreateWrapper(){
        super();
        showTermOfferingLink = true;
        formatOfferingWrappers = new ArrayList<FormatOfferingCreateWrapper>();
        existingOfferingsInCurrentTerm = new ArrayList<ExistingCourseOffering>();
        existingTermOfferings = new ArrayList<ExistingCourseOffering>();
        coListedCOs = new ArrayList<String>();
        jointCourses = new ArrayList<CourseJointCreateWrapper>();
        showJointOption = false;
        crossListedCo = false;
    }

    public String getTargetTermCode() {
        return targetTermCode;
    }

    public void setTargetTermCode(String targetTermCode) {
        this.targetTermCode = targetTermCode;
    }

    public String getCatalogCourseCode() {
        return catalogCourseCode;
    }

    public void setCatalogCourseCode(String catalogCourseCode) {
        this.catalogCourseCode = catalogCourseCode;
    }

    public boolean isCreateFromCatalog() {
        return createFromCatalog;
    }

    public void setCreateFromCatalog(boolean createFromCatalog) {
        this.createFromCatalog = createFromCatalog;
    }

    public String getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }

    public boolean isShowTermOfferingLink() {
        return showTermOfferingLink;
    }

    public void setShowTermOfferingLink(boolean showTermOfferingLink) {
        this.showTermOfferingLink = showTermOfferingLink;
    }

    public boolean isShowCatalogLink() {
        return showCatalogLink;
    }

    public void setShowCatalogLink(boolean showCatalogLink) {
        this.showCatalogLink = showCatalogLink;
    }

    public String getCourseOfferingSuffix() {
        return courseOfferingSuffix;
    }

    public void setCourseOfferingSuffix(String courseOfferingSuffix) {
        this.courseOfferingSuffix = courseOfferingSuffix;
    }

    public boolean isShowAllSections() {
        return showAllSections;
    }

    public void setShowAllSections(boolean showAllSections) {
        this.showAllSections = showAllSections;
    }

    public List<ExistingCourseOffering> getExistingOfferingsInCurrentTerm() {
        return existingOfferingsInCurrentTerm;
    }

    public void setExistingOfferingsInCurrentTerm(List<ExistingCourseOffering> existingOfferingsInCurrentTerm) {
        this.existingOfferingsInCurrentTerm = existingOfferingsInCurrentTerm;
    }

    public List<ExistingCourseOffering> getExistingTermOfferings() {
        return existingTermOfferings;
    }

    public void setExistingTermOfferings(List<ExistingCourseOffering> existingTermOfferings) {
        this.existingTermOfferings = existingTermOfferings;
    }

    public int getNoOfTermOfferings() {
        return getExistingTermOfferings().size();
    }

    public boolean isEnableCreateButton() {
        return enableCreateButton;
    }

    public void setEnableCreateButton(boolean enableCreateButton) {
        this.enableCreateButton = enableCreateButton;
    }

    public boolean isExcludeCancelledActivityOfferings() {
        return excludeCancelledActivityOfferings;
    }

    public void setExcludeCancelledActivityOfferings(boolean excludeCancelledActivityOfferings) {
        this.excludeCancelledActivityOfferings = excludeCancelledActivityOfferings;
    }

    public boolean isExcludeSchedulingInformation() {
        return excludeSchedulingInformation;
    }

    public void setExcludeSchedulingInformation(boolean excludeSchedulingInformation) {
        this.excludeSchedulingInformation = excludeSchedulingInformation;
    }

    public boolean isExcludeInstructorInformation() {
        return excludeInstructorInformation;
    }

    public void setExcludeInstructorInformation(boolean excludeInstructorInformation) {
        this.excludeInstructorInformation = excludeInstructorInformation;
    }

    public List<String> getCoListedCOs() {
        return coListedCOs;
    }

    public void setCoListedCOs(List<String> coListedCOs) {
        this.coListedCOs = coListedCOs;
    }

    public boolean isSelectCrossListingAllowed() {
         if (null == selectCrossListingAllowed) {
             String selectiveColocationAllowed = ConfigContext.getCurrentContextConfig().getProperty("kuali.ks.enrollment.options.selective-crossListing-allowed");
             if("false".equalsIgnoreCase(selectiveColocationAllowed)) {
                 selectCrossListingAllowed = false;
             } else {
                 selectCrossListingAllowed = true;
             };
         }

        return selectCrossListingAllowed;
    }

    public boolean isCrossListedCo() {
        return crossListedCo;
    }

    public void setCrossListedCo(boolean crossListedCo) {
        this.crossListedCo = crossListedCo;
    }

    public void setSelectCrossListingAllowed(boolean selectCrossListingAllowed) {
        this.selectCrossListingAllowed = selectCrossListingAllowed;
    }

    public List<CourseJointCreateWrapper> getJointCourses() {
        return jointCourses;
    }

    /**
     * List of wrappers for the joint courses exists for a course
     * @param jointCourses
     */
    @SuppressWarnings("unused")
    public void setJointCourses(List<CourseJointCreateWrapper> jointCourses) {
        this.jointCourses = jointCourses;
    }

    /**
     * This is used in the view xml to whether display the joint courses table or not
     * @return
     */
    @SuppressWarnings("unused")
    public boolean isShowJointOption() {
        return showJointOption;
    }

    /**
     * To decide whether to display joint courses if exists sothat the joint course table
     * will be displayed.
     *
     * @param showJointOption
     */
    public void setShowJointOption(boolean showJointOption) {
        this.showJointOption = showJointOption;
    }

    public String getJointCourseCodes() {
        return jointCourseCodes;
    }

    /**
     * This is a ui property to display all the course offering codes for
     * which the user can create format offerings
     *
     * @param jointCourseCodes
     */
    public void setJointCourseCodes(String jointCourseCodes) {
        this.jointCourseCodes = jointCourseCodes;
    }

    public List<FormatOfferingCreateWrapper> getFormatOfferingWrappers() {
        return formatOfferingWrappers;
    }

    /**
     * This collection is used to have a list of format offering wrappers
     * which includes all the format offerings from joint courses.
     *
     * @param formatOfferingWrappers list of format offering wrappers
     */
    @SuppressWarnings("unused")
    public void setFormatOfferingWrappers(List<FormatOfferingCreateWrapper> formatOfferingWrappers) {
        this.formatOfferingWrappers = formatOfferingWrappers;
    }

    /**
     * Clears all the properties. This is needed to clear out all the previous ui data first
     * before displaying the new data when the user clicks on the Show button
     */
    public void clear(){
        setCourse(null);
        setShowAllSections(false);
        setCreditCount("");
        getExistingTermOfferings().clear();
        getExistingOfferingsInCurrentTerm().clear();
        setEnableCreateButton(false);
        setExcludeCancelledActivityOfferings(false);
        setExcludeSchedulingInformation(false);
        setExcludeInstructorInformation(false);
        getFormatOfferingWrappers().clear();
        getJointCourses().clear();
        setJointCourseCodes(StringUtils.EMPTY);
        setShowJointOption(false);
        setCrossListedCo(false);
    }
}
