/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;

/**
 * This class holds the constants used by the state service
 *
 * @version 2.0
 * @author Sri komandur@uw.edu
 */
public class StateServiceConstants {

    /**
     * Reference Object URIs
     */
    public static final String SERVICE_NAME_LOCAL_PART = "StateService";
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "state";
    public static final String REF_OBJECT_URI_STATE = NAMESPACE + "/" + StateInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LIFECYCLE = NAMESPACE + "/" + LifecycleInfo.class.getSimpleName();

    /**
     * StateChange types
     */
    public static final String STATE_CHANGE_TYPE_KEY = "kuali.state.change.type";

    /**
    * StateConstraint types
    */
    public static final String PRECONDITION_STATE_CONSTRAINT_TYPE_KEY = "kuali.state.constraint.type.precondition";
    public static final String PROPAGATION_STATE_CONSTRAINT_TYPE_KEY = "kuali.state.constraint.type.propagation";

    /**
     * StatePropagation types
     */
    public static final String STATE_PROPAGATION_TYPE_KEY = "kuali.state.propagation.type";



    /**
     *  StateChange states
     */
    public static final String STATE_CHANGE_LIFECYCLE_KEY = "kuali.state.change.lifecycle";
    public static final String STATE_CHANGE_STATE_ACTIVE_KEY = "kuali.state.change.state.active";

    public static final String[] STATE_CHANGE_LIFECYCLE_STATE_KEYS = {
            STATE_CHANGE_STATE_ACTIVE_KEY
    };

    public static boolean isValidStateChangeState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: STATE_CHANGE_LIFECYCLE_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  StateConstraint states
     */
    public static final String STATE_CONSTRAINT_LIFECYCLE_KEY = "kuali.state.constraint.lifecycle";
    public static final String STATE_CONSTRAINT_STATE_ACTIVE_KEY = "kuali.state.constraint.state.active";

    public static final String[] STATE_CONSTRAINT_LIFECYCLE_STATE_KEYS = {
            STATE_CONSTRAINT_STATE_ACTIVE_KEY
    };

    public static boolean isValidStateConstraintState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: STATE_CONSTRAINT_LIFECYCLE_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  StatePropagation states
     */
    public static final String STATE_PROPAGATION_LIFECYCLE_KEY = "kuali.state.propagation.lifecycle";
    public static final String STATE_PROPAGATION_STATE_ACTIVE_KEY = "kuali.state.propagation.state.active";

    public static final String[] STATE_PROPAGATION_LIFECYCLE_STATE_KEYS = {
            STATE_PROPAGATION_STATE_ACTIVE_KEY
    };

    public static boolean isValidStatePropagationState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: STATE_PROPAGATION_LIFECYCLE_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }
}
