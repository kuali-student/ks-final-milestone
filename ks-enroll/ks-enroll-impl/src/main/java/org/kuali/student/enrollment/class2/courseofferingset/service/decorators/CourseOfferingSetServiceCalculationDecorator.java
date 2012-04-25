/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.decorators;

import java.util.List;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

public class CourseOfferingSetServiceCalculationDecorator extends CourseOfferingSetServiceDecorator {

    private CourseOfferingService coService;
    private CourseService courseService;
    private AcademicCalendarService acalService;

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    @Override
    public StatusInfo scheduleSoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("scheduleSoc has not been implemented");
    }

    @Override
    public SocInfo rolloverSoc(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // validate the target term
        TermInfo targetTerm = this.acalService.getTerm(targetTermId, context);
        // first create the new soc
        SocInfo sourceSoc = this.getSoc(sourceSocId, context);
        if (sourceSoc.getTermId().equals(targetTermId)) {
            throw new InvalidParameterException("The term of the source soc and the target term must be different");
        }
        SocInfo targetSoc = new SocInfo(sourceSoc);
        targetSoc.setTermId(targetTermId);
        try {
            targetSoc = this.createSoc(targetSoc.getTermId(), targetSoc.getTypeKey(), sourceSoc, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // then build the result so we can track stuff
        SocRolloverResultInfo result = new SocRolloverResultInfo();
        result.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        result.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        result.setSourceSocId(sourceSocId);
        result.setTargetTermId(targetTermId);
        result.setOptionKeys(optionKeys);
        result.setTargetSocId(targetSoc.getId());
        try {
            result = this.createSocRolloverResult(result.getTypeKey(), result, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // create the runner so we can kick it off in another thread
        CourseOfferingRolloverRunner runner = new CourseOfferingRolloverRunner();
        runner.setContext(context);
        runner.setCoService(coService);
        runner.setSocService(this);
        runner.setCourseService(courseService);
        runner.setAcalService(acalService);
        runner.setResult(result);
        Thread thread = new Thread(runner);
        thread.start();
        return targetSoc;
    }

    // this is for setting parameters if using the general batch job result service
//    private AttributeInfo conv2Attr(String key, String value) {
//        AttributeInfo attr = new AttributeInfo();
//        attr.setKey(key);
//        attr.setValue(value);
//        return attr;
//    }
    @Override
    public SocRolloverResultInfo reverseRollover(String rolloverResultId, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {// validate the target term
        SocRolloverResultInfo rolloverResult = this.getSocRolloverResult(rolloverResultId, context);
        if (optionKeys.contains(CourseOfferingSetServiceConstants.REVERSE_JUST_CREATES_OPTION_KEY)) {
            if (!rolloverResult.getOptionKeys().contains(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY)) {
                throw new InvalidParameterException ("You cannot reverse just the creates if the original rollover did not log the course offerings that it successfully created");
            }
        }
        SocRolloverResultInfo reverseResult = new SocRolloverResultInfo (rolloverResult);
        reverseResult.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        reverseResult.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
        reverseResult.setOptionKeys(optionKeys);
       
        try {
            reverseResult = this.createSocRolloverResult(reverseResult.getTypeKey(), reverseResult, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Unexpected", ex);
        } catch (ReadOnlyException ex) {
            throw new OperationFailedException("Unexpected", ex);
        }
        // create the runner so we can kick it off in another thread
        CourseOfferingReverseRolloverRunner runner = new CourseOfferingReverseRolloverRunner();
        runner.setContext(context);
        runner.setCoService(coService);
        runner.setSocService(this);
        runner.setCourseService(courseService);
        runner.setAcalService(acalService);
        runner.setRolloverResult(rolloverResult);
        runner.setReverseResult(reverseResult);
        Thread thread = new Thread(runner);
        thread.start();
        return reverseResult;
    }
}
