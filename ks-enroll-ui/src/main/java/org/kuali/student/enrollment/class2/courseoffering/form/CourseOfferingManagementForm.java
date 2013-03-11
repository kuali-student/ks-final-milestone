package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.uif.form.KSUifForm;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseOfferingManagementForm extends KSUifForm {

    //for authorization purpose
    private String adminOrg;

    private String termCode;
    private TermInfo termInfo;

    /**
     * @see #setSubjectCode(String)
     */
    private String subjectCode;

    /**
     * @see #setSubjectCodeDescription(String)
     */
    private String subjectCodeDescription;

    /**
     * This is the user entered field data when they search for Course/Activity Offerings.
     * For a term and subjectArea/CourseOffering code, search will be performed.
     */
    private String inputCode;

    private String selectedOfferingAction;
    private String coViewLinkWrapper = "View"; // temp var to hold/store the View Details Link

    /**
     * To display the soc state
     */
    private String socState;

    /**
     * This is being used in authz to decide the toolbar permissions.
     */
    private String socStateKey;

    /**
     * FIXME: Dont think we're using this property at view xml
     */
    private String socSchedulingStateKey;

    private boolean readOnly;

    private List<ActivityOfferingWrapper> activityWrapperList;

    /**
     * FIXME: Cant we have a flag at the ActivityOfferingWrapper to handle whether the AO is selected to delete or not?
     * I dont think we need a seperate list to handle that - courseOfferingCopyWrapper
     */
    private List<ActivityOfferingWrapper> selectedToDeleteList;
    private CourseOfferingCopyWrapper courseOfferingCopyWrapper;

    private boolean isCrossListedCO;
    private boolean isColocatedCO;
    private List<CourseOfferingListSectionWrapper> selectedCoToDeleteList;
    private int totalAOsToBeDeleted = 0;
    /**
     * The courseOfferingResultList once created is unmodifiable. This had to be done because various
     * methods were illegally modifying the list throughout the app and it took me forever to track down.
     * Since the screen needs the list to be not null and the list.clear() method cannot be called we
     * have created an empty version of the list. There is a helper method to "clear"  courseOfferingResultList
     * which really just sets the "empty" list in it's place.
     */
    private List<CourseOfferingListSectionWrapper> courseOfferingResultList;
//    private List<CourseOfferingListSectionWrapper> courseOfferingResultListEmpty;  // look at definition above.

    //For Adding Activity
    private String formatIdForNewAO;
    private String activityIdForNewAO;
    private String noOfActivityOfferings;

    private CourseOfferingWrapper currentCourseOfferingWrapper;
    private CourseOfferingWrapper previousCourseOfferingWrapper;
    private CourseOfferingWrapper nextCourseOfferingWrapper;

    private String toBeScheduledCourseOfferingsUI;
    private int toBeScheduledCourseOfferingsCount;
    private boolean selectedIllegalAOInDeletion = false;

    private boolean withinPortal = true;

    private boolean editAuthz;

    private boolean enableAddButton = false;

    private Date termClassStartDate;

    public CourseOfferingManagementForm (){
        activityWrapperList = new ArrayList<ActivityOfferingWrapper>();
        selectedToDeleteList = new ArrayList<ActivityOfferingWrapper>();
        courseOfferingResultList = new ArrayList<CourseOfferingListSectionWrapper>();
//        courseOfferingResultListEmpty = new ArrayList<CourseOfferingListSectionWrapper>();
        selectedCoToDeleteList = new ArrayList<CourseOfferingListSectionWrapper>();
        setCourseOfferingCopyWrapper(null);
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
     * @return
     */
    public String getSubjectCode(){
        return subjectCode;
    }

    /**
     * Sets the subject code (Org Code). Based on this code, subject are description will be displayed
     * for CO search,
     *
     * @see #setSubjectCodeDescription(String)
     * @param subjectCode
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
     * @return
     */
    @SuppressWarnings("unused")
    public String getSubjectCodeDescription() {
        return subjectCodeDescription;
    }

    /**
     * Sets the subject code. This will be displayed when the result set is only Course Offerings
     *
     * @param subjectCodeDescription
     */
    public void setSubjectCodeDescription(String subjectCodeDescription) {
        this.subjectCodeDescription = subjectCodeDescription;
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

    public boolean isColocatedCO() {
        return isColocatedCO;
    }

    public void setColocatedCO(boolean colocatedCO) {
        isColocatedCO = colocatedCO;
    }

    public Date getTermClassStartDate() {
        return termClassStartDate;
    }

    public void setTermClassStartDate(Date termClassStartDate) {
        this.termClassStartDate = termClassStartDate;
    }

}
