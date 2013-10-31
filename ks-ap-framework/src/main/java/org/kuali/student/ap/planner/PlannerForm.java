package org.kuali.student.ap.planner;

import java.math.BigDecimal;
import java.util.List;

public interface PlannerForm extends PlanItemForm {

	boolean isBackup();

	String getCourseNote();

	BigDecimal getCreditsForPlanItem();

	void populateFromPlanItem();

	String getTermNote();

	String getTargetTermId();

	String getCourseCd();

	List<PlannerTerm> getTerms();

}
