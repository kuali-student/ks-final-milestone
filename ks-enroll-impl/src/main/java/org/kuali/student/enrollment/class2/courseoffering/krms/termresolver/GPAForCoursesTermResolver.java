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
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.AcademicRecordTermResolverSupport;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Rule statement examples:
 * 1) Must have earned a minimum GPA of <GPA> in <courses>
 *
 * @author Kuali Student Team
 */
public class GPAForCoursesTermResolver extends AcademicRecordTermResolverSupport<Float> {

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_GPAFORCOURSES;
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
    public Float resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());

        try {
            //Retrieve the completed course records from cluset.
            String cluSetId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY);
            List<StudentCourseRecordInfo> studentCourseRecordInfoList = this.getCourseRecordsForCourseSet(personId, cluSetId, parameters, context);

            if (studentCourseRecordInfoList.size() > 0) {
                GPAInfo gpa = this.getAcademicRecordService().calculateGPA(studentCourseRecordInfoList, AcademicRecordServiceConstants.ACADEMIC_RECORD_CALCULATION_GPA_TYPE_KEY, context);
                return Float.valueOf(gpa.getValue());
            }

        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return new Float(0);
    }

}
