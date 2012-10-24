package org.kuali.student.r2.core.class1.state.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

import java.util.List;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;

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

    public List<StateChangeInfo> getStateChangesByIds(List<String> stateChangeIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByIds(stateChangeIds, contextInfo);
    }

    public List<String> getStateChangeIdsByType(String stateChangeTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangeIdsByType(stateChangeTypeKey, contextInfo);
    }

    public List<StateChangeInfo> getStateChangesByFromState(String fromStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByFromState(fromStateKey, contextInfo);
    }

    public List<StateChangeInfo> getStateChangesByToState(String toStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByToState(toStateKey, contextInfo);
    }

    public List<StateChangeInfo> getStateChangesByFromStateAndToState(String fromStateKey, String toStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateChangesByFromStateAndToState(fromStateKey, toStateKey, contextInfo);
    }

    public List<String> searchForStateChangeIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateChangeIds(criteria, contextInfo);
    }

    public List<StateChangeInfo> searchForStateChanges(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateChanges(criteria, contextInfo);
    }

    public List<ValidationResultInfo> validateStateChange(String validationTypeKey, String toStateKey, String fromStateKey, String stateChangeTypeKey, StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStateChange(validationTypeKey, toStateKey, fromStateKey, stateChangeTypeKey, stateChangeInfo, contextInfo);
    }

    public StateChangeInfo createStateChange(String toStateKey, String fromStateKey, String stateChangeTypeKey, StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStateChange(toStateKey, fromStateKey, stateChangeTypeKey, stateChangeInfo, contextInfo);
    }

    public StateChangeInfo updateStateChange(String stateChangeId, StateChangeInfo stateChangeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStateChange(stateChangeId, stateChangeInfo, contextInfo);
    }

    public StatusInfo deleteStateChange(String stateChangeId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStateChange(stateChangeId, contextInfo);
    }

    public StatusInfo deleteStateChangesByFromState(String fromStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStateChangesByFromState(fromStateKey, contextInfo);
    }

    public StatusInfo deleteStateChangesByToState(String toStateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStateChangesByToState(toStateKey, contextInfo);
    }

    public StateConstraintInfo getStateConstraint(String stateConstraintId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraint(stateConstraintId, contextInfo);
    }

    public List<StateConstraintInfo> getStateConstraintsByIds(List<String> stateConstraintIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraintsByIds(stateConstraintIds, contextInfo);
    }

    public List<StateConstraintInfo> getStateConstraintsByRelatedStateKeys(List<String> relatedStateKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraintsByRelatedStateKeys(relatedStateKeys, contextInfo);
    }

    public List<StateConstraintInfo> getStateConstraintsByAgendaId(String agendaId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStateConstraintsByAgendaId(agendaId, contextInfo);
    }

    public List<String> searchForStateConstraintIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateConstraintIds(criteria, contextInfo);
    }

    public List<StateConstraintInfo> searchForStateConstraints(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStateConstraints(criteria, contextInfo);
    }

    public List<ValidationResultInfo> validateStateConstraint(String validationTypeKey, String stateConstraintId, String stateConstraintTypeKey, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStateConstraint(validationTypeKey, stateConstraintId, stateConstraintTypeKey, stateConstraintInfo, contextInfo);
    }

    public StateConstraintInfo createStateConstraint(String stateConstraintId, String stateConstraintTypeKey, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStateConstraint(stateConstraintId, stateConstraintTypeKey, stateConstraintInfo, contextInfo);
    }

    public StateConstraintInfo updateStateConstraint(String stateConstraintId, StateConstraintInfo stateConstraintInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStateConstraint(stateConstraintId, stateConstraintInfo, contextInfo);
    }

    public StatusInfo deleteStateConstraint(String stateConstraintId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStateConstraint(stateConstraintId, contextInfo);
    }

    public StatePropagationInfo getStatePropagation(String statePropagationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagation(statePropagationId, contextInfo);
    }

    public List<StatePropagationInfo> getStatePropagationsByIds(List<String> statePropagationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagationsByIds(statePropagationIds, contextInfo);
    }

    public List<StatePropagationInfo> getStatePropagationsByTargetState(String targetStateKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagationsByTargetState(targetStateKey, contextInfo);
    }

    public List<StatePropagationInfo> getStatePropagationsByTargetStateAndStateConstraints(String targetStateKey, String stateConstraintIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStatePropagationsByTargetStateAndStateConstraints(targetStateKey, stateConstraintIds, contextInfo);
    }

    public List<String> searchForStatePropagationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStatePropagationIds(criteria, contextInfo);
    }

    public List<StatePropagationInfo> searchForStatePropagations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStatePropagations(criteria, contextInfo);
    }

    public List<ValidationResultInfo> validateStatePropagation(String validationTypeKey, String statePropagationId, String statePropagationTypeKey, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStatePropagation(validationTypeKey, statePropagationId, statePropagationTypeKey, statePropagationInfo, contextInfo);
    }

    public StatePropagationInfo createStatePropagation(String statePropagationId, String statePropagationTypeKey, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStatePropagation(statePropagationId, statePropagationTypeKey, statePropagationInfo, contextInfo);
    }

    public StatePropagationInfo updateStatePropagation(String statePropagationId, StatePropagationInfo statePropagationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStatePropagation(statePropagationId, statePropagationInfo, contextInfo);
    }

    public StatusInfo deleteStatePropagation(String statePropagationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStatePropagation(statePropagationId, contextInfo);
    }
    
    
}
