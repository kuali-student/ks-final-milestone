package org.kuali.student.r2.core.class1.state.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

import javax.jws.WebParam;
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
    public LifecycleInfo getLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(@WebParam(name = "lifecycleKeys") List<String> lifecycleKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLifecyclesByKeys(lifecycleKeys, contextInfo);
    }

    @Override
    public List<String> getLifecycleKeysByRefObjectUri(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLifecycleKeysByRefObjectUri(refObjectUri, contextInfo);
    }

    @Override
    public List<String> searchForLifecycleKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForLifecycleKeys(criteria, contextInfo);
    }

    @Override
    public List<LifecycleInfo> searchForLifecycles(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForLifecycles(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLifecycle(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateLifecycle(validationTypeKey, lifecycleInfo, contextInfo);
    }

    @Override
    public LifecycleInfo createLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createLifecycle(lifecycleKey, lifecycleInfo, contextInfo);
    }

    @Override
    public LifecycleInfo updateLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "lifecycleInfo") LifecycleInfo lifecycleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateLifecycle(lifecycleKey, lifecycleInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public StateInfo getState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getState(stateKey, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByKeys(@WebParam(name = "stateKeys") List<String> stateKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatesByKeys(stateKeys, contextInfo);
    }

    @Override
    public List<StateInfo> getStatesByLifecycle(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatesByLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<String> getInitialStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getInitialStatesByLifecycle(lifecycleKey, contextInfo);
    }

    @Override
    public List<String> searchForStateKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateKeys(criteria, contextInfo);
    }

    @Override
    public List<StateInfo> searchForStates(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStates(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateState(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "stateInfo") StateInfo stateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateState(validationTypeKey, lifecycleKey, stateInfo, contextInfo);
    }

    @Override
    public StateInfo createState(@WebParam(name = "lifecycleKey") String lifecycleKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "stateInfo") StateInfo stateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createState(lifecycleKey, stateKey, stateInfo, contextInfo);
    }

    @Override
    public StateInfo updateState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "stateInfo") StateInfo stateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateState(stateKey, stateInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteState(@WebParam(name = "stateKey") String stateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteState(stateKey, contextInfo);
    }

    @Override
    public StatusInfo addInitialStateToLifecycle(String initialStateKey, String lifecycleKey, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.addInitialStateToLifecycle(initialStateKey, lifecycleKey, contextInfo);
    }

    @Override
    public StatusInfo removeInitialStateFromLifecycle(String initialStateKey, String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.removeInitialStateFromLifecycle(initialStateKey, lifecycleKey, contextInfo);
    }

    @Override
    public StateChangeInfo getStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChange(stateChangeId, contextInfo);
    }

    @Override
    public List<StateChangeInfo> getStateChangesByIds(@WebParam(name = "stateChangeIds") List<String> stateChangeIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByIds(stateChangeIds, contextInfo);
    }

    @Override
    public List<String> getStateChangeIdsByType(@WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangeIdsByType(stateChangeTypeKey, contextInfo);
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromState(@WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByFromState(fromStateKey, contextInfo);
    }

    @Override
    public List<StateChangeInfo> getStateChangesByToState(@WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByToState(toStateKey, contextInfo);
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromStateAndToState(@WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByFromStateAndToState(fromStateKey, toStateKey, contextInfo);
    }

    @Override
    public List<String> searchForStateChangeIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateChangeIds(criteria, contextInfo);
    }

    @Override
    public List<StateChangeInfo> searchForStateChanges(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateChanges(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStateChange(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStateChange(validationTypeKey, toStateKey, fromStateKey, stateChangeTypeKey, stateChangeInfo, contextInfo);
    }

    @Override
    public StateChangeInfo createStateChange(@WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStateChange(toStateKey, fromStateKey, stateChangeTypeKey, stateChangeInfo, contextInfo);
    }

    @Override
    public StateChangeInfo updateStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStateChange(stateChangeId, stateChangeInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStateChange(stateChangeId, contextInfo);
    }

    @Override
    public StateConstraintInfo getStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraint(stateConstraintId, contextInfo);
    }

    @Override
    public List<StateConstraintInfo> getStateConstraintsByIds(@WebParam(name = "stateConstraintIds") List<String> stateConstraintIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraintsByIds(stateConstraintIds, contextInfo);
    }

    @Override
    public List<String> getStateConstraintIdsByType(@WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraintIdsByType(stateConstraintTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForStateConstraintIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateConstraintIds(criteria, contextInfo);
    }

    @Override
    public List<StateConstraintInfo> searchForStateConstraints(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateConstraints(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStateConstraint(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStateConstraint(validationTypeKey, stateConstraintTypeKey, stateConstraintInfo, contextInfo);
    }

    @Override
    public StateConstraintInfo createStateConstraint(@WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStateConstraint(stateConstraintTypeKey, stateConstraintInfo, contextInfo);
    }

    @Override
    public StateConstraintInfo updateStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStateConstraint(stateConstraintId, stateConstraintInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStateConstraint(stateConstraintId, contextInfo);
    }

    @Override
    public StatePropagationInfo getStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagation(statePropagationId, contextInfo);
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByIds(@WebParam(name = "statePropagationIds") List<String> statePropagationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagationsByIds(statePropagationIds, contextInfo);
    }

    @Override
    public List<String> getStatePropagationIdsByType(@WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagationIdsByType(statePropagationTypeKey, contextInfo);
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByTargetState(@WebParam(name = "targetStateId") String targetStateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagationsByTargetState(targetStateId, contextInfo);
    }

    @Override
    public List<String> searchForStatePropagationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStatePropagationIds(criteria, contextInfo);
    }

    @Override
    public List<StatePropagationInfo> searchForStatePropagations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStatePropagations(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStatePropagation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "targetStateChangeId") String targetStateChangeId, @WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStatePropagation(validationTypeKey, targetStateChangeId, statePropagationTypeKey, statePropagationInfo, contextInfo);
    }

    @Override
    public StatePropagationInfo createStatePropagation(@WebParam(name = "targetStateChangeId") String targetStateChangeId, @WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStatePropagation(targetStateChangeId, statePropagationTypeKey, statePropagationInfo, contextInfo);
    }

    @Override
    public StatePropagationInfo updateStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStatePropagation(statePropagationId, statePropagationInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStatePropagation(statePropagationId, contextInfo);
    }
}
