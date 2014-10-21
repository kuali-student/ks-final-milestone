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
 * Created by David Yin on 3/4/13
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.krad.web.bind.RequestAccessible;
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class2.acal.dto.ExamPeriodWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ActivityOfferingDisplayUI;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.CourseOfferingDisplayUI;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class provides a form for the Course Offering Management ui
 *
 * @author Kuali Student Team
 */
public class CourseOfferingManagementForm extends KSUifForm implements ActivityOfferingDisplayUI, CourseOfferingDisplayUI {
    /**
     * This is for authorization purpose of manageTheCO page
     */
    @RequestAccessible
    private String adminOrg;

    /**
     * Search input field with the label of *Term
     */
    @RequestAccessible
    private String termCode;
    /**
     * Search input filed with the label of *Course
     * This is the user entered field data when they search for Course/Activity Offerings.
     * For a term and subjectArea/CourseOffering code, search will be performed.        *
     */
    @RequestAccessible
    private String inputCode;
    /**
     * This is used to display CO search result list in manage COs page
     */
    @RequestAccessible
    private List<CourseOfferingListSectionWrapper> courseOfferingResultList;
    /**
     * This is used to display AO list under a specified CO in manage the CO page
     */
    @RequestAccessible
    private List<ActivityOfferingWrapper> activityWrapperList;
    /**
     * This is used to display cluster list under a specified CO in manage the CO page
     * was filteredAOClusterWrapperList in RGManagementForm
     */
    @RequestAccessible
    private List<ActivityOfferingClusterWrapper> clusterResultList;
    /**
     * This is used to display all registration group list under a specified CO in
     * manage the CO page.
     */
    @RequestAccessible
    private List<RegistrationGroupWrapper> rgResultList;

    @RequestAccessible
    private boolean hasMoreThanOneFormat = false;
    @RequestAccessible
    private boolean hasMoreThanOneCluster = false;

    @RequestAccessible
    private TermInfo termInfo;

    /**
     * @see #setSubjectCode(String)
     */
    @RequestAccessible
    private String subjectCode;

    /**
     * @see #setSubjectCodeDescription(String)
     */
    @RequestAccessible
    private String subjectCodeDescription;


    //For Adding Activity
    @RequestAccessible
    private String formatIdForNewAO;
    @RequestAccessible
    private String formatOfferingIdForNewAO;
    @RequestAccessible
    private String activityIdForNewAO;
    @RequestAccessible
    private String clusterIdForNewAO;
    @RequestAccessible
    private String noOfActivityOfferings;

    //TODO: why do we need wrapper objects here. why can't be COInfo.id?
    /**
     * This is used to hold the Course Offering for mange the CO page
     */
    @RequestAccessible
    private CourseOfferingWrapper currentCourseOfferingWrapper;
    @RequestAccessible
    private CourseOfferingWrapper previousCourseOfferingWrapper;
    @RequestAccessible
    private CourseOfferingWrapper nextCourseOfferingWrapper;

    //TODO: do we need them?
    @RequestAccessible
    private String selectedOfferingAction;
    @RequestAccessible
    private String coViewLinkWrapper = "View"; // temp var to hold/store the View Details Link

    /**
     * To display the soc state
     */
    @RequestAccessible
    private String socState;

    /**
     * This is being used in authz to decide the toolbar permissions.
     */
    @RequestAccessible
    private String socStateKey;

    /**
     * FIXME: Dont think we're using this property at view xml
     */
    @RequestAccessible
    private String socSchedulingStateKey;

    /**
     * FIXME: Cant we have a flag at the ActivityOfferingWrapper to handle whether the AO is selected to delete or not?
     * I dont think we need a seperate list to handle that - courseOfferingCopyWrapper
     */
    @RequestAccessible
    private List<ActivityOfferingWrapper> selectedToDeleteList;
    @RequestAccessible
    private List<ActivityOfferingWrapper> selectedToCSRList;
    @RequestAccessible
    private CourseOfferingCopyWrapper courseOfferingCopyWrapper;

