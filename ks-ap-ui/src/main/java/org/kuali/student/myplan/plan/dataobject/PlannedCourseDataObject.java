package org.kuali.student.myplan.plan.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;
import org.kuali.student.ap.coursesearch.dataobject.CourseSummaryDetails;
import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditor;
import org.kuali.student.ap.coursesearch.util.ScheduledTermsPropertyEditor;

/**
 * Captures a course detail object along with a single instance of its planned
 * information.
 * <p/>
 * Date: 4/26/12 Time: 3:40 PM To change this template use File | Settings |
 * File Templates.
 */
public class PlannedCourseDataObject implements
		Comparable<PlannedCourseDataObject>, Serializable {

	private static final long serialVersionUID = -5983123014220935865L;

	private PlanItemDataObject planItemDataObject;

	private List<ActivityOfferingItem> planActivities;

	private CourseSummaryDetails courseDetails;

	private boolean showAlert;

	// TODO KSAP-741 - Rice Trackback KULRICE-9003.
	// This should be on plannedTerm once the jira is resolved
	private boolean timeScheduleOpen;

	public String getCourseCode() {
		return courseDetails == null ? null : courseDetails.getCode();
	}
	
	public String getCampusCode() {
		return planItemDataObject == null ? null : planItemDataObject.getCampusCode();
	}

	public String getActivityCode() {
		return planItemDataObject == null ? null : planItemDataObject.getActivityCode();
	}

	public String getCourseTitle() {
		return courseDetails == null ? null : courseDetails.getCourseTitle();
	}
	
	public CourseSummaryDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseSummaryDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public PlanItemDataObject getPlanItemDataObject() {
		return planItemDataObject;
	}

	public void setPlanItemDataObject(PlanItemDataObject planItemDataObject) {
		this.planItemDataObject = planItemDataObject;
	}

	public boolean isShowAlert() {
		return showAlert;
	}

	public void setShowAlert(boolean showAlert) {
		this.showAlert = showAlert;
	}

	@Override
	public int compareTo(PlannedCourseDataObject that) {
		return this.getPlanItemDataObject().getDateAdded()
				.compareTo(that.getPlanItemDataObject().getDateAdded())
				* -1;
	}

	public boolean isTimeScheduleOpen() {
		return timeScheduleOpen;
	}

	public void setTimeScheduleOpen(boolean timeScheduleOpen) {
		this.timeScheduleOpen = timeScheduleOpen;
	}

	public List<ActivityOfferingItem> getPlanActivities() {
		return planActivities;
	}

	public void setPlanActivities(List<ActivityOfferingItem> planActivities) {
		this.planActivities = planActivities;
	}

    public String getScheduledForUI(){
        ScheduledTermsPropertyEditor editor = new ScheduledTermsPropertyEditor();
        editor.setValue(getCourseDetails());
        return editor.getAsText();
    }

    public String getProjectedForUI(){
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(getCourseDetails());
        editor.setEmptyListMessage("Check with the department or your adviser for more information about this course.");
        editor.setApplyClassOnItem(true);
        if (editor.getEmptyListStyleClasses()==null) {
            editor.setEmptyListStyleClasses(new ArrayList<String>());
        }
        editor.getEmptyListStyleClasses().add("empty");

        return editor.getAsText();
    }
    public String getRequisitesForUI(){
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(getCourseDetails().getRequisites());
        editor.setEmptyListMessage("None");
        return editor.getAsText();
    }
    public String getAbbrGenEdRequirementsForUI(){
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(getCourseDetails().getAbbrGenEdRequirements());
        editor.setEmptyListMessage("&nbsp;");
        return editor.getAsText();
    }
}
