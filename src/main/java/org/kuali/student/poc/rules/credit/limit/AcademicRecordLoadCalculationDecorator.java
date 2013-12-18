package org.kuali.student.poc.rules.credit.limit;

import java.util.List;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.class2.academicrecord.service.decorators.AcademicRecordServiceDecorator;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * This is the decorator that does the load calculation for the academic record service
 */
public class AcademicRecordLoadCalculationDecorator extends AcademicRecordServiceDecorator {

    private CourseRegistrationService courseRegistrationService;
    private LoadCalculatorRuleFactory loadCalculatorRuleFactory;

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public LoadCalculatorRuleFactory getLoadCalculatorRuleFactory() {
        if (this.loadCalculatorRuleFactory == null) {
            loadCalculatorRuleFactory = new LoadCalculatorRuleFactoryHardwiredImpl();
        }
        return loadCalculatorRuleFactory;
    }

    public void setLoadCalculatorRuleFactory(LoadCalculatorRuleFactory loadCalculatorRuleFactory) {
        this.loadCalculatorRuleFactory = loadCalculatorRuleFactory;
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        List<CourseRegistrationInfo> existingCrs =
                this.getCourseRegistrationService().getCourseRegistrationsByStudentAndTerm(personId, termId, contextInfo);

        // get what rule we are using to do the calcualtion
        LoadCalculator calculator = this.getLoadCalculatorRuleFactory().getLoadCalculatorForRuleType(calculationTypeKey, contextInfo);;

        // calculate the load prior to the request
        LoadInfo load = calculator.calculateLoad(existingCrs, personId, contextInfo);
        return load;

    }
}
