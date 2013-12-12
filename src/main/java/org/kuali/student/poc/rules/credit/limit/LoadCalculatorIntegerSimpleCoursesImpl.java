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
public class LoadCalculatorIntegerSimpleCoursesImpl extends LoadCalculatorAbstractImpl implements LoadCalculator {

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
            totalCourses++;
        }
        load.setTotalCredits(new KualiDecimal (totalCourses));
        return load;
    }
}
