package org.kuali.student.ap.planner;

import java.math.BigDecimal;
import java.util.List;
import org.kuali.student.r2.lum.course.infc.Course;

public interface PlannerForm extends PlanItemForm {

	boolean isBackup();

	String getCourseNote();

	BigDecimal getCreditsForPlanItem();
    BigDecimal getCreditsForPlanItem(Course course);

	void populateFromPlanItem();

	String getTermNote();

	String getTargetTermId();

	String getCourseCd();

	List<PlannerTerm> getTerms();

}
