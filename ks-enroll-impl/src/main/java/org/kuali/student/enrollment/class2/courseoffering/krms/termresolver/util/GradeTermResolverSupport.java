package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.List;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class GradeTermResolverSupport<T> extends AcademicRecordTermResolverSupport<T> {

    public LRCService lrcService;

    public boolean checkCourseWithGrade(String personId, String versionIndId, String grade, String gradeType, ContextInfo context) throws Exception {
        ResultValueInfo gradeValue = this.getLrcService().getResultValue(grade, context);
        Integer gradeNumericValue = Integer.valueOf(gradeValue.getNumericValue());

        //Retrieve the list of cluIds from the cluset.
        List<StudentCourseRecordInfo> courseRecords = this.getCourseRecordsForCourse(personId, versionIndId, context);
        for (StudentCourseRecordInfo courseRecord : courseRecords) {
            if (gradeType.equals(courseRecord.getAssignedGradeScaleKey())) {
                ResultValueInfo resultValue = this.getLrcService().getResultValue(courseRecord.getAssignedGradeValue(), context);
                Integer resultNumericValue = Integer.valueOf(resultValue.getNumericValue());
                if(resultNumericValue>=gradeNumericValue){
                    return true;
                }
            }
        }

        return false;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
}
