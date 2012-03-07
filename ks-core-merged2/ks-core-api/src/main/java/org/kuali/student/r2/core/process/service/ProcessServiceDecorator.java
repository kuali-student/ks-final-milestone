package org.kuali.student.r2.core.process.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

import java.util.List;

public abstract class ProcessServiceDecorator implements ProcessService {
    private ProcessService nextDecorator;

    public ProcessService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(ProcessService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public ProcessCategoryInfo getProcessCategory(String processCategoryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessCategory(processCategoryId, contextInfo);
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesByIds(List<String> processCategoryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessCategoriesByIds(processCategoryIds, contextInfo);
    }

    @Override
    public List<String> getProcessCategoryIdsByType(String processTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessCategoryIdsByType(processTypeKey, contextInfo);
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesForProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessCategoriesForProcess(processKey, contextInfo);
    }

    @Override
    public List<String> searchForProcessCategoryIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForProcessCategoryIds(criteria, contextInfo);
    }

    @Override
    public List<ProcessCategoryInfo> searchForProcessCategories(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForProcessCategories(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateProcessCategory(validationTypeKey, processCategoryInfo, contextInfo);
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createProcessCategory(processCategoryInfo, contextInfo);
    }

    @Override
    public ProcessCategoryInfo updateProcessCategory(String processCategoryId, ProcessCategoryInfo processInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateProcessCategory(processCategoryId, processInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteProcessCategory(String processCategoryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteProcessCategory(processCategoryId, contextInfo);
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addProcessToProcessCategory(processKey, processCategoryId, contextInfo);
    }

    @Override
    public StatusInfo removeProcessFromProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeProcessFromProcessCategory(processKey, processCategoryId, contextInfo);
    }

    @Override
    public ProcessInfo getProcess(String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcess(processKey, contextInfo);
    }

    @Override
    public List<ProcessInfo> getProcessesByKeys(List<String> processKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessesByKeys(processKeys, contextInfo);
    }

    @Override
    public List<String> getProcessKeysByType(String processTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessKeysByType(processTypeKey, contextInfo);
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(String processCategoryId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getProcessesForProcessCategory(processCategoryId, contextInfo);
    }

    @Override
    public List<String> searchForProcessKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForProcessKeys(criteria, contextInfo);
    }

    @Override
    public List<ProcessInfo> searchForProcesss(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForProcesss(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateProcess(validationTypeKey, processInfo, contextInfo);
    }

    @Override
    public ProcessInfo createProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createProcess(processKey, processInfo, contextInfo);
    }

    @Override
    public ProcessInfo updateProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateProcess(processKey, processInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteProcess(String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteProcess(processKey, contextInfo);
    }

    @Override
    public CheckInfo getCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCheck(checkKey, contextInfo);
    }

    @Override
    public List<CheckInfo> getChecksByIds(List<String> checkKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getChecksByIds(checkKeys, contextInfo);
    }

    @Override
    public List<String> getCheckKeysByType(String checkTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCheckKeysByType(checkTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForCheckKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCheckKeys(criteria, contextInfo);
    }

    @Override
    public List<CheckInfo> searchForChecks(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForChecks(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCheck(validationTypeKey, checkInfo, contextInfo);
    }

    @Override
    public CheckInfo createCheck(String checkKey, CheckInfo checkInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCheck(checkKey, checkInfo, contextInfo);
    }

    @Override
    public CheckInfo updateCheck(String checkKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCheck(checkKey, checkInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCheck(checkKey, contextInfo);
    }

    @Override
    public InstructionInfo getInstruction(String instructionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstruction(instructionId, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByIds(List<String> instructionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsByIds(instructionIds, contextInfo);
    }

    @Override
    public List<String> getInstructionIdsByType(String instructionTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionIdsByType(instructionTypeKey, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsByProcess(processKey, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByCheck(String checkKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsByCheck(checkKey, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcessAndCheck(String checkKey, String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsByProcessAndCheck(checkKey, processKey, contextInfo);
    }

    @Override
    public List<String> searchForInstructionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForInstructionIds(criteria, contextInfo);
    }

    @Override
    public List<InstructionInfo> searchForInstructions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForInstructions(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey, String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateInstruction(validationTypeKey, processKey, checkKey, instructionInfo, contextInfo);
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createInstruction(processKey, checkKey, instructionInfo, contextInfo);
    }

    @Override
    public InstructionInfo updateInstruction(String instructionId, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateInstruction(instructionId, instructionInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteInstruction(String instructionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteInstruction(instructionId, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsForEvaluation(processKey, contextInfo);
    }

  
}
