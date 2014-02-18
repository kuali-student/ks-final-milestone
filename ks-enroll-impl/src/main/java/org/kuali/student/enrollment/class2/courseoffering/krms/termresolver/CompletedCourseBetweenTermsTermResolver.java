package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
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
 * Return true if student has successfully completed the course between the
 * given two terms.
 *
 * Rule statement example:
 * 1) Must have successfully completed <course> between <term1> and <term2>
 *
 * @author Kuali Student Team
 */
public class CompletedCourseBetweenTermsTermResolver implements TermResolver<Boolean> {

    private AcademicRecordService academicRecordService;
    private AtpService atpService;

    private TermResolver<List<String>> cluIdsTermResolver;
    private TermResolver<AtpInfo> atpForCOIdTermResolver;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COMPLETEDCOURSEBETWEENTERMS;
    }

    @Override
    public Set<String> getParameterNames() {
        Set<String> parameters = new HashSet<String>(2);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLU_KEY);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY);
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String personId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);

        try {
            //Retrieve the start and end term.
            String startTermId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY);
            AtpInfo startTerm = this.getAtpService().getAtp(startTermId, context);

            String endTermId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY);
            AtpInfo endTerm = this.getAtpService().getAtp(endTermId, context);

            //Retrieve the version independent clu id.
            List<String> courseIds = this.getCluIdsTermResolver().resolve(resolvedPrereqs, parameters);
            for(String courseId : courseIds){
                //Retrieve the students academic record for this version.
                List<StudentCourseRecordInfo> courseRecords = this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, courseId, context);
                for (StudentCourseRecordInfo courseRecord : courseRecords){
                    parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CO_KEY, courseRecord.getCourseOfferingId());
                    AtpInfo atpInfo = this.getAtpForCOIdTermResolver().resolve(resolvedPrereqs, parameters);
                    if((atpInfo.getStartDate().before(startTerm.getStartDate())) || (atpInfo.getEndDate().after(endTerm.getEndDate()))){
                        continue;
                    }
                    return true;
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

    public TermResolver<AtpInfo> getAtpForCOIdTermResolver() {
        return atpForCOIdTermResolver;
    }

    public void setAtpForCOIdTermResolver(TermResolver<AtpInfo> atpForCOIdTermResolver) {
        this.atpForCOIdTermResolver = atpForCOIdTermResolver;
    }

    public TermResolver<List<String>> getCluIdsTermResolver() {
        return cluIdsTermResolver;
    }

    public void setCluIdsTermResolver(TermResolver<List<String>> cluIdsTermResolver) {
        this.cluIdsTermResolver = cluIdsTermResolver;
    }
}
