package org.kuali.student.myplan.plan.service;

import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.myplan.plan.PlanConstants;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.utils.UserSessionHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SavedCoursesSummaryLookupableHelperImpl extends PlanItemLookupableHelperBase {

    @Override
    protected List<PlannedCourseDataObject> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        String studentId = UserSessionHelper.getStudentId();
        try {
            List<PlannedCourseDataObject> plannedCoursesList = getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST, true, studentId);
            Collections.sort(plannedCoursesList);
            return plannedCoursesList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
