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
package org.kuali.student.core.krms.proposition;

import java.util.Date;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;

/**
 * This proposition is used to evaluate whether or not a person is alive
 *
 * @author alubbers
 */
public class IsAliveProposition extends AbstractLeafProposition {

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        Date deceasedDate = environment.resolveTerm(RulesExecutionConstants.STUDENT_DECEASED_DATE_TERM, this);

        if (deceasedDate == null) {
            return this.recordResultWithNoDetails(environment, true);
        }
        return this.recordResultWithNoDetails(environment, false);
    }
}
