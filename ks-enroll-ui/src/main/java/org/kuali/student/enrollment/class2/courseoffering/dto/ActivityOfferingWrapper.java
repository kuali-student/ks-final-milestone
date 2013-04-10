package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ColocatedOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.CourseCrossListing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main model object in Edit AO view.
 *
 * @see ColocatedActivity
 *
 */
public class ActivityOfferingWrapper implements Serializable{
    //added for ARG
    private ActivityOfferingClusterInfo aoCluster;
    private String aoClusterName;
    private String aoClusterID;

    private ActivityOfferingInfo aoInfo;
    private FormatOfferingInfo formatOffering;
    private TermInfo term;
    private List<OfferingInstructorWrapper> instructors;
    private List<ScheduleComponentWrapper> scheduleComponentWrappers;
    private List<SeatPoolWrapper> seatpools;
    private boolean readOnlyView;
    private boolean isChecked;
    private boolean isCheckedByCluster;
    private String courseOfferingId;
    // Tanveer 06/13/2012
    private String stateName;
    private String typeName;
    private String typeKey;

    private String termName;

    private String formatOfferingName;

    private Date termRegStartDate;

    // Tanveer 06/27/2012
    private String waitListLevelTypeKey;
    private String waitListTypeKey;
    private boolean hasWaitList;
    private String waitListText = "";
    private String toolTipText = "";

    private String instructorNameHighestPercentEffort = "";

    private String firstInstructorDisplayName;
    private String instructorDisplayNames;

    private String courseOfferingCode = "";

    private List<ScheduleWrapper> actualScheduleComponents;
    private List<ScheduleWrapper> requestedScheduleComponents;
//    private List<ScheduleWrapper> revisedScheduleRequestComponents;
    private ScheduleWrapper newScheduleRequest;

    private ScheduleRequestInfo scheduleRequestInfo;
    private ScheduleInfo scheduleInfo;
    private SocInfo socInfo;

    private String startTimeDisplay = "";
    private String endTimeDisplay = "";
    private String daysDisplayName = "";
    private String buildingName = "";
    private String roomName = "";
    private String tbaDisplayName = "";
    private String colocatedAoInfo = "";

    private boolean schedulesModified;

    private String adminOrg;

      //hidden columns for toolbar
    private boolean enableApproveButton = false;
    private boolean enableDraftButton = false;
    private boolean enableSuspendButton = false;
    private boolean enableCancelButton = false;
    private boolean enableReinstateButton = false;
    private boolean enableDeleteButton = false;
    private boolean enableCopyAOActionLink = false;
    private boolean enableEditAOActionLink = false;

    private boolean colocatedAO;
    private List<ColocatedActivity> colocatedActivities;
    private boolean maxEnrollmentShared;
    private int sharedMaxEnrollment;

    private ColocatedOfferingSetInfo colocatedOfferingSetInfo;

    private EditRenderHelper editRenderHelper;
    private boolean isPartOfColoSetOnLoadAlready;
    private boolean isSendRDLsToSchedulerAfterMSE;

    //This is needed to display the cross listed courses
    private CourseInfo course;

    public ActivityOfferingWrapper(){
        aoInfo = new ActivityOfferingInfo();
        instructors = new ArrayList<OfferingInstructorWrapper>();
        seatpools = new ArrayList<SeatPoolWrapper>();
        aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
        scheduleComponentWrappers = new ArrayList<ScheduleComponentWrapper>();
        this.setReadOnlyView(false);
        this.setIsChecked(false);
        actualScheduleComponents = new ArrayList<ScheduleWrapper>();
        requestedScheduleComponents = new ArrayList<ScheduleWrapper>();
//        revisedScheduleRequestComponents = new ArrayList<ScheduleWrapper>();
        newScheduleRequest = new ScheduleWrapper();
        colocatedActivities = new ArrayList<ColocatedActivity>();
        maxEnrollmentShared = true;
        editRenderHelper = new EditRenderHelper();
        colocatedOfferingSetInfo = new ColocatedOfferingSetInfo();
    }

    public ActivityOfferingWrapper(ActivityOfferingInfo info){
        this();
        aoInfo = info;
        instructors = new ArrayList<OfferingInstructorWrapper>();
        seatpools = new ArrayList<SeatPoolWrapper>();
    }

    public ActivityOfferingClusterInfo getAoCluster() {
        return aoCluster;
    }

    public void setAoCluster(ActivityOfferingClusterInfo aoCluster) {
        this.aoCluster = aoCluster;
    }

    public String getAoClusterName() {
        return aoClusterName;
    }

