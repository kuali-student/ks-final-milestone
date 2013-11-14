package org.kuali.student.poc.rules.credit.limit;

import java.math.BigDecimal;
import java.util.List;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This calculates credits using big decimal 
 * TODO: explicitly manage rounding to ??? decimals
 *
 */
public class LoadCalculatorBigDecimalCreditImpl extends AbstractLoadCalculator implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        LoadInfo load = this.constructLoadInfo(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_CREDITS,
                actions, loadLevelTypeKey, contextInfo);

        BigDecimal totalCredits = BigDecimal.ZERO;
        for (CourseRegistrationAction action : actions) {
            if (!this.accept(action, load, actions, loadLevelTypeKey, contextInfo)) {
                continue;
            }
            BigDecimal credits = this.calcBigDecimalCreditsForRegistration(action, load, actions, loadLevelTypeKey, contextInfo);
            totalCredits = totalCredits.add(credits);
        }
        load.setTotalCredits(totalCredits + "");
        return load;
    }

    protected BigDecimal calcBigDecimalCreditsForRegistration(CourseRegistrationAction action,
            LoadInfo load,
            List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        String creditString = this.getCreditsForRegistration(action, load, actions, loadLevelTypeKey, contextInfo);
        BigDecimal credits = this.parseCreditsAsBigDecimal(creditString);
        return credits;
    }

}
