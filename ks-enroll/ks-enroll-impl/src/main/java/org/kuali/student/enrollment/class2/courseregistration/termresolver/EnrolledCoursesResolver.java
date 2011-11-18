package org.kuali.student.enrollment.class2.courseregistration.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;
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

    private final static Set<TermSpecification> prerequisites = new HashSet<TermSpecification>(2);

    static {
        prerequisites.add(RulesExecutionConstants.studentIdTermSpec);
        prerequisites.add(RulesExecutionConstants.contextInfoTermSpec);
    }

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public TermSpecification getOutput() {
        return RulesExecutionConstants.enrolledCourseIdsTermSpec;
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
    public Collection<String> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(RulesExecutionConstants.studentIdTermSpec).toString();
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.contextInfoTermSpec);

        Collection<String> results = null;

        try {
            List<CourseRegistrationInfo> registrations = courseRegService.getCourseRegistrationsForStudent(studentId, context);

            results = new ArrayList<String>(registrations.size());

            for (CourseRegistrationInfo courseRegInfo : registrations) {
                CourseOfferingInfo co = courseRegInfo.getCourseOffering();
                if(co != null && co.getCourseId() != null) {
                    results.add(courseRegInfo.getCourseOffering().getCourseId());
                }
            }
        } catch (DoesNotExistException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
        } catch (DisabledIdentifierException e) {
            throw new TermResolutionException(e.getMessage(), this, parameters, e);
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
