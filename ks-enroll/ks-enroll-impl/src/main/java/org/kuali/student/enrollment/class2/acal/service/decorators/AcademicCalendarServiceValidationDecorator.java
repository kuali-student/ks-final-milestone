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
            AcademicCalendarInfo acalInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        try{
        	List<ValidationResultInfo> errors = this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), acalInfo, context);
        	 if ( ! errors.isEmpty ())   {
    		    return errors;
		     }
        }catch(PermissionDeniedException ex){
            throw new OperationFailedException();
        }
        
        return this.nextDecorator.validateAcademicCalendar(validationType, acalInfo, context);
    }
    
    @Override
    public List<ValidationResultInfo> validateTerm(String validationType,
            TermInfo termInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        try{
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), termInfo, context);
        }catch(PermissionDeniedException ex){
            throw new OperationFailedException();
        }
        return this.nextDecorator.validateTerm(validationType,termInfo, context);
    }

    
 @Override
 public AcademicCalendarInfo createAcademicCalendar (String academicCalendarKey, AcademicCalendarInfo academicCalendarInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException
 {
  try
  {
   List<ValidationResultInfo> errors = this.validateAcademicCalendar (DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), academicCalendarInfo, context);
   if ( ! errors.isEmpty ())   {
    throw new DataValidationErrorException ("Errors", errors);
   }
  }
  catch (DoesNotExistException ex)
  {
   throw new OperationFailedException("erorr trying to validate", ex);
  }
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
	  try
	  {
	   List<ValidationResultInfo> errors = this.validateAcademicCalendar (DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), academicCalendarInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }
	  
 	return this.nextDecorator.updateAcademicCalendar(academicCalendarKey,academicCalendarInfo, context);
 }

 @Override
 public List<ValidationResultInfo> validateCampusCalendar(
         String validationType, CampusCalendarInfo campusCalendarInfo,
         ContextInfo context) throws DoesNotExistException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException {
 	return this.nextDecorator.validateCampusCalendar(validationType, campusCalendarInfo, context);
 }

 @Override
 public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
         CampusCalendarInfo campusCalendarInfo, ContextInfo context)
 throws AlreadyExistsException, DataValidationErrorException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException {
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateCampusCalendar (DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), campusCalendarInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }
	  
 	return this.nextDecorator.createCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
 }

 @Override
 public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
         CampusCalendarInfo campusCalendarInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {
	 try
	  {
	   List<ValidationResultInfo> errors = this.validateCampusCalendar (DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), campusCalendarInfo, context);
	   if ( ! errors.isEmpty ())   {
	    throw new DataValidationErrorException ("Errors", errors);
	   }
	  }
	  catch (DoesNotExistException ex)
	  {
	   throw new OperationFailedException("erorr trying to validate", ex);
	  }
	  
 	return this.nextDecorator.updateCampusCalendar(campusCalendarKey, campusCalendarInfo, context);
 }

 @Override
 public TermInfo createTerm(String termKey, TermInfo termInfo,
         ContextInfo context) throws AlreadyExistsException,
         DataValidationErrorException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException {
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
	  
 	return this.nextDecorator.createTerm(termKey,termInfo, context);
 }

 @Override
 public TermInfo updateTerm(String termKey, TermInfo termInfo,
         ContextInfo context) throws DataValidationErrorException,
         DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, VersionMismatchException {
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
	  
 	return this.nextDecorator.updateTerm(termKey,termInfo, context);
 }

 @Override
 public List<ValidationResultInfo> validateKeyDate(String validationType,
         KeyDateInfo keyDateInfo, ContextInfo context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException {
	 
 	return this.nextDecorator.validateKeyDate(validationType,keyDateInfo , context);
 }

 @Override
 public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
         KeyDateInfo keyDateInfo, ContextInfo context)
 throws AlreadyExistsException, DataValidationErrorException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException {
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
	  
 	return this.nextDecorator.createKeyDateForTerm(termKey,keyDateKey, keyDateInfo , context);
 }

 @Override
 public KeyDateInfo updateKeyDate(String keyDateKey,
         KeyDateInfo keyDateInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {
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
	  
 	return this.nextDecorator.updateKeyDate(keyDateKey, keyDateInfo , context);
 }

 @Override
 public List<ValidationResultInfo> validateHoliday(String validationType,
         HolidayInfo holidayInfo, ContextInfo context)
         throws DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException {
 	return this.nextDecorator.validateHoliday(validationType,holidayInfo,  context);
 }

 @Override
 public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
         String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
 throws AlreadyExistsException, DataValidationErrorException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException {
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
	  
   	return this.nextDecorator.createHolidayForCampusCalendar(campusCalendarKey,holidayKey,holidayInfo,  context);
 }

 @Override
 public HolidayInfo updateHoliday(String holidayKey,
         HolidayInfo holidayInfo, ContextInfo context)
 throws DataValidationErrorException, DoesNotExistException,
 InvalidParameterException, MissingParameterException,
 OperationFailedException, PermissionDeniedException,
 VersionMismatchException {	 
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
	  
 	return this.nextDecorator.updateHoliday(holidayKey,holidayInfo,  context);
 }

 @Override
 public List<ValidationResultInfo> validateRegistrationDateGroup(
         String validationType,
         RegistrationDateGroupInfo registrationDateGroupInfo,
         ContextInfo context) throws DoesNotExistException,
         InvalidParameterException, MissingParameterException,
         OperationFailedException {
 	return this.nextDecorator.validateRegistrationDateGroup(validationType,registrationDateGroupInfo, context);
 }

 @Override
 public RegistrationDateGroupInfo updateRegistrationDateGroup(
         String termKey,
         RegistrationDateGroupInfo registrationDateGroupInfo,
         ContextInfo context) throws DataValidationErrorException,
         DoesNotExistException, InvalidParameterException,
         MissingParameterException, OperationFailedException,
         PermissionDeniedException, VersionMismatchException {
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
	  
 	return this.nextDecorator.updateRegistrationDateGroup(termKey,registrationDateGroupInfo, context);
 }

}
