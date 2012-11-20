package org.kuali.student.enrollment.class1.state.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Kuali Student ks.collab@kuali.org
 */
public class StateHelperFactory {

    private static final String AO_STATE_KEY_PREFIX = "kuali.lui.activity.offering";
    private static final String CO_STATE_KEY_PREFIX = "kuali.lui.course.offering";
    private static final String FO_STATE_KEY_PREFIX = "kuali.lui.format.offering";

    /**
     * Factory method for state change adapters.
     *
     * @param stateKeyPrefix
     * @return
     */
    public static StateHelper getStateHelper(String stateKeyPrefix) {
        StateHelper stateHelper = null;
        if (StringUtils.equals(stateKeyPrefix, CO_STATE_KEY_PREFIX)) {
            CourseOfferingService coService = null; // Look up?
            stateHelper = new COStateHelperImpl(coService);
        } else if (StringUtils.equals(stateKeyPrefix, AO_STATE_KEY_PREFIX)) {
            CourseOfferingService coService = null; // Look up?
            stateHelper = new AOStateHelperImpl(coService);
        }
        return stateHelper;
    }

    /**
     * Factory method for related entity lookup helpers.
     *
     * @param entityKey
     * @param relatedEntityKey
     * @return
     */
    public static RelatedObjectHelper makeRelatedObjectHelper(String entityKey, String relatedEntityKey) {
        RelatedObjectHelper relatedObjectHelper = null;
        if (StringUtils.equals(entityKey, AO_STATE_KEY_PREFIX)
                && StringUtils.equals(relatedEntityKey, CO_STATE_KEY_PREFIX)) {
            CourseOfferingService coService = null; // Look up?
            relatedObjectHelper = new RelatedObjectHelperAOtoCOImpl(coService);
        } else if (StringUtils.equals(entityKey, AO_STATE_KEY_PREFIX)
                && StringUtils.equals(relatedEntityKey, FO_STATE_KEY_PREFIX)) {
            CourseOfferingService coService = null; // Look up?
            relatedObjectHelper = new RelatedObjectHelperAOtoFOImpl(coService);
        }
        return relatedObjectHelper;
    }

    /**
     * This is the big one ... Generalized state change logic.
     */
    public static void stateChange(String entityId, String nextStateKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {

        //  FIXME: Summon the state service!
        StateService stateService = null;

        String stateKeyPrefix = findStateKeyPrefix(nextStateKey);
        StateHelper stateChangeHelper = getStateHelper(stateKeyPrefix);
        /**
         * Lookup a StateChangeInfo for the current and next states of the entity. If no StateChangeInfo is found
         * then the state change (from->to state combination) isn't valid.
         */
        String fromStateKey = stateChangeHelper.getStateKey(entityId, context);
        List<StateChangeInfo> stateChangeInfos = stateService.getStateChangesByFromStateAndToState(fromStateKey, nextStateKey, context);

        if (stateChangeInfos.size() == 0) {
            // If there are no StateChanges, the transition is allowed by default - otherwise we require a StateChange for every possible transition in the system
            stateChangeHelper.updateState(entityId, nextStateKey, context);
            return;
        } else if (stateChangeInfos.size() > 1) {
            throw new OperationFailedException("Multiple StateChanges between two states is unsupported.");
        } else if (stateChangeInfos.size() == 1) {
            List<StateConstraintInfo> constraintInfos = null;

            constraintInfos = stateService.getStateConstraintsByIds(stateChangeInfos.iterator().next().getStateConstraintIds(), context);

            if (constraintInfos != null) {
                //  HashMap for related entity and related entity state keys. Map<entityKey, List<stateKey>>
                Map<String, List<String>> relatedObjectStatesMap = new HashMap<String, List<String>>();
                //  Parallel HashMap to store the StateConstraintInfo ffr.
                Map<String, StateConstraintInfo> constraintsMap = new HashMap<String, StateConstraintInfo>();
                for (StateConstraintInfo stateConstraintInfo : constraintInfos) {
                    List<String> relatedObjectStateKeyValues = stateConstraintInfo.getRelatedObjectStateKeys();
                    mapStateKeys(relatedObjectStateKeyValues, relatedObjectStatesMap, stateConstraintInfo, constraintsMap);
                }

                //  Now check related objects constraints
                for (Map.Entry<String, List<String>> c : relatedObjectStatesMap.entrySet()) {
                    String relatedEntityKey = c.getKey();
                    StateConstraintOperator operator = constraintsMap.get(relatedEntityKey).getStateConstraintOperator();
                    RelatedObjectHelper relatedObjectHelper = makeRelatedObjectHelper(stateKeyPrefix, relatedEntityKey);
                    if (!evaluateConstraint(relatedObjectHelper.getRelatedObjectIds(entityId, context), c.getValue(), operator)) {
                        //  !!!  Fail the constraint  !!!
                    }
                }
            }
            //  If the next state is valid then go ahead and update the state.
            stateChangeHelper.updateState(entityId, nextStateKey, context);

            /**
             *  Propagate
             */
            //  Gather the StatePropagationInfos ...
            List<StatePropagationInfo> propagations = null;
            for (StateChangeInfo stateChangeInfo : stateChangeInfos) {
                propagations = stateService.getStatePropagationsByIds(stateChangeInfo.getStatePropagationIds(), context);
            }

            if (propagations != null) {
                for (StatePropagationInfo spi : propagations) {
                    StateChangeInfo stateChangeInfo = stateService.getStateChange(spi.getTargetStateChangeId(), context);
                    String propagationTargetEntityKey = findStateKeyPrefix(stateChangeInfo.getFromStateKey());
                    RelatedObjectHelper relatedObjectHelper = makeRelatedObjectHelper(stateKeyPrefix, propagationTargetEntityKey);
                    StateHelper statehelper = getStateHelper(propagationTargetEntityKey);
                    for (String id : relatedObjectHelper.getRelatedObjectIds(entityId, context)) {
                        statehelper.updateState(id, stateChangeInfo.getToStateKey(), context);
                    }
                }
            }
        }
    }


    private static void mapStateKeys(List<String> relatedObjectStateKeys, Map<String, List<String>> entityKeyStatesMap, StateConstraintInfo stateConstraintInfo, Map<String, StateConstraintInfo> constraintsMap) {
        for (String stateKey : relatedObjectStateKeys) {
            String stateKeyPrefix = findStateKeyPrefix(stateKey);

            //  Store the constraint entry if it doesn't exist already.
            if (!constraintsMap.containsKey(stateKeyPrefix)) {
                constraintsMap.put(stateKeyPrefix, stateConstraintInfo);
            }

            //  Initialize the value list if the key doesn't yet exist.
            if (!entityKeyStatesMap.containsKey(stateKeyPrefix)) {
                entityKeyStatesMap.put(stateKeyPrefix, new ArrayList<String>());
            }
            entityKeyStatesMap.get(stateKeyPrefix).add(stateKey);
        }
    }


    public static boolean evaluateConstraint(List<String> actualStateKeys, List<String> expectedStateKeys, StateConstraintOperator operator) {
        /**
         *  FIXME: Put some logics up in which evaluates constraints.
         */
        return false;
    }

    private static String findStateKeyPrefix(String stateKey) {
        return StringUtils.substringBefore(stateKey, ".state.");
    }
}
