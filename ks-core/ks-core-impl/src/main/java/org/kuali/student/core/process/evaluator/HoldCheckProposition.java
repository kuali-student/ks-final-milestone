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
import org.kuali.student.common.date.utils.EffectiveDateUtils;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.core.atp.infc.Atp;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.hold.infc.HoldIssue;
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
        AtpService atpService = environment.resolveTerm(RulesExecutionConstants.ATP_SERVICE_TERM, this);

        // Business may decide this method should take an as of date and if so what that date should be
        // if so these lines are how we may want to evaluate the as of date as of the start of the term if the term is available
        List<AppliedHoldInfo> returnAppliedHolds = new ArrayList<>();
        try {
            List<AppliedHoldInfo> appliedHolds = holdService.getActiveAppliedHoldsByIssueAndPerson(check.getHoldIssueId(), personId, contextInfo);
            for (AppliedHoldInfo appliedholdInfo : appliedHolds) {

                HoldIssue holdIssue = holdService.getHoldIssue(appliedholdInfo.getHoldIssueId(), contextInfo);
                if (holdIssue.getIsHoldIssueTermBased()) {
                    Date startDate = atpService.getAtp(appliedholdInfo.getApplicationEffectiveTermId(), contextInfo).getStartDate();
                    Date endDate = null;
                    if (appliedholdInfo.getApplicationExpirationTermId() != null) {
                        endDate = atpService.getAtp(appliedholdInfo.getApplicationExpirationTermId(), contextInfo).getEndDate();
                    }

                    //Check if registering term is within applied term range.
                    Atp atp = environment.resolveTerm(RulesExecutionConstants.ATP_TERM, this);
                    if(EffectiveDateUtils.isTargetDateEffective(startDate, endDate, atp.getStartDate())){
                        returnAppliedHolds.add(appliedholdInfo);
                    }
                } else { //Not term based
                    returnAppliedHolds.add(appliedholdInfo);
                }
            }
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        if (returnAppliedHolds.isEmpty()) {
            return this.recordSuccessResult(environment);
        }
        return this.recordFailureResultOrExemption(environment);
    }
}
