package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.ComparatorModel;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Main model object in Edit AO view.
 *
 * @see ColocatedActivity
 *
 */
public class ActivityOfferingWrapper implements Serializable, ComparatorModel{

    private String waitListType;
    private String waitListTypeUI;

    private String aoClusterName;
    private String aoClusterID;
    private ActivityOfferingInfo aoInfo;
    private FormatOfferingInfo formatOffering;
    private List<OfferingInstructorWrapper> instructors;
    private List<ScheduleComponentWrapper> scheduleComponentWrappers;
    private List<SeatPoolWrapper> seatpools;
    private List<PopulationInfo> populationsForSPValidation;
    private boolean readOnlyView;
    private boolean isChecked;
    private boolean isCheckedByCluster;
    private String courseOfferingId;
    private String populationsJSONString;

    private String stateName;
    private String typeName;
    private String typeKey;

    private String formatOfferingName;

    private Date termRegStartDate;
    private String waitListText = "";
    private String toolTipText = "";

    private String instructorNameHighestPercentEffort = "";

    private String firstInstructorDisplayName;
    private String instructorDisplayNames;

    private String courseOfferingCode = "";

    private List<ScheduleWrapper> actualScheduleComponents;
    private List<ScheduleWrapper> requestedScheduleComponents;
    private List<ScheduleWrapper> deletedScheduleComponents;
    private ScheduleWrapper newScheduleRequest;

    private SocInfo socInfo;

    private String scheduledState = "";
    private String startTimeDisplay = "";
    private String endTimeDisplay = "";
    private String daysDisplayName = "";
    private String buildingName = "";
    private String buildingCode = "";
    private String bldgCodeSimple = "";
    private String roomName = "";
    private String tbaDisplayName = "";
    private String colocatedAoInfo = "";

    private List<String> startTime;
    private List<String> endTime;
    private List<String> weekDays;

    private boolean schedulesModified;

    private String adminOrg;

      //hidden columns for toolbar
    private boolean enableApproveButton = false;
    private boolean enableDraftButton = false;
    private boolean enableSuspendButton = false;
    private boolean enableCancelButton = false;
    private boolean enableReinstateButton = false;
    private boolean enableMoveToButton = false;
    private boolean enableDeleteButton = false;
    private boolean enableCopyAOActionLink = false;
    private boolean enableEditAOActionLink = false;

    private boolean colocatedAO;
    private List<ColocatedActivity> colocatedActivities;
    private boolean maxEnrollmentShared;
    private boolean hiddenMaxEnrollmentShared;
    private int sharedMaxEnrollment;
    private boolean hasSeatpools;

    private ScheduleRequestSetInfo scheduleRequestSetInfo;

    private EditRenderHelper editRenderHelper;
    private boolean isPartOfColoSetOnLoadAlready;
    private boolean isColocatedOnLoadAlready;
    private boolean isSendRDLsToSchedulerAfterMSE;
    private boolean isRemovedFromColoSet;
    private String reinstateStateName;

    protected String viewId;

    private CourseOfferingContextBar contextBar = CourseOfferingContextBar.NULL_SAFE_INSTANCE;

    //This is needed to display the cross listed courses
    private CourseInfo course;

    /*
    * Always carry the parent or the standard term if any -- Is this because
    * manage COs/AOs only support search on TermCode but not on subterm?
    */
    private TermInfo term;
    //for Term (name) field display of the parent/standard term on Edit AO page
    private String termName;
    //for Sub Term (name) field display on Edit AO page
    private String subTermName = "";
    //for Start/End field display on Edit AO page, if subTermId is not empty,
    //display Start/End date info for the subterm
    private String termDisplayString = "";
    //for displaying subterm drop-down list when click change link on Edit AO page
    //and hold the id value for the selected subterm
    private String subTermId;
    //use this boolean to decide if "Change" link should be rendered or not
    private boolean hasSubTerms;
    //would it be better to define 'TermInfo subTerm' instead of the following three strings
    private String termStartEndDate = "";

    /*
    This is a list of subTerm start/end date strings to support javascript update of the
     displayed start/end when subterm is changed/choremoved
     */
    private String subTermDatesJsonString;

    //use this boolean to check if AO has any rule attached to it
    private boolean hasRule;

    private SchOfClassesRenderHelper schOfClassesRenderHelper;

