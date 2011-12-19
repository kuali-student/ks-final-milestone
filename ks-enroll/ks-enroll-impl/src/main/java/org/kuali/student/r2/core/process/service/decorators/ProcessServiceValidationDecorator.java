package org.kuali.student.r2.core.process.service.decorators;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.process.dao.InstructionDao;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessServiceDecorator;
import org.kuali.student.r2.core.service.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class ProcessServiceValidationDecorator extends ProcessServiceDecorator implements HoldsValidator{

    private DataDictionaryValidator validator;
    private InstructionDao instructionDao;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == validationTypeKey) {
            throw new MissingParameterException("validationTypeKey");
        }
        if (null == processCategoryInfo) {
            throw new MissingParameterException("processCategoryInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        List<ValidationResultInfo> errors;
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, processCategoryInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateProcessCategory(validationTypeKey, processCategoryInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
        return errors;
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == processCategoryInfo) {
            throw new MissingParameterException("processCategoryInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (null != processCategoryInfo.getId()) {
            throw new ReadOnlyException("Id is not allowed to be supplied on a create");
        }
        if (null != processCategoryInfo.getMeta()) {
            throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
        }

        // TODO check for existing

        _processCategoryFullValidation(processCategoryInfo, contextInfo);
        return getNextDecorator().createProcessCategory(processCategoryInfo, contextInfo);
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == processCategoryId) {
            throw new MissingParameterException("processCategoryId");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return getNextDecorator().addProcessToProcessCategory(processKey, processCategoryId, contextInfo);
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(String processCategoryId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == processCategoryId) {
            throw new MissingParameterException("processCategoryId");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return getNextDecorator().getProcessesForProcessCategory(processCategoryId, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == validationTypeKey) {
            throw new MissingParameterException("validationTypeKey");
        }
        if (null == processInfo) {
            throw new MissingParameterException("processInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        List<ValidationResultInfo> errors;
        errors = new ArrayList<ValidationResultInfo>(); // TODO remove
/*  TODO Need to add dictionary entry
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, processInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateProcess(validationTypeKey, processInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
*/
        return errors;
    }

    @Override
    public ProcessInfo createProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == processInfo) {
            throw new MissingParameterException("processInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (null != processInfo.getKey() && !processKey.equals(processInfo.getKey())) {
            throw new InvalidParameterException("Process key different than supplied processKey");
        }
        if (null != processInfo.getMeta()) {
            throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
        }

        // TODO check for existing

        _processFullValidation(processInfo, contextInfo);
        return getNextDecorator().createProcess(processKey, processInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == validationTypeKey) {
            throw new MissingParameterException("validationTypeKey");
        }
        if (null == checkInfo) {
            throw new MissingParameterException("checkInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        List<ValidationResultInfo> errors;
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, checkInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateCheck(validationTypeKey, checkInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
        return errors;
    }

    @Override
    public CheckInfo createCheck(CheckInfo checkInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == checkInfo) {
            throw new MissingParameterException("checkInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (null != checkInfo.getKey()) { // TODO Do we need to change method to include key or change object to have ID instead of key?
            throw new ReadOnlyException("Id is not allowed to be supplied on a create");
        }
        if (null != checkInfo.getMeta()) {
            throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
        }

        // TODO check for existing

        _checkFullValidation(checkInfo, contextInfo);
        return getNextDecorator().createCheck(checkInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == checkKey) {
            throw new MissingParameterException("checkKey");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return getNextDecorator().deleteCheck(checkKey, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        return getNextDecorator().getInstructionsByProcess(processKey, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey, String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == validationTypeKey) {
            throw new MissingParameterException("validationTypeKey");
        }
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == checkKey) {
            throw new MissingParameterException("checkKey");
        }
        if (null == instructionInfo) {
            throw new MissingParameterException("instructionInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        List<ValidationResultInfo> errors;
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, instructionInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateInstruction(validationTypeKey, processKey, checkKey, instructionInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
        return errors;
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == checkKey) {
            throw new MissingParameterException("checkKey");
        }
        if (null == instructionInfo) {
            throw new MissingParameterException("instructionInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (null != instructionInfo.getId()) {
            throw new ReadOnlyException("Id is not allowed to be supplied on a create");
        }
        if (null != instructionInfo.getMeta()) {
            throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
        }

        _instructionFullValidation(processKey, checkKey, instructionInfo, contextInfo);
        return getNextDecorator().createInstruction(processKey, checkKey, instructionInfo, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return getNextDecorator().getInstructionsForEvaluation(processKey, contextInfo);
    }

    private void _processCategoryFullValidation(ProcessCategoryInfo processCategoryInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateProcessCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processCategoryInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating process Category", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating process Category", ex);
        }
    }

    private void _processFullValidation(ProcessInfo processInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateProcess(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating Process", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating Process", ex);
        }
    }

    private void _checkFullValidation(CheckInfo checkInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateCheck(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), checkInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating Check", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating Check", ex);
        }
    }

    private void _instructionFullValidation(String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateInstruction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processKey, checkKey, instructionInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating Instruction", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating Instruction", ex);
        }
    }

}

