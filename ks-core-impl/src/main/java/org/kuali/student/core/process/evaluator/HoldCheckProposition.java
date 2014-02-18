/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.core.process.evaluator;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;

import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

/**
 * This class represents a proposition to evaluate if a student has a Hold
 *
 * @author nwright
 */
public class HoldCheckProposition extends AbstractCheckProposition {

    public HoldCheckProposition(InstructionInfo instruction, CheckInfo check) {
        super(instruction, check);
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        HoldService holdService = environment.resolveTerm(RulesExecutionConstants.HOLD_SERVICE_TERM, this);

        // Business may decide this method should take an as of date and if so what that date should be
        // if so these lines are how we may want to evaluate the as of date as of the start of the term if the term is available
//        String atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
//        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);
        List<AppliedHoldInfo> appliedHolds;
        try {
            appliedHolds = holdService.getActiveAppliedHoldsByIssueAndPerson(check.getHoldIssueId(), personId, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        if (appliedHolds.isEmpty()) {
            return this.recordSuccessResult(environment);
        }
        return this.recordFailureResultOrExemption(environment);
    }
}
