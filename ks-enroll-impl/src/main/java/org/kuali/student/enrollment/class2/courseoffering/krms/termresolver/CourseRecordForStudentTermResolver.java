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
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.AcademicRecordTermResolverSupport;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Return a student's academic record for a single CLU.
 *
 * This is intended to be an intermediary term resolver that provides the student's course record to
 * dependent term resolvers that use that data to resolve themselves.
 *
 * @author Kuali Student Team
 */
public class CourseRecordForStudentTermResolver extends AcademicRecordTermResolverSupport<List<StudentCourseRecordInfo>> {

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSE_RECORD_FOR_STUDENT;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(5);
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.CLU_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.ACADEMIC_RECORD_SERVICE_TERM.getName());
        prereqs.add(RulesExecutionConstants.CLU_SERVICE.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public List<StudentCourseRecordInfo> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        AcademicRecordService academicRecordService = (AcademicRecordService) resolvedPrereqs.get(RulesExecutionConstants.ACADEMIC_RECORD_SERVICE_TERM.getName());
        this.setAcademicRecordService(academicRecordService);

        CluService cluService = (CluService) resolvedPrereqs.get(RulesExecutionConstants.CLU_SERVICE.getName());
        this.setCluService(cluService);


        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());
        CluInfo cluInfo = (CluInfo) resolvedPrereqs.get(RulesExecutionConstants.CLU_INFO_TERM.getName());

        try {
            // Retrieve the students academic record for this course.
            List<StudentCourseRecordInfo> records = this.getAllCourseRecordsForCourse(personId, cluInfo.getVersion().getVersionIndId(), parameters, context);
            return records;
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return null;
    }

}
