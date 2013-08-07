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
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.Collection;

/**
 * Parent class for all course enrollment propositions
 *
 * @author alubbers
 */
public abstract class CourseEnrollmentProposition extends AbstractLeafProposition {

    protected final boolean checkForAllEnrolled;

    protected Integer minToEnroll;

    public CourseEnrollmentProposition(Integer minToEnroll) {
        this.checkForAllEnrolled = false;
        this.minToEnroll = minToEnroll;
    }

    public CourseEnrollmentProposition() {
        checkForAllEnrolled = true;
    }


    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        Collection<String> enrolledCourses = environment.resolveTerm(RulesExecutionConstants.enrolledCourseIdsTerm, this);

        Collection<String> requiredCourseIds = getRequiredCourseIds(environment);

        PropositionResult result = null;

        if(checkForAllEnrolled) {
            result = new PropositionResult(enrolledCourses.containsAll(requiredCourseIds));
        }

        else {
            result = new PropositionResult(CollectionUtils.intersection(enrolledCourses, requiredCourseIds).size() >= minToEnroll);
        }

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult()));

        return result;

    }

    protected abstract Collection<String> getRequiredCourseIds(ExecutionEnvironment environment);
}
