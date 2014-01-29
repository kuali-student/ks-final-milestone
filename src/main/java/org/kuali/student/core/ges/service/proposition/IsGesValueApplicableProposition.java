/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 1/29/14
 */
package org.kuali.student.core.ges.service.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;

/**
 * This class represents a proposition that checks whether a Ges Service value is applicable.
 *
 * @author Mezba Mahtab
 */
public class IsGesValueApplicableProposition extends AbstractLeafProposition {

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        // for now, return true
        return this.recordResultWithNoDetails(environment, true);
    }
}
