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
 * This TermResolver returns TRUE if a student has passed all the courses in the list of courses passed as a parameter.
 *
 * The "list of courses" is obtained from a courseSetId passed as a parameter. The CluService is used to retrieve
 * courseCodes from the courseSetId.
 *
 * The studentId is passed as a resolvedPrereq.
 *
 */
public class CompletedCoursesTermResolver implements TermResolver<Boolean> {

    private TermResolver<List<String>> cluIdsInCluSetTermResolver;
    private TermResolver<Boolean> completedCourseTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSES;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY);
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
                if(!this.getCompletedCourseTermResolver().resolve(resolvedPrereqs, parameters)){
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

    public TermResolver<Boolean> getCompletedCourseTermResolver() {
        return completedCourseTermResolver;
    }

    public void setCompletedCourseTermResolver(TermResolver<Boolean> completedCourseTermResolver) {
        this.completedCourseTermResolver = completedCourseTermResolver;
    }
}
