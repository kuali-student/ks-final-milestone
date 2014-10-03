package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class CourseRegistrationTermResolverSupport<T> extends CourseOfferingTermResolverSupport<T> {

    private CourseRegistrationService courseRegistrationService;

    public boolean checkCourseEnrolled(String personId, String versionIndId, String termId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        try {
            List<String> courseIds = this.getCluIdsFromVersionIndId(versionIndId, parameters, context);

            //First check in the students current registration requests
            List<String> regGroupIds = new ArrayList<>();
            List<RegistrationRequestInfo> regRequests = this.getCourseRegistrationService().getUnsubmittedRegistrationRequestsByRequestorAndTerm(personId, termId, context);
            for (RegistrationRequestInfo request : regRequests) {
                for (RegistrationRequestItemInfo regItem : request.getRegistrationRequestItems()) {
                    if (regItem.getExistingCourseRegistrationId() != null) {
                        // FIXME KSENROLL-11465
                        // the existing registration is an lpr  so this can't work this way.
                        regGroupIds.remove(regItem.getExistingCourseRegistrationId());
                    }
                    regGroupIds.add(regItem.getRegistrationGroupId());
                }
            }

            //Check to see if one of the course version is in the registration request.
            for (String regGroupId : regGroupIds) {
                RegistrationGroupInfo regGroup = this.getCourseOfferingService().getRegistrationGroup(regGroupId, context);
                CourseOfferingInfo courseOffering = this.getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), context);
                if (courseIds.contains(courseOffering.getCourseId())) {
                    return true;
                }
            }

            //Also check for already enrolled but not yet completed courses.
            List<CourseRegistrationInfo> recordInfoList = this.getCourseRegistrationService().getCourseRegistrationsByStudent(personId, context);
            for (CourseRegistrationInfo studentRecord : recordInfoList) {
                CourseOffering courseOffering = this.getCourseOfferingService().getCourseOffering(studentRecord.getCourseOfferingId(), context);
                if (courseIds.contains(courseOffering.getCourseId())) {
                    return true;
                }
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return false;
    }

    protected String getVersionIndIdFromCourseOfferingId(String courseOfferingId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(courseOfferingId, contextInfo);
        String cluId = courseOfferingInfo.getCourseId();
        return getCluService().getVersionIndependentId(cluId, contextInfo);
    }

    protected Integer getMatchedCoursesCount(String versionIndId, List<CourseRegistrationInfo> courseRegistrationInfoList, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Integer matchedCourses = 0;
        for (CourseRegistrationInfo courseRegistrationInfo:courseRegistrationInfoList) {
            String waitlistVersionIndId = getVersionIndIdFromCourseOfferingId(courseRegistrationInfo.getCourseOfferingId(), contextInfo);
            if (waitlistVersionIndId.equals(versionIndId)) {
                matchedCourses++;
            }
        }
        return matchedCourses;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
