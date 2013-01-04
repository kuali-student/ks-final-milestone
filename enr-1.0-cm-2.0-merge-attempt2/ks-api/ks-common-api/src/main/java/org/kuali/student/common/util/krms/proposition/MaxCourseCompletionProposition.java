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

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.Collection;
import java.util.Collections;

/**
 * Proposition class that checks for completion of no more than N of the given courses
 */
public class MaxCourseCompletionProposition extends AbstractLeafProposition {

    private final String courseSetId;

    private final String singleCourseId;

    private final Integer maxCoursesCompleted;

    public MaxCourseCompletionProposition(String singleCourseId) {
        this.courseSetId = null;
        this.singleCourseId = singleCourseId;
        this.maxCoursesCompleted = 0;
    }

    public MaxCourseCompletionProposition(String courseSetId, Integer maxCourses) {
        this.courseSetId = courseSetId;
        this.singleCourseId = null;
        this.maxCoursesCompleted = maxCourses;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        Collection<String> completedCourses = environment.resolveTerm(RulesExecutionConstants.completedCourseIdsTerm, this);
        Collection<String> coursesToCheck;

        if(singleCourseId == null) {
            Term term = new Term(RulesExecutionConstants.COURSE_SET_TERM_NAME, Collections.singletonMap(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY, courseSetId));
            coursesToCheck = environment.resolveTerm(term, this);
        }
        else {
            coursesToCheck = Collections.singleton(singleCourseId);
        }


        PropositionResult result = new PropositionResult(CollectionUtils.intersection(completedCourses, coursesToCheck).size() <= maxCoursesCompleted);

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult()));

        return result;
    }
}
