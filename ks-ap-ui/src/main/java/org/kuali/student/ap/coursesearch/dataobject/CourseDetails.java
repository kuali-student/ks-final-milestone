package org.kuali.student.ap.coursesearch.dataobject;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.kuali.student.ap.coursesearch.util.CrudMessageMatrixFormatter;
import org.kuali.student.ap.coursesearch.util.ScheduledTermsPropertyEditor;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseSummary;

/**
 * This data object records all instances where the course has been planned,
 * bookmarked, recommended or present in academic records of the student
 */
public class CourseDetails {

	private String searchTerm;
	private String credits;
	private boolean backup;
	
	private CourseSummaryDetails courseSummaryDetails;
	private List<CourseOfferingInstitution> courseOfferingInstitutionList;

	private PlannedCourseSummary plannedCourseSummary;

	public CourseDetails() {
		plannedCourseSummary = new PlannedCourseSummary();
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public boolean isBackup() {
		return backup;
	}

	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	public CourseSummaryDetails getCourseSummaryDetails() {
		return courseSummaryDetails;
	}

	public void setCourseSummaryDetails(
			CourseSummaryDetails courseSummaryDetails) {
		this.courseSummaryDetails = courseSummaryDetails;
	}

	public List<CourseOfferingInstitution> getCourseOfferingInstitutionList() {
		if (courseOfferingInstitutionList == null) {
			courseOfferingInstitutionList = new ArrayList<CourseOfferingInstitution>();
		}
		return courseOfferingInstitutionList;
	}

	public void setCourseOfferingInstitutionList(
			List<CourseOfferingInstitution> courseOfferingInstitutionList) {
		this.courseOfferingInstitutionList = courseOfferingInstitutionList;
	}

	public PlannedCourseSummary getPlannedCourseSummary() {
		return plannedCourseSummary;
	}

	public void setPlannedCourseSummary(
			PlannedCourseSummary plannedCourseSummary) {
		this.plannedCourseSummary = plannedCourseSummary;
	}


	@JsonIgnore
	public CourseDetails getThis() {
		return this;
	}

	// Using this as a property for the crudMessageMatrixFormatter property
	// editor
	// because getThis() is already used for the timeschedule property editor
	// so it overides crudmessage with timeschedule if we use the same property
	// .
	@JsonIgnore
	public CourseDetails getDetails() {
		return this;
	}

	// Using this as a property for the scheduleterms property editor
	// because courseofferinginstitution is already used for the scheduled terms
	// property editor
	// In order to use the same list for another property we created this.
	@JsonIgnore
	public List<CourseOfferingInstitution> getInstitutionsList() {
		return getCourseOfferingInstitutionList();
	}

    /**
     * get details formatted as a String using propertyFormatter setup to support KRAD rendering
     * @return
     */
    @JsonIgnore
    public String getDetailsFormatted() {
        CrudMessageMatrixFormatter formatter = new CrudMessageMatrixFormatter();
        formatter.setValue(this);
        return formatter.getAsText();
    }

    /**
     * get CourseSummaryDetails as an edited String using a propertyEditor setup to support KRAD rendering
     * @return
     */
    @JsonIgnore
    public String  getCourseSummaryDetailsEdited() {
        ScheduledTermsPropertyEditor editor = new ScheduledTermsPropertyEditor();
        editor.setValue(this.courseSummaryDetails.getScheduledTerms());
        return editor.getAsText();
    }


}