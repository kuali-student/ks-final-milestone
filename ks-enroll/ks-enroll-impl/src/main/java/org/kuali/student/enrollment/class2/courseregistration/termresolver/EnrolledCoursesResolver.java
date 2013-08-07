package org.kuali.student.enrollment.class2.courseregistration.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnrolledCoursesResolver implements TermResolver<Collection<String>> {

    private CourseRegistrationService courseRegService;

    private final static Set<String> prerequisites = new HashSet<String>(2);

    static {
        prerequisites.add(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        prerequisites.add(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    @Override
    public Set<String> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.STUDENT_ENROLLED_COURSE_IDS_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        // TODO analyze actual cost
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(RulesExecutionConstants.STUDENT_ID_TERM_NAME).toString();
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);

        Collection<String> results = null;

        try {
            List<CourseRegistrationInfo> registrations = courseRegService.getCourseRegistrationsByStudent(studentId, context);

            results = new ArrayList<String>(registrations.size());

            for (CourseRegistrationInfo courseRegInfo : registrations) {
                if (courseRegInfo.getCourseOfferingId() != null) {
                    results.add(courseRegInfo.getCourseOfferingId());
                }
            }
        } catch (InvalidParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (MissingParameterException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (OperationFailedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (PermissionDeniedException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        }

        return results;
    }
}
