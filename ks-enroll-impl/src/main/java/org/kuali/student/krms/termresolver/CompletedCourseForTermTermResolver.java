package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/07/15
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompletedCourseForTermTermResolver implements TermResolver<Boolean> {

    private AcademicRecordService academicRecordService;
    private CourseOfferingService courseOfferingService;

    private TermResolver<List<String>> courseIdsTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEFORTERM;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<String>(2);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String personId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);

        try {
            //Retrieve the version independent clu id.
            String termId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY);

            //Retrieve the version independent clu id.
            List<String> courseIds = this.getCourseIdsTermResolver().resolve(resolvedPrereqs, parameters);
            for(String courseId : courseIds){
                //Retrieve the students academic record for this version.
                List<StudentCourseRecordInfo> courseRecords = this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, courseId, context);
                for (StudentCourseRecordInfo courseRecord : courseRecords){
                    CourseOffering courseOffering = this.getCourseOfferingService().getCourseOffering(courseRecord.getCourseOfferingId(), context);
                    if(termId.equals(courseOffering.getTermId())){
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

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public TermResolver<List<String>> getCourseIdsTermResolver() {
        return courseIdsTermResolver;
    }

    public void setCourseIdsTermResolver(TermResolver<List<String>> courseIdsTermResolver) {
        this.courseIdsTermResolver = courseIdsTermResolver;
    }
}
