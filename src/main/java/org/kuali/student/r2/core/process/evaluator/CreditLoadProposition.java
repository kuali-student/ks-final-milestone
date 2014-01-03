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
package org.kuali.student.r2.core.process.evaluator;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;

/**
 * This proposition is used to evaluate whether or not a person is alive
 *
 * @author alubbers
 */
public class CreditLoadProposition extends AbstractLeafProposition {

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);
        String registrationRequestId = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        String atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
        
        // TODO: wire in the actual credit load calculation
        KualiDecimal value = new KualiDecimal ("14");
        return this.recordSuccessWithDecimalValueDetail(environment, value);
    }
}
