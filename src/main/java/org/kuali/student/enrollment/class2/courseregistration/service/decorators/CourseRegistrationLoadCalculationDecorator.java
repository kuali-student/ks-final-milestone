package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

import java.util.List;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.class2.courseregistration.service.decorators.CourseRegistrationServiceDecorator;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CreditLoadInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.poc.rules.credit.limit.AcademicRecordServiceTypeStateConstants;
import org.kuali.student.poc.rules.credit.limit.LoadCalculator;
import org.kuali.student.poc.rules.credit.limit.LoadCalculatorRuleFactory;
import org.kuali.student.poc.rules.credit.limit.LoadCalculatorRuleFactoryHardwiredImpl;
import org.kuali.student.poc.rules.credit.limit.RegistrationRequestMerger;
import org.kuali.student.poc.rules.credit.limit.RegistrationRequestMergerImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * This defines the interface that needs to be implemented to execute a load calculation
 */
public class CourseRegistrationLoadCalculationDecorator extends CourseRegistrationServiceDecorator {

    private CourseOfferingService courseOfferingService;
    private RegistrationRequestMerger registrationRequestMerger;
    private LoadCalculatorRuleFactory loadCalculatorRuleFactory;

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
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

    public RegistrationRequestMerger getRegistrationRequestMerger() {
        if (registrationRequestMerger == null) {
            registrationRequestMerger = new RegistrationRequestMergerImpl(this.getCourseOfferingService());
        }
        return registrationRequestMerger;
    }

    public void setRegistrationRequestMerger(RegistrationRequestMerger registrationRequestMerger) {
        this.registrationRequestMerger = registrationRequestMerger;
    }
    // defaults to the simple logic 
    private String loadLevelTypeKey = AcademicRecordServiceTypeStateConstants.LOAD_CALC_CREDITS_INTEGER;

    public String getLoadLevelTypeKey() {
        return loadLevelTypeKey;
    }

    public void setLoadLevelTypeKey(String loadLevelTypeKey) {
        this.loadLevelTypeKey = loadLevelTypeKey;
    }

    @Override
    public CreditLoadInfo calculateCreditLoadForStudentRegistrationRequest(String registrationRequestId, String studentId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        RegistrationRequestMerger merger = this.getRegistrationRequestMerger();

        // get current request and current courses if any
        RegistrationRequestInfo currentRequest = this.getRegistrationRequest(registrationRequestId, contextInfo);
        List<CourseRegistrationInfo> existingCrs = this.getCourseRegistrationsByStudentAndTerm(studentId,
                currentRequest.getTermId(),
                contextInfo);
        // use an empty request just so we can get a list of existing Crs as a list of courseRegistrations
        RegistrationRequestInfo emptyRequest = new RegistrationRequestInfo();
        List<CourseRegistrationInfo> beforeRegistrations = merger.simulate(emptyRequest, existingCrs, true, contextInfo);

        // get what rule we are using to do the calcualtion
        LoadCalculator calculator = this.getCalculatorForRuleType(this.getLoadLevelTypeKey(), contextInfo);

        // calculate the load prior to the request
        LoadInfo beforeLoad = calculator.calculateLoad(beforeRegistrations, studentId, contextInfo);

        // calculate the load assuming the request went through
        List<CourseRegistrationInfo> afterRegistrations = merger.simulate(currentRequest, existingCrs, true, contextInfo);
        LoadInfo afterLoad = calculator.calculateLoad(afterRegistrations, studentId, contextInfo);

        // compute the before and after so we can get the difference
        KualiDecimal beforeCredits = beforeLoad.getTotalCredits();
        KualiDecimal afterCredits = afterLoad.getTotalCredits();
        CreditLoadInfo creditLoad = new CreditLoadInfo();
        creditLoad.setStudentId(studentId);
        creditLoad.setCreditLimit(this.calcStudentCreditLimit(studentId, contextInfo));
        creditLoad.setCreditLoad(beforeCredits);
        KualiDecimal addlCredits = new KualiDecimal(afterCredits.bigDecimalValue().subtract(beforeCredits.bigDecimalValue()));
        creditLoad.setAdditionalCredits(addlCredits);
        return creditLoad;
    }

    private KualiDecimal calcStudentCreditLimit(String studentId, ContextInfo contextInfo) throws OperationFailedException {
//        TODO: call GES to get this
//                ??? do we return override if student had an exemption here?
        return new KualiDecimal(12);
    }

    private LoadCalculator getCalculatorForRuleType(String loadLevelTypeKey, ContextInfo contextInfo)
            throws OperationFailedException,
            DoesNotExistException {
        return this.getLoadCalculatorRuleFactory().getLoadCalculatorForRuleType(loadLevelTypeKey, contextInfo);
    }

    protected int parseCreditsAsInt(String creditString) throws OperationFailedException {
        try {
            int credits = Integer.parseInt(creditString);
            return credits;
        } catch (NumberFormatException ex) {
            throw new OperationFailedException("this implementation only supports integer credits: " + creditString, ex);
        }
    }

    
    
}
