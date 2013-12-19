package org.kuali.student.poc.rules.credit.limit;

import java.util.List;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This load calculator calculates load in terms of number of full credited courses
 *
 */
public class LoadCalculatorIntegerTieredCoursesBasedOnCreditImpl extends LoadCalculatorAbstractImpl implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        // NOTE: this uses the type for Courses indicating that the load is measured in terms of courses not credits
        LoadInfo load = this.constructLoadInfo(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_COURSES,
                courseRegistrations, loadLevelTypeKey, contextInfo);
        
        int totalCourses = 0;
        for (CourseRegistrationInfo action : courseRegistrations) {
            if (!this.accept(action, load, courseRegistrations, loadLevelTypeKey, contextInfo)) {
                continue;
            }
            int courses = this.calcIntCoursesForRegistration(action, load, courseRegistrations, loadLevelTypeKey, contextInfo);
            totalCourses = totalCourses + courses;
        }
        load.setTotalCredits(new KualiDecimal (totalCourses));
        return load;
    }

    protected int calcIntCoursesForRegistration(CourseRegistrationInfo action,
            LoadInfo load,
            List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        KualiDecimal creditsKD = this.getCreditsForRegistration(action, load, courseRegistrations, loadLevelTypeKey, contextInfo);
        // this is an example of the kind of crazy logic schools use.. .in this example any 1 credit courses doesn't count at all
        // course of 2-4 counts as one course and any course with more than 4 credits counts as 2
        // some may exclude not by the credits but by the type of course... i.e. registration for thesis automatically counts as 4 courses
        // and a ROTC course counts as 0 no matter how many credits it has.
        int credits = creditsKD.intValue();
        if (credits <= 1) {
            return 0;
        }
        if (credits <= 4) {
            return 1;
        }
        return 2;
    }

}
