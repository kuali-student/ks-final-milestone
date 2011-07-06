package org.kuali.student.enrollment.class1.lui.service.decorators;

import java.util.List;

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;

public class LuiServiceValidationDecorator extends LuiServiceDecorator implements HoldsValidator, HoldsDataDictionaryService{
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
    
    private List<ValidationResultInfo> validate(String validationType, Object info, ContextInfo context) throws OperationFailedException, MissingParameterException, InvalidParameterException {
        List<ValidationResultInfo> errors;
        try {
            errors = this.validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), info, context);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Validation failed due to permission exception", ex);
        }
        return errors;
    }
    
	@Override
	public List<ValidationResultInfo> validateLui(String validationType,
			LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return validate(validationType, luiInfo, context);
	}
	
    private void validateLui(LuiInfo luiInfo, ContextInfo context) 
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateLui(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Errors", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("erorr trying to validate", ex);
        }
    }
	
	@Override
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		validateLui(luiInfo, context);
		return nextDecorator.createLui(cluId,atpKey,luiInfo, context);
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		validateLui(luiInfo, context);
		return nextDecorator.updateLui(luiId,luiInfo, context);
	}
	
	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(
			String validationType, LuiLuiRelationInfo luiLuiRelationInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return validate(validationType, luiLuiRelationInfo, context);
	}

    private void validateLuiLuiRelation(LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context) 
			throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
		    List<ValidationResultInfo> errors = this.validateLuiLuiRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiLuiRelationInfo, context);
		    if (!errors.isEmpty()) {
		        throw new DataValidationErrorException("Errors", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("erorr trying to validate", ex);
		}
}
    
	@Override
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws AlreadyExistsException, CircularRelationshipException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		validateLuiLuiRelation(luiLuiRelationInfo, context);
		return nextDecorator.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiLuiRelationInfo, context);
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		validateLuiLuiRelation(luiLuiRelationInfo, context); 
		return nextDecorator.updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
	}

}
