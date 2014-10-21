package org.kuali.student.ap.planner.form;

import org.kuali.student.ap.coursesearch.dataobject.CourseDetailsPopoverWrapper;
import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl2;
import org.kuali.student.ap.planner.PlannerTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends the default planner form to tie in course summary details from course
 * search in support of the course summary dialog.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 0.7.6
 */
public class PlannerFormImpl extends DefaultPlannerForm {

	private static final long serialVersionUID = -3275492339094049260L;

	private transient CourseDetailsPopoverWrapper courseSummaryDetails;

    private boolean loadCalendar;

    public PlannerFormImpl(){
        this.setFocusTermIndex(-1);
    }

	@Override
	public void setLearningPlanId(String learningPlanId) {
		super.setLearningPlanId(learningPlanId);
		this.courseSummaryDetails = null;
	}

	@Override
	public void setPlanItemId(String planItemId) {
		super.setPlanItemId(planItemId);
		this.courseSummaryDetails = null;
	}

	@Override
	public void setCourseId(String courseId) {
		super.setCourseId(courseId);
		this.courseSummaryDetails = null;
	}

	public CourseDetailsPopoverWrapper getCourseSummaryDetails() {
		if (courseSummaryDetails == null && getCourseId() != null)
			courseSummaryDetails = new CourseDetailsInquiryHelperImpl2()
					.retrieveCoursePopoverDetails(getCourseId());

		return courseSummaryDetails;
	}

    public boolean isLoadCalendar() {
        return loadCalendar;
    }

    public void setLoadCalendar(boolean loadCalendar) {
        this.loadCalendar = loadCalendar;
    }


    @Override
    public List<PlannerTerm> getTerms() {
        if(isLoadCalendar()){
            return super.getTerms();
        }
        return new ArrayList<PlannerTerm>();
    }
}
