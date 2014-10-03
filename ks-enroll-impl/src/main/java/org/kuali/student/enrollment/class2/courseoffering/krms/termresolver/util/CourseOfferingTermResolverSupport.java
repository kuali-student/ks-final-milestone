package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
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

    protected CourseRegistrationInfo createNewCourseRegistration(RegistrationRequestItemInfo item, ContextInfo contextInfo) throws OperationFailedException {
        CourseRegistrationInfo reg = new CourseRegistrationInfo();
        RegistrationGroupInfo regGroup;
        try {
            regGroup = courseOfferingService.getRegistrationGroup(item.getRegistrationGroupId(), contextInfo);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("new reg group should exist", ex);
        } catch (InvalidParameterException | MissingParameterException | PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        reg.setPersonId(item.getPersonId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);
        reg.setCourseOfferingId(regGroup.getCourseOfferingId());
        reg.setCredits(item.getCredits());
        reg.setGradingOptionId(item.getGradingOptionId());
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        reg.setRegistrationGroupId(regGroup.getId());
        // Adding all but we might want to split and store some attributes on the Activity and others on Course registration
        reg.getAttributes().addAll(item.getAttributes());
        return reg;
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
