package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/01/25
 * Time: 2:19 PM
 *
 * This TermResolver returns TRUE if a student has passed all the courses in the list of courses passed as a parameter.
 *
 * The "list of courses" could be only a single courseId or courseCode, or a courseSetId of a comma seperated list of
 * coursecodes. The CluService is used to retrieve courseCodes based on the courseId and courseSetId.
 *
 * The studentId is passed as a resolvedPrereq.
 *
 */
public class CompletedCoursesTermResolver extends AbstractCourseTermResolver implements TermResolver<Boolean> {

    @Override
    public String getOutput() {
        return KSKRMSExecutionConstants.COMPLETED_COURSES_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSExecutionConstants.COURSE_CODE_TERM_PROPERTY);
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 1;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        //Get the number of completed courses in list.
        TermResolver<Integer> numberOfCompletedCoursesTermResolver = new NumberOfCompletedCoursesTermResolver();
        Integer completedCourses = numberOfCompletedCoursesTermResolver.resolve(resolvedPrereqs, parameters);

        //Get the number of the courses in the list.
        int courses = 0;
        String[] courseCodes = this.resolveCourseCodes(parameters);
        if (courseCodes != null){
            courses = courseCodes.length;
        }

        return completedCourses == courses;
    }
}
