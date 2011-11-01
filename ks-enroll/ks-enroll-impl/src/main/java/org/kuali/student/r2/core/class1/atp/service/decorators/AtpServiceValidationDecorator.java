package org.kuali.student.r2.core.class1.atp.service.decorators;

import java.util.List;

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
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpServiceDecorator;
import org.kuali.student.r2.core.service.util.ValidationUtils;

public class AtpServiceValidationDecorator extends AtpServiceDecorator implements HoldsValidator, HoldsDataDictionaryService
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
    public AtpInfo createAtp(String atpKey, AtpInfo atpInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        _atpFullValidation(atpInfo, context);
        return getNextDecorator().createAtp(atpKey, atpInfo, context);
    }

    @Override
    public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        _atpFullValidation(atpInfo, context);
        return getNextDecorator().updateAtp(atpKey, atpInfo, context);
    }

    private void _atpFullValidation(AtpInfo atpInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateAtp(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), atpInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating atp", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating atp", ex);
        }
    }
    
    @Override
    public List<ValidationResultInfo> validateAtp(String validationType, AtpInfo atpInfo, ContextInfo context)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, atpInfo, context);
    		List<ValidationResultInfo> nextDecorationErrors =
    				getNextDecorator().validateAtp(validationType, atpInfo, context);
    		if (null != nextDecorationErrors) {
    			errors.addAll(nextDecorationErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating atp", ex);
    	}
    	return errors;
    }

    
    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	_atpAtpRelationFullValidation(atpAtpRelationInfo, context);
        return getNextDecorator().createAtpAtpRelation(atpAtpRelationInfo, context);
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	_atpAtpRelationFullValidation(atpAtpRelationInfo, context);
        return getNextDecorator().updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, context);
    }
    
    private void _atpAtpRelationFullValidation(AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateAtpAtpRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), atpAtpRelationInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) occurred validating atp-atp relation", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating atp-atp relation", ex);
        }
    }
    
    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationType, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, atpAtpRelationInfo, context);
    		List<ValidationResultInfo> nextDecorationErrors =
    				getNextDecorator().validateAtpAtpRelation(validationType, atpAtpRelationInfo, context);
    		if (null != nextDecorationErrors) {
    			errors.addAll(nextDecorationErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating atp-atp relation", ex);
    	}
    	return errors;
    }


    @Override
    public AtpMilestoneRelationInfo createAtpMilestoneRelation(AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        _atpMilestoneRelationFullValidation(atpMilestoneRelationInfo, context);
        return getNextDecorator().createAtpMilestoneRelation(atpMilestoneRelationInfo, context);
    }

    @Override
    public AtpMilestoneRelationInfo updateAtpMilestoneRelation(String atpMilestoneRelationId, AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        _atpMilestoneRelationFullValidation(atpMilestoneRelationInfo, context);
        return getNextDecorator().updateAtpMilestoneRelation(atpMilestoneRelationId, atpMilestoneRelationInfo, context);
    }

    private void _atpMilestoneRelationFullValidation(AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateAtpMilestoneRelation(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), atpMilestoneRelationInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating atp-milestone relation", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating atp-milestone relation", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateAtpMilestoneRelation(String validationType, AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, atpMilestoneRelationInfo, context);
    		List<ValidationResultInfo> nextDecorationErrors =
    				getNextDecorator().validateAtpMilestoneRelation(validationType, atpMilestoneRelationInfo, context);
    		if (null != nextDecorationErrors) {
    			errors.addAll(nextDecorationErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error validating atp-milestone relation", ex);
    	}
    	return errors;
    }

    
    @Override
    public MilestoneInfo createMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context)
    		throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        _milestoneFullValidation(milestoneInfo, context);
        return getNextDecorator().createMilestone(milestoneKey, milestoneInfo, context);
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context)
    		throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        _milestoneFullValidation(milestoneInfo, context);
        return getNextDecorator().updateMilestone(milestoneKey, milestoneInfo, context);
    }

    private void _milestoneFullValidation(MilestoneInfo milestoneInfo, ContextInfo context)
    		throws DataValidationErrorException, OperationFailedException, InvalidParameterException, MissingParameterException {
        try {
            List<ValidationResultInfo> errors = this.validateMilestone(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), milestoneInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating milestone", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating milestone", ex);
        }
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context)
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<ValidationResultInfo> errors;
    	try {
    		errors = ValidationUtils.validateInfo(validator, validationType, milestoneInfo, context);
    		List<ValidationResultInfo> nextDecoratorErrors =
    				getNextDecorator().validateMilestone(validationType, milestoneInfo, context);
    		if (null != nextDecoratorErrors) {
    			errors.addAll(nextDecoratorErrors);
    		}
    	}
    	catch (DoesNotExistException ex) {
    		throw new OperationFailedException("Error trying to validate milestone", ex);
    	}
    	return errors;
    }

}