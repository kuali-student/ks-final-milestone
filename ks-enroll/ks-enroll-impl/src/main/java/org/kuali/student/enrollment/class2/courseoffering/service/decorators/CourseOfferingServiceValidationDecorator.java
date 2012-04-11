package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.FormatOffering;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

import javax.jws.WebParam;

public class CourseOfferingServiceValidationDecorator extends CourseOfferingServiceDecorator implements HoldsValidator, HoldsDataDictionaryService {

    private DataDictionaryValidator validator;
    private DataDictionaryService dataDictionaryService;
    private AcademicCalendarService aCalService;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    @Override
    public void setDataDictionaryService(
            DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(
            String validationType, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, courseOfferingInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors =
                    getNextDecorator().validateCourseOffering(validationType, courseOfferingInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate course offering", ex);
        }
        return errors;
    }

    private void _courseOfferingFullValidation(CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors =
                    this.validateCourseOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseOfferingInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating course offering", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating course offering", ex);
        }
    }

    private void _formatOfferingFullValidation(FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors =
                    this.validateFormatOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), formatOfferingInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating course offering", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating course offering", ex);
        }
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, 
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        try {
            List<ValidationResultInfo> errors = this.validateFormatOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), formatOfferingInfo, context);
            if (errors != null) {
                throw new DataValidationErrorException("Update method failed due to validation errors", errors);
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage());
        }
        return getNextDecorator().updateFormatOffering(formatOfferingId, formatOfferingInfo, context);

    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
            CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        _courseOfferingFullValidation(courseOfferingInfo, context);
        return getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
    }

    //createCourseOffering
    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey,
            CourseOfferingInfo coInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, AlreadyExistsException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        CourseOfferingInfo courseOfferingInfo = getCourseOffering(courseId, context);
        _courseOfferingFullValidation(courseOfferingInfo, context);
        return getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, coInfo, context);
    }

    //deleteCourseOffering
    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            CourseOfferingInfo courseOfferingInfo = getCourseOffering(courseOfferingId, context);
            _courseOfferingFullValidation(courseOfferingInfo, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Error validating course offering", ex);
        }
        return getNextDecorator().deleteCourseOffering(courseOfferingId, context);
    }
    //getCourseOffering

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            CourseOfferingInfo courseOfferingInfo = getCourseOffering(courseOfferingId, context);
            _courseOfferingFullValidation(courseOfferingInfo, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Error validating course offering", ex);
        }
        return getNextDecorator().getCourseOffering(courseOfferingId, context);
    }

    //getCourseOfferingIdsByTerm
    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        //_courseOfferingFullValidation(courseOfferingInfo, context);
        if (null != aCalService.getTerm(termId, context)) {
            return getNextDecorator().getCourseOfferingIdsByTerm(termId, useIncludedTerm, context);
        } else {
            throw new DoesNotExistException("termID: " + termId + " does not exist.");
        }
        //return getNextDecorator().getCourseOfferingIdsByTerm(courseOfferingId, courseOfferingInfo, context);
        //return null;
    }

    //getCourseOfferingByCourseAndTerm
    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            CourseOfferingInfo courseOfferingInfo = getCourseOffering(courseId, context);
            _courseOfferingFullValidation(courseOfferingInfo, context);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("Error validating course offering", ex);
        }
        return getNextDecorator().getCourseOfferingsByCourseAndTerm(courseId, termId, context);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(
            String validationType, FormatOfferingInfo formatOfferingInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, formatOfferingInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors =
                    getNextDecorator().validateFormatOffering(validationType, formatOfferingInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate course offering", ex);
        }
        return errors;
    }

    @Override
    public FormatOfferingInfo createFormatOffering(
            String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo,
            ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateFormatOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), formatOfferingInfo, context);
            if (errors != null) {
                throw new DataValidationErrorException("Create method failed due to validation errors", errors);
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage());
        }
        return getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
    }
}
