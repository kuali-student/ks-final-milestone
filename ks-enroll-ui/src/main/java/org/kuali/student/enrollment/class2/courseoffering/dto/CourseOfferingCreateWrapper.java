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
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class around {@link org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo} to hold all
 * the details related to a course offering used at the presentation layer. This is used to handle the ui
 * needs at the Create Co screen
 *
 * @see JointCourseWrapper
 * @see FormatOfferingWrapper
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

    private List<FormatOfferingWrapper> formatOfferingWrappers;
    private List<ExistingCourseOffering> existingOfferingsInCurrentTerm;
    private List<ExistingCourseOffering> existingTermOfferings;

//    private List<String> coListedCOs;
    private boolean crossListedCo;
//    private String displayStringCoListedCOs;


    private boolean excludeCancelledActivityOfferings;
    private boolean excludeSchedulingInformation;
    private boolean excludeInstructorInformation;

    private List<JointCourseWrapper> jointCourses;
    private boolean showJointOption;
    private String jointCourseCodes;

    private FormatOfferingWrapper addLineFormatWrapper;
    private boolean showCreateFormatSection;
    private boolean showCopyFormatSection;
    private List<FormatOfferingWrapper> copyFromFormats;

    private String adminOrg;

    private SocInfo socInfo;

    private int selectedJointCourseIndex;
    private String selectedJointCourseCode;
    private List<FormatOfferingWrapper> dialogFormatOfferingWrapperList;

    public CourseOfferingCreateWrapper(){
        showTermOfferingLink = true;
        formatOfferingWrappers = new ArrayList<FormatOfferingWrapper>();
        existingOfferingsInCurrentTerm = new ArrayList<ExistingCourseOffering>();
        existingTermOfferings = new ArrayList<ExistingCourseOffering>();
//        coListedCOs = new ArrayList<String>();
        jointCourses = new ArrayList<JointCourseWrapper>();
        showJointOption = false;
        crossListedCo = false;
        addLineFormatWrapper = new FormatOfferingWrapper();
        copyFromFormats = new ArrayList<FormatOfferingWrapper>();
        showCreateFormatSection = true;
        dialogFormatOfferingWrapperList = new ArrayList<FormatOfferingWrapper>();
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

    /**
     * Reference at the view xml
     * @return
     */
    @SuppressWarnings("unused")
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

    /**
     * Reference at the view xml
     * @return
     */
    @SuppressWarnings("unused")
    public int getNoOfTermOfferings() {
        return getExistingTermOfferings().size();
    }

    /**
     * Reference at the view xml
     * @return
     */
    @SuppressWarnings("unused")
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

    /*public List<String> getCoListedCOs() {
        return coListedCOs;
    }

    public void setCoListedCOs(List<String> coListedCOs) {
        this.coListedCOs = coListedCOs;
    }*/

    public boolean isCrossListedCo() {
        return crossListedCo;
    }

    public void setCrossListedCo(boolean crossListedCo) {
        this.crossListedCo = crossListedCo;
    }

    /*public String getDisplayStringCoListedCOs() {
        return displayStringCoListedCOs;
    }

    public void setDisplayStringCoListedCOs(String displayStringCoListedCOs) {
        this.displayStringCoListedCOs = displayStringCoListedCOs;
    }*/


    public List<JointCourseWrapper> getJointCourses() {
        return jointCourses;
    }

    /**
     * List of wrappers for the joint courses exists for a course
     * @param jointCourses
     */
    @SuppressWarnings("unused")
    public void setJointCourses(List<JointCourseWrapper> jointCourses) {
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

    /**
     * @see #setJointCourses(java.util.List)
     * @return
     */
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

    /**
     * @see #setFormatOfferingWrappers(java.util.List)
     * @return
     */
    public List<FormatOfferingWrapper> getFormatOfferingWrappers() {
        return formatOfferingWrappers;
    }

    /**
     * This collection is used to have a list of format offering wrappers
     * which includes all the format offerings from joint courses.
     *
     * @param formatOfferingWrappers list of format offering wrappers
     */
    @SuppressWarnings("unused")
    public void setFormatOfferingWrappers(List<FormatOfferingWrapper> formatOfferingWrappers) {
        this.formatOfferingWrappers = formatOfferingWrappers;
    }

    /**
     * @see #setAddLineFormatWrapper(FormatOfferingWrapper)
     * @return
     */
    public FormatOfferingWrapper getAddLineFormatWrapper() {
        return addLineFormatWrapper;
    }

    /**
     * This is a format offering wrapper used to handle the new format offering add logic
     * at the ui.
     *
     * @param addLineFormatWrapper
     */
    public void setAddLineFormatWrapper(FormatOfferingWrapper addLineFormatWrapper) {
        this.addLineFormatWrapper = addLineFormatWrapper;
    }

    /**
     *
     * @see #setShowCreateFormatSection(boolean)
     * @return
     */
    public boolean isShowCreateFormatSection() {
        return showCreateFormatSection;
    }

    /**
     * Whether to display the <i>'Add Formats'</i> link at the ui.
     *
     * <p>
     *     Please note, if the course doesnt have any joint courses associated with
     *     this course, the copy link wont show up at the ui.
     * </p>
     *
     * @see #setShowCopyFormatSection(boolean)
     * @param showCreateFormatSection
     */
    public void setShowCreateFormatSection(boolean showCreateFormatSection) {
        this.showCreateFormatSection = showCreateFormatSection;
    }

    /**
     * @see #setShowCreateFormatSection(boolean)
     * @return
     */
    @SuppressWarnings("unused")
    public boolean isShowCopyFormatSection() {
        return showCopyFormatSection;
    }

    /**
     * This is a flag whether to display the <i>'Copy From Joints'</i> link at the UI.
     * If allowed, user can copy existing format offerings to create new ones.
     *
     * <p>
     *     Please note, if the course doesnt have any joint courses associated with
     *     this course, the copy link wont show up at the ui.
     * </p>
     *
     * @see #setShowCreateFormatSection(boolean)
     * @param showCopyFormatSection
     */
    public void setShowCopyFormatSection(boolean showCopyFormatSection) {
        this.showCopyFormatSection = showCopyFormatSection;
    }

    /**
     *
     * @see #setCopyFromFormats(java.util.List)
     * @return
     */
    public List<FormatOfferingWrapper> getCopyFromFormats() {
        return copyFromFormats;
    }

    /**
     * List of existing format offerings from which users can create new formatofferings
     * for other joint courses or regular course.
     *
     * @param copyFromFormats list of format offering wrappers
     */
    public void setCopyFromFormats(List<FormatOfferingWrapper> copyFromFormats) {
        this.copyFromFormats = copyFromFormats;
    }

    /*
    *  @param adminOrg
     */
    public String getAdminOrg(){
        return adminOrg;
    }

    public void setAdminOrg(String adminOrg){
        this.adminOrg=adminOrg;
    }

    /*
    *  @param socInfo
     */
    public SocInfo getSocInfo() {
        return socInfo;
    }

    public void setSocInfo(SocInfo socInfo) {
        this.socInfo = socInfo;
    }

    /**
     * @see #setSelectedJointCourseIndex(int)
     * @return
     */
    public int getSelectedJointCourseIndex() {
        return selectedJointCourseIndex;
    }

    /**
     * This tracks the user selected index of the joint courses. As there is no way
     * to track the selected index when we use dialog within a collection line index,
     * we track the selected index with client side script
     *
     * @see <a href="https://jira.kuali.org/browse/KSENROLL-5767">KSENROLL-5767</a>
     * @param selectedJointCourseIndex
     */
    @SuppressWarnings("unused")
    public void setSelectedJointCourseIndex(int selectedJointCourseIndex) {
        this.selectedJointCourseIndex = selectedJointCourseIndex;
    }

    /**
     *
     * @see #setSelectedJointCourseCode(String)
     * @return
     */
    @SuppressWarnings("unused")
    public String getSelectedJointCourseCode() {
        return selectedJointCourseCode;
    }

    /**
     * This is used to display the joint course offering code at the delete confirmation dialog
     *
     * @param selectedJointCourseCode
     */
    public void setSelectedJointCourseCode(String selectedJointCourseCode) {
        this.selectedJointCourseCode = selectedJointCourseCode;
    }

    /**
     * @see #setDialogFormatOfferingWrapperList(java.util.List)
     * @return
     */
    public List<FormatOfferingWrapper> getDialogFormatOfferingWrapperList() {
        return dialogFormatOfferingWrapperList;
    }

    /**
     * This is a list of format offerrings for a cross list used to display at the delete
     * confirmation dialog.
     *
     * @param dialogFormatOfferingWrapperList
     */
    public void setDialogFormatOfferingWrapperList(List<FormatOfferingWrapper> dialogFormatOfferingWrapperList) {
        this.dialogFormatOfferingWrapperList = dialogFormatOfferingWrapperList;
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
        setShowCopyFormatSection(false);
        setShowCreateFormatSection(true);
        getCopyFromFormats().clear();
        if (getAlternateCOCodes() != null){
            getAlternateCOCodes().clear();
        }
        setSelectedJointCourseCode(StringUtils.EMPTY);
        setSelectedJointCourseIndex(0);
        getDialogFormatOfferingWrapperList().clear();
        setSocInfo(null);
        setAdminOrg(null);
    }

}