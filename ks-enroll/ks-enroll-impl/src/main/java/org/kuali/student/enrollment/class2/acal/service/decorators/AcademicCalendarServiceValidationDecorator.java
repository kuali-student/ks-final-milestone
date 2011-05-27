package org.kuali.student.enrollment.class2.acal.service.decorators;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator.ValidationType;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
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


public class AcademicCalendarServiceValidationDecorator extends AcademicCalendarServiceDecorator  implements HoldsValidator, HoldsDataDictionaryService{
    private DataDictionaryValidator validator;
    
    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;        
    }

    
    private DataDictionaryService dataDictionaryService;

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
    public List<ValidationResultInfo> validateAcademicCalendar(String validationType,
            AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
    	return validate(validationType, academicCalendarInfo, context);
    }
    
    @Override
    public List<ValidationResultInfo> validateTerm(String validationType,
            TermInfo termInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
    	return validate(validationType, termInfo, context);
    }

    private void validateAcademicCalendar( AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
    InvalidParameterException, MissingParameterException{
	  try
	  {
	   List<ValidationResultInfo> errors = this.validateAcademicCalendar(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), academicCalendarInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }   	
    }
    
 @Override
 public AcademicCalendarInfo createAcademicCalendar (String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
 {
	 validateAcademicCalendar(academicCalendarInfo, context);
	 
	 return nextDecorator.createAcademicCalendar (academicCalendarKey, academicCalendarInfo, context);
 }
    
 @Override
 public AcademicCalendarInfo updateAcademicCalendar(
         String academicCalendarKey,
         AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {
	validateAcademicCalendar(academicCalendarInfo, context);
	  
 	return this.nextDecorator.updateAcademicCalendar(academicCalendarKey,academicCalendarInfo, context);
 }

 @Override
 public List<ValidationResultInfo> validateCampusCalendar(
         String validationType, CampusCalendarInfo campusCalendarInfo,
         ContextInfo context) throws DoesNotExistException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException {
	 return validate(validationType, campusCalendarInfo, context);
 }

 private void validateCampusCalendar(CampusCalendarInfo campusCalendarInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
 InvalidParameterException, MissingParameterException{
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateCampusCalendar(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), campusCalendarInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }	 
 }
 
 @Override
 public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
         CampusCalendarInfo campusCalendarInfo, ContextInfo context)
 throws AlreadyExistsException, DataValidationErrorException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException {
	 validateCampusCalendar(campusCalendarInfo, context);
	  
 	return this.nextDecorator.createCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
 }

 @Override
 public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
         CampusCalendarInfo campusCalendarInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {
	 validateCampusCalendar(campusCalendarInfo, context);
	  
 	return this.nextDecorator.updateCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
 }
 
 private void validateTerm(TermInfo termInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
 InvalidParameterException, MissingParameterException{
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateTerm(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), termInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }	 
 }
 
 @Override
 public TermInfo createTerm(String termKey, TermInfo termInfo,
         ContextInfo context) throws AlreadyExistsException,
         DataValidationErrorException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
	validateTerm(termInfo, context);
	
 	return this.nextDecorator.createTerm(termKey,termInfo, context);
 }

 @Override
 public TermInfo updateTerm(String termKey, TermInfo termInfo,
         ContextInfo context) throws DataValidationErrorException,
         DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, VersionMismatchException {
	 	validateTerm(termInfo, context);
	  
 		return this.nextDecorator.updateTerm(termKey,termInfo, context);
 }

 @Override
 public List<ValidationResultInfo> validateKeyDate(String validationType,
         KeyDateInfo keyDateInfo, ContextInfo context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException {
	 
	 	return validate(validationType, keyDateInfo, context);
 }

 private void validateKeyDate(KeyDateInfo keyDateInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
 InvalidParameterException, MissingParameterException{
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateKeyDate(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), keyDateInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }	 
 }
 
 @Override
 public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
         KeyDateInfo keyDateInfo, ContextInfo context)
 throws AlreadyExistsException, DataValidationErrorException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException {
	validateKeyDate(keyDateInfo, context);
	  
 	return this.nextDecorator.createKeyDateForTerm(termKey,keyDateKey, keyDateInfo , context);
 }

 @Override
 public KeyDateInfo updateKeyDate(String keyDateKey,
         KeyDateInfo keyDateInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {
	validateKeyDate(keyDateInfo, context);
	  
 	return this.nextDecorator.updateKeyDate(keyDateKey, keyDateInfo , context);
 }

 @Override
 public List<ValidationResultInfo> validateHoliday(String validationType,
         HolidayInfo holidayInfo, ContextInfo context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException {
	 
	 return validate(validationType, holidayInfo, context);
 }

 @Override
 public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
         String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
 throws AlreadyExistsException, DataValidationErrorException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException {
	validateHoliday(holidayInfo,context);
	  
   	return this.nextDecorator.createHolidayForCampusCalendar(campusCalendarKey,holidayKey,holidayInfo,  context);
 }

 private void validateHoliday(HolidayInfo holidayInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
 InvalidParameterException, MissingParameterException{
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateHoliday(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), holidayInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }	 
 }
 
 @Override
 public HolidayInfo updateHoliday(String holidayKey,
         HolidayInfo holidayInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {	 
	validateHoliday(holidayInfo,context);
	  
 	return this.nextDecorator.updateHoliday(holidayKey,holidayInfo,  context);
 }

 @Override
 public List<ValidationResultInfo> validateRegistrationDateGroup(
         String validationType,
         RegistrationDateGroupInfo registrationDateGroupInfo,
         ContextInfo context) throws DoesNotExistException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException {
	 return validate(validationType, registrationDateGroupInfo, context);
 }

 private void validateRegistrationDateGroup(RegistrationDateGroupInfo registrationDateGroupInfo,
         ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
         InvalidParameterException, MissingParameterException{
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateRegistrationDateGroup(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), registrationDateGroupInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }	 
 }
 
 @Override
 public RegistrationDateGroupInfo updateRegistrationDateGroup(
         String termKey,
         RegistrationDateGroupInfo registrationDateGroupInfo,
         ContextInfo context) throws DataValidationErrorException,
         DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, VersionMismatchException {
	validateRegistrationDateGroup(registrationDateGroupInfo, context);
	  
 	return this.nextDecorator.updateRegistrationDateGroup(termKey,registrationDateGroupInfo, context);
 }

 private List<ValidationResultInfo> validate(String validationType, Object info, ContextInfo context)
 throws OperationFailedException, MissingParameterException, InvalidParameterException{
 	List<ValidationResultInfo> errors;
    try{
    	errors = this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), info, context);
    }catch(PermissionDeniedException ex){
        throw new OperationFailedException();
    }
    return errors;
 }
}
