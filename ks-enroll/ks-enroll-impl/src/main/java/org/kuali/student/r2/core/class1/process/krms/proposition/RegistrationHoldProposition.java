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

package org.kuali.student.r2.core.class1.process.krms.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.core.hold.dto.HoldInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a proposition to evaluate if a student has a Hold that would prevent registration
 *
 * @author alubbers
 */
public class RegistrationHoldProposition extends AbstractLeafProposition {

    private final String issueKey;

    public RegistrationHoldProposition(String issueKey) {
        this.issueKey = issueKey;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {


        Term studentRegistrationHoldsTerm = new Term(RulesExecutionConstants.STUDENT_REGISTRATION_HOLDS_TERM_NAME, Collections.singletonMap(RulesExecutionConstants.ISSUE_KEY_TERM_PROPERTY, issueKey));
        List<HoldInfo> studentRegistrationHolds = environment.resolveTerm(studentRegistrationHoldsTerm, this);

        PropositionResult result = null;

        if(studentRegistrationHolds.isEmpty()) {
            result = new PropositionResult(true);
        }
        else {
            List<HoldInfo> blockingHolds = new ArrayList<HoldInfo>();
            List<HoldInfo> warningHolds = new ArrayList<HoldInfo>();

            // Split the found holds into warning and blocking holds
            for(HoldInfo hold : studentRegistrationHolds) {
                if(hold.getIsWarning()) {
                    warningHolds.add(hold);
                }
                else {
                    blockingHolds.add(hold);
                }
            }

            // if there are no blocking holds, the result of the evaluation is true
            result = new PropositionResult(blockingHolds.isEmpty());

            // check the warning holds, add the hold ids to the environment attributes if any have been found for the student
            if(!warningHolds.isEmpty()) {
                List<String> warningHoldIds = (List<String>) environment.getEngineResults().getAttribute(RulesExecutionConstants.REGISTRATION_HOLD_WARNINGS_ATTRIBUTE);
                if(warningHoldIds == null) {
                    warningHoldIds = new ArrayList<String>(warningHolds.size());
                }

                for(HoldInfo hold : warningHolds) {
                    warningHoldIds.add(hold.getId());
                }

                environment.getEngineResults().setAttribute(RulesExecutionConstants.REGISTRATION_HOLD_WARNINGS_ATTRIBUTE, warningHoldIds);
            }
        }

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult()));

        return result;
    }
}
