/* Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.myplan.plan.form;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.myplan.course.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.dataobject.CourseSummaryDetails;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseSummary;
import org.kuali.student.r2.common.dto.StatusInfo;

/**
 * Form for all plan item actions.
 */
public class PlanForm extends UifFormBase {
    private final Logger logger = Logger.getLogger(PlanForm.class);

    /**
     * The return code for a plan item add, change, or delete.
     */
    public enum REQUEST_STATUS {
        /*  The requested operation was successful. */
        SUCCESS,
        /*  The requested operation was unnecessary (e.g. the plan item was already deleted), but appropriate
         *  javascript events were generated/available.
         *
         *  TODO: Looks like this status may not be necessary. */
        NOOP,
        /* The requested operation failed. */
        ERROR
    }

    /**
     * Storage for the status of a request for add, change, or delete of a plan item.
     */
    private REQUEST_STATUS requestStatus;

    //  Saved courses params.
    private String planItemId;

    private String courseId;

    // Quick Add params
    private String courseCd;

    private String courseCredit;

    private String courseNote;

    /*properties used for section Planning*/
    private String sectionCode;

    private String primarySectionCode;

    private String primaryPlanItemId;

    private String instituteCode;

    private boolean primary;

    private List<String> sectionsToDelete;

    //Flag Used for student to hide/unhide plan view to adviser
    private String enableAdviserView= PlanConstants.LEARNING_PLAN_ITEM_SHARED_TRUE_KEY;

    private CourseSummaryDetails courseSummaryDetails;

    private List<ActivityOfferingItem> planActivities;

    private PlannedCourseSummary plannedCourseSummary;

    //  Form fields.
    private String atpId;

    private String termName;

    private boolean other = false;

    //  Additional fields needed for the Other option.
    private String termYear;

    //   Form checkbox to determine plan item type (planned or backup).
    private boolean backup = false;

    // Used for populating the menu oprions for the Academic record course link
    private String acadRecAtpId;

    //   based on this Add to plan page items are populated
    private boolean moveCourse = false;

    // boolean to show or hide Other option.
    private boolean showOther = false;

    /*Flag used for populating the exact menu items for a course in past,present, future terms */
    private boolean setToPlanning=false;

    private int messagesCount=0;

    private int bookmarkedCount=0;

    private boolean newUser;

    private boolean courseInPlan;

    private boolean courseInBackup;

    private StatusInfo statusInfo = new StatusInfo();

    private String termNote;
    public int getBookmarkedCount() {
        return bookmarkedCount;
    }

    public void setBookmarkedCount(int bookmarkedCount) {
        this.bookmarkedCount = bookmarkedCount;
    }

    public int getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(int messagesCount) {
        this.messagesCount = messagesCount;
    }

    public boolean isSetToPlanning() {
        return setToPlanning;
    }

    public void setSetToPlanning(boolean setToPlanning) {
        this.setToPlanning = setToPlanning;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    /**
     * A list of javascript events as:
     * EVENT_NAME
     * param1: p1
     * param2: p2
     * PLAN_ITEM_ADDED
     * itemType: plannedCourse
     * planItem: pi1
     * courseId: c1
     */
    private Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> javascriptEvents;

    private ObjectMapper mapper = new ObjectMapper();

    public PlanForm() {
        super();
    }

    public Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> getJavascriptEvents() {
        return javascriptEvents;
    }

    public void setJavascriptEvents(Map<PlanConstants.JS_EVENT_NAME, Map<String, String>> javascriptEvents) {
        this.javascriptEvents = javascriptEvents;
    }

    public String getCourseCd() {
		return courseCd;
	}

	public void setCourseCd(String courseCd) {
		this.courseCd = courseCd;
	}

	public String getCourseCredit() {
		return courseCredit;
	}

	public void setCourseCredit(String courseCredit) {
		this.courseCredit = courseCredit;
	}



	public String getCourseNote() {
		return courseNote;
	}

	public void setCourseNote(String courseNote) {
		this.courseNote = courseNote;
	}

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        if(atpId!=null){
            if(atpId.contains(",")){
                atpId=atpId.substring(0,atpId.indexOf(","));
            }
        }
        this.atpId = atpId;
    }



    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPlanItemId() {
        return planItemId;
    }

    public void setPlanItemId(String planItemId) {
        this.planItemId = planItemId;
    }

