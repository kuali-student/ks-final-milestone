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
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns true if the student has completed all the courses in the courseset with
 * a equal or higher grade than the given grade.
 * <p/>
 * Rule Statement examples:
 * 1) Must not have earned a grade of <gradeType> <grade> or higher in <course>
 * 2) Must have earned a minimum grade of <gradeType> <grade> in <course>
 *
 * @author Kuali Student Team
 */
public class CourseWithGradeTermResolver implements TermResolver<Boolean> {

    private LRCService lrcService;

    private TermResolver<List<StudentCourseRecordInfo>> courseRecordsForCourseIdTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSEWITHGRADE;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> temp = new HashSet<String>(3);
        temp.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY);
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
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String gradeType = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY);
        String grade = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY);

        try {

            ResultValueInfo gradeValue = this.getLrcService().getResultValue(grade, context);
            Integer gradeNumericValue = Integer.valueOf(gradeValue.getNumericValue());

            //Retrieve the list of cluIds from the cluset.
            List<StudentCourseRecordInfo> courseRecords = this.getCourseRecordsForCourseIdTermResolver().resolve(resolvedPrereqs, parameters);
            for (StudentCourseRecordInfo courseRecord : courseRecords) {
                if (gradeType.equals(courseRecord.getAssignedGradeScaleKey())) {
                    ResultValueInfo resultValue = this.getLrcService().getResultValue(courseRecord.getAssignedGradeValue(), context);
                    Integer resultNumericValue = Integer.valueOf(resultValue.getNumericValue());
                    if(resultNumericValue>=gradeNumericValue){
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public TermResolver<List<StudentCourseRecordInfo>> getCourseRecordsForCourseIdTermResolver() {
        return courseRecordsForCourseIdTermResolver;
    }

    public void setCourseRecordsForCourseIdTermResolver(TermResolver<List<StudentCourseRecordInfo>> courseRecordsForCourseIdTermResolver) {
        this.courseRecordsForCourseIdTermResolver = courseRecordsForCourseIdTermResolver;
    }

}
