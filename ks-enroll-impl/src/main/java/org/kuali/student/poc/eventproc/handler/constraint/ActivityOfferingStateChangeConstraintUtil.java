/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 9/16/13
 */
package org.kuali.student.poc.eventproc.handler.constraint;

import org.apache.xerces.xs.AttributePSVI;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Determines whether AO can change state to toState (based on SOC state, etc)
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingStateChangeConstraintUtil {
    public static final Map<SocAoFromStateToStateElement, AoStateTransitionEnum> aoStateTransitionGrid;
    public static final Set<IllegalAoStateBySocElement> illegalAoStatesBySoc = new HashSet<IllegalAoStateBySocElement>();
    static {
        aoStateTransitionGrid = new HashMap<SocAoFromStateToStateElement, AoStateTransitionEnum>();
        final String draft = LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY;
        final String approved = LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY;
        final String offered = LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY;
        final String suspended = LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY;
        final String canceled = LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY;

        String socState = null;
        // SOC state = draft
        socState = CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY;
        _addTransition(socState, draft, draft, AoStateTransitionEnum.SAME);
        _addTransition(socState, draft, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, approved, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, approved, AoStateTransitionEnum.SAME);
        _addTransition(socState, approved, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, approved, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, approved, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, offered, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, suspended, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, canceled, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, canceled, approved, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, canceled, AoStateTransitionEnum.SAME);

        // SOC state = open
        socState = CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY;
        _addTransition(socState, draft, draft, AoStateTransitionEnum.SAME);
        _addTransition(socState, draft, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, approved, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, approved, AoStateTransitionEnum.SAME);
        _addTransition(socState, approved, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, approved, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, offered, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, suspended, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, suspended, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, canceled, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, canceled, approved, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, canceled, AoStateTransitionEnum.SAME);

        // SOC state = locked
        socState = socState;
        _addTransition(socState, draft, draft, AoStateTransitionEnum.SAME);
        _addTransition(socState, draft, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, approved, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, approved, AoStateTransitionEnum.SAME);
        _addTransition(socState, approved, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, approved, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, offered, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, suspended, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, suspended, suspended, AoStateTransitionEnum.SAME);
        _addTransition(socState, suspended, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, canceled, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, canceled, approved, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, canceled, AoStateTransitionEnum.SAME);

        // SOC state = final edits
        socState = CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY;
        _addTransition(socState, draft, draft, AoStateTransitionEnum.SAME);
        _addTransition(socState, draft, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, approved, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, approved, AoStateTransitionEnum.SAME);
        _addTransition(socState, approved, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, approved, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, offered, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, suspended, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, suspended, suspended, AoStateTransitionEnum.SAME);
        _addTransition(socState, suspended, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, canceled, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, canceled, approved, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, canceled, AoStateTransitionEnum.SAME);

        // SOC state = publishing
        socState = CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY;
        _addTransition(socState, draft, draft, AoStateTransitionEnum.SAME);
        _addTransition(socState, draft, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, approved, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, approved, AoStateTransitionEnum.SAME);
        _addTransition(socState, approved, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, approved, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, offered, draft, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, approved, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, offered, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, suspended, AoStateTransitionEnum.INVALID);
        _addTransition(socState, offered, canceled, AoStateTransitionEnum.INVALID);

        _addTransition(socState, suspended, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, suspended, suspended, AoStateTransitionEnum.SAME);
        _addTransition(socState, suspended, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, canceled, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, canceled, approved, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, canceled, AoStateTransitionEnum.SAME);

        // SOC state = publishing
        socState = CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY;
        _addTransition(socState, draft, draft, AoStateTransitionEnum.SAME);
        _addTransition(socState, draft, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, draft, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, draft, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, approved, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, approved, AoStateTransitionEnum.SAME);
        _addTransition(socState, approved, offered, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, approved, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, offered, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, offered, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, offered, offered, AoStateTransitionEnum.SAME);
        _addTransition(socState, offered, suspended, AoStateTransitionEnum.YES);
        _addTransition(socState, offered, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, suspended, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, approved, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, offered, AoStateTransitionEnum.YES);
        _addTransition(socState, suspended, suspended, AoStateTransitionEnum.SAME);
        _addTransition(socState, suspended, canceled, AoStateTransitionEnum.YES);

        _addTransition(socState, canceled, draft, AoStateTransitionEnum.YES);
        _addTransition(socState, canceled, approved, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, offered, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, suspended, AoStateTransitionEnum.NO);
        _addTransition(socState, canceled, canceled, AoStateTransitionEnum.SAME);

        // Add illegal states by soc
        _addIllegalStateBySoc(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY, offered);
        _addIllegalStateBySoc(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY, suspended);
        _addIllegalStateBySoc(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, offered);
        _addIllegalStateBySoc(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, suspended);
        _addIllegalStateBySoc(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, offered);
        _addIllegalStateBySoc(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, offered);
    }

    private static void _addTransition(String socState, String aoFromState, String aoToState, AoStateTransitionEnum val) {
        SocAoFromStateToStateElement elt = new SocAoFromStateToStateElement(socState, aoFromState, aoToState);
        aoStateTransitionGrid.put(elt, val);
    }

    private static void _addIllegalStateBySoc(String socState, String aoState) {
        IllegalAoStateBySocElement elt = new IllegalAoStateBySocElement(socState, aoState);
        illegalAoStatesBySoc.add(elt);
    }

    private static boolean _isValidAoStateInSoc(String aoState, String socState) {
        IllegalAoStateBySocElement elt = new IllegalAoStateBySocElement(socState, aoState);
        return !illegalAoStatesBySoc.contains(elt);
    }

    private static AoStateTransitionEnum _getAoStateTransition(String fromState, String toState, String socState) {
        SocAoFromStateToStateElement elt = new SocAoFromStateToStateElement(socState, fromState, toState);
        return aoStateTransitionGrid.get(elt);
    }

    public static ConstraintResult checkConstraint(ActivityOfferingInfo aoInfo,
                                                   String toState,
                                                   KSInternalEventProcessor processor,
                                                   ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        ConstraintResult result = new ConstraintResult();
        SocInfo mainSoc = CourseOfferingSetUtil.getMainSocForTermId(aoInfo.getTermId(), context);

        String fromState = aoInfo.getStateKey();
        // Check if socState is valid
        if (mainSoc == null) {
            result.addError("Error: missing SOC");
            return result;
        }
        String socState = mainSoc.getStateKey();
        // Check if fromState is valid
        if (!_isValidAoStateInSoc(fromState, socState)) {
            result.addError("Error: illegal start state (" + fromState + ") for SOC state: " + socState);
            return result;
        }
        // Check if transition is valid
        AoStateTransitionEnum value = _getAoStateTransition(fromState, toState, socState);
        if (value == null) {
            result.addError("Error: no AO state transition found");
        } else if (value == AoStateTransitionEnum.INVALID) {
            result.addError("Error: AO transition is invalid due to bad start state"); // shouldn't happen
        } else if (value == AoStateTransitionEnum.NO) {
            result.addError("Error: AO transition is not allowed");
        } else if (value == AoStateTransitionEnum.SAME) {
            result.addError("Error: AO from state and to state are same: " + fromState);
            result.setNoStateChange(true);
        } // else result is YES, so successful

        return result;
    }
}
