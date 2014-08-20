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
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns true if the student has received a certain grade in a course.
 * <p/>
 * Rule Statement examples:
 * 1) Must not have earned a grade of <grade> in <course>
 * 2) Must have earned a grade of <grade> in <course>
 *
 * @author Kuali Student Team
 */
public class StudentWithGradeForCourseTermResolver implements TermResolver<Boolean> {

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_STUDENT_WITH_GRADE_FOR_COURSE;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> params = new HashSet<>(1);
        params.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY);
        return Collections.unmodifiableSet(params);
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(1);
        prereqs.add(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        try {
            String grade = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY);

            List<StudentCourseRecordInfo> studentCourseRecords = (List<StudentCourseRecordInfo>) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT);
            for (StudentCourseRecordInfo record : studentCourseRecords) {
                if (record.getAssignedGradeValue().equals(grade)) {
                    return true;
                }
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

}