    @RequestAccessible
    private boolean isCrossListedCO;
    @RequestAccessible
    private int numOfCrossListedCosToDelete=0;
    @RequestAccessible
    private boolean isColocatedCO;
    @RequestAccessible
    private boolean isColocatedCoOnly;
    @RequestAccessible
    private Integer numOfColocatedCosToDelete = 0;
    @RequestAccessible
    private Integer numOfColocatedAosToDelete = 0;
    @RequestAccessible
    private boolean isJointDefinedCo;
    @RequestAccessible
    private boolean isJointDefinedCoOnly;
    @RequestAccessible
    private Integer numOfJointDefinedCosToDelete = 0;
    @RequestAccessible
    private int numIneligibleAOsForCSR=0;

    //TODO: do we need this one?
    @RequestAccessible
    private boolean readOnly;
    //TODO: do we need this one?
    @RequestAccessible
    private List<CourseOfferingListSectionWrapper> selectedCoToDeleteList;
    @RequestAccessible
    private int totalAOsToBeDeleted = 0;

    @RequestAccessible
    private String toBeScheduledCourseOfferingsUI;
    @RequestAccessible
    private int toBeScheduledCourseOfferingsCount;
    @RequestAccessible
    private boolean selectedIllegalAOInDeletion = false;
    @RequestAccessible
    private boolean selectedIllegalAOInCSR = false;
    @RequestAccessible
    private String actionCSR = "";

    @RequestAccessible
    private boolean withinPortal = true;

    @RequestAccessible
    private boolean editAuthz;

    @RequestAccessible
    private boolean enableAddButton = false;
    @RequestAccessible
    private boolean enableMoveAOButton = false;
    @RequestAccessible
    private boolean enableAddClusterButton = false;

    @RequestAccessible
    private Date termClassStartDate;

    //for manage AOs, Clusters, and RGs under a CO
    @RequestAccessible
    private String privateClusterNamePopover;
    @RequestAccessible
    private String publishedClusterNamePopover;
    @RequestAccessible
    private String privateClusterNameForRenamePopover;
    @RequestAccessible
    private String publishedClusterNameForRenamePopover;
    @RequestAccessible
    private String privateClusterNameForMovePopover;
    @RequestAccessible
    private String publishedClusterNameForMovePopover;
    @RequestAccessible
    private String formatOfferingIdForViewRG;
    @RequestAccessible
    private String formatOfferingName;
    @RequestAccessible
    private Map<String,FormatOfferingInfo> foId2aoTypeMap;

    @RequestAccessible
    private boolean disableMoveButtonForMoveAOCPopOver;
    @RequestAccessible
    private String clusterIdForAOMove;
    @RequestAccessible
    private String selectedFOIDForAOMove;
    @RequestAccessible
    private String selectedFONameForAOMove;
    @RequestAccessible
    private String csrLabel;

    @RequestAccessible
    private ActivityOfferingClusterWrapper selectedCluster;
    @RequestAccessible
    private int aoCount=0;

    @RequestAccessible
    private String selectedTabId; //Which tab was selected

    //TODO: do we still need this parameter?
    @RequestAccessible
    private boolean hasAOCluster;

    @RequestAccessible
    private CourseOfferingContextBar contextBar = CourseOfferingContextBar.NULL_SAFE_INSTANCE;

    //Requisite link read only flag
    @RequestAccessible
    private boolean requisiteLink;

    /**
     * This is used to display EOs list under a specified CO in view ExamOfferings
     */
    @RequestAccessible
    private List<ExamOfferingRelationInfo> eoRelations;
    @RequestAccessible
    private List<ExamOfferingWrapper> examOfferingWrapperList;
    @RequestAccessible
    private List<ExamOfferingWrapper> examOfferingCancelledList;

    @RequestAccessible
    private List<ExamOfferingClusterWrapper> eoClusterResultList;
    @RequestAccessible
    private List<ExamOfferingClusterWrapper> eoCancelClusterList;

    @RequestAccessible
    private ExamPeriodWrapper examPeriodWrapper;

    public boolean getHasAOCluster() {
        return hasAOCluster;
    }

    public void setHasAOCluster(boolean hasAOCluster) {
        this.hasAOCluster = hasAOCluster;
    }

