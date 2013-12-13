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

package org.kuali.student.enrollment.class2.examoffering.krms.termresolver;

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
 * Return true if student has an unsubmitted registration request that includes
 * the given course or if the student is currently enrolled for the given course.
 *
 * Example rule statement:
 * 1) Must be concurrently enrolled in all courses from <courses>
 *
 * @author Kuali Student Team
 */
public class MatchingCourseSetTermResolver implements TermResolver<Boolean> {

    private TermResolver<List<String>> cluIdsInCluSetTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_COURSE_VERSIONINDID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_MATCHINGCOURSESET;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> temp = new HashSet<String>(3);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLUSET_KEY);
        return Collections.unmodifiableSet(temp);
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        try {

            String versionIndId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_COURSE_VERSIONINDID);
            List<String> versionIndIds = this.getCluIdsInCluSetTermResolver().resolve(resolvedPrereqs, parameters);
            if(versionIndIds.contains(versionIndId)){
                return true;
            }

        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

    public TermResolver<List<String>> getCluIdsInCluSetTermResolver() {
        return cluIdsInCluSetTermResolver;
    }

    public void setCluIdsInCluSetTermResolver(TermResolver<List<String>> cluIdsInCluSetTermResolver) {
        this.cluIdsInCluSetTermResolver = cluIdsInCluSetTermResolver;
    }

}