    public boolean isBackup() {
        return backup;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public CourseSummaryDetails getCourseSummaryDetails() {
        if(courseSummaryDetails==null) courseSummaryDetails = new CourseSummaryDetails();
        return this.courseSummaryDetails;
    }

    public void setCourseSummaryDetails(CourseSummaryDetails courseSummaryDetails) {
        this.courseSummaryDetails = courseSummaryDetails;
    }

    public PlannedCourseSummary getPlannedCourseSummary() {
        if(plannedCourseSummary==null) plannedCourseSummary = new PlannedCourseSummary();
        return plannedCourseSummary;
    }

    public void setPlannedCourseSummary(PlannedCourseSummary plannedCourseSummary) {
        this.plannedCourseSummary = plannedCourseSummary;
    }

    public REQUEST_STATUS getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(REQUEST_STATUS requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isMoveCourse() {
        return moveCourse;
    }

    public void setMoveCourse(boolean moveCourse) {
        this.moveCourse = moveCourse;
    }

    public String getAcadRecAtpId() {
        return acadRecAtpId;
    }

    public void setAcadRecAtpId(String acadRecAtpId) {
        this.acadRecAtpId = acadRecAtpId;
    }

    public boolean isShowOther() {
        return showOther;
    }

    public void setShowOther(boolean showOther) {
        this.showOther = showOther;
    }

    public String getEnableAdviserView() {
        return enableAdviserView;
    }

    public void setEnableAdviserView(String enableAdviserView) {
        this.enableAdviserView = enableAdviserView;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public boolean isCourseInPlan() {
        return courseInPlan;
    }

    public void setCourseInPlan(boolean courseInPlan) {
        this.courseInPlan = courseInPlan;
    }

    public boolean isCourseInBackup() {
        return courseInBackup;
    }

    public void setCourseInBackup(boolean courseInBackup) {
        this.courseInBackup = courseInBackup;
    }

    public String getPrimarySectionCode() {
        return primarySectionCode;
    }

    public void setPrimarySectionCode(String primarySectionCode) {
        this.primarySectionCode = primarySectionCode;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getPrimaryPlanItemId() {
        return primaryPlanItemId;
    }

    public void setPrimaryPlanItemId(String primaryPlanItemId) {
        this.primaryPlanItemId = primaryPlanItemId;
    }

    public List<String> getSectionsToDelete() {
        return sectionsToDelete;
    }

    public void setSectionsToDelete(List<String> sectionsToDelete) {
        this.sectionsToDelete = sectionsToDelete;
    }

    public List<ActivityOfferingItem> getPlanActivities() {
        return planActivities;
    }

    public void setPlanActivities(List<ActivityOfferingItem> planActivities) {
        this.planActivities = planActivities;
    }

    /*Only used in the Ui for getting the short Term*/
    public String getShortTerm() {
        String shortTermName = "";
        if (getAtpId() != null) {
            shortTermName = KsapFrameworkServiceLocator.getTermHelper().getYearTerm(getAtpId()).getShortName();
        }
        return shortTermName;
    }

    /**
     * Returns the list of events that should be
     */
    public String getJavascriptEventsAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonOut = null;
        try {
            //  Turn the list of javascript events into a string of JSON.
            jsonOut = mapper.writeValueAsString(javascriptEvents);
            jsonOut = StringEscapeUtils.unescapeJava(jsonOut);
        } catch (Exception e) {
            logger.error("Could not convert javascript events to JSON.", e);
            jsonOut = "";
        }

        //  TODO: Determine if there is a config that can be set to avoid having to do this.
        jsonOut = jsonOut.replaceAll("\"\\{", "{");
        jsonOut = jsonOut.replaceAll("}\"", "}");

        return jsonOut;
    }

    //  Added this for using in the crud message matrix property editor
    public CourseDetails getCourseAndPlanSummary() {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseSummaryDetails(this.getCourseSummaryDetails());
        courseDetails.setPlannedCourseSummary(this.getPlannedCourseSummary());
        return courseDetails;
    }

    public String getPlanItemIdXmlSafe(){
        if(planItemId!=null)
            return planItemId.replace(".","_");
        return "";
    }

    public String getCourseIdXmlSafe(){
        if(courseId!=null)
            return courseId.replace(".","_");
        return "";
    }
    public StatusInfo getStatusInfo(){
        return this.statusInfo;
    }
    public void setStatusInfo(StatusInfo statusInfo){
        this.statusInfo=statusInfo;
    }

    public String getTermNote() {
        return termNote;
    }

    public void setTermNote(String termNote) {
        this.termNote = termNote;
    }


    /**
     * Fake Property Wrappers
     * Used to display form values in Datafield/InputFields but pass them back in javascript
     */

    public String getFakeTermNote() {
        return getTermNote();
    }

    public void setFakeTermNote(String fakeTermNote) {}

    public String getFakeCourseNote() {
        return getCourseNote();
    }

    public void setFakeCourseNote(String fakeCourseNote) {}

    public String getFakeCourseCredit() {
        return getCourseCredit();
    }

    public void setFakeCourseCredit(BigDecimal fakeCourseCredit) {}

    public String getFakeAtpId() {
        return getAtpId();
    }

    public void setFakeAtpId(String fakeAtpId) {}

    public boolean isFakeBackup() {
        return isBackup();
    }

    public void setFakeBackup(boolean fakeBackup) {}

}
