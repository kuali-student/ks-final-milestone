package org.kuali.student.r2.core.class1.state.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.state.service.StateService;

import java.util.List;

/**
 * Base decorator class for state service
 */
public class StateServiceDecorator implements StateService {
    private StateService nextDecorator;

    public StateService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(StateService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLifecyclesByKeys(lifecycleKeys, contextInfo);
    }

    @Override
    public List<String> getLifecycleKeysByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLifecycleKeysByRefObjectUri(refObjectUri, contextInfo);
    }

    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForLifecycleKeys(criteria, contextInfo);
    }

    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForLifecycles(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(String validationTypeKey, LifecycleInfo lifecycleInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateLifecycle(validationTypeKey, lifecycleInfo, contextInfo);
    }

    @Override
    public LifecycleInfo createLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createLifecycle(lifecycleKey, lifecycleInfo, contextInfo);
    }

    @Override
    public LifecycleInfo updateLifecycle(String lifecycleKey, LifecycleInfo lifecycleInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateLifecycle(lifecycleKey, lifecycleInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLifecycle(String lifecycleKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public StateInfo getState(String stateKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getState(stateKey, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatesByKeys(stateKeys, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByLifecycle(String lifecycleKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatesByLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<String> searchForStateKeys(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateKeys(criteria, contextInfo);
    }

    @Override
    public List<StateInfo> searchForStates(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStates(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateState(String validationTypeKey, String lifecycleKey,StateInfo stateInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateState(validationTypeKey, lifecycleKey, stateInfo, contextInfo);
    }

    @Override
    public StateInfo createState(String lifecycleKey, String stateKey,StateInfo stateInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createState(lifecycleKey, stateKey, stateInfo, contextInfo);
    }

    @Override
    public StateInfo updateState(String stateKey,StateInfo stateInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateState(stateKey, stateInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteState(String stateKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteState(stateKey, contextInfo);
    }
}
