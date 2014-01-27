package org.kuali.student.enrollment.rules.credit.limit;

import java.math.BigDecimal;
import java.util.List;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This calculates credits using big decimal 
 * TODO: explicitly manage rounding to ??? decimals
 *
 */
public class LoadCalculatorBigDecimalCreditImpl extends LoadCalculatorAbstractImpl implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        LoadInfo load = this.constructLoadInfo(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_CREDITS,
                courseRegistrations, loadLevelTypeKey, contextInfo);

        KualiDecimal totalCredits = new KualiDecimal (BigDecimal.ZERO);
        for (CourseRegistrationInfo action : courseRegistrations) {
            if (!this.accept(action, load, courseRegistrations, loadLevelTypeKey, contextInfo)) {
                continue;
            }
            KualiDecimal credits = this.calcBigDecimalCreditsForRegistration(action, load, courseRegistrations, loadLevelTypeKey, contextInfo);
            totalCredits = totalCredits.add(credits);
        }
        load.setTotalCredits(totalCredits);
        return load;
    }

    protected KualiDecimal calcBigDecimalCreditsForRegistration(CourseRegistrationInfo action,
            LoadInfo load,
            List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        KualiDecimal credits = this.getCreditsForRegistration(action, load, courseRegistrations, loadLevelTypeKey, contextInfo);
        return credits;
    }

}