    public void setAoClusterName(String aoClusterName) {
        this.aoClusterName = aoClusterName;
    }

    public String getAoClusterID() {
        return aoClusterID;
    }

    public void setAoClusterID(String aoClusterID) {
        this.aoClusterID = aoClusterID;
    }

    public boolean isEnableApproveButton() {
        return enableApproveButton;
    }

    public void setEnableApproveButton(boolean enableApproveButton) {
        this.enableApproveButton = enableApproveButton;
    }

    public boolean isEnableDraftButton() {
        return enableDraftButton;
    }

    public void setEnableDraftButton(boolean enableDraftButton) {
        this.enableDraftButton = enableDraftButton;
    }

    public boolean isEnableSuspendButton() {
        return enableSuspendButton;
    }

    public void setEnableSuspendButton(boolean enableSuspendButton) {
        this.enableSuspendButton = enableSuspendButton;
    }

    public boolean isEnableCancelButton() {
        return enableCancelButton;
    }

    public void setEnableCancelButton(boolean enableCancelButton) {
        this.enableCancelButton = enableCancelButton;
    }

    public boolean isEnableReinstateButton() {
        return enableReinstateButton;
    }

    public void setEnableReinstateButton(boolean enableReinstateButton) {
        this.enableReinstateButton = enableReinstateButton;
    }

    public boolean isEnableDeleteButton() {
        return enableDeleteButton;
    }

    public void setEnableDeleteButton(boolean enableDeleteButton) {
        this.enableDeleteButton = enableDeleteButton;
    }

    public boolean isEnableCopyAOActionLink() {
        return enableCopyAOActionLink;
    }

    public void setEnableCopyAOActionLink(boolean enableCopyAOActionLink) {
        this.enableCopyAOActionLink = enableCopyAOActionLink;
    }

    public boolean isEnableEditAOActionLink() {
        return enableEditAOActionLink;
    }

    public void setEnableEditAOActionLink(boolean enableEditAOActionLink) {
        this.enableEditAOActionLink = enableEditAOActionLink;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String courseOfferingTitle;
    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }

    private String credits = "";
    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    private String activityCode = "";

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    private String abbreviatedCourseType = "";

    public String getAbbreviatedCourseType() {
        return abbreviatedCourseType;
    }

    public void setAbbreviatedCourseType(String abbreviatedCourseType) {
        this.abbreviatedCourseType= abbreviatedCourseType;
    }

    private String termDisplayString = "";

    public String getTermDisplayString() {
        return termDisplayString;
    }

    public void setTermDisplayString(String termDisplayString) {
        this.termDisplayString = termDisplayString;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public String getWaitListText() {
        return waitListText;
    }

    public void setWaitListText(String waitListText) {
        this.waitListText = waitListText;
    }



    public boolean getHasWaitList() {
        return hasWaitList;
    }

    public String getTbaDisplayName() {
        return tbaDisplayName;
    }

    public void setTbaDisplayName(boolean tba,boolean appendForDisplay) {
        if (appendForDisplay){
            tbaDisplayName =  tbaDisplayName + "<br>" + (tba ? "TBA" : StringUtils.EMPTY);
        } else {
            tbaDisplayName = StringUtils.EMPTY;
            if (tba){
                tbaDisplayName =  "TBA";
            }
        }
    }

    public void setHasWaitList(boolean hasWaitList) {
        this.hasWaitList = hasWaitList;
    }

    public String getWaitListLevelTypeKey() {
        return waitListLevelTypeKey;
    }
    public void setWaitListLevelTypeKey(String waitListLevelTypeKey) {
        this.waitListLevelTypeKey = waitListLevelTypeKey;
    }
    public String getWaitListTypeKey() {
        return waitListTypeKey;
    }
    public void setWaitListTypeKey(String waitListTypeKey) {
        this.waitListTypeKey = waitListTypeKey;
    }

    public FormatOfferingInfo getFormatOffering() {
        return formatOffering;
    }

    public void setFormatOffering(FormatOfferingInfo formatOffering) {
        this.formatOffering = formatOffering;
    }

    public TermInfo getTerm() {
        return term;
    }

    public void setTerm(TermInfo term) {
        this.term = term;
    }

    public String getTermId(){
        if (term != null){
            return term.getId();
        }
        return StringUtils.EMPTY;
    }

    public ActivityOfferingInfo getAoInfo() {
        return aoInfo;
    }

    public void setAoInfo(ActivityOfferingInfo aoInfo) {
        this.aoInfo = aoInfo;
    }

    public List<ScheduleComponentWrapper> getScheduleComponentWrappers() {
        return scheduleComponentWrappers;
    }

    public void setScheduleComponentWrappers(List<ScheduleComponentWrapper> scheduleComponentWrappers) {
        this.scheduleComponentWrappers = scheduleComponentWrappers;
    }

    public boolean getReadOnlyView() {
        return readOnlyView;
    }

    public void setReadOnlyView(boolean readOnlyView) {
        this.readOnlyView = readOnlyView;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

    public boolean getIsCheckedByCluster() {
        return isCheckedByCluster;
    }

    public void setIsCheckedByCluster(boolean checkedByCluster) {
        isCheckedByCluster = checkedByCluster;
    }


    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public List<OfferingInstructorWrapper> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<OfferingInstructorWrapper> instructors) {
        this.instructors = instructors;
    }

    public List<SeatPoolWrapper> getSeatpools() {
        return seatpools;
    }

    public void setSeatpools(List<SeatPoolWrapper> seatpools) {
        this.seatpools = seatpools;
    }

    public String getFirstInstructorDisplayName() {
        return firstInstructorDisplayName;
    }

    public void setFirstInstructorDisplayName(String firstInstructorDisplayName) {
        this.firstInstructorDisplayName = firstInstructorDisplayName;
    }

    public String getId() {
        return aoInfo.getId();
    }

    public void setId(String id) {
        aoInfo.setId(id);
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    public String getIsMaxEnrollmentEstimateUI(){
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(aoInfo.getIsMaxEnrollmentEstimate()));
    }

    public String getIsEvaluatedUI(){
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(aoInfo.getIsEvaluated()));
    }

