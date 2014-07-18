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
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.AcademicRecordTermResolverSupport;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Return the number of completed courses from a given course list.
 *
 * The "list of courses" is obtained from a courseSetId passed as a parameter. The CluService is used to retrieve
 * courseCodes from the courseSetId.
 *
 * The studentId is passed as a resolvedPrereq.
 *
 * @author Kuali Student Team
 */
public class NumberOfCompletedCoursesTermResolver extends AcademicRecordTermResolverSupport<Integer> {

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES;
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
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());

        //Create a counter so that we can check that the student completed all of the courses.
        int clusCompleted = 0;
        try {
            ///Retrieve the list of cluIds from the cluset.
            String cluSetId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY);
            List<String> versionIndIds = this.getCluIdsForCluSet(cluSetId, parameters, context);
            for(String versionIndId : versionIndIds){
                if(this.checkCourseCompleted(personId, versionIndId, parameters, context)){
                    clusCompleted++;
                }
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return clusCompleted;
    }

}