    public CourseOfferingManagementForm(){
        activityWrapperList = new ArrayList<ActivityOfferingWrapper>();
        selectedToDeleteList = new ArrayList<ActivityOfferingWrapper>();
        selectedToCSRList = new ArrayList<ActivityOfferingWrapper>();
        courseOfferingResultList = new ArrayList<CourseOfferingListSectionWrapper>();
        selectedCoToDeleteList = new ArrayList<CourseOfferingListSectionWrapper>();
        clusterResultList = new ArrayList<ActivityOfferingClusterWrapper>();
        rgResultList = new ArrayList<RegistrationGroupWrapper>();
        examOfferingWrapperList = new ArrayList<ExamOfferingWrapper>();
        examOfferingCancelledList = new ArrayList<ExamOfferingWrapper>();
        eoClusterResultList = new ArrayList<ExamOfferingClusterWrapper>();
        eoCancelClusterList = new ArrayList<ExamOfferingClusterWrapper>();
        setCourseOfferingCopyWrapper(null);
        setFormatOfferingIdForNewAO(null);
    }

    public boolean isHasAO() {
        return !activityWrapperList.isEmpty();
    }

    public String getTermCode(){
        return termCode;
    }

    public void setTermCode(String termCode){
        this.termCode = termCode;
    }

    public TermInfo getTermInfo(){
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo){
        this.termInfo = termInfo;
    }

    /**
     * Returns the Subject (Org) code.
     *
     * @see #setSubjectCode(String)
     * @return subjectCode
     */
    public String getSubjectCode(){
        return subjectCode;
    }

    /**
     * Sets the subject code (Org Code). Based on this code, subject are description will be displayed
     * for CO search,
     *
     * @see #setSubjectCodeDescription(String)
     * @param subjectCode Subject Code
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getInputCode(){
        return inputCode;
    }

    public void setInputCode(String inputCode){
        this.inputCode = inputCode;
    }

    public String getSelectedOfferingAction() {
        return selectedOfferingAction;
    }

    public void setSelectedOfferingAction(String selectedOfferingAction) {
        this.selectedOfferingAction = selectedOfferingAction;
    }

    public String getNoOfActivityOfferings() {
        return noOfActivityOfferings;
    }

    public void setNoOfActivityOfferings(String noOfActivityOfferings) {
        this.noOfActivityOfferings = noOfActivityOfferings;
    }

    @Override
    public String getCourseOfferingId() {
        return getCurrentCourseOfferingWrapper().getCourseOfferingId();
    }

    public List<ActivityOfferingWrapper> getActivityWrapperList() {
        return activityWrapperList;
    }

    public void setActivityWrapperList(List<ActivityOfferingWrapper> activityWrapperList) {
        this.activityWrapperList = activityWrapperList;
    }

    public List<ActivityOfferingWrapper> getSelectedToDeleteList() {
        return selectedToDeleteList;
    }

    public void setSelectedToDeleteList(List<ActivityOfferingWrapper> selectedToDeleteList) {
        this.selectedToDeleteList = selectedToDeleteList;
    }

    public List<ActivityOfferingWrapper> getSelectedToCSRList() {
        return selectedToCSRList;
    }

    public void setSelectedToCSRList(List<ActivityOfferingWrapper> selectedToCSRList) {
        this.selectedToCSRList = selectedToCSRList;
    }

    public String getFormatIdForNewAO() {
        return formatIdForNewAO;
    }

    public void setFormatIdForNewAO(String formatIdForNewAO) {
        this.formatIdForNewAO = formatIdForNewAO;
    }

    public String getActivityIdForNewAO() {
        return activityIdForNewAO;
    }

    public void setActivityIdForNewAO(String activityIdForNewAO) {
        this.activityIdForNewAO = activityIdForNewAO;
    }

    public String getCoViewLinkWrapper() {
        return coViewLinkWrapper;
    }

    public void setCoViewLinkWrapper(String coViewLinkWrapper) {
        this.coViewLinkWrapper = coViewLinkWrapper;
    }

    public CourseOfferingCopyWrapper getCourseOfferingCopyWrapper() {
        return courseOfferingCopyWrapper;
    }

    public void setCourseOfferingCopyWrapper(CourseOfferingCopyWrapper courseOfferingCopyWrapper) {
        this.courseOfferingCopyWrapper = courseOfferingCopyWrapper;
    }

    /**
     * This has its ref at view xml.
     *
     * @see #setSubjectCodeDescription(String)
     * @return subjectCodeDescription
     */
    @SuppressWarnings("unused")
    public String getSubjectCodeDescription() {
        return StringEscapeUtils.escapeJavaScript( subjectCodeDescription );
    }