    public String getIsHonorsOfferingUI(){
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(aoInfo.getIsHonorsOffering()));
    }

    public String getInstructorNameHighestPercentEffort() {
        return instructorNameHighestPercentEffort;
    }

    public void setInstructorNameHighestPercentEffort(String instructorNameHighestPercentEffort) {
        this.instructorNameHighestPercentEffort = instructorNameHighestPercentEffort;
    }

    public boolean isLegalToDelete() {
        if (StringUtils.equals(aoInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY) ||
                StringUtils.equals(aoInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY) ||
                StringUtils.equals(aoInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_SUBMITTED_KEY) ||
                StringUtils.equals(aoInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY) ||
                StringUtils.equals(aoInfo.getStateKey(), LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY) ) {
            return true;
        }
        return false;
    }

    public List<ScheduleWrapper> getActualScheduleComponents() {
        if (actualScheduleComponents == null){
            actualScheduleComponents = new ArrayList<ScheduleWrapper>();
        }
        return actualScheduleComponents;
    }

    public void setActualScheduleComponents(List<ScheduleWrapper> actualScheduleComponents) {
        this.actualScheduleComponents = actualScheduleComponents;
    }

    public List<ScheduleWrapper> getRequestedScheduleComponents() {
        if (requestedScheduleComponents == null){
            requestedScheduleComponents = new ArrayList<ScheduleWrapper>();
        }
        return requestedScheduleComponents;
    }

    public void setRequestedScheduleComponents(List<ScheduleWrapper> requestedScheduleComponents) {
        this.requestedScheduleComponents = requestedScheduleComponents;
    }

    public ScheduleWrapper getNewScheduleRequest() {
        return newScheduleRequest;
    }

    public void setNewScheduleRequest(ScheduleWrapper newScheduleRequest) {
        this.newScheduleRequest = newScheduleRequest;
    }

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay,boolean appendForDisplay) {
        if (appendForDisplay){
            this.startTimeDisplay = this.startTimeDisplay + "<br>" + StringUtils.defaultString(startTimeDisplay);
        }else{
            this.startTimeDisplay = StringUtils.defaultString(startTimeDisplay);
        }

    }

    public void setStartTimeDisplay(String startTimeDisplay, boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.startTimeDisplay)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.startTimeDisplay = this.startTimeDisplay + "<br><span " + cssClass + " >" + startTimeDisplay + "</span>";
        }else{
            this.startTimeDisplay = "<span " + cssClass + " >" + startTimeDisplay + "</span>";
        }
    }

    public String getInstructorDisplayNames() {
        return instructorDisplayNames;
    }

    public void setInstructorDisplayNames(String instructorDisplayNames) {
        this.instructorDisplayNames = instructorDisplayNames;
    }

    public void setInstructorDisplayNames(String instructorDisplayNames,boolean appendForDisplay) {
        if (appendForDisplay && this.instructorDisplayNames!=null){
            this.instructorDisplayNames = this.instructorDisplayNames + "<br>" + StringUtils.defaultString(instructorDisplayNames);
        }else{
            this.instructorDisplayNames = StringUtils.defaultString(instructorDisplayNames);
        }

    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay) {
        if (appendForDisplay){
            this.endTimeDisplay = this.endTimeDisplay + "<br>" + StringUtils.defaultString(endTimeDisplay);
        }else{
            this.endTimeDisplay = StringUtils.defaultString(endTimeDisplay);
        }
    }

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.endTimeDisplay)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.endTimeDisplay = this.endTimeDisplay + "<br><span " + cssClass + " >" + endTimeDisplay + "</span>";
        }else{
            this.endTimeDisplay = "<span " + cssClass + " >" + endTimeDisplay + "</span>";
        }
    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay) {
        if (appendForDisplay){
            this.daysDisplayName = this.daysDisplayName + "<br>" + StringUtils.defaultString(daysDisplayName);
        }else{
            this.daysDisplayName = StringUtils.defaultString(daysDisplayName);
        }
    }

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.daysDisplayName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.daysDisplayName = this.daysDisplayName + "<br><span " + cssClass + " >" + daysDisplayName + "</span>";
        }else{
            this.daysDisplayName = "<span " + cssClass + " >" + daysDisplayName + "</span>";
        }
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName,boolean appendForDisplay) {
        if (appendForDisplay){
            this.buildingName = this.buildingName + "<br>" + StringUtils.defaultString(buildingName);
        }else{
            this.buildingName = StringUtils.defaultString(buildingName);
        }
    }
    public void setBuildingName(String buildingName,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.buildingName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.buildingName = this.buildingName + "<br><span " + cssClass + " >" + buildingName + "</span>";
        }else{
            this.buildingName = "<span " + cssClass + " >" + buildingName + "</span>";
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName,boolean appendForDisplay) {
        if (appendForDisplay){
            this.roomName = this.roomName + "<br>" + StringUtils.defaultString(roomName);
        }else{
            this.roomName = StringUtils.defaultString(roomName);
        }
    }
    public void setRoomName(String roomName,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.roomName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.roomName = this.roomName + "<br><span " + cssClass + " >" + roomName + "</span>";
        }else{
            this.roomName = "<span " + cssClass + " >" + roomName + "</span>";
        }
    }

    public ScheduleRequestInfo getScheduleRequestInfo() {
        return scheduleRequestInfo;
    }

    public void setScheduleRequestInfo(ScheduleRequestInfo scheduleRequestInfo) {
        this.scheduleRequestInfo = scheduleRequestInfo;
    }

    public ScheduleInfo getScheduleInfo() {
        return scheduleInfo;
    }

    public void setScheduleInfo(ScheduleInfo scheduleInfo) {
        this.scheduleInfo = scheduleInfo;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public boolean isSendRDLsToSchedulerAfterMSE() {
        return isSendRDLsToSchedulerAfterMSE;
    }

    public void setSendRDLsToSchedulerAfterMSE(boolean sendRDLsToSchedulerAfterMSE) {
        isSendRDLsToSchedulerAfterMSE = sendRDLsToSchedulerAfterMSE;
    }

    public boolean isSchedulesModified() {
       return schedulesModified;
    }

    public void setSchedulesModified(boolean schedulesModified) {
       this.schedulesModified = schedulesModified;
    }

    public SocInfo getSocInfo() {
        return socInfo;
    }

    public void setSocInfo(SocInfo socInfo) {
        this.socInfo = socInfo;
    }

    public String getAdminOrg(){
        return adminOrg;
    }
    public void setAdminOrg(String adminOrg){
        this.adminOrg=adminOrg;
    }

    public Date getTermRegStartDate() {
        return termRegStartDate;
    }

    public void setTermRegStartDate(Date termRegStartDate) {
        this.termRegStartDate = termRegStartDate;
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public String getColocatedAoInfo() {
        return colocatedAoInfo;
    }

    public void setColocatedAoInfo(String colocatedAoInfo) {
        this.colocatedAoInfo = colocatedAoInfo;
    }

    /**
     * This method returns a list of comma seperated alternate course codes.
     * This is used in create and edit course offerings screen.
     * @return
     */
    @SuppressWarnings("unused")
    public String getCrossListedCourseCodes(){
        StringBuilder builder = new StringBuilder();
        if (course != null){
            for (CourseCrossListing crossListing : course.getCrossListings()){
                builder.append(crossListing.getCode() + ", ");
            }
        }
        return StringUtils.removeEnd(builder.toString(), ", ");
    }

    /**
     * @see #setColocatedAO(boolean)
     * @return
     */
    public boolean isColocatedAO() {
        return colocatedAO;
    }

    /**
     * Whether this AO is part of a colocated set
     *
     * @param colocatedAO
     */
    public void setColocatedAO(boolean colocatedAO) {
        this.colocatedAO = colocatedAO;
    }

    public List<ColocatedActivity> getColocatedActivities() {
        return colocatedActivities;
    }

    public void setColocatedActivities(List<ColocatedActivity> colocatedActivities) {
        this.colocatedActivities = colocatedActivities;
    }

    public boolean isMaxEnrollmentShared() {
        return maxEnrollmentShared;
    }

    public void setMaxEnrollmentShared(boolean maxEnrollmentShared) {
        this.maxEnrollmentShared = maxEnrollmentShared;
    }

    public int getSharedMaxEnrollment() {
        return sharedMaxEnrollment;
    }

    public void setSharedMaxEnrollment(int sharedMaxEnrollment) {
        this.sharedMaxEnrollment = sharedMaxEnrollment;
    }

    /**
     * This method return a colocated AO code for current course. This will
     * be displayed as the tooltip (if colocated AO exists) at manage and delete AO screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getColocatedAoInfoUI(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("This activity is colocated with:<br>");
        buffer.append(colocatedAoInfo + "<br>");

        return StringUtils.removeEnd(buffer.toString(),"<br>");
    }

    public ColocatedOfferingSetInfo getColocatedOfferingSetInfo() {
        return colocatedOfferingSetInfo;
    }

    public void setColocatedOfferingSetInfo(ColocatedOfferingSetInfo colocatedOfferingSetInfo) {
        this.colocatedOfferingSetInfo = colocatedOfferingSetInfo;
    }

    public boolean isPartOfColoSetOnLoadAlready() {
        return isPartOfColoSetOnLoadAlready;
    }

    public void setPartOfColoSetOnLoadAlready(boolean isPartOfColoSetOnLoadAlready) {
        this.isPartOfColoSetOnLoadAlready = isPartOfColoSetOnLoadAlready;
    }

    public boolean isSchedulingCompleted(){
        if (getSocInfo() != null){
            if (StringUtils.equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED,getSocInfo().getSchedulingStateKey())){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the Edit Helper.
     *
     * @return
     */
    public EditRenderHelper getEditRenderHelper() {
        return editRenderHelper;
    }

    /**
     * Helper to render the colocated activites in Edit AO screen.
     */
    public class EditRenderHelper implements Serializable {

        private List<ColocatedActivity> manageSeperateEnrollmentList;

        private boolean scheduleEditInProgress;
        private boolean isPersistedRDLsExists;

        public EditRenderHelper(){
            manageSeperateEnrollmentList = new ArrayList<ColocatedActivity>();
        }

        /**
         * @see #setManageSeperateEnrollmentList(java.util.List)
         * @return
         */
        public List<ColocatedActivity> getManageSeperateEnrollmentList() {
            return manageSeperateEnrollmentList;
        }

        /**
         * If the user is going to manage the enrollment list seperately for each activity offerings in a
         * cross listed set, this list is used to display that section at the ui.
         *
         * It's just a seperate list but it holds the same reference elements from {@link #setColocatedActivities(java.util.List)} plus
         * the current AO.
         *
         * @param manageSeperateEnrollmentList
         */
        @SuppressWarnings("unused")
        public void setManageSeperateEnrollmentList(List<ColocatedActivity> manageSeperateEnrollmentList) {
            this.manageSeperateEnrollmentList = manageSeperateEnrollmentList;
        }

        public boolean isPersistedRDLsExists() {
            return isPersistedRDLsExists;
        }

        public void setPersistedRDLsExists(boolean persistedRDLsExists) {
            isPersistedRDLsExists = persistedRDLsExists;
        }

        public String getColocatedActivitiesAsString(){
            StringBuffer s = new StringBuffer();
            for (ColocatedActivity colo : getColocatedActivities()){
                s.append(colo.getEditRenderHelper().getCode() + ", ");
            }
            return StringUtils.stripEnd(s.toString(),", ");
        }

        public boolean isScheduleEditInProgress() {
            return scheduleEditInProgress;
        }

        public void setScheduleEditInProgress(boolean scheduleEditInProgress) {
            this.scheduleEditInProgress = scheduleEditInProgress;
        }
    }

}
