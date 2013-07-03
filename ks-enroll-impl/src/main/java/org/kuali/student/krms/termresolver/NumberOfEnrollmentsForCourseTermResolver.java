package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/01/25
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class NumberOfEnrollmentsForCourseTermResolver implements TermResolver<Integer> {

    @Override
    public Set<String> getPrerequisites() {
        Set<String> temp = new HashSet<String>(2);
        temp.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        temp.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(temp);
    }

    @Override
    public String getOutput() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<String> getParameterNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getCost() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
