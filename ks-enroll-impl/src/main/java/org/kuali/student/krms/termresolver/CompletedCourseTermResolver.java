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

package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


public class CompletedCourseTermResolver extends AbstractCourseTermResolver implements TermResolver<Boolean> {

    @Override
    public String getOutput() {
        return KSKRMSExecutionConstants.COMPLETED_COURSES_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 1;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        TermResolver<Boolean> completedCoursesTermResolver = new CompletedCoursesTermResolver();

        String courseCode = this.resolveCourseCode(parameters);

        parameters.put(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY, courseCode);
        return completedCoursesTermResolver.resolve(resolvedPrereqs, parameters);
    }
}
