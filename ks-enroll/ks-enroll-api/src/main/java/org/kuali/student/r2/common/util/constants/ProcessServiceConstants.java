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

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;


/**
 * This class holds the constants used by the Process service.
 *
 * @author tom
 */

public class ProcessServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "process";
    public static final String REF_OBJECT_URI_PROCESS_CATEGORY = NAMESPACE + "/" + ProcessCategoryInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_PROCESS = NAMESPACE + "/" + ProcessInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_CHECK = NAMESPACE + "/" + CheckInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_INSTRUCTION = NAMESPACE + "/" + InstructionInfo.class.getSimpleName();

    /**
     * ProcessCategory types
     */
    public static final String PROCESS_CATEGORY_TYPE_KEY = "kuali.process.type.process.category";

    /**
     * Process types
     */
    public static final String PROCESS_TYPE_KEY = "kuali.process.type.process";

    /**
     * Check types
     */
    public static final String CHECK_TYPE_KEY = "kuali.process.type.check";

    /**
     * Instruction types
     */
    public static final String INSTRUCTION_TYPE_KEY = "kuali.process.type.instruction";

    /**
     * States for Process Categories
     */
    public static final String PROCESS_CATEGORY_LIFECYCLE_KEY = "kuali.process.process.category.lifecycle";

    /**
     * States for Processes
     */
    public static final String PROCESS_LIFECYCLE_KEY = "kuali.process.process.lifecycle";
    public static final String PROCESS_ENABLED_STATE_KEY = "kuali.process.instruction.state.active";
    public static final String PROCESS_DISABLED_STATE_KEY = "kuali.process.instruction.state.inactive";
    public static final String[] PROCESS_LIFECYCLE_KEYS = {
        PROCESS_ENABLED_STATE_KEY,
        PROCESS_DISABLED_STATE_KEY
    };

    /**
     * States for Checks
     */
    public static final String CHECK_LIFECYCLE_KEY = "kuali.process.check.lifecycle";

    /**
     * States for Instructions
     */
    public static final String INSTRUCTION_LIFECYCLE_KEY = "kuali.process.instruction.lifecycle";
    public static final String INSTRUCTION_ACTIVE_STATE_KEY = "kuali.process.instruction.state.active";
    public static final String INSTRUCTION_INACTIVE_STATE_KEY = "kuali.process.instruction.state.inactive";
    public static final String[] INSTRUCTION_LIFECYCLE_KEYS = {
        INSTRUCTION_ACTIVE_STATE_KEY,
        INSTRUCTION_INACTIVE_STATE_KEY
    };
}
