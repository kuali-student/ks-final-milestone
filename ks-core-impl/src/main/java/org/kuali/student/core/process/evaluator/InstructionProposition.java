/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.core.process.evaluator;

import java.util.Date;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.process.service.ProcessService;

/**
 * A proposition that evaluates an instruction
 *
 * @author alubbers
 */
public class InstructionProposition extends AbstractLeafProposition {

    private InstructionInfo instruction;

    public InstructionProposition() {
    }

    public InstructionProposition(InstructionInfo instruction) {
        this.instruction = instruction;
    }

    public InstructionInfo getInstruction() {
        return instruction;
    }

    public void setInstruction(InstructionInfo instruction) {
        this.instruction = instruction;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);

        // get all the needed services from the execution context
        // we do this so we can locally cache some services and the cache lives just for the length of krms execution
        ProcessService processService = environment.resolveTerm(RulesExecutionConstants.PROCESS_SERVICE_TERM, this);

        CheckInfo check;
        try {
            check = processService.getCheck(instruction.getCheckId(), contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        Proposition proposition;
        try {
            proposition = this.constructProposition(check);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        PropositionResult result = proposition.evaluate(environment);
        return result;
    }

    private Proposition constructProposition(CheckInfo check) throws Exception {

        // TODO: when we can use java7 change to a switch  statement based on the type
        // if a sub-process is found,
        if (check.getTypeKey().equals(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY)) {
            Proposition prop = new ChildProcessCheckProposition(instruction, check);
            return prop;
        }
        // always false
        if (check.getTypeKey().equals(ProcessServiceConstants.ALWAYS_FALSE_CHECK_TYPE_KEY)) {
            Proposition prop = new AlwaysFalseCheckProposition(instruction, check);
            return prop;
        }
        // hold check
        if (check.getTypeKey().equals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY)) {
            Proposition prop = new HoldCheckProposition(instruction, check);
            return prop;
        }
        // start date check use the milestone proposition configured with after
        if (check.getTypeKey().equals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY)) {
            Proposition prop = new MilestoneStartDateCheckProposition(instruction, check);
            return prop;
        }
        // deadline check use the milestone proposition with the before
        if (check.getTypeKey().equals(ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY)) {
            Proposition prop = new MilestoneDeadlineDateCheckProposition(instruction, check);
            return prop;
        }
        // range check use milestone with between 
        if (check.getTypeKey().equals(ProcessServiceConstants.TIME_PERIOD_CHECK_TYPE_KEY)) {
            Proposition prop = new MilestoneTimePeriodDateCheckProposition(instruction, check);
            return prop;
        }
        // direct rule get the proposition for that rule
        if (check.getTypeKey().equals(ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY)) {
            Proposition prop = new DirectRuleCheckProposition(instruction, check);
            return prop;
        }
        // minimum value check key
        if (check.getTypeKey().equals(ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY)) {
            Proposition prop = new MinValueCheckProposition(instruction, check);
            return prop;
        }
        // max value check key
        if (check.getTypeKey().equals(ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY)) {
            Proposition prop = new MaxValueCheckProposition(instruction, check);
            return prop;
        }
        // equals value check key
        if (check.getTypeKey().equals(ProcessServiceConstants.EQUALS_VALUE_CHECK_TYPE_KEY)) {
            Proposition prop = new EqualsValueCheckProposition(instruction, check);
            return prop;
        }
        throw new OperationFailedException("unknown/unsupported check type" + check.getTypeKey() + " check=" + check);
    }
}
