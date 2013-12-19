package org.kuali.student.poc.rules.credit.limit;

import java.util.List;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class LoadCalculatorIntegerCreditImpl extends LoadCalculatorAbstractImpl implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        LoadInfo load = this.constructLoadInfo(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_CREDITS,
                courseRegistrations, loadLevelTypeKey, contextInfo);

        int totalCredits = 0;
        for (CourseRegistrationInfo action : courseRegistrations) {
            if (!this.accept(action, load, courseRegistrations, loadLevelTypeKey, contextInfo)) {
                continue;
            }
            int credits = this.calcIntCreditsForRegistration(action, load, courseRegistrations, loadLevelTypeKey, contextInfo);
            totalCredits = totalCredits + credits;
        }
        load.setTotalCredits(new KualiDecimal (totalCredits));
        return load;
    }

    protected int calcIntCreditsForRegistration(CourseRegistrationInfo action,
            LoadInfo load,
            List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        KualiDecimal credits = this.getCreditsForRegistration(action, load, courseRegistrations, loadLevelTypeKey, contextInfo);
        return credits.intValue();
    }
}
