package org.kuali.student.r2.core.process.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessServiceDecorator;

import java.util.List;

public class ProcessServiceAuthorizationDecorator extends ProcessServiceDecorator {
    @Override
    public ProcessCategoryInfo createProcessCategory(ProcessCategoryInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return super.createProcessCategory(processInfo, contextInfo);    // TODO needs implementation
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return super.addProcessToProcessCategory(processKey, processCategoryId, contextInfo);    // TODO needs implementation
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(String processCategoryId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return super.getProcessesForProcessCategory(processCategoryId, contextInfo);    // TODO needs implementation
    }

    @Override
    public ProcessInfo createProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return super.createProcess(processKey, processInfo, contextInfo);    // TODO needs implementation
    }

    @Override
    public CheckInfo createCheck(CheckInfo checkInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return super.createCheck(checkInfo, contextInfo);    // TODO needs implementation
    }

    @Override
    public StatusInfo deleteCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return super.deleteCheck(checkKey, contextInfo);    // TODO needs implementation
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return super.getInstructionsByProcess(processKey, contextInfo);    // TODO needs implementation
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return super.createInstruction(processKey, checkKey, instructionInfo, contextInfo);    // TODO needs implementation
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return super.getInstructionsForEvaluation(processKey, contextInfo);    // TODO needs implementation
    }
}
