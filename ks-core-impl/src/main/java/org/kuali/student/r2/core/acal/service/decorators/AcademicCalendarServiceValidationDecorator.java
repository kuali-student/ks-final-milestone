package org.kuali.student.r2.core.acal.service.decorators;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
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
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.util.ValidationUtils;

public class AcademicCalendarServiceValidationDecorator extends AcademicCalendarServiceDecorator implements HoldsValidator, HoldsDataDictionaryService {
    private DataDictionaryValidator validator;
    private DataDictionaryService dataDictionaryService;

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
    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    @Override
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, ReadOnlyException {
        _academicCalendarFullValidation(academicCalendarKey, academicCalendarInfo, context);
        return getNextDecorator().createAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
    }

    @Override
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        _academicCalendarFullValidation(academicCalendarKey, academicCalendarInfo, context);
        return this.getNextDecorator().updateAcademicCalendar(academicCalendarKey, academicCalendarInfo, context);
    }

    private void _academicCalendarFullValidation(String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this
                    .validateAcademicCalendar(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), academicCalendarTypeKey, academicCalendarInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating academic calendar", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating academic calendar", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationTypeKey, String academicCalendarTypeKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, academicCalendarInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateAcademicCalendar(validationTypeKey, academicCalendarTypeKey, academicCalendarInfo, contextInfo);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating academic calendar", ex);
        }
        return errors;
    }

    @Override
    public HolidayCalendarInfo createHolidayCalendar(String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        _holidayCalendarFullValidation(holidayCalendarInfo, context);
        return this.getNextDecorator().createHolidayCalendar(holidayCalendarTypeKey, holidayCalendarInfo, context);
    }

    @Override
    public HolidayCalendarInfo updateHolidayCalendar(String holidayCalendarId, HolidayCalendarInfo holidayCalendarInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return this.getNextDecorator().updateHolidayCalendar(holidayCalendarId, holidayCalendarInfo, contextInfo);
    }

    private void _holidayCalendarFullValidation(HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException,
            MissingParameterException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateHolidayCalendar(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), null, holidayCalendarInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating holiday calendar", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating holiday calendar", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateHolidayCalendar(String validationTypeKey, String holidayCalendarTypeKey, HolidayCalendarInfo holidayCalendarInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, holidayCalendarInfo, contextInfo);

            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateHolidayCalendar(validationTypeKey, holidayCalendarTypeKey, holidayCalendarInfo, contextInfo);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating holiday calendar", ex);
        }
        return errors;
    }

    @Override
    public TermInfo createTerm(String termTypeKey, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DoesNotExistException, ReadOnlyException {
        _termFullValidation(termInfo, context);
        if (termInfo.getId() != null) {
            throw new ReadOnlyException("ID not allowed when creating term.");
        }
        return this.getNextDecorator().createTerm(termTypeKey, termInfo, context);
    }

    @Override
    public TermInfo updateTerm(String termId, TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        _termFullValidation(termInfo, context);
        return this.getNextDecorator().updateTerm(termId, termInfo, context);
    }

    private void _termFullValidation(TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException,
            PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateTerm(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), termInfo.getTypeKey(), termInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating term", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating term", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateTerm(String validationTypeKey, String termTypeKey, TermInfo termInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, termInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateTerm(validationTypeKey, termTypeKey, termInfo, contextInfo);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating term", ex);
        }
        return errors;
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateId, KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        _keyDateFullValidation(keyDateInfo, context);
        return this.getNextDecorator().updateKeyDate(keyDateId, keyDateInfo, context);
    }

    @Override
    public KeyDateInfo calculateKeyDate(String keyDateId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (StringUtils.isEmpty(keyDateId)) {
            throw new MissingParameterException("keyDateId");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return this.getNextDecorator().calculateKeyDate(keyDateId, contextInfo);
    }

    private void _keyDateFullValidation(KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException,
            MissingParameterException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateKeyDate(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), null, keyDateInfo.getTypeKey(), keyDateInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating key date", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating key date", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateKeyDate(String validationTypeKey, String termId, String keyDateTypeKey, KeyDateInfo keyDateInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, keyDateInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateKeyDate(validationTypeKey, termId, keyDateTypeKey, keyDateInfo, contextInfo);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating key date", ex);
        }
        return errors;
    }

    @Override
    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        _holidayFullValidation(holidayInfo, contextInfo);
        return this.getNextDecorator().createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo, contextInfo);
    }

    @Override
    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        _holidayFullValidation(holidayInfo, context);
        return this.getNextDecorator().updateHoliday(holidayId, holidayInfo, context);
    }

    private void _holidayFullValidation(HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, InvalidParameterException,
            MissingParameterException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateHoliday(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), null, null, holidayInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating holiday", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating holiday", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateHoliday(String validationTypeKey, String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, holidayInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateHoliday(validationTypeKey, holidayCalendarId, holidayTypeKey, holidayInfo, contextInfo);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating holiday", ex);
        }
        return errors;
    }

    @Override
    public List<HolidayInfo> getHolidaysByDateForAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        AcademicCalendarInfo academicCalendar = getAcademicCalendar(academicCalendarId, contextInfo);
        
        if(academicCalendar == null)
            throw new DoesNotExistException("Invalid academicCalendarId in the input parameter");

        if (startDate == null || endDate == null) {
            throw new OperationFailedException("Null dates in the input parameter");
        }

        if (startDate.before(academicCalendar.getStartDate()) || endDate.after(academicCalendar.getEndDate())) {
            throw new OperationFailedException("Invalid date in the input parameter");
        }

        return this.getNextDecorator().getHolidaysByDateForAcademicCalendar(academicCalendarId, startDate, endDate, contextInfo);
    }

}
