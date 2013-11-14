package org.kuali.student.poc.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class LoadCalculatorIntegerCreditImpl extends AbstractLoadCalculator implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        LoadInfo load = this.constructLoadInfo(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_CREDITS,
                actions, loadLevelTypeKey, contextInfo);

        int totalCredits = 0;
        for (CourseRegistrationAction action : actions) {
            if (!this.accept(action, load, actions, loadLevelTypeKey, contextInfo)) {
                continue;
            }
            int credits = this.calcIntCreditsForRegistration(action, load, actions, loadLevelTypeKey, contextInfo);
            totalCredits = totalCredits + credits;
        }
        load.setTotalCredits(totalCredits + "");
        return load;
    }

    protected int calcIntCreditsForRegistration(CourseRegistrationAction action,
            LoadInfo load,
            List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        String creditString = this.getCreditsForRegistration(action, load, actions, loadLevelTypeKey, contextInfo);
        int credits = this.parseCreditsAsInt(creditString);
        return credits;
    }
}
