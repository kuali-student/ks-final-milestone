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
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

/**
 * This proposition evaluates whether a student is a summer student and if so, checks that the term being registered is a summer term
 *
 * @author alubbers
 */
public class SummerTermProposition extends AbstractLeafProposition {

    private TermInfo term;

    public SummerTermProposition(TermInfo term) {
        this.term = term;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        PropositionResult result = new PropositionResult(term.getTypeKey().equals(AtpServiceConstants.ATP_SUMMER_TYPE_KEY));

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult()));

        return result;
    }
}
