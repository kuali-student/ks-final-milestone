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
 * This class is a term resolver for retrieving the total number of
 * attempts for a student/course.
 *
 * Created by Paul Richardson on 8/19/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Returns the total number of attempts for a student and course
 *
 * @author Kuali Student Team
 */
public class CourseTotalAttemptsTermResolver implements TermResolver<Integer> {

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSE_TOTAL_ATTEMPTS;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_COMPLETED_ATTEMPTS);
        prereqs.add(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REGISTERED_COUNT);

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        Integer completedAttempts = (Integer) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_COMPLETED_ATTEMPTS);
        Integer registeredCount = (Integer) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REGISTERED_COUNT);

        return completedAttempts + registeredCount;
    }
}
