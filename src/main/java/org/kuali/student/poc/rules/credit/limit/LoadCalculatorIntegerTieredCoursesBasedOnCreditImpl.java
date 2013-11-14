package org.kuali.student.poc.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This load calculator calculates load in terms of number of full credited courses
 *
 */
public class LoadCalculatorIntegerTieredCoursesBasedOnCreditImpl extends AbstractLoadCalculator implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        // NOTE: this uses the type for Courses indicating that the load is measured in terms of courses not credits
        LoadInfo load = this.constructLoadInfo(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_COURSES,
                actions, loadLevelTypeKey, contextInfo);
        
        int totalCourses = 0;
        for (CourseRegistrationAction action : actions) {
            if (!this.accept(action, load, actions, loadLevelTypeKey, contextInfo)) {
                continue;
            }
            int courses = this.calcIntCoursesForRegistration(action, load, actions, loadLevelTypeKey, contextInfo);
            totalCourses = totalCourses + courses;
        }
        load.setTotalCredits(totalCourses + "");
        return load;
    }

    protected int calcIntCoursesForRegistration(CourseRegistrationAction action,
            LoadInfo load,
            List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        String creditString = this.getCreditsForRegistration(action, load, actions, loadLevelTypeKey, contextInfo);
        // this is an example of the kind of crazy logic schools use.. .in this example any 1 credit courses doesn't count at all
        // course of 2-4 counts as one course and any course with more than 4 credits counts as 2
        // some may exclude not by the credits but by the type of course... i.e. registration for thesis automatically counts as 4 courses
        // and a ROTC course counts as 0 no matter how many credits it has.
        int credits = this.parseCreditsAsInt(creditString);
        if (credits <= 1) {
            return 0;
        }
        if (credits <= 4) {
            return 1;
        }
        return 2;
    }

}
