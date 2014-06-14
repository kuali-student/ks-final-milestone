package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class AcademicRecordTermResolverSupport<T> extends CourseTermResolverSupport<T> {

    private AcademicRecordService academicRecordService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(RulesExecutionConstants.PERSON_ID_TERM.getName());
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    public List<StudentCourseRecordInfo> getCourseRecordsForCourse(String personId, String versionIndId, ContextInfo context) throws Exception {
        List<StudentCourseRecordInfo> studentRecords = new ArrayList<StudentCourseRecordInfo>();

        List<String> courseIds = this.getCluIdsFromVersionIndId(versionIndId, context);
        for (String courseId : courseIds) {
            studentRecords.addAll(this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, courseId, context));
        }

        return studentRecords;
    }

    public List<StudentCourseRecordInfo> getCourseRecordsForCourseSet(String personId, String cluSetId, ContextInfo context) throws Exception {
        List<StudentCourseRecordInfo> studentRecords = new ArrayList<StudentCourseRecordInfo>();

        List<String> versionIndIds = this.getCluIdsForCluSet(cluSetId, context);
        for (String versionIndId : versionIndIds) {
            studentRecords.addAll(this.getCourseRecordsForCourse(personId, versionIndId, context));
        }

        return studentRecords;
    }

    public boolean checkCourseCompleted(String personId, String versionIndId, ContextInfo context) throws Exception {
        //Retrieve the students academic record for this version.
        List<String> courseIds = this.getCluIdsFromVersionIndId(versionIndId, context);
        for(String courseId : courseIds){
            if(this.getAcademicRecordService().getCompletedCourseRecordsForCourse(personId, courseId, context).size()>0){
                return true; //if service returned anything, the student has completed a version of the clu.
            }
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
