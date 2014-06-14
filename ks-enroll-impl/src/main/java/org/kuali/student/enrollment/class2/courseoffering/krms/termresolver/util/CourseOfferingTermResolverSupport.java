package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class CourseOfferingTermResolverSupport<T> extends CourseTermResolverSupport<T> {

    private CourseOfferingService courseOfferingService;
    private AtpService atpService;

    public AtpInfo getAtpForCourseOfferingId(String coId, ContextInfo context) throws Exception {
        //Retrieve term.
        CourseOffering courseOffering = this.getCourseOfferingService().getCourseOffering(coId, context);
        return this.getAtpService().getAtp(courseOffering.getTermId(), context);
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
