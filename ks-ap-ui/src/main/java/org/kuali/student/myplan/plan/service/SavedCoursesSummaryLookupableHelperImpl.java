package org.kuali.student.myplan.plan.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;

public class SavedCoursesSummaryLookupableHelperImpl extends
		PlanItemLookupableHelperBase {

	private static final long serialVersionUID = 2501508416108678512L;

	@SuppressWarnings("unchecked")
	@Override
	protected List<PlannedCourseDataObject> getSearchResults(
			LookupForm lookupForm, Map<String, String> fieldValues,
			boolean unbounded) {
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
		try {
			List<PlannedCourseDataObject> plannedCoursesList = getPlanItems(
					AcademicPlanServiceConstants.ItemCategory.WISHLIST, studentId);
			Collections.sort(plannedCoursesList);
			return plannedCoursesList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
