/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.brms.ruleexecution.runtime.report;

import java.util.Map;

import org.kuali.student.brms.internal.common.statement.PropositionContainer;
import org.kuali.student.brms.internal.common.statement.report.RuleReport;
import org.kuali.student.brms.ruleexecution.runtime.report.ast.exceptions.MessageBuilderException;

public interface ReportBuilder {
    /**
     * Creates proposition report.
     * 
     * @param propContainer Contains a list of propositions
     * @return Rule report
     * @throws MessageBuilderException Errors building message
     */
    public RuleReport buildReport(PropositionContainer propContainer);

    /**
     * Builds a report using proposition message templates and message 
     * context objects.
     * proposition message template keys must match keys in the message 
     * context map.
     * 
     * @param pc Contains a list of propositions
     * @param messageContextMap Message context map
     * @return Rule report
     * @throws MessageBuilderException Errors building message
     */
    public RuleReport buildReport(PropositionContainer propContainer, Map<String, Object> messageContextMap);
}
