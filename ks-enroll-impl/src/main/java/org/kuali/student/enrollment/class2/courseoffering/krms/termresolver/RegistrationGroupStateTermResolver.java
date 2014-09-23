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
 * Created by jmorris on 9/17/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is a term resolver for the state of a provided registration group.
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupStateTermResolver implements TermResolver<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationGroupStateTermResolver.class);

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_REG_GROUP_STATE;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(1);
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        RegistrationGroupInfo regGroupInfo = (RegistrationGroupInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());

        String stateKey = regGroupInfo.getStateKey();

        if (stateKey == null || !stateKey.equals(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY)) {
            LOGGER.warn("State check failed for regGroup {}. Key: {}", regGroupInfo.getId(), stateKey);
        }

        return stateKey;
    }
}