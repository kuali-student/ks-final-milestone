/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.class2.courseregistration.service.decorators.CourseRegistrationServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.context.CourseRegistrationProcessContextInfo;
import org.kuali.student.r2.core.process.evaluator.ProcessEvaluator;

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

    private ProcessEvaluator<CourseRegistrationProcessContextInfo> processEvaluator;

    public ProcessEvaluator<CourseRegistrationProcessContextInfo> getProcessEvaluator() {
        return processEvaluator;
    }

    public void setProcessEvaluator(ProcessEvaluator<CourseRegistrationProcessContextInfo> processEvaluator) {
        this.processEvaluator = processEvaluator;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibility(String studentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CourseRegistrationProcessContextInfo processContext = CourseRegistrationProcessContextInfo.createForRegistrationEligibility(studentId, null);
        processContext.setProcessKey(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        List<? extends ValidationResult> results = this.processEvaluator.evaluate(processContext, context);
        List<ValidationResultInfo> infos = new ArrayList<ValidationResultInfo>(results.size());
        for (ValidationResult vr : results) {
            infos.add(new ValidationResultInfo(vr));
        }
        return infos;
    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibilityForTerm(String studentId, String termKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CourseRegistrationProcessContextInfo processContext = CourseRegistrationProcessContextInfo.createForRegistrationEligibility(studentId, termKey);
        processContext.setProcessKey(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM);
        List<? extends ValidationResult> results;
        results = this.processEvaluator.evaluate(processContext, context);
        List<ValidationResultInfo> infos = new ArrayList<ValidationResultInfo>(results.size());
        for (ValidationResult vr : results) {
            infos.add(new ValidationResultInfo(vr));
        }
        return infos;

    }

    @Override
    public List<ValidationResultInfo> checkStudentEligibiltyForCourseOffering(String studentId, String courseOfferingId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO: implement in phase II
        return new ArrayList();
    }
}
