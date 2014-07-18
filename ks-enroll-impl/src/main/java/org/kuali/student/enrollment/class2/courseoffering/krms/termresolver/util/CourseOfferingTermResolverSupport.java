package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.Map;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class CourseOfferingTermResolverSupport<T> extends CourseTermResolverSupport<T> {

    private CourseOfferingService courseOfferingService;
    private AtpService atpService;

    public AtpInfo getAtpForCourseOfferingId(String coId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        //Retrieve term.
        try {
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
