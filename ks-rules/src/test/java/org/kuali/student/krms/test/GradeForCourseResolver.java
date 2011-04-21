package org.kuali.student.krms.test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.service.LrcService;

public class GradeForCourseResolver implements TermResolver<GradeInfo> {

    private LrcService lrcService;

    public GradeForCourseResolver(LrcService lrcService) {
        this.lrcService = lrcService;
    }
    
    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.singleton(Constants.studentIdTermSpec);
    }

    @Override
    public TermSpecification getOutput() {
        return Constants.gradeForCourseTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(Constants.COURSE_ID_TERM_PROPERTY_NAME);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public GradeInfo resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(Constants.studentIdTermSpec).toString();
        String courseId = parameters.get(Constants.COURSE_ID_TERM_PROPERTY_NAME);
        
        try {
            if(studentId.equals("1")) {
                if(courseId.equals("1")) {
                    return getPassGrade();
                }
            }
            else if(studentId.equals("2")) {
                if (courseId.equals("2")){
                    return getPassGrade();
                }
                else if(courseId.equals("3")) {
                    return getFailGrade();
                }
            }
            else if(studentId.equals("3")) {
                if(courseId.equals("1") || courseId.equals("2") || courseId.equals("3")) {
                    return getPassGrade();
                }
            }
        } catch(Exception e) {
            throw new TermResolutionException(e);
        }

        throw new TermResolutionException("Could not find grade for student id: " + studentId + " and course id: " + courseId);
    }

    private GradeInfo getFailGrade() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lrcService.getGrade(Constants.PASS_GRADE_ID);
    }

    private GradeInfo getPassGrade() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return lrcService.getGrade(Constants.FAIL_GRADE_ID);
    }

}