    private CourseWaitListInfo courseWaitListInfo;
    //indicate at AO level, the state of the waitlist
    private boolean hasWaitlist;
    //indicate if wailtlist has been enabled in CO level
    private boolean hasWaitlistCO;
    //hold the checkbox value for Limit Waitlist Size
    private boolean limitWaitlistSize;

    private String requisite;

    private boolean isCentralSchedulingCoOrdinator;

    private String timeSlotType;

    private String crossListedCourseCodes;

    /**
     * Valid modes for creating non-standard timeslots
     *
     * The application supports three non-standard timeslot creation modes
     * 1. Allows the user to create non-standard timeslots
     * 2. Disallows the user to create non-standard timeslots
     * 3. Allows the user to create non-standard timeslots, but only with prior approval
     *
     * By default, the creation of non-standard timeslots is allowed only with prior approval.  This can be configured via property
     * kuali.ks.enrollment.timeslots.adhoc_creation_mode
     */
    public static enum NonStandardTimeslotCreationMode {

        ALLOWED,
        NOT_ALLOWED,
        NEEDS_APPROVAL;

        private NonStandardTimeslotCreationMode() { }

        /**
         * Returns a value based on the string provided.  If the provided string is null or a match cannot be found,
         * will return {@link org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper.NonStandardTimeslotCreationMode#NEEDS_APPROVAL}
         *
         * @param text
         * @return {@link org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper.NonStandardTimeslotCreationMode}
         */
        public static NonStandardTimeslotCreationMode fromString( String text ) {
            if( text != null ) {
                for( NonStandardTimeslotCreationMode m : NonStandardTimeslotCreationMode.values() ) {
                    if( text.equalsIgnoreCase( m.name() ) ) {
                        return m;
                    }
                }
            }
            return NonStandardTimeslotCreationMode.ALLOWED;
        }

    }

    public ActivityOfferingWrapper(){
        aoInfo = new ActivityOfferingInfo();
        instructors = new ArrayList<OfferingInstructorWrapper>();
        OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper();
        instructors.add(instructorWrapper);
        seatpools = new ArrayList<SeatPoolWrapper>();
        populationsForSPValidation = new ArrayList<PopulationInfo>();
        aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
        scheduleComponentWrappers = new ArrayList<ScheduleComponentWrapper>();
        this.setReadOnlyView(false);
        this.setIsChecked(false);
        actualScheduleComponents = new ArrayList<ScheduleWrapper>();
        requestedScheduleComponents = new ArrayList<ScheduleWrapper>();
        newScheduleRequest = new ScheduleWrapper();
        colocatedActivities = new ArrayList<ColocatedActivity>();
        maxEnrollmentShared = true;
        editRenderHelper = new EditRenderHelper();
        deletedScheduleComponents = new ArrayList<ScheduleWrapper>();
        courseWaitListInfo = new CourseWaitListInfo();
        hasWaitlist = false;
        hasWaitlistCO = false;
        limitWaitlistSize = false;
        startTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        weekDays = new ArrayList<String>();
    }

    public ActivityOfferingWrapper(ActivityOfferingInfo info){
        this(info,false);
    }

    public ActivityOfferingWrapper(ActivityOfferingInfo info,boolean isCentralSchedulingCoOrdinator){
        this();
        aoInfo = info;
        instructors = new ArrayList<OfferingInstructorWrapper>();
        if(info.getInstructors() == null || info.getInstructors().isEmpty()) {
            OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper();
            instructors.add(instructorWrapper);
        } else if(info.getInstructors().size() > 0) {
            for(OfferingInstructorInfo instructorInfo : info.getInstructors()) {
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructorInfo);
                if (instructorInfo.getPercentageEffort() != null) {
                    instructorWrapper.setsEffort(Integer.toString(instructorInfo.getPercentageEffort().intValue()));
                }
                instructors.add(instructorWrapper);
            }
        }
        seatpools = new ArrayList<SeatPoolWrapper>();
        populationsForSPValidation = new ArrayList<PopulationInfo>();
        startTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        weekDays = new ArrayList<String>();
        this.isCentralSchedulingCoOrdinator = isCentralSchedulingCoOrdinator;
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

    public boolean isEnableMoveToButton() {
        return enableMoveToButton;
    }

