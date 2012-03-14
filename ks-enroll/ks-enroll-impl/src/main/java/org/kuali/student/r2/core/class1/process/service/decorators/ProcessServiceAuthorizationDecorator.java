package org.kuali.student.r2.core.class1.process.service.decorators;

import org.kuali.rice.kim.api.permission.PermissionService;
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
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessServiceDecorator;

import java.util.List;

public class ProcessServiceAuthorizationDecorator extends ProcessServiceDecorator implements HoldsPermissionService {

    private PermissionService permissionService;

    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "ProcessService.";

    @Override
    public PermissionService getPermissionService() {
        return permissionService;
    }
    @Override
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(ProcessCategoryInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createProcessCategory", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createProcessCategory(processInfo, contextInfo);
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "addProcessToProcessCategory", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().addProcessToProcessCategory(processKey, processCategoryId, contextInfo);
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(String processCategoryId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getProcessesForProcessCategory", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().getProcessesForProcessCategory(processCategoryId, contextInfo);
    }

    @Override
    public ProcessInfo createProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createProcess", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createProcess(processKey, processInfo, contextInfo);
    }

    @Override
    public CheckInfo createCheck(String checkKey, CheckInfo checkInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createCheck", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createCheck(checkKey, checkInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCheck", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().deleteCheck(checkKey, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionsByProcess", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().getInstructionsByProcess(processKey, contextInfo);
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createInstruction", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createInstruction(processKey, checkKey, instructionInfo, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionsForEvaluation", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().getInstructionsForEvaluation(processKey, contextInfo);
    }
}
