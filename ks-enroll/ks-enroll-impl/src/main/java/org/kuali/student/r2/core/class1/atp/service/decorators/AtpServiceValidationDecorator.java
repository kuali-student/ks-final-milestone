package org.kuali.student.r2.core.class1.atp.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
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
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpServiceDecorator;

public class AtpServiceValidationDecorator extends AtpServiceDecorator  implements HoldsValidator, HoldsDataDictionaryService{
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
    public List<ValidationResultInfo> validateAtp(
            String validationType,
            AtpInfo atpInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
    	return validate(validationType, atpInfo, context);
    }
    
    private void validateAtp( AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, OperationFailedException, 
    InvalidParameterException, MissingParameterException{
	  try
	  {
	   List<ValidationResultInfo> errors = this.validateAtp(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString (), atpInfo, context);
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
	public AtpInfo createAtp(
			String atpKey,
			AtpInfo atpInfo,
			ContextInfo context)
	throws AlreadyExistsException,
	DataValidationErrorException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		validateAtp(atpInfo, context);
		return this.nextDecorator.createAtp(atpKey ,atpInfo,  context);
	}

	@Override
	public AtpInfo updateAtp(
			String atpKey,
			AtpInfo atpInfo,
			ContextInfo context)
	throws DataValidationErrorException,
	DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException,
	VersionMismatchException {
		validateAtp(atpInfo, context);
		return this.nextDecorator.updateAtp(atpKey ,atpInfo,  context);
	}

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        
        try{
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), milestoneInfo, context);
        } catch(PermissionDeniedException ex){
            throw new OperationFailedException("Validation of Milestone failed due to Permission denied exception", ex);
        }
        
        return this.nextDecorator.validateMilestone(validationType, milestoneInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateAtpMilestoneRelation(String validationType, AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try{
            this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), atpMilestoneRelationInfo, context);
        } catch(PermissionDeniedException ex){
            throw new OperationFailedException("Validation of AtpMilestoneRelation failed due to Permission denied exception", ex);
        }
        
        return this.nextDecorator.validateAtpMilestoneRelation(validationType, atpMilestoneRelationInfo, context);
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
