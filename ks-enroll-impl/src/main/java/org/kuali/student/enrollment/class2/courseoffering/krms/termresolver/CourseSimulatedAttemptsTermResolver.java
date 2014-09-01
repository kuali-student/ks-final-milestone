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
 * This class is a term resolver for retrieving the number of simulated
 * attempts for a student/course.
 *
 * Created by Paul Richardson on 8/19/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CourseRegistrationTermResolverSupport;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * After a course registration passes validation, it added to a simulated list of
 * registered courses before actual registration. This term resolver produces a
 * count of how many simulated attempts match the current course (based on
 * version independent id).
 *
 * @author Kuali Student Team
 */
public class CourseSimulatedAttemptsTermResolver extends CourseRegistrationTermResolverSupport<Integer> {

    @Override
    public String getOutput() {
        return RulesExecutionConstants.SIMULATED_ATTEMPTS_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(3);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        Integer simulatedAttempts;

        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationGroupInfo registrationGroupInfo = (RegistrationGroupInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());

        @SuppressWarnings("unchecked")
        List<CourseRegistrationInfo> simulatedCourses = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());

        try {
            String versionIndId = getVersionIndIdFromCourseOfferingId(registrationGroupInfo.getCourseOfferingId(), contextInfo);
            simulatedAttempts = getMatchedCoursesCount(versionIndId, simulatedCourses, contextInfo);
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
            simulatedAttempts = null;
        }

        return simulatedAttempts;
    }
}
