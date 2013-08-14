package org.kuali.student.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns the atp for the given course offering.
 *
 * @author Kuali Student Team
 */
public class AtpForCourseOfferingIdTermResolver implements TermResolver<AtpInfo> {

    private CourseOfferingService courseOfferingService;
    private AtpService atpService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
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
        parameters.add(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY);
        return Collections.unmodifiableSet(parameters);
    }

    @Override
    public int getCost() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AtpInfo resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        try {
            //Retrieve the start and end term.
            String coId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CO_KEY);
            CourseOffering courseOffering = this.getCourseOfferingService().getCourseOffering(coId, context);
            return this.getAtpService().getAtp(courseOffering.getTermId(), context);
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return null;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

}
