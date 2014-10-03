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
 * Created by Charles on 9/15/13
 */
package org.kuali.student.poc.eventproc.handler.impl.helper;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to compute FO/CO/RG states given a list of AOs
 *
 * @author Kuali Student Team
 */
public class FoCoRgComputeStateUtil {
    private static final Map<String, Integer> aoStateToPriority;
    static {
        aoStateToPriority = new HashMap<String, Integer>();
        aoStateToPriority.put(LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY, 0);
        aoStateToPriority.put(LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY, 1);
        aoStateToPriority.put(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, 2);
        aoStateToPriority.put(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, 3);
        aoStateToPriority.put(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, 4);
    }

    private static final Map<String, Integer> foStateToPriority;
    private static final List<String> foStates;
    static {
        foStateToPriority = new HashMap<String, Integer>();
        foStateToPriority.put(LuiServiceConstants.LUI_FO_STATE_CANCELED_KEY, 0);
        foStateToPriority.put(LuiServiceConstants.LUI_FO_STATE_SUSPENDED_KEY, 1);
        foStateToPriority.put(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, 2);
        foStateToPriority.put(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, 3);
        foStateToPriority.put(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY, 4);

        foStates = new ArrayList<String>();
        foStates.add(LuiServiceConstants.LUI_FO_STATE_CANCELED_KEY);
        foStates.add(LuiServiceConstants.LUI_FO_STATE_SUSPENDED_KEY);
        foStates.add(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY);
        foStates.add(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY);
        foStates.add(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
    }

    private static final List<String> coStates;
    static {
        coStates = new ArrayList<String>();
        coStates.add(LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY);
        coStates.add(LuiServiceConstants.LUI_CO_STATE_SUSPENDED_KEY);
        coStates.add(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        coStates.add(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY);
        coStates.add(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
    }

    private static final List<String> rgStates;
    static {
        rgStates = new ArrayList<String>();
        rgStates.add(LuiServiceConstants.REGISTRATION_GROUP_CANCELED_STATE_KEY);
        rgStates.add(LuiServiceConstants.REGISTRATION_GROUP_SUSPENDED_STATE_KEY);
        rgStates.add(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
        rgStates.add(LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY);
        rgStates.add(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
    }

    public static String computeFoState(List<ActivityOfferingInfo> aoInfos) {
        if (aoInfos == null || aoInfos.isEmpty()) {
            return LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY; // default
        }
        List<Integer> priorities = new ArrayList<Integer>();
        for (ActivityOfferingInfo aoInfo: aoInfos) {
            priorities.add(aoStateToPriority.get(aoInfo.getStateKey()));
        }
        int max = Collections.max(priorities);
        return foStates.get(max);
    }

    public static String computeCoState(List<FormatOfferingInfo> foInfos) {
        if (foInfos == null || foInfos.isEmpty()) {
            return LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY; // default
        }
        List<Integer> priorities = new ArrayList<Integer>();
        for (FormatOfferingInfo foInfo: foInfos) {
            priorities.add(foStateToPriority.get(foInfo.getStateKey()));
        }
        int max = Collections.max(priorities);
        return coStates.get(max);
    }

    public static String computeRgState(List<ActivityOfferingInfo> aoInfos) {
        // RG state computed using the minimum of AO state priorities
        if (aoInfos == null || aoInfos.isEmpty()) {
            return LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY; // default
        }
        List<Integer> priorities = new ArrayList<Integer>();
        for (ActivityOfferingInfo aoInfo: aoInfos) {
            priorities.add(aoStateToPriority.get(aoInfo.getStateKey()));
        }
        int min = Collections.min(priorities);
        return rgStates.get(min);
    }
}
