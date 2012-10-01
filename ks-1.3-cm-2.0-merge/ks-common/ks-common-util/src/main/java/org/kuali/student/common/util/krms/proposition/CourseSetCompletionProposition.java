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

import java.util.Collection;
import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

/**
 * Proposition that checks for completion of a set of courses
 *
 * @author alubbers
 */
public class CourseSetCompletionProposition extends CourseCompletionProposition {

    private String courseSetId;
    
    private Collection<String> courseIds;
    
    public CourseSetCompletionProposition(String courseSetId, Integer minToComplete) {
        super(minToComplete);
        this.courseSetId = courseSetId;
    }

    public CourseSetCompletionProposition(String courseSetId) {
        super();
        this.courseSetId = courseSetId;
    }

    @Override
    protected Collection<String> getTermCourseIds(ExecutionEnvironment environment) {
        
        if(courseIds == null) {
            
            Term term = new Term(RulesExecutionConstants.COURSE_SET_TERM_NAME, Collections.singletonMap(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY, courseSetId));

            courseIds = environment.resolveTerm(term, this);
        }
        
        return courseIds;
    }

}
