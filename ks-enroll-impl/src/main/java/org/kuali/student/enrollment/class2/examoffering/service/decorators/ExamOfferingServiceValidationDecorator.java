package org.kuali.student.enrollment.class2.examoffering.service.decorators;

import org.kuali.student.enrollment.class2.exam.service.decorators.ExamServiceDecorator;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

import java.util.List;

public class ExamOfferingServiceValidationDecorator
        extends ExamOfferingServiceDecorator
        implements HoldsValidator {

    private DataDictionaryValidator validator;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateExamOffering( String termId,  String examId,  String examTypeKey,  String validationTypeKey,  ExamOfferingInfo examOfferingInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(termId, "termId");
        checkForMissingParameter(examId, "examId");
        checkForMissingParameter(examTypeKey, "examTypeKey");
        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(examOfferingInfo, "examOfferingInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, examOfferingInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateExamOffering(termId,   examId, examTypeKey,
                    validationTypeKey, examOfferingInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ExamOfferingInfo createExamOffering( String termId,  String examId,  String examTypeKey,  ExamOfferingInfo examOfferingInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkForMissingParameter(termId, "termId");
        checkForMissingParameter(examId, "examId");
        checkForMissingParameter(examTypeKey, "examTypeKey");
        checkForMissingParameter(examOfferingInfo, "examOfferingInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        if (!examTypeKey.equals(examOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException(examTypeKey + " does not match the corresponding value in the object " + examOfferingInfo.getTypeKey());
        }
        if (!termId.equals(examOfferingInfo.getExamPeriodId())) {
            throw new InvalidParameterException(termId + " does not match the corresponding value in the object " + examOfferingInfo.getExamPeriodId());
        }
        if (!examId.equals(examOfferingInfo.getExamId())) {
            throw new InvalidParameterException(examId + " does not match the corresponding value in the object " + examOfferingInfo.getExamId());
        }
        List<ValidationResultInfo> errors = validateExamOffering(termId,   examId, examTypeKey, DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), examOfferingInfo, contextInfo);
        if (checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().createExamOffering(  termId,   examId,   examTypeKey,   examOfferingInfo,   contextInfo);
    }

    @Override
    public ExamOfferingInfo updateExamOffering( String examOfferingId,  ExamOfferingInfo examOfferingInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        checkForMissingParameter(examOfferingId, "examOfferingId");
        checkForMissingParameter(examOfferingInfo, "examOfferingInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        if (!examOfferingId.equals(examOfferingInfo.getId())) {
            throw new InvalidParameterException(examOfferingId + " does not match the corresponding value in the object " + examOfferingInfo.getId());
        }
        List<ValidationResultInfo> errors = validateExamOffering(examOfferingInfo.getExamPeriodId(), examOfferingInfo.getExamId(),examOfferingInfo.getTypeKey(),DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), examOfferingInfo, contextInfo);
        if (checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().updateExamOffering(  examOfferingId,   examOfferingInfo,   contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateExamOfferingRelation( String formatOfferingId,  String examOfferingId,  String examOfferingTypeKey,  String validationTypeKey,  ExamOfferingRelationInfo examOfferingRelationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(formatOfferingId, "formatOfferingId");
        checkForMissingParameter(examOfferingId, "examOfferingId");
        checkForMissingParameter(examOfferingTypeKey, "examOfferingTypeKey");
        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(examOfferingRelationInfo, "examOfferingRelationInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, examOfferingRelationInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateExamOfferingRelation(  formatOfferingId,   examOfferingId,   examOfferingTypeKey,   validationTypeKey,   examOfferingRelationInfo,   contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ExamOfferingRelationInfo createExamOfferingRelation( String formatOfferingId,  String examOfferingId,  String examOfferingTypeKey,  ExamOfferingRelationInfo examOfferingRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkForMissingParameter(formatOfferingId, "formatOfferingId");
        checkForMissingParameter(examOfferingId, "examOfferingId");
        checkForMissingParameter(examOfferingTypeKey, "examOfferingTypeKey");
        checkForMissingParameter(examOfferingRelationInfo, "examOfferingRelationInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        if (!formatOfferingId.equals(examOfferingRelationInfo.getFormatOfferingId())) {
            throw new InvalidParameterException(formatOfferingId + " does not match the corresponding value in the object " + examOfferingRelationInfo.getFormatOfferingId());
        }
        if (!examOfferingId.equals(examOfferingRelationInfo.getExamOfferingId())) {
            throw new InvalidParameterException(examOfferingId + " does not match the corresponding value in the object " + examOfferingRelationInfo.getExamOfferingId());
        }
        if (!examOfferingTypeKey.equals(examOfferingRelationInfo.getTypeKey())) {
            throw new InvalidParameterException(examOfferingTypeKey + " does not match the corresponding value in the object " + examOfferingRelationInfo.getTypeKey());
        }

        List<ValidationResultInfo> errors = validateExamOfferingRelation(  formatOfferingId,   examOfferingId,   examOfferingTypeKey,   DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),   examOfferingRelationInfo,   contextInfo);
        if (checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().createExamOfferingRelation(  formatOfferingId,   examOfferingId,   examOfferingTypeKey,   examOfferingRelationInfo,   contextInfo);
    }

    @Override
    public ExamOfferingRelationInfo updateExamOfferingRelation( String examOfferingRelationId,  ExamOfferingRelationInfo examOfferingRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        checkForMissingParameter(examOfferingRelationId, "examOfferingRelationId");
        checkForMissingParameter(examOfferingRelationInfo, "examOfferingRelationInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        if (!examOfferingRelationId.equals(examOfferingRelationInfo.getId())) {
            throw new InvalidParameterException(examOfferingRelationId + " does not match the corresponding value in the object " + examOfferingRelationInfo.getId());
        }
        List<ValidationResultInfo> errors = validateExamOfferingRelation(  examOfferingRelationInfo.getFormatOfferingId(),   examOfferingRelationInfo.getExamOfferingId(),   examOfferingRelationInfo.getTypeKey(),   DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(),   examOfferingRelationInfo,   contextInfo);
        if (checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }

        return getNextDecorator().updateExamOfferingRelation(  examOfferingRelationId,   examOfferingRelationInfo,   contextInfo);
    }

    private static boolean checkForErrors(List<ValidationResultInfo> errors) {

        if (errors != null && !errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                if (error.isError()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }


}
