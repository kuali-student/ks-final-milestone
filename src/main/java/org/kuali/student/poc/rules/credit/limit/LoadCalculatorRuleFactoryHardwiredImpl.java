package org.kuali.student.poc.rules.credit.limit;

import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class LoadCalculatorRuleFactoryHardwiredImpl implements LoadCalculatorRuleFactory {

    public LoadCalculatorRuleFactoryHardwiredImpl() {
    }

    public LoadCalculatorRuleFactoryHardwiredImpl(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    private CourseOfferingService courseOfferingService;

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            QName qname = new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseOfferingService = GlobalResourceLoader.getService(qname);
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    @Override
    public LoadCalculator getLoadCalculatorForRuleType (String loadLevelCalcuationTypeKey, ContextInfo contextInfo) 
            throws OperationFailedException, DoesNotExistException {
        if (loadLevelCalcuationTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_CREDITS_INTEGER)) {
            LoadCalculatorIntegerCreditImpl impl = new LoadCalculatorIntegerCreditImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelCalcuationTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_CREDITS_BIG_DECIMAL)) {
            LoadCalculatorBigDecimalCreditImpl impl = new LoadCalculatorBigDecimalCreditImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelCalcuationTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_COURSES_SIMPLE)) {
            LoadCalculatorIntegerSimpleCoursesImpl impl = new LoadCalculatorIntegerSimpleCoursesImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelCalcuationTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_COURSES_TIERED)) {
            LoadCalculatorIntegerTieredCoursesBasedOnCreditImpl impl = new LoadCalculatorIntegerTieredCoursesBasedOnCreditImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        if (loadLevelCalcuationTypeKey.equals(AcademicRecordServiceTypeStateConstants.LOAD_CALC_CODE_4_TIERS)) {
            LoadCalculatorCode4TiersImpl impl = new LoadCalculatorCode4TiersImpl ();
            impl.setCourseOfferingService(this.getCourseOfferingService());
            return impl;
        }
        throw new DoesNotExistException ("unknown/unsupported load calculation " + loadLevelCalcuationTypeKey);
    }
}
