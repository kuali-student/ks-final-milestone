/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 * This class holds the constants used by the type service
 *
 * @author tom
 */
public class StateServiceConstants {

    /**
     * Reference Object URIs
     */
    public static final String SERVICE_NAME_LOCAL_PART = "StateService";
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "state";
    public static final String REF_OBJECT_URI_STATE = NAMESPACE + "/" + StateInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LIFECYCLE = NAMESPACE + "/" + LifecycleInfo.class.getSimpleName();   
}
