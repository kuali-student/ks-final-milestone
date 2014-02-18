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

package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns true if the student has completed all the courses in the courseset with
 * a equal or higher grade than the given grade.
 *
 * Rule Statement examples:
 * 1) Must not have earned a grade of <gradeType> <grade> or higher in <courses>
 * 2) Must have earned a minimum grade of <gradeType> <grade> in <courses>
 *
 * @author Kuali Student Team
 */
public class CoursesWithGradeTermResolver implements TermResolver<Boolean> {

    private TermResolver<List<String>> cluIdsInCluSetTermResolver;
    private TermResolver<Boolean> courseWithGradeTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSESWITHGRADE;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> temp = new HashSet<String>(3);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY);
        return Collections.unmodifiableSet(temp);
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        try {
            //Retrieve the list of cluIds from the cluset.
            List<String> versionIndIds = this.getCluIdsInCluSetTermResolver().resolve(resolvedPrereqs, parameters);
            for(String versionIndId : versionIndIds){
                parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY, versionIndId);
                if(!this.getCourseWithGradeTermResolver().resolve(resolvedPrereqs, parameters)){
                    return false;
                }
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return true;
    }

    public TermResolver<List<String>> getCluIdsInCluSetTermResolver() {
        return cluIdsInCluSetTermResolver;
    }

    public void setCluIdsInCluSetTermResolver(TermResolver<List<String>> cluIdsInCluSetTermResolver) {
        this.cluIdsInCluSetTermResolver = cluIdsInCluSetTermResolver;
    }

    public TermResolver<Boolean> getCourseWithGradeTermResolver() {
        return courseWithGradeTermResolver;
    }

    public void setCourseWithGradeTermResolver(TermResolver<Boolean> courseWithGradeTermResolver) {
        this.courseWithGradeTermResolver = courseWithGradeTermResolver;
    }

}
