package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class AcademicRecordTermResolverSupport<T> extends CourseTermResolverSupport<T> {

    private AcademicRecordService academicRecordService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(2);
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    public List<StudentCourseRecordInfo> getAllCourseRecordsForCourse(String personId, String versionIndId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        List<StudentCourseRecordInfo> studentRecords = new ArrayList<>();
        try {
            studentRecords.addAll(this.getAcademicRecordService().getStudentCourseRecordsForCourse(personId, versionIndId, context));
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return studentRecords;
    }

    public List<StudentCourseRecordInfo> getCourseRecordsForCourse(String personId, String versionIndId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        List<StudentCourseRecordInfo> studentRecords = new ArrayList<>();
        try {
            studentRecords.addAll(this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, versionIndId, context));
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return studentRecords;
    }

    public List<StudentCourseRecordInfo> getCourseRecordsForCourseSet(String personId, String cluSetId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        List<StudentCourseRecordInfo> studentRecords = new ArrayList<>();
        try {
            List<String> versionIndIds = this.getCluIdsForCluSet(cluSetId, parameters, context);
            for (String versionIndId : versionIndIds) {
                studentRecords.addAll(this.getCourseRecordsForCourse(personId, versionIndId, parameters, context));
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return studentRecords;
    }

    public boolean checkCourseCompleted(String personId, String versionIndId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        //Retrieve the students academic record for this version.
        try {
            if(this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, versionIndId, context).size()>0){
                return true; //if service returned anything, the student has completed a version of the clu.
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

}
