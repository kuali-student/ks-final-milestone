package org.kuali.student.enrollment.class2.acal.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.service.util.ValidationUtils;


public class AcademicCalendarServiceValidationDecorator extends AcademicCalendarServiceDecorator  implements HoldsValidator, HoldsDataDictionaryService
{
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
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        return dataDictionaryService.getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
            throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        return this.dataDictionaryService.getDataDictionaryEntryKeys(context);
    }
    
    
    @Override
    public AcademicCalendarInfo createAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
   	 	_academicCalendarFullValidation(academicCalendarInfo, context);
   	 	return getNextDecorator().createAcademicCalendar (academicCalendarKey, academicCalendarInfo, context);
    }
       
    @Override
    public AcademicCalendarInfo updateAcademicCalendar(String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	_academicCalendarFullValidation(academicCalendarInfo, context);
    	return this.getNextDecorator().updateAcademicCalendar(academicCalendarKey,academicCalendarInfo, context);
    }

    private void _academicCalendarFullValidation(AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
    	try {
    		List<ValidationResultInfo> errors = this.validateAcademicCalendar(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), academicCalendarInfo, context);
    		if ( ! errors.isEmpty() ) {
    			throw new DataValidationErrorException ("Error(s) occurred validating academic calendar", errors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating academic calendar", ex);
    	}   	
    }
    
    @Override
    public List<ValidationResultInfo> validateAcademicCalendar(String validationType, AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
            errors = ValidationUtils.validateInfo(validator, validationType, academicCalendarInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateAcademicCalendar(validationType, academicCalendarInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating academic calendar", ex);
    	}
    	return errors;
    }

    
    @Override
    public CampusCalendarInfo createCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
   	 	_campusCalendarFullValidation(campusCalendarInfo, context);
    	return this.getNextDecorator().createCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
    }

    @Override
    public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey, CampusCalendarInfo campusCalendarInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
   	 	_campusCalendarFullValidation(campusCalendarInfo, context);
    	return this.getNextDecorator().updateCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
    }

	private void _campusCalendarFullValidation(CampusCalendarInfo campusCalendarInfo, ContextInfo context)
			throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
			List<ValidationResultInfo> errors = this.validateCampusCalendar(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), campusCalendarInfo, context);
			if ( ! errors.isEmpty() ) {
				throw new DataValidationErrorException ("Error(s) occurred validating campus calendar", errors);
			}
		}
		catch (DoesNotExistException ex) {
			throw new OperationFailedException("Error validating campus calendar", ex);
		}	 
	}
    
	@Override
	public List<ValidationResultInfo> validateCampusCalendar(String validationType, CampusCalendarInfo campusCalendarInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, campusCalendarInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateCampusCalendar(validationType, campusCalendarInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating campus calendar", ex);
    	}
    	return errors;
	}
    
    

	@Override
	public TermInfo createTerm(String termKey, TermInfo termInfo, ContextInfo context)
	 		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		_termFullValidation(termInfo, context);
		return this.getNextDecorator().createTerm(termKey,termInfo, context);
	 }

	@Override
	public TermInfo updateTerm(String termKey, TermInfo termInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		_termFullValidation(termInfo, context);
		return this.getNextDecorator().updateTerm(termKey,termInfo, context);
	}
	
	private void _termFullValidation(TermInfo termInfo, ContextInfo context)
			throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
			List<ValidationResultInfo> errors = this.validateTerm(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), termInfo, context);
			if ( ! errors.isEmpty() ) {
				throw new DataValidationErrorException ("Error(s) occurred validating term", errors);
			}
		}
		catch (DoesNotExistException ex) {
			throw new OperationFailedException("Error validating term", ex);
		}	 
	}
    
    @Override
    public List<ValidationResultInfo> validateTerm(String validationType, TermInfo termInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, termInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateTerm(validationType, termInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating term", ex);
    	}
    	return errors;
    }

    

    @Override
    public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	_keyDateFullValidation(keyDateInfo, context);
    	return this.getNextDecorator().createKeyDateForTerm(termKey,keyDateKey, keyDateInfo , context);
    }

    @Override
    public KeyDateInfo updateKeyDate(String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	_keyDateFullValidation(keyDateInfo, context);
    	return this.getNextDecorator().updateKeyDate(keyDateKey, keyDateInfo , context);
    }
 
    private void _keyDateFullValidation(KeyDateInfo keyDateInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
    	try {
    		List<ValidationResultInfo> errors = this.validateKeyDate(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), keyDateInfo, context);
    		if ( ! errors.isEmpty() ) {
    			throw new DataValidationErrorException ("Error(s) occurred validating key date", errors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating key date", ex);
    	}	 
    }
    
	@Override
	public List<ValidationResultInfo> validateKeyDate(String validationType, KeyDateInfo keyDateInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, keyDateInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateKeyDate(validationType, keyDateInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating key date", ex);
    	}
    	return errors;
	}


	@Override
	public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey, String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		_holidayFullValidation(holidayInfo,context);  
	  	return this.getNextDecorator().createHolidayForCampusCalendar(campusCalendarKey,holidayKey,holidayInfo,  context);
	}
	
	@Override
	public HolidayInfo updateHoliday(String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {	 
		_holidayFullValidation(holidayInfo,context);
		return this.getNextDecorator().updateHoliday(holidayKey,holidayInfo,  context);
	}

	private void _holidayFullValidation(HolidayInfo holidayInfo, ContextInfo context)
			throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
			List<ValidationResultInfo> errors = this.validateHoliday(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), holidayInfo, context);
			if ( ! errors.isEmpty ())   {
				throw new DataValidationErrorException ("Error(s) occurred validating holiday", errors);
			}
		}
		catch (DoesNotExistException ex) {
			throw new OperationFailedException("Error validating holiday", ex);
		}	 
	}
 
	@Override
	public List<ValidationResultInfo> validateHoliday(String validationType, HolidayInfo holidayInfo, ContextInfo context)
	        throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, holidayInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateHoliday(validationType, holidayInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating holiday", ex);
    	}
    	return errors;
	}

	 
	@Override
	public RegistrationDateGroupInfo updateRegistrationDateGroup(String termKey, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		_validateRegistrationDateGroup(registrationDateGroupInfo, context);  
		return this.getNextDecorator().updateRegistrationDateGroup(termKey,registrationDateGroupInfo, context);
	}

	private void _validateRegistrationDateGroup(RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context)
			throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
			List<ValidationResultInfo> errors = this.validateRegistrationDateGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), registrationDateGroupInfo, context);
			if ( ! errors.isEmpty ()) {
				throw new DataValidationErrorException ("Error(s) occurred validating registration date group", errors);
			}
		}
		catch (DoesNotExistException ex) {
			throw new OperationFailedException("Error validating registration date group", ex);
		}	 
	}
	
 	@Override
	public List<ValidationResultInfo> validateRegistrationDateGroup(String validationType, RegistrationDateGroupInfo registrationDateGroupInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, registrationDateGroupInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateRegistrationDateGroup(validationType, registrationDateGroupInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating registration date group", ex);
    	}
    	return errors;
	}

}
