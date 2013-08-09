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

package org.kuali.student.r2.core.process.krms.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.process.context.CourseRegistrationProcessContextInfo;
import org.kuali.student.r2.core.process.evaluator.ProcessEvaluator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class evaluates a sub process
 *
 * @author alubbers
 */
public class SubProcessProposition extends AbstractLeafProposition {

    private CourseRegistrationProcessContextInfo checkContext;
    private ProcessEvaluator<CourseRegistrationProcessContextInfo> processEvaluator;

    public SubProcessProposition(CourseRegistrationProcessContextInfo checkContext, ProcessEvaluator<CourseRegistrationProcessContextInfo> processEvaluator) {
        this.checkContext = checkContext;
        this.processEvaluator = processEvaluator;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        ContextInfo context = environment.resolveTerm(new Term(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME), this);
        List<ValidationResultInfo> results = null;

        Map<String, Object> resultDetails = new HashMap<String, Object>();

        try {
            results = processEvaluator.evaluate(checkContext, context);
        } catch (OperationFailedException e) {

            // on an evaluation exception, report the details of the exception to the KRMS environment
            resultDetails.put(RulesExecutionConstants.SUBPROCESS_EVALUATION_EXCEPTION, e);
            environment.getEngineResults().addResult(new BasicResult(resultDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, false));
            return new PropositionResult(false);

        }

        // add all the validation results into the map
        resultDetails.put(RulesExecutionConstants.SUBPROCESS_EVALUATION_RESULTS, results);

        boolean propositionResult = true;
        for (ValidationResultInfo evalResult : results) {
            // if any result is an error, the entire proposition evaluation is false
            if(evalResult.isError()) {
                propositionResult = false;
                break;
            }
        }

        // add a result in the environment that can be analyzed by callers of the KRMS engine
        environment.getEngineResults().addResult(new BasicResult(resultDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment, propositionResult));

        return new PropositionResult(propositionResult);
    }

}
