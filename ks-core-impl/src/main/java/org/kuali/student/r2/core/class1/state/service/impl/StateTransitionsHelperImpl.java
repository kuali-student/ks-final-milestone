package org.kuali.student.r2.core.class1.state.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;
import org.kuali.student.r2.core.class1.state.infc.StateConstraintOperator;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;
import org.kuali.student.r2.core.class1.state.service.StateHelper;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.state.service.StateTransitionsHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *  Business logic for StateService constraints and propagations.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class StateTransitionsHelperImpl implements StateTransitionsHelper {

    private static final Logger LOGGER = Logger.getLogger(StateTransitionsHelperImpl.class);
     /**
      * The following are Maps injected via Spring config.
      *
      * The key of the StateHelperMap is the prefix of the state key(s) for the entity
      * which means everything before .state, so the key for a SOC as derived from
      * "kuali.soc.state.draft" would be "kuali.soc".
      *
      * The key of the RelatedObjectHelperMap is the prefix of the entity + ":" +
      * the prefix of the related entity. So, the key for the CO/AO helper would be
      * "kuali.lui.course.offering:kuali.lui.activity.offering".
      */
    private Map<String, StateHelper> stateHelperMap;
    private Map<String, RelatedObjectHelper> relatedObjectHelperMap;

    private StateService stateService;

    public Map<String, StateHelper> getStateHelperMap() {
        if (this.stateHelperMap == null) {
            this.stateHelperMap = new HashMap<String, StateHelper>();
        }
        return stateHelperMap;
    }

    public void setStateHelperMap(Map<String, StateHelper> stateHelperMap) {
        this.stateHelperMap = stateHelperMap;
    }

    public Map<String, RelatedObjectHelper> getRelatedObjectHelperMap() {
        if (this.relatedObjectHelperMap == null) {
            this.relatedObjectHelperMap = new HashMap<String, RelatedObjectHelper>();
        }
        return relatedObjectHelperMap;
    }

    public void setRelatedObjectHelperMap(Map<String, RelatedObjectHelper> relatedObjectHelperMap) {
        this.relatedObjectHelperMap = relatedObjectHelperMap;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public StatusInfo processStateConstraints(String entityId, String nextStateKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(Boolean.TRUE);

        /*
         *  Grab the appropriate StateHelper based on the prefix of the next state key, get the current state key from the entity
         *  via the helper, then query the state service for a StateChange. The presence of a StateChange is validates that the requested
         *  next state is valid as long as all constraints are met.
         */
        String stateKeyPrefix = findStateKeyPrefix(nextStateKey);
        StateHelper stateChangeHelper = this.stateHelperMap.get(stateKeyPrefix);
        if (stateChangeHelper == null) {
            statusInfo.setSuccess(Boolean.FALSE);
            statusInfo.setMessage(String.format("No state helper is registered for [%s].", stateKeyPrefix));
            return statusInfo;
        }

        String fromStateKey = stateChangeHelper.getStateKey(entityId, context);
        //  Allow the constraint to pass if the from and to states are the same.
        if (StringUtils.equals(fromStateKey, nextStateKey)) {
            statusInfo.setMessage("To and from state were the same.");
            return statusInfo;
        }

        List<StateChangeInfo> stateChanges = this.stateService.getStateChangesByFromStateAndToState(fromStateKey, nextStateKey, context);
        if (stateChanges.size() == 0) {
            statusInfo.setSuccess(Boolean.FALSE);
            statusInfo.setMessage(String.format("No state change is defined for [%s] to [%s].", fromStateKey, nextStateKey));
            return statusInfo;
        }

        //  Verify that related object constraints are met.
        List<StateConstraintInfo> constraints = getStateService().getStateConstraintsByIds(stateChanges.iterator().next().getStateConstraintIds(), context);
        if (constraints != null) {
            for (StateConstraintInfo constraint : constraints) {
                 //Should we break once we encountered invalid status
                 StatusInfo status = processConstraint(entityId,constraint,stateKeyPrefix,context);
                if (!status.getIsSuccess()){
                     return status;
                }

            }
        }
        return statusInfo;
    }

    /**
     * Process state propagations.
     * @param entityId The ID of then entity to state change.
     * @param stateKeys The from and to state keys in the format "from:to".
     * @param context
     * @return A Map of StatusInfos with the related object id as the key.
     */
    @Override
    public Map<String, StatusInfo> processStatePropagations(String entityId, String stateKeys, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {

        Map<String, StatusInfo> resultMap = new HashMap<String, StatusInfo>();

        //  Parse from and to states
        String tokens[] = stateKeys.split(":");
        String fromStateKey = tokens[0];
        String nextStateKey =  tokens[1];

        String stateKeyPrefix = findStateKeyPrefix(nextStateKey);

        List<StateChangeInfo> stateChanges = this.stateService.getStateChangesByFromStateAndToState(fromStateKey, nextStateKey, context);
        //  If no StateChange is defined here just log and return.
        if (stateChanges.size() == 0) {
            LOGGER.warn(String.format("No state change was defined from [%s] to [%s].", fromStateKey, nextStateKey));
            return resultMap;
        } else if (stateChanges.size() > 1) {
            throw new OperationFailedException("Multiple StateChanges between two states is unsupported.");
        }
        List<StatePropagationInfo> propagations = this.stateService.getStatePropagationsByIds(stateChanges.iterator().next().getStatePropagationIds(), context);
        if (propagations != null) {
            for (StatePropagationInfo propagation : propagations) {
                //Process all the propagation constraints first
                List<String> propagationConstraintIds = propagation.getStateConstraintIds();
                List<StateConstraintInfo> propStateConstraintInfos = getStateService().getStateConstraintsByIds(propagationConstraintIds,context);

                boolean isConstraintsSucceeded = true;

                for (StateConstraintInfo propStateConstraintInfo : propStateConstraintInfos) {
                    StatusInfo statusInfo = processConstraint(entityId,propStateConstraintInfo,stateKeyPrefix,context);
                    if (!statusInfo.getIsSuccess()){
                         resultMap.put(stateKeyPrefix,statusInfo);
                         isConstraintsSucceeded = false;
                         break;
                    }
                }

                if (!isConstraintsSucceeded){
                     continue;
                }

                StateChangeInfo stateChangeInfo = this.stateService.getStateChange(propagation.getTargetStateChangeId(), context);

                if (stateChangeInfo == null) {
                    StatusInfo si = new StatusInfo();
                    si.setSuccess(Boolean.FALSE);
                    si.setMessage(String.format("Unable to find target state change for propagation [%s]. This state change is most likely misconfigured.", propagation.getId()));
                    resultMap.put(stateKeyPrefix, si);
                    continue;
                }
                String targetStateKey = findStateKeyPrefix(stateChangeInfo.getFromStateKey());
                String roHelperKey = makeRelatedObjectHelperKey(stateKeyPrefix, targetStateKey);
                RelatedObjectHelper relatedObjectHelper = this.relatedObjectHelperMap.get(roHelperKey);
                if (relatedObjectHelper == null) {
                    StatusInfo si = new StatusInfo();
                    si.setSuccess(Boolean.FALSE);
                    si.setMessage(String.format("No related object helper was registered for key [%s].", roHelperKey));
                    resultMap.put(stateKeyPrefix, si);
                    continue;
                }
                StateHelper stateHelper = this.stateHelperMap.get(targetStateKey);
                if (stateHelper == null) {
                    StatusInfo si = new StatusInfo();
                    si.setSuccess(Boolean.FALSE);
                    si.setMessage(String.format("No state helper is registered for [%s].", stateKeyPrefix));
                    resultMap.put(stateKeyPrefix, si);
                    continue;
                }


                Map<String,String> idsAndState = relatedObjectHelper.getRelatedObjectsIdAndState(entityId, context);

                for (String id : idsAndState.keySet()) {
                    String currentStateKey = idsAndState.get(id);
                    StatusInfo si = new StatusInfo();
                    if (StringUtils.equals(stateChangeInfo.getFromStateKey(),currentStateKey)){
                        si = stateHelper.updateState(id, stateChangeInfo.getToStateKey(), context);
                    }
                    resultMap.put(id, si);
                }
            }
        }
        return resultMap;
    }

    /**
     * Evaluate related object state constraints.
     * @param actualStateKeySet The actual values of the state keys of related objects.
     * @param constraintStateKeys The state key values defined by the constraint.
     * @param operator The operator to apply when comparing the the two collections.
     * @return
     */
    public Boolean evaluateConstraint(Set<String> actualStateKeySet, List<String> constraintStateKeys, StateConstraintOperator operator) {
        Boolean successFlag = Boolean.FALSE;

        int origActualSetSize = actualStateKeySet.size();
        actualStateKeySet.removeAll(constraintStateKeys);

        if ((operator == StateConstraintOperator.ALL) && (actualStateKeySet.size() == 0)) {
            successFlag = Boolean.TRUE;
        } else if ((operator == StateConstraintOperator.EXISTS) && (origActualSetSize > actualStateKeySet.size())) {
            successFlag = Boolean.TRUE;
        } else if ((operator == StateConstraintOperator.NONE) && (origActualSetSize == actualStateKeySet.size())) {
            successFlag = Boolean.TRUE;
        }
        return successFlag;
    }

    /**
     * Evaluates the constraint for an entity object
     *
     * @param entityId  entity id
     * @param constraint constraint to be verified
     * @param entityKeyPrefix entity key prefix
     * @param contextInfo context info
     * @return the statusInfo
     */
    protected StatusInfo processConstraint(String entityId, StateConstraintInfo constraint, String entityKeyPrefix, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        StatusInfo statusInfo = new StatusInfo();
        /*
         *  Get the related object state keys provided by the constraint.
         *  Determine the related object state key prefix and combine that with the entity state key prefix to lookup the related object helper.
         *  Lookup the actual state key values of the related objects.
         *  Then apply the operator to the two lists and either pass or fail the constraint.
         */
        List<String> constraintObjectStateKeys = constraint.getRelatedObjectStateKeys();
        if (constraintObjectStateKeys.size() == 0) {
            statusInfo.setSuccess(Boolean.TRUE);
            statusInfo.setMessage(String.format("State constraint [%s] has no related state keys defined.", constraint.getId()));
            return statusInfo;
        }
        String relatedObjStateKeyPrefix = findStateKeyPrefix(constraintObjectStateKeys.get(0));
        StateConstraintOperator operator = constraint.getStateConstraintOperator();
        String roHelperKey =  makeRelatedObjectHelperKey(entityKeyPrefix, relatedObjStateKeyPrefix);
        RelatedObjectHelper relatedObjectHelper = this.relatedObjectHelperMap.get(roHelperKey);
        if (relatedObjectHelper == null) {
            statusInfo.setSuccess(Boolean.FALSE);
            statusInfo.setMessage(String.format("No related object helper was registered for key [%s].", roHelperKey));
            return statusInfo;
        }

        Map<String,String> idsAndState = relatedObjectHelper.getRelatedObjectsIdAndState(entityId, contextInfo);
        Set<String> stateKeys = new HashSet<String>(idsAndState.values());

        if (!evaluateConstraint(stateKeys, constraintObjectStateKeys, operator)) {
            statusInfo.setSuccess(Boolean.FALSE);
            statusInfo.setMessage(String.format("Related object constraint for state prefix '%s' failed.", relatedObjStateKeyPrefix));
            return statusInfo;
        }

        return statusInfo;
    }

    public String findStateKeyPrefix(String stateKey) {
        return StringUtils.substringBefore(stateKey, ".state.");
    }

    public String makeRelatedObjectHelperKey(String entityKeyPrefix, String relatedObjKeyPrefix) {
        return entityKeyPrefix + ":" + relatedObjKeyPrefix;
    }
}
