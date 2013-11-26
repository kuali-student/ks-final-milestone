package org.kuali.student.poc.rules.credit.limit;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This defines the interface that needs to be implemented to execute a load calculation
 */
public class LoadCalculatorRuleUtility  {

    private CourseOfferingService courseOfferingService;

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public LoadCalculator getLoadCalculatorForRuleType (String loadLevelTypeKey, ContextInfo contextInfo) 
            throws OperationFailedException {
        if (loadLevelTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_CREDITS_INTEGER)) {
            LoadCalculatorIntegerCreditImpl impl = new LoadCalculatorIntegerCreditImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_CREDITS_BIG_DECIMAL)) {
            LoadCalculatorBigDecimalCreditImpl impl = new LoadCalculatorBigDecimalCreditImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_COURSES_SIMPLE)) {
            LoadCalculatorIntegerSimpleCoursesImpl impl = new LoadCalculatorIntegerSimpleCoursesImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_COURSES_TIERED)) {
            LoadCalculatorIntegerTieredCoursesBasedOnCreditImpl impl = new LoadCalculatorIntegerTieredCoursesBasedOnCreditImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_CODE_4_TIERS)) {
            LoadCalculatorCode4TiersImpl impl = new LoadCalculatorCode4TiersImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        throw new OperationFailedException ("unknown/unsupported load calculation " + loadLevelTypeKey);
    }
}
