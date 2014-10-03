package org.kuali.student.enrollment.rules.credit.limit;

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
public class LoadCalculatorCode4TiersImpl extends LoadCalculatorAbstractImpl implements LoadCalculator {

    @Override
    public LoadInfo calculateLoad(List<CourseRegistrationInfo> courseRegistrations,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        LoadCalculatorIntegerCreditImpl creditCalculator = new LoadCalculatorIntegerCreditImpl();
        creditCalculator.setCourseOfferingService(this.getCourseOfferingService());
        LoadInfo creditLoad = creditCalculator.calculateLoad(courseRegistrations, loadLevelTypeKey, contextInfo);

        // Note: this logic is often much more complicated than this... 
        // for example if the student is taking a thesis course
        // the load is automatically pegged to Full Time
        // OR... if the term is SUMMER (instead of fall or spring...) the tiers for FT/HT is different 
        // 
        LoadInfo load = new LoadInfo(creditLoad);
        load.setTypeKey(AcademicRecordServiceTypeStateConstants.LOAD_TYPE_CODE_4_TIER);
        int credits = creditLoad.getTotalCredits().intValue();
//      TODO: KSENROLL-11719 get the cutoff points from the GES
//      Might want to add a string field so we can store the level code in a string field instead of as a number.
        if (credits == 0) {
            load.setTotalCredits(new KualiDecimal (1));
//            load.setTotalCredits(AcademicRecordServiceTypeStateConstants.LOAD_CODE_4_TIER_1_NO_LOAD);
            return load;
        }
        if (credits <= 5) {
            load.setTotalCredits(new KualiDecimal (2));
//            load.setTotalCredits(AcademicRecordServiceTypeStateConstants.LOAD_CODE_4_TIER_2_LT_HT);
            return load;
        }
        if (credits <= 11) {
            load.setTotalCredits(new KualiDecimal (3));
//            load.setTotalCredits(AcademicRecordServiceTypeStateConstants.LOAD_CODE_4_TIER_3_HT);
            return load;
        }
        load.setTotalCredits(new KualiDecimal (4));
//        load.setTotalCredits(AcademicRecordServiceTypeStateConstants.LOAD_CODE_4_TIER_4_FT);
        return load;
    }
}
