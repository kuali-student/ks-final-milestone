package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CourseOfferingTermResolverSupport;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kuali Student Team
 */
public class CompletedCoursePriorToTermTermResolver extends CourseOfferingTermResolverSupport<Boolean> {

    private AcademicRecordService academicRecordService;
    private AtpService atpService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEPRIORTOTERM;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<String>(2);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());

        try {
            //Retrieve the version independent clu id.
            String cluId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY);
            String startTermId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY);
            AtpInfo term = this.getAtpService().getAtp(startTermId, context);

            //Retrieve the version independent clu id.
            List<String> courseIds = this.getCluIdsFromVersionIndId(cluId, parameters, context);
            for(String courseId : courseIds){
                //Retrieve the students academic record for this version.
                List<StudentCourseRecordInfo> courseRecords = this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, courseId, context);
                for (StudentCourseRecordInfo courseRecord : courseRecords){
                    AtpInfo atpInfo = this.getAtpForCourseOfferingId(courseRecord.getCourseOfferingId(), parameters, context);
                    if(atpInfo.getEndDate().before(term.getEndDate())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

}
