package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.ComparatorModel;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Main model object in Edit AO view.
 *
 * @see org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity
 *
 */
public class ExamOfferingWrapper implements Serializable, ComparatorModel{


    private String stateName;
    private String typeName;
    private String typeKey;
    private ExamOfferingInfo eoInfo;
    private List<String> startTime;
    private List<String> endTime;
    private List<String> weekDays;
    private ActivityOfferingInfo aoInfo;

    private String labelState;

    private List<ScheduleWrapper> requestedScheduleComponents;
    private List<ScheduleWrapper> deletedRequestedScheduleComponents;
    private ScheduleWrapper requestedSchedule;
    private ScheduleWrapper scheduleRequest;
    private ScheduleRequestSetInfo scheduleRequestSetInfo;
    private String courseOfferingCode;
    private boolean driverPerAO;
    private boolean overrideMatrix;
    private String overrideMatrixUI;
    private String eoResultKey;

    public ExamOfferingWrapper(){
        startTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        weekDays = new ArrayList<String>();
        aoInfo = new ActivityOfferingInfo();
        requestedScheduleComponents = new ArrayList<ScheduleWrapper>();
        deletedRequestedScheduleComponents = new ArrayList<ScheduleWrapper>();
    }

    public ExamOfferingWrapper(ExamOfferingInfo info){
        this();
        eoInfo = info;
        startTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        weekDays = new ArrayList<String>();
        requestedScheduleComponents = new ArrayList<ScheduleWrapper>();
        deletedRequestedScheduleComponents = new ArrayList<ScheduleWrapper>();

    }

    public String courseOfferingTitle;
    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }


    private String activityCode = "";

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
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

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public ExamOfferingInfo getEoInfo() {
        return eoInfo;
    }

    public void setEoInfo(ExamOfferingInfo eoInfo) {
        this.eoInfo = eoInfo;
    }

    public ActivityOfferingInfo getAoInfo() {
        return aoInfo;
    }

    public void setAoInfo(ActivityOfferingInfo aoInfo) {
        this.aoInfo = aoInfo;
    }

    public List<ScheduleWrapper> getRequestedScheduleComponents() {
        return requestedScheduleComponents;
    }

    public void setRequestedScheduleComponents(List<ScheduleWrapper> requestedScheduleComponents) {
        this.requestedScheduleComponents = requestedScheduleComponents;
    }

    public List<ScheduleWrapper> getDeletedRequestedScheduleComponents() {
        return deletedRequestedScheduleComponents;
    }

    public void setDeletedRequestedScheduleComponents(List<ScheduleWrapper> deletedRequestedScheduleComponents) {
        this.deletedRequestedScheduleComponents = deletedRequestedScheduleComponents;
    }

    public ScheduleWrapper getRequestedSchedule() {
        return requestedSchedule;
    }

    public void setRequestedSchedule(ScheduleWrapper requestedSchedule) {
        this.requestedSchedule = requestedSchedule;
    }

    public ScheduleWrapper getScheduleRequest() {
        return scheduleRequest;
    }

    public void setScheduleRequest(ScheduleWrapper scheduleRequest) {
        this.scheduleRequest = scheduleRequest;
    }

    public ScheduleRequestSetInfo getScheduleRequestSetInfo() {
        return scheduleRequestSetInfo;
    }

    public void setScheduleRequestSetInfo(ScheduleRequestSetInfo scheduleRequestSetInfo) {
        this.scheduleRequestSetInfo = scheduleRequestSetInfo;
    }

    public boolean isDriverPerAO() {
        return driverPerAO;
    }

    public void setDriverPerAO(boolean driverPerAO) {
        this.driverPerAO = driverPerAO;
    }

    public boolean isOverrideMatrix() {
        return overrideMatrix;
    }

    public void setOverrideMatrix(boolean overrideMatrix) {
        this.overrideMatrix = overrideMatrix;
    }

    public String getOverrideMatrixUI() {
        return String.valueOf(isOverrideMatrix());
    }

    public void setOverrideMatrixUI(String overrideMatrixUI) {
        this.overrideMatrixUI = overrideMatrixUI;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getEoResultKey() {
        return eoResultKey;
    }

    public void setEoResultKey(String eoResultKey) {
        this.eoResultKey = eoResultKey;
    }



    public String getLabelState() {

        String stateSchedulingName = "";
        if (StringUtils.equals(eoInfo.getSchedulingStateKey(), ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY)) {
            stateSchedulingName = "Unscheduled";
        } else if (StringUtils.equals(eoInfo.getSchedulingStateKey(), ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_EXEMPT_STATE_KEY)) {
            stateSchedulingName = "Exempt";
        } else if (StringUtils.equals(eoInfo.getSchedulingStateKey(), ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_MATRIX_ERROR_STATE_KEY)) {
            stateSchedulingName = "Matrix Error";
        } else {
            stateSchedulingName = "Unscheduled";
        }

        labelState = stateName +
                "<br/><i>" + stateSchedulingName+
                "</i>"   ;
        return labelState;
    }

    public void setLabelState(String labelState) {
        this.labelState = labelState;
    }
}
