package org.kuali.student.r2.core.class1.process.service.decorators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.class1.process.dao.InstructionDao;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessServiceDecorator;

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
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey, String processCategoryTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
        errors = new ArrayList<ValidationResultInfo>(); // TODO remove
/*  TODO Need to add dictionary entry
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, processCategoryInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateProcessCategory(validationTypeKey, processCategoryTypeKey, processCategoryInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
*/
        return errors;
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(String processCategoryTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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

        _processCategoryFullValidation(processCategoryTypeKey, processCategoryInfo, contextInfo);
        return getNextDecorator().createProcessCategory(processCategoryTypeKey, processCategoryInfo, contextInfo);
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
    public List<ValidationResultInfo> validateProcess(String validationTypeKey, String processTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateProcess(validationTypeKey, processTypeKey, processInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
*/
        return errors;
    }

    @Override
    public ProcessInfo createProcess(String processKey, String processTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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

        _processFullValidation(processTypeKey, processInfo, contextInfo);
        return getNextDecorator().createProcess(processKey, processTypeKey, processInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey, String checkTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
        errors = new ArrayList<ValidationResultInfo>(); // TODO remove
/* TODO populate dictionary entry
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, checkInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateCheck(validationTypeKey, checkTypeKey, checkInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
*/
        return errors;
    }

    @Override
    public CheckInfo createCheck(String checkTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == checkTypeKey) {
            throw new MissingParameterException("checkTypeKey");
        }
        if (null == checkInfo) {
            throw new MissingParameterException("checkInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (StringUtils.isBlank(checkInfo.getStateKey())) {
            throw new InvalidParameterException("Check stateKey is required");
        }

        if (StringUtils.isBlank(checkInfo.getMilestoneTypeKey())) {
            throw new InvalidParameterException("Check milestoneTypeKey is required");
        }
        if (StringUtils.isBlank(checkInfo.getIssueId())) {
            throw new InvalidParameterException("Check issueId is required");
        }
        if (StringUtils.isBlank(checkInfo.getAgendaId())) {
            throw new InvalidParameterException("Check agendaId is required");
        }

        if (null != checkInfo.getMeta()) {
            throw new ReadOnlyException("MetaInfo is not allowed to be supplied on a create");
        }

        // TODO check for existing

        _checkFullValidation(checkTypeKey, checkInfo, contextInfo);
        return getNextDecorator().createCheck(checkTypeKey, checkInfo, contextInfo);
    }

    @Override
    public CheckInfo updateCheck(String checkId, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if (null == checkId) {
            throw new MissingParameterException("checkId");
        }
        if (null == checkInfo) {
            throw new MissingParameterException("checkInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (StringUtils.isBlank(checkInfo.getTypeKey())) {
            throw new InvalidParameterException("Check typeKey is required");
        }
        if (StringUtils.isBlank(checkInfo.getStateKey())) {
            throw new InvalidParameterException("Check stateKey is required");
        }
        if (StringUtils.isBlank(checkInfo.getMilestoneTypeKey())) {
            throw new InvalidParameterException("Check milestoneTypeKey is required");
        }
        if (StringUtils.isBlank(checkInfo.getIssueId())) {
            throw new InvalidParameterException("Check issueId is required");
        }
        if (StringUtils.isBlank(checkInfo.getAgendaId())) {
            throw new InvalidParameterException("Check agendaId is required");
        }

        if (null != checkInfo.getId() && !checkId.equals(checkInfo.getId())) {
            throw new InvalidParameterException("Check Id different than supplied checkid");
        }

        return getNextDecorator().updateCheck(checkId, checkInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCheck(String checkId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == checkId) {
            throw new MissingParameterException("checkId");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }
        return getNextDecorator().deleteCheck(checkId, contextInfo);
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
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey, String processKey, String checkId, String instructionTypeKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == validationTypeKey) {
            throw new MissingParameterException("validationTypeKey");
        }
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == checkId) {
            throw new MissingParameterException("checkId");
        }
        if (null == instructionInfo) {
            throw new MissingParameterException("instructionInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        List<ValidationResultInfo> errors;
        errors = new ArrayList<ValidationResultInfo>(); // TODO remove
/*  TODO Need to add dictionary entry
        errors = ValidationUtils.validateInfo(validator, validationTypeKey, instructionInfo, contextInfo);
        List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateInstruction(validationTypeKey, processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
        if (null != nextDecoratorErrors) {
            errors.addAll(nextDecoratorErrors);
        }
*/
        return errors;
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkId, String instructionTypeKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == processKey) {
            throw new MissingParameterException("processKey");
        }
        if (null == checkId) {
            throw new MissingParameterException("checkId");
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

        if (null != instructionInfo.getProcessKey() && !processKey.equals(instructionInfo.getProcessKey())) {
            throw new InvalidParameterException("Instruction processKey different than supplied processKey");
        }

        if (null != instructionInfo.getCheckKey() && !checkId.equals(instructionInfo.getCheckKey())) {
            throw new InvalidParameterException("Instruction checkId different than supplied checkId");
        }

        if (StringUtils.isBlank(instructionInfo.getStateKey())) {
            throw new InvalidParameterException("Instruction stateKey is required");
        }
        if (null == instructionInfo.getAppliedAtpTypeKeys() || instructionInfo.getAppliedAtpTypeKeys().isEmpty()) {
            throw new InvalidParameterException("Instruction appliedAtpTypeKeys is required");
        }

        _instructionFullValidation(processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
        return getNextDecorator().createInstruction(processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
    }

    @Override
    public InstructionInfo updateInstruction(String instructionId, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if (null == instructionId) {
            throw new MissingParameterException("instructionId");
        }
        if (null == instructionInfo) {
            throw new MissingParameterException("instructionInfo");
        }
        if (null == contextInfo) {
            throw new MissingParameterException("contextInfo");
        }

        if (null != instructionInfo.getId() && !instructionId.equals(instructionInfo.getId())) {
            throw new InvalidParameterException("Instruction id different than supplied instructionId");
        }

        if (StringUtils.isBlank(instructionInfo.getProcessKey())) {
            throw new InvalidParameterException("Instruction processKey is required");
        }
        if (StringUtils.isBlank(instructionInfo.getCheckKey())) {
            throw new InvalidParameterException("Instruction checkId is required");
        }
        if (StringUtils.isBlank(instructionInfo.getTypeKey())) {
            throw new InvalidParameterException("Instruction typeKey is required");
        }
        if (StringUtils.isBlank(instructionInfo.getStateKey())) {
            throw new InvalidParameterException("Instruction stateKey is required");
        }
        if (null == instructionInfo.getAppliedAtpTypeKeys() || instructionInfo.getAppliedAtpTypeKeys().isEmpty()){
            throw new InvalidParameterException("Instruction appliedAtpTypeKeys is required");
        }

        return getNextDecorator().updateInstruction(instructionId, instructionInfo, contextInfo);
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

    private void _processCategoryFullValidation(String processCategoryTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateProcessCategory(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processCategoryTypeKey, processCategoryInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating process Category", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating process Category", ex);
        }
    }

    private void _processFullValidation(String processTypeKey, ProcessInfo processInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateProcess(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processTypeKey, processInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating Process", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating Process", ex);
        }
    }

    private void _checkFullValidation(String checkTypeKey, CheckInfo checkInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateCheck(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), checkTypeKey, checkInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating Check", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating Check", ex);
        }
    }

    private void _instructionFullValidation(String processKey, String checkId, String instructionTypeKey, InstructionInfo instructionInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            List<ValidationResultInfo> errors = this.validateInstruction(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), processKey, checkId, instructionTypeKey, instructionInfo, context);
            if (!errors.isEmpty()) {
                throw new DataValidationErrorException("Error(s) validating Instruction", errors);
            }
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating Instruction", ex);
        }
    }

}

