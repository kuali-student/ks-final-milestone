package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.context.ShoppingCartHelper;
import org.kuali.student.myplan.academicplan.dto.PlanItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;

import java.util.ArrayList;
import java.util.List;


public class MockShoppingCartHelper implements ShoppingCartHelper {
    /**
     * Validates that requirements for a list of courses are complete and open for enrollment.
     *
     * @param planItems - A list of planItems to be validated
     * @param studentId - The id of the student
     * @param context
     * @return Status of the validation.
     */
    @Override
    public StatusInfo validateCartContents(List<PlanItemInfo> planItems, String studentId, ContextInfo context) {

        StatusInfo mockStatus = new StatusInfo();
        mockStatus.setSuccess(true);
        mockStatus.setMessage("Validation was successful under mock.");

        return mockStatus;
    }

    /**
     * Validates that requirements for all courses in the shopping cart for a term are complete and open for enrollment.
     *
     * @param learningPlanTypeKey - The plan key for the selected learning plan
     * @param atpId               - The id of the term whose cart is being being validated
     * @param studentId           - The id of the student
     * @param context
     * @return Status of the validation.
     */
    @Override
    public StatusInfo validateCartContents(String learningPlanTypeKey, String atpId, String studentId, ContextInfo context) {

        StatusInfo mockStatus = new StatusInfo();
        mockStatus.setSuccess(true);
        mockStatus.setMessage("Validation was successful under mock.");

        return mockStatus;
    }

    /**
     * Enrolls a student in a list of courses.
     *
     * @param planItems - A list of planItems to be enrolled in
     * @param studentId - The id of the student
     * @param context
     * @return
     */
    @Override
    public StatusInfo enrollCourses(List<PlanItemInfo> planItems, String studentId, ContextInfo context) {

        StatusInfo mockStatus = new StatusInfo();
        mockStatus.setSuccess(true);
        mockStatus.setMessage("Enroll was successful under mock.");

        return mockStatus;
    }

    /**
     * Enrolls a student in all courses in the shopping cart for a term.
     *
     * @param learningPlanTypeKey - The plan key for the selected learning plan
     * @param atpId               - The id of the term whose cart is being being validated
     * @param studentId           - The id of the student
     * @param context
     * @return
     */
    @Override
    public StatusInfo enrollCourses(String learningPlanTypeKey, String atpId, String studentId, ContextInfo context) {

        StatusInfo mockStatus = new StatusInfo();
        mockStatus.setSuccess(true);
        mockStatus.setMessage("Enroll was successful under mock.");

        return mockStatus;
    }

    /**
     * Validates that requirements for all courses in the shopping cart for a term are complete and open for enrollment.
     * Then enrolls the student in the courses.
     *
     * @param planItems - A list of planItems to be enrolled in
     * @param studentId - The id of the student
     * @param context
     * @return
     */
    @Override
    public StatusInfo validateAndEnrollCourses(List<PlanItemInfo> planItems, String studentId, ContextInfo context) {

        StatusInfo mockStatus = new StatusInfo();
        mockStatus.setSuccess(true);
        mockStatus.setMessage("Validation and Enroll was successful under mock.");

        return mockStatus;
    }

    /**
     * Validates that requirements for a list of courses are complete and open for enrollment.
     * Then enrolls the student in the courses.
     *
     * @param learningPlanTypeKey - The plan key for the selected learning plan
     * @param atpId               - The id of the term whose cart is being being validated
     * @param studentId           - The id of the student
     * @param context
     * @return
     */
    @Override
    public StatusInfo validateAndEnrollCourses(String learningPlanTypeKey, String atpId, String studentId, ContextInfo context) {

        StatusInfo mockStatus = new StatusInfo();
        mockStatus.setSuccess(true);
        mockStatus.setMessage("Validation and Enroll was successful under mock.");

        return mockStatus;
    }

    /**
     * Retrieves a list of plan items for a specific students learning plan that have been placed in the shopping cart.
     *
     * @param learningPlanTypeKey - The plan key for the selected learning plan
     * @param atpId               - The id of the term whose cart is being being validated
     * @param studentId           - The id of the student
     * @param context
     * @return A list of plan items
     */
    @Override
    public List<PlanItemInfo> getShoppingCartEntries(String learningPlanTypeKey, String atpId, String studentId, ContextInfo context) {
        List<PlanItemInfo> mockItems = new ArrayList<PlanItemInfo>();

        return mockItems;
    }
}
