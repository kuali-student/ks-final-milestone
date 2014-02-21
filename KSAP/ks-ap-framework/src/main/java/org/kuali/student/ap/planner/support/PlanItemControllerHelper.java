package org.kuali.student.ap.planner.support;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.planner.PlanItemForm;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.lum.course.infc.Course;

public final class PlanItemControllerHelper {

	public static LearningPlan getAuthorizedLearningPlan(PlanItemForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		if (KsapFrameworkServiceLocator.getUserSessionHelper().isAdviser()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Advisor access denied");
			return null;
		}

		LearningPlan plan = form.getLearningPlan();
		if (plan == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Invalid learning plan ID " + form.getLearningPlanId());
			return null;
		}

		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();
		if (!studentId.equals(plan.getStudentId())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, request.getRemoteUser()
					+ " is not allowed to update plan " + plan.getId());
			return null;
		}

		return plan;
	}

	public static PlanItem getValidatedPlanItem(PlanItemForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		LearningPlan plan = getAuthorizedLearningPlan(form, request, response);
		if (plan == null)
			return null;

		String planItemId = form.getPlanItemId();

		PlanItem planItem = form.getPlanItem();
		if (planItem == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid plan item ID " + form.getPlanItemId());
			return null;
		}

		if (!planItem.getLearningPlanId().equals(plan.getId())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Plan item " + planItemId + " is not associated with learning plan " + plan.getId() + ", found "
							+ planItem.getLearningPlanId());
			return null;
		}

		if (!PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Plan item " + planItemId
					+ " does not refer to a course, found " + planItem.getRefObjectType());
			return null;
		}

		Course course = form.getCourse();
		if (course == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Plan item " + planItemId
					+ " does not refer to a course, found " + planItem.getRefObjectId());
			return null;
		}

		AcademicPlanServiceConstants.ItemCategory expectedCategory = form.getExpectedPlanItemCategory();
		if (expectedCategory != null && !planItem.getTypeKey().equals(expectedCategory)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Plan item " + planItemId + " not of expected type "
					+ expectedCategory + ", found " + planItem.getTypeKey());
			return null;
		}

		String expectedTermId = form.getTermId();
		if (expectedTermId != null) {
            try{
                String planPeriod = KSCollectionUtils.getRequiredZeroElement(planItem.getPlanPeriods());
                if (!expectedTermId.equals(planPeriod)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Plan item " + planItemId
                            + " not from expected term " + expectedCategory + ", found " + planItem.getTypeKey());
                    return null;
			}
            }catch (OperationFailedException e){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Plan item " + planItemId
                        + " not from expected term " + expectedCategory + ", found " + planItem.getTypeKey());
                return null;
            }
		}

		return planItem;
	}

	private PlanItemControllerHelper() {
	}

}