    /**
     * Sets the subject code. This will be displayed when the result set is only Course Offerings
     *
     * @param subjectCodeDescription Subject Code Description
     */
    public void setSubjectCodeDescription(String subjectCodeDescription) {
        this.subjectCodeDescription = StringEscapeUtils.unescapeJavaScript( subjectCodeDescription ); // reverse encoding obtained from getter
    }

    public String getToBeScheduledCourseOfferingsUI() {
        return toBeScheduledCourseOfferingsUI;
    }

    public void setToBeScheduledCourseOfferingsUI(String toBeScheduledCourseOfferingsUI) {
        this.toBeScheduledCourseOfferingsUI = toBeScheduledCourseOfferingsUI;
    }

    public int getToBeScheduledCourseOfferingsCount() {
        return toBeScheduledCourseOfferingsCount;
    }

    public void setToBeScheduledCourseOfferingsCount(int toBeScheduledCourseOfferingsCount) {
        this.toBeScheduledCourseOfferingsCount = toBeScheduledCourseOfferingsCount;
    }

    public boolean isSelectedIllegalAOInDeletion() {
        return selectedIllegalAOInDeletion;
    }

    public void setSelectedIllegalAOInDeletion(boolean selectedIllegalAOInDeletion) {
        this.selectedIllegalAOInDeletion = selectedIllegalAOInDeletion;
    }

    public boolean isSelectedIllegalAOInCSR() {
        return selectedIllegalAOInCSR;
    }

    public void setSelectedIllegalAOInCSR(boolean selectedIllegalAOInCSR) {
        this.selectedIllegalAOInCSR = selectedIllegalAOInCSR;
    }

    public String getActionCSR() {
        return actionCSR;
    }

    public void setActionCSR(String actionCSR) {
        this.actionCSR = actionCSR;
    }

    public boolean isWithinPortal() {
        return withinPortal;
    }

    public void setWithinPortal(boolean withinPortal) {
        this.withinPortal = withinPortal;
    }

    public String getAdminOrg() {
        return adminOrg;
    }

    public void setAdminOrg(String adminOrg) {
        this.adminOrg = adminOrg;
    }

    public List<CourseOfferingListSectionWrapper> getCourseOfferingResultList() {
        return courseOfferingResultList;
    }

    public void setCourseOfferingResultList(List<CourseOfferingListSectionWrapper> courseOfferingResultList) {
        this.courseOfferingResultList = courseOfferingResultList;
    }

    public List<ActivityOfferingClusterWrapper> getClusterResultList() {
        return clusterResultList;
    }

    public void setClusterResultList(List<ActivityOfferingClusterWrapper> clusterResultList) {
        this.clusterResultList = clusterResultList;
    }

    public List<RegistrationGroupWrapper> getRgResultList() {
        return rgResultList;
    }

    public void setRgResultList(List<RegistrationGroupWrapper> rgResultList) {
        this.rgResultList = rgResultList;
    }

    public boolean getEditAuthz(){
        return editAuthz;
    }
    public void setEditAuthz(boolean editAuthz){
        this.editAuthz=editAuthz;
    }

    public String getSocState() {
        return socState;
    }

