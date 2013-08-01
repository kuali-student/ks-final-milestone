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
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns an integer based on the number of credits earned for the given course list.
 *
 * Rule statement examples:
 * 1) Must successfully complete no more than  <n> credits from <courses>
 * 2) Must have successfully completed a minimum of <n> credits from <courses>
 * 3) Must not have successfully completed any credits from <courses>
 *
 * @author Kuali Student Team
 */
public class CreditsEarnedFromCoursesTermResolver implements TermResolver<Integer> {

    private TermResolver<List<StudentCourseRecordInfo>> courseRecordsForCourseSetTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLUSET_KEY);
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String personId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);

        Integer credits = 0;
        try {
            //Retrieve the list of cluIds from the cluset.
            List<StudentCourseRecordInfo> recordInfos = this.getCourseRecordsForCourseSetTermResolver().resolve(resolvedPrereqs, parameters);
            for(StudentCourseRecordInfo recordInfo : recordInfos){
                credits += Integer.parseInt(recordInfo.getCreditsEarned());
            }

        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return credits;
    }

    public TermResolver<List<StudentCourseRecordInfo>> getCourseRecordsForCourseSetTermResolver() {
        return courseRecordsForCourseSetTermResolver;
    }

    public void setCourseRecordsForCourseSetTermResolver(TermResolver<List<StudentCourseRecordInfo>> courseRecordsForCourseSetTermResolver) {
        this.courseRecordsForCourseSetTermResolver = courseRecordsForCourseSetTermResolver;
    }
}
