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
 * Created by Charles on 5/17/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AoStateTransitionRefSolution {
    // Only supports draft, approved, offered AO states
    public static final TransitionGridYesNoEnum [][] AO_STATE_SOC_DRAFT =    {
            {TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.NO},
            {TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.NO},
            {TransitionGridYesNoEnum.INVALID, TransitionGridYesNoEnum.INVALID, TransitionGridYesNoEnum.INVALID} };  // Technically, can't be in offered unless SOC in publishing/published

    public static final TransitionGridYesNoEnum [][] AO_STATE_SOC_OPEN = AO_STATE_SOC_DRAFT;
    public static final TransitionGridYesNoEnum [][] AO_STATE_SOC_LOCKED = AO_STATE_SOC_DRAFT;
    public static final TransitionGridYesNoEnum [][] AO_STATE_SOC_FINAL_EDITS = AO_STATE_SOC_DRAFT;

    public static final TransitionGridYesNoEnum [][] AO_STATE_SOC_PUBLISHING =    {
            {TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES},
            {TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES},
            {TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES, TransitionGridYesNoEnum.YES} };
    public static final TransitionGridYesNoEnum [][] AO_STATE_SOC_PUBLISHED =  AO_STATE_SOC_PUBLISHING;
    public static Map<String, TransitionGridYesNoEnum [][]> SOC_STATE_TO_AO_TRANSITION;
    static {
        SOC_STATE_TO_AO_TRANSITION = new HashMap<String, TransitionGridYesNoEnum [][]>();
        SOC_STATE_TO_AO_TRANSITION.put(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY, AO_STATE_SOC_DRAFT);
        SOC_STATE_TO_AO_TRANSITION.put(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, AO_STATE_SOC_OPEN);
        SOC_STATE_TO_AO_TRANSITION.put(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, AO_STATE_SOC_LOCKED);
        SOC_STATE_TO_AO_TRANSITION.put(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, AO_STATE_SOC_FINAL_EDITS);
        SOC_STATE_TO_AO_TRANSITION.put(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, AO_STATE_SOC_PUBLISHING);
        SOC_STATE_TO_AO_TRANSITION.put(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY, AO_STATE_SOC_PUBLISHED);
    }
    // AO states (currently, only draft, approved, offered since state matrix doesn't indicate any more
    public static final List<String> AO_STATES_ORDERED;
    static {
        AO_STATES_ORDERED = new ArrayList<String>();
        AO_STATES_ORDERED.add(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
        AO_STATES_ORDERED.add(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
        // can only get to this state if SOC state is publishing/published
        AO_STATES_ORDERED.add(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
    }

    public static PseudoUnitTestStateTransitionGrid getReferenceGridForState(String socState) {
        final TransitionGridYesNoEnum[][] stateTransition = SOC_STATE_TO_AO_TRANSITION.get(socState);
        List<String> values = new ArrayList<String>();
        values.add(TransitionGridYesNoEnum.YES.getName());
        values.add(TransitionGridYesNoEnum.NO.getName());
        values.add(TransitionGridYesNoEnum.INVALID.getName());
        PseudoUnitTestStateTransitionGrid grid = new PseudoUnitTestStateTransitionGrid(AoStateTransitionRefSolution.AO_STATES_ORDERED, values, "ao");
        grid.setSocStateKey(socState);
        for (int from = 0; from < stateTransition.length; from++) {
            for (int to = 0; to < stateTransition.length; to++) {
                grid.setTransition(TransitionGridTypeEnum.EXPECTED, from, to, stateTransition[from][to].getName());
            }
        }
        return grid;
    }
}