    public void setEnableMoveToButton(boolean enableMoveToButton) {
        this.enableMoveToButton = enableMoveToButton;
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

    /**
     * This method returns whether editing of the activity code is allowed or not.
     * This method reads the configuration property <code>kuali.ks.enrollment.options.edit-activity-offering-code-allowed</code>
     * and returns it's value (true/false). This is to allow the institutional configuration to decide whether the users
     * should be allowed to edit the activity code.
     *
     * The default is to allow the user to edit the activity code.
     *
     * @return boolean based on the configured property
     */
    public boolean isEditActivityCodeAllowed() {
        if( "false".equalsIgnoreCase( ConfigContext.getCurrentContextConfig().getProperty( CourseOfferingConstants.CONFIG_PARAM_KEY_EDIT_ACTIVITY_CODE ) ) ) {
            return false;
        }

        return true;
    }

    /**
     * This method returns the allowed creation-mode of non-standard time-slots.
     * This method reads the configuration property <code>kuali.ks.enrollment.timeslots.adhoc_creation_mode</code> and
     * returns it's value (ALLOWED, NOT_ALLOWED, NEEDS_APPROVAL).  This is to allow the institutional configuration to
     * decide whether or not users should be allowed to create non-standard time-slots.
     *
     * The default is to allow the user to create non-standard time-slots, but only with prior approval.
     *
     * @return {@link org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper.NonStandardTimeslotCreationMode}
     */
    public NonStandardTimeslotCreationMode getNonStandardTimeslotCreationMode() {
        String contextConfigProp = ConfigContext.getCurrentContextConfig().getProperty( ActivityOfferingConstants.ConfigProperties.NON_STANDARD_TIMESLOT_CREATION_MODE);
        return NonStandardTimeslotCreationMode.fromString(contextConfigProp);
    }

    public boolean getCanNonStandardTimeslotsBeApproved() {
        switch( getNonStandardTimeslotCreationMode() ) {
            case NEEDS_APPROVAL:
                return true;
            default:
                return false;

        }
    }

    private String abbreviatedCourseType = "";

    public String getAbbreviatedCourseType() {
        return abbreviatedCourseType;
    }

    public void setAbbreviatedCourseType(String abbreviatedCourseType) {
        this.abbreviatedCourseType= abbreviatedCourseType;
    }

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

    public String getScheduledState() {
        return scheduledState;
    }

    public void setScheduledState(String scheduledState) {
        this.scheduledState = scheduledState;
    }

    public void setScheduledState(String scheduledState,boolean appendForDisplay) {
        if (appendForDisplay){
            this.scheduledState = this.scheduledState + "<br>" + StringUtils.defaultString(scheduledState);
        }else{
            this.scheduledState = StringUtils.defaultString(scheduledState);
        }
    }
    public void setScheduledState(String scheduledState,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.scheduledState)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.scheduledState = this.scheduledState + "<br><span " + cssClass + " >" + scheduledState + "</span>";
        }else{
            this.scheduledState = "<span " + cssClass + " >" + scheduledState + "</span>";
        }
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

    public List<PopulationInfo> getPopulationsForSPValidation() {
        return populationsForSPValidation;
    }

    public void setPopulationsForSPValidation(List<PopulationInfo> populationsForSPValidation) {
        this.populationsForSPValidation = populationsForSPValidation;
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

    public List<ScheduleWrapper> getDeletedScheduleComponents() {
        return deletedScheduleComponents;
    }

    public void setDeletedScheduleComponents(List<ScheduleWrapper> deletedScheduleComponents) {
        this.deletedScheduleComponents = deletedScheduleComponents;
    }

    public ScheduleWrapper getNewScheduleRequest() {
        return newScheduleRequest;
    }

    public void setNewScheduleRequest(ScheduleWrapper newScheduleRequest) {
        this.newScheduleRequest = newScheduleRequest;
    }

    public boolean isRequestedScheduleComponentsRecentlyEmpty() {
        boolean haveNoRequesteds = ( this.requestedScheduleComponents == null || this.requestedScheduleComponents.isEmpty() ) ? true : false;
        boolean haveSomeDeleteds = ( this.deletedScheduleComponents != null && !this.deletedScheduleComponents.isEmpty() ) ? true : false;

        return haveNoRequesteds && haveSomeDeleteds;
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

    public List<String> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<String> startTime) {
        this.startTime = startTime;
    }

    public List<String> getEndTime() {
        return endTime;
    }

    public void setEndTime(List<String> endTime) {
        this.endTime = endTime;
    }

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
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

    public String getBldgCodeSimple() {
        return bldgCodeSimple;
    }

    public void setBldgCodeSimple(String buildingCode, String buildingName, String dlTypeClass) {
        String cssClass = "";
        boolean  appendForDisplay = true;
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.bldgCodeSimple)){
            appendForDisplay = false;
        } else {
            appendForDisplay = true;
        }
        String underlineCssClass = "<span" + cssClass + "style=\"border-bottom: 1px dotted\">";

