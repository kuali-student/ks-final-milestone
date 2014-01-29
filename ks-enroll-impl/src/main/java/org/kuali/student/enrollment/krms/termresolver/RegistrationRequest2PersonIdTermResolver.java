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
package org.kuali.student.core.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;

/**
 * @author alubbers
 */
public class RegistrationRequest2PersonIdTermResolver implements TermResolver<String> {

    private final static Set<String> prereqs;

    static {
        Set<String> temp = new HashSet<String>(2);
        temp.add(RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName());
        prereqs = Collections.unmodifiableSet(temp);
    }

    @Override
    public Set<String> getPrerequisites() {
        return prereqs;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.PERSON_ID_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        RegistrationRequestInfo request = (RegistrationRequestInfo) resolvedPrereqs.get(
                RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName());
        // TODO: modify this to be the person id once it is added to the request
        return request.getRequestorId();

    }
}
