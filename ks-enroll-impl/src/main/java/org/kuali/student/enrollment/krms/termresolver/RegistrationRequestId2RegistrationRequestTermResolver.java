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
package org.kuali.student.enrollment.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * @author alubbers
 */
public class RegistrationRequestId2RegistrationRequestTermResolver implements TermResolver<RegistrationRequestInfo> {

    private final static Set<String> prereqs;

    static {
        Set<String> temp = new HashSet<String>(2);
        temp.add(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM.getName());
        temp.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        temp.add(RulesExecutionConstants.COURSE_REGISTRATION_SERVICE_TERM.getName());
        prereqs = Collections.unmodifiableSet(temp);
    }

    @Override
    public Set<String> getPrerequisites() {
        return prereqs;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.REGISTRATION_REQUEST_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public RegistrationRequestInfo resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws
            TermResolutionException {

        String registrationRequestId = (String) resolvedPrereqs.
                get(RulesExecutionConstants.REGISTRATION_REQUEST_ID_TERM.getName());
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        CourseRegistrationService courseRegistrationService = (CourseRegistrationService) resolvedPrereqs.get(
                RulesExecutionConstants.COURSE_REGISTRATION_SERVICE_TERM.getName());

        RegistrationRequestInfo info;
        try {
            info = courseRegistrationService.getRegistrationRequest(registrationRequestId, contextInfo);
        } catch (Exception ex) {
            throw new TermResolutionException("Unexpected", this, null, ex);
        }
        return info;

    }
}
