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
 *
 * Created by Mezba Mahtab on 6/21/12
 */
package org.kuali.student.r2.core.process.constants;

/**
 * This class holds Process Service constants.
 *
 * @author Kuali Student Team
 */
public class ProcessServiceConstants {

    // Process Category
    // --------------------
    public static final String PROCESS_CATEGORY_TYPE_KEY = "kuali.process.category.type";
    public static final String PROCESS_CATEGORY_STATE_KEY = "kuali.process.process.state.active";

    // Process
    // ------------
    public static final String PROCESS_TYPE_KEY = "kuali.process.process.type";
    public static final String PROCESS_STATE_ENABLED_KEY = "kuali.process.process.state.enabled";
    public static final String PROCESS_STATE_DISABLED_KEY = "kuali.process.process.state.disabled";
    public static final String PROCESS_STATE_INACTIVE_KEY = "kuali.process.process.state.inactive";

    // Instruction
    // ----------------
    public static final String INSTRUCTION_TYPE_KEY = "kuali.process.instruction.type";
    public static final String INSTRUCTION_STATE_ENABLED_KEY = "kuali.process.instruction.state.enabled";
    public static final String INSTRUCTION_STATE_DISABLED_KEY = "kuali.process.instruction.state.disabled";

    // Check
    // ------------
    public static final String CHECK_TYPE_HOLD_KEY = "kuali.process.check.type.hold";
    public static final String CHECK_TYPE_START_DATE_KEY = "kuali.process.check.type.milestone.startdate";
    public static final String CHECK_TYPE_DEADLINE_KEY = "kuali.process.check.type.milestone.deadline";
    public static final String CHECK_TYPE_TIME_PERIOD_KEY = "kuali.process.check.type.milestone.period";
    public static final String CHECK_TYPE_PROCESS_KEY = "kuali.process.check.type.process";
    public static final String CHECK_TYPE_DIRECT_RULE_KEY = "kuali.process.check.type.rule.direct";
    public static final String CHECK_TYPE_INDIRECT_RULE_KEY = "kuali.process.check.type.rule.indirect";
    public static final String CHECK_TYPE_EQUAL_VALUE_KEY = "kuali.process.check.type.value.equals";
    public static final String CHECK_TYPE_MAXIMUM_VALUE_KEY = "kuali.process.check.type.value.max";
    public static final String CHECK_TYPE_MINIMUM_VALUE_KEY = "kuali.process.check.type.value.min";
    public static final String CHECK_TYPE_ACKNOWLEDGEMENT_KEY = "kuali.process.check.type.acknowledgement";
    public static final String CHECK_STATE_ENABLED_KEY = "kuali.process.check.state.enabled";
    public static final String CHECK_STATE_DISABLED_KEY = "kuali.process.check.state.disabled";
    public static final String CHECK_STATE_INACTIVE_KEY = "kuali.process.check.state.inactive";
}