    public void setSocState(String socState) {
        this.socState = socState;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isEnableAddButton() {
        return enableAddButton;
    }

    public void setEnableAddButton(boolean enableAddButton) {
        this.enableAddButton = enableAddButton;
    }

    public boolean isEnableMoveAOButton() {
        return enableMoveAOButton;
    }

    public void setEnableMoveAOButton(boolean enableMoveAOButton) {
        this.enableMoveAOButton = enableMoveAOButton;
    }

    public boolean isEnableAddClusterButton() {
        return enableAddClusterButton;
    }

    public void setEnableAddClusterButton(boolean enableAddClusterButton) {
        this.enableAddClusterButton = enableAddClusterButton;
    }

    public String getSocStateKey() {
        return socStateKey;
    }

    public void setSocStateKey(String socStateKey) {
        this.socStateKey = socStateKey;
    }

    public String getSocSchedulingStateKey() {
        return socSchedulingStateKey;
    }

    public void setSocSchedulingStateKey(String socSchedulingStateKey) {
        this.socSchedulingStateKey = socSchedulingStateKey;
    }

    public CourseOfferingWrapper getPreviousCourseOfferingWrapper() {
        return previousCourseOfferingWrapper;
    }

    public void setPreviousCourseOfferingWrapper(CourseOfferingWrapper previousCourseOfferingWrapper) {
        this.previousCourseOfferingWrapper = previousCourseOfferingWrapper;
    }

    public CourseOfferingWrapper getNextCourseOfferingWrapper() {
        return nextCourseOfferingWrapper;
    }

    public void setNextCourseOfferingWrapper(CourseOfferingWrapper nextCourseOfferingWrapper) {
        this.nextCourseOfferingWrapper = nextCourseOfferingWrapper;
    }

    public CourseOfferingWrapper getCurrentCourseOfferingWrapper() {
        return currentCourseOfferingWrapper;
    }

    public void setCurrentCourseOfferingWrapper(CourseOfferingWrapper currentCourseOfferingWrapper) {
        this.currentCourseOfferingWrapper = currentCourseOfferingWrapper;
    }

    public List<CourseOfferingListSectionWrapper> getSelectedCoToDeleteList() {
        return selectedCoToDeleteList;
    }

    public void setSelectedCoToDeleteList(List<CourseOfferingListSectionWrapper> selectedCoToDeleteList) {
        this.selectedCoToDeleteList = selectedCoToDeleteList;
    }

    public int getTotalAOsToBeDeleted() {
        return totalAOsToBeDeleted;
    }

    public void setTotalAOsToBeDeleted(int totalAOsToBeDeleted) {
        this.totalAOsToBeDeleted = totalAOsToBeDeleted;
    }

    public boolean isCrossListedCO() {
        return isCrossListedCO;
    }

    public void setCrossListedCO(boolean crossListedCO) {
        this.isCrossListedCO = crossListedCO;
    }

    public Date getTermClassStartDate() {
        return termClassStartDate;
    }

    public void setTermClassStartDate(Date termClassStartDate) {
        this.termClassStartDate = termClassStartDate;
    }

    public String getPrivateClusterNamePopover() {
        return privateClusterNamePopover;
    }

    public void setPrivateClusterNamePopover(String privateClusterNamePopover) {
        this.privateClusterNamePopover = privateClusterNamePopover;
    }

    public String getPublishedClusterNamePopover() {
        return publishedClusterNamePopover;
    }

    public void setPublishedClusterNamePopover(String publishedClusterNamePopover) {
        this.publishedClusterNamePopover = publishedClusterNamePopover;
    }


    public String getPrivateClusterNameForRenamePopover() {
        return privateClusterNameForRenamePopover;
    }

    public void setPrivateClusterNameForRenamePopover(String privateClusterNameForRenamePopover) {
        this.privateClusterNameForRenamePopover = privateClusterNameForRenamePopover;
    }

    public String getPublishedClusterNameForRenamePopover() {
        return publishedClusterNameForRenamePopover;
    }

    public void setPublishedClusterNameForRenamePopover(String publishedClusterNameForRenamePopover) {
        this.publishedClusterNameForRenamePopover = publishedClusterNameForRenamePopover;
    }

    public String getPrivateClusterNameForMovePopover() {
        return privateClusterNameForMovePopover;
    }

    public void setPrivateClusterNameForMovePopover(String privateClusterNameForMovePopover) {
        this.privateClusterNameForMovePopover = privateClusterNameForMovePopover;
    }

    public String getPublishedClusterNameForMovePopover() {
        return publishedClusterNameForMovePopover;
    }

    public void setPublishedClusterNameForMovePopover(String publishedClusterNameForMovePopover) {
        this.publishedClusterNameForMovePopover = publishedClusterNameForMovePopover;
    }

    public String getFormatOfferingIdForViewRG() {
        return formatOfferingIdForViewRG;
    }

    public void setFormatOfferingIdForViewRG(String formatOfferingIdForViewRG) {
        this.formatOfferingIdForViewRG = formatOfferingIdForViewRG;
    }

    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    public Map<String,FormatOfferingInfo> getFoId2aoTypeMap() {
        return foId2aoTypeMap;
    }

    public void setFoId2aoTypeMap(Map<String, FormatOfferingInfo> foId2aoTypeMap) {
        this.foId2aoTypeMap = foId2aoTypeMap;
    }

    public boolean isDisableMoveButtonForMoveAOCPopOver() {
        return disableMoveButtonForMoveAOCPopOver;
    }

    public void setDisableMoveButtonForMoveAOCPopOver(boolean disableMoveButtonForMoveAOCPopOver) {
        this.disableMoveButtonForMoveAOCPopOver = disableMoveButtonForMoveAOCPopOver;
    }

    public String getClusterIdForAOMove() {
        return clusterIdForAOMove;
    }

    public void setClusterIdForAOMove(String clusterIdForAOMove) {
        this.clusterIdForAOMove = clusterIdForAOMove;
    }

    public String getSelectedFOIDForAOMove() {
        return selectedFOIDForAOMove;
    }

    public void setSelectedFOIDForAOMove(String selectedFOIDForAOMove) {
        this.selectedFOIDForAOMove = selectedFOIDForAOMove;
    }

    public String getSelectedFONameForAOMove() {
        return selectedFONameForAOMove;
    }

    public void setSelectedFONameForAOMove(String selectedFONameForAOMove) {
        this.selectedFONameForAOMove = selectedFONameForAOMove;
    }

    public boolean isHasMoreThanOneFormat() {
        return hasMoreThanOneFormat;
    }

    public void setHasMoreThanOneFormat(boolean hasMoreThanOneFormat) {
        this.hasMoreThanOneFormat = hasMoreThanOneFormat;
    }

    public boolean isHasMoreThanOneCluster() {
        return hasMoreThanOneCluster;
    }

    public void setHasMoreThanOneCluster(boolean hasMoreThanOneCluster) {
        this.hasMoreThanOneCluster = hasMoreThanOneCluster;
    }

    public int getNumOfCrossListedCosToDelete() {
        return numOfCrossListedCosToDelete;
    }

    public void setNumOfCrossListedCosToDelete(int numOfCrossListedCosToDelete) {
        this.numOfCrossListedCosToDelete = numOfCrossListedCosToDelete;
    }

    public boolean isJointDefinedCo() {
        return isJointDefinedCo;
    }

    public void setJointDefinedCo(boolean jointDefinedCo) {
        isJointDefinedCo = jointDefinedCo;
    }

    public boolean isJointDefinedCoOnly() {
        return isJointDefinedCoOnly;
    }

    public void setJointDefinedCoOnly(boolean jointDefinedCoOnly) {
        isJointDefinedCoOnly = jointDefinedCoOnly;
    }

    public Integer getNumOfJointDefinedCosToDelete() {
        return numOfJointDefinedCosToDelete;
    }

    public void setNumOfJointDefinedCosToDelete(Integer numOfJointDefinedCosToDelete) {
        this.numOfJointDefinedCosToDelete = numOfJointDefinedCosToDelete;
    }

    public boolean isColocatedCO() {
        return isColocatedCO;
    }

    public void setColocatedCO(boolean colocatedCO) {
        isColocatedCO = colocatedCO;
    }

    public boolean isColocatedCoOnly() {
        return isColocatedCoOnly;
    }

    public void setColocatedCoOnly(boolean colocatedCoOnly) {
        isColocatedCoOnly = colocatedCoOnly;
    }

    public Integer getNumOfColocatedCosToDelete() {
        return numOfColocatedCosToDelete;
    }

    public void setNumOfColocatedCosToDelete(Integer numOfColocatedCosToDelete) {
        this.numOfColocatedCosToDelete = numOfColocatedCosToDelete;
    }

    public Integer getNumOfColocatedAosToDelete() {
        return numOfColocatedAosToDelete;
    }

    public void setNumOfColocatedAosToDelete(Integer numOfColocatedAosToDelete) {
        this.numOfColocatedAosToDelete = numOfColocatedAosToDelete;
    }

    public int getAoCount() {
        return aoCount;
    }

    public void setAoCount(int aoCount) {
        this.aoCount = aoCount;
    }

    public ActivityOfferingClusterWrapper getSelectedCluster() {
        return selectedCluster;
    }

    public void setSelectedCluster(ActivityOfferingClusterWrapper selectedCluster) {
        this.selectedCluster = selectedCluster;
    }

    public String getClusterIdForNewAO() {
        return clusterIdForNewAO;
    }

    public void setClusterIdForNewAO(String clusterIdForNewAO) {
        this.clusterIdForNewAO = clusterIdForNewAO;
    }

    public String getFormatOfferingIdForNewAO() {
        return formatOfferingIdForNewAO;
    }

    public void setFormatOfferingIdForNewAO(String formatOfferingIdForNewAO) {
        this.formatOfferingIdForNewAO = formatOfferingIdForNewAO;
    }

    public String getSelectedTabId() {
        return selectedTabId;
    }

    public void setSelectedTabId(String selectedTabId) {
        this.selectedTabId = selectedTabId;
    }

    public CourseOfferingContextBar getContextBar() {
        return contextBar;
    }

    public void setContextBar(CourseOfferingContextBar contextBar) {
        this.contextBar = contextBar;
    }

    public boolean isRequisiteLink() {
        return requisiteLink;
    }

    public void setRequisiteLink(boolean requisiteLink) {
        this.requisiteLink = requisiteLink;
    }

    public String getCsrLabel() {
        return csrLabel;
    }

    public void setCsrLabel(String csrLabel) {
        this.csrLabel = csrLabel;
    }


    public int getNumIneligibleAOsForCSR() {
        return numIneligibleAOsForCSR;
    }

    public void setNumIneligibleAOsForCSR(int numIneligibleAOsForCSR) {
        this.numIneligibleAOsForCSR = numIneligibleAOsForCSR;
    }

    public List<ExamOfferingRelationInfo> getEoRelations() {
        return eoRelations;
    }

    public void setEoRelations(List<ExamOfferingRelationInfo> eoRelations) {
        this.eoRelations = eoRelations;
    }

    public List<ExamOfferingWrapper> getExamOfferingWrapperList() {
        return examOfferingWrapperList;
    }

    public void setExamOfferingWrapperList(List<ExamOfferingWrapper> examOfferingWrapperList) {
        this.examOfferingWrapperList = examOfferingWrapperList;
    }

    public List<ExamOfferingWrapper> getExamOfferingCancelledList() {
        return examOfferingCancelledList;
    }

    public void setExamOfferingCancelledList(List<ExamOfferingWrapper> examOfferingCancelledList) {
        this.examOfferingCancelledList = examOfferingCancelledList;
    }

    public List<ExamOfferingClusterWrapper> getEoClusterResultList() {
        return eoClusterResultList;
    }

    public void setEoClusterResultList(List<ExamOfferingClusterWrapper> eoClusterResultList) {
        this.eoClusterResultList = eoClusterResultList;
    }

    public List<ExamOfferingClusterWrapper> getEoCancelClusterList() {
        return eoCancelClusterList;
    }

    public void setEoCancelClusterList(List<ExamOfferingClusterWrapper> eoCancelClusterList) {
        this.eoCancelClusterList = eoCancelClusterList;
    }

    public ExamPeriodWrapper getExamPeriodWrapper() {
        return examPeriodWrapper;
    }

    public void setExamPeriodWrapper(ExamPeriodWrapper examPeriodWrapper) {
        this.examPeriodWrapper = examPeriodWrapper;
    }
}
