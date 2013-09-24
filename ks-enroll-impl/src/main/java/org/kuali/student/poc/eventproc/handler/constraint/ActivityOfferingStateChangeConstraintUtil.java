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

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.poc.eventproc.KSEventProcessorImpl;
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

import java.util.List;

/**
 * Determines whether AO can change state to toState (based on SOC state, etc)
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingStateChangeConstraintUtil {
    public static ConstraintResult checkConstraint(ActivityOfferingInfo aoInfo, String toState, KSInternalEventProcessor processor, ContextInfo context)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        ConstraintResult result = new ConstraintResult();
        String fromState = aoInfo.getStateKey();
        List<StateChangeInfo> stateChanges =
                processor.getStateService().getStateChangesByFromStateAndToState(fromState, toState, context);
        StateChangeInfo stateChange = null;
        if (stateChanges == null || stateChanges.isEmpty()) {
            // Quit
            result.addError("Error: from/to state identical");
            return result;
        } else if (stateChanges.size() == 1) {
            stateChange = stateChanges.get(0);
        } else {
            throw new OperationFailedException("Multiple state changes found");
        }
        boolean valid = false;
        if (LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY.equals(toState)) {
            // Can only change to suspended in SOC state that's not in {draft, open}
            List<String> socIds = processor.getSocService().getSocIdsByTerm(aoInfo.getTermId(), context);
            if (socIds == null || socIds.isEmpty()) {
                throw new OperationFailedException("No SOC found for term: " + aoInfo.getTermId());
            }
            SocInfo mainSoc = null;
            for (String socId: socIds) {
                SocInfo socInfo = processor.getSocService().getSoc(socId, context);
                if (CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY.equals(socInfo.getTermId())) {
                    mainSoc = socInfo;
                    break;
                }
            }
            if (mainSoc == null) {
                result.addError("Error: no main SOC");
                return result;
            } else {
                valid = !CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY.equals(mainSoc.getStateKey())
                        && !CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY.equals(mainSoc.getStateKey());
                if (!valid) {
                    result.addError("Error: can't suspend in SOC state: " + mainSoc.getStateKey());
                    return result;
                }
            }
        } else if (LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(fromState) &&
                !LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(toState)) {
            // Canceled must go to draft
            result.addError("Error: canceled AO state can only go to draft");
        } else {
            result.setSatisfiesConstraint(true);
        }
        return result;
    }
}