        String BldgCodeMark = underlineCssClass + buildingCode + "</span>";
        if (appendForDisplay){
            this.bldgCodeSimple = this.bldgCodeSimple + "<br>" + BldgCodeMark;
        }else{
            this.bldgCodeSimple = BldgCodeMark;
        }
    }

    public void setBldgCodeSimple(String bldgCodeSimple) {
        this.bldgCodeSimple = bldgCodeSimple;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode, boolean appendForDisplay) {
        String cssClass = "style=\"border-bottom: 1px dotted;\"";
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br><span " + cssClass + " >" + StringUtils.defaultString(buildingCode) + "</span>";
        }else{
            this.buildingCode = "<span " + cssClass + " >" + StringUtils.defaultString(buildingCode) + "</span>";
        }
    }

    public void setBuildingCode(String buildingCode, boolean appendForDisplay, String dlTypeClass) {

        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "style=\"border-bottom: 1px dotted\"";
        }
        if(StringUtils.isEmpty(buildingCode)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br><span " + cssClass + " class=\"" + dlTypeClass + "\""+ " >" + buildingCode + "</span>";
        }else{
            this.buildingCode = "<span " + cssClass + " class=\"" + dlTypeClass + "\""+ " >" + buildingCode + "</span>";
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

    public String getPopulationsJSONString() {
        return populationsJSONString;
    }

    public void setPopulationsJSONString(String populationsJSONString) {
        this.populationsJSONString = populationsJSONString;
    }

    /**
     * This method returns a list of comma seperated alternate course codes.
     * This is used in create and edit course offerings screen.
     * @return
     */
    public String getCrossListedCourseCodes(){
        return crossListedCourseCodes;
    }

    public void setCrossListedCourseCodes(String crossListedCourseCodes) {
        this.crossListedCourseCodes = crossListedCourseCodes;
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

    public boolean getHiddenMaxEnrollmentShared(){
        return maxEnrollmentShared;
    }

    public int getSharedMaxEnrollment() {
        return sharedMaxEnrollment;
    }

    public void setSharedMaxEnrollment(int sharedMaxEnrollment) {
        this.sharedMaxEnrollment = sharedMaxEnrollment;
    }

    public boolean getHasSeatpools() {
        return hasSeatpools;
    }

    public void setHasSeatpools(boolean hasSeatpools) {
        this.hasSeatpools = hasSeatpools;
    }

    public ScheduleRequestSetInfo getScheduleRequestSetInfo() {
        return scheduleRequestSetInfo;
    }

    public void setScheduleRequestSetInfo(ScheduleRequestSetInfo scheduleRequestSetInfo) {
        this.scheduleRequestSetInfo = scheduleRequestSetInfo;
    }

    /**
     * This method return a colocated AO code for current course. This will
     * be displayed as the tooltip (if colocated AO exists) at manage and delete AO screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getColocatedAoInfoUI(){
        //JIRA FIX : KSENROLL-8731 - Replaced StringBuffer with StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("This activity is colocated with:<br>");
        sb.append(colocatedAoInfo + "<br>");

        return StringUtils.removeEnd(sb.toString(),"<br>");
    }

    public boolean isPartOfColoSetOnLoadAlready() {
        return isPartOfColoSetOnLoadAlready;
    }

    public void setPartOfColoSetOnLoadAlready(boolean isPartOfColoSetOnLoadAlready) {
        this.isPartOfColoSetOnLoadAlready = isPartOfColoSetOnLoadAlready;
    }

    public boolean isColocatedOnLoadAlready() {
        return isColocatedOnLoadAlready;
    }

    public void setColocatedOnLoadAlready(boolean colocatedOnLoadAlready) {
        isColocatedOnLoadAlready = colocatedOnLoadAlready;
    }

    public boolean isSchedulingCompleted(){
        if (getSocInfo() != null){
            if (StringUtils.equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED,getSocInfo().getSchedulingStateKey())){
                return true;
            }
        }
        return false;
    }

    public CourseOfferingContextBar getContextBar() {
        return contextBar;
    }

    public void setContextBar(CourseOfferingContextBar contextBar) {
        this.contextBar = contextBar;
    }

    /**
     * Returns the Edit Helper.
     *
     * @return
     */
    public EditRenderHelper getEditRenderHelper() {
        return editRenderHelper;
    }

    public SchOfClassesRenderHelper getSchOfClassesRenderHelper() {
        return schOfClassesRenderHelper;
    }

    public void setSchOfClassesRenderHelper(SchOfClassesRenderHelper schOfClassesRenderHelper) {
        this.schOfClassesRenderHelper = schOfClassesRenderHelper;
    }

    /**
     * Helper to render the colocated activites in Edit AO screen.
     */
    public class EditRenderHelper implements Serializable {

        private List<ColocatedActivity> manageSeperateEnrollmentList;

        private boolean scheduleEditInProgress;
        private boolean isPersistedRDLsExists;
        private String selectedAO;

        private String prevAOTypeName;
        private String nextAOTypeName;

        private ActivityOfferingInfo prevAO;
        private ActivityOfferingInfo nextAO;

        private List<KeyValue> aoCodes;

        public EditRenderHelper(){
            manageSeperateEnrollmentList = new ArrayList<ColocatedActivity>();
            aoCodes = new ArrayList<KeyValue>();
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
            //JIRA FIX : KSENROLL-8731 - Replaced StringBuffer with StringBuilder
            StringBuilder sb = new StringBuilder();
            for (ColocatedActivity colo : getColocatedActivities()){
                sb.append(colo.getEditRenderHelper().getCode() + ", ");
            }
            return StringUtils.stripEnd(sb.toString(),", ");
        }

        public boolean isScheduleEditInProgress() {
            return scheduleEditInProgress;
        }

        public void setScheduleEditInProgress(boolean scheduleEditInProgress) {
            this.scheduleEditInProgress = scheduleEditInProgress;
        }

        public String getSelectedAO() {
            return selectedAO;
        }

        public void setSelectedAO(String selectedAO) {
            this.selectedAO = selectedAO;
        }


        public List<KeyValue> getAoCodes() {
            return aoCodes;
        }

        public void setAoCodes(List<KeyValue> aoCodes) {
            this.aoCodes = aoCodes;
        }

        public ActivityOfferingInfo getPrevAO() {
            return prevAO;
        }

        public void setPrevAO(ActivityOfferingInfo prevAO) {
            this.prevAO = prevAO;
        }

        public ActivityOfferingInfo getNextAO() {
            return nextAO;
        }

        public void setNextAO(ActivityOfferingInfo nextAO) {
            this.nextAO = nextAO;
        }

        public String getPrevAOTypeName() {
            return prevAOTypeName;
        }

        public void setPrevAOTypeName(String prevAOTypeName) {
            this.prevAOTypeName = prevAOTypeName;
        }

        public String getNextAOTypeName() {
            return nextAOTypeName;
        }

        public void setNextAOTypeName(String nextAOTypeName) {
            this.nextAOTypeName = nextAOTypeName;
        }

    }

    /**
     * Model to handle all the ui elements needed at schedule of classes view
     */
    public class SchOfClassesRenderHelper implements Serializable {

        private String information;

        public SchOfClassesRenderHelper(){

        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }
    }

    // subterms
    public String getSubTermName() {
        return subTermName;
    }

    public void setSubTermName(String subTermName) {
        this.subTermName = subTermName;
    }

    public boolean getHasSubTerms() {
        return hasSubTerms;
    }

    public void setHasSubTerms(boolean hasSubTerms) {
        this.hasSubTerms = hasSubTerms;
    }

    public String getSubTermId() {
        return subTermId;
    }

    public void setSubTermId(String subTermId) {
        this.subTermId = subTermId;
    }

    public String getTermStartEndDate() {
           return termStartEndDate;
    }

    public void setTermStartEndDate(String termStartEndDate) {
        this.termStartEndDate = termStartEndDate;
    }

    public String getSubTermDatesJsonString() {
        return subTermDatesJsonString;
    }

    public void setSubTermDatesJsonString(String subTermDatesJsonString) {
        this.subTermDatesJsonString = subTermDatesJsonString;
    }

    /**
     * @see #setRemovedFromColoSet(boolean)
     * @return true if the user breaks the colo set
     */
    public boolean isRemovedFromColoSet() {
        return isRemovedFromColoSet;
    }

    /**
     * This flag will be set when the user breaks the AO from the colo set.
     *
     * @param removedFromColoSet
     */
    public void setRemovedFromColoSet(boolean removedFromColoSet) {
        isRemovedFromColoSet = removedFromColoSet;
    }

    public boolean isHasRule() {
        return hasRule;
    }

    public void setHasRule(boolean hasRule) {
        this.hasRule = hasRule;
    }

    public CourseWaitListInfo getCourseWaitListInfo() {
        return courseWaitListInfo;
    }

    public void setCourseWaitListInfo(CourseWaitListInfo courseWaitListInfo) {
        this.courseWaitListInfo = courseWaitListInfo;
    }

    public boolean isHasWaitlist() {
        return hasWaitlist;
    }

    public void setHasWaitlist(boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }

    public boolean isHasWaitlistCO() {
        return hasWaitlistCO;
    }

    public void setHasWaitlistCO(boolean hasWaitlistCO) {
        this.hasWaitlistCO = hasWaitlistCO;
    }

    public String getReinstateStateName() {
        return reinstateStateName;
    }

    public void setReinstateStateName(String reinstateStateName) {
        this.reinstateStateName = reinstateStateName;
    }

    public boolean isLimitWaitlistSize() {
        return limitWaitlistSize;
    }

    public void setLimitWaitlistSize(boolean limitWaitlistSize) {
        this.limitWaitlistSize = limitWaitlistSize;
    }

    public String getWaitListType() {
        return waitListType;
    }

    public void setWaitListType(String waitListType) {
        this.waitListType = waitListType;
    }

    public String getWaitListTypeUI() {
        return waitListTypeUI;
    }

    public void setWaitListTypeUI(String waitListTypeUI) {
        this.waitListTypeUI = waitListTypeUI;
    }

    public String getRequisite() {
        return requisite;
    }

    public void setRequisite(String requisite) {
        this.requisite = requisite;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public HashMap getSchedulingStateHash() {
        try {
            return CourseOfferingManagementUtil.getSchedulingStateAndNameHash();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *   This method will update waitListType depending on automaticallyProcessed and confirmationRequired boolean
     *
     *   automatic -> automaticallyProcessed = true, confirmationRequired = false
     *   Confirmation (semi-automatic) -> automaticallyProcessed = true, confirmationRequired = true
     *   manual -> automaticallyProcessed = false, confirmationRequired = false
     *
     */
    public void updateWaitListType(){

        Boolean automaticallyProcessed = courseWaitListInfo.getAutomaticallyProcessed();
        Boolean confirmationRequired   = courseWaitListInfo.getConfirmationRequired();
        //default value is automatic
        if((null == automaticallyProcessed) || (null == confirmationRequired)) {
               waitListType = LuiServiceConstants.AUTOMATIC_WAITLIST_TYPE_KEY ;
               waitListTypeUI = "Automatic";
        }

        if(automaticallyProcessed && !(confirmationRequired)){
            waitListType = LuiServiceConstants.AUTOMATIC_WAITLIST_TYPE_KEY ;
            waitListTypeUI = "Automatic";
        } else if(automaticallyProcessed && confirmationRequired ) {
            waitListType = LuiServiceConstants.CONFIRMATION_WAITLIST_TYPE_KEY;
            waitListTypeUI = "Confirmation";
        } else if(!(automaticallyProcessed) &&  !(confirmationRequired)) {
            waitListType = LuiServiceConstants.MANUAL_WAITLIST_TYPE_KEY;
            waitListTypeUI = "Manual";
        }
    }

    public boolean isAuthorizedToModifyEndTimeTS() {
        if (isCentralSchedulingCoOrdinator){
            return true;
        } else {
            if (getNonStandardTimeslotCreationMode() == ActivityOfferingWrapper.NonStandardTimeslotCreationMode.ALLOWED){
                return true;
            } else if (getNonStandardTimeslotCreationMode() == ActivityOfferingWrapper.NonStandardTimeslotCreationMode.NOT_ALLOWED){
                return false;
            } else if (getNonStandardTimeslotCreationMode() == ActivityOfferingWrapper.NonStandardTimeslotCreationMode.NEEDS_APPROVAL){
                return isAdHocFlagSet();
            }
        }

        return false;

    }

    private boolean isAdHocFlagSet(){
       for (AttributeInfo attr : aoInfo.getAttributes()){
           if (StringUtils.equals(attr.getKey(),"kuali.attribute.nonstd.ts.indicator")){
               return BooleanUtils.toBoolean(attr.getValue());
           }
       }
       return false;
    }

    public String getTimeSlotType() {
        return timeSlotType;
    }

    public void setTimeSlotType(String timeSlotType) {
        this.timeSlotType = timeSlotType;
    }
}
