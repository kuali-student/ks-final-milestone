package org.kuali.student.r2.core.process.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;

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
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey, String processCategoryTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateProcessCategory(validationTypeKey, processCategoryTypeKey, processCategoryInfo, contextInfo);
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(String processCategoryTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createProcessCategory(processCategoryTypeKey, processCategoryInfo, contextInfo);
    }

    @Override
    public ProcessCategoryInfo updateProcessCategory(String processCategoryId, ProcessCategoryInfo processInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateProcessCategory(processCategoryId, processInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteProcessCategory(String processCategoryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException{
        return getNextDecorator().deleteProcessCategory(processCategoryId, contextInfo);
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addProcessToProcessCategory(processKey, processCategoryId, contextInfo);
    }

    @Override
    public StatusInfo removeProcessFromProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ProcessInfo> searchForProcess(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForProcess(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey, String processTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateProcess(validationTypeKey, processTypeKey, processInfo, contextInfo);
    }

    @Override
    public ProcessInfo createProcess(String processKey, String processTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createProcess(processKey, processTypeKey, processInfo, contextInfo);
    }

    @Override
    public ProcessInfo updateProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateProcess(processKey, processInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteProcess(String processKey, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteProcess(processKey, contextInfo);
    }

    @Override
    public CheckInfo getCheck(String checkId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCheck(checkId, contextInfo);
    }

    @Override
    public List<CheckInfo> getChecksByIds(List<String> checkIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getChecksByIds(checkIds, contextInfo);
    }

    @Override
    public List<String> getCheckIdsByType(String checkTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCheckIdsByType(checkTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForCheckIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCheckIds(criteria, contextInfo);
    }

    @Override
    public List<CheckInfo> searchForChecks(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForChecks(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey, String checkTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCheck(validationTypeKey, checkTypeKey, checkInfo, contextInfo);
    }

    @Override
    public CheckInfo createCheck(String checkTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCheck(checkTypeKey, checkInfo, contextInfo);
    }

    @Override
    public CheckInfo updateCheck(String checkId, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCheck(checkId, checkInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCheck(String checkId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCheck(checkId, contextInfo);
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
    public List<InstructionInfo> getInstructionsByCheck(String checkId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsByCheck(checkId, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcessAndCheck(String checkId, String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getInstructionsByProcessAndCheck(checkId, processKey, contextInfo);
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
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey, String processKey, String checkId, String instructionTypeKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateInstruction(validationTypeKey, processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkId, String instructionTypeKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createInstruction(processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
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

    @Override
    public StatusInfo reorderInstructions(String processKey,
            List<String> instructionIds,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator ().reorderInstructions(processKey, instructionIds, contextInfo);
    }
    
}
