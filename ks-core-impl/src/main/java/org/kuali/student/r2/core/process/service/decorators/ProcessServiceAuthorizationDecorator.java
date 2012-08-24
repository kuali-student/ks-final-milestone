package org.kuali.student.r2.core.process.service.decorators;

import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

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
    public ProcessCategoryInfo createProcessCategory(String processCategoryTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createProcessCategory", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createProcessCategory(processCategoryTypeKey, processCategoryInfo, contextInfo);
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
    public ProcessInfo createProcess(String processKey, String processTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createProcess", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createProcess(processKey, processTypeKey, processInfo, contextInfo);
    }

    @Override
    public CheckInfo createCheck(String checkTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createCheck", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createCheck(checkTypeKey, checkInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteCheck(String checkId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteCheck", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().deleteCheck(checkId, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionsByProcess", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().getInstructionsByProcess(processKey, contextInfo);
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkId, String instructionTypeKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createInstruction", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().createInstruction(processKey, checkId, instructionTypeKey, instructionInfo, contextInfo);
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInstructionsForEvaluation", null)) {
            throw new PermissionDeniedException();
        }
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
        if (!permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "reorderInstructions", null)) {
            throw new PermissionDeniedException();
        }
        return getNextDecorator().reorderInstructions(processKey, instructionIds, contextInfo);
    }


}
