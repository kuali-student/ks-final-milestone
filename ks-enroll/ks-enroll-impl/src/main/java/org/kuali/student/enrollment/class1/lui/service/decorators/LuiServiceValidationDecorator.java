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
import org.kuali.student.r2.core.service.util.ValidationUtils;

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
	public List<ValidationResultInfo> validateLui(String validationType,
			LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, luiInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors =
                    getNextDecorator().validateLui(validationType, luiInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        }
        catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate lui", ex);
        }
        return errors;
	}
	
    private void _luiFullValidation(LuiInfo luiInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors =
                    this.validateLui(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating lui", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating lui", ex);
        }
    }
	
	@Override
	public LuiInfo createLui(String cluId, String atpId, LuiInfo luiInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		_luiFullValidation(luiInfo, context);
		return getNextDecorator().createLui(cluId,atpId,luiInfo, context);
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		_luiFullValidation(luiInfo, context);
		return getNextDecorator().updateLui(luiId,luiInfo, context);
	}
	
	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(
			String validationType, LuiLuiRelationInfo luiLuiRelationInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationType, luiLuiRelationInfo, context);
            List<ValidationResultInfo> nextDecoratorErrors =
                    getNextDecorator().validateLuiLuiRelation(validationType, luiLuiRelationInfo, context);
            if (null != nextDecoratorErrors) {
                errors.addAll(nextDecoratorErrors);
            }
        }
        catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error trying to validate lui-lui relation", ex);
        }
        return errors;
	}

    private void _luiLuiRelationFullValidation(LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
		try {
		    List<ValidationResultInfo> errors =
                    this.validateLuiLuiRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), luiLuiRelationInfo, context);
		    if (!errors.isEmpty()) {
		        throw new DataValidationErrorException("Error(s) validating lui-lui relation", errors);
		    }
		} catch (DoesNotExistException ex) {
		    throw new OperationFailedException("Error validating lui-lui relation", ex);
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
		_luiLuiRelationFullValidation(luiLuiRelationInfo, context);
		return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiLuiRelationInfo, context);
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		_luiLuiRelationFullValidation(luiLuiRelationInfo, context);
		return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
	}

}
