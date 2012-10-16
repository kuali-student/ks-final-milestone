package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingManagementForm extends UifFormBase {

    private String termCode;
    private TermInfo termInfo;
    private String courseOfferingCode;
    private String subjectCode;
    private String subjectCodeDescription;
    private String radioSelection;
    private String inputCode;
    private String selectedOfferingAction;
    private CourseOfferingInfo theCourseOffering;
    private String coViewLinkWrapper = "View"; // temp var to hold/store the View Details Link

    private List<ActivityOfferingWrapper> activityWrapperList;
    private List<ActivityOfferingWrapper> selectedToDeleteList;
    private List<CourseOfferingEditWrapper> courseOfferingEditWrapperList;
    private CourseOfferingCopyWrapper courseOfferingCopyWrapper;

    //For View Registration Groups
    private String formatOfferingIdForViewRG;
    private String formatOfferingName;
    private List<ActivityOfferingWrapper> filteredUnassignedAOsForSelectedFO;
    private List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList;
    private boolean hasAOCluster;
    private String privateClusterName;
    private String publishedClusterName;
    private String privateClusterNameForLightBox;
    private String publishedClusterNameForLightBox;
    private String privateClusterNameForRename;
    private String publishedClusterNameForRename;

    //This is used to handle DialogGroup/Lightbox action within collection.
    //It only works within the lifecycle of each Dialog.
    private ActivityOfferingClusterWrapper selectedCluster;

    //For Adding Activity
    private String formatIdForNewAO;
    private String clusterIdIdForNewFO;
    private String clusterIdForAOMove;
    private String activityIdForNewAO;
    private String noOfActivityOfferings;

    private CourseOfferingInfo previousCourseOffering;
    private CourseOfferingInfo nextCourseOffering;
    private String previousCourseOfferingCodeUI;
    private String nextCourseOfferingCodeUI;

    private String toBeScheduledCourseOfferingsUI;
    private int toBeScheduledCourseOfferingsCount;
    private boolean selectedIllegalAOInDeletion = false;

    private boolean withinPortal = true;

    public CourseOfferingManagementForm (){
        activityWrapperList = new ArrayList<ActivityOfferingWrapper>();
        selectedToDeleteList = new ArrayList<ActivityOfferingWrapper>();
        filteredUnassignedAOsForSelectedFO = new ArrayList<ActivityOfferingWrapper>();
        filteredAOClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        courseOfferingEditWrapperList = new ArrayList<CourseOfferingEditWrapper>();
        setCourseOfferingCopyWrapper(null);
        hasAOCluster = false;
        withinPortal = true;
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
    
    public String getCourseOfferingCode(){
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode){
        this.courseOfferingCode = courseOfferingCode;
    }
    
    public String getSubjectCode(){
        return subjectCode;
    }
    
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
    public String getRadioSelection(){
        return radioSelection;
    } 
    
    public void setRadioSelection(String radioSelection){
        this.radioSelection = radioSelection;
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

    public CourseOfferingInfo getTheCourseOffering(){
        return theCourseOffering;
    }

    public void setTheCourseOffering(CourseOfferingInfo theCourseOffering)  {
        this.theCourseOffering = theCourseOffering;
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

    public List<ActivityOfferingWrapper> getFilteredUnassignedAOsForSelectedFO() {
        return filteredUnassignedAOsForSelectedFO;
    }

    public void setFilteredUnassignedAOsForSelectedFO(List<ActivityOfferingWrapper> filteredUnassignedAOsForSelectedFO) {
        this.filteredUnassignedAOsForSelectedFO = filteredUnassignedAOsForSelectedFO;
    }

    public List<ActivityOfferingClusterWrapper> getFilteredAOClusterWrapperList() {
        return filteredAOClusterWrapperList;
    }

    public void setFilteredAOClusterWrapperList(List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList) {
        this.filteredAOClusterWrapperList = filteredAOClusterWrapperList;
    }

    public String getFormatIdForNewAO() {
        return formatIdForNewAO;
    }

    public void setFormatIdForNewAO(String formatIdForNewAO) {
        this.formatIdForNewAO = formatIdForNewAO;
    }

    public String getClusterIdIdForNewFO() {
        return clusterIdIdForNewFO;
    }

    public void setClusterIdIdForNewFO(String clusterIdIdForNewFO) {
        this.clusterIdIdForNewFO = clusterIdIdForNewFO;
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

    public List<CourseOfferingEditWrapper> getCourseOfferingEditWrapperList() {
        return courseOfferingEditWrapperList;
    }

    public void setCourseOfferingEditWrapperList(List<CourseOfferingEditWrapper> courseOfferingEditWrapperList) {
        this.courseOfferingEditWrapperList = courseOfferingEditWrapperList;
    }

    public CourseOfferingCopyWrapper getCourseOfferingCopyWrapper() {
        return courseOfferingCopyWrapper;
    }

    public void setCourseOfferingCopyWrapper(CourseOfferingCopyWrapper courseOfferingCopyWrapper) {
        this.courseOfferingCopyWrapper = courseOfferingCopyWrapper;
    }

    public String getPreviousCourseOfferingCodeUI() {
        return previousCourseOfferingCodeUI;
    }

    public void setPreviousCourseOfferingCodeUI(String previousCourseOfferingCodeUI) {
        this.previousCourseOfferingCodeUI = previousCourseOfferingCodeUI;
    }

    public String getNextCourseOfferingCodeUI() {
        return nextCourseOfferingCodeUI;
    }

    public void setNextCourseOfferingCodeUI(String nextCourseOfferingCodeUI) {
        this.nextCourseOfferingCodeUI = nextCourseOfferingCodeUI;
    }

    public CourseOfferingInfo getPreviousCourseOffering() {
        return previousCourseOffering;
    }

    public void setPreviousCourseOffering(CourseOfferingInfo previousCourseOffering) {
        this.previousCourseOffering = previousCourseOffering;
        if (previousCourseOffering != null){
            setPreviousCourseOfferingCodeUI(previousCourseOffering.getCourseOfferingCode());
        }else{
            setPreviousCourseOfferingCodeUI(StringUtils.EMPTY);
        }
    }

    public CourseOfferingInfo getNextCourseOffering() {
        return nextCourseOffering;
    }

    public String getSubjectCodeDescription() {
        return subjectCodeDescription;
    }

    public void setSubjectCodeDescription(String subjectCodeDescription) {
        this.subjectCodeDescription = subjectCodeDescription;
    }

    public void setNextCourseOffering(CourseOfferingInfo nextCourseOffering) {
        this.nextCourseOffering = nextCourseOffering;
        if (nextCourseOffering != null){
            setNextCourseOfferingCodeUI(nextCourseOffering.getCourseOfferingCode());
        }else{
            setNextCourseOfferingCodeUI(StringUtils.EMPTY);
        }
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

    public String getPrivateClusterName() {
        return privateClusterName;
    }

    public void setPrivateClusterName(String privateClusterName) {
        this.privateClusterName = privateClusterName;
    }

    public String getPublishedClusterName() {
        return publishedClusterName;
    }

    public void setPublishedClusterName(String publishedClusterName) {
        this.publishedClusterName = publishedClusterName;
    }

    public boolean isHasAOCluster() {
        return hasAOCluster;
    }

    public void setHasAOCluster(boolean hasAOCluster) {
        this.hasAOCluster = hasAOCluster;
    }

    public String getPrivateClusterNameForLightBox() {
        return privateClusterNameForLightBox;
    }

    public void setPrivateClusterNameForLightBox(String privateClusterNameForLightBox) {
        this.privateClusterNameForLightBox = privateClusterNameForLightBox;
    }

    public String getPublishedClusterNameForLightBox() {
        return publishedClusterNameForLightBox;
    }

    public void setPublishedClusterNameForLightBox(String publishedClusterNameForLightBox) {
        this.publishedClusterNameForLightBox = publishedClusterNameForLightBox;
    }

    public ActivityOfferingClusterWrapper getSelectedCluster() {
        return selectedCluster;
    }

    public void setSelectedCluster(ActivityOfferingClusterWrapper selectedCluster) {
        this.selectedCluster = selectedCluster;
    }

    public String getPrivateClusterNameForRename() {
        return privateClusterNameForRename;
    }

    public void setPrivateClusterNameForRename(String privateClusterNameForRename) {
        this.privateClusterNameForRename = privateClusterNameForRename;
    }

    public String getPublishedClusterNameForRename() {
        return publishedClusterNameForRename;
    }

    public void setPublishedClusterNameForRename(String publishedClusterNameForRename) {
        this.publishedClusterNameForRename = publishedClusterNameForRename;
    }

    public String getClusterIdForAOMove() {
        return clusterIdForAOMove;
    }

    public void setClusterIdForAOMove(String clusterIdForAOMove) {
        this.clusterIdForAOMove = clusterIdForAOMove;
    }

    public boolean isWithinPortal() {
        return withinPortal;
    }

    public void setWithinPortal(boolean withinPortal) {
        this.withinPortal = withinPortal;
    }
}
