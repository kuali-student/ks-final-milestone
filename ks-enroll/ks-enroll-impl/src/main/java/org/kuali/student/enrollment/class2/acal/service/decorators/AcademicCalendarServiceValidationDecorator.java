package org.kuali.student.enrollment.class2.acal.service.decorators;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
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
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), acalInfo, context);
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
    
    
}
