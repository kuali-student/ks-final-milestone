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

package org.kuali.student.common.util.krms.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.Collection;
import java.util.Collections;

/**
 * Proposition that checks for current enrollment in a set of courses
 *
 * @author alubbers
 */
public class CourseSetEnrollmentProposition extends CourseEnrollmentProposition {

    private String courseSetId;

    private Collection<String> courseIds;

    public CourseSetEnrollmentProposition(String courseSetId, Integer minToEnroll) {
        super(minToEnroll);
        this.courseSetId = courseSetId;
    }

    public CourseSetEnrollmentProposition(String courseSetId) {
        super();
        this.courseSetId = courseSetId;
    }

    @Override
    protected Collection<String> getRequiredCourseIds(ExecutionEnvironment environment) {

        if(courseIds == null) {

            Term term = new Term(RulesExecutionConstants.COURSE_SET_TERM_NAME, Collections.singletonMap(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY, courseSetId));

            courseIds = environment.resolveTerm(term, this);
        }

        return courseIds;
    }
}
