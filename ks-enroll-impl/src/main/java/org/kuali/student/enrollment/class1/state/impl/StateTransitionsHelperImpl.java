package org.kuali.student.enrollment.class1.state.impl;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class StateTransitionsHelperImpl implements StateTransitionsHelper {

    // The following are injected through Spring config
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

        String stateKeyPrefix = findStateKeyPrefix(nextStateKey);
        StateHelper stateChangeHelper = this.stateHelperMap.get(stateKeyPrefix);
        String fromStateKey = stateChangeHelper.getStateKey(entityId, context);
        List<StateChangeInfo> stateChangeInfos = this.stateService.getStateChangesByFromStateAndToState(fromStateKey, nextStateKey, context);

        if (stateChangeInfos.size() == 0) {
            // If there are no StateChanges, the transition is allowed by default - otherwise we require a StateChange for every possible transition in the system
            stateChangeHelper.updateState(entityId, nextStateKey, context);
            return new StatusInfo();
        } else if (stateChangeInfos.size() > 1) {
            throw new OperationFailedException("Multiple StateChanges between two states is unsupported.");
        }
        List<StateConstraintInfo> constraintInfos = null;

        constraintInfos = this.stateService.getStateConstraintsByIds(stateChangeInfos.iterator().next().getStateConstraintIds(), context);

        if (constraintInfos != null) {
            //  Map<relatedObjectStateKeyPrefix, List<relatedObjectStateKeysInTheConstraint>>
            Map<String, List<String>> relatedObjectStateKeysMap = new HashMap<String, List<String>>();
            //  Map<relatedObjectStateKeyPrefix, associatedConstraint>
            Map<String, StateConstraintInfo> constraintsMap = new HashMap<String, StateConstraintInfo>();
            for (StateConstraintInfo stateConstraintInfo : constraintInfos) {
                List<String> relatedObjectStateKeyValues = stateConstraintInfo.getRelatedObjectStateKeys();
                mapStateKeys(relatedObjectStateKeysMap, constraintsMap, relatedObjectStateKeyValues, stateConstraintInfo);
            }

            //  Now check related objects constraint
            for (Map.Entry<String, List<String>> relatedMapEntry : relatedObjectStateKeysMap.entrySet()) {
                String relatedObjStateKeyPrefix = relatedMapEntry.getKey();
                StateConstraintOperator operator = constraintsMap.get(relatedObjStateKeyPrefix).getStateConstraintOperator();
                RelatedObjectHelper relatedObjectHelper = this.relatedObjectHelperMap.get(makeRelatedObjectHelperKey(stateKeyPrefix, relatedObjStateKeyPrefix));
                if (!evaluateConstraint(relatedObjectHelper.getRelatedObjectStateKeys(entityId, context), relatedMapEntry.getValue(), operator)) {
                    statusInfo.setSuccess(Boolean.FALSE);
                    statusInfo.setMessage("One of the constraints failed. StateConstraint id: " + constraintsMap.get(relatedObjStateKeyPrefix).getId());
                }
            }
        }

        return statusInfo;
    }

    @Override
    public Map<String, StatusInfo> processStatePropagations(String entityId, String nextStateKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {

        Map<String, StatusInfo> resultMap = new HashMap<String, StatusInfo>();

        //  Gather the StatePropagationInfos ...
        List<StatePropagationInfo> propagations = null;
        String stateKeyPrefix = findStateKeyPrefix(nextStateKey);
        StateHelper stateChangeHelper = this.stateHelperMap.get(stateKeyPrefix);
        String fromStateKey = stateChangeHelper.getStateKey(entityId, context);
        List<StateChangeInfo> stateChangeInfos = this.stateService.getStateChangesByFromStateAndToState(fromStateKey, nextStateKey, context);
        if (stateChangeInfos.size() == 0) {
            return resultMap;
        } else if (stateChangeInfos.size() > 1) {
            throw new OperationFailedException("Multiple StateChanges between two states is unsupported.");
        }
        propagations = this.stateService.getStatePropagationsByIds(stateChangeInfos.iterator().next().getStatePropagationIds(), context);
        if (propagations != null) {
            for (StatePropagationInfo statePropagationInfo : propagations) {
                StateChangeInfo stateChangeInfo = this.stateService.getStateChange(statePropagationInfo.getTargetStateChangeId(), context);
                String targetStateKey = findStateKeyPrefix(stateChangeInfo.getFromStateKey());
                RelatedObjectHelper relatedObjectHelper = this.relatedObjectHelperMap.get(makeRelatedObjectHelperKey(stateKeyPrefix, targetStateKey));
                StateHelper statehelper = this.stateHelperMap.get(targetStateKey);
                for (String id : relatedObjectHelper.getRelatedObjectIds(entityId, context)) {
                    StatusInfo statusInfo = statehelper.updateState(id, stateChangeInfo.getToStateKey(), context);
                    resultMap.put(id, statusInfo);
                }
            }
        }
        return resultMap;
    }


    private static void mapStateKeys(Map<String, List<String>> relatedObjectStateKeyMap, Map<String, StateConstraintInfo> constraintsMap, List<String> relatedObjectStateKeys, StateConstraintInfo stateConstraintInfo) {
        for (String relatedStateKey : relatedObjectStateKeys) {
            String relatedStateKeyPrefix = findStateKeyPrefix(relatedStateKey);

            //  Store the constraint entry if it doesn't exist already.
            if (!constraintsMap.containsKey(relatedStateKeyPrefix)) {
                constraintsMap.put(relatedStateKeyPrefix, stateConstraintInfo);
            }

            //  Initialize the value list if the key doesn't yet exist.
            if (!relatedObjectStateKeyMap.containsKey(relatedStateKeyPrefix)) {
                relatedObjectStateKeyMap.put(relatedStateKeyPrefix, new ArrayList<String>());
            }
            relatedObjectStateKeyMap.get(relatedStateKeyPrefix).add(relatedStateKey);
        }
    }


    private static Boolean evaluateConstraint(Set<String> actualStateKeySet, List<String> expectedStateKeys, StateConstraintOperator operator) {

        Set<String> expectedStateKeySet = new HashSet<String>(expectedStateKeys);
        Boolean successFlag = Boolean.FALSE;

        int origActualSetSize = actualStateKeySet.size();
        actualStateKeySet.removeAll(expectedStateKeys);

        if ((operator == StateConstraintOperator.ALL) && (actualStateKeySet.size() == 0)) {
            successFlag = Boolean.TRUE;
        } else if ((operator == StateConstraintOperator.EXISTS) && (origActualSetSize > actualStateKeySet.size())) {
            successFlag = Boolean.TRUE;
        } else if ((operator == StateConstraintOperator.NONE) && (origActualSetSize == actualStateKeySet.size())) {
            successFlag = Boolean.TRUE;
        }

        return successFlag;
    }

    private static String findStateKeyPrefix(String stateKey) {
        return StringUtils.substringBefore(stateKey, ".state.");
    }

    private static String makeRelatedObjectHelperKey(String entityKeyPrefix, String relatedObjKeyPrefix) {
        return entityKeyPrefix + ":" + relatedObjKeyPrefix;
    }
}
