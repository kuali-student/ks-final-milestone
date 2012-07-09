package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingWrapper implements Serializable{

    private ActivityOfferingInfo aoInfo;
    private FormatOfferingInfo formatOffering;
    private TermInfo term;
    private List<OfferingInstructorWrapper> instructors;
    private List<ScheduleComponentWrapper> scheduleComponentWrappers;
    private boolean readOnlyView;
    private boolean isChecked;
    private String courseOfferingId;
    // Tanveer 06/13/2012
    private String stateName;
    private String typeName;

    private String termName;

    private String formatOfferingName;

    // Tanveer 06/27/2012

    private String waitListLevelTypeKey;
    private String waitListTypeKey;
    private boolean hasWaitList;
    private String waitListText = "";
    private String toolTipText = "";

    private String firstInstructorDisplayName;

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




    public ActivityOfferingWrapper(){
        aoInfo = new ActivityOfferingInfo();
        instructors = new ArrayList<OfferingInstructorWrapper>();
        aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        aoInfo.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        formatOffering = new FormatOfferingInfo();
        term = new TermInfo();
        scheduleComponentWrappers = new ArrayList<ScheduleComponentWrapper>();
        this.setReadOnlyView(false);
        this.setIsChecked(false);

    }

    public ActivityOfferingWrapper(ActivityOfferingInfo info){
        super();
        aoInfo = info;
        instructors = new ArrayList<OfferingInstructorWrapper>();
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
}
