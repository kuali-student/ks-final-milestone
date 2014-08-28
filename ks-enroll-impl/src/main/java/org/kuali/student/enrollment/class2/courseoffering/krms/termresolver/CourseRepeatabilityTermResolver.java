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
 * This class is a term resolver for checking the total course attempts against
 * the max repeatability rules for that course.
 *
 * Returns a string indicating error, warning, or success
 *
 * Created by Paul Richardson on 8/19/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Returns String based on:
 * -- total times the course has been attempted (prereq)
 * -- max repeatability for this course (prereq)
 *
 * If total attempts >= max repeats, return an error string
 * If total attempts < max repeats and total attempts >= (max repeats - 1), return a warning string
 * Otherwise return a success string
 *
 * @author Kuali Student Team
 */
public class CourseRepeatabilityTermResolver implements TermResolver<String> {

    public final static String MAX_REPEATABILITY_ERROR = "kuali.max.repeatability.error";
    public final static String MAX_REPEATABILITY_WARNING = "kuali.max.repeatability.warning";
    public final static String MAX_REPEATABILITY_SUCCESS = "kuali.max.repeatability.success";

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REPEATABILITY;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(2);
        prereqs.add(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS.getName());
        prereqs.add(RulesExecutionConstants.MAX_REPEATABILITY.getName());

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        Integer maxRepeats = (Integer) resolvedPrereqs.get(RulesExecutionConstants.MAX_REPEATABILITY.getName());
        Integer totalAttempts = (Integer) resolvedPrereqs.get(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS.getName());

        String errorLevel = MAX_REPEATABILITY_SUCCESS;

        if (totalAttempts != null && maxRepeats != null) {
            if (totalAttempts >= maxRepeats) {
                errorLevel = MAX_REPEATABILITY_ERROR;
            } else if (maxRepeats > 1 && totalAttempts >= maxRepeats - 1) {
                errorLevel = MAX_REPEATABILITY_WARNING;
            }
        }

        return errorLevel;
    }
}
