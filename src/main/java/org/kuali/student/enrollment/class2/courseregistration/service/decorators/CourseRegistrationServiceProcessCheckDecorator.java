/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseregistration.service.decorators;

import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.core.process.krms.proposition.ProcessProposition;

/**
 *
 * @author nwright
 */
public class CourseRegistrationServiceProcessCheckDecorator
        extends CourseRegistrationServiceDecorator {

    public CourseRegistrationServiceProcessCheckDecorator() {
    }

    public CourseRegistrationServiceProcessCheckDecorator(CourseRegistrationService nextDecorator) {
        setNextDecorator(nextDecorator);
    }
    private KRMSEvaluator krmsEvaluator;

    public KRMSEvaluator getKrmsEvaluator() {
        return krmsEvaluator;
    }

    public void setKrmsEvaluator(KRMSEvaluator krmsEvaluator) {
        this.krmsEvaluator = krmsEvaluator;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Proposition prop = new ProcessProposition (ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        Map<String, Object> executionFacts = new LinkedHashMap<String, Object> ();
        executionFacts.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), personId);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        EngineResults engineResults = this.krmsEvaluator.evaluateProposition(prop, executionFacts);
        Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
        if (ex != null) {
            throw new OperationFailedException ("Unexpected exception while executing rules", ex);
        }
        List<ValidationResultInfo> vrs = KRMSEvaluator.extractValidationResults(engineResults);
        return vrs;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String personId, String termId, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Proposition prop = new ProcessProposition (ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM);
        Map<String, Object> executionFacts = new LinkedHashMap<String, Object> ();
        executionFacts.put(RulesExecutionConstants.PERSON_ID_TERM.getName(), personId);
        executionFacts.put(RulesExecutionConstants.ATP_ID_TERM.getName(), termId);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        EngineResults engineResults = this.krmsEvaluator.evaluateProposition(prop, executionFacts);
        Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
        if (ex != null) {
            throw new OperationFailedException ("Unexpected exception while executing rules", ex);
        }
        List<ValidationResultInfo> vrs = KRMSEvaluator.extractValidationResults(engineResults);
        return vrs;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId,
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO: implement in phase II
        return new ArrayList();
    }
    
    
    @Override
    public List<ValidationResultInfo> verifyRegistrationRequestForSubmission(String registrationRequestId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Proposition prop = new ProcessProposition (ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES);
        Map<String, Object> executionFacts = new LinkedHashMap<String, Object> ();
        executionFacts.put(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM.getName(), registrationRequestId);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        EngineResults engineResults = this.krmsEvaluator.evaluateProposition(prop, executionFacts);
        Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
        if (ex != null) {
            throw new OperationFailedException ("Unexpected exception while executing rules", ex);
        }
        List<ValidationResultInfo> vrs = KRMSEvaluator.extractValidationResults(engineResults);
        return vrs;
    }
    
    
}